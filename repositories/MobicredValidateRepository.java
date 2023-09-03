package za.co.wirecard.channel.backoffice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import za.co.wirecard.channel.backoffice.entities.MobicredValidateEntity;
import za.co.wirecard.channel.backoffice.entities.TransactionLegEntity;

public interface MobicredValidateRepository extends JpaRepository<MobicredValidateEntity, Long> {
    MobicredValidateEntity findMobicredValidateEntityByTransactionLegByTransactionLegId(TransactionLegEntity transactionLegEntity);
}
