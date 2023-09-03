package za.co.wirecard.channel.backoffice.builder;

import org.springframework.data.jpa.domain.Specification;
import za.co.wirecard.channel.backoffice.entities.LineItemTransactionEntity;

public class LineItemTransactionSpecifications {
    public static Specification<LineItemTransactionEntity> merchantReferenceEquals(String merchantReference) {
        return (root, query, builder) -> builder.like(root.join("transactionByTransactionId").get("merchantReference"), "%" + merchantReference + "%");
    }

    public static Specification<LineItemTransactionEntity> lineItemIdEquals(Long lineItemId) {
        return (root, query, builder) -> builder.equal(root.get("lineItemId"), lineItemId);
    }

    public static Specification<LineItemTransactionEntity> subLineItemIdEquals(Long subLineItemId) {
        return (root, query, builder) -> builder.equal(root.get("subLineItemId"), subLineItemId);
    }
}
