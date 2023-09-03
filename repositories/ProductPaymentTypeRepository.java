package za.co.wirecard.channel.backoffice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import za.co.wirecard.channel.backoffice.entities.ProductEntity;
import za.co.wirecard.channel.backoffice.entities.ProductPaymentTypeEntity;

import java.util.List;

public interface ProductPaymentTypeRepository extends JpaRepository<ProductPaymentTypeEntity, Long>, JpaSpecificationExecutor<ProductPaymentTypeEntity> {
    List<ProductPaymentTypeEntity> findAllByProductEntityByProductId(ProductEntity productEntity);
}
