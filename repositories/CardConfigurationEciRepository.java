package za.co.wirecard.channel.backoffice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import za.co.wirecard.channel.backoffice.entities.CardConfigurationEciEntity;
import za.co.wirecard.channel.backoffice.entities.CardConfigurationEntity;
import za.co.wirecard.channel.backoffice.entities.EciEntity;

import java.util.List;

public interface CardConfigurationEciRepository extends JpaRepository<CardConfigurationEciEntity, Long> {
    List<CardConfigurationEciEntity> findAllByCardConfigurationId(long id);
    CardConfigurationEciEntity findByCardConfigurationIdAndEciByEciId(long id, EciEntity eciEntity);
    void deleteByCardConfigurationIdAndEciByEciId(long id, EciEntity eciEntity);
}
