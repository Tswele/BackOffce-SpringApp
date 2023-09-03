package za.co.wirecard.channel.backoffice.dto.models.requests;

import lombok.Data;
import za.co.wirecard.channel.backoffice.dto.models.BillingFrequency;
import za.co.wirecard.channel.backoffice.dto.models.RateStructureLineItem;

import java.util.ArrayList;

@Data
public class EditRateStructureRequest {
    Long id;
    Long lastModifiedBy;
    ArrayList<RateStructureLineItem> fixedCharges;
    ArrayList<RateStructureLineItem> variableCharges;
    ArrayList<RateStructureLineItem> minimumBilling;
}
