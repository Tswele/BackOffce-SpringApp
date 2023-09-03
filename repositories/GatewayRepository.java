package za.co.wirecard.channel.backoffice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import za.co.wirecard.channel.backoffice.entities.GatewayEntity;

public interface GatewayRepository extends JpaRepository<GatewayEntity, Long> {
    GatewayEntity findByCode(String code);
    GatewayEntity findById(long id);
}
