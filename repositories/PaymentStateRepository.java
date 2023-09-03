package za.co.wirecard.channel.backoffice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import za.co.wirecard.channel.backoffice.entities.PaymentStateEntity;

import java.util.List;

public interface PaymentStateRepository extends JpaRepository<PaymentStateEntity, Long> {

    List<PaymentStateEntity> findAllByPaymentTypeId(long paymentTypeId);
    void deleteAllByPaymentTypeId(long paymentTypeId);

}
