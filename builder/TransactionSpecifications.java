package za.co.wirecard.channel.backoffice.builder;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.jpa.domain.Specification;
import za.co.wirecard.channel.backoffice.constants.TransactionStateEnum;
import za.co.wirecard.channel.backoffice.controllers.TransactionController;
import za.co.wirecard.channel.backoffice.entities.*;

import javax.persistence.criteria.*;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

public final class TransactionSpecifications {

    private static final Logger logger = LogManager.getLogger(TransactionSpecifications.class);

    public static Specification<TransactionEntity> merchantIdEquals(Long merchantId) {
        return (root, query, builder) -> builder.equal(root.get("merchantId"), merchantId);
    }

    public static Specification<TransactionEntity> merchantIdsEquals(List<Long> merchantIds) {
        return new Specification<TransactionEntity>() {
            public Predicate toPredicate(Root<TransactionEntity> root, CriteriaQuery<?> query, CriteriaBuilder builder){
                final Path<MerchantEntity> merchant_ = root.<MerchantEntity> get("merchantId");
                return merchant_.in(merchantIds);
            }
        };
    }

    public static Specification<TransactionEntity> applicationIdsEquals(List<Long> applicationIds) {
        return new Specification<TransactionEntity>() {
            public Predicate toPredicate(Root<TransactionEntity> root, CriteriaQuery<?> query, CriteriaBuilder builder){
                final Path<ApplicationEntity> application_ = root.join("interfaceByInterfaceId").join("applicationPaymentTypesById").join("applicationByApplicationId").get("id");
                return application_.in(applicationIds);
            }
        };
    }

//    public static Specification<TransactionEntity> applicationIdsEquals(List<Long> applicationIds) {
//        return (Specification<TransactionEntity>) (root, query, builder) -> {
//            final Path<ApplicationEntity> application_ = root.<TransactionEntity> get("interfaceByInterfaceId").get("applicationPaymentTypesById").get("applicationByApplicationId").get("id");
//            return application_.in(applicationIds);
//        };
//    }

    public static Specification<TransactionEntity> merchantUidEquals(String merchantUid) {
        return (root, query, builder) -> builder.like(root.join("merchantByMerchantId").get("merchantUid"), "%" + merchantUid + "%");
    }

    public static Specification<TransactionEntity> applicationUidEquals(String applicationUid) {
        return (root, query, builder) -> builder.like(root.join("interfaceByInterfaceId").join("applicationPaymentTypesById").join("applicationByApplicationId").get("applicationUid"), "%" + applicationUid + "%");
    }

    public static Specification<TransactionEntity> paymentTypeCodeEquals(List<String> paymentTypeCodes) {
        return (Specification<TransactionEntity>) (root, query, builder) -> {
            final Path<PaymentTypeEntity> paymentType_ = root.join("paymentTypeByPaymentTypeId").get("code");
            return paymentType_.in(paymentTypeCodes);
    };
    }

    //card type added

    public static Specification<TransactionEntity> cardTypesEquals(List<String> cardType){
        return  (Specification<TransactionEntity>) (root,query, builder)->{
            final Path<CardTypeEntity> cardType_ =root.join("cardTransactionsById").get("cardType");
            return cardType_.in(cardType);
        };
    }



    public static Specification<TransactionEntity> transactionStateCodeEquals(List<String> transactionStateCodes) {
        return new Specification<TransactionEntity>() {
            public Predicate toPredicate(Root<TransactionEntity> root, CriteriaQuery<?> query, CriteriaBuilder builder){
                final Path<TransactionStateEntity> transactionState_ = root.join("transactionStateByTransactionStateId").get("code");
                return transactionState_.in(transactionStateCodes);
            }
        };
    }

    public static Specification<TransactionEntity> interfaceIdsEquals(List<Long> interfaceIds) {
        return new Specification<TransactionEntity>() {
            public Predicate toPredicate(Root<TransactionEntity> root, CriteriaQuery<?> query, CriteriaBuilder builder){
                final Path<InterfaceEntity> interface_ = root.<InterfaceEntity> get("interfaceId");
                return interface_.in(interfaceIds);
            }
        };
    }

    public static Specification<TransactionEntity> ecisEquals(List<String> ecis) {
        return (Specification<TransactionEntity>) (root, query, builder) -> {
            final Path<EciEntity> paymentType_ = root.join("interfaceByInterfaceId").join("interfaceEcisById").join("eciByEciId").get("eci");
            return paymentType_.in(ecis);
        };
    }

    public static Specification<TransactionEntity> acquiringBankCodeEquals(String acquiringBankCode) {
        return (root, query, builder) -> builder.equal(root.join("gatewayByGatewayId") != null ? root.join("gatewayByGatewayId").get("code") : null, acquiringBankCode);
    }

    public static Specification<TransactionEntity> issuingBankEquals(String issuingBank) {
        // How does casing work?
        return (root, query, builder) -> builder.equal(root.join("cardTransactionsById") != null ? root.join("cardTransactionsById").get("issuingBank") : null, issuingBank);
    }



