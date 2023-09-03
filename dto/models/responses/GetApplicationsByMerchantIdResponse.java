package za.co.wirecard.channel.backoffice.dto.models.responses;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import za.co.wirecard.channel.backoffice.models.Application;
import za.co.wirecard.channel.backoffice.models.Merchant;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
public class GetApplicationsByMerchantIdResponse extends Application {

    @NotNull
    private long id;

    @NotNull
    private Merchant merchant;
}
