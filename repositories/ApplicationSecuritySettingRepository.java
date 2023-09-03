package za.co.wirecard.channel.backoffice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import za.co.wirecard.channel.backoffice.entities.ApplicationSecuritySettingEntity;

public interface ApplicationSecuritySettingRepository extends JpaRepository<ApplicationSecuritySettingEntity, Long> {
    ApplicationSecuritySettingEntity findByApplicationIdAndSecurityTypeId(long applicationId, long securityTypeId);
}
