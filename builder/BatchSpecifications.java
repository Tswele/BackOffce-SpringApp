package za.co.wirecard.channel.backoffice.builder;

import org.springframework.data.jpa.domain.Specification;
import za.co.wirecard.channel.backoffice.entities.BatchTransactionEntity;

import java.sql.Timestamp;
import java.util.Date;

public final class BatchSpecifications {

    public static Specification<BatchTransactionEntity> merchantUidEquals(String merchantUid) {
        return (root, query, builder) -> builder.equal(builder.lower(root.get("merchantUid")), merchantUid.toLowerCase());
    }

    public static Specification<BatchTransactionEntity> applicationUidEquals(String applicationUid) {
        return (root, query, builder) -> builder.equal(builder.lower(root.get("applicationUid")), applicationUid.toLowerCase());
    }

    public static Specification<BatchTransactionEntity> dateGreaterThan(Date fromDate) {
        return (root, query, builder) -> builder.greaterThan(root.<Timestamp>get("date"), new Date(fromDate.getTime()));
    }

    public static Specification<BatchTransactionEntity> dateLessThan(Date toDate) {
        toDate.setTime(toDate.getTime()+((22*3600*1000)-1));
        return (root, query, builder) -> builder.lessThan(root.<Timestamp>get("date"), new Date(toDate.getTime()));
    }
}
