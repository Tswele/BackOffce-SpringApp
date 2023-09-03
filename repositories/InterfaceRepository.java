package za.co.wirecard.channel.backoffice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import za.co.wirecard.channel.backoffice.entities.InterfaceEntity;
import za.co.wirecard.channel.backoffice.entities.MerchantEntity;
import za.co.wirecard.channel.backoffice.entities.PaymentTypeEntity;

import java.util.List;

public interface InterfaceRepository extends JpaRepository<InterfaceEntity, Long> {
    List<InterfaceEntity> findAllByMerchantId(long merchantId);
    InterfaceEntity findDistinctFirstByMerchantByMerchantIdAndPaymentTypeByPaymentTypeId(MerchantEntity merchantEntity, PaymentTypeEntity paymentTypeEntity);
}
