package za.co.wirecard.channel.backoffice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import za.co.wirecard.channel.backoffice.entities.SecurityTypeEntity;

public interface SecurityTypeRepository extends JpaRepository<SecurityTypeEntity, Long> {
    SecurityTypeEntity findByCode(String code);
}
