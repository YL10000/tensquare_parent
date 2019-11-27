package com.tensquare.base.validator;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.validation.ValidationUtils;
import util.ValidatorUtil;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Service
public class IsMobileValidator implements ConstraintValidator<IsMobile,String> {

    private boolean required=false;
    
    @Override
    public void initialize(IsMobile constraintAnnotation) {
        required=constraintAnnotation.required();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        if (required||StringUtils.isNotBlank(value)){
            return ValidatorUtil.isMobile(value);
        }
        return false;
    }
}
