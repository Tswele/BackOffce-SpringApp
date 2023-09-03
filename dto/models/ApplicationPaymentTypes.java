package za.co.wirecard.channel.backoffice.dto.models;

import lombok.Data;

import java.util.List;

@Data
public class ApplicationPaymentTypes {
    List<ApplicationPaymentType> applicationPaymentTypes;

    public ApplicationPaymentTypes(List<ApplicationPaymentType> applicationPaymentTypes) {
        this.applicationPaymentTypes = applicationPaymentTypes;
    }

}
