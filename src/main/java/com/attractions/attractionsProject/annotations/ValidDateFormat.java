package com.attractions.attractionsProject.annotations;

import com.attractions.attractionsProject.validators.ValidDateFormatValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = ValidDateFormatValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidDateFormat {

    String message() default "Invalid date format. Please, enter a date that matches the following structure: yyyy" +
            ".mm.dd ee - where yyyy stands for year, mm for month, dd for day and ee for era (BC or AD case-sensitive)";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
