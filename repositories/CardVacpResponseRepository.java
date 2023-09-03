package za.co.wirecard.channel.backoffice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import za.co.wirecard.channel.backoffice.entities.CardVacpResponseEntity;

public interface CardVacpResponseRepository extends JpaRepository<CardVacpResponseEntity, Long> {
    CardVacpResponseEntity findByTransactionLegId(long id);
}
