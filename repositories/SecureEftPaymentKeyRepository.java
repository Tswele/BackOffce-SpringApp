package za.co.wirecard.channel.backoffice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import za.co.wirecard.channel.backoffice.entities.SecureEftPaymentKeyEntity;
import za.co.wirecard.channel.backoffice.entities.TransactionLegEntity;

public interface SecureEftPaymentKeyRepository extends JpaRepository<SecureEftPaymentKeyEntity, Long> {
    SecureEftPaymentKeyEntity findSecureEftPaymentKeyEntityByTransactionLegByTransactionLegId(TransactionLegEntity transactionLegEntity);
}
