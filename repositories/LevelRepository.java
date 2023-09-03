package za.co.wirecard.channel.backoffice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import za.co.wirecard.channel.backoffice.entities.LevelEntity;
import za.co.wirecard.channel.backoffice.entities.StatusEntity;

public interface LevelRepository extends JpaRepository<LevelEntity,Long> {
    LevelEntity findByCode(String code);
}
