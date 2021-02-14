package com.domain.helper;

import java.lang.annotation.*;

@Documented
@Target(ElementType.FIELD)
@Inherited
@Retention(RetentionPolicy.RUNTIME)

public @interface CustomAnnotations {
    String alternateFieldName() default "";
    int alternateSize() default 255;
    boolean primaryKey() default false;
    DBMapping dbMapping() default DBMapping.NONE;
    boolean isNullable() default true;

}
