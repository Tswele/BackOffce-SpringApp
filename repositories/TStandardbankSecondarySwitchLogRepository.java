package za.co.wirecard.channel.backoffice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import za.co.wirecard.channel.backoffice.entities.TStandardbankSecondarySwitchLogEntity;

import java.util.List;
import java.util.Optional;

public interface TStandardbankSecondarySwitchLogRepository extends JpaRepository<TStandardbankSecondarySwitchLogEntity, Long>, JpaSpecificationExecutor<TStandardbankSecondarySwitchLogEntity> {
    Optional<List<TStandardbankSecondarySwitchLogEntity>> findAllByTransactionId(Long transactionId);
}
