package za.co.wirecard.channel.backoffice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import za.co.wirecard.channel.backoffice.entities.MasterPassTxEntity;
import za.co.wirecard.channel.backoffice.entities.TransactionLegEntity;

public interface MasterPassTxRepository extends JpaRepository<MasterPassTxEntity, Long> {
    MasterPassTxEntity findMasterPassTxEntityByTransactionLegByTransactionLegId(TransactionLegEntity transactionLegEntity);
}
