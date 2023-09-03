package za.co.wirecard.channel.backoffice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import za.co.wirecard.channel.backoffice.entities.BankservAuthenticateRequestEntity;
import za.co.wirecard.channel.backoffice.entities.TransactionLegEntity;

public interface BankservAuthenticateRequestRepository extends JpaRepository<BankservAuthenticateRequestEntity, Long> {
    BankservAuthenticateRequestEntity findBankservAuthenticateRequestEntityByTransactionLegByTransactionLegId(TransactionLegEntity transactionLegEntity);
}
