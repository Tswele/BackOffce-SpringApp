package za.co.wirecard.channel.backoffice.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor

public class EftInterfaceConfig {

    @NotNull
    public long interfaceId;
    @NotNull
    public String merchantKey;

}
