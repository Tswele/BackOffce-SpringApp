package za.co.wirecard.channel.backoffice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import za.co.wirecard.channel.backoffice.entities.BackOfficeUserEntity;

import java.util.List;
import java.util.Optional;

public interface BackOfficeUserRepository extends JpaRepository<BackOfficeUserEntity, Long> {
    // BackOfficeUserEntity findAllBy
    Optional<BackOfficeUserEntity> findByEmailIgnoreCase(String email);
    List<BackOfficeUserEntity> findAll();
    List<BackOfficeUserEntity> findAllByIsSalesPersonIsTrue();
    List<BackOfficeUserEntity> findAllByIsAccountManagerIsTrue();
    List<BackOfficeUserEntity> findAllByIsCreditControllerIsTrue();
    BackOfficeUserEntity findOneById(Long id);
    List<BackOfficeUserEntity> findAllByBackOfficeGroupId(Long id);
}
