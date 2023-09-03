package za.co.wirecard.channel.backoffice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import za.co.wirecard.channel.backoffice.entities.MerchantTypeEntity;

public interface MerchantTypeRepository extends JpaRepository<MerchantTypeEntity, Long> {
    MerchantTypeEntity findByCode(String code);
    MerchantTypeEntity findById(long id);
}
