package com.attractions.attractionsProject.validators;

import com.attractions.attractionsProject.annotations.ValidDateFormat;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Calendar;
import java.util.regex.Pattern;

public class ValidDateFormatValidator implements ConstraintValidator<ValidDateFormat, String> {

    @Override
    public void initialize(ValidDateFormat constraintAnnotation) {
    }

    @Override
    public boolean isValid(String date, ConstraintValidatorContext constraintValidatorContext) {

        String regex = "^[0-9]{4}[.](0[1-9]|1[0-2])[.](0[1-9]|[12][0-9]|3[01]) (BC|AD)$";
        if (date == null) {

            return true;

        } else if (!Pattern.matches(regex, date)) {

            return false;
        }

        int[] dateArr = new int[]{
                Integer.parseInt(date.substring(0, 4)),
                Integer.parseInt(date.substring(5, 7)),
                Integer.parseInt(date.substring(8, 10))
        };

        if (date.startsWith("BC", 11)) {

            dateArr[0]--;
        }

        if (isNotInAcceptableRange(dateArr)) {

            constraintValidatorContext.disableDefaultConstraintViolation();
            constraintValidatorContext
                    .buildConstraintViolationWithTemplate("Given date isn't in acceptable range. Date must be " +
                            "greater than 4714.01.01 BC and lesser than current date.")
                    .addConstraintViolation();

            return false;
        }

        if (dateArr[1] == 2 && dateArr[2] == 29) {

            return isLeap(dateArr[0]);
        }
        return true;
    }

    private boolean isLeap(int date) {

        if (date % 4 == 0) {

             if (date % 100 == 0) {

                 return date % 400 == 0;
             }
             return true;
        }
        return false;
    }

    private boolean isNotInAcceptableRange(int[] date) {

        java.sql.Date dat = new java.sql.Date(System.currentTimeMillis());
        Calendar cal = Calendar.getInstance();
        cal.setTime(dat);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        int year = cal.get(Calendar.YEAR);

        if ((date[0] > 4712)) {

            return true;

        } else if ((date[0] > year) && (date[1] > month) && (date[2] > day)) {

            return true;
        }
        return false;
    }

}
