package za.co.wirecard.channel.backoffice.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import za.co.wirecard.channel.backoffice.entities.SessionEntity;

@Repository
public interface SessionRepository extends JpaRepository<SessionEntity, Long>, JpaSpecificationExecutor<SessionEntity> {
    Page<SessionEntity> findAll(Specification specification, Pageable pageable);
    SessionEntity findBySessionToken(String sessionToken);
}
