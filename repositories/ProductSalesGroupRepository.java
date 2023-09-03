package za.co.wirecard.channel.backoffice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import za.co.wirecard.channel.backoffice.entities.ProductSalesGroupEntity;

import java.util.List;

public interface ProductSalesGroupRepository extends JpaRepository<ProductSalesGroupEntity, Long>, JpaSpecificationExecutor<ProductSalesGroupEntity> {
//    List<ProductSalesGroupEntity> findAll();
}
