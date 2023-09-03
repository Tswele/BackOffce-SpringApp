package za.co.wirecard.channel.backoffice.builder;

import org.springframework.data.jpa.domain.Specification;
import za.co.wirecard.channel.backoffice.entities.ApplicationEntity;

import javax.persistence.criteria.*;

public class ApplicationSpecifications {

    public static Specification<ApplicationEntity> merchantIdEquals(long merchantId) {
        return (root, query, builder) -> builder.equal(root.join("merchantByMerchantId").get("id"), merchantId);
    }

    public static Specification<ApplicationEntity> stringSearchApplicationNameLike(String stringSearch){
        return (root, query, builder) -> builder.like(builder.lower(root.get("name")), "%" + stringSearch.toLowerCase() + "%");
    }

    public static Specification<ApplicationEntity> stringSearchApplicationUidLike(String stringSearch){
        return (root, query, builder) -> builder.like(builder.lower(root.get("applicationUid")), "%" + stringSearch.toLowerCase() + "%");
    }

}
