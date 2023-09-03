package za.co.wirecard.channel.backoffice.dto.models.requests;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

@Data
@NoArgsConstructor
public class ProductDefaultSetRequest {
    ArrayList<ProductDefaultRequest> productDefaultRequestArrayList;
}
