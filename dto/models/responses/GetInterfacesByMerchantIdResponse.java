package za.co.wirecard.channel.backoffice.dto.models.responses;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import za.co.wirecard.channel.backoffice.models.Interface;
import za.co.wirecard.channel.backoffice.models.Merchant;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor

public class GetInterfacesByMerchantIdResponse extends Interface {

    @NotNull
    private long id;
    @NotNull
    private Merchant merchant;
    @NotNull
    private long paymentTypeId;
    @NotNull
    private String code;
}
