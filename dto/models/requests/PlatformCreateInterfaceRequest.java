package za.co.wirecard.channel.backoffice.dto.models.requests;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import za.co.wirecard.channel.backoffice.models.Interface;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
public class PlatformCreateInterfaceRequest extends Interface {

    @NotNull
    private long applicationId;

    @NotNull
    private long merchantId;

    @NotNull
    private long gatewayId;

    @NotNull
    private long paymentTypeId;

    @NotNull
    private long securityMethodId;

    @NotNull
    private long countryId;

    @NotNull
    private long currencyId;
    private long tdsMerchantTypeId;
    private String tdsPassword;
    private String merchantCategoryCode;
    private String terminalId;
//    @NotNull
//    private boolean paymentTypeSelection;
}
