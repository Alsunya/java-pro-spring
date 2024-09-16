package ru.flamexander.transfer.service.core.backend.services;


import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ru.flamexander.transfer.service.core.api.dtos.TransferDto;
import ru.flamexander.transfer.service.core.api.dtos.TransferResponseDto;
import ru.flamexander.transfer.service.core.backend.entities.Account;
import ru.flamexander.transfer.service.core.backend.entities.Transfer;
import ru.flamexander.transfer.service.core.backend.errors.AppLogicException;
import ru.flamexander.transfer.service.core.backend.repositories.AccountsRepository;
import ru.flamexander.transfer.service.core.backend.repositories.TransferRepository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TransferService {
    private final AccountsService accountsService;
    private final AccountsRepository accountsRepository;
    private final TransferRepository transferRepository;

    private static final Logger logger = LoggerFactory.getLogger(TransferService.class.getName());
    @Transactional
    public TransferResponseDto transfer(TransferDto transferDto) {
        Account sender = accountsService.getAccountById(1L, transferDto.getSenderAccountId())
                .orElseThrow(() -> new AppLogicException("TRANSFER_SOURCE_ACCOUNT_NOT_FOUND",
                        "Перевод невозможен, поскольку не существует счет отправителя"));
        Account recipient = accountsService.getAccountById(1L, transferDto.getRecipientAccountId())
                .orElseThrow(() -> new AppLogicException("TRANSFER_TARGET_ACCOUNT_NOT_FOUND",
                        "Перевод невозможен, поскольку не существует счет получателя"));

        BigDecimal amount = transferDto.getAmount();
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new AppLogicException("INVALID_TRANSFER_AMOUNT", "Сумма перевода должна быть положительной");
        }

        if (sender.getBalance().compareTo(amount) < 0) {
            throw new AppLogicException("INSUFFICIENT_FUNDS", "У отправителя на счете недостаточно средств");
        }

        sender.setBalance(sender.getBalance().subtract(amount));
        recipient.setBalance(recipient.getBalance().add(amount));
        accountsRepository.save(sender);
        accountsRepository.save(recipient);

        Transfer transfer = new Transfer();
        transfer.setSenderAccountId(sender.getId());
        transfer.setRecipientAccountId(recipient.getId());
        transfer.setAmount(amount);
        transfer.setStatus("COMPLETED");
        transfer.setCreatedAt(LocalDateTime.now());
        transfer.setUpdatedAt(LocalDateTime.now());
        transfer = transferRepository.save(transfer);

        logger.info("Transfer from account id = {} to account id = {} completed successfully. Amount = {}.",
                sender.getId(), recipient.getId(), amount);

        TransferResponseDto responseDto = new TransferResponseDto();
        responseDto.setId(transfer.getId());
        responseDto.setSenderAccountId(transfer.getSenderAccountId());
        responseDto.setRecipientAccountId(transfer.getRecipientAccountId());
        responseDto.setAmount(transfer.getAmount());
        responseDto.setStatus(transfer.getStatus());
        responseDto.setCreatedAt(transfer.getCreatedAt());
        responseDto.setUpdatedAt(transfer.getUpdatedAt());

        return responseDto;
    }

    public List<TransferResponseDto> getTransfersByClientId(Long clientId) {
        List<Account> accounts = accountsRepository.findAllByClientId(clientId);
        Set<Transfer> transfers = new HashSet<>();

        for (Account account : accounts) {
            transfers.addAll(transferRepository.findAllBySenderAccountId(account.getId()));
            transfers.addAll(transferRepository.findAllByRecipientAccountId(account.getId()));
        }

        return transfers.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    private TransferResponseDto convertToDto(Transfer transfer) {
        TransferResponseDto responseDto = new TransferResponseDto();
        responseDto.setId(transfer.getId());
        responseDto.setSenderAccountId(transfer.getSenderAccountId());
        responseDto.setRecipientAccountId(transfer.getRecipientAccountId());
        responseDto.setAmount(transfer.getAmount());
        responseDto.setStatus(transfer.getStatus());
        responseDto.setCreatedAt(transfer.getCreatedAt());
        responseDto.setUpdatedAt(transfer.getUpdatedAt());
        return responseDto;
    }
}
