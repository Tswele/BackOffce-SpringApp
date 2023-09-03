package za.co.wirecard.channel.backoffice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import za.co.wirecard.channel.backoffice.entities.MerchantEntity;
import za.co.wirecard.channel.backoffice.entities.MerchantOnboardingEntity;

import java.util.Optional;

public interface MerchantOnboardingRepository extends JpaRepository<MerchantOnboardingEntity, Long> {
    Optional<MerchantOnboardingEntity> findByMerchantId(long merchantId);
}
