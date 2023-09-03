package za.co.wirecard.channel.backoffice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import za.co.wirecard.channel.backoffice.entities.ZapperClientUpdateEntity;

import java.util.Optional;

public interface ZapperClientUpdateRepository extends JpaRepository<ZapperClientUpdateEntity, Long> {

    Optional<ZapperClientUpdateEntity> findByTransactionLegId(long transactionLegId);

}
