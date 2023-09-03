package za.co.wirecard.channel.backoffice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import za.co.wirecard.channel.backoffice.entities.RateStructureEntity;

import java.util.ArrayList;
import java.util.List;

public interface RateStructureRepository extends JpaRepository<RateStructureEntity, Long>, JpaSpecificationExecutor<RateStructureEntity> {
}
