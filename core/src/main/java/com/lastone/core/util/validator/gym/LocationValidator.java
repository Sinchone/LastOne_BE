package com.lastone.core.util.validator.gym;

import com.lastone.core.util.validator.member.WorkoutDay;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class LocationValidator implements ConstraintValidator<Location, String> {

    private static final int CITY_NUMBER = 0;
    private static final int DISTRICT_NUMBER = 1;

    private static final String CITY = "시";

    private static final String DISTRICT = "구";


    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return false;
        }
        String[] locations = value.split(" ");
        if (locations.length != 2) {
            return false;
        }
        String city = locations[CITY_NUMBER];
        String district = locations[DISTRICT_NUMBER];

        if (!CITY.equals(city.substring(city.length() - 1))) {
            return false;
        }
        if (!DISTRICT.equals(district.substring(district.length()-1))) {
            return false;
        }
        return true;
    }
}
