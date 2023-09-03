package za.co.wirecard.channel.backoffice.dto.models.responses;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import za.co.wirecard.channel.backoffice.models.BackOfficeUser;
import za.co.wirecard.channel.backoffice.models.MerchantClassification;
import za.co.wirecard.channel.backoffice.models.MerchantStatus;

import javax.validation.constraints.NotNull;
import java.util.Date;

@Setter
@Getter
@NoArgsConstructor
public class PlatformGetMerchantByIdResponse {

    @NotNull
    private long id;
    @NotNull
    private String merchantUid;
    @NotNull
    private String vatNumber;
    @NotNull
    private String accountNumber;
    @NotNull
    private String companyRegNo;
    @NotNull
    private Integer invoiceDay;
    @NotNull
    private Date lastModified;
    private Date dateJoined;
    private Date activationDate;
    @NotNull
    private String merchantName;
    @NotNull
    private String tradingAs;
    private MerchantClassification merchantClassification;
    private String parentCompany;
    private String codaNumber;
    private String website;
    private MerchantStatus merchantStatus;
    private BackOfficeUser accountManager;
    private BackOfficeUser salesPerson;
    private long createdBy;
//    private BackOfficeUser createdBy;
//    private BackOfficeUser modifiedBy;
//
//    private AddressResponse address.ts;
}
