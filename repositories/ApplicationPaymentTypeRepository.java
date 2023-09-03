package za.co.wirecard.channel.backoffice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import za.co.wirecard.channel.backoffice.entities.ApplicationEntity;
import za.co.wirecard.channel.backoffice.entities.ApplicationPaymentTypeEntity;
import za.co.wirecard.channel.backoffice.entities.InterfaceEntity;
import za.co.wirecard.channel.backoffice.entities.PaymentTypeEntity;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

public interface ApplicationPaymentTypeRepository extends JpaRepository<ApplicationPaymentTypeEntity, Long> {
    List<ApplicationPaymentTypeEntity> findByApplicationId(long applicationId);
    List<ApplicationPaymentTypeEntity> findByApplicationByApplicationIdInAndPaymentTypeByPaymentTypeId(List<ApplicationEntity> applicationIds, PaymentTypeEntity paymentTypeEntity);
    List<ApplicationPaymentTypeEntity> findByApplicationByApplicationIdIn(List<ApplicationEntity> applicationIds);
    Optional<ApplicationPaymentTypeEntity> findDistinctByInterfaceByInterfaceIdAndPaymentTypeByPaymentTypeId(InterfaceEntity interfaceEntity, PaymentTypeEntity paymentTypeEntity);
    ApplicationPaymentTypeEntity findFirstByInterfaceByInterfaceIdAndPaymentTypeByPaymentTypeId(InterfaceEntity interfaceEntity, PaymentTypeEntity paymentTypeEntity);
    Optional<ApplicationPaymentTypeEntity> findDistinctFirstByApplicationByApplicationIdAndPaymentTypeByPaymentTypeId(ApplicationEntity applicationEntity, PaymentTypeEntity paymentTypeEntity);
    ApplicationPaymentTypeEntity findByApplicationIdAndPaymentTypeId(long applicationId, long paymentTypeId);
}
