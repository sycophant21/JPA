package com.domain.helper;

import java.lang.annotation.*;

@Documented
@Target(ElementType.TYPE)
@Inherited
@Retention(RetentionPolicy.RUNTIME)

public @interface TableNameAnnotation {
    String tableName() default "";
}
