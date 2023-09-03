package za.co.wirecard.channel.backoffice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import za.co.wirecard.channel.backoffice.entities.ThreeDsAuthEntity;
import za.co.wirecard.channel.backoffice.entities.ThreeDsTransactionEntity;

public interface ThreeDsAuthRepository extends JpaRepository<ThreeDsAuthEntity, Long> {
    ThreeDsAuthEntity findByThreeDsTransactionByThreeDsTransactionId(ThreeDsTransactionEntity threeDsTransactionEntity);
}