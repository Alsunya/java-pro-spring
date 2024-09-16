package ru.flamexander.transfer.service.core.backend.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.flamexander.transfer.service.core.api.dtos.TransferDto;
import ru.flamexander.transfer.service.core.api.dtos.TransferResponseDto;
import ru.flamexander.transfer.service.core.backend.services.TransferService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/transfers")
public class TransfersController {
    private final TransferService transferService;

    @PostMapping("/execute")
    public ResponseEntity<TransferResponseDto> executeTransfer(@RequestBody TransferDto transferDto) {
        TransferResponseDto response = transferService.transfer(transferDto);
        return ResponseEntity.status(201).body(response);
    }

    @GetMapping
    public List<TransferResponseDto> getTransfers(@RequestHeader Long clientId) {
        return transferService.getTransfersByClientId(clientId);
    }
}
