package za.co.wirecard.channel.backoffice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import za.co.wirecard.channel.backoffice.entities.MerchantAddressEntity;

import java.util.Optional;

@Repository
public interface MerchantAddressRepository extends JpaRepository<MerchantAddressEntity, Long> {
    Optional<MerchantAddressEntity> findByMerchantIdAndPostalAddressIsTrue(long merchantId);
    Optional<MerchantAddressEntity> findByMerchantIdAndPostalAddressIsFalse(long merchantId);
}
