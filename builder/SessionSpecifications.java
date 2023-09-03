package za.co.wirecard.channel.backoffice.builder;

import org.springframework.data.jpa.domain.Specification;
import za.co.wirecard.channel.backoffice.entities.SessionEntity;

import java.sql.Timestamp;
import java.util.Date;

public final class SessionSpecifications {

    public static Specification<SessionEntity> merchantUidEquals(String merchantUid) {
        return (root, query, builder) -> builder.equal(builder.lower(root.get("merchantUid")), merchantUid.toLowerCase());
    }

    public static Specification<SessionEntity> applicationUidEquals(String applicationUid) {
        return (root, query, builder) -> builder.like(builder.lower(root.get("applicationUid")), "%" + applicationUid.toLowerCase() + "%");
    }

    public static Specification<SessionEntity> dateGreaterThan(Date fromDate) {
        return (root, query, builder) -> builder.greaterThan(root.<Timestamp>get("dateLogged"), new Date(fromDate.getTime()));
    }

    public static Specification<SessionEntity> dateLessThan(Date toDate) {
        return (root, query, builder) -> builder.lessThan(root.<Timestamp>get("dateLogged"), new Date(toDate.getTime()));

    }
}
