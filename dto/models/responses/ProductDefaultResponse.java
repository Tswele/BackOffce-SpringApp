package za.co.wirecard.channel.backoffice.dto.models.responses;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ProductDefaultResponse {
    Long productId;
    Long rateStructureId;
    Long pricingModelId;
}
