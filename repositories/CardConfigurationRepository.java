package za.co.wirecard.channel.backoffice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import za.co.wirecard.channel.backoffice.entities.CardConfigStatusEntity;
import za.co.wirecard.channel.backoffice.entities.CardConfigurationEntity;
import za.co.wirecard.channel.backoffice.entities.CardFlowEntity;
import za.co.wirecard.channel.backoffice.entities.InterfaceEntity;

import java.util.List;

public interface CardConfigurationRepository extends JpaRepository<CardConfigurationEntity, Long> {
    List<CardConfigurationEntity> findAllByCardFlowByCardFlowIdAndInterfaceByInterfaceId(CardFlowEntity cardFlowEntity, InterfaceEntity interfaceEntity);
    List<CardConfigurationEntity> findAllByCardFlowByCardFlowId(CardFlowEntity cardFlowEntity);
    List<CardConfigurationEntity> findAllByInterfaceByInterfaceId(InterfaceEntity interfaceEntity);
    CardConfigurationEntity findByInterfaceByInterfaceId(InterfaceEntity interfaceEntity);
    CardConfigurationEntity findByInterfaceIdAndCardFlowId(long interfaceId, long cardFlowId);
    List<CardConfigurationEntity> findAllByCardConfigStatusByCardConfigStatusIdAndCardFlowByCardFlowIdAndInterfaceByInterfaceId(CardConfigStatusEntity cardConfigStatusEntity, CardFlowEntity cardFlowEntity, InterfaceEntity interfaceEntity);
}
