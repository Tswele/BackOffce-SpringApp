package za.co.wirecard.channel.backoffice.builder;

import org.springframework.data.jpa.domain.Specification;
import za.co.wirecard.channel.backoffice.entities.LineItemEntity;

public class LineItemSpecifications {
    public static Specification<LineItemEntity> stringSearchLineItemNameLike(String stringSearch) {
        return (root, query, builder) -> builder.like(builder.lower(root.get("name")), "%" + stringSearch + "%");
    }

    public static Specification<LineItemEntity> invoiceIdEquals(Long invoiceId) {
        return (root, query, builder) -> builder.equal(root.join("invoiceByInvoiceId").get("id"), invoiceId);
    }
}
