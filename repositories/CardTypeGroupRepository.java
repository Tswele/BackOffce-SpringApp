package za.co.wirecard.channel.backoffice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import za.co.wirecard.channel.backoffice.entities.CardConfigurationEntity;
import za.co.wirecard.channel.backoffice.entities.CardTypeEntity;
import za.co.wirecard.channel.backoffice.entities.CardTypeGroupEntity;

import java.util.List;

public interface CardTypeGroupRepository extends JpaRepository<CardTypeGroupEntity, Long> {
    CardTypeGroupEntity findByCardConfigurationByCardConfigurationId(CardConfigurationEntity cardConfigurationEntity);
    List<CardTypeGroupEntity> findAllByCardConfigurationId(long id);
    List<CardTypeGroupEntity> findAllByCardConfigurationByCardConfigurationIdIn(List<CardConfigurationEntity> id);
    CardTypeGroupEntity findByCardConfigurationIdAndCardTypeEntityByCardTypeId(long id, CardTypeEntity cardTypeEntity);
}
