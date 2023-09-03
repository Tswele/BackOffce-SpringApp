package za.co.wirecard.channel.backoffice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import za.co.wirecard.channel.backoffice.entities.CardinalConfigurationEntity;

@Repository
public interface CardinalConfigurationRepository extends JpaRepository<CardinalConfigurationEntity, Long> {
    CardinalConfigurationEntity findByCardConfigurationId(long cardinalConfigurationEntityId);
}
