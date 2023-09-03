package za.co.wirecard.channel.backoffice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import za.co.wirecard.channel.backoffice.entities.InvoiceTypeEntity;

public interface InvoiceTypeRepository extends JpaRepository<InvoiceTypeEntity, Long> {
    InvoiceTypeEntity findInvoiceTypeEntityByCode(String code);
}
