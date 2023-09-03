package za.co.wirecard.channel.backoffice.dto.models.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import za.co.wirecard.channel.backoffice.dto.models.FixedCharge;
import za.co.wirecard.channel.backoffice.dto.models.MinimumBilling;
import za.co.wirecard.channel.backoffice.dto.models.VariableCharge;
import za.co.wirecard.channel.backoffice.models.Currency;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreatePricingModelRequest {
    Long lastModifiedBy;
    String name;
    String description;
    boolean globalPricingModel;
    Currency currency;
    Long rateStructureId;
    List<FixedCharge> fixedChargeList;
    List<VariableCharge> variableChargeList;
    MinimumBilling minimumBilling;
}
