package za.co.wirecard.channel.backoffice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import za.co.wirecard.channel.backoffice.entities.AuthUserMerchantEntity;

import java.util.Optional;

public interface AuthUserMerchantRepository extends JpaRepository<AuthUserMerchantEntity, Long> {
    Optional<AuthUserMerchantEntity> findByMerchantIdAndPrimaryContactIsTrue(long merchantId);
}
