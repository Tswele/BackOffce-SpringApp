package za.co.wirecard.channel.backoffice.builder;

import org.springframework.data.jpa.domain.Specification;
import za.co.wirecard.channel.backoffice.entities.InvoiceEntity;

public class InvoiceSpecifications {
    public static Specification<InvoiceEntity> stringSearchInvoiceNumberLike(String stringSearch) {
        return (root, query, builder) -> builder.like(builder.lower(root.get("invoiceNumber")), "%" + stringSearch + "%");
    }

    public static Specification<InvoiceEntity> billingRunIdEquals(Long billingRunId) {
        return (root, query, builder) -> builder.equal(root.join("billingRunByBillingRunId").get("id"), billingRunId);
    }
}
