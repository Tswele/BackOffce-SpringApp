package za.co.wirecard.channel.backoffice.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import za.co.wirecard.channel.backoffice.entities.PricingModelEntity;
import za.co.wirecard.channel.backoffice.entities.PricingModelVersionEntity;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PricingModel {
    Long id;
    String name;
    String description;
    String code;
    Timestamp lastModified;
    Boolean globalPricingModel;
    Currency currency;
    List<PricingModelVersion> pricingModelVersions;

    public PricingModel(PricingModelEntity pricingModelEntity) {
        this.id = pricingModelEntity.getId();
        this.setName(pricingModelEntity.getName());
        this.setDescription(pricingModelEntity.getDescription());
        this.setCode(pricingModelEntity.getCode());
        this.setLastModified(pricingModelEntity.getLastModified());
        this.setGlobalPricingModel(pricingModelEntity.getGlobalPricingModel());
        this.setCurrency(new Currency(pricingModelEntity.getCurrencyByCurrencyId()));
        List<PricingModelVersion> pricingModelVersions = new ArrayList<>();
        for (PricingModelVersionEntity pricingModelVersionEntity: pricingModelEntity.getPricingModelVersionsById()) {
            pricingModelVersions.add(new PricingModelVersion(pricingModelVersionEntity));
        }
        this.setPricingModelVersions(pricingModelVersions);
    }

    public PricingModel(PricingModelEntity pricingModelEntity, PricingModelVersionEntity pricingModelVersionEntity) {
        this.id = pricingModelEntity.getId();
        this.setName(pricingModelEntity.getName());
        this.setDescription(pricingModelEntity.getDescription());
        this.setCode(pricingModelEntity.getCode());
        this.setLastModified(pricingModelEntity.getLastModified());
        this.setGlobalPricingModel(pricingModelEntity.getGlobalPricingModel());
        this.setCurrency(new Currency(pricingModelEntity.getCurrencyByCurrencyId()));
        List<PricingModelVersion> pricingModelVersions = new ArrayList<>();
        pricingModelVersions.add(new PricingModelVersion(pricingModelVersionEntity));
        this.setPricingModelVersions(pricingModelVersions);
    }
}
