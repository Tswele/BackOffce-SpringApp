package za.co.wirecard.channel.backoffice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import za.co.wirecard.channel.backoffice.entities.ZapperClientUpdateEntity;
import za.co.wirecard.channel.backoffice.entities.ZapperProviderUpdateEntity;

import java.util.Optional;

public interface ZapperProviderUpdateRepository extends JpaRepository<ZapperProviderUpdateEntity, Long> {

    Optional<ZapperProviderUpdateEntity> findByTransactionLegId(long transactionLegId);

}
