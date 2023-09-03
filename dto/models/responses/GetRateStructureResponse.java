package za.co.wirecard.channel.backoffice.dto.models.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import za.co.wirecard.channel.backoffice.dto.models.RateStructure;
import za.co.wirecard.channel.backoffice.dto.models.RateStructureLineItem;
import za.co.wirecard.channel.backoffice.models.PricingModel;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetRateStructureResponse {
    RateStructure rateStructure;
    List<RateStructureLineItem> fixedCharges;
    List<RateStructureLineItem> variableCharges;
    List<RateStructureLineItem> minimumBilling;
    List<PricingModel> pricingModels;
}
