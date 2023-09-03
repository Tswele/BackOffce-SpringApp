package za.co.wirecard.channel.backoffice.dto.models;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LinkedProduct {
    Long productId;
    Long pricingModelId;
    CustomLineItems customLineItems;
}
