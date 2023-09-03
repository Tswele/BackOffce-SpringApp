package za.co.wirecard.channel.backoffice.dto.models.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Create2FAResponse {

    private String qrCodeUrl;

}
