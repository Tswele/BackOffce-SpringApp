package za.co.wirecard.channel.backoffice.dto.models.requests;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ProductDefaultRequest {
    Long productId;
    Long rateStructureId;
    Long pricingModelId;
}
