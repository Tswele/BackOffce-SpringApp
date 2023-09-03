package za.co.wirecard.channel.backoffice.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import za.co.wirecard.channel.backoffice.entities.MerchantProductEntity;
import za.co.wirecard.channel.backoffice.entities.ProductEntity;
import za.co.wirecard.channel.backoffice.entities.StatusEntity;

import javax.persistence.*;
import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MerchantProduct {

    private long id;
    //    private Timestamp lastModified;
    //    private Boolean active;
    //    private ProductEntity productByProductId;
    private long merchantId;
    //    private Long lastModifiedBy;
    //    private Timestamp dateAdded;
    //    private Timestamp dateInactive;
    //    private Long customRateStructure;
    //    private Long customPricingModel;
    //    private Long selectedPricingModelId;
    private StatusEntity statusByStatusId;

    public MerchantProduct(MerchantProductEntity merchantProductEntity) {
        this.id = merchantProductEntity.getId();
        this.merchantId = merchantProductEntity.getMerchantId();
        this.statusByStatusId = merchantProductEntity.getStatusByStatusId();
    }
}
