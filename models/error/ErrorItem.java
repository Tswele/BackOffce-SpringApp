package za.co.wirecard.channel.backoffice.models.error;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ErrorItem {

    private String field;
    private String message;

}
