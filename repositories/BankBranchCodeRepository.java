package za.co.wirecard.channel.backoffice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import za.co.wirecard.channel.backoffice.entities.BankBranchCodeEntity;
import za.co.wirecard.channel.backoffice.entities.BankEntity;

import java.util.List;
import java.util.Optional;

public interface BankBranchCodeRepository extends JpaRepository<BankBranchCodeEntity, Long> {
    Optional<BankBranchCodeEntity> findById(long bankId);
    Optional<List<BankBranchCodeEntity>> findAllByBankByBankId(BankEntity bankEntity);
}
