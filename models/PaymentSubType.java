package za.co.wirecard.channel.backoffice.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import za.co.wirecard.channel.backoffice.entities.PaymentSubtypeEntity;

@Getter
@Setter
@NoArgsConstructor
public class PaymentSubType {
    private long id;
    private Long externalId;
    private String name;
    private Boolean refundSupported;

    public PaymentSubType (PaymentSubtypeEntity paymentSubtypeEntity){

        this.setId(paymentSubtypeEntity.getId());
        this.setExternalId(paymentSubtypeEntity.getExternalId());
        this.setName(paymentSubtypeEntity.getName());
        this.setRefundSupported(paymentSubtypeEntity.getRefundSupported());
    }
}
