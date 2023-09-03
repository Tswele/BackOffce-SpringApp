package za.co.wirecard.channel.backoffice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import za.co.wirecard.channel.backoffice.entities.CardTransactionEntity;
import za.co.wirecard.channel.backoffice.entities.TransactionEntity;
import za.co.wirecard.channel.backoffice.models.CardType;

import java.util.List;

public interface CardTransactionRepository extends JpaRepository<CardTransactionEntity, Long> {
    CardTransactionEntity findByTransactionByTransactionId(TransactionEntity transactionEntity);

    @Query("SELECT c.issuingBank FROM CardTransactionEntity c group by c.issuingBank")
    List<String> findDistinctIssuingBanks();

   @Query("SELECT x.cardName FROM CardTypeEntity x group by x.cardName")
    List<CardType> findDistinctCardType();
}
