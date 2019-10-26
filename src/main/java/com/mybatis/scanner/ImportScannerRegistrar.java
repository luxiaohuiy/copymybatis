package com.mybatis.scanner;

import com.mybatis.annotion.MyMapperScan;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.type.AnnotationMetadata;

import java.io.File;
import java.io.FileFilter;
import java.net.URL;
import java.net.URLDecoder;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;

public class ImportScannerRegistrar implements ImportBeanDefinitionRegistrar {

    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        AnnotationAttributes mapperScanAttrs = AnnotationAttributes
                .fromMap(importingClassMetadata.getAnnotationAttributes(MyMapperScan.class.getName()));
        Set<String> set = mapperScanAttrs.keySet();
        for (String s : set){
            String[] allPackage = (String[]) mapperScanAttrs.get(s);
            for (int i = 0; i < allPackage.length; i++) {
                String packageName = allPackage[i];
                /**
                 * getMapperFromPackage
                 * 第二个参数是是否扫描子包
                 */
                Set<Class<?>> singletonlMapper = getMapperFromPackage(packageName, true);
                registerMapper(singletonlMapper,registry);
            }
        }
    }

    public void registerMapper(Set<Class<?>> classSet,BeanDefinitionRegistry registry){
        for (Class<?> clazz : classSet) {
            String className = clazz.getName();
            String name = clazz.getSimpleName();
            String simpleName = name.substring(0,1).toLowerCase() + name.substring(1);
            BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.genericBeanDefinition(MyFactoryBean.class);
            AbstractBeanDefinition beanDefinition = beanDefinitionBuilder.getBeanDefinition();
            registry.registerBeanDefinition(simpleName,beanDefinition);
            beanDefinition.getConstructorArgumentValues().addGenericArgumentValue(className);
            beanDefinition.setAutowireMode(3);
        }
    }

    public static Set<Class<?>> getMapperFromPackage(String packageName,boolean recursive) {
        Set<Class<?>> clazzs = new HashSet<Class<?>>();
        // 包名对应的路径名称
        String packageDirName = packageName.replace('.', '/');
        Enumeration<URL> dirs;

        try {
            dirs = Thread.currentThread().getContextClassLoader().getResources(packageDirName);
            while (dirs.hasMoreElements()) {

                URL url = dirs.nextElement();
                String protocol = url.getProtocol();

                if ("file".equals(protocol)) {
                    String filePath = URLDecoder.decode(url.getFile(), "UTF-8");
                    findMapperInPackageByFile(packageName, filePath, recursive, clazzs);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return clazzs;
    }

    /**
     * 在package对应的路径下找到所有的class
     */
    public static void findMapperInPackageByFile(String packageName, String filePath, final boolean recursive,
                                                 Set<Class<?>> clazzs) {
        File dir = new File(filePath);
        if (!dir.exists() || !dir.isDirectory()) {
            return;
        }
        // 在给定的目录下找到所有的文件，并且进行条件过滤
        File[] dirFiles = dir.listFiles(new FileFilter() {

            public boolean accept(File file) {
                boolean acceptDir = recursive && file.isDirectory();// 接受dir目录
                boolean acceptClass = file.getName().endsWith("class");// 接受class文件
                return acceptDir || acceptClass;
            }
        });

        for (File file : dirFiles) {
            if (file.isDirectory()) {
                findMapperInPackageByFile(packageName + "." + file.getName(), file.getAbsolutePath(), recursive, clazzs);
            } else {
                String className = file.getName().substring(0, file.getName().length() - 6);
                try {
                    Class<?> clazz = Thread.currentThread().getContextClassLoader().loadClass(packageName + "." + className);
                    if (clazz.isInterface()){
                        clazzs.add(clazz);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
