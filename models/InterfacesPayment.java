package za.co.wirecard.channel.backoffice.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import za.co.wirecard.channel.backoffice.entities.ApplicationPaymentTypeEntity;
import za.co.wirecard.channel.backoffice.entities.InterfaceEntity;
import za.co.wirecard.channel.backoffice.entities.PaymentTypeEntity;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InterfacesPayment {
    private PaymentInterface interfaceEntity;
    private PaymentType paymentType;

    public InterfacesPayment(ApplicationPaymentTypeEntity applicationPaymentTypeEntity) {
        this.interfaceEntity = new PaymentInterface(applicationPaymentTypeEntity.getInterfaceByInterfaceId());
        this.paymentType = new PaymentType(applicationPaymentTypeEntity.getPaymentTypeByPaymentTypeId());
    }
}
