package za.co.wirecard.channel.backoffice.dto.models;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class CustomLineItems {
    List<FixedCharge> fixedChargeList;
    List<VariableCharge> variableChargeList;
    MinimumBilling minimumBilling;
}
