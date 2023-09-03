package za.co.wirecard.channel.backoffice.services;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import za.co.wirecard.channel.backoffice.entities.PricingModelEntity;

@Service
public interface PricingModelService {

    Page<PricingModelEntity> getAllGlobalPricingModels(int page, int limit);
}
