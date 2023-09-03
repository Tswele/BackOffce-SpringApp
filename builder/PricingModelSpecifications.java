package za.co.wirecard.channel.backoffice.builder;

import org.springframework.data.jpa.domain.Specification;
import za.co.wirecard.channel.backoffice.entities.PricingModelEntity;

import javax.persistence.criteria.*;

public class PricingModelSpecifications {

    public static Specification<PricingModelEntity> globalPricingModel(boolean global) {
        return new Specification<PricingModelEntity>() {
            public Predicate toPredicate(Root<PricingModelEntity> root, CriteriaQuery<?> query, CriteriaBuilder builder){
                final Path<PricingModelEntity> pricingModel_ = root.get("globalPricingModel");
                return pricingModel_.in(global);
            }
        };
    }

}
