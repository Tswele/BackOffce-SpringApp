package za.co.wirecard.channel.backoffice.dto.models.responses;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MerchantProductDetail {
    Long productId;
    Long selectedRateStructureId;
    Long selectedPricingModelId;
    Long customPricingModelId;
}
