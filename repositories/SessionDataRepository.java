package za.co.wirecard.channel.backoffice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import za.co.wirecard.channel.backoffice.entities.SessionDataEntity;
import za.co.wirecard.channel.backoffice.entities.SessionEntity;

import java.util.List;

public interface SessionDataRepository extends JpaRepository<SessionDataEntity, Long>, JpaSpecificationExecutor<SessionDataEntity> {
    List<SessionDataEntity> findAllBySessionBySessionId(SessionEntity sessionEntity);
}
