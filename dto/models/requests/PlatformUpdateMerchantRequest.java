package za.co.wirecard.channel.backoffice.dto.models.requests;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import za.co.wirecard.channel.backoffice.models.Client;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
public class PlatformUpdateMerchantRequest extends Client {

    @NotNull
    private long id;
    @NotNull
    private Long modifiedBy;
    @NotNull
    private String vatNumber;
    @NotNull
    private String companyRegNo;
    @NotNull
    private Integer invoiceDay;
    @NotNull
    private Boolean active;

    //private AddressCityIdResponse addressCityIdResponse;
}
