package za.co.wirecard.channel.backoffice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import za.co.wirecard.channel.backoffice.entities.ApplicationEntity;
import za.co.wirecard.channel.backoffice.entities.MerchantProductApplicationEntity;
import za.co.wirecard.channel.backoffice.entities.MerchantProductEntity;

import java.util.List;

public interface MerchantProductApplicationRepository extends JpaRepository<MerchantProductApplicationEntity, Long> {
    List<MerchantProductApplicationEntity> findAllByApplicationByApplicationId(ApplicationEntity applicationEntity);
    MerchantProductApplicationEntity findByApplicationByApplicationIdAndMerchantProductByMerchantProductId(ApplicationEntity applicationEntity, MerchantProductEntity merchantProductEntity);
}
