package za.co.wirecard.channel.backoffice.dto.models.requests;

import lombok.Data;
import za.co.wirecard.channel.backoffice.dto.models.FixedCharge;
import za.co.wirecard.channel.backoffice.dto.models.MinimumBilling;
import za.co.wirecard.channel.backoffice.dto.models.VariableCharge;

import java.util.List;

@Data
public class EditPricingModelRequest {
    Long lastModifiedBy;
    List<FixedCharge> fixedChargeList;
    List<VariableCharge> variableChargeList;
    MinimumBilling minimumBilling;
}
