package com.px.project.common.customannotation;

import java.lang.annotation.*;

@Target(value={ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface AttributeAnnotation {
    String value() default "";
    boolean isGenerate() default true;//是否生成sql
    boolean isPrimaryKey() default false;//是否主键
}
