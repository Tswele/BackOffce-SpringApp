package za.co.wirecard.channel.backoffice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import za.co.wirecard.channel.backoffice.entities.CardPostillionResponseEntity;
import za.co.wirecard.channel.backoffice.entities.TransactionLegEntity;

public interface CardPostillionResponseRepository extends JpaRepository<CardPostillionResponseEntity, Long> {
    CardPostillionResponseEntity findCardPostillionResponseEntityByTransactionLegByTransactionLegId(TransactionLegEntity transactionLegEntity);
}
