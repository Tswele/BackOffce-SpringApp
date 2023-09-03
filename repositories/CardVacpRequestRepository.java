package za.co.wirecard.channel.backoffice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import za.co.wirecard.channel.backoffice.entities.CardVacpRequestEntity;

public interface CardVacpRequestRepository extends JpaRepository<CardVacpRequestEntity, Long> {
    CardVacpRequestEntity findByTransactionLegId(long id);
}
