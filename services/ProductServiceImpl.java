package za.co.wirecard.channel.backoffice.services;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import za.co.wirecard.channel.backoffice.config.Utils;
import za.co.wirecard.channel.backoffice.dto.models.*;
import za.co.wirecard.channel.backoffice.dto.models.requests.SetApplicationProductRequest;
import za.co.wirecard.channel.backoffice.dto.models.responses.GetApplicationProductsResponse;
import za.co.wirecard.channel.backoffice.entities.*;
import za.co.wirecard.channel.backoffice.exceptions.GenericException;
import za.co.wirecard.channel.backoffice.models.MerchantProduct;
import za.co.wirecard.channel.backoffice.repositories.*;
import za.co.wirecard.common.exceptions.MerchantNotFoundException;
import za.co.wirecard.common.exceptions.NotFoundException;

import java.sql.Timestamp;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService{

    private static final Logger logger = LogManager.getLogger(ProductServiceImpl.class);
    private final MerchantProductRepository merchantProductRepository;
    private final ProductRepository productRepository;
    private final ProductSalesGroupRepository productSalesGroupRepository;
    private final MerchantRepository merchantRepository;
    private final ApplicationPaymentTypeRepository applicationPaymentTypeRepository;
    private final ApplicationRepository applicationRepository;
    private final PaymentTypeRepository paymentTypeRepository;
    private final ProductPaymentTypeRepository productPaymentTypeRepository;
    private final ApplicationSecuritySettingRepository applicationSecuritySettingRepository;
    private final SecurityTypeRepository securityTypeRepository;
    private final MerchantProductApplicationRepository merchantProductApplicationRepository;
    private final PricingModelRepository pricingModelRepository;
    private final PricingModelVersionRepository pricingModelVersionRepository;
    private final FixedChargeRepository fixedChargeRepository;
    private final FixedChargeRateStructureLineItemRepository fixedChargeRateStructureLineItemRepository;
    private final VariableChargeRepository variableChargeRepository;
    private final VariableChargeRateStructureLineItemRepository variableChargeRateStructureLineItemRepository;
    private final MinimumBillingRepository minimumBillingRepository;
    private final MinimumBillingRateStructureLineItemRepository minimumBillingRateStructureLineItemRepository;
    private final BackOfficeUserRepository backOfficeUserRepository;
    private final RateStructureLineItemRepository rateStructureLineItemRepository;
    private final CurrencyRepository currencyRepository;
    private final StatusRepository statusRepository;

    public ProductServiceImpl(
            MerchantProductRepository merchantProductRepository,
            ProductRepository productRepository,
            ProductSalesGroupRepository productSalesGroupRepository,
            MerchantRepository merchantRepository,
            ApplicationPaymentTypeRepository applicationPaymentTypeRepository,
            ApplicationRepository applicationRepository,
            PaymentTypeRepository paymentTypeRepository,
            ProductPaymentTypeRepository productPaymentTypeRepository,
            ApplicationSecuritySettingRepository applicationSecuritySettingRepository,
            SecurityTypeRepository securityTypeRepository,
            MerchantProductApplicationRepository merchantProductApplicationRepository,
            PricingModelRepository pricingModelRepository,
            PricingModelVersionRepository pricingModelVersionRepository,
            FixedChargeRepository fixedChargeRepository,
            FixedChargeRateStructureLineItemRepository fixedChargeRateStructureLineItemRepository,
            VariableChargeRepository variableChargeRepository,
            VariableChargeRateStructureLineItemRepository variableChargeRateStructureLineItemRepository,
            MinimumBillingRepository minimumBillingRepository,
            MinimumBillingRateStructureLineItemRepository minimumBillingRateStructureLineItemRepository,
            BackOfficeUserRepository backOfficeUserRepository,
            RateStructureLineItemRepository rateStructureLineItemRepository,
            CurrencyRepository currencyRepository, StatusRepository statusRepository){
        this.merchantProductRepository = merchantProductRepository;
        this.productRepository = productRepository;
        this.productSalesGroupRepository = productSalesGroupRepository;
        this.merchantRepository = merchantRepository;
        this.applicationPaymentTypeRepository = applicationPaymentTypeRepository;
        this.applicationRepository = applicationRepository;
        this.paymentTypeRepository = paymentTypeRepository;
        this.productPaymentTypeRepository = productPaymentTypeRepository;
        this.applicationSecuritySettingRepository = applicationSecuritySettingRepository;
        this.securityTypeRepository = securityTypeRepository;
        this.merchantProductApplicationRepository = merchantProductApplicationRepository;
        this.pricingModelRepository = pricingModelRepository;
        this.pricingModelVersionRepository = pricingModelVersionRepository;
        this.fixedChargeRepository = fixedChargeRepository;
        this.fixedChargeRateStructureLineItemRepository = fixedChargeRateStructureLineItemRepository;
        this.variableChargeRepository = variableChargeRepository;
        this.variableChargeRateStructureLineItemRepository = variableChargeRateStructureLineItemRepository;
        this.minimumBillingRepository = minimumBillingRepository;
        this.minimumBillingRateStructureLineItemRepository = minimumBillingRateStructureLineItemRepository;
        this.backOfficeUserRepository = backOfficeUserRepository;
        this.rateStructureLineItemRepository = rateStructureLineItemRepository;
        this.currencyRepository = currencyRepository;
        this.statusRepository = statusRepository;
    }

    @Override
    public ResponseEntity<MerchantProductData> getMerchantProducts(long merchantId){
        try {
            MerchantEntity merchantEntity;
            MerchantProductData merchantProductData = new MerchantProductData();
            merchantProductData.setProductSalesGroups(new ArrayList<>());
            merchantEntity = merchantRepository.findById(merchantId).orElseThrow(() -> new MerchantNotFoundException(merchantId));

//                List<MerchantProductEntity> merchantProductEntityList = merchantProductRepository.findByMerchantId(merchantEntity.getId());
            List<ProductSalesGroupEntity> productSalesGroupEntityList = productSalesGroupRepository.findAll();
            List<ProductEntity> productEntityList = productRepository.findAllByActive(true);

            //loop through product sales group entities and create the sales group data for the merchant product data array
            for (ProductSalesGroupEntity productSalesGroupEntity : productSalesGroupEntityList) {
                //create the sales group data object
                ProductSalesGroupData newProductSalesGroupData = new ProductSalesGroupData(
                        productSalesGroupEntity.getId(),productSalesGroupEntity.getName(), productSalesGroupEntity.getDescription());
                //loop through products to add them to the product sales group array
                for (ProductEntity productEntity : productEntityList) {
                    //check if the product is part of the product sales grouptransaction/transaction-history
                    if(productEntity.getProductSalesGroupByProductSalesGroupId().getId().longValue() == productSalesGroupEntity.getId().longValue()){
                        ProductData productData = new ProductData();
                        //check that there is a merchant product entity, in order to set the boolean
                        MerchantProductEntity merchantProductEntity = merchantProductRepository.findByMerchantIdAndProductByProductId(merchantEntity.getId(), productEntity);
                        if(merchantProductEntity != null && merchantProductEntity.getActive()){
                            productData.setActiveForMerchant(true);
                        } else {
                            productData.setActiveForMerchant(false);
                        }
                        //then add the other product data properties
                        productData.setId(productEntity.getId());
                        productData.setName(productEntity.getName());
                        productData.setDescription(productEntity.getDescription());
                        //assert merchantProductEntity != null;
                        if (merchantProductEntity != null) {
                            productData.setStatusId(merchantProductEntity.getStatusByStatusId());
                        }
                        newProductSalesGroupData.getProducts().add(productData);
                    }
                }
                merchantProductData.getProductSalesGroups().add(newProductSalesGroupData);
            }
            return ResponseEntity.ok(merchantProductData);
        } catch (Exception e) {
            logger.error(e);
            throw new GenericException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, e.getLocalizedMessage());
        }
    }

    @Override
    public MerchantProduct getMerchantProductStatus(long merchantId, long productId){
        ProductEntity productEntity = productRepository.findById(productId);
        MerchantProductEntity merchantProductEntity = merchantProductRepository.findByMerchantIdAndProductByProductId(merchantId,productEntity);
        return new MerchantProduct(merchantProductEntity);
    }

    @Override
    public ResponseEntity<?> setMerchantProducts(long merchantId, MerchantProductData merchantProductData){
//        try {
            MerchantEntity merchantEntity;
            logger.info("Exception here | 1");
            merchantEntity = merchantRepository.findById(merchantId).orElseThrow(() -> new MerchantNotFoundException(merchantId));
            //foreach sales group data in merchant product data
            logger.info("Exception here | 2");



            for (ProductSalesGroupData productSalesGroupData: merchantProductData.getProductSalesGroups()) {
                //loop through the products array and check if they are active for merchant
                for (ProductData productData : productSalesGroupData.getProducts()) {
                    //check if active for merchant. either way, try to find a merchant product entity record and update it if found
                    if(productData.isActiveForMerchant()){
                        ProductEntity productEntity1 = productRepository.getOne(productData.getId());
                        MerchantProductEntity merchantProductEntityTrue = merchantProductRepository.findByMerchantIdAndProductByProductId(merchantEntity.getId(), productEntity1);
                        //if there is an entity, set active to true. if not, create one and set active to true.
                        if(merchantProductEntityTrue != null){
                            merchantProductEntityTrue.setActive(true);
                            merchantProductRepository.save(merchantProductEntityTrue);
                            setApplicationPaymentTypeEntity(true, merchantEntity.getId(), merchantProductEntityTrue);
                        } else {
                            MerchantProductEntity newMerchantProductEntityTrue = new MerchantProductEntity();
                            newMerchantProductEntityTrue.setProductByProductId(productEntity1);
                            newMerchantProductEntityTrue.setMerchantId(merchantEntity.getId());
                            newMerchantProductEntityTrue.setActive(true);
                            newMerchantProductEntityTrue.setLastModified(new Timestamp(new Date().getTime()));
                            newMerchantProductEntityTrue.setDateAdded(new Timestamp(new Date().getTime()));
                            StatusEntity statusEntity = statusRepository.findByCode(Utils.MERCHANT_STATUS_DEVELOPING_CODE);
                            newMerchantProductEntityTrue.setStatusByStatusId(statusEntity);
                            merchantProductRepository.save(newMerchantProductEntityTrue);
                            setApplicationPaymentTypeEntity(true, merchantEntity.getId(), newMerchantProductEntityTrue);
                        }
                    } else {
                        ProductEntity productEntity1 = productRepository.getOne(productData.getId());
                        MerchantProductEntity merchantProductEntityFalse = merchantProductRepository.findByMerchantIdAndProductByProductId(merchantEntity.getId(), productEntity1);
                        if(merchantProductEntityFalse != null){
                            merchantProductEntityFalse.setActive(false);
                            merchantProductRepository.save(merchantProductEntityFalse);
                            setApplicationPaymentTypeEntity(false, merchantEntity.getId(), merchantProductEntityFalse);
                        }
                    }
                }
            }


            // Configure products based on result
            // Fraud -> Fraud
            // 1Click -> Tokenization

            List<ApplicationEntity> applicationEntities = applicationRepository.findAllByMerchantId(merchantId);
            List<SecurityTypeEntity> securityTypeEntities = securityTypeRepository.findAll();

            applicationEntities
                    .forEach(applicationEntity -> securityTypeEntities
                            .forEach(securityTypeEntity -> {
                                if (securityTypeEntity.getCode().equalsIgnoreCase("1Click")) {
                                    ProductEntity productEntity1 = productRepository.findByProductCode("TOKENIZATION");
                                    MerchantProductEntity merchantProduct = merchantProductRepository.findByMerchantIdAndProductByProductId(merchantEntity.getId(), productEntity1);
                                    if (merchantProduct != null) {
                                        this.setApplicationSecuritySetting("SECURITY", applicationEntity, securityTypeEntity, merchantProduct.getActive());
                                    }
                                } else if (securityTypeEntity.getCode().equalsIgnoreCase("Fraud")) {
                                    ProductEntity productEntity1 = productRepository.findByProductCode("FRAUD");
                                    MerchantProductEntity merchantProduct = merchantProductRepository.findByMerchantIdAndProductByProductId(merchantEntity.getId(), productEntity1);
                                    if (merchantProduct != null) {
                                        this.setApplicationSecuritySetting("SECURITY", applicationEntity, securityTypeEntity, merchantProduct.getActive());
                                    }
                                }
                            }));


            return ResponseEntity.ok().build();
//        } catch (Exception e) {
//            logger.error(e);
//            throw new GenericException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, e.getLocalizedMessage());
//        }

    }

    @Override
    public ResponseEntity<?> getApplicationProducts(long applicationId) {
        ApplicationEntity applicationEntity = applicationRepository.getOne(applicationId);
        List<MerchantProductApplicationEntity> merchantProductApplicationEntities = merchantProductApplicationRepository.findAllByApplicationByApplicationId(applicationEntity);
        List<GetApplicationProductsResponse> getApplicationProductsResponses = new ArrayList<>();
        for (MerchantProductApplicationEntity merchantProductApplicationEntity: merchantProductApplicationEntities) {
            getApplicationProductsResponses.add(new GetApplicationProductsResponse(merchantProductApplicationEntity));
        }
        return ResponseEntity.ok(getApplicationProductsResponses);
    }

    @Override
    public ResponseEntity<?> setApplicationProducts(long applicationId, long backOfficeUserId, List<SetApplicationProductRequest> setApplicationProductRequestList) {
        logger.info("Received request to set Merchant Product Application for Application ID | " + applicationId);
        ApplicationEntity applicationEntity = applicationRepository.findById(applicationId).
                orElseThrow(() -> new NotFoundException("Application not found for ID | " + applicationId));
        List<MerchantProductApplicationEntity> merchantProductApplicationEntities = merchantProductApplicationRepository.findAllByApplicationByApplicationId(applicationEntity);
        List<MerchantProductApplicationEntity> foundList = new ArrayList<>();

        for (SetApplicationProductRequest setApplicationProductRequest: setApplicationProductRequestList) {
            MerchantProductApplicationEntity merchantProductApplication = new MerchantProductApplicationEntity();
            ProductEntity productEntity = null;
            MerchantProductEntity merchantProduct = null;
            if (setApplicationProductRequest.getProductId() != null) {
                productEntity = productRepository.findById(setApplicationProductRequest.getProductId()).
                        orElseThrow(() -> new NotFoundException("Product not found for ID | " + setApplicationProductRequest.getProductId()));
                merchantProduct = merchantProductRepository.findByMerchantIdAndProductByProductId(applicationEntity.getMerchantId(), productEntity);
            }

            boolean found = false;
            for (MerchantProductApplicationEntity merchantProductApplicationEntity: merchantProductApplicationEntities) {

                if (merchantProduct != null) {
                    if (merchantProductApplicationEntity.getMerchantProductByMerchantProductId() != null) {
                        if (Objects.equals(merchantProductApplicationEntity.getMerchantProductByMerchantProductId().getId(), merchantProduct.getId())) {
                            merchantProductApplication = merchantProductApplicationEntity;
                            merchantProduct = merchantProductApplicationEntity.getMerchantProductByMerchantProductId();
                            foundList.add(merchantProductApplicationEntity);
                            found = true;
                        }
                    }
                } else {
                    if (merchantProductApplicationEntity.getMerchantProductByMerchantProductId() == null) {
                        merchantProductApplication = merchantProductApplicationEntity;
                        foundList.add(merchantProductApplicationEntity);
                        found = true;
                    }
                }

            }
            if (!found) {
                if (merchantProduct != null) {
                    logger.info("No Merchant Product Application found for Merchant Product ID | " + merchantProduct.getId() + " | Creating new Merchant Product Application");
                    merchantProductApplication.setApplicationByApplicationId(applicationEntity);
                    merchantProductApplication.setMerchantProductByMerchantProductId(merchantProduct);
                } else {
                    logger.info("No Merchant Product Application found for Application UID | " + applicationEntity.getApplicationUid() + " | Creating new Merchant Product Application");
                    merchantProductApplication.setApplicationByApplicationId(applicationEntity);
                }
            }

            PricingModelEntity pricingModelEntity = pricingModelRepository.findById(setApplicationProductRequest.getSelectedPricingModelId()).
                    orElseThrow(() -> new NotFoundException("Pricing Model not found for ID | " + setApplicationProductRequest.getSelectedPricingModelId()));
            merchantProductApplication.setSelectedPricingModel(pricingModelEntity);

            //check if customLineItems is not null, if not then save the new custom pricing model and set in merchant product entity.
            if(setApplicationProductRequest.getCustomLineItems() != null) {
                //if there is a custom pricing model already existing, handle differently to if there is not.
                if(merchantProductApplication.getCustomPricingModel() != null) {
                    //logic to add a new version to custom pricing model
                    MerchantProductApplicationEntity finalMerchantProductApplication = merchantProductApplication;
                    PricingModelEntity customPricingModelEntity =
                            pricingModelRepository.findById(merchantProductApplication.getCustomPricingModel()).orElseThrow(() ->
                                    new GenericException("Pricing Model Not Found", HttpStatus.INTERNAL_SERVER_ERROR, "No Pricing Model found for id | " + finalMerchantProductApplication.getCustomPricingModel()));
                    customPricingModelEntity.setLastModified(new Timestamp(new Date().getTime()));
                    PricingModelEntity savedPricingModel = pricingModelRepository.save(customPricingModelEntity);
                    pricingModelRepository.flush();

                    PricingModelVersionEntity pricingModelVersionEntity = pricingModelVersionRepository.findFirstByPricingModelIdOrderByVersionDesc(savedPricingModel.getId());
                    PricingModelVersionEntity newPricingModelVersionEntity = new PricingModelVersionEntity();
                    newPricingModelVersionEntity.setVersion(pricingModelVersionEntity.getVersion() + 1);
                    newPricingModelVersionEntity.setBackOfficeUserByLastModifiedBy(backOfficeUserRepository.findOneById(backOfficeUserId));
                    newPricingModelVersionEntity.setLastModified(new Timestamp(new Date().getTime()));
                    newPricingModelVersionEntity.setPricingModelId(savedPricingModel.getId());
                    PricingModelVersionEntity savedPricingModelVersionEntity = pricingModelVersionRepository.save(newPricingModelVersionEntity);
                    pricingModelVersionRepository.flush();

                    for (FixedCharge fixedCharge : setApplicationProductRequest.getCustomLineItems().getFixedChargeList()) {
                        FixedChargeEntity fixedChargeEntity = new FixedChargeEntity();
                        fixedChargeEntity.setPricingModelVersionId(savedPricingModelVersionEntity.getId());
                        fixedChargeEntity.setVatable(fixedCharge.isVatable());
                        fixedChargeEntity.setVatInclusive(fixedCharge.isVatInclusive());
                        FixedChargeEntity savedFixedChargeEntity = fixedChargeRepository.save(fixedChargeEntity);
                        fixedChargeRepository.flush();

                        FixedChargeRateStructureLineItemEntity fixedChargeRateStructureLineItemEntity = new FixedChargeRateStructureLineItemEntity();
                        fixedChargeRateStructureLineItemEntity.setValue(fixedCharge.getValue());
                        fixedChargeRateStructureLineItemEntity.setFixedChargeId(savedFixedChargeEntity.getId());
                        RateStructureLineItemEntity rateStructureLineItemEntity = rateStructureLineItemRepository.findByCode(fixedCharge.getRateStructureLineItem().getCode());
                        fixedChargeRateStructureLineItemEntity.setRateStructureLineItemByRateStructureLineItemId(rateStructureLineItemEntity);
                        FixedChargeRateStructureLineItemEntity savedFixedChargeRateStructureLineItemEntity = fixedChargeRateStructureLineItemRepository.save(fixedChargeRateStructureLineItemEntity);
                        fixedChargeRateStructureLineItemRepository.flush();
                    }

                    for (VariableCharge variableCharge : setApplicationProductRequest.getCustomLineItems().getVariableChargeList()) {
                        VariableChargeEntity variableChargeEntity = new VariableChargeEntity();
                        variableChargeEntity.setPricingModelVersionId(savedPricingModelVersionEntity.getId());
                        variableChargeEntity.setVatable(variableCharge.isVatable());
                        variableChargeEntity.setVatInclusive(variableCharge.isVatInclusive());
                        variableChargeEntity.setFromAmount(variableCharge.getFromAmount());
                        variableChargeEntity.setToAmount(variableCharge.getToAmount());
                        VariableChargeEntity savedVariableChargeEntity = variableChargeRepository.save(variableChargeEntity);
                        variableChargeRepository.flush();

                        VariableChargeRateStructureLineItemEntity variableChargeRateStructureLineItemEntity = new VariableChargeRateStructureLineItemEntity();
                        variableChargeRateStructureLineItemEntity.setValue(variableCharge.getValue());
                        variableChargeRateStructureLineItemEntity.setVariableChargeId(savedVariableChargeEntity.getId());
                        RateStructureLineItemEntity rateStructureLineItemEntity = rateStructureLineItemRepository.findByCode(variableCharge.getRateStructureLineItem().getCode());
                        variableChargeRateStructureLineItemEntity.setRateStructureLineItemByRateStructureLineItemId(rateStructureLineItemEntity);
                        VariableChargeRateStructureLineItemEntity savedVariableChargeRateStructureLineItemEntity = variableChargeRateStructureLineItemRepository.save(variableChargeRateStructureLineItemEntity);
                        variableChargeRateStructureLineItemRepository.flush();
                    }

                    if(setApplicationProductRequest.getCustomLineItems().getMinimumBilling() != null) {
                        MinimumBillingEntity minimumBillingEntity = new MinimumBillingEntity();
                        minimumBillingEntity.setValue(setApplicationProductRequest.getCustomLineItems().getMinimumBilling().getValue());
                        minimumBillingEntity.setVatable(setApplicationProductRequest.getCustomLineItems().getMinimumBilling().isVatable());
                        minimumBillingEntity.setVatInclusive(setApplicationProductRequest.getCustomLineItems().getMinimumBilling().isVatInclusive());
                        minimumBillingEntity.setPricingModelVersionId(savedPricingModelVersionEntity.getId());
                        MinimumBillingEntity savedMinimumBillingEntity = minimumBillingRepository.save(minimumBillingEntity);
                        minimumBillingRepository.flush();

                        for (RateStructureLineItem rateStructureLineItem : setApplicationProductRequest.getCustomLineItems().getMinimumBilling().getRateStructureLineItem()) {
                            MinimumBillingRateStructureLineItemEntity minimumBillingRateStructureLineItemEntity = new MinimumBillingRateStructureLineItemEntity();
                            minimumBillingRateStructureLineItemEntity.setMinimumBillingId(savedMinimumBillingEntity.getId());
                            RateStructureLineItemEntity rateStructureLineItemEntity = rateStructureLineItemRepository.findByCode(rateStructureLineItem.getCode());
                            minimumBillingRateStructureLineItemEntity.setRateStructureLineItemByRateStructureLineItemId(rateStructureLineItemEntity);
                            MinimumBillingRateStructureLineItemEntity savedMinimumBillingRateStructureLineItemEntity = minimumBillingRateStructureLineItemRepository.save(minimumBillingRateStructureLineItemEntity);
                            minimumBillingRateStructureLineItemRepository.flush();
                        }
                    }

                } else {
                    //logic to create a completely new pricing model entity
                    //note: currency inherited from selected pricing model
                    PricingModelEntity selectedPricingModelEntity = pricingModelRepository.findById(setApplicationProductRequest.getSelectedPricingModelId()).orElseThrow(() -> new GenericException("Pricing Model Not Found", HttpStatus.NOT_FOUND, "Error finding pricing model with ID | " + setApplicationProductRequest.getSelectedPricingModelId()));
                    PricingModelEntity newCustomPricingModel = new PricingModelEntity();

                    try {
                        if (productEntity != null) {
                            newCustomPricingModel.setName(applicationEntity.getMerchantByMerchantId().getCompanyName() + " custom pricing model for product: " + productEntity.getName());
                            newCustomPricingModel.setDescription("The custom pricing model generated for " + applicationEntity.getMerchantByMerchantId().getCompanyName() + " for modified pricing values for the product: " + productEntity.getName());
                        } else {
                            newCustomPricingModel.setName(applicationEntity.getMerchantByMerchantId().getCompanyName() + " custom pricing model for application: " + applicationEntity.getName());
                            newCustomPricingModel.setDescription("The custom pricing model generated for " + applicationEntity.getMerchantByMerchantId().getCompanyName() + " for modified pricing values for the application: " + applicationEntity.getName());
                        }
                        newCustomPricingModel.setCode(UUID.randomUUID().toString());
                        newCustomPricingModel.setGlobalPricingModel(false);
                        newCustomPricingModel.setCurrencyByCurrencyId(currencyRepository.findByCode(selectedPricingModelEntity.getCurrencyByCurrencyId().getCode()));
                        newCustomPricingModel.setRateStructureId(selectedPricingModelEntity.getRateStructureId());
                        newCustomPricingModel.setLastModified(new Timestamp(new Date().getTime()));
                        PricingModelEntity savedPricingModelEntity = pricingModelRepository.save(newCustomPricingModel);
                        pricingModelRepository.flush();

                        PricingModelVersionEntity pricingModelVersionEntity = new PricingModelVersionEntity();
                        pricingModelVersionEntity.setPricingModelId(savedPricingModelEntity.getId());
                        pricingModelVersionEntity.setVersion(Long.valueOf(1));
                        pricingModelVersionEntity.setLastModified(new Timestamp(new Date().getTime()));
                        pricingModelVersionEntity.setBackOfficeUserByLastModifiedBy(backOfficeUserRepository.findOneById(backOfficeUserId));
                        PricingModelVersionEntity savedPricingModelVersionEntity = pricingModelVersionRepository.save(pricingModelVersionEntity);
                        pricingModelVersionRepository.flush();

                        for (FixedCharge fixedCharge : setApplicationProductRequest.getCustomLineItems().getFixedChargeList()) {
                            FixedChargeEntity fixedChargeEntity = new FixedChargeEntity();
                            fixedChargeEntity.setPricingModelVersionId(savedPricingModelVersionEntity.getId());
                            fixedChargeEntity.setVatable(fixedCharge.isVatable());
                            fixedChargeEntity.setVatInclusive(fixedCharge.isVatInclusive());
                            FixedChargeEntity savedFixedChargeEntity = fixedChargeRepository.save(fixedChargeEntity);
                            fixedChargeRepository.flush();

                            FixedChargeRateStructureLineItemEntity fixedChargeRateStructureLineItemEntity = new FixedChargeRateStructureLineItemEntity();
                            fixedChargeRateStructureLineItemEntity.setValue(fixedCharge.getValue());
                            fixedChargeRateStructureLineItemEntity.setFixedChargeId(savedFixedChargeEntity.getId());
                            RateStructureLineItemEntity rateStructureLineItemEntity = rateStructureLineItemRepository.findByCode(fixedCharge.getRateStructureLineItem().getCode());
                            fixedChargeRateStructureLineItemEntity.setRateStructureLineItemByRateStructureLineItemId(rateStructureLineItemEntity);
                            FixedChargeRateStructureLineItemEntity savedFixedChargeRateStructureLineItemEntity = fixedChargeRateStructureLineItemRepository.save(fixedChargeRateStructureLineItemEntity);
                            fixedChargeRateStructureLineItemRepository.flush();
                        }

                        for (VariableCharge variableCharge : setApplicationProductRequest.getCustomLineItems().getVariableChargeList()) {
                            VariableChargeEntity variableChargeEntity = new VariableChargeEntity();
                            variableChargeEntity.setPricingModelVersionId(savedPricingModelVersionEntity.getId());
                            variableChargeEntity.setVatable(variableCharge.isVatable());
                            variableChargeEntity.setVatInclusive(variableCharge.isVatInclusive());
                            variableChargeEntity.setFromAmount(variableCharge.getFromAmount());
                            variableChargeEntity.setToAmount(variableCharge.getToAmount());
                            VariableChargeEntity savedVariableChargeEntity = variableChargeRepository.save(variableChargeEntity);
                            variableChargeRepository.flush();

                            VariableChargeRateStructureLineItemEntity variableChargeRateStructureLineItemEntity = new VariableChargeRateStructureLineItemEntity();
                            variableChargeRateStructureLineItemEntity.setValue(variableCharge.getValue());
                            variableChargeRateStructureLineItemEntity.setVariableChargeId(savedVariableChargeEntity.getId());
                            RateStructureLineItemEntity rateStructureLineItemEntity = rateStructureLineItemRepository.findByCode(variableCharge.getRateStructureLineItem().getCode());
                            variableChargeRateStructureLineItemEntity.setRateStructureLineItemByRateStructureLineItemId(rateStructureLineItemEntity);
                            VariableChargeRateStructureLineItemEntity savedVariableChargeRateStructureLineItemEntity = variableChargeRateStructureLineItemRepository.save(variableChargeRateStructureLineItemEntity);
                            variableChargeRateStructureLineItemRepository.flush();
                        }

                        if(setApplicationProductRequest.getCustomLineItems().getMinimumBilling() != null) {
                            MinimumBillingEntity minimumBillingEntity = new MinimumBillingEntity();
                            minimumBillingEntity.setValue(setApplicationProductRequest.getCustomLineItems().getMinimumBilling().getValue());
                            minimumBillingEntity.setVatable(setApplicationProductRequest.getCustomLineItems().getMinimumBilling().isVatable());
                            minimumBillingEntity.setVatInclusive(setApplicationProductRequest.getCustomLineItems().getMinimumBilling().isVatInclusive());
                            minimumBillingEntity.setPricingModelVersionId(savedPricingModelVersionEntity.getId());
                            MinimumBillingEntity savedMinimumBillingEntity = minimumBillingRepository.save(minimumBillingEntity);
                            minimumBillingRepository.flush();

                            for (RateStructureLineItem rateStructureLineItem : setApplicationProductRequest.getCustomLineItems().getMinimumBilling().getRateStructureLineItem()) {
                                MinimumBillingRateStructureLineItemEntity minimumBillingRateStructureLineItemEntity = new MinimumBillingRateStructureLineItemEntity();
                                minimumBillingRateStructureLineItemEntity.setMinimumBillingId(savedMinimumBillingEntity.getId());
                                RateStructureLineItemEntity rateStructureLineItemEntity = rateStructureLineItemRepository.findByCode(rateStructureLineItem.getCode());
                                minimumBillingRateStructureLineItemEntity.setRateStructureLineItemByRateStructureLineItemId(rateStructureLineItemEntity);
                                MinimumBillingRateStructureLineItemEntity savedMinimumBillingRateStructureLineItemEntity = minimumBillingRateStructureLineItemRepository.save(minimumBillingRateStructureLineItemEntity);
                                minimumBillingRateStructureLineItemRepository.flush();
                            }
                        }

                    } catch(Exception e) {
                        throw new GenericException("Error saving Pricing Model", HttpStatus.INTERNAL_SERVER_ERROR, "Error saving Pricing Model: " + e.getMessage());
                    }

                    PricingModelEntity savedCustomPricingModel = pricingModelRepository.save(newCustomPricingModel);
                    pricingModelRepository.flush();
                    merchantProductApplication.setCustomPricingModel(savedCustomPricingModel.getId());
                }

            }

            merchantProductApplicationRepository.save(merchantProductApplication);
            logger.info( merchantProduct != null
                    ? "Successfully saved Merchant Product Application for Merchant Product ID | " + merchantProduct.getId()
                    : "Successfully saved Merchant Product Application for Application UID | " + applicationEntity.getApplicationUid());
        }

        if (!foundList.isEmpty()) {
            merchantProductApplicationEntities.removeAll(foundList);
        }
        if (!merchantProductApplicationEntities.isEmpty()) {
            logger.info("Deleting deactivated Merchant Product Applications");
            merchantProductApplicationRepository.deleteAll(merchantProductApplicationEntities);
        }
        logger.info("Successfully processed request to set Merchant Product Application for Application ID | " + applicationId);
        return ResponseEntity.ok().build();
    }

    private void setApplicationSecuritySetting(String type, ApplicationEntity applicationEntity, SecurityTypeEntity securityTypeEntity, boolean active) {
        logger.info(String.format("Set %s | for application %s | for security setting %s | to %s", type, applicationEntity.getId(), securityTypeEntity.getId(), active));
        if (type.equalsIgnoreCase("SECURITY")) {
            ApplicationSecuritySettingEntity applicationSecuritySettingEntity = applicationSecuritySettingRepository.findByApplicationIdAndSecurityTypeId(applicationEntity.getId(), securityTypeEntity.getId());
            if (applicationSecuritySettingEntity != null) {
                applicationSecuritySettingEntity.setIsActive(active);
                applicationSecuritySettingRepository.save(applicationSecuritySettingEntity);
            } else {
                ApplicationSecuritySettingEntity applicationSecuritySettingEntity1 = new ApplicationSecuritySettingEntity();
                applicationSecuritySettingEntity1.setApplicationId(applicationEntity.getId());
                applicationSecuritySettingEntity1.setSecurityTypeId(securityTypeEntity.getId());
                applicationSecuritySettingEntity1.setIsActive(active);
                applicationSecuritySettingRepository.save(applicationSecuritySettingEntity1);
            }
        }
    }

    private void setApplicationPaymentTypeEntity(boolean isActive, long merchantId, MerchantProductEntity merchantProductEntity) {
        // Find all applications for given merchantId passed in params.
        List<ApplicationEntity> applicationEntities = applicationRepository.findAllByMerchantId(merchantId);
        // Find out which products belong to which payment types
        List<ProductPaymentTypeEntity> productPaymentTypeEntities = productPaymentTypeRepository.findAllByProductEntityByProductId(merchantProductEntity.getProductByProductId());
        for (ProductPaymentTypeEntity productPaymentTypeEntity: productPaymentTypeEntities) {
            // Find all application payment type entities which already exist for this merchant.
            List<ApplicationPaymentTypeEntity> applicationPaymentTypeEntity =
                    applicationPaymentTypeRepository.findByApplicationByApplicationIdInAndPaymentTypeByPaymentTypeId(
                            applicationEntities,
                            productPaymentTypeEntity.getPaymentTypeEntityByPaymentTypeId()
                    );
            if (applicationPaymentTypeEntity != null) {
                List<ApplicationPaymentTypeEntity> newApplicationPaymentTypeEntityList = applicationPaymentTypeEntity
                        .stream()
                        .map(applicationPaymentTypeEntity1 -> {
                            applicationPaymentTypeEntity1.setActive(isActive);
                            return applicationPaymentTypeEntity1;
                        })
                        .collect(Collectors.toList());
                   applicationPaymentTypeRepository.saveAll(newApplicationPaymentTypeEntityList);
            }
//            else {
//                ApplicationPaymentTypeEntity applicationPaymentTypeEntity1 = new ApplicationPaymentTypeEntity();
//                applicationPaymentTypeEntity1.setApplicationByApplicationId();
//                applicationPaymentTypeEntity1.setPaymentTypeByPaymentTypeId();
//                applicationPaymentTypeEntity1.setApplicationId();
//                applicationPaymentTypeEntity1.setPaymentTypeId();
//                applicationPaymentTypeEntity1.setInterfaceId();
//                applicationPaymentTypeEntity1.setInterfaceByInterfaceId();
//                applicationPaymentTypeEntity1.setActive(true);
//                applicationPaymentTypeRepository.save(applicationPaymentTypeEntity1);
//            }
        }
    }

}
