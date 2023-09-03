package za.co.wirecard.channel.backoffice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import za.co.wirecard.channel.backoffice.entities.BankservLookupRequestEntity;
import za.co.wirecard.channel.backoffice.entities.TransactionLegEntity;

public interface BankservLookupRequestRepository extends JpaRepository<BankservLookupRequestEntity, Long> {
    BankservLookupRequestEntity findBankservLookupRequestEntityByTransactionLegByTransactionLegId(TransactionLegEntity transactionLegEntity);
}
