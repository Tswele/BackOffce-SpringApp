package za.co.wirecard.channel.backoffice.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor

public class Client {
    @NotNull
    private String merchantName;
    @NotNull
    private String tradingAs;
    private long merchantClassificationId;
    private String vatNumber;
    private String companyRegNo;
    private String parentCompany;
    private String codaNumber;
    private String website;
    private Long merchantStatusId;
    private long accountManagerId;
    private long salesPersonId;
    private long createdBy;
}
