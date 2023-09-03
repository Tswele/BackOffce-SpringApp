package za.co.wirecard.channel.backoffice.dto.models;

import lombok.Data;
import za.co.wirecard.channel.backoffice.entities.ApplicationPaymentTypeEntity;
import za.co.wirecard.channel.backoffice.entities.PaymentTypeEntity;

@Data
public class ApplicationPaymentType {
    PaymentTypeEntity applicationPaymentType;
    boolean isActive;

    public ApplicationPaymentType(PaymentTypeEntity applicationPaymentType, boolean isActive) {
        this.applicationPaymentType = applicationPaymentType;
        this.isActive = isActive;
    }

}
