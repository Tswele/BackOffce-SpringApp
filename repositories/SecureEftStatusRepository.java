package za.co.wirecard.channel.backoffice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import za.co.wirecard.channel.backoffice.entities.SecureEftStatusEntity;
import za.co.wirecard.channel.backoffice.entities.TransactionLegEntity;

public interface SecureEftStatusRepository extends JpaRepository<SecureEftStatusEntity, Long> {
    SecureEftStatusEntity findSecureEftStatusEntityByTransactionLegByTransactionLegId(TransactionLegEntity transactionLegEntity);
}
