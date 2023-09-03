package za.co.wirecard.channel.backoffice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import za.co.wirecard.channel.backoffice.entities.IveriConfigurationEntity;

public interface IveriConfigurationRepository extends JpaRepository<IveriConfigurationEntity, Long> {
    IveriConfigurationEntity findByCardConfigurationId(long id);
}
