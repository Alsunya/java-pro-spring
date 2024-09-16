package ru.flamexander.transfer.service.core.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.flamexander.transfer.service.core.backend.entities.Transfer;
import java.util.List;

@Repository
public interface TransferRepository extends JpaRepository<Transfer, Long> {
    List<Transfer> findAllBySenderAccountId(Long senderAccountId);
    List<Transfer> findAllByRecipientAccountId(Long recipientAccountId);
}
