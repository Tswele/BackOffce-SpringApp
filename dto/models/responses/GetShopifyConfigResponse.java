package za.co.wirecard.channel.backoffice.dto.models.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import za.co.wirecard.channel.backoffice.entities.ShopifyConfigurationEntity;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetShopifyConfigResponse {
    private String applicationUid;
    private String shopDomain;

    public GetShopifyConfigResponse(ShopifyConfigurationEntity shopifyConfigurationEntity) {
        this.setApplicationUid(shopifyConfigurationEntity.getApplicationByApplicationId().getApplicationUid());
        this.setShopDomain(shopifyConfigurationEntity.getShopDomain());
    }
}