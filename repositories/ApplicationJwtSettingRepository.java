package za.co.wirecard.channel.backoffice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import za.co.wirecard.channel.backoffice.entities.ApplicationJwtSettingEntity;

public interface ApplicationJwtSettingRepository extends JpaRepository<ApplicationJwtSettingEntity, Long> {
}
