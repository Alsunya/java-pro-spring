package ru.flamexander.transfer.service.core.api.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Schema(description = "нформация о счетах клиента")
public class AccountsPageDto {
    @Schema(description = "Список счетов клиента")
    private List<AccountDto> items;
}