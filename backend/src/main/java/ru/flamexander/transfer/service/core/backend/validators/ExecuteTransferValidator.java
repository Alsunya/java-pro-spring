package ru.flamexander.transfer.service.core.backend.validators;

import org.springframework.stereotype.Component;
import ru.flamexander.transfer.service.core.api.dtos.ExecuteTransferDtoRequest;
import ru.flamexander.transfer.service.core.backend.errors.FieldValidationError;
import ru.flamexander.transfer.service.core.backend.errors.FieldsValidationException;

import java.util.ArrayList;
import java.util.List;

@Component
public class ExecuteTransferValidator {
    public void validate(ExecuteTransferDtoRequest request) {
        List<FieldValidationError> errorFields = new ArrayList<>();



        if (!errorFields.isEmpty()) {
            throw new FieldsValidationException(errorFields);
        }
    }
}
