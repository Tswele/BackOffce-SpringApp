package za.co.wirecard.channel.backoffice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import za.co.wirecard.channel.backoffice.entities.MobicredOtpEntity;
import za.co.wirecard.channel.backoffice.entities.TransactionLegEntity;

public interface MobicredOtpRepository extends JpaRepository<MobicredOtpEntity, Long> {
    MobicredOtpEntity findMobicredOtpEntityByTransactionLegByTransactionLegId(TransactionLegEntity transactionLegEntity);
}
