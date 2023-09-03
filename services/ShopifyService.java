package za.co.wirecard.channel.backoffice.services;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import za.co.wirecard.channel.backoffice.dto.models.requests.CreateShopifyConfigRequest;
import za.co.wirecard.channel.backoffice.dto.models.responses.GetShopifyConfigResponse;
import za.co.wirecard.channel.backoffice.entities.ApplicationEntity;
import za.co.wirecard.channel.backoffice.entities.ShopifyConfigurationEntity;
import za.co.wirecard.channel.backoffice.repositories.ApplicationRepository;
import za.co.wirecard.channel.backoffice.repositories.ShopifyConfigurationRepository;
import za.co.wirecard.common.exceptions.NotFoundException;

@Service
public class ShopifyService {

    private final ApplicationRepository applicationRepository;
    private final ShopifyConfigurationRepository shopifyConfigurationRepository;

    public ShopifyService(ApplicationRepository applicationRepository,
                          ShopifyConfigurationRepository shopifyConfigurationRepository) {
        this.applicationRepository = applicationRepository;
        this.shopifyConfigurationRepository = shopifyConfigurationRepository;
    }

    public GetShopifyConfigResponse getShopifyConfiguration(String applicationUid) {
        ApplicationEntity applicationEntity = applicationRepository.findByApplicationUidEquals(applicationUid);
        ShopifyConfigurationEntity shopifyConfigurationEntity = shopifyConfigurationRepository.findByApplicationByApplicationId(applicationEntity);
        if (shopifyConfigurationEntity != null) {
            return new GetShopifyConfigResponse(shopifyConfigurationEntity);
        } else {
            throw new NotFoundException("No shopify configuration found for application Uid | " + applicationUid);
        }
    }

    public ResponseEntity<?> createShopifyConfiguration(CreateShopifyConfigRequest createShopifyConfigRequest) {
        ApplicationEntity applicationEntity = applicationRepository.findByApplicationUidEquals(createShopifyConfigRequest.getApplicationUid());
        ShopifyConfigurationEntity shopifyConfigurationEntity = new ShopifyConfigurationEntity();
        shopifyConfigurationEntity.setApplicationByApplicationId(applicationEntity);
        shopifyConfigurationEntity.setShopDomain(createShopifyConfigRequest.getShopDomain());
        shopifyConfigurationRepository.save(shopifyConfigurationEntity);
        return ResponseEntity.ok().build();
    }

    public ResponseEntity<?> editShopifyConfiguration(CreateShopifyConfigRequest createShopifyConfigRequest) {
        ApplicationEntity applicationEntity = applicationRepository.findByApplicationUidEquals(createShopifyConfigRequest.getApplicationUid());
        ShopifyConfigurationEntity shopifyConfigurationEntity = shopifyConfigurationRepository.findByApplicationByApplicationId(applicationEntity);
        if (shopifyConfigurationEntity != null) {
            shopifyConfigurationEntity.setShopDomain(createShopifyConfigRequest.getShopDomain());
            shopifyConfigurationRepository.save(shopifyConfigurationEntity);
            return ResponseEntity.ok().build();
        } else {
            throw new NotFoundException("No shopify configuration found for application Uid | " + createShopifyConfigRequest.getApplicationUid());
        }
    }
}
