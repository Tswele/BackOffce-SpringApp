package za.co.wirecard.channel.backoffice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import za.co.wirecard.channel.backoffice.entities.BillingFrequencyEntity;

public interface BillingFrequencyRepository extends JpaRepository<BillingFrequencyEntity, Long> {
    BillingFrequencyEntity findOneById(Long id);
    BillingFrequencyEntity findByCode(String code);
}
