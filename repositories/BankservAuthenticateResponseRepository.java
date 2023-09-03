package za.co.wirecard.channel.backoffice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import za.co.wirecard.channel.backoffice.entities.BankservAuthenticateResponseEntity;
import za.co.wirecard.channel.backoffice.entities.TransactionLegEntity;

public interface BankservAuthenticateResponseRepository extends JpaRepository<BankservAuthenticateResponseEntity, Long> {
    BankservAuthenticateResponseEntity findBankservAuthenticateResponseEntityByTransactionLegByTransactionLegId(TransactionLegEntity transactionLegEntity);
}
