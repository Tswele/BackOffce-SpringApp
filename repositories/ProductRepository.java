package za.co.wirecard.channel.backoffice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import za.co.wirecard.channel.backoffice.entities.ProductEntity;

import java.util.List;

public interface ProductRepository extends JpaRepository<ProductEntity, Long>, JpaSpecificationExecutor<ProductEntity> {
//    List<ProductEntity> findAll();
    ProductEntity findByProductCode(String code);
    List<ProductEntity> findAllByActive(Boolean active);
    ProductEntity findById (long id);
}
