package za.co.wirecard.channel.backoffice.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CardInterfaceConfig {

    private long interfaceId;
    private long eciId;
    private String description;

}
