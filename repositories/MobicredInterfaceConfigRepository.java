package za.co.wirecard.channel.backoffice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import za.co.wirecard.channel.backoffice.entities.MobicredInterfaceConfigEntity;

public interface MobicredInterfaceConfigRepository extends JpaRepository<MobicredInterfaceConfigEntity, Long> {
    MobicredInterfaceConfigEntity findByInterfaceId(long id);
}
