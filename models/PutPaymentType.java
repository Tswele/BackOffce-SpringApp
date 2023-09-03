package za.co.wirecard.channel.backoffice.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PutPaymentType {

    @NotNull
    private long id;
    private String paymentTypeName;

}
