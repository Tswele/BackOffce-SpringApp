package za.co.wirecard.channel.backoffice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import za.co.wirecard.channel.backoffice.entities.CardConfigurationTokenizationMethodEntity;
import za.co.wirecard.channel.backoffice.entities.TokenizationMethodEntity;

import java.util.List;

public interface CardConfigurationTokenizationMethodRepository extends JpaRepository<CardConfigurationTokenizationMethodEntity, Long>, JpaSpecificationExecutor<CardConfigurationTokenizationMethodEntity> {
    List<CardConfigurationTokenizationMethodEntity> findAllByCardConfigurationId(long id);
    CardConfigurationTokenizationMethodEntity findByCardConfigurationIdAndTokenizationMethodByTokenizationMethodId(long id, TokenizationMethodEntity tokenizationMethodEntity);
}
