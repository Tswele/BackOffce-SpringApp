package za.co.wirecard.channel.backoffice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import za.co.wirecard.channel.backoffice.entities.MobicredPurchseCreateEntity;
import za.co.wirecard.channel.backoffice.entities.TransactionLegEntity;

import java.util.List;

public interface MobicredPurchseCreateRepository extends JpaRepository<MobicredPurchseCreateEntity, Long> {
    List<MobicredPurchseCreateEntity> findMobicredPurchaseCreateEntitiesByTransactionLegByTransactionLegId(TransactionLegEntity transactionLegEntity);
}
