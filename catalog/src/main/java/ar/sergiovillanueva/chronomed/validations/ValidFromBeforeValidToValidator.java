package ar.sergiovillanueva.chronomed.validations;

import ar.sergiovillanueva.chronomed.dto.SpecialtyPriceRequest;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ValidFromBeforeValidToValidator implements ConstraintValidator<ValidFromBeforeValidTo, SpecialtyPriceRequest> {
    @Override
    public boolean isValid(SpecialtyPriceRequest value, ConstraintValidatorContext context) {
        if (value.getValidFrom() == null || value.getValidTo() == null) {
            return true;
        }
        return !value.getValidFrom().isAfter(value.getValidTo());
    }
}
