package za.co.wirecard.channel.backoffice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import za.co.wirecard.channel.backoffice.entities.ZapperInterfaceConfigEntity;

public interface ZapperInterfaceConfigRepository extends JpaRepository<ZapperInterfaceConfigEntity, Long> {
    ZapperInterfaceConfigEntity findByInterfaceId(long id);
}
