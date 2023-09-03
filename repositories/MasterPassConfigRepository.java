package za.co.wirecard.channel.backoffice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import za.co.wirecard.channel.backoffice.entities.MasterPassConfigEntity;

public interface MasterPassConfigRepository extends JpaRepository<MasterPassConfigEntity, Long> {
    MasterPassConfigEntity findByInterfaceId(long id);
}
