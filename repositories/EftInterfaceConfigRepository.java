package za.co.wirecard.channel.backoffice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import za.co.wirecard.channel.backoffice.entities.EftInterfaceConfigEntity;

public interface EftInterfaceConfigRepository extends JpaRepository<EftInterfaceConfigEntity, Long> {
    EftInterfaceConfigEntity findByInterfaceId(long id);
}
