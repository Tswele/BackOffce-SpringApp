package za.co.wirecard.channel.backoffice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import za.co.wirecard.channel.backoffice.entities.VacpConfigurationEntity;

public interface VacpConfigurationRepository extends JpaRepository<VacpConfigurationEntity, Long> {
    VacpConfigurationEntity findByCardConfigurationId(long id);
}
