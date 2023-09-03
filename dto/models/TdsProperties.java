package za.co.wirecard.channel.backoffice.dto.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TdsProperties {

    private BankservConfiguration bankservConfiguration;
    private CardinalConfiguration cardinalConfiguration;


}
