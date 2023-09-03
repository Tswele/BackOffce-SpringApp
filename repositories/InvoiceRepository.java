package za.co.wirecard.channel.backoffice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import za.co.wirecard.channel.backoffice.entities.BillingRunEntity;
import za.co.wirecard.channel.backoffice.entities.InvoiceEntity;

import java.util.List;

public interface InvoiceRepository extends JpaRepository<InvoiceEntity, Long>, JpaSpecificationExecutor<InvoiceEntity> {
    List<InvoiceEntity> findInvoiceEntitiesByBillingRunByBillingRunId(BillingRunEntity billingRunEntity);
}
