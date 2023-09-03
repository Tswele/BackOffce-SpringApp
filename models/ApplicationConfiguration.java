package za.co.wirecard.channel.backoffice.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor

public class ApplicationConfiguration {

    private long id;
    private long applicationId;
    private long configurationTypeId;

}
