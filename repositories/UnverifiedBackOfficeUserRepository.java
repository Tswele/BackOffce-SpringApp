package za.co.wirecard.channel.backoffice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import za.co.wirecard.channel.backoffice.entities.BackOfficeUserEntity;
import za.co.wirecard.channel.backoffice.entities.UnverifiedBackOfficeUserEntity;

import java.util.Optional;

public interface UnverifiedBackOfficeUserRepository extends JpaRepository<UnverifiedBackOfficeUserEntity, Long> {
    UnverifiedBackOfficeUserEntity findByBackOfficeUserByUserId(BackOfficeUserEntity backOfficeUserEntity);
    Optional<UnverifiedBackOfficeUserEntity> findByUuid(String uuid);
    void deleteAllByBackOfficeUserByUserId(BackOfficeUserEntity backOfficeUserEntities);
}
