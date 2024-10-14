package com.mjc.school.service.validator;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.hibernate.validator.messageinterpolation.ParameterMessageInterpolator;
import org.springframework.stereotype.Component;

@Component
public class ValidatorInstance {
    private static final Validator VALIDATOR;

    static {
        try (var factory = Validation.byDefaultProvider()
                .configure()
                .messageInterpolator(new ParameterMessageInterpolator())
                .buildValidatorFactory()) {
            VALIDATOR = factory.getValidator();
        }
    }

    public Validator getVALIDATOR() {
        return VALIDATOR;
    }
}
