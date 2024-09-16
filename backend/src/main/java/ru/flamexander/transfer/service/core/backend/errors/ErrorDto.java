package ru.flamexander.transfer.service.core.backend.errors;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@Data
public class ErrorDto {
    private String code;
    private String message;
    private LocalDateTime date;

    public ErrorDto(String code, String message) {
        this.code = code;
        this.message = message;
        this.date = LocalDateTime.now();
    }
}
