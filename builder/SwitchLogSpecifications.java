package za.co.wirecard.channel.backoffice.builder;

import org.springframework.data.jpa.domain.Specification;
import za.co.wirecard.channel.backoffice.entities.TStandardbankSecondarySwitchLogEntity;

public final class SwitchLogSpecifications {

    public static Specification<TStandardbankSecondarySwitchLogEntity> transactionIdEquals(long transactionId) {
        return (root, query, builder) -> builder.equal(root.get("transactionId"), transactionId);
    }
}
