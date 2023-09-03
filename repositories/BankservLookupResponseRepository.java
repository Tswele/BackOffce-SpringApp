package za.co.wirecard.channel.backoffice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import za.co.wirecard.channel.backoffice.entities.BankservLookupResponseEntity;
import za.co.wirecard.channel.backoffice.entities.TransactionLegEntity;

public interface BankservLookupResponseRepository extends JpaRepository<BankservLookupResponseEntity, Long> {
    BankservLookupResponseEntity findBankservLookupResponseEntityByTransactionLegByTransactionLegId(TransactionLegEntity transactionLegEntity0);

}
