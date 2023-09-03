package za.co.wirecard.channel.backoffice.dto.models.responses;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import za.co.wirecard.channel.backoffice.models.Address;

import javax.validation.constraints.NotNull;

@Setter
@Getter
@NoArgsConstructor
public class AddressCityIdResponse extends Address {

    @NotNull
    private long city;

}