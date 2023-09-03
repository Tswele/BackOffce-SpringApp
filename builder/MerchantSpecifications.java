package za.co.wirecard.channel.backoffice.builder;

import org.springframework.data.jpa.domain.Specification;
import za.co.wirecard.channel.backoffice.entities.MerchantEntity;
import za.co.wirecard.channel.backoffice.entities.PaymentTypeEntity;
import za.co.wirecard.channel.backoffice.entities.TransactionEntity;

import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

public class MerchantSpecifications {

    public static Specification<MerchantEntity> merchantUidEquals(String merchantUid) {
        return (root, query, builder) -> builder.equal(builder.lower(root.get("merchantUid")), merchantUid.toLowerCase());
    }

    public static Specification<MerchantEntity> merchantIdEquals(Long merchantId) {
        return (root, query, builder) -> builder.equal(root.get("id"), merchantId);
    }

    public static Specification<MerchantEntity> accountNoEquals(String accountNo) {
        return (root, query, builder) -> builder.equal(builder.lower(root.get("accountNumber")), accountNo.toLowerCase());
    }

    public static Specification<MerchantEntity> clientNameLike(String clientName) {
        return (root, query, builder) -> {
            Predicate predicateForCompanyName = builder.like(builder.lower(root.get("companyName")), "%" + clientName.toLowerCase() + "%");
            Predicate predicateForTradingAs = builder.like(builder.lower(root.get("tradingAs")), "%" + clientName.toLowerCase() + "%");
            return builder.or(predicateForTradingAs, predicateForCompanyName);
        };
    }

//    public static Specification<MerchantEntity> tradingAsLike(String tradingAs) {
//        return (root, query, builder) -> builder.like(root.get("trading_as"), "%" + tradingAs + "%");
//    }

    public static Specification<MerchantEntity> isOnboarding() {
        return (root, query, builder) -> builder.equal(root.join("merchantStatusByMerchantStatusId").get("name"), "Onboarding");
    }

    public static Specification<MerchantEntity> nonOnboarding() {
        List<String> merchantStatusNames = new ArrayList<>();
        merchantStatusNames.add("Active");
        merchantStatusNames.add("Cancelled");
        merchantStatusNames.add("Pending Certification");
        merchantStatusNames.add("Suspended");
        merchantStatusNames.add("Developing");
        return (Specification<MerchantEntity>) (root, query, builder) -> {
            final Path<MerchantEntity> merchantStatus_ = root.join("merchantStatusByMerchantStatusId").get("name");
            return merchantStatus_.in(merchantStatusNames);
        };
    }

}
