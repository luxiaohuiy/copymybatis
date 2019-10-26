package com.mybatis.annotion;

import com.mybatis.scanner.ImportScannerRegistrar;
import org.springframework.context.annotation.Import;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@Import({ImportScannerRegistrar.class})
public @interface MyMapperScan {

    String[] value() default {};

}
