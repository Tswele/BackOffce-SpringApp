package za.co.wirecard.channel.backoffice.dto.models.responses;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import za.co.wirecard.channel.backoffice.entities.MerchantEntity;
import za.co.wirecard.channel.backoffice.models.MerchantClassification;

import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PlatformGetMerchantsResponse {

    @NotNull
    private long id;
    @NotNull
    private Date lastModified;
    @JsonFormat(pattern="yyyy-MM-dd")
    private Date dateJoined;
    @JsonFormat(pattern="yyyy-MM-dd")
    private Date activationDate;
    private String accountNumber;
    private String merchantUid;
    private String country;
    @NotNull
    private String clientName;
    @NotNull
    private String tradingAs;
    private MerchantClassification merchantClassification;
    private String vatNumber;
    private String companyRegNo;
    private String parentCompany;
    private String codaNumber;
    private String website;
    private Long merchantStatusId;
    private Long accountManagerId;
    private Long salesPersonId;
    private Long createdBy;

    public PlatformGetMerchantsResponse(MerchantEntity merchantEntity) {
        this.id = merchantEntity.getId();
        this.lastModified = merchantEntity.getLastModified();
        this.dateJoined = merchantEntity.getDateJoined();
        this.activationDate = merchantEntity.getActivationDate();
        this.accountNumber = merchantEntity.getAccountNumber();
        this.merchantUid = merchantEntity.getMerchantUid();
        // this.country
        this.clientName = merchantEntity.getCompanyName();
        this.tradingAs = merchantEntity.getTradingAs();
        // this.merchantClassification
        this.vatNumber = merchantEntity.getVatNumber();
        this.companyRegNo = merchantEntity.getCompanyRegNo();
        // this.parentCompany
        // this.codaNumber
        this.website = merchantEntity.getWebsite();
        // this.merchantStatusId
        // this.accountManagerId
        // this.salesPersonId
        // this.createdBy
    }
}
