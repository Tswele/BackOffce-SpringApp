package za.co.wirecard.channel.backoffice.dto.models.responses;

import lombok.Data;
import lombok.NoArgsConstructor;
import za.co.wirecard.channel.backoffice.dto.models.RateStructure;
import za.co.wirecard.channel.backoffice.models.PricingModel;

import java.util.ArrayList;

@Data
@NoArgsConstructor
public class PricingModelDropdownResponse {

    ArrayList<PricingModel> pricingModels;
}
