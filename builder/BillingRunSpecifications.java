package za.co.wirecard.channel.backoffice.builder;

import org.springframework.data.jpa.domain.Specification;
import za.co.wirecard.channel.backoffice.entities.BillingRunEntity;

public class BillingRunSpecifications {
    public static Specification<BillingRunEntity> stringSearchNameLike(String stringSearch) {
        return (root, query, builder) -> builder.like(builder.lower(root.get("name")), "%" + stringSearch + "%");
    }
}
