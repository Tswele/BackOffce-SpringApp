package za.co.wirecard.channel.backoffice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import za.co.wirecard.channel.backoffice.entities.BackOfficeUserEntity;
import za.co.wirecard.channel.backoffice.entities.BackOfficeUserLoginEntity;

public interface BackOfficeUserLoginRepository extends JpaRepository<BackOfficeUserLoginEntity, Long> {
    void deleteAllByIpAddressAndBackOfficeUserByBackOfficeUserId(String ipAddress, BackOfficeUserEntity backOfficeUserEntity);
    BackOfficeUserLoginEntity findByIpAddressAndBackOfficeUserByBackOfficeUserId(String ipAddress, BackOfficeUserEntity backOfficeUserEntity);
}
