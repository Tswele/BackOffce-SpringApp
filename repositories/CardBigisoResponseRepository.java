package za.co.wirecard.channel.backoffice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import za.co.wirecard.channel.backoffice.entities.CardBigisoResponseEntity;
import za.co.wirecard.channel.backoffice.entities.TransactionLegEntity;

public interface CardBigisoResponseRepository extends JpaRepository<CardBigisoResponseEntity, Long> {
    CardBigisoResponseEntity findCardBigisoResponseEntityByTransactionLegByTransactionLegId(TransactionLegEntity transactionLegEntity);
}
