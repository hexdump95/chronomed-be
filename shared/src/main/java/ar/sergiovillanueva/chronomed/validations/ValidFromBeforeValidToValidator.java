package ar.sergiovillanueva.chronomed.validations;

import ar.sergiovillanueva.chronomed.dto.InstantRangeRequest;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ValidFromBeforeValidToValidator implements ConstraintValidator<ValidFromBeforeValidTo, InstantRangeRequest> {
    @Override
    public boolean isValid(InstantRangeRequest value, ConstraintValidatorContext context) {
        if (value.getValidFrom() == null || value.getValidTo() == null) {
            return true;
        }
        return !value.getValidFrom().isAfter(value.getValidTo());
    }
}
