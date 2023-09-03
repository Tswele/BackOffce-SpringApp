package za.co.wirecard.channel.backoffice.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import za.co.wirecard.channel.backoffice.entities.MerchantEntity;

import javax.validation.constraints.NotNull;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class Merchant {
    @NotNull
    private long id;
    @NotNull
    private String merchantName;
    private Date dateCreated;
    @NotNull
    private Date lastModified;
    private String xeroContactId;

    public Merchant(MerchantEntity merchantEntity){
        this.id = merchantEntity.getId();
        this.merchantName = merchantEntity.getCompanyName();
        this.dateCreated = merchantEntity.getDateJoined();
        this.lastModified = merchantEntity.getLastModified();
        this.xeroContactId = merchantEntity.getXeroContactId();
    }
}
