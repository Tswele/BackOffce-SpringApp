package za.co.wirecard.channel.backoffice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import za.co.wirecard.channel.backoffice.entities.InvoiceEntity;
import za.co.wirecard.channel.backoffice.entities.RateStructureHistoryEntity;

import java.util.List;

public interface RateStructureHistoryRepository extends JpaRepository<RateStructureHistoryEntity, Long> {
    List<RateStructureHistoryEntity> findRateStructureHistoryEntitiesByInvoiceByInvoiceId(InvoiceEntity invoiceEntity);
}
