package za.co.wirecard.channel.backoffice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import za.co.wirecard.channel.backoffice.entities.PaymentTypeEntity;

import java.util.Optional;

public interface PaymentTypeRepository extends JpaRepository<PaymentTypeEntity, Long> {
    PaymentTypeEntity findByCode(String code);
    PaymentTypeEntity findById(long id);
}