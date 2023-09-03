package za.co.wirecard.channel.backoffice.services;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import za.co.wirecard.channel.backoffice.dto.models.BillingFrequency;
import za.co.wirecard.channel.backoffice.dto.models.requests.*;
import za.co.wirecard.channel.backoffice.dto.models.responses.*;
import za.co.wirecard.channel.backoffice.dto.models.RateStructure;
import za.co.wirecard.channel.backoffice.models.PricingModel;

import java.util.ArrayList;
import java.util.List;

@Service
public interface RateStructureService {

    Page<RateStructure> getAllRateStructures(int page, int limit);
    ArrayList<RateStructure> getHistoricalRateStructures(Long id);
    List<BillingFrequency> getBillingFrequencies();
    GetRateStructureLineItemsResponse getRateStructureLineItems();
    GetRateStructureResponse getRateStructureById(long rateStructureId, long rateStructureVersion);
    GetRateStructureLineItemsResponse getRateStructureLineItemsByRateStructureId(long rateStructureId);
    ResponseEntity<?> createRateStructure(CreateRateStructureRequest createRateStructureRequest);
    ResponseEntity<?> editRateStructure(EditRateStructureRequest editRateStructureRequest);
    ResponseEntity<?> approveRateStructure(Long rateStructureId, Long modifiedById);
    ResponseEntity<?> createPricingModel(Long rateStructureId, CreatePricingModelRequest createPricingModelRequest);
    ResponseEntity<?> editGlobalPricingModel(Long pricingModelId, EditPricingModelRequest editPricingModelRequest);
    PricingModel getPricingModelById(Long pricingModelId);
    RateStructureDropdownResponse rateStructureDropdown();
    PricingModelDropdownResponse pricingModelDropdown(Long rateStructureId);
    ArrayList<ProductDefaultResponse> getAllCurrentProductDefaults();
    ResponseEntity<?> setAllCurrentProductDefaults(ProductDefaultSetRequest productDefaultSetRequest);
    ArrayList<MerchantProductDetail> getMerchantProductDetails(Long merchantId);
    ResponseEntity<?> setMerchantProductDetails(LinkMerchantProductToPricingModelRequest linkMerchantProductToPricingModelRequest);

}
