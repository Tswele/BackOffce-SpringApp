package za.co.wirecard.channel.backoffice.dto.models.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import za.co.wirecard.channel.backoffice.dto.models.RateStructureLineItem;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetRateStructureLineItemsResponse {
    private List<RateStructureLineItem> fixedCharges;
    private List<RateStructureLineItem> variableCharges;
    private List<RateStructureLineItem> minimumBilling;
}
