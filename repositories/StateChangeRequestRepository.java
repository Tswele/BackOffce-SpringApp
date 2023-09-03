package za.co.wirecard.channel.backoffice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import za.co.wirecard.channel.backoffice.entities.StateChangeRequestEntity;

public interface StateChangeRequestRepository extends JpaRepository<StateChangeRequestEntity, Long> {
}
