package za.co.wirecard.channel.backoffice.dto.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class RefundResponse {
    @NotNull
    private float amount;
    @NotNull
    private String currency;
    private List<Payment> refundedPayments;
}
