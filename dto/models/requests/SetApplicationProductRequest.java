package za.co.wirecard.channel.backoffice.dto.models.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import za.co.wirecard.channel.backoffice.dto.models.CustomLineItems;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SetApplicationProductRequest {
    private Long productId;
    private Long selectedPricingModelId;
    private CustomLineItems customLineItems;
}
