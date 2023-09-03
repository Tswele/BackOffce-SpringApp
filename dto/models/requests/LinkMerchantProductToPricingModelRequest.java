package za.co.wirecard.channel.backoffice.dto.models.requests;

import lombok.Data;
import lombok.NoArgsConstructor;
import za.co.wirecard.channel.backoffice.dto.models.LinkedProduct;

import java.util.ArrayList;

@Data
@NoArgsConstructor
public class LinkMerchantProductToPricingModelRequest {
    Long backOfficeUserId;
    Long merchantId;
    ArrayList<LinkedProduct> products;
}
