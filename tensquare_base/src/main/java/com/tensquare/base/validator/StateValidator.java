package com.tensquare.base.validator;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import util.ValidatorUtil;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Service
public class StateValidator implements ConstraintValidator<State,String> {

    boolean required =false;
    @Override
    public void initialize(State constraintAnnotation) {
        required=constraintAnnotation.required();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        if (StringUtils.isNotBlank(value)&&(("1".equals(value)||"2".equals(value)))){
            return true;
        }else if (StringUtils.isBlank(value)&&!required){
            return  true;
        }
        return false;
    }
}
