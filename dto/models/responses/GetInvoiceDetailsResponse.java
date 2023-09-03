package za.co.wirecard.channel.backoffice.dto.models.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import za.co.wirecard.channel.backoffice.entities.RateStructureHistoryEntity;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetInvoiceDetailsResponse {
    private String rateStructureName;
    private Long rateStructureVersion;
    private String pricingModelName;
    private Long pricingModelVersion;

    public GetInvoiceDetailsResponse(RateStructureHistoryEntity rateStructureHistoryEntity) {
        this.setRateStructureName(rateStructureHistoryEntity.getRateStructureName());
        this.setRateStructureVersion(rateStructureHistoryEntity.getRateStructureVersion());
        this.setPricingModelName(rateStructureHistoryEntity.getPricingModelName());
        this.setPricingModelVersion(rateStructureHistoryEntity.getPricingModelVersion());
    }
}
