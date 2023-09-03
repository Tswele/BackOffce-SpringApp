package za.co.wirecard.channel.backoffice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import za.co.wirecard.channel.backoffice.entities.MerchantProductEntity;
import za.co.wirecard.channel.backoffice.entities.ProductEntity;

import java.util.List;
import java.util.Optional;

public interface MerchantProductRepository extends JpaRepository<MerchantProductEntity, Long>, JpaSpecificationExecutor<MerchantProductEntity> {

    List<MerchantProductEntity> findByMerchantId(long id);
    MerchantProductEntity findByMerchantIdAndProductByProductId(long merchantId, ProductEntity productEntity);
    MerchantProductEntity findByProductByProductIdAndMerchantId(ProductEntity productEntity, long merchantId);

}