    public static Specification<TransactionEntity> transactionStateIdEquals(Long transactionStateId) {
        return (root, query, builder) -> builder.equal(root.get("transactionStateId"), transactionStateId);
    }

//    public static Specification<TransactionEntity> filterOnDisplayedTransactionStates() {
//        return (transactionEntityRoot, criteriaQuery, criteriaBuilder) -> {
//            return criteriaBuilder.or(
//                    criteriaBuilder.equal(transactionEntityRoot.get("transactionStateByTransactionStateId").get("code"), TransactionStateEnum.SETTLED.value()),
//                    criteriaBuilder.equal(transactionEntityRoot.get("transactionStateByTransactionStateId").get("code"), TransactionStateEnum.REFUNDED.value()),
//                    criteriaBuilder.equal(transactionEntityRoot.get("transactionStateByTransactionStateId").get("code"), TransactionStateEnum.PARTIALLY_REFUNDED.value()),
//                    criteriaBuilder.equal(transactionEntityRoot.get("transactionStateByTransactionStateId").get("code"), TransactionStateEnum.AUTHORISED.value()),
//                    criteriaBuilder.equal(transactionEntityRoot.get("transactionStateByTransactionStateId").get("code"), TransactionStateEnum.TIMED_OUT.value()),
//                    criteriaBuilder.equal(transactionEntityRoot.get("transactionStateByTransactionStateId").get("code"), TransactionStateEnum.FAILED.value()),
//                    criteriaBuilder.equal(transactionEntityRoot.get("transactionStateByTransactionStateId").get("code"), TransactionStateEnum.TDS_AUTH_FAILED.value()),
//                    criteriaBuilder.equal(transactionEntityRoot.get("transactionStateByTransactionStateId").get("code"), TransactionStateEnum.TDS_AUTH_REQUIRED.value()),
//                    criteriaBuilder.equal(transactionEntityRoot.get("transactionStateByTransactionStateId").get("code"), TransactionStateEnum.REVERSED.value()),
//                    criteriaBuilder.equal(transactionEntityRoot.get("transactionStateByTransactionStateId").get("code"), TransactionStateEnum.PAYMENT_FAILED.value())
//            );
//        };
//    }



    public static Specification<TransactionEntity> initiationDateGreaterThan(Date fromDate) {
        return (root, query, builder) -> builder.greaterThan(root.<Timestamp>get("initiationDate"), new Date(fromDate.getTime()));
    }

    public static Specification<TransactionEntity> initiationDateLessThan(Date toDate) {
        toDate.setTime(toDate.getTime()+((22*3600*1000)-1));
        return (root, query, builder) -> builder.lessThan(root.<Timestamp>get("initiationDate"), new Date(toDate.getTime()));
    }

    public static Specification<TransactionEntity> stringSearchMerchantReferenceLike(String stringSearch) {
        return (root, query, builder) -> builder.like(builder.lower(root.get("merchantReference")), "%" + stringSearch.toLowerCase() + "%");
    }

    public static Specification<TransactionEntity> stringSearchPurchaserNameLike(String stringSearch) {
        return (root, query, builder) -> builder.like(builder.lower(root.get("purchaserFullName")), "%" + stringSearch.toLowerCase() + "%");
    }

    public static Specification<TransactionEntity> stringSearchTransactionUidLike(String stringSearch) {
        return (root, query, builder) -> builder.like(builder.lower(root.get("transactionUid")), "%" + stringSearch.toLowerCase() + "%");
    }

    public static Specification<TransactionEntity> stringSearchSessionUidLike(String stringSearch) {
        return (root, query, builder) -> builder.like(builder.lower(root.get("originatingTransactionId")), "%" + stringSearch.toLowerCase() + "%");
    }

    public static Specification<TransactionEntity> stringSearchTransactionIdLike(Long stringSearch) {
        return (root, query, builder) -> builder.equal(root.get("id").as(String.class),  stringSearch );
    }

    public static Specification<TransactionEntity> errorMessageLike(String stringSearch) {
        return (root, query, builder) -> builder.like(builder.lower(root.get("errorMessage")), "%" + stringSearch.toLowerCase() + "%");
    }

    public static Specification<TransactionEntity> stringSearchTransactionValueLike(String stringSearch) {
        return (root, query, builder) -> builder.like(builder.lower(root.get("transactionValue")).as(String.class), "%" + stringSearch.toLowerCase() + "%");
    }

    public static Specification<TransactionEntity> stringSearchRefundValueLike(String stringSearch) {
        return (root, query, builder) -> builder.like(builder.lower(root.get("refundValue")).as(String.class), "%" + stringSearch.toLowerCase() + "%");
    }

    public static Specification<TransactionEntity> stringSearchAuthorisedValueLike(String stringSearch) {
        return (root, query, builder) -> builder.like(builder.lower(root.get("authorisedValue")).as(String.class), "%" + stringSearch.toLowerCase() + "%");
    }

    public static Specification<TransactionEntity> stringSearchSettledValueLike(String stringSearch) {
        return (root, query, builder) -> builder.like(builder.lower(root.get("settledValue")).as(String.class), "%" + stringSearch.toLowerCase() + "%");
    }

    public static Specification<TransactionEntity> cardTransactionIdsEquals(List<Long> transactionIds) {
        return new Specification<TransactionEntity>() {
            public Predicate toPredicate(Root<TransactionEntity> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                final Path<TransactionEntity> cardTransaction_ = root.<TransactionEntity> get("id");
                return cardTransaction_.in(transactionIds);
            }
        };
    }

    public static Specification<TransactionEntity> transactionByRelatedTransactionIdIsNull() {
        return (root, query, builder) -> builder.isNull(root.get("transactionByRelatedTransactionId"));
    }

}
