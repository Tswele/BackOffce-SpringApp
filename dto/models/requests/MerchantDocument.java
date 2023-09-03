package za.co.wirecard.channel.backoffice.dto.models.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import za.co.wirecard.channel.backoffice.entities.MerchantDocumentEntity;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MerchantDocument {

    private long merchantId;
    private long id;
    @NotNull
    private String link;
    @NotNull
    private String name;

    public MerchantDocument(MerchantDocumentEntity merchantDocumentEntity) {
        this.merchantId = merchantDocumentEntity.getMerchantByMerchantId().getId();
        this.id = merchantDocumentEntity.getId();
        this.link = merchantDocumentEntity.getLink();
        this.name = merchantDocumentEntity.getName();
    }
}
