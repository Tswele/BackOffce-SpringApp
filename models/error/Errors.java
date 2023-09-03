package za.co.wirecard.channel.backoffice.models.error;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Errors {

    private String errorCode;
    private String message;
    private List<ErrorItem> errorItems;

}
