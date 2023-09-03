package za.co.wirecard.channel.backoffice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import za.co.wirecard.channel.backoffice.entities.SecurityMethodEntity;

public interface SecurityMethodRepository extends JpaRepository<SecurityMethodEntity, Long> {
    SecurityMethodEntity findByCode(String code);
}
