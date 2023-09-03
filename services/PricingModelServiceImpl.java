package za.co.wirecard.channel.backoffice.services;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import za.co.wirecard.channel.backoffice.entities.PricingModelEntity;
import za.co.wirecard.channel.backoffice.entities.RateStructureEntity;
import za.co.wirecard.channel.backoffice.repositories.PricingModelRepository;

import static za.co.wirecard.channel.backoffice.builder.PricingModelSpecifications.globalPricingModel;

@Service
public class PricingModelServiceImpl implements PricingModelService{

    private final PricingModelRepository pricingModelRepository;

    private static final Logger logger = LogManager.getLogger(PricingModelServiceImpl.class);

    public PricingModelServiceImpl(PricingModelRepository pricingModelRepository) {
        this.pricingModelRepository = pricingModelRepository;
    }

    @Override
    public Page<PricingModelEntity> getAllGlobalPricingModels(int page, int limit) {
        Specification<PricingModelEntity> specification = Specification.where(
                globalPricingModel(true)
        );
        Pageable pageable = PageRequest.of(page, limit);
        Page<PricingModelEntity> pricingModelEntities = pricingModelRepository.findAll(specification, pageable);

        return pricingModelEntities;
    }
}
