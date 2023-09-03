package za.co.wirecard.channel.backoffice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import za.co.wirecard.channel.backoffice.entities.PlanetConfigurationEntity;

@Repository
public interface PlanetConfigurationRepository extends JpaRepository<PlanetConfigurationEntity, Long> {
    PlanetConfigurationEntity findByCardConfigurationId(long planetCardConfigurationEntityId);
}
