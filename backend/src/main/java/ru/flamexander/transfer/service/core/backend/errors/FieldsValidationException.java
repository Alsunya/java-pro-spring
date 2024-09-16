package ru.flamexander.transfer.service.core.backend.errors;

import java.util.List;

public class FieldsValidationException extends RuntimeException {
    private List<FieldValidationError> fields;

    public List<FieldValidationError> getFields() {
        return fields;
    }

    public FieldsValidationException(List<FieldValidationError> fields) {
        this.fields = fields;
    }
}
