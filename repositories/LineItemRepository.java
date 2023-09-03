package za.co.wirecard.channel.backoffice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import za.co.wirecard.channel.backoffice.entities.InvoiceEntity;
import za.co.wirecard.channel.backoffice.entities.LineItemEntity;

import java.util.List;

public interface LineItemRepository extends JpaRepository<LineItemEntity, Long>, JpaSpecificationExecutor<LineItemEntity> {
    List<LineItemEntity> findLineItemEntitiesByInvoiceByInvoiceId(InvoiceEntity invoiceEntity);
}
