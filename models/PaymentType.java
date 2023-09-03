package za.co.wirecard.channel.backoffice.models;

import lombok.*;
import za.co.wirecard.channel.backoffice.entities.PaymentTypeEntity;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
public class PaymentType {
    @NotNull
    private long id;
    private String paymentTypeName;
    private String code;
    private boolean isActiveForMerchant;
    private boolean entryExists;

    public PaymentType (PaymentTypeEntity paymentTypeEntity){
        this.setId(paymentTypeEntity.getId());
        this.setPaymentTypeName(paymentTypeEntity.getName());
        this.code = paymentTypeEntity.getCode();
    }

    public PaymentType (PaymentTypeEntity paymentTypeEntity, boolean isActive, boolean entryExists){
        this.setId(paymentTypeEntity.getId());
        this.setPaymentTypeName(paymentTypeEntity.getName());
        this.code = paymentTypeEntity.getCode();
        this.isActiveForMerchant = isActive;
        this.entryExists = entryExists;
    }
}
