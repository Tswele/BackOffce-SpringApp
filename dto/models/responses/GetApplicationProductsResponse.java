package za.co.wirecard.channel.backoffice.dto.models.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import za.co.wirecard.channel.backoffice.entities.MerchantProductApplicationEntity;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetApplicationProductsResponse {
    private Long productId;
    private Long selectedRateStructureId;
    private Long selectedPricingModelId;
    private Long customPricingModelId;

    public GetApplicationProductsResponse(MerchantProductApplicationEntity merchantProductApplicationEntity) {
        if (merchantProductApplicationEntity.getMerchantProductByMerchantProductId() != null) {
            this.setProductId(merchantProductApplicationEntity.getMerchantProductByMerchantProductId().getProductByProductId().getId());
        }
        this.setSelectedRateStructureId(merchantProductApplicationEntity.getSelectedPricingModel().getRateStructureId());
        this.setSelectedPricingModelId(merchantProductApplicationEntity.getSelectedPricingModel().getId());
        if (merchantProductApplicationEntity.getCustomPricingModel() != null) {
            this.setCustomPricingModelId(merchantProductApplicationEntity.getCustomPricingModel());
        }
    }
}
