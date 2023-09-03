package za.co.wirecard.channel.backoffice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import za.co.wirecard.channel.backoffice.entities.TransactionActionEntity;

public interface TransactionActionRepository extends JpaRepository<TransactionActionEntity, Long> {
    TransactionActionEntity findOneById(Long id);
}
