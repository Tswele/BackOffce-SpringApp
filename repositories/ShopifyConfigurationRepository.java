package za.co.wirecard.channel.backoffice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import za.co.wirecard.channel.backoffice.entities.ApplicationEntity;
import za.co.wirecard.channel.backoffice.entities.ShopifyConfigurationEntity;

public interface ShopifyConfigurationRepository extends JpaRepository<ShopifyConfigurationEntity, Long> {
    ShopifyConfigurationEntity findByApplicationByApplicationId(ApplicationEntity applicationEntity);
}
