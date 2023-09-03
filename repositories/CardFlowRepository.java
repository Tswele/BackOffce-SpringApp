package za.co.wirecard.channel.backoffice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import za.co.wirecard.channel.backoffice.entities.CardFlowEntity;

public interface CardFlowRepository extends JpaRepository<CardFlowEntity, Long>, JpaSpecificationExecutor<CardFlowEntity> {
    CardFlowEntity findByCode(String code);
}
