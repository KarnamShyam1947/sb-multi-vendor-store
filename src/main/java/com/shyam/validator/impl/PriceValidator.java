package com.shyam.validator.impl;

import com.shyam.validator.Price;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PriceValidator implements ConstraintValidator<Price, Double> {

    double min;

    @Override
    public void initialize(Price price) {
        min = price.min();    
    }

    @Override
    public boolean isValid(Double value, ConstraintValidatorContext context) {
        if(value <= min)
            return false;

        return true;
    }

    
}
