package za.co.wirecard.channel.backoffice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import za.co.wirecard.channel.backoffice.entities.TransactionStateEntity;

import java.util.List;
import java.util.Optional;

public interface TransactionStateRepository extends JpaRepository<TransactionStateEntity, Long> {
    Optional<TransactionStateEntity> findByCode(String code);
    List<TransactionStateEntity> findByCodeIn(String[] t);
}
