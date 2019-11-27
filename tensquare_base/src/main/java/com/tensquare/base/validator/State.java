package com.tensquare.base.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.constraints.NotNull;
import java.lang.annotation.*;

@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER, ElementType.TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = {StateValidator.class})
public @interface State {
    boolean required() default true;

    String message() default "状态只能是1或0";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    @Documented
    public @interface List {
        State[] value();
    }
}
