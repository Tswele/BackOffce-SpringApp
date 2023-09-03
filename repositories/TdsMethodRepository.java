package za.co.wirecard.channel.backoffice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import za.co.wirecard.channel.backoffice.entities.TdsMethodEntity;

public interface TdsMethodRepository extends JpaRepository<TdsMethodEntity, Long>, JpaSpecificationExecutor<TdsMethodEntity> {
    TdsMethodEntity findByCode(String code);
    TdsMethodEntity findById(long id);
}
