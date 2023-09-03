package za.co.wirecard.channel.backoffice.builder;

import org.springframework.data.jpa.domain.Specification;
import za.co.wirecard.channel.backoffice.entities.ApplicationEntity;
import za.co.wirecard.channel.backoffice.entities.MerchantEntity;
import za.co.wirecard.channel.backoffice.entities.RateStructureEntity;
import za.co.wirecard.channel.backoffice.entities.TransactionEntity;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

public class RateStructureSpecifications {

    public static Specification<RateStructureEntity> rateStructureIdEquals(Long id) {
        return (root, query, builder) -> builder.equal(root.get("id"), id);
    }
//
//    public static Specification<RateStructureEntity> rateStructureIdEquals(Long id) {
//        return new Specification<RateStructureEntity>() {
//            public Predicate toPredicate(Root<RateStructureEntity> root, CriteriaQuery<?> query, CriteriaBuilder builder){
//                final Path<RateStructureEntity> rateStructure_ = root.get("id");
//                return rateStructure_.in(id);
//            }
//        };
//    }

    public static Specification<RateStructureEntity> rateStructureState(String stateCode) {
        return new Specification<RateStructureEntity>() {
            public Predicate toPredicate(Root<RateStructureEntity> root, CriteriaQuery<?> query, CriteriaBuilder builder){
                final Path<RateStructureEntity> rateStructure_ = root.join("rateStructureVersionsById").join("rateStructureStateByRateStructureStateId").get("code");
                return rateStructure_.in(stateCode);
            }
        };
    }

    public static Specification<RateStructureEntity> rateStructureStates(ArrayList<String> stateCodes) {
        return new Specification<RateStructureEntity>() {
            public Predicate toPredicate(Root<RateStructureEntity> root, CriteriaQuery<?> query, CriteriaBuilder builder){
                final Path<RateStructureEntity> rateStructure_ = root.join("rateStructureVersionsById").join("rateStructureStateByRateStructureStateId").get("code");
                return rateStructure_.in(stateCodes);
            }
        };
    }

}
