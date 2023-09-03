package za.co.wirecard.channel.backoffice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import za.co.wirecard.channel.backoffice.entities.StatusEntity;

public interface StatusRepository extends JpaRepository<StatusEntity, Long> {
    StatusEntity findByCode(String code);
}
