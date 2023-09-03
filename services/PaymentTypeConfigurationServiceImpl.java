package za.co.wirecard.channel.backoffice.services;


import org.apache.commons.lang3.EnumUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;
import za.co.wirecard.channel.backoffice.config.UtilityService;
import za.co.wirecard.channel.backoffice.config.Utils;
import za.co.wirecard.channel.backoffice.constants.*;
import za.co.wirecard.channel.backoffice.dto.models.*;
import za.co.wirecard.channel.backoffice.dto.models.requests.*;
import za.co.wirecard.channel.backoffice.dto.models.responses.*;
import za.co.wirecard.channel.backoffice.entities.*;
import za.co.wirecard.channel.backoffice.exceptions.ExceptionExtender;
import za.co.wirecard.channel.backoffice.exceptions.GenericException;
import za.co.wirecard.channel.backoffice.models.*;
import za.co.wirecard.channel.backoffice.models.Currency;
import za.co.wirecard.channel.backoffice.repositories.*;
import za.co.wirecard.common.exceptions.MerchantNotFoundException;
import za.co.wirecard.channel.backoffice.dto.models.ApplicationPaymentTypeStatus;

import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;


@Service
public class PaymentTypeConfigurationServiceImpl implements PaymentTypeConfigurationService {

    @Value("${api.paymenttypeconfigmanagement.url}")
    private String paymentTypeConfigManagementUrl;

    private final UtilityService utilityService;
    private final RestTemplate restTemplate;

    private final ProductRepository productRepository;
    private final MerchantProductRepository merchantProductRepository;
    private final PaymentTypeRepository paymentTypeRepository;
    private final GatewayRepository gatewayRepository;
    private final SecurityMethodRepository securityMethodRepository;
    private final ApplicationRepository applicationRepository;
    private final InterfaceRepository interfaceRepository;
    private final ApplicationPaymentTypeRepository applicationPaymentTypeRepository;
    private final ZapperInterfaceConfigRepository zapperInterfaceConfigRepository;
    private final MasterPassConfigRepository masterPassConfigRepository;
    private final MobicredInterfaceConfigRepository mobicredInterfaceConfigRepository;
    private final EftInterfaceConfigRepository eftInterfaceConfigRepository;
    private final DisbursementConfigRepository disbursementConfigRepository;
    private final ProductPaymentTypeRepository productPaymentTypeRepository;
    private final CurrencyRepository currencyRepository;
    private final CountryRepository countryRepository;
    private final TradingCurrencyRepository tradingCurrencyRepository;
    private final CardTypeRepository cardTypeRepository;
    private final MerchantTypeRepository merchantTypeRepository;
    private final TokenizationMethodRepository tokenizationMethodRepository;
    private final EciRepository eciRepository;
    private final MerchantRepository merchantRepository;
    private final TdsMethodRepository tdsMethodRepository;
    private final CardFlowRepository cardFlowRepository;
    private final CardConfigStatusRepository cardConfigStatusRepository;
    private final CardConfigurationRepository cardConfigurationRepository;
    private final CardTypeGroupRepository cardTypeGroupRepository;
    private final CardConfigurationEciRepository cardConfigurationEciRepository;
    private final CardConfigurationTokenizationMethodRepository cardConfigurationTokenizationMethodRepository;
    private final StandardBankConfigurationRepository standardBankConfigurationRepository;
    private final BankWindhoekConfigurationRepository bankWindhoekConfigurationRepository;
    private final AbsaConfigurationRepository absaConfigurationRepository;
    private final FnbConfigurationRepository fnbConfigurationRepository;
    private final IveriConfigurationRepository iveriConfigurationRepository;
    private final VacpConfigurationRepository vacpConfigurationRepository;
    private final BwConfigurationRepository bwConfigurationRepository;
    private final PlanetConfigurationRepository planetConfigurationRepository;
    private final BankservConfigurationRepository bankservConfigurationRepository;
    private final CardinalConfigurationRepository cardinalConfigurationRepository;
    private final OzowInterfaceConfigRepository ozowInterfaceConfigRepository;
    private final StitchEftBanksRepository stitchEftBanksRepository;
    private final StitchInterfaceConfigRepository stitchInterfaceConfigRepository;
    private final StitchEftBankAccountRepository stitchEftBankAccountRepository;

    private static final Logger logger = LogManager.getLogger(PaymentTypeConfigurationService.class);
    private final StatusRepository statusRepository;

    public PaymentTypeConfigurationServiceImpl(UtilityService utilityService, RestTemplateBuilder restTemplateBuilder,
                                               ProductRepository productRepository, MerchantProductRepository merchantProductRepository,
                                               PaymentTypeRepository paymentTypeRepository, GatewayRepository gatewayRepository,
                                               SecurityMethodRepository securityMethodRepository, ApplicationRepository applicationRepository,
                                               InterfaceRepository interfaceRepository, ApplicationPaymentTypeRepository applicationPaymentTypeRepository,
                                               ZapperInterfaceConfigRepository zapperInterfaceConfigRepository, MasterPassConfigRepository masterPassConfigRepository,
                                               MobicredInterfaceConfigRepository mobicredInterfaceConfigRepository, EftInterfaceConfigRepository eftInterfaceConfigRepository,
                                               DisbursementConfigRepository disbursementConfigRepository, ProductPaymentTypeRepository productPaymentTypeRepository,
                                               CurrencyRepository currencyRepository, CountryRepository countryRepository,
                                               TradingCurrencyRepository tradingCurrencyRepository, CardTypeRepository cardTypeRepository,
                                               MerchantTypeRepository merchantTypeRepository, TokenizationMethodRepository tokenizationMethodRepository,
                                               EciRepository eciRepository, MerchantRepository merchantRepository,
                                               TdsMethodRepository tdsMethodRepository, CardFlowRepository cardFlowRepository,
                                               CardConfigStatusRepository cardConfigStatusRepository, CardConfigurationRepository cardConfigurationRepository,
                                               CardTypeGroupRepository cardTypeGroupRepository, CardConfigurationEciRepository cardConfigurationEciRepository,
                                               CardConfigurationTokenizationMethodRepository cardConfigurationTokenizationMethodRepository,
                                               StandardBankConfigurationRepository standardBankConfigurationRepository, AbsaConfigurationRepository absaConfigurationRepository,
                                               BankWindhoekConfigurationRepository bankWindhoekConfigurationRepository,
                                               FnbConfigurationRepository fnbConfigurationRepository, IveriConfigurationRepository iveriConfigurationRepository,
                                               VacpConfigurationRepository vacpConfigurationRepository, BwConfigurationRepository bwConfigurationRepository, PlanetConfigurationRepository planetConfigurationRepository, BankservConfigurationRepository bankservConfigurationRepository, CardinalConfigurationRepository cardinalConfigurationRepository, OzowInterfaceConfigRepository ozowInterfaceConfigRepository, StitchEftBanksRepository stitchEftBanksRepository, StitchInterfaceConfigRepository stitchInterfaceConfigRepository, StitchEftBankAccountRepository stitchEftBankAccountRepository, StatusRepository statusRepository) {
        this.utilityService = utilityService;
        this.restTemplate = restTemplateBuilder.build();
        this.productRepository = productRepository;
        this.merchantProductRepository = merchantProductRepository;
        this.paymentTypeRepository = paymentTypeRepository;
        this.gatewayRepository = gatewayRepository;
        this.securityMethodRepository = securityMethodRepository;
        this.applicationRepository = applicationRepository;
        this.interfaceRepository = interfaceRepository;
        this.applicationPaymentTypeRepository = applicationPaymentTypeRepository;
        this.zapperInterfaceConfigRepository = zapperInterfaceConfigRepository;
        this.masterPassConfigRepository = masterPassConfigRepository;
        this.mobicredInterfaceConfigRepository = mobicredInterfaceConfigRepository;
        this.eftInterfaceConfigRepository = eftInterfaceConfigRepository;
        this.disbursementConfigRepository = disbursementConfigRepository;
        this.productPaymentTypeRepository = productPaymentTypeRepository;
        this.currencyRepository = currencyRepository;
        this.countryRepository = countryRepository;
        this.tradingCurrencyRepository = tradingCurrencyRepository;
        this.cardTypeRepository = cardTypeRepository;
        this.merchantTypeRepository = merchantTypeRepository;
        this.tokenizationMethodRepository = tokenizationMethodRepository;
        this.eciRepository = eciRepository;
        this.tdsMethodRepository = tdsMethodRepository;
        this.cardFlowRepository = cardFlowRepository;
        this.cardConfigStatusRepository = cardConfigStatusRepository;
        this.cardConfigurationRepository = cardConfigurationRepository;
        this.cardTypeGroupRepository = cardTypeGroupRepository;
        this.cardConfigurationEciRepository = cardConfigurationEciRepository;
        this.cardConfigurationTokenizationMethodRepository = cardConfigurationTokenizationMethodRepository;
        this.standardBankConfigurationRepository = standardBankConfigurationRepository;
        this.bankWindhoekConfigurationRepository = bankWindhoekConfigurationRepository;

        this.absaConfigurationRepository = absaConfigurationRepository;
        this.fnbConfigurationRepository = fnbConfigurationRepository;
        this.iveriConfigurationRepository = iveriConfigurationRepository;
        this.vacpConfigurationRepository = vacpConfigurationRepository;
        this.bwConfigurationRepository = bwConfigurationRepository;
        this.merchantRepository = merchantRepository;
        this.planetConfigurationRepository = planetConfigurationRepository;
        this.bankservConfigurationRepository = bankservConfigurationRepository;
        this.cardinalConfigurationRepository = cardinalConfigurationRepository;
        this.ozowInterfaceConfigRepository = ozowInterfaceConfigRepository;
        this.stitchEftBanksRepository = stitchEftBanksRepository;
        this.stitchInterfaceConfigRepository = stitchInterfaceConfigRepository;
        this.stitchEftBankAccountRepository = stitchEftBankAccountRepository;
        this.statusRepository = statusRepository;
    }

    @Override
    public List<PaymentType> getAvailablePaymentTypes(long merchantId, long applicationId) {

        ApplicationEntity applicationEntity = applicationRepository.findById(applicationId)
                .orElseThrow(() -> new GenericException("No application found for that id | " + applicationId, HttpStatus.NOT_FOUND, "No application found for that id | " + applicationId));

        List<PaymentTypeEntity> availablePaymentTypes = new ArrayList<>();

        List<MerchantProductEntity> merchantProductEntities = merchantProductRepository.findByMerchantId(merchantId);

        for (MerchantProductEntity merchantProductEntity : merchantProductEntities) {
            List<ProductPaymentTypeEntity> productPaymentTypeEntities = productPaymentTypeRepository.findAllByProductEntityByProductId(merchantProductEntity.getProductByProductId());
            for (ProductPaymentTypeEntity productPaymentTypeEntity :
                    productPaymentTypeEntities) {
                availablePaymentTypes.add(productPaymentTypeEntity.getPaymentTypeEntityByPaymentTypeId());
            }
        }

        if (availablePaymentTypes.size() == 0) {
            throw new GenericException("No configured payment types found", HttpStatus.NOT_FOUND, "Could not find any configured payment types for merchantId id | " + merchantId);
        }

//        return availablePaymentTypes
//                .stream()
//                .map(paymentTypeEntity -> {
//                    return new PaymentType(
//                            paymentTypeEntity,
//
//                    );
//                })
//                .collect(Collectors.toList());

        return availablePaymentTypes
                .stream()
                .map(paymentTypeEntity -> {
                    Optional<ApplicationPaymentTypeEntity> applicationPaymentTypeEntity = applicationPaymentTypeRepository.findDistinctFirstByApplicationByApplicationIdAndPaymentTypeByPaymentTypeId(applicationEntity, paymentTypeEntity);
//                            .orElseThrow(() -> new GenericException("Payment type not configured for application", HttpStatus.NOT_FOUND, "ApplicationID: " + applicationId));
                        return new PaymentType(
                            paymentTypeEntity,
                            applicationPaymentTypeEntity.isPresent() && applicationPaymentTypeEntity
                                    .get().isActive(),
                            applicationPaymentTypeEntity.isPresent()

                    );
                })
                .collect(Collectors.toList());
    }

    @Override
    public ApplicationPaymentTypes getApplicationPaymentTypes(long merchantId, long applicationId) {
        List<ApplicationPaymentType> applicationPaymentTypeList = new ArrayList<>();

        List<MerchantProductEntity> merchantProductEntities = merchantProductRepository.findByMerchantId(merchantId);

        for (MerchantProductEntity merchantProductEntity : merchantProductEntities) {
            List<ProductPaymentTypeEntity> productPaymentTypeEntities = productPaymentTypeRepository.findAllByProductEntityByProductId(merchantProductEntity.getProductByProductId());
            for (ProductPaymentTypeEntity productPaymentTypeEntity : productPaymentTypeEntities) {
                ApplicationEntity applicationEntity = applicationRepository.findById(applicationId)
                        .orElseThrow(() -> new GenericException("Could not find application", HttpStatus.INTERNAL_SERVER_ERROR, "ApplicationID: " + applicationId));
                PaymentTypeEntity paymentTypeEntity = productPaymentTypeEntity.getPaymentTypeEntityByPaymentTypeId();
                ApplicationPaymentTypeEntity applicationPaymentTypeEntity = applicationPaymentTypeRepository.findDistinctFirstByApplicationByApplicationIdAndPaymentTypeByPaymentTypeId(applicationEntity, paymentTypeEntity)
                        .orElseThrow(() -> new GenericException("Payment type not configured for application", HttpStatus.NOT_FOUND, "ApplicationID: " + applicationId));
                applicationPaymentTypeList.add(new ApplicationPaymentType(paymentTypeEntity, applicationPaymentTypeEntity.isActive()));

            }
        }

        return new ApplicationPaymentTypes(applicationPaymentTypeList);
    }

    @Override
    public ApplicationPaymentTypeStatus getApplicationPaymentType(long paymentTypeId, long applicationId) {
        ApplicationPaymentTypeEntity applicationPaymentTypeEntity = applicationPaymentTypeRepository.findByApplicationIdAndPaymentTypeId(applicationId,paymentTypeId);
        return new ApplicationPaymentTypeStatus(applicationPaymentTypeEntity);
    }

    @Override
    public ResponseEntity<?> createWalletApplicationConfig(CreateWalletConfigRequest createWalletConfigRequest) {
        ProductEntity walletProductEntity = productRepository.findByProductCode("WALLET");
        try {
            MerchantProductEntity merchantProductEntity
                    = merchantProductRepository.findByMerchantIdAndProductByProductId(createWalletConfigRequest.getMerchantId(), walletProductEntity);
            if(merchantProductEntity != null) {
                //create interface entity
                InterfaceEntity newInterfaceEntity = new InterfaceEntity();
                MerchantEntity merchantEntity = merchantRepository
                        .findById(createWalletConfigRequest.getMerchantId())
                        .orElseThrow(() -> new GenericException("Could not find merchant with id | " + createWalletConfigRequest.getMerchantId(), HttpStatus.NOT_FOUND, "Could not find merchant with id | " + createWalletConfigRequest.getMerchantId()));
                newInterfaceEntity.setMerchantByMerchantId(merchantEntity);
                PaymentTypeEntity paymentTypeEntity = paymentTypeRepository.findByCode("WALLET");
                newInterfaceEntity.setPaymentTypeByPaymentTypeId(paymentTypeEntity);

                // Set gateway and security method
                GatewayEntity gatewayEntity = gatewayRepository.findByCode("1");
                SecurityMethodEntity securityMethodEntity = securityMethodRepository.findByCode("NONE");
                if (gatewayEntity != null) {
                    newInterfaceEntity.setGatewayByGatewayId(gatewayEntity);
                }
                if (securityMethodEntity != null) {
                    newInterfaceEntity.setSecurityMethodBySecurityMethodId(securityMethodEntity);
                }

                CurrencyEntity currencyEntity = currencyRepository.findByCode(createWalletConfigRequest.getCurrencyCode());
                CountryEntity countryEntity = countryRepository.findByCode(createWalletConfigRequest.getCountryCode());
                TradingCurrencyEntity tradingCurrencyEntity =
                        tradingCurrencyRepository.findByCurrencyByCurrencyIdAndCountryByCountryId(currencyEntity, countryEntity);
                if(tradingCurrencyEntity != null) {
                    newInterfaceEntity.setTradingCurrencyByTradingCurrencyId(tradingCurrencyEntity);
                } else {
                    TradingCurrencyEntity newTradingCurrencyEntity = new TradingCurrencyEntity();
                    newTradingCurrencyEntity.setCurrencyByCurrencyId(currencyEntity);
                    newTradingCurrencyEntity.setCountryByCountryId(countryEntity);
                    newTradingCurrencyEntity.setLastModified(new java.sql.Timestamp(new Date().getTime()));
                    TradingCurrencyEntity savedTradingCurrencyEntity = tradingCurrencyRepository.save(newTradingCurrencyEntity);
                    newInterfaceEntity.setTradingCurrencyByTradingCurrencyId(savedTradingCurrencyEntity);
                }

                ApplicationEntity applicationEntity = applicationRepository.findById(createWalletConfigRequest.getApplicationId())
                        .orElseThrow(() -> new GenericException("Could not find application", HttpStatus.INTERNAL_SERVER_ERROR, "ApplicationID: " + createWalletConfigRequest.getApplicationId()));
                newInterfaceEntity.setName(applicationEntity.getName() + ": Wallet Interface");
                newInterfaceEntity.setLastModified(new java.sql.Timestamp(new Date().getTime()));
                newInterfaceEntity.setMerchantMid(createWalletConfigRequest.getMerchantMid());
                newInterfaceEntity.setMerchantByMerchantId(merchantEntity);
                InterfaceEntity savedInterfaceEntity = interfaceRepository.save(newInterfaceEntity);

                //create application payment type entity
                ApplicationPaymentTypeEntity newApplicationPaymentTypeEntity = new ApplicationPaymentTypeEntity();
                newApplicationPaymentTypeEntity.setApplicationByApplicationId(applicationEntity);
                newApplicationPaymentTypeEntity.setPaymentTypeByPaymentTypeId(paymentTypeEntity);
                newApplicationPaymentTypeEntity.setInterfaceByInterfaceId(savedInterfaceEntity);
                newApplicationPaymentTypeEntity.setActive(true);
                newApplicationPaymentTypeEntity.setCreatedDate(new java.sql.Timestamp(new Date().getTime()));
                newApplicationPaymentTypeEntity.setLastModified(new java.sql.Timestamp(new Date().getTime()));
                StatusEntity statusEntity = statusRepository.findByCode(Utils.MERCHANT_STATUS_DEVELOPING_CODE);
                newApplicationPaymentTypeEntity.setStatusByStatusId(statusEntity);
                ApplicationPaymentTypeEntity savedApplicationPaymentTypeEntity = applicationPaymentTypeRepository.save(newApplicationPaymentTypeEntity);

                return ResponseEntity.ok().build();

            } else {
                throw new GenericException("Merchant not registered for product", HttpStatus.INTERNAL_SERVER_ERROR, "MerchantID: " + createWalletConfigRequest.getMerchantId());
            }
        } catch (Exception e) {
            logger.error(e);
            throw new GenericException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, e.getLocalizedMessage());
        }

    }

    @Override
    public WalletConfigResponse getWalletApplicationConfig(long applicationId) {
        ProductEntity walletProductEntity = productRepository.findByProductCode("WALLET");
        PaymentTypeEntity paymentTypeEntity = paymentTypeRepository.findByCode("WALLET");

        ApplicationEntity applicationEntity = applicationRepository.findById(applicationId)
                .orElseThrow(() -> new GenericException("Could not find application", HttpStatus.INTERNAL_SERVER_ERROR, "ApplicationID: " + applicationId));
        MerchantEntity merchantEntity = applicationEntity.getMerchantByMerchantId();

        try {
            MerchantProductEntity merchantProductEntity
                    = merchantProductRepository.findByMerchantIdAndProductByProductId(merchantEntity.getId(), walletProductEntity);
            if(merchantProductEntity != null) {
                ApplicationPaymentTypeEntity applicationPaymentTypeEntity
                        = applicationPaymentTypeRepository.findDistinctFirstByApplicationByApplicationIdAndPaymentTypeByPaymentTypeId(applicationEntity, paymentTypeEntity)
                        .orElseThrow(() -> new GenericException("Payment type not configured for application", HttpStatus.NOT_FOUND, "ApplicationID: " + applicationId));
                if(applicationPaymentTypeEntity != null) {

                    InterfaceEntity interfaceEntity = applicationPaymentTypeEntity.getInterfaceByInterfaceId();
                    WalletConfigResponse walletConfigResponse = new WalletConfigResponse();
                    walletConfigResponse.setMerchantMid(interfaceEntity.getMerchantMid());
                    walletConfigResponse.setCountryCode(interfaceEntity.getTradingCurrencyByTradingCurrencyId().getCountryByCountryId().getCode());
                    walletConfigResponse.setCurrencyCode(interfaceEntity.getTradingCurrencyByTradingCurrencyId().getCurrencyByCurrencyId().getCode());

                    return walletConfigResponse;

                } else {
                    throw new GenericException("Payment type WALLET not configured for application", HttpStatus.INTERNAL_SERVER_ERROR, "ApplicationID: " + applicationEntity.getId());
                }

            } else {
                throw new GenericException("Merchant not registered for product", HttpStatus.INTERNAL_SERVER_ERROR, "MerchantID: " + merchantEntity.getId());
            }
        } catch (Exception e) {
            logger.error(e);
            throw new GenericException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, e.getLocalizedMessage());
        }
    }

    @Override
    public ResponseEntity<?> editWalletApplicationConfig(EditWalletConfigRequest editWalletConfigRequest) {
        ProductEntity walletProductEntity = productRepository.findByProductCode("WALLET");
        PaymentTypeEntity paymentTypeEntity = paymentTypeRepository.findByCode("WALLET");
        try {
            MerchantProductEntity merchantProductEntity
                    = merchantProductRepository.findByMerchantIdAndProductByProductId(editWalletConfigRequest.getMerchantId(), walletProductEntity);
            if(merchantProductEntity != null) {
                ApplicationEntity applicationEntity = applicationRepository.findById(editWalletConfigRequest.getApplicationId())
                        .orElseThrow(() -> new GenericException("Could not find application", HttpStatus.INTERNAL_SERVER_ERROR, "ApplicationID: " + editWalletConfigRequest.getApplicationId()));
                ApplicationPaymentTypeEntity applicationPaymentTypeEntity
                        = applicationPaymentTypeRepository.findDistinctFirstByApplicationByApplicationIdAndPaymentTypeByPaymentTypeId(applicationEntity, paymentTypeEntity)
                        .orElseThrow(() -> new GenericException("Payment type not configured for application", HttpStatus.NOT_FOUND, "ApplicationID: " + editWalletConfigRequest.getApplicationId()));

                InterfaceEntity interfaceEntity = applicationPaymentTypeEntity.getInterfaceByInterfaceId();
                interfaceEntity.setMerchantMid(editWalletConfigRequest.getMerchantMid());

                CurrencyEntity currencyEntity = currencyRepository.findByCode(editWalletConfigRequest.getCurrencyCode());
                CountryEntity countryEntity = countryRepository.findByCode(editWalletConfigRequest.getCountryCode());
                TradingCurrencyEntity tradingCurrencyEntity =
                        tradingCurrencyRepository.findByCurrencyByCurrencyIdAndCountryByCountryId(currencyEntity, countryEntity);
                if(tradingCurrencyEntity != null) {
                    interfaceEntity.setTradingCurrencyId(tradingCurrencyEntity.getId());
                } else {
                    TradingCurrencyEntity newTradingCurrencyEntity = new TradingCurrencyEntity();
                    newTradingCurrencyEntity.setCurrencyByCurrencyId(currencyEntity);
                    newTradingCurrencyEntity.setCountryByCountryId(countryEntity);
                    newTradingCurrencyEntity.setLastModified(new java.sql.Timestamp(new Date().getTime()));
                    TradingCurrencyEntity savedTradingCurrencyEntity = tradingCurrencyRepository.save(newTradingCurrencyEntity);
                    interfaceEntity.setTradingCurrencyId(savedTradingCurrencyEntity.getId());
                }
                InterfaceEntity savedInterfaceEntity = interfaceRepository.save(interfaceEntity);

                return ResponseEntity.ok().build();

            } else {
                throw new GenericException("Merchant not registered for product", HttpStatus.INTERNAL_SERVER_ERROR, "MerchantID: " + editWalletConfigRequest.getMerchantId());
            }
        } catch (Exception e) {
            logger.error(e);
            throw new GenericException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, e.getLocalizedMessage());
        }
    }

    @Override
    public ResponseEntity<?> createOttVoucherApplicationConfig(CreateOttVoucherConfigRequest createOttVoucherConfigRequest) {
        ProductEntity walletProductEntity = productRepository.findByProductCode("VOUCHER");
        try {
            MerchantProductEntity merchantProductEntity
                    = merchantProductRepository.findByMerchantIdAndProductByProductId(createOttVoucherConfigRequest.getMerchantId(), walletProductEntity);
            if(merchantProductEntity != null) {
                //create interface entity
                InterfaceEntity newInterfaceEntity = new InterfaceEntity();
                MerchantEntity merchantEntity = merchantRepository
                        .findById(createOttVoucherConfigRequest.getMerchantId())
                        .orElseThrow(() -> new GenericException("Could not find merchant with id | " + createOttVoucherConfigRequest.getMerchantId(), HttpStatus.NOT_FOUND, "Could not find merchant with id | " + createOttVoucherConfigRequest.getMerchantId()));
                newInterfaceEntity.setMerchantByMerchantId(merchantEntity);
                PaymentTypeEntity paymentTypeEntity = paymentTypeRepository.findByCode("OTT_VOUCHER");
                newInterfaceEntity.setPaymentTypeByPaymentTypeId(paymentTypeEntity);
                // Set gateway and security method
                GatewayEntity gatewayEntity = gatewayRepository.findByCode("1");
                SecurityMethodEntity securityMethodEntity = securityMethodRepository.findByCode("NONE");
                if (gatewayEntity != null) {
                    newInterfaceEntity.setGatewayByGatewayId(gatewayEntity);
                }
                if (securityMethodEntity != null) {
                    newInterfaceEntity.setSecurityMethodBySecurityMethodId(securityMethodEntity);
                }

                CurrencyEntity currencyEntity = currencyRepository.findByCode(createOttVoucherConfigRequest.getCurrencyCode());
                CountryEntity countryEntity = countryRepository.findByCode(createOttVoucherConfigRequest.getCountryCode());
                TradingCurrencyEntity tradingCurrencyEntity =
                        tradingCurrencyRepository.findByCurrencyByCurrencyIdAndCountryByCountryId(currencyEntity, countryEntity);
                if(tradingCurrencyEntity != null) {
                    newInterfaceEntity.setTradingCurrencyByTradingCurrencyId(tradingCurrencyEntity);
                } else {
                    TradingCurrencyEntity newTradingCurrencyEntity = new TradingCurrencyEntity();
                    newTradingCurrencyEntity.setCurrencyByCurrencyId(currencyEntity);
                    newTradingCurrencyEntity.setCountryByCountryId(countryEntity);
                    newTradingCurrencyEntity.setLastModified(new java.sql.Timestamp(new Date().getTime()));
                    TradingCurrencyEntity savedTradingCurrencyEntity = tradingCurrencyRepository.save(newTradingCurrencyEntity);
                    newInterfaceEntity.setTradingCurrencyByTradingCurrencyId(savedTradingCurrencyEntity);
                }

                ApplicationEntity applicationEntity = applicationRepository.findById(createOttVoucherConfigRequest.getApplicationId())
                        .orElseThrow(() -> new GenericException("Could not find application", HttpStatus.INTERNAL_SERVER_ERROR, "ApplicationID: " + createOttVoucherConfigRequest.getApplicationId()));
                newInterfaceEntity.setName(applicationEntity.getName() + ": OTT Voucher Interface");
                newInterfaceEntity.setLastModified(new java.sql.Timestamp(new Date().getTime()));
                newInterfaceEntity.setMerchantByMerchantId(merchantEntity);
                newInterfaceEntity.setMerchantMid(createOttVoucherConfigRequest.getMerchantMid());
                InterfaceEntity savedInterfaceEntity = interfaceRepository.save(newInterfaceEntity);

                //create application payment type entity
                ApplicationPaymentTypeEntity newApplicationPaymentTypeEntity = new ApplicationPaymentTypeEntity();
                newApplicationPaymentTypeEntity.setApplicationByApplicationId(applicationEntity);
                newApplicationPaymentTypeEntity.setPaymentTypeByPaymentTypeId(paymentTypeEntity);
                newApplicationPaymentTypeEntity.setInterfaceByInterfaceId(savedInterfaceEntity);
                newApplicationPaymentTypeEntity.setActive(true);
                newApplicationPaymentTypeEntity.setCreatedDate(new java.sql.Timestamp(new Date().getTime()));
                newApplicationPaymentTypeEntity.setLastModified(new java.sql.Timestamp(new Date().getTime()));
                StatusEntity statusEntity = statusRepository.findByCode(Utils.MERCHANT_STATUS_DEVELOPING_CODE);
                newApplicationPaymentTypeEntity.setStatusByStatusId(statusEntity);
                ApplicationPaymentTypeEntity savedApplicationPaymentTypeEntity = applicationPaymentTypeRepository.save(newApplicationPaymentTypeEntity);

                return ResponseEntity.ok().build();

            } else {
                throw new GenericException("Merchant not registered for product", HttpStatus.INTERNAL_SERVER_ERROR, "MerchantID: " + createOttVoucherConfigRequest.getMerchantId());
            }
        } catch (Exception e) {
            logger.error(e);
            throw new GenericException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, e.getLocalizedMessage());
        }
    }

    @Override
    public OttVoucherConfigResponse getOttVoucherApplicationConfig(long applicationId) {
        ProductEntity walletProductEntity = productRepository.findByProductCode("VOUCHER");
        PaymentTypeEntity paymentTypeEntity = paymentTypeRepository.findByCode("OTT_VOUCHER");

        ApplicationEntity applicationEntity = applicationRepository.findById(applicationId)
                .orElseThrow(() -> new GenericException("Could not find application", HttpStatus.INTERNAL_SERVER_ERROR, "ApplicationID: " + applicationId));
        MerchantEntity merchantEntity = applicationEntity.getMerchantByMerchantId();

        try {
            MerchantProductEntity merchantProductEntity
                    = merchantProductRepository.findByMerchantIdAndProductByProductId(merchantEntity.getId(), walletProductEntity);
            if(merchantProductEntity != null) {
                ApplicationPaymentTypeEntity applicationPaymentTypeEntity
                        = applicationPaymentTypeRepository.findDistinctFirstByApplicationByApplicationIdAndPaymentTypeByPaymentTypeId(applicationEntity, paymentTypeEntity)
                        .orElseThrow(() -> new GenericException("Payment type not configured for application", HttpStatus.NOT_FOUND, "ApplicationID: " + applicationId));
                if(applicationPaymentTypeEntity != null) {

                    InterfaceEntity interfaceEntity = applicationPaymentTypeEntity.getInterfaceByInterfaceId();
                    OttVoucherConfigResponse ottVoucherConfigResponse = new OttVoucherConfigResponse();
                    ottVoucherConfigResponse.setMerchantMid(interfaceEntity.getMerchantMid());
                    ottVoucherConfigResponse.setCountryCode(interfaceEntity.getTradingCurrencyByTradingCurrencyId().getCountryByCountryId().getCode());
                    ottVoucherConfigResponse.setCurrencyCode(interfaceEntity.getTradingCurrencyByTradingCurrencyId().getCurrencyByCurrencyId().getCode());

                    return ottVoucherConfigResponse;

                } else {
                    throw new GenericException("Payment type OTT_VOUCHER not configured for application", HttpStatus.INTERNAL_SERVER_ERROR, "ApplicationID: " + applicationEntity.getId());
                }

            } else {
                throw new GenericException("Merchant not registered for product", HttpStatus.INTERNAL_SERVER_ERROR, "MerchantID: " + merchantEntity.getId());
            }
        } catch (Exception e) {
            logger.error(e);
            throw new GenericException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, e.getLocalizedMessage());
        }
    }

    @Override
    public ResponseEntity<?> editOttVoucherApplicationConfig(EditOttVoucherConfigRequest editOttVoucherConfigRequest) {
        ProductEntity walletProductEntity = productRepository.findByProductCode("VOUCHER");
        PaymentTypeEntity paymentTypeEntity = paymentTypeRepository.findByCode("OTT_VOUCHER");
        try {
            MerchantProductEntity merchantProductEntity
                    = merchantProductRepository.findByMerchantIdAndProductByProductId(editOttVoucherConfigRequest.getMerchantId(), walletProductEntity);
            if(merchantProductEntity != null) {
                ApplicationEntity applicationEntity = applicationRepository.findById(editOttVoucherConfigRequest.getApplicationId())
                        .orElseThrow(() -> new GenericException("Could not find application", HttpStatus.INTERNAL_SERVER_ERROR, "ApplicationID: " + editOttVoucherConfigRequest.getApplicationId()));
                ApplicationPaymentTypeEntity applicationPaymentTypeEntity
                        = applicationPaymentTypeRepository.findDistinctFirstByApplicationByApplicationIdAndPaymentTypeByPaymentTypeId(applicationEntity, paymentTypeEntity)
                        .orElseThrow(() -> new GenericException("Payment type not configured for application", HttpStatus.NOT_FOUND, "ApplicationID: " + editOttVoucherConfigRequest.getApplicationId()));

                InterfaceEntity interfaceEntity = applicationPaymentTypeEntity.getInterfaceByInterfaceId();
                interfaceEntity.setMerchantMid(editOttVoucherConfigRequest.getMerchantMid());

                CurrencyEntity currencyEntity = currencyRepository.findByCode(editOttVoucherConfigRequest.getCurrencyCode());
                CountryEntity countryEntity = countryRepository.findByCode(editOttVoucherConfigRequest.getCountryCode());
                TradingCurrencyEntity tradingCurrencyEntity =
                        tradingCurrencyRepository.findByCurrencyByCurrencyIdAndCountryByCountryId(currencyEntity, countryEntity);
                if(tradingCurrencyEntity != null) {
                    interfaceEntity.setTradingCurrencyId(tradingCurrencyEntity.getId());
                } else {
                    TradingCurrencyEntity newTradingCurrencyEntity = new TradingCurrencyEntity();
                    newTradingCurrencyEntity.setCurrencyByCurrencyId(currencyEntity);
                    newTradingCurrencyEntity.setCountryByCountryId(countryEntity);
                    newTradingCurrencyEntity.setLastModified(new java.sql.Timestamp(new Date().getTime()));
                    TradingCurrencyEntity savedTradingCurrencyEntity = tradingCurrencyRepository.save(newTradingCurrencyEntity);
                    interfaceEntity.setTradingCurrencyId(savedTradingCurrencyEntity.getId());
                }
                InterfaceEntity savedInterfaceEntity = interfaceRepository.save(interfaceEntity);

                return ResponseEntity.ok().build();

            } else {
                throw new GenericException("Merchant not registered for product", HttpStatus.INTERNAL_SERVER_ERROR, "MerchantID: " + editOttVoucherConfigRequest.getMerchantId());
            }
        } catch (Exception e) {
            logger.error(e);
            throw new GenericException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, e.getLocalizedMessage());
        }
    }

    @Override
    public ResponseEntity<?> createDisbursementApplicationConfig(CreateDisbursementConfigRequest createDisbursementConfigRequest) {
        ProductEntity walletProductEntity = productRepository.findByProductCode("DISBURSEMENT");
        try {
            MerchantProductEntity merchantProductEntity
                    = merchantProductRepository.findByMerchantIdAndProductByProductId(createDisbursementConfigRequest.getMerchantId(), walletProductEntity);
            if(merchantProductEntity != null) {
                //create interface entity
                InterfaceEntity newInterfaceEntity = new InterfaceEntity();
                MerchantEntity merchantEntity = merchantRepository
                        .findById(createDisbursementConfigRequest.getMerchantId())
                        .orElseThrow(() -> new GenericException("Could not find merchant with id | " + createDisbursementConfigRequest.getMerchantId(), HttpStatus.NOT_FOUND, "Could not find merchant with id | " + createDisbursementConfigRequest.getMerchantId()));
                newInterfaceEntity.setMerchantByMerchantId(merchantEntity);
                PaymentTypeEntity paymentTypeEntity = paymentTypeRepository.findByCode("DISBURSEMENT");
                newInterfaceEntity.setPaymentTypeByPaymentTypeId(paymentTypeEntity);
                // Set gateway and security method
                GatewayEntity gatewayEntity = gatewayRepository.findByCode("1");
                SecurityMethodEntity securityMethodEntity = securityMethodRepository.findByCode("NONE");
                if (gatewayEntity != null) {
                    newInterfaceEntity.setGatewayByGatewayId(gatewayEntity);
                }
                if (securityMethodEntity != null) {
                    newInterfaceEntity.setSecurityMethodBySecurityMethodId(securityMethodEntity);
                }

                CurrencyEntity currencyEntity = currencyRepository.findByCode(createDisbursementConfigRequest.getCurrencyCode());
                CountryEntity countryEntity = countryRepository.findByCode(createDisbursementConfigRequest.getCountryCode());
                TradingCurrencyEntity tradingCurrencyEntity =
                        tradingCurrencyRepository.findByCurrencyByCurrencyIdAndCountryByCountryId(currencyEntity, countryEntity);
                if(tradingCurrencyEntity != null) {
                    newInterfaceEntity.setTradingCurrencyByTradingCurrencyId(tradingCurrencyEntity);
                } else {
                    TradingCurrencyEntity newTradingCurrencyEntity = new TradingCurrencyEntity();
                    newTradingCurrencyEntity.setCurrencyByCurrencyId(currencyEntity);
                    newTradingCurrencyEntity.setCountryByCountryId(countryEntity);
                    newTradingCurrencyEntity.setLastModified(new java.sql.Timestamp(new Date().getTime()));
                    TradingCurrencyEntity savedTradingCurrencyEntity = tradingCurrencyRepository.save(newTradingCurrencyEntity);
                    newInterfaceEntity.setTradingCurrencyByTradingCurrencyId(savedTradingCurrencyEntity);
                }

                ApplicationEntity applicationEntity = applicationRepository.findById(createDisbursementConfigRequest.getApplicationId())
                        .orElseThrow(() -> new GenericException("Could not find application", HttpStatus.INTERNAL_SERVER_ERROR, "ApplicationID: " + createDisbursementConfigRequest.getApplicationId()));
                newInterfaceEntity.setName(applicationEntity.getName() + ": Disbursement Interface");
                newInterfaceEntity.setLastModified(new java.sql.Timestamp(new Date().getTime()));
                newInterfaceEntity.setMerchantByMerchantId(merchantEntity);
                newInterfaceEntity.setMerchantMid(createDisbursementConfigRequest.getMerchantMid());
                InterfaceEntity savedInterfaceEntity = interfaceRepository.save(newInterfaceEntity);

                DisbursementConfigEntity disbursementConfigEntity = new DisbursementConfigEntity();
                disbursementConfigEntity.setInterfaceId(savedInterfaceEntity.getId());
                disbursementConfigEntity.setApiKey(createDisbursementConfigRequest.getApiKey());
                disbursementConfigEntity.setAccessToken(createDisbursementConfigRequest.getAccessToken());
                disbursementConfigEntity.setSecret(createDisbursementConfigRequest.getSecret());
                disbursementConfigRepository.save(disbursementConfigEntity);

                //create application payment type entity
                ApplicationPaymentTypeEntity newApplicationPaymentTypeEntity = new ApplicationPaymentTypeEntity();
                newApplicationPaymentTypeEntity.setApplicationByApplicationId(applicationEntity);
                newApplicationPaymentTypeEntity.setPaymentTypeByPaymentTypeId(paymentTypeEntity);
                newApplicationPaymentTypeEntity.setInterfaceByInterfaceId(savedInterfaceEntity);
                newApplicationPaymentTypeEntity.setActive(true);
                newApplicationPaymentTypeEntity.setCreatedDate(new java.sql.Timestamp(new Date().getTime()));
                newApplicationPaymentTypeEntity.setLastModified(new java.sql.Timestamp(new Date().getTime()));
                StatusEntity statusEntity = statusRepository.findByCode(Utils.MERCHANT_STATUS_DEVELOPING_CODE);
                newApplicationPaymentTypeEntity.setStatusByStatusId(statusEntity);
                ApplicationPaymentTypeEntity savedApplicationPaymentTypeEntity = applicationPaymentTypeRepository.save(newApplicationPaymentTypeEntity);

                return ResponseEntity.ok().build();

            } else {
                throw new GenericException("Merchant not registered for product", HttpStatus.INTERNAL_SERVER_ERROR, "MerchantID: " + createDisbursementConfigRequest.getMerchantId());
            }
        } catch (Exception e) {
            logger.error(e);
            throw new GenericException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, e.getLocalizedMessage());
        }
    }

    @Override
    public DisbursementConfigResponse getDisbursementApplicationConfig(long applicationId) {
        ProductEntity walletProductEntity = productRepository.findByProductCode("DISBURSEMENT");
        PaymentTypeEntity paymentTypeEntity = paymentTypeRepository.findByCode("DISBURSEMENT");

        ApplicationEntity applicationEntity = applicationRepository.findById(applicationId)
                .orElseThrow(() -> new GenericException("Could not find application", HttpStatus.INTERNAL_SERVER_ERROR, "ApplicationID: " + applicationId));
        MerchantEntity merchantEntity = applicationEntity.getMerchantByMerchantId();

        try {
            MerchantProductEntity merchantProductEntity
                    = merchantProductRepository.findByMerchantIdAndProductByProductId(merchantEntity.getId(), walletProductEntity);
            if(merchantProductEntity != null) {
                ApplicationPaymentTypeEntity applicationPaymentTypeEntity
                        = applicationPaymentTypeRepository.findDistinctFirstByApplicationByApplicationIdAndPaymentTypeByPaymentTypeId(applicationEntity, paymentTypeEntity)
                        .orElseThrow(() -> new GenericException("Payment type not configured for application", HttpStatus.NOT_FOUND, "ApplicationID: " + applicationId));
                if(applicationPaymentTypeEntity != null) {

                    InterfaceEntity interfaceEntity = applicationPaymentTypeEntity.getInterfaceByInterfaceId();
                    DisbursementConfigResponse disbursementConfigResponse = new DisbursementConfigResponse();
                    disbursementConfigResponse.setMerchantMid(interfaceEntity.getMerchantMid());
                    disbursementConfigResponse.setCountryCode(interfaceEntity.getTradingCurrencyByTradingCurrencyId().getCountryByCountryId().getCode());
                    disbursementConfigResponse.setCurrencyCode(interfaceEntity.getTradingCurrencyByTradingCurrencyId().getCurrencyByCurrencyId().getCode());

                    DisbursementConfigEntity disbursementConfigEntity = disbursementConfigRepository.findByInterfaceId(interfaceEntity.getId());
                    disbursementConfigResponse.setApiKey(disbursementConfigEntity.getApiKey());
                    disbursementConfigResponse.setAccessToken(disbursementConfigEntity.getAccessToken());
                    disbursementConfigResponse.setSecret(disbursementConfigEntity.getSecret());

                    return disbursementConfigResponse;

                } else {
                    throw new GenericException("Payment type DISBURSEMENT not configured for application", HttpStatus.INTERNAL_SERVER_ERROR, "ApplicationID: " + applicationEntity.getId());
                }

            } else {
                throw new GenericException("Merchant not registered for product", HttpStatus.INTERNAL_SERVER_ERROR, "MerchantID: " + merchantEntity.getId());
            }
        } catch (Exception e) {
            logger.error(e);
            throw new GenericException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, e.getLocalizedMessage());
        }
    }

    @Override
    public ResponseEntity<?> editDisbursementApplicationConfig(EditDisbursementConfigRequest editDisbursementConfigRequest) {
        ProductEntity walletProductEntity = productRepository.findByProductCode("DISBURSEMENT");
        PaymentTypeEntity paymentTypeEntity = paymentTypeRepository.findByCode("DISBURSEMENT");
        try {
            MerchantProductEntity merchantProductEntity
                    = merchantProductRepository.findByMerchantIdAndProductByProductId(editDisbursementConfigRequest.getMerchantId(), walletProductEntity);
            if(merchantProductEntity != null) {
                ApplicationEntity applicationEntity = applicationRepository.findById(editDisbursementConfigRequest.getApplicationId())
                        .orElseThrow(() -> new GenericException("Could not find application", HttpStatus.INTERNAL_SERVER_ERROR, "ApplicationID: " + editDisbursementConfigRequest.getApplicationId()));
                ApplicationPaymentTypeEntity applicationPaymentTypeEntity
                        = applicationPaymentTypeRepository.findDistinctFirstByApplicationByApplicationIdAndPaymentTypeByPaymentTypeId(applicationEntity, paymentTypeEntity)
                        .orElseThrow(() -> new GenericException("Payment type not configured for application", HttpStatus.NOT_FOUND, "ApplicationID: " + editDisbursementConfigRequest.getApplicationId()));

                InterfaceEntity interfaceEntity = applicationPaymentTypeEntity.getInterfaceByInterfaceId();
                interfaceEntity.setMerchantMid(editDisbursementConfigRequest.getMerchantMid());

                CurrencyEntity currencyEntity = currencyRepository.findByCode(editDisbursementConfigRequest.getCurrencyCode());
                CountryEntity countryEntity = countryRepository.findByCode(editDisbursementConfigRequest.getCountryCode());
                TradingCurrencyEntity tradingCurrencyEntity =
                        tradingCurrencyRepository.findByCurrencyByCurrencyIdAndCountryByCountryId(currencyEntity, countryEntity);
                if(tradingCurrencyEntity != null) {
                    interfaceEntity.setTradingCurrencyId(tradingCurrencyEntity.getId());
                } else {
                    TradingCurrencyEntity newTradingCurrencyEntity = new TradingCurrencyEntity();
                    newTradingCurrencyEntity.setCurrencyByCurrencyId(currencyEntity);
                    newTradingCurrencyEntity.setCountryByCountryId(countryEntity);
                    newTradingCurrencyEntity.setLastModified(new java.sql.Timestamp(new Date().getTime()));
                    TradingCurrencyEntity savedTradingCurrencyEntity = tradingCurrencyRepository.save(newTradingCurrencyEntity);
                    interfaceEntity.setTradingCurrencyId(savedTradingCurrencyEntity.getId());
                }
                InterfaceEntity savedInterfaceEntity = interfaceRepository.save(interfaceEntity);

                //payment type specific config
                DisbursementConfigEntity disbursementConfigEntity = disbursementConfigRepository.findByInterfaceId(savedInterfaceEntity.getId());
                disbursementConfigEntity.setApiKey(editDisbursementConfigRequest.getApiKey());
                disbursementConfigEntity.setAccessToken(editDisbursementConfigRequest.getAccessToken());
                disbursementConfigEntity.setSecret(editDisbursementConfigRequest.getSecret());
                DisbursementConfigEntity savedDisbursementConfigEntity = disbursementConfigRepository.save(disbursementConfigEntity);

                return ResponseEntity.ok().build();

            } else {
                throw new GenericException("Merchant not registered for product", HttpStatus.INTERNAL_SERVER_ERROR, "MerchantID: " + editDisbursementConfigRequest.getMerchantId());
            }
        } catch (Exception e) {
            logger.error(e);
            throw new GenericException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, e.getLocalizedMessage());
        }
    }

    @Override
    public ResponseEntity<?> createEftApplicationConfig(CreateEftConfigRequest createEftConfigRequest) {
        logger.info("Create EFT Config Request | " + createEftConfigRequest.toString());
        ProductEntity walletProductEntity = productRepository.findByProductCode("EFT");
        try {
            MerchantProductEntity merchantProductEntity
                    = merchantProductRepository.findByMerchantIdAndProductByProductId(createEftConfigRequest.getMerchantId(), walletProductEntity);
            if(merchantProductEntity != null) {
                //create interface entity
                InterfaceEntity newInterfaceEntity = new InterfaceEntity();
                MerchantEntity merchantEntity = merchantRepository
                        .findById(createEftConfigRequest.getMerchantId())
                        .orElseThrow(() -> new GenericException("Could not find merchant with id | " + createEftConfigRequest.getMerchantId(), HttpStatus.NOT_FOUND, "Could not find merchant with id | " + createEftConfigRequest.getMerchantId()));
                newInterfaceEntity.setMerchantByMerchantId(merchantEntity);
                PaymentTypeEntity paymentTypeEntity = paymentTypeRepository.findByCode("EFT");
                newInterfaceEntity.setPaymentTypeByPaymentTypeId(paymentTypeEntity);
                // Set gateway and security method
                GatewayEntity gatewayEntity = gatewayRepository.findByCode("1");
                SecurityMethodEntity securityMethodEntity = securityMethodRepository.findByCode("NONE");
                if (gatewayEntity != null) {
                    newInterfaceEntity.setGatewayByGatewayId(gatewayEntity);
                }
                if (securityMethodEntity != null) {
                    newInterfaceEntity.setSecurityMethodBySecurityMethodId(securityMethodEntity);
                }

                CurrencyEntity currencyEntity = currencyRepository.findByCode(createEftConfigRequest.getCurrencyCode());
                CountryEntity countryEntity = countryRepository.findByCode(createEftConfigRequest.getCountryCode());
                TradingCurrencyEntity tradingCurrencyEntity =
                        tradingCurrencyRepository.findByCurrencyByCurrencyIdAndCountryByCountryId(currencyEntity, countryEntity);
                if(tradingCurrencyEntity != null) {
                    newInterfaceEntity.setTradingCurrencyByTradingCurrencyId(tradingCurrencyEntity);
                } else {
                    TradingCurrencyEntity newTradingCurrencyEntity = new TradingCurrencyEntity();
                    newTradingCurrencyEntity.setCurrencyByCurrencyId(currencyEntity);
                    newTradingCurrencyEntity.setCountryByCountryId(countryEntity);
                    newTradingCurrencyEntity.setLastModified(new java.sql.Timestamp(new Date().getTime()));
                    TradingCurrencyEntity savedTradingCurrencyEntity = tradingCurrencyRepository.save(newTradingCurrencyEntity);
                    newInterfaceEntity.setTradingCurrencyByTradingCurrencyId(savedTradingCurrencyEntity);
                }

                ApplicationEntity applicationEntity = applicationRepository.findById(createEftConfigRequest.getApplicationId())
                        .orElseThrow(() -> new GenericException("Could not find application", HttpStatus.INTERNAL_SERVER_ERROR, "ApplicationID: " + createEftConfigRequest.getApplicationId()));
                newInterfaceEntity.setName(applicationEntity.getName() + ": EFT Interface");
                newInterfaceEntity.setLastModified(new java.sql.Timestamp(new Date().getTime()));
                newInterfaceEntity.setMerchantByMerchantId(merchantEntity);
                newInterfaceEntity.setMerchantMid(createEftConfigRequest.getMerchantMid());
                logger.info("Save new interface entity | " + newInterfaceEntity.toString());
                InterfaceEntity savedInterfaceEntity = interfaceRepository.save(newInterfaceEntity);

                EftInterfaceConfigEntity eftInterfaceConfigEntity = new EftInterfaceConfigEntity();
                eftInterfaceConfigEntity.setInterfaceId(savedInterfaceEntity.getId());
                eftInterfaceConfigEntity.setMerchantKey(createEftConfigRequest.getMerchantKey());
                eftInterfaceConfigEntity.setMerchantSecret(createEftConfigRequest.getMerchantSecret());
                eftInterfaceConfigRepository.save(eftInterfaceConfigEntity);

                //create application payment type entity
                ApplicationPaymentTypeEntity newApplicationPaymentTypeEntity = new ApplicationPaymentTypeEntity();
                newApplicationPaymentTypeEntity.setApplicationByApplicationId(applicationEntity);
                newApplicationPaymentTypeEntity.setPaymentTypeByPaymentTypeId(paymentTypeEntity);
                newApplicationPaymentTypeEntity.setInterfaceByInterfaceId(savedInterfaceEntity);
                newApplicationPaymentTypeEntity.setActive(true);
                newApplicationPaymentTypeEntity.setCreatedDate(new java.sql.Timestamp(new Date().getTime()));
                newApplicationPaymentTypeEntity.setLastModified(new java.sql.Timestamp(new Date().getTime()));
                StatusEntity statusEntity = statusRepository.findByCode(Utils.MERCHANT_STATUS_DEVELOPING_CODE);
                newApplicationPaymentTypeEntity.setStatusByStatusId(statusEntity);
                ApplicationPaymentTypeEntity savedApplicationPaymentTypeEntity = applicationPaymentTypeRepository.save(newApplicationPaymentTypeEntity);

                return ResponseEntity.ok().build();

            } else {
                throw new GenericException("Merchant not registered for product", HttpStatus.INTERNAL_SERVER_ERROR, "MerchantID: " + createEftConfigRequest.getMerchantId());
            }
        } catch (Exception e) {
            logger.error(e);
            throw new GenericException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, e.getLocalizedMessage());
        }
    }

    @Override
    public EftConfigResponse getEftApplicationConfig(long applicationId) {
        ProductEntity walletProductEntity = productRepository.findByProductCode("EFT");
        PaymentTypeEntity paymentTypeEntity = paymentTypeRepository.findByCode("EFT");

        ApplicationEntity applicationEntity = applicationRepository.findById(applicationId)
                .orElseThrow(() -> new GenericException("Could not find application", HttpStatus.INTERNAL_SERVER_ERROR, "ApplicationID: " + applicationId));
        MerchantEntity merchantEntity = applicationEntity.getMerchantByMerchantId();

        try {
            MerchantProductEntity merchantProductEntity
                    = merchantProductRepository.findByMerchantIdAndProductByProductId(merchantEntity.getId(), walletProductEntity);
            if(merchantProductEntity != null) {
                ApplicationPaymentTypeEntity applicationPaymentTypeEntity
                        = applicationPaymentTypeRepository.findDistinctFirstByApplicationByApplicationIdAndPaymentTypeByPaymentTypeId(applicationEntity, paymentTypeEntity)
                        .orElseThrow(() -> new GenericException("Payment type not configured for application", HttpStatus.NOT_FOUND, "ApplicationID: " + applicationId));
                if(applicationPaymentTypeEntity != null) {

                    InterfaceEntity interfaceEntity = applicationPaymentTypeEntity.getInterfaceByInterfaceId();
                    EftConfigResponse eftConfigResponse = new EftConfigResponse();
                    eftConfigResponse.setMerchantMid(interfaceEntity.getMerchantMid());
                    eftConfigResponse.setCountryCode(interfaceEntity.getTradingCurrencyByTradingCurrencyId().getCountryByCountryId().getCode());
                    eftConfigResponse.setCurrencyCode(interfaceEntity.getTradingCurrencyByTradingCurrencyId().getCurrencyByCurrencyId().getCode());

                    EftInterfaceConfigEntity eftInterfaceConfigEntity = eftInterfaceConfigRepository.findByInterfaceId(interfaceEntity.getId());
                    eftConfigResponse.setMerchantKey(eftInterfaceConfigEntity.getMerchantKey());
                    eftConfigResponse.setMerchantSecret(eftInterfaceConfigEntity.getMerchantSecret());

                    return eftConfigResponse;

                } else {
                    throw new GenericException("Payment type EFT not configured for application", HttpStatus.INTERNAL_SERVER_ERROR, "ApplicationID: " + applicationEntity.getId());
                }

            } else {
                throw new GenericException("Merchant not registered for product", HttpStatus.INTERNAL_SERVER_ERROR, "MerchantID: " + merchantEntity.getId());
            }
        } catch (Exception e) {
            logger.error(e);
            throw new GenericException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, e.getLocalizedMessage());
        }
    }

    @Override
    public ResponseEntity<?> editEftApplicationConfig(EditEftConfigRequest editEftConfigRequest) {
        ProductEntity walletProductEntity = productRepository.findByProductCode("EFT");
        PaymentTypeEntity paymentTypeEntity = paymentTypeRepository.findByCode("EFT");
        try {
            MerchantProductEntity merchantProductEntity
                    = merchantProductRepository.findByMerchantIdAndProductByProductId(editEftConfigRequest.getMerchantId(), walletProductEntity);
            if(merchantProductEntity != null) {
                ApplicationEntity applicationEntity = applicationRepository.findById(editEftConfigRequest.getApplicationId())
                        .orElseThrow(() -> new GenericException("Could not find application", HttpStatus.INTERNAL_SERVER_ERROR, "ApplicationID: " + editEftConfigRequest.getApplicationId()));
                ApplicationPaymentTypeEntity applicationPaymentTypeEntity
                        = applicationPaymentTypeRepository.findDistinctFirstByApplicationByApplicationIdAndPaymentTypeByPaymentTypeId(applicationEntity, paymentTypeEntity)
                        .orElseThrow(() -> new GenericException("Payment type not configured for application", HttpStatus.NOT_FOUND, "ApplicationID: " + editEftConfigRequest.getApplicationId()));

                InterfaceEntity interfaceEntity = applicationPaymentTypeEntity.getInterfaceByInterfaceId();
                interfaceEntity.setMerchantMid(editEftConfigRequest.getMerchantMid());

                CurrencyEntity currencyEntity = currencyRepository.findByCode(editEftConfigRequest.getCurrencyCode());
                CountryEntity countryEntity = countryRepository.findByCode(editEftConfigRequest.getCountryCode());
                TradingCurrencyEntity tradingCurrencyEntity =
                        tradingCurrencyRepository.findByCurrencyByCurrencyIdAndCountryByCountryId(currencyEntity, countryEntity);
                if(tradingCurrencyEntity != null) {
                    interfaceEntity.setTradingCurrencyId(tradingCurrencyEntity.getId());
                } else {
                    TradingCurrencyEntity newTradingCurrencyEntity = new TradingCurrencyEntity();
                    newTradingCurrencyEntity.setCurrencyByCurrencyId(currencyEntity);
                    newTradingCurrencyEntity.setCountryByCountryId(countryEntity);
                    newTradingCurrencyEntity.setLastModified(new java.sql.Timestamp(new Date().getTime()));
                    TradingCurrencyEntity savedTradingCurrencyEntity = tradingCurrencyRepository.save(newTradingCurrencyEntity);
                    interfaceEntity.setTradingCurrencyId(savedTradingCurrencyEntity.getId());
                }
                InterfaceEntity savedInterfaceEntity = interfaceRepository.save(interfaceEntity);

                //payment type specific config
                EftInterfaceConfigEntity eftInterfaceConfigEntity = eftInterfaceConfigRepository.findByInterfaceId(savedInterfaceEntity.getId());
                eftInterfaceConfigEntity.setMerchantKey(editEftConfigRequest.getMerchantKey());
                eftInterfaceConfigEntity.setMerchantSecret(editEftConfigRequest.getMerchantSecret());
                EftInterfaceConfigEntity savedEftInterfaceConfigEntity = eftInterfaceConfigRepository.save(eftInterfaceConfigEntity);

                return ResponseEntity.ok().build();

            } else {
                throw new GenericException("Merchant not registered for product", HttpStatus.INTERNAL_SERVER_ERROR, "MerchantID: " + editEftConfigRequest.getMerchantId());
            }
        } catch (Exception e) {
            logger.error(e);
            throw new GenericException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, e.getLocalizedMessage());
        }
    }

    @Override
    public ResponseEntity<?> createMobicredApplicationConfig(CreateMobicredConfigRequest createMobicredConfigRequest) {
        ProductEntity walletProductEntity = productRepository.findByProductCode("MOBICRED");
        try {
            MerchantProductEntity merchantProductEntity
                    = merchantProductRepository.findByMerchantIdAndProductByProductId(createMobicredConfigRequest.getMerchantId(), walletProductEntity);
            if(merchantProductEntity != null) {
                //create interface entity
                InterfaceEntity newInterfaceEntity = new InterfaceEntity();
                                MerchantEntity merchantEntity = merchantRepository
                        .findById(createMobicredConfigRequest.getMerchantId())
                        .orElseThrow(() -> new GenericException("Could not find merchant with id | " + createMobicredConfigRequest.getMerchantId(), HttpStatus.NOT_FOUND, "Could not find merchant with id | " + createMobicredConfigRequest.getMerchantId()));
                newInterfaceEntity.setMerchantByMerchantId(merchantEntity);
                PaymentTypeEntity paymentTypeEntity = paymentTypeRepository.findByCode("MOBICRED");
                newInterfaceEntity.setPaymentTypeByPaymentTypeId(paymentTypeEntity);
                // Set gateway and security method
                GatewayEntity gatewayEntity = gatewayRepository.findByCode("1");
                SecurityMethodEntity securityMethodEntity = securityMethodRepository.findByCode("NONE");
                if (gatewayEntity != null) {
                    newInterfaceEntity.setGatewayByGatewayId(gatewayEntity);
                }
                if (securityMethodEntity != null) {
                    newInterfaceEntity.setSecurityMethodBySecurityMethodId(securityMethodEntity);
                }

                CurrencyEntity currencyEntity = currencyRepository.findByCode(createMobicredConfigRequest.getCurrencyCode());
                CountryEntity countryEntity = countryRepository.findByCode(createMobicredConfigRequest.getCountryCode());
                TradingCurrencyEntity tradingCurrencyEntity =
                        tradingCurrencyRepository.findByCurrencyByCurrencyIdAndCountryByCountryId(currencyEntity, countryEntity);
                if(tradingCurrencyEntity != null) {
                    newInterfaceEntity.setTradingCurrencyByTradingCurrencyId(tradingCurrencyEntity);
                } else {
                    TradingCurrencyEntity newTradingCurrencyEntity = new TradingCurrencyEntity();
                    newTradingCurrencyEntity.setCurrencyByCurrencyId(currencyEntity);
                    newTradingCurrencyEntity.setCountryByCountryId(countryEntity);
                    newTradingCurrencyEntity.setLastModified(new java.sql.Timestamp(new Date().getTime()));
                    TradingCurrencyEntity savedTradingCurrencyEntity = tradingCurrencyRepository.save(newTradingCurrencyEntity);
                    newInterfaceEntity.setTradingCurrencyByTradingCurrencyId(savedTradingCurrencyEntity);
                }

                ApplicationEntity applicationEntity = applicationRepository.findById(createMobicredConfigRequest.getApplicationId())
                        .orElseThrow(() -> new GenericException("Could not find application", HttpStatus.INTERNAL_SERVER_ERROR, "ApplicationID: " + createMobicredConfigRequest.getApplicationId()));
                newInterfaceEntity.setName(applicationEntity.getName() + ": Mobicred Interface");
                newInterfaceEntity.setLastModified(new java.sql.Timestamp(new Date().getTime()));
                newInterfaceEntity.setMerchantByMerchantId(merchantEntity);
                newInterfaceEntity.setMerchantMid(createMobicredConfigRequest.getMerchantMid());
                InterfaceEntity savedInterfaceEntity = interfaceRepository.save(newInterfaceEntity);

                MobicredInterfaceConfigEntity mobicredInterfaceConfigEntity = new MobicredInterfaceConfigEntity();
                mobicredInterfaceConfigEntity.setInterfaceId(savedInterfaceEntity.getId());
                mobicredInterfaceConfigEntity.setCMerchantId(createMobicredConfigRequest.getCMerchantId());
                mobicredInterfaceConfigEntity.setCMerchantKey(createMobicredConfigRequest.getCMerchantKey());
                mobicredInterfaceConfigRepository.save(mobicredInterfaceConfigEntity);

                //create application payment type entity
                ApplicationPaymentTypeEntity newApplicationPaymentTypeEntity = new ApplicationPaymentTypeEntity();
                newApplicationPaymentTypeEntity.setApplicationByApplicationId(applicationEntity);
                newApplicationPaymentTypeEntity.setPaymentTypeByPaymentTypeId(paymentTypeEntity);
                newApplicationPaymentTypeEntity.setInterfaceByInterfaceId(savedInterfaceEntity);
                newApplicationPaymentTypeEntity.setActive(true);
                newApplicationPaymentTypeEntity.setCreatedDate(new java.sql.Timestamp(new Date().getTime()));
                newApplicationPaymentTypeEntity.setLastModified(new java.sql.Timestamp(new Date().getTime()));
                StatusEntity statusEntity = statusRepository.findByCode(Utils.MERCHANT_STATUS_DEVELOPING_CODE);
                newApplicationPaymentTypeEntity.setStatusByStatusId(statusEntity);
                ApplicationPaymentTypeEntity savedApplicationPaymentTypeEntity = applicationPaymentTypeRepository.save(newApplicationPaymentTypeEntity);

                return ResponseEntity.ok().build();

            } else {
                throw new GenericException("Merchant not registered for product", HttpStatus.INTERNAL_SERVER_ERROR, "MerchantID: " + createMobicredConfigRequest.getMerchantId());
            }
        } catch (Exception e) {
            logger.error(e);
            throw new GenericException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, e.getLocalizedMessage());
        }
    }

    @Override
    public MobicredConfigResponse getMobicredApplicationConfig(long applicationId) {
        ProductEntity walletProductEntity = productRepository.findByProductCode("MOBICRED");
        PaymentTypeEntity paymentTypeEntity = paymentTypeRepository.findByCode("MOBICRED");

        ApplicationEntity applicationEntity = applicationRepository.findById(applicationId)
                .orElseThrow(() -> new GenericException("Could not find application", HttpStatus.INTERNAL_SERVER_ERROR, "ApplicationID: " + applicationId));
        MerchantEntity merchantEntity = applicationEntity.getMerchantByMerchantId();

        try {
            MerchantProductEntity merchantProductEntity
                    = merchantProductRepository.findByMerchantIdAndProductByProductId(merchantEntity.getId(), walletProductEntity);
            if(merchantProductEntity != null) {
                ApplicationPaymentTypeEntity applicationPaymentTypeEntity
                        = applicationPaymentTypeRepository.findDistinctFirstByApplicationByApplicationIdAndPaymentTypeByPaymentTypeId(applicationEntity, paymentTypeEntity)
                        .orElseThrow(() -> new GenericException("Payment type not configured for application", HttpStatus.NOT_FOUND, "ApplicationID: " + applicationId));
                if(applicationPaymentTypeEntity != null) {

                    InterfaceEntity interfaceEntity = applicationPaymentTypeEntity.getInterfaceByInterfaceId();
                    MobicredConfigResponse mobicredConfigResponse = new MobicredConfigResponse();
                    mobicredConfigResponse.setMerchantMid(interfaceEntity.getMerchantMid());
                    mobicredConfigResponse.setCountryCode(interfaceEntity.getTradingCurrencyByTradingCurrencyId().getCountryByCountryId().getCode());
                    mobicredConfigResponse.setCurrencyCode(interfaceEntity.getTradingCurrencyByTradingCurrencyId().getCurrencyByCurrencyId().getCode());

                    MobicredInterfaceConfigEntity mobicredInterfaceConfigEntity = mobicredInterfaceConfigRepository.findByInterfaceId(interfaceEntity.getId());
                    mobicredConfigResponse.setCMerchantKey(mobicredInterfaceConfigEntity.getCMerchantKey());
                    mobicredConfigResponse.setCMerchantId(mobicredInterfaceConfigEntity.getCMerchantId());

                    return mobicredConfigResponse;

                } else {
                    throw new GenericException("Payment type MOBICRED not configured for application", HttpStatus.INTERNAL_SERVER_ERROR, "ApplicationID: " + applicationEntity.getId());
                }

            } else {
                throw new GenericException("Merchant not registered for product", HttpStatus.INTERNAL_SERVER_ERROR, "MerchantID: " + merchantEntity.getId());
            }
        } catch (Exception e) {
            logger.error(e);
            throw new GenericException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, e.getLocalizedMessage());
        }
    }

    @Override
    public ResponseEntity<?> editMobicredApplicationConfig(EditMobicredConfigRequest editMobicredConfigRequest) {
        ProductEntity walletProductEntity = productRepository.findByProductCode("MOBICRED");
        PaymentTypeEntity paymentTypeEntity = paymentTypeRepository.findByCode("MOBICRED");
        try {
            MerchantProductEntity merchantProductEntity
                    = merchantProductRepository.findByMerchantIdAndProductByProductId(editMobicredConfigRequest.getMerchantId(), walletProductEntity);
            if(merchantProductEntity != null) {
                ApplicationEntity applicationEntity = applicationRepository.findById(editMobicredConfigRequest.getApplicationId())
                        .orElseThrow(() -> new GenericException("Could not find application", HttpStatus.INTERNAL_SERVER_ERROR, "ApplicationID: " + editMobicredConfigRequest.getApplicationId()));
                ApplicationPaymentTypeEntity applicationPaymentTypeEntity
                        = applicationPaymentTypeRepository.findDistinctFirstByApplicationByApplicationIdAndPaymentTypeByPaymentTypeId(applicationEntity, paymentTypeEntity)
                        .orElseThrow(() -> new GenericException("Payment type not configured for application", HttpStatus.NOT_FOUND, "ApplicationID: " + editMobicredConfigRequest.getApplicationId()));

                InterfaceEntity interfaceEntity = applicationPaymentTypeEntity.getInterfaceByInterfaceId();
                interfaceEntity.setMerchantMid(editMobicredConfigRequest.getMerchantMid());

                CurrencyEntity currencyEntity = currencyRepository.findByCode(editMobicredConfigRequest.getCurrencyCode());
                CountryEntity countryEntity = countryRepository.findByCode(editMobicredConfigRequest.getCountryCode());
                TradingCurrencyEntity tradingCurrencyEntity =
                        tradingCurrencyRepository.findByCurrencyByCurrencyIdAndCountryByCountryId(currencyEntity, countryEntity);
                if(tradingCurrencyEntity != null) {
                    interfaceEntity.setTradingCurrencyByTradingCurrencyId(tradingCurrencyEntity);
                } else {
                    TradingCurrencyEntity newTradingCurrencyEntity = new TradingCurrencyEntity();
                    newTradingCurrencyEntity.setCurrencyByCurrencyId(currencyEntity);
                    newTradingCurrencyEntity.setCountryByCountryId(countryEntity);
                    newTradingCurrencyEntity.setLastModified(new java.sql.Timestamp(new Date().getTime()));
                    TradingCurrencyEntity savedTradingCurrencyEntity = tradingCurrencyRepository.save(newTradingCurrencyEntity);
                    interfaceEntity.setTradingCurrencyByTradingCurrencyId(savedTradingCurrencyEntity);
                }
                InterfaceEntity savedInterfaceEntity = interfaceRepository.save(interfaceEntity);

                //payment type specific config
                MobicredInterfaceConfigEntity mobicredInterfaceConfigEntity = mobicredInterfaceConfigRepository.findByInterfaceId(savedInterfaceEntity.getId());
                mobicredInterfaceConfigEntity.setCMerchantId(editMobicredConfigRequest.getCMerchantId());
                mobicredInterfaceConfigEntity.setCMerchantKey(editMobicredConfigRequest.getCMerchantKey());
                MobicredInterfaceConfigEntity savedMobicredInterfaceConfigEntity = mobicredInterfaceConfigRepository.save(mobicredInterfaceConfigEntity);

                return ResponseEntity.ok().build();

            } else {
                throw new GenericException("Merchant not registered for product", HttpStatus.INTERNAL_SERVER_ERROR, "MerchantID: " + editMobicredConfigRequest.getMerchantId());
            }
        } catch (Exception e) {
            logger.error(e);
            throw new GenericException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, e.getLocalizedMessage());
        }
    }

    @Override
    public ResponseEntity<?> createMasterpassApplicationConfig(CreateMasterpassConfigRequest createMasterpassConfigRequest) {
        ProductEntity walletProductEntity = productRepository.findByProductCode("QR");
        try {
            MerchantProductEntity merchantProductEntity
                    = merchantProductRepository.findByMerchantIdAndProductByProductId(createMasterpassConfigRequest.getMerchantId(), walletProductEntity);
            if(merchantProductEntity != null) {
                //create interface entity
                InterfaceEntity newInterfaceEntity = new InterfaceEntity();
                                MerchantEntity merchantEntity = merchantRepository
                        .findById(createMasterpassConfigRequest.getMerchantId())
                        .orElseThrow(() -> new GenericException("Could not find merchant with id | " + createMasterpassConfigRequest.getMerchantId(), HttpStatus.NOT_FOUND, "Could not find merchant with id | " + createMasterpassConfigRequest.getMerchantId()));
                newInterfaceEntity.setMerchantByMerchantId(merchantEntity);
                PaymentTypeEntity paymentTypeEntity = paymentTypeRepository.findByCode("MASTER_PASS");
                newInterfaceEntity.setPaymentTypeByPaymentTypeId(paymentTypeEntity);
                // Set gateway and security method
                GatewayEntity gatewayEntity = gatewayRepository.findByCode("1");
                SecurityMethodEntity securityMethodEntity = securityMethodRepository.findByCode("NONE");
                if (gatewayEntity != null) {
                    newInterfaceEntity.setGatewayByGatewayId(gatewayEntity);
                }
                if (securityMethodEntity != null) {
                    newInterfaceEntity.setSecurityMethodBySecurityMethodId(securityMethodEntity);
                }

                CurrencyEntity currencyEntity = currencyRepository.findByCode(createMasterpassConfigRequest.getCurrencyCode());
                CountryEntity countryEntity = countryRepository.findByCode(createMasterpassConfigRequest.getCountryCode());
                TradingCurrencyEntity tradingCurrencyEntity =
                        tradingCurrencyRepository.findByCurrencyByCurrencyIdAndCountryByCountryId(currencyEntity, countryEntity);
                if(tradingCurrencyEntity != null) {
                    newInterfaceEntity.setTradingCurrencyByTradingCurrencyId(tradingCurrencyEntity);
                } else {
                    TradingCurrencyEntity newTradingCurrencyEntity = new TradingCurrencyEntity();
                    newTradingCurrencyEntity.setCurrencyByCurrencyId(currencyEntity);
                    newTradingCurrencyEntity.setCountryByCountryId(countryEntity);
                    newTradingCurrencyEntity.setLastModified(new java.sql.Timestamp(new Date().getTime()));
                    TradingCurrencyEntity savedTradingCurrencyEntity = tradingCurrencyRepository.save(newTradingCurrencyEntity);
                    newInterfaceEntity.setTradingCurrencyByTradingCurrencyId(savedTradingCurrencyEntity);
                }

                ApplicationEntity applicationEntity = applicationRepository.findById(createMasterpassConfigRequest.getApplicationId())
                        .orElseThrow(() -> new GenericException("Could not find application", HttpStatus.INTERNAL_SERVER_ERROR, "ApplicationID: " + createMasterpassConfigRequest.getApplicationId()));
                newInterfaceEntity.setName(applicationEntity.getName() + ": Masterpass Interface");
                newInterfaceEntity.setLastModified(new java.sql.Timestamp(new Date().getTime()));
                newInterfaceEntity.setMerchantByMerchantId(merchantEntity);
                newInterfaceEntity.setMerchantMid(createMasterpassConfigRequest.getMerchantMid());
                InterfaceEntity savedInterfaceEntity = interfaceRepository.save(newInterfaceEntity);

                MasterPassConfigEntity masterPassConfigEntity = new MasterPassConfigEntity();
                masterPassConfigEntity.setApiUsername(createMasterpassConfigRequest.getApiUsername());
                masterPassConfigEntity.setApiKey(createMasterpassConfigRequest.getApiKey());
                masterPassConfigEntity.setNotificationKey(createMasterpassConfigRequest.getNotificationKey());
                masterPassConfigEntity.setInterfaceId(savedInterfaceEntity.getId());
                masterPassConfigRepository.save(masterPassConfigEntity);

                //create application payment type entity
                ApplicationPaymentTypeEntity newApplicationPaymentTypeEntity = new ApplicationPaymentTypeEntity();
                newApplicationPaymentTypeEntity.setApplicationByApplicationId(applicationEntity);
                newApplicationPaymentTypeEntity.setPaymentTypeByPaymentTypeId(paymentTypeEntity);
                newApplicationPaymentTypeEntity.setInterfaceByInterfaceId(savedInterfaceEntity);
                newApplicationPaymentTypeEntity.setActive(true);
                newApplicationPaymentTypeEntity.setCreatedDate(new java.sql.Timestamp(new Date().getTime()));
                newApplicationPaymentTypeEntity.setLastModified(new java.sql.Timestamp(new Date().getTime()));
                StatusEntity statusEntity = statusRepository.findByCode(Utils.MERCHANT_STATUS_DEVELOPING_CODE);
                newApplicationPaymentTypeEntity.setStatusByStatusId(statusEntity);
                ApplicationPaymentTypeEntity savedApplicationPaymentTypeEntity = applicationPaymentTypeRepository.save(newApplicationPaymentTypeEntity);

                return ResponseEntity.ok().build();

            } else {
                throw new GenericException("Merchant not registered for product", HttpStatus.INTERNAL_SERVER_ERROR, "MerchantID: " + createMasterpassConfigRequest.getMerchantId());
            }
        } catch (Exception e) {
            logger.error(e);
            throw new GenericException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, e.getLocalizedMessage());
        }
    }

    @Override
    public MasterpassConfigResponse getMasterpassApplicationConfig(long applicationId) {
        ProductEntity walletProductEntity = productRepository.findByProductCode("QR");
        PaymentTypeEntity paymentTypeEntity = paymentTypeRepository.findByCode("MASTER_PASS");

        ApplicationEntity applicationEntity = applicationRepository.findById(applicationId)
                .orElseThrow(() -> new GenericException("Could not find application", HttpStatus.INTERNAL_SERVER_ERROR, "ApplicationID: " + applicationId));
        MerchantEntity merchantEntity = applicationEntity.getMerchantByMerchantId();

        try {
            MerchantProductEntity merchantProductEntity
                    = merchantProductRepository.findByMerchantIdAndProductByProductId(merchantEntity.getId(), walletProductEntity);
            if(merchantProductEntity != null) {
                ApplicationPaymentTypeEntity applicationPaymentTypeEntity
                        = applicationPaymentTypeRepository.findDistinctFirstByApplicationByApplicationIdAndPaymentTypeByPaymentTypeId(applicationEntity, paymentTypeEntity)
                        .orElseThrow(() -> new GenericException("Payment type not configured for application", HttpStatus.NOT_FOUND, "ApplicationID: " + applicationId));
                if(applicationPaymentTypeEntity != null) {

                    InterfaceEntity interfaceEntity = applicationPaymentTypeEntity.getInterfaceByInterfaceId();
                    MasterpassConfigResponse masterpassConfigResponse = new MasterpassConfigResponse();
                    masterpassConfigResponse.setMerchantMid(interfaceEntity.getMerchantMid());
                    masterpassConfigResponse.setCountryCode(interfaceEntity.getTradingCurrencyByTradingCurrencyId().getCountryByCountryId().getCode());
                    masterpassConfigResponse.setCurrencyCode(interfaceEntity.getTradingCurrencyByTradingCurrencyId().getCurrencyByCurrencyId().getCode());

                    MasterPassConfigEntity masterPassConfigEntity = masterPassConfigRepository.findByInterfaceId(interfaceEntity.getId());
                    masterpassConfigResponse.setApiKey(masterPassConfigEntity.getApiKey());
                    masterpassConfigResponse.setApiUsername(masterPassConfigEntity.getApiUsername());
                    masterpassConfigResponse.setNotificationKey(masterPassConfigEntity.getNotificationKey());

                    return masterpassConfigResponse;

                } else {
                    throw new GenericException("Payment type MASTER_PASS not configured for application", HttpStatus.INTERNAL_SERVER_ERROR, "ApplicationID: " + applicationEntity.getId());
                }

            } else {
                throw new GenericException("Merchant not registered for product", HttpStatus.INTERNAL_SERVER_ERROR, "MerchantID: " + merchantEntity.getId());
            }
        } catch (Exception e) {
            logger.error(e);
            throw new GenericException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, e.getLocalizedMessage());
        }
    }

    @Override
    public ResponseEntity<?> editMasterpassApplicationConfig(EditMasterpassConfigRequest editMasterpassConfigRequest) {
        ProductEntity walletProductEntity = productRepository.findByProductCode("QR");
        PaymentTypeEntity paymentTypeEntity = paymentTypeRepository.findByCode("MASTER_PASS");
        try {
            MerchantProductEntity merchantProductEntity
                    = merchantProductRepository.findByMerchantIdAndProductByProductId(editMasterpassConfigRequest.getMerchantId(), walletProductEntity);
            if(merchantProductEntity != null) {
                ApplicationEntity applicationEntity = applicationRepository.findById(editMasterpassConfigRequest.getApplicationId())
                        .orElseThrow(() -> new GenericException("Could not find application", HttpStatus.INTERNAL_SERVER_ERROR, "ApplicationID: " + editMasterpassConfigRequest.getApplicationId()));
                ApplicationPaymentTypeEntity applicationPaymentTypeEntity
                        = applicationPaymentTypeRepository.findDistinctFirstByApplicationByApplicationIdAndPaymentTypeByPaymentTypeId(applicationEntity, paymentTypeEntity)
                        .orElseThrow(() -> new GenericException("Payment type not configured for application", HttpStatus.NOT_FOUND, "ApplicationID: " + editMasterpassConfigRequest.getApplicationId()));

                InterfaceEntity interfaceEntity = applicationPaymentTypeEntity.getInterfaceByInterfaceId();
                interfaceEntity.setMerchantMid(editMasterpassConfigRequest.getMerchantMid());

                CurrencyEntity currencyEntity = currencyRepository.findByCode(editMasterpassConfigRequest.getCurrencyCode());
                CountryEntity countryEntity = countryRepository.findByCode(editMasterpassConfigRequest.getCountryCode());
                TradingCurrencyEntity tradingCurrencyEntity =
                        tradingCurrencyRepository.findByCurrencyByCurrencyIdAndCountryByCountryId(currencyEntity, countryEntity);
                if(tradingCurrencyEntity != null) {
                    interfaceEntity.setTradingCurrencyId(tradingCurrencyEntity.getId());
                } else {
                    TradingCurrencyEntity newTradingCurrencyEntity = new TradingCurrencyEntity();
                    newTradingCurrencyEntity.setCurrencyByCurrencyId(currencyEntity);
                    newTradingCurrencyEntity.setCountryByCountryId(countryEntity);
                    newTradingCurrencyEntity.setLastModified(new java.sql.Timestamp(new Date().getTime()));
                    TradingCurrencyEntity savedTradingCurrencyEntity = tradingCurrencyRepository.save(newTradingCurrencyEntity);
                    interfaceEntity.setTradingCurrencyId(savedTradingCurrencyEntity.getId());
                }
                InterfaceEntity savedInterfaceEntity = interfaceRepository.save(interfaceEntity);

                //payment type specific config
                MasterPassConfigEntity masterPassConfigEntity = masterPassConfigRepository.findByInterfaceId(savedInterfaceEntity.getId());
                masterPassConfigEntity.setApiUsername(editMasterpassConfigRequest.getApiUsername());
                masterPassConfigEntity.setApiKey(editMasterpassConfigRequest.getApiKey());
                masterPassConfigEntity.setNotificationKey(editMasterpassConfigRequest.getNotificationKey());
                MasterPassConfigEntity savedMasterPassConfigEntity = masterPassConfigRepository.save(masterPassConfigEntity);

                return ResponseEntity.ok().build();

            } else {
                throw new GenericException("Merchant not registered for product", HttpStatus.INTERNAL_SERVER_ERROR, "MerchantID: " + editMasterpassConfigRequest.getMerchantId());
            }
        } catch (Exception e) {
            logger.error(e);
            throw new GenericException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, e.getLocalizedMessage());
        }
    }

    @Override
    public ResponseEntity<?> createZapperApplicationConfig(CreateZapperConfigRequest createZapperConfigRequest) {
        ProductEntity walletProductEntity = productRepository.findByProductCode("QR");
        try {
            MerchantProductEntity merchantProductEntity
                    = merchantProductRepository.findByMerchantIdAndProductByProductId(createZapperConfigRequest.getMerchantId(), walletProductEntity);
            if(merchantProductEntity != null) {
                //create interface entity
                InterfaceEntity newInterfaceEntity = new InterfaceEntity();
                                MerchantEntity merchantEntity = merchantRepository
                        .findById(createZapperConfigRequest.getMerchantId())
                        .orElseThrow(() -> new GenericException("Could not find merchant with id | " + createZapperConfigRequest.getMerchantId(), HttpStatus.NOT_FOUND, "Could not find merchant with id | " + createZapperConfigRequest.getMerchantId()));
                newInterfaceEntity.setMerchantByMerchantId(merchantEntity);
                PaymentTypeEntity paymentTypeEntity = paymentTypeRepository.findByCode("ZAPPER");
                newInterfaceEntity.setPaymentTypeByPaymentTypeId(paymentTypeEntity);
                // Set gateway and security method
                GatewayEntity gatewayEntity = gatewayRepository.findByCode("1");
                SecurityMethodEntity securityMethodEntity = securityMethodRepository.findByCode("NONE");
                if (gatewayEntity != null) {
                    newInterfaceEntity.setGatewayByGatewayId(gatewayEntity);
                }
                if (securityMethodEntity != null) {
                    newInterfaceEntity.setSecurityMethodBySecurityMethodId(securityMethodEntity);
                }

                CurrencyEntity currencyEntity = currencyRepository.findByCode(createZapperConfigRequest.getCurrencyCode());
                CountryEntity countryEntity = countryRepository.findByCode(createZapperConfigRequest.getCountryCode());
                TradingCurrencyEntity tradingCurrencyEntity =
                        tradingCurrencyRepository.findByCurrencyByCurrencyIdAndCountryByCountryId(currencyEntity, countryEntity);
                if(tradingCurrencyEntity != null) {
                    newInterfaceEntity.setTradingCurrencyByTradingCurrencyId(tradingCurrencyEntity);
                } else {
                    TradingCurrencyEntity newTradingCurrencyEntity = new TradingCurrencyEntity();
                    newTradingCurrencyEntity.setCurrencyByCurrencyId(currencyEntity);
                    newTradingCurrencyEntity.setCountryByCountryId(countryEntity);
                    newTradingCurrencyEntity.setLastModified(new java.sql.Timestamp(new Date().getTime()));
                    TradingCurrencyEntity savedTradingCurrencyEntity = tradingCurrencyRepository.save(newTradingCurrencyEntity);
                    newInterfaceEntity.setTradingCurrencyByTradingCurrencyId(savedTradingCurrencyEntity);
                }

                ApplicationEntity applicationEntity = applicationRepository.findById(createZapperConfigRequest.getApplicationId())
                        .orElseThrow(() -> new GenericException("Could not find application", HttpStatus.INTERNAL_SERVER_ERROR, "ApplicationID: " + createZapperConfigRequest.getApplicationId()));
                newInterfaceEntity.setName(applicationEntity.getName() + ": Zapper Interface");
                newInterfaceEntity.setLastModified(new java.sql.Timestamp(new Date().getTime()));
                newInterfaceEntity.setMerchantByMerchantId(merchantEntity);
                newInterfaceEntity.setMerchantMid(createZapperConfigRequest.getMerchantMid());
                InterfaceEntity savedInterfaceEntity = interfaceRepository.save(newInterfaceEntity);

                ZapperInterfaceConfigEntity zapperInterfaceConfigEntity = new ZapperInterfaceConfigEntity();
                zapperInterfaceConfigEntity.setMerchantId(createZapperConfigRequest.getZapperMerchantId());
                zapperInterfaceConfigEntity.setSiteId(createZapperConfigRequest.getSiteId());
                zapperInterfaceConfigEntity.setSiteApiKey(createZapperConfigRequest.getSiteApiKey());
                zapperInterfaceConfigEntity.setMerchantApiKey(createZapperConfigRequest.getMerchantApiKey());
                zapperInterfaceConfigEntity.setInterfaceId(savedInterfaceEntity.getId());
                zapperInterfaceConfigRepository.save(zapperInterfaceConfigEntity);

                //create application payment type entity
                ApplicationPaymentTypeEntity newApplicationPaymentTypeEntity = new ApplicationPaymentTypeEntity();
                newApplicationPaymentTypeEntity.setApplicationByApplicationId(applicationEntity);
                newApplicationPaymentTypeEntity.setPaymentTypeByPaymentTypeId(paymentTypeEntity);
                newApplicationPaymentTypeEntity.setInterfaceByInterfaceId(savedInterfaceEntity);
                newApplicationPaymentTypeEntity.setActive(true);
                newApplicationPaymentTypeEntity.setCreatedDate(new java.sql.Timestamp(new Date().getTime()));
                newApplicationPaymentTypeEntity.setLastModified(new java.sql.Timestamp(new Date().getTime()));
                StatusEntity statusEntity = statusRepository.findByCode(Utils.MERCHANT_STATUS_DEVELOPING_CODE);
                newApplicationPaymentTypeEntity.setStatusByStatusId(statusEntity);
                ApplicationPaymentTypeEntity savedApplicationPaymentTypeEntity = applicationPaymentTypeRepository.save(newApplicationPaymentTypeEntity);

                return ResponseEntity.ok().build();

            } else {
                throw new GenericException("Merchant not registered for product", HttpStatus.INTERNAL_SERVER_ERROR, "MerchantID: " + createZapperConfigRequest.getMerchantId());
            }
        } catch (Exception e) {
            logger.error(e);
            throw new GenericException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, e.getLocalizedMessage());
        }
    }

    @Override
    public ZapperConfigResponse getZapperApplicationConfig(long applicationId) {
        ProductEntity walletProductEntity = productRepository.findByProductCode("QR");
        PaymentTypeEntity paymentTypeEntity = paymentTypeRepository.findByCode("ZAPPER");

        ApplicationEntity applicationEntity = applicationRepository.findById(applicationId)
                .orElseThrow(() -> new GenericException("Could not find application", HttpStatus.INTERNAL_SERVER_ERROR, "ApplicationID: " + applicationId));
        MerchantEntity merchantEntity = applicationEntity.getMerchantByMerchantId();

        try {
            MerchantProductEntity merchantProductEntity
                    = merchantProductRepository.findByMerchantIdAndProductByProductId(merchantEntity.getId(), walletProductEntity);
            if(merchantProductEntity != null) {
                ApplicationPaymentTypeEntity applicationPaymentTypeEntity
                        = applicationPaymentTypeRepository.findDistinctFirstByApplicationByApplicationIdAndPaymentTypeByPaymentTypeId(applicationEntity, paymentTypeEntity)
                        .orElseThrow(() -> new GenericException("Payment type not configured for application", HttpStatus.NOT_FOUND, "ApplicationID: " + applicationId));
                if(applicationPaymentTypeEntity != null) {

                    InterfaceEntity interfaceEntity = applicationPaymentTypeEntity.getInterfaceByInterfaceId();
                    ZapperConfigResponse zapperConfigResponse = new ZapperConfigResponse();
                    zapperConfigResponse.setMerchantMid(interfaceEntity.getMerchantMid());
                    zapperConfigResponse.setCountryCode(interfaceEntity.getTradingCurrencyByTradingCurrencyId().getCountryByCountryId().getCode());
                    zapperConfigResponse.setCurrencyCode(interfaceEntity.getTradingCurrencyByTradingCurrencyId().getCurrencyByCurrencyId().getCode());

                    ZapperInterfaceConfigEntity zapperInterfaceConfigEntity = zapperInterfaceConfigRepository.findByInterfaceId(interfaceEntity.getId());
                    zapperConfigResponse.setZapperMerchantId(zapperInterfaceConfigEntity.getMerchantId());
                    zapperConfigResponse.setSiteId(zapperInterfaceConfigEntity.getSiteId());
                    zapperConfigResponse.setSiteApiKey(zapperInterfaceConfigEntity.getSiteApiKey());
                    zapperConfigResponse.setMerchantApiKey(zapperInterfaceConfigEntity.getMerchantApiKey());

                    return zapperConfigResponse;

                } else {
                    throw new GenericException("Payment type ZAPPER not configured for application", HttpStatus.INTERNAL_SERVER_ERROR, "ApplicationID: " + applicationEntity.getId());
                }

            } else {
                throw new GenericException("Merchant not registered for product", HttpStatus.INTERNAL_SERVER_ERROR, "MerchantID: " + merchantEntity.getId());
            }
        } catch (Exception e) {
            logger.error(e);
            throw new GenericException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, e.getLocalizedMessage());
        }
    }

    @Override
    public ResponseEntity<?> editZapperApplicationConfig(EditZapperConfigRequest editZapperConfigRequest) {
        ProductEntity walletProductEntity = productRepository.findByProductCode("QR");
        PaymentTypeEntity paymentTypeEntity = paymentTypeRepository.findByCode("ZAPPER");
        try {
            MerchantProductEntity merchantProductEntity
                    = merchantProductRepository.findByMerchantIdAndProductByProductId(editZapperConfigRequest.getMerchantId(), walletProductEntity);
            if(merchantProductEntity != null) {
                ApplicationEntity applicationEntity = applicationRepository.findById(editZapperConfigRequest.getApplicationId())
                        .orElseThrow(() -> new GenericException("Could not find application", HttpStatus.INTERNAL_SERVER_ERROR, "ApplicationID: " + editZapperConfigRequest.getApplicationId()));
                ApplicationPaymentTypeEntity applicationPaymentTypeEntity
                        = applicationPaymentTypeRepository.findDistinctFirstByApplicationByApplicationIdAndPaymentTypeByPaymentTypeId(applicationEntity, paymentTypeEntity)
                        .orElseThrow(() -> new GenericException("Payment type not configured for application", HttpStatus.NOT_FOUND, "ApplicationID: " + editZapperConfigRequest.getApplicationId()));

                InterfaceEntity interfaceEntity = applicationPaymentTypeEntity.getInterfaceByInterfaceId();
                interfaceEntity.setMerchantMid(editZapperConfigRequest.getMerchantMid());

                CurrencyEntity currencyEntity = currencyRepository.findByCode(editZapperConfigRequest.getCurrencyCode());
                CountryEntity countryEntity = countryRepository.findByCode(editZapperConfigRequest.getCountryCode());
                TradingCurrencyEntity tradingCurrencyEntity =
                        tradingCurrencyRepository.findByCurrencyByCurrencyIdAndCountryByCountryId(currencyEntity, countryEntity);
                if(tradingCurrencyEntity != null) {
                    interfaceEntity.setTradingCurrencyId(tradingCurrencyEntity.getId());
                } else {
                    TradingCurrencyEntity newTradingCurrencyEntity = new TradingCurrencyEntity();
                    newTradingCurrencyEntity.setCurrencyByCurrencyId(currencyEntity);
                    newTradingCurrencyEntity.setCountryByCountryId(countryEntity);
                    newTradingCurrencyEntity.setLastModified(new java.sql.Timestamp(new Date().getTime()));
                    TradingCurrencyEntity savedTradingCurrencyEntity = tradingCurrencyRepository.save(newTradingCurrencyEntity);
                    interfaceEntity.setTradingCurrencyId(savedTradingCurrencyEntity.getId());
                }
                InterfaceEntity savedInterfaceEntity = interfaceRepository.save(interfaceEntity);

                //payment type specific config
                ZapperInterfaceConfigEntity zapperInterfaceConfigEntity = zapperInterfaceConfigRepository.findByInterfaceId(savedInterfaceEntity.getId());
                zapperInterfaceConfigEntity.setMerchantId(editZapperConfigRequest.getZapperMerchantId());
                zapperInterfaceConfigEntity.setSiteId(editZapperConfigRequest.getSiteId());
                zapperInterfaceConfigEntity.setSiteApiKey(editZapperConfigRequest.getSiteApiKey());
                zapperInterfaceConfigEntity.setMerchantApiKey(editZapperConfigRequest.getMerchantApiKey());
                ZapperInterfaceConfigEntity savedZapperInterfaceConfigEntity = zapperInterfaceConfigRepository.save(zapperInterfaceConfigEntity);

                return ResponseEntity.ok().build();

            } else {
                throw new GenericException("Merchant not registered for product", HttpStatus.INTERNAL_SERVER_ERROR, "MerchantID: " + editZapperConfigRequest.getMerchantId());
            }
        } catch (Exception e) {
            logger.error(e);
            throw new GenericException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, e.getLocalizedMessage());
        }
    }


    @Override
    public ResponseEntity<?> createOzowApplicationConfig(CreateOzowConfigRequest createOzowConfigRequest) {
        ProductEntity productEntity = productRepository.findByProductCode("EFT");
        try {
            MerchantProductEntity merchantProductEntity
                    = merchantProductRepository.findByMerchantIdAndProductByProductId(createOzowConfigRequest.getMerchantId(), productEntity);
            if(merchantProductEntity != null) {
                //create interface entity
                InterfaceEntity newInterfaceEntity = new InterfaceEntity();
                MerchantEntity merchantEntity = merchantRepository
                        .findById(createOzowConfigRequest.getMerchantId())
                        .orElseThrow(() -> new GenericException("Could not find merchant with id | " + createOzowConfigRequest.getMerchantId(), HttpStatus.NOT_FOUND, "Could not find merchant with id | " + createOzowConfigRequest.getMerchantId()));
                newInterfaceEntity.setMerchantByMerchantId(merchantEntity);
                PaymentTypeEntity paymentTypeEntity = paymentTypeRepository.findByCode("EFT_OZOW");
                newInterfaceEntity.setPaymentTypeByPaymentTypeId(paymentTypeEntity);
                // Set gateway and security method
                GatewayEntity gatewayEntity = gatewayRepository.findByCode("1");
                SecurityMethodEntity securityMethodEntity = securityMethodRepository.findByCode("NONE");
                if (gatewayEntity != null) {
                    newInterfaceEntity.setGatewayByGatewayId(gatewayEntity);
                }
                if (securityMethodEntity != null) {
                    newInterfaceEntity.setSecurityMethodBySecurityMethodId(securityMethodEntity);
                }

                CurrencyEntity currencyEntity = currencyRepository.findByCode(createOzowConfigRequest.getCurrencyCode());
                CountryEntity countryEntity = countryRepository.findByCode(createOzowConfigRequest.getCountryCode());
                TradingCurrencyEntity tradingCurrencyEntity =
                        tradingCurrencyRepository.findByCurrencyByCurrencyIdAndCountryByCountryId(currencyEntity, countryEntity);
                if(tradingCurrencyEntity != null) {
                    newInterfaceEntity.setTradingCurrencyByTradingCurrencyId(tradingCurrencyEntity);
                } else {
                    TradingCurrencyEntity newTradingCurrencyEntity = new TradingCurrencyEntity();
                    newTradingCurrencyEntity.setCurrencyByCurrencyId(currencyEntity);
                    newTradingCurrencyEntity.setCountryByCountryId(countryEntity);
                    newTradingCurrencyEntity.setLastModified(new java.sql.Timestamp(new Date().getTime()));
                    TradingCurrencyEntity savedTradingCurrencyEntity = tradingCurrencyRepository.save(newTradingCurrencyEntity);
                    newInterfaceEntity.setTradingCurrencyByTradingCurrencyId(savedTradingCurrencyEntity);
                }

                ApplicationEntity applicationEntity = applicationRepository.findById(createOzowConfigRequest.getApplicationId())
                        .orElseThrow(() -> new GenericException("Could not find application", HttpStatus.INTERNAL_SERVER_ERROR, "ApplicationID: " + createOzowConfigRequest.getApplicationId()));
                newInterfaceEntity.setName(applicationEntity.getName() + ": Ozow Interface");
                newInterfaceEntity.setLastModified(new java.sql.Timestamp(new Date().getTime()));
                newInterfaceEntity.setMerchantByMerchantId(merchantEntity);
                InterfaceEntity savedInterfaceEntity = interfaceRepository.save(newInterfaceEntity);

                OzowInterfaceConfigurationEntity ozowInterfaceConfigurationEntity = new OzowInterfaceConfigurationEntity();
                ozowInterfaceConfigurationEntity.setSiteCode(createOzowConfigRequest.getSiteCode());
                ozowInterfaceConfigurationEntity.setPrivateKey(createOzowConfigRequest.getPrivateKey());
                ozowInterfaceConfigurationEntity.setBankReference(createOzowConfigRequest.getBankReference());
                ozowInterfaceConfigurationEntity.setApiKey(createOzowConfigRequest.getApiKey());
                ozowInterfaceConfigurationEntity.setInterfaceId(savedInterfaceEntity.getId());
                ozowInterfaceConfigRepository.save(ozowInterfaceConfigurationEntity);

                //create application payment type entity
                ApplicationPaymentTypeEntity newApplicationPaymentTypeEntity = new ApplicationPaymentTypeEntity();
                newApplicationPaymentTypeEntity.setApplicationByApplicationId(applicationEntity);
                newApplicationPaymentTypeEntity.setPaymentTypeByPaymentTypeId(paymentTypeEntity);
                newApplicationPaymentTypeEntity.setInterfaceByInterfaceId(savedInterfaceEntity);
                newApplicationPaymentTypeEntity.setActive(true);
                newApplicationPaymentTypeEntity.setCreatedDate(new java.sql.Timestamp(new Date().getTime()));
                newApplicationPaymentTypeEntity.setLastModified(new java.sql.Timestamp(new Date().getTime()));
                StatusEntity statusEntity = statusRepository.findByCode(Utils.MERCHANT_STATUS_DEVELOPING_CODE);
                newApplicationPaymentTypeEntity.setStatusByStatusId(statusEntity);
                ApplicationPaymentTypeEntity savedApplicationPaymentTypeEntity = applicationPaymentTypeRepository.save(newApplicationPaymentTypeEntity);

                return ResponseEntity.ok().build();

            } else {
                throw new GenericException("Merchant not registered for product", HttpStatus.INTERNAL_SERVER_ERROR, "MerchantID: " + createOzowConfigRequest.getMerchantId());
            }
        } catch (Exception e) {
            logger.error(e);
            throw new GenericException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, e.getLocalizedMessage());
        }
    }

    @Override
    public OzowConfigResponse getOzowApplicationConfig(long applicationId) {
        ProductEntity productEntity = productRepository.findByProductCode("EFT");
        PaymentTypeEntity paymentTypeEntity = paymentTypeRepository.findByCode("EFT_OZOW");

        ApplicationEntity applicationEntity = applicationRepository.findById(applicationId)
                .orElseThrow(() -> new GenericException("Could not find application", HttpStatus.INTERNAL_SERVER_ERROR, "ApplicationID: " + applicationId));
        MerchantEntity merchantEntity = applicationEntity.getMerchantByMerchantId();

        try {
            MerchantProductEntity merchantProductEntity
                    = merchantProductRepository.findByMerchantIdAndProductByProductId(merchantEntity.getId(), productEntity);
            if(merchantProductEntity != null) {
                ApplicationPaymentTypeEntity applicationPaymentTypeEntity
                        = applicationPaymentTypeRepository.findDistinctFirstByApplicationByApplicationIdAndPaymentTypeByPaymentTypeId(applicationEntity, paymentTypeEntity)
                        .orElseThrow(() -> new GenericException("Payment type not configured for application", HttpStatus.NOT_FOUND, "ApplicationID: " + applicationId));
                if(applicationPaymentTypeEntity != null) {

                    InterfaceEntity interfaceEntity = applicationPaymentTypeEntity.getInterfaceByInterfaceId();
                    OzowConfigResponse ozowConfigResponse = new OzowConfigResponse();
                    ozowConfigResponse.setCountryCode(interfaceEntity.getTradingCurrencyByTradingCurrencyId().getCountryByCountryId().getCode());
                    ozowConfigResponse.setCurrencyCode(interfaceEntity.getTradingCurrencyByTradingCurrencyId().getCurrencyByCurrencyId().getCode());

                    OzowInterfaceConfigurationEntity ozowInterfaceConfigurationEntity = ozowInterfaceConfigRepository.findByInterfaceId(interfaceEntity.getId());
                    ozowConfigResponse.setApiKey(ozowInterfaceConfigurationEntity.getApiKey());
                    ozowConfigResponse.setBankReference(ozowInterfaceConfigurationEntity.getBankReference());
                    ozowConfigResponse.setPrivateKey(ozowInterfaceConfigurationEntity.getPrivateKey());
                    ozowConfigResponse.setSiteCode(ozowInterfaceConfigurationEntity.getSiteCode());

                    return ozowConfigResponse;

                } else {
                    throw new GenericException("Payment type OZOW_EFT not configured for application", HttpStatus.INTERNAL_SERVER_ERROR, "ApplicationID: " + applicationEntity.getId());
                }

            } else {
                throw new GenericException("Merchant not registered for product", HttpStatus.INTERNAL_SERVER_ERROR, "MerchantID: " + merchantEntity.getId());
            }
        } catch (Exception e) {
            logger.error(e);
            throw new GenericException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, e.getLocalizedMessage());
        }
    }

    @Override
    public ResponseEntity<?> editOzowApplicationConfig(EditOzowConfigRequest editOzowConfigRequest) {
        ProductEntity productEntity = productRepository.findByProductCode("EFT");
        PaymentTypeEntity paymentTypeEntity = paymentTypeRepository.findByCode("EFT_OZOW");
        try {
            MerchantProductEntity merchantProductEntity
                    = merchantProductRepository.findByMerchantIdAndProductByProductId(editOzowConfigRequest.getMerchantId(), productEntity);
            if(merchantProductEntity != null) {
                ApplicationEntity applicationEntity = applicationRepository.findById(editOzowConfigRequest.getApplicationId())
                        .orElseThrow(() -> new GenericException("Could not find application", HttpStatus.INTERNAL_SERVER_ERROR, "ApplicationID: " + editOzowConfigRequest.getApplicationId()));
                ApplicationPaymentTypeEntity applicationPaymentTypeEntity
                        = applicationPaymentTypeRepository.findDistinctFirstByApplicationByApplicationIdAndPaymentTypeByPaymentTypeId(applicationEntity, paymentTypeEntity)
                        .orElseThrow(() -> new GenericException("Payment type not configured for application", HttpStatus.NOT_FOUND, "ApplicationID: " + editOzowConfigRequest.getApplicationId()));

                InterfaceEntity interfaceEntity = applicationPaymentTypeEntity.getInterfaceByInterfaceId();

                CurrencyEntity currencyEntity = currencyRepository.findByCode(editOzowConfigRequest.getCurrencyCode());
                CountryEntity countryEntity = countryRepository.findByCode(editOzowConfigRequest.getCountryCode());
                TradingCurrencyEntity tradingCurrencyEntity =
                        tradingCurrencyRepository.findByCurrencyByCurrencyIdAndCountryByCountryId(currencyEntity, countryEntity);
                if(tradingCurrencyEntity != null) {
                    interfaceEntity.setTradingCurrencyId(tradingCurrencyEntity.getId());
                } else {
                    TradingCurrencyEntity newTradingCurrencyEntity = new TradingCurrencyEntity();
                    newTradingCurrencyEntity.setCurrencyByCurrencyId(currencyEntity);
                    newTradingCurrencyEntity.setCountryByCountryId(countryEntity);
                    newTradingCurrencyEntity.setLastModified(new java.sql.Timestamp(new Date().getTime()));
                    TradingCurrencyEntity savedTradingCurrencyEntity = tradingCurrencyRepository.save(newTradingCurrencyEntity);
                    interfaceEntity.setTradingCurrencyId(savedTradingCurrencyEntity.getId());
                }
                InterfaceEntity savedInterfaceEntity = interfaceRepository.save(interfaceEntity);

                //payment type specific config
                OzowInterfaceConfigurationEntity ozowInterfaceConfigurationEntity = ozowInterfaceConfigRepository.findByInterfaceId(savedInterfaceEntity.getId());
                ozowInterfaceConfigurationEntity.setApiKey(editOzowConfigRequest.getApiKey());
                ozowInterfaceConfigurationEntity.setBankReference(editOzowConfigRequest.getBankReference());
                ozowInterfaceConfigurationEntity.setPrivateKey(editOzowConfigRequest.getPrivateKey());
                ozowInterfaceConfigurationEntity.setSiteCode(editOzowConfigRequest.getSiteCode());
                ozowInterfaceConfigRepository.save(ozowInterfaceConfigurationEntity);

                return ResponseEntity.ok().build();

            } else {
                throw new GenericException("Merchant not registered for product", HttpStatus.INTERNAL_SERVER_ERROR, "MerchantID: " + editOzowConfigRequest.getMerchantId());
            }
        } catch (Exception e) {
            logger.error(e);
            throw new GenericException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, e.getLocalizedMessage());
        }
    }



    @Override
    public ResponseEntity<?> createStitchApplicationConfig(CreateStitchConfigRequest createStitchConfigRequest) {
        ProductEntity productEntity = productRepository.findByProductCode("EFT");
        try {
            MerchantProductEntity merchantProductEntity
                    = merchantProductRepository.findByMerchantIdAndProductByProductId(createStitchConfigRequest.getMerchantId(), productEntity);
            if(merchantProductEntity != null) {
                //create interface entity
                InterfaceEntity newInterfaceEntity = new InterfaceEntity();
                MerchantEntity merchantEntity = merchantRepository
                        .findById(createStitchConfigRequest.getMerchantId())
                        .orElseThrow(() -> new GenericException("Could not find merchant with id | " + createStitchConfigRequest.getMerchantId(), HttpStatus.NOT_FOUND, "Could not find merchant with id | " + createStitchConfigRequest.getMerchantId()));
                newInterfaceEntity.setMerchantByMerchantId(merchantEntity);
                PaymentTypeEntity paymentTypeEntity = paymentTypeRepository.findByCode("EFT_ADUMO");
                newInterfaceEntity.setPaymentTypeByPaymentTypeId(paymentTypeEntity);
                // Set gateway and security method
                GatewayEntity gatewayEntity = gatewayRepository.findByCode("1");
                SecurityMethodEntity securityMethodEntity = securityMethodRepository.findByCode("NONE");
                if (gatewayEntity != null) {
                    newInterfaceEntity.setGatewayByGatewayId(gatewayEntity);
                }
                if (securityMethodEntity != null) {
                    newInterfaceEntity.setSecurityMethodBySecurityMethodId(securityMethodEntity);
                }

                CurrencyEntity currencyEntity = currencyRepository.findByCode(createStitchConfigRequest.getCurrencyCode());
                CountryEntity countryEntity = countryRepository.findByCode(createStitchConfigRequest.getCountryCode());
                TradingCurrencyEntity tradingCurrencyEntity =
                        tradingCurrencyRepository.findByCurrencyByCurrencyIdAndCountryByCountryId(currencyEntity, countryEntity);
                if(tradingCurrencyEntity != null) {
                    newInterfaceEntity.setTradingCurrencyByTradingCurrencyId(tradingCurrencyEntity);
                } else {
                    TradingCurrencyEntity newTradingCurrencyEntity = new TradingCurrencyEntity();
                    newTradingCurrencyEntity.setCurrencyByCurrencyId(currencyEntity);
                    newTradingCurrencyEntity.setCountryByCountryId(countryEntity);
                    newTradingCurrencyEntity.setLastModified(new java.sql.Timestamp(new Date().getTime()));
                    TradingCurrencyEntity savedTradingCurrencyEntity = tradingCurrencyRepository.save(newTradingCurrencyEntity);
                    newInterfaceEntity.setTradingCurrencyByTradingCurrencyId(savedTradingCurrencyEntity);
                }

                ApplicationEntity applicationEntity = applicationRepository.findById(createStitchConfigRequest.getApplicationId())
                        .orElseThrow(() -> new GenericException("Could not find application", HttpStatus.INTERNAL_SERVER_ERROR, "ApplicationID: " + createStitchConfigRequest.getApplicationId()));
                newInterfaceEntity.setName(applicationEntity.getName() + ": Adumo EFT Interface");
                newInterfaceEntity.setLastModified(new java.sql.Timestamp(new Date().getTime()));
                newInterfaceEntity.setMerchantByMerchantId(merchantEntity);
                InterfaceEntity savedInterfaceEntity = interfaceRepository.save(newInterfaceEntity);

                //Adumo EFT specific config
                StitchInterfaceConfigurationEntity stitchInterfaceConfigurationEntity = new StitchInterfaceConfigurationEntity();
                stitchInterfaceConfigurationEntity.setInterfaceId(savedInterfaceEntity.getId());
                stitchInterfaceConfigurationEntity.setBankReference(createStitchConfigRequest.getBankReference());
                stitchInterfaceConfigurationEntity = stitchInterfaceConfigRepository.save(stitchInterfaceConfigurationEntity);

                StitchEftBankAccountEntity stitchEftBankAccountEntity = new StitchEftBankAccountEntity();
                stitchEftBankAccountEntity.setBankAccountNumber(createStitchConfigRequest.getAccountNumber());
                stitchEftBankAccountEntity.setBankId(createStitchConfigRequest.getBankId());
                stitchEftBankAccountEntity.setStitchInterfaceConfigurationId(stitchInterfaceConfigurationEntity.getId());
                stitchEftBankAccountRepository.save(stitchEftBankAccountEntity);

                //create application payment type entity
                ApplicationPaymentTypeEntity newApplicationPaymentTypeEntity = new ApplicationPaymentTypeEntity();
                newApplicationPaymentTypeEntity.setApplicationByApplicationId(applicationEntity);
                newApplicationPaymentTypeEntity.setPaymentTypeByPaymentTypeId(paymentTypeEntity);
                newApplicationPaymentTypeEntity.setInterfaceByInterfaceId(savedInterfaceEntity);
                newApplicationPaymentTypeEntity.setActive(true);
                newApplicationPaymentTypeEntity.setCreatedDate(new java.sql.Timestamp(new Date().getTime()));
                newApplicationPaymentTypeEntity.setLastModified(new java.sql.Timestamp(new Date().getTime()));
                StatusEntity statusEntity = statusRepository.findByCode(Utils.MERCHANT_STATUS_DEVELOPING_CODE);
                newApplicationPaymentTypeEntity.setStatusByStatusId(statusEntity);
                ApplicationPaymentTypeEntity savedApplicationPaymentTypeEntity = applicationPaymentTypeRepository.save(newApplicationPaymentTypeEntity);

                return ResponseEntity.ok().build();

            } else {
                throw new GenericException("Merchant not registered for product", HttpStatus.INTERNAL_SERVER_ERROR, "MerchantID: " + createStitchConfigRequest.getMerchantId());
            }
        } catch (Exception e) {
            logger.error(e);
            throw new GenericException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, e.getLocalizedMessage());
        }
    }

    @Override
    public StitchConfigResponse getStitchApplicationConfig(long applicationId) {
        ProductEntity productEntity = productRepository.findByProductCode("EFT");
        PaymentTypeEntity paymentTypeEntity = paymentTypeRepository.findByCode("EFT_ADUMO");

        ApplicationEntity applicationEntity = applicationRepository.findById(applicationId)
                .orElseThrow(() -> new GenericException("Could not find application", HttpStatus.INTERNAL_SERVER_ERROR, "ApplicationID: " + applicationId));
        MerchantEntity merchantEntity = applicationEntity.getMerchantByMerchantId();

        try {
            MerchantProductEntity merchantProductEntity
                    = merchantProductRepository.findByMerchantIdAndProductByProductId(merchantEntity.getId(), productEntity);
            if(merchantProductEntity != null) {
                ApplicationPaymentTypeEntity applicationPaymentTypeEntity
                        = applicationPaymentTypeRepository.findDistinctFirstByApplicationByApplicationIdAndPaymentTypeByPaymentTypeId(applicationEntity, paymentTypeEntity)
                        .orElseThrow(() -> new GenericException("Payment type not configured for application", HttpStatus.NOT_FOUND, "ApplicationID: " + applicationId));
                if(applicationPaymentTypeEntity != null) {

                    InterfaceEntity interfaceEntity = applicationPaymentTypeEntity.getInterfaceByInterfaceId();
                    StitchConfigResponse stitchConfigResponse = new StitchConfigResponse();
                    stitchConfigResponse.setCountryCode(interfaceEntity.getTradingCurrencyByTradingCurrencyId().getCountryByCountryId().getCode());
                    stitchConfigResponse.setCurrencyCode(interfaceEntity.getTradingCurrencyByTradingCurrencyId().getCurrencyByCurrencyId().getCode());

                    StitchInterfaceConfigurationEntity stitchInterfaceConfigurationEntity = stitchInterfaceConfigRepository.findByInterfaceId(interfaceEntity.getId());
                    stitchConfigResponse.setBankReference(stitchInterfaceConfigurationEntity.getBankReference());

                    StitchEftBankAccountEntity stitchEftBankAccountEntity = stitchEftBankAccountRepository.findByStitchInterfaceConfigurationId(stitchInterfaceConfigurationEntity.getId());
                    stitchConfigResponse.setAccountNumber(stitchEftBankAccountEntity.getBankAccountNumber());

                    stitchConfigResponse.setBank(stitchEftBanksRepository.findById(stitchEftBankAccountEntity.getBankId()));
                    return stitchConfigResponse;

                } else {
                    throw new GenericException("Payment type EFT_ADUMO not configured for application", HttpStatus.INTERNAL_SERVER_ERROR, "ApplicationID: " + applicationEntity.getId());
                }

            } else {
                throw new GenericException("Merchant not registered for product", HttpStatus.INTERNAL_SERVER_ERROR, "MerchantID: " + merchantEntity.getId());
            }
        } catch (Exception e) {
            logger.error(e);
            throw new GenericException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, e.getLocalizedMessage());
        }
    }

    @Override
    public ResponseEntity<?> editStitchApplicationConfig(EditStitchConfigRequest editStitchConfigRequest) {
        ProductEntity productEntity = productRepository.findByProductCode("EFT");
        PaymentTypeEntity paymentTypeEntity = paymentTypeRepository.findByCode("EFT_ADUMO");
        try {
            MerchantProductEntity merchantProductEntity
                    = merchantProductRepository.findByMerchantIdAndProductByProductId(editStitchConfigRequest.getMerchantId(), productEntity);
            if(merchantProductEntity != null) {
                ApplicationEntity applicationEntity = applicationRepository.findById(editStitchConfigRequest.getApplicationId())
                        .orElseThrow(() -> new GenericException("Could not find application", HttpStatus.INTERNAL_SERVER_ERROR, "ApplicationID: " + editStitchConfigRequest.getApplicationId()));
                ApplicationPaymentTypeEntity applicationPaymentTypeEntity
                        = applicationPaymentTypeRepository.findDistinctFirstByApplicationByApplicationIdAndPaymentTypeByPaymentTypeId(applicationEntity, paymentTypeEntity)
                        .orElseThrow(() -> new GenericException("Payment type not configured for application", HttpStatus.NOT_FOUND, "ApplicationID: " + editStitchConfigRequest.getApplicationId()));

                InterfaceEntity interfaceEntity = applicationPaymentTypeEntity.getInterfaceByInterfaceId();

                CurrencyEntity currencyEntity = currencyRepository.findByCode(editStitchConfigRequest.getCurrencyCode());
                CountryEntity countryEntity = countryRepository.findByCode(editStitchConfigRequest.getCountryCode());
                TradingCurrencyEntity tradingCurrencyEntity =
                        tradingCurrencyRepository.findByCurrencyByCurrencyIdAndCountryByCountryId(currencyEntity, countryEntity);
                if(tradingCurrencyEntity != null) {
                    interfaceEntity.setTradingCurrencyId(tradingCurrencyEntity.getId());
                } else {
                    TradingCurrencyEntity newTradingCurrencyEntity = new TradingCurrencyEntity();
                    newTradingCurrencyEntity.setCurrencyByCurrencyId(currencyEntity);
                    newTradingCurrencyEntity.setCountryByCountryId(countryEntity);
                    newTradingCurrencyEntity.setLastModified(new java.sql.Timestamp(new Date().getTime()));
                    TradingCurrencyEntity savedTradingCurrencyEntity = tradingCurrencyRepository.save(newTradingCurrencyEntity);
                    interfaceEntity.setTradingCurrencyId(savedTradingCurrencyEntity.getId());
                }
                InterfaceEntity savedInterfaceEntity = interfaceRepository.save(interfaceEntity);

                //stitch specific config
                StitchInterfaceConfigurationEntity stitchInterfaceConfigurationEntity = stitchInterfaceConfigRepository.findByInterfaceId(savedInterfaceEntity.getId());
                stitchInterfaceConfigurationEntity.setBankReference(editStitchConfigRequest.getBankReference());

                StitchEftBankAccountEntity stitchEftBankAccountEntity = stitchEftBankAccountRepository.findByStitchInterfaceConfigurationId(stitchInterfaceConfigurationEntity.getId());
                stitchEftBankAccountEntity.setBankId(editStitchConfigRequest.getBankId());
                stitchEftBankAccountEntity.setBankAccountNumber(editStitchConfigRequest.getAccountNumber());

                stitchInterfaceConfigRepository.save(stitchInterfaceConfigurationEntity);
                stitchEftBankAccountRepository.save(stitchEftBankAccountEntity);

                return ResponseEntity.ok().build();

            } else {
                throw new GenericException("Merchant not registered for product", HttpStatus.INTERNAL_SERVER_ERROR, "MerchantID: " + editStitchConfigRequest.getMerchantId());
            }
        } catch (Exception e) {
            logger.error(e);
            throw new GenericException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, e.getLocalizedMessage());
        }
    }





    @Override
    public List<CardTypeEntity> getCardTypes() {
        return cardTypeRepository.findAll();
    }

    @Override
    public StitchConfigData getStitchBanks() {
        return new StitchConfigData(stitchEftBanksRepository.findAll());
    }

    @Override
    public List<MerchantTypeEntity> getMerchantTypes() { return merchantTypeRepository.findAll(); }

    @Override
    public List<TokenizationMethodEntity> getTokenizationMethods() { return tokenizationMethodRepository.findAll(); }

    @Override
    public List<EciEntity> getEcis() { return eciRepository.findAll(); }

    @Override
    @Transactional
    public ResponseEntity<?> createCardConfigurationForApplication(long merchantId, long applicationId, long cardFlowId, CreateCardFlowConfigRequest createCardFlowConfigRequest) {

        CardConfigurationEntity cardConfigurationEntity = new CardConfigurationEntity();
        MerchantEntity merchantEntity = merchantRepository.findById(merchantId).orElseThrow(() -> new MerchantNotFoundException(merchantId));
        PaymentTypeEntity paymentTypeEntity = paymentTypeRepository.findByCode("CARD");
        ApplicationEntity applicationEntity = applicationRepository.findById(applicationId)
                .orElseThrow(() -> new GenericException("No application found with id | " + applicationId, HttpStatus.NOT_FOUND, "No application found with id | "));
        InterfaceEntity interfaceEntity;
        CardFlowEntity cardFlowEntity = cardFlowRepository.findById(cardFlowId)
                .orElseThrow(() -> new GenericException("Could not find card flow with id | " + cardFlowId, HttpStatus.NOT_FOUND, "Could not find card flow with id | "));

        // Check if interface already exists
        Optional<ApplicationPaymentTypeEntity> applicationPaymentTypeEntity = applicationPaymentTypeRepository.findDistinctFirstByApplicationByApplicationIdAndPaymentTypeByPaymentTypeId(applicationEntity, paymentTypeEntity);

        // Should perhaps add an isActive check...
        // cardFlowEntity.getCode().equalsIgnoreCase(CardFlow.CARD.value()) &&
        if (!applicationPaymentTypeEntity.isPresent()) {
            interfaceEntity = createInterface(merchantEntity, applicationEntity, paymentTypeEntity, cardConfigurationEntity, createCardFlowConfigRequest);
        } else {
            applicationPaymentTypeEntity
                    .orElseThrow(() -> new GenericException("Payment type not configured for application", HttpStatus.NOT_FOUND, "ApplicationID: " + applicationId));
            if(applicationPaymentTypeEntity != null) {
                interfaceEntity = applicationPaymentTypeEntity.get().getInterfaceByInterfaceId();
            } else {
                throw new GenericException("Basic card interface not found for application", HttpStatus.INTERNAL_SERVER_ERROR, "ApplicationID: " + applicationId);
            }

            //create card configuration
        }
        cardConfigurationEntity.setInterfaceId(interfaceEntity.getId());

        cardConfigurationEntity.setName(createCardFlowConfigRequest.getName());
        cardConfigurationEntity.setDescription(createCardFlowConfigRequest.getDescription());
        ProductEntity cardProductEntity = productRepository.findByProductCode("CARD");
        logger.info("Payload | " + createCardFlowConfigRequest.toString());
        try {
            MerchantProductEntity merchantProductEntity
                    = merchantProductRepository.findByMerchantIdAndProductByProductId(merchantId, cardProductEntity);
            if(merchantProductEntity != null) {
                //check for tokenization product enabled if selected
                logger.info("Tokenization enabled" + createCardFlowConfigRequest.isTokenizationEnabled());
                logger.info("Tokenization methods" + createCardFlowConfigRequest.getTokenizationMethods());
                logger.info("Tokenization methods size" + createCardFlowConfigRequest.getTokenizationMethods().size());
                if(createCardFlowConfigRequest.isTokenizationEnabled() || createCardFlowConfigRequest.getTokenizationMethods().size() > 0) {
                    ProductEntity tokenizationProductEntity = productRepository.findByProductCode("TOKENIZATION");
                    MerchantProductEntity tokenizationMerchantProductEntity
                            = merchantProductRepository.findByMerchantIdAndProductByProductId(merchantId, tokenizationProductEntity);
                    if(tokenizationMerchantProductEntity == null) {
                        throw new GenericException("Merchant not registered for tokenization product", HttpStatus.INTERNAL_SERVER_ERROR, "MerchantID: " + merchantId);
                    }
                }
                
                //create card configuration
                TdsMethodEntity tdsMethodEntity = tdsMethodRepository.findByCode(createCardFlowConfigRequest.getTdsMethod());
                cardConfigurationEntity.setTdsMethodId(tdsMethodEntity.getId());
                GatewayEntity gatewayEntity = gatewayRepository.findByCode(createCardFlowConfigRequest.getGateway());
                cardConfigurationEntity.setGatewayId(gatewayEntity.getId());
                cardConfigurationEntity.setCardFlowId(cardFlowEntity.getId());
                MerchantTypeEntity merchantTypeEntity = merchantTypeRepository.findByCode(createCardFlowConfigRequest.getMerchantType());
                cardConfigurationEntity.setMerchantTypeId(merchantTypeEntity.getId());
                CardConfigStatusEntity cardConfigStatusEntity = cardConfigStatusRepository.findByStatus("INACTIVE");
                cardConfigurationEntity.setCardConfigStatusByCardConfigStatusId(cardConfigStatusEntity);
                cardConfigurationEntity.setTokenizationEnabled(createCardFlowConfigRequest.isTokenizationEnabled());
                cardConfigurationEntity.setOnlyAuthenticatedToken(createCardFlowConfigRequest.isOnlyAuthenticatedToken());
                cardConfigurationEntity.setOnlyVerifiedToken(createCardFlowConfigRequest.isOnlyVerifiedToken());
                cardConfigurationEntity.setTdsRequired(createCardFlowConfigRequest.isTdsEnabled());
                cardConfigurationEntity.setCvvRequired(createCardFlowConfigRequest.isCvvRequired());
                cardConfigurationEntity.setAutoSettle(createCardFlowConfigRequest.isAutoSettle());
                CardConfigurationEntity savedCardConfigurationEntity = cardConfigurationRepository.save(cardConfigurationEntity);

                if (!checkConflictingCardConfigurations(savedCardConfigurationEntity.getId(), applicationId)){
                    logger.warn("Similar card configuratino already exist");
                }
                //link card types, tokenization methods, ecis
                for (String cardTypeCode : createCardFlowConfigRequest.getCardTypes()) {
                    CardTypeEntity cardTypeEntity = cardTypeRepository.findByCode(cardTypeCode);
                    CardTypeGroupEntity cardTypeGroupEntity = new CardTypeGroupEntity();
                    cardTypeGroupEntity.setCardTypeId(cardTypeEntity.getId());
                    cardTypeGroupEntity.setCardConfigurationId(savedCardConfigurationEntity.getId());
                    CardTypeGroupEntity savedCardTypeGroupEntity = cardTypeGroupRepository.save(cardTypeGroupEntity);
                }

                for (String eci : createCardFlowConfigRequest.getEcis()) {
                    EciEntity eciEntity = eciRepository.findByEci(eci);
                    CardConfigurationEciEntity cardConfigurationEciEntity = new CardConfigurationEciEntity();
                    cardConfigurationEciEntity.setEciId(eciEntity.getId());
                    cardConfigurationEciEntity.setCardConfigurationId(savedCardConfigurationEntity.getId());
                    CardConfigurationEciEntity savedCardConfigurationEciEntity = cardConfigurationEciRepository.save(cardConfigurationEciEntity);
                }

                for (String tokenizationMethod : createCardFlowConfigRequest.getTokenizationMethods()) {
                    TokenizationMethodEntity tokenizationMethodEntity = tokenizationMethodRepository.findByCode(tokenizationMethod);
                    CardConfigurationTokenizationMethodEntity cardConfigurationTokenizationMethodEntity = new CardConfigurationTokenizationMethodEntity();
                    cardConfigurationTokenizationMethodEntity.setCardConfigurationId(savedCardConfigurationEntity.getId());
                    cardConfigurationTokenizationMethodEntity.setTokenizationMethodId(tokenizationMethodEntity.getId());
                    CardConfigurationTokenizationMethodEntity savedCardConfigurationTokenizationMethodEntity = cardConfigurationTokenizationMethodRepository.save(cardConfigurationTokenizationMethodEntity);

                }

                // Tds method

                switch (tdsMethodEntity.getCode()) {
                    case "BANKSERV_V1":
                        BankservConfigurationEntity bankservConfigurationEntity = new BankservConfigurationEntity();
                        bankservConfigurationEntity.setCardConfigurationId(savedCardConfigurationEntity.getId());
                        bankservConfigurationEntity.setMerchantId(createCardFlowConfigRequest.getTdsProperties().getBankservConfiguration().getMerchantId());
                        bankservConfigurationEntity.setOrgUnitId(createCardFlowConfigRequest.getTdsProperties().getBankservConfiguration().getOrgUnitId());
                        bankservConfigurationEntity.setThreeDsPassword(createCardFlowConfigRequest.getTdsProperties().getBankservConfiguration().getThreeDsPassword());
                        bankservConfigurationEntity.setTransactionType(createCardFlowConfigRequest.getTdsProperties().getBankservConfiguration().getTransactionType());
                        bankservConfigurationRepository.save(bankservConfigurationEntity);
                        break;
                    case "BANKSERV_V2":
                        BankservConfigurationEntity bankservConfigurationEntity1 = new BankservConfigurationEntity();
                        bankservConfigurationEntity1.setCardConfigurationId(savedCardConfigurationEntity.getId());
                        bankservConfigurationEntity1.setMerchantId(createCardFlowConfigRequest.getTdsProperties().getBankservConfiguration().getMerchantId());
                        bankservConfigurationEntity1.setOrgUnitId(createCardFlowConfigRequest.getTdsProperties().getBankservConfiguration().getOrgUnitId());
                        bankservConfigurationEntity1.setThreeDsPassword(createCardFlowConfigRequest.getTdsProperties().getBankservConfiguration().getThreeDsPassword());
                        bankservConfigurationEntity1.setTransactionType(createCardFlowConfigRequest.getTdsProperties().getBankservConfiguration().getTransactionType());
                        bankservConfigurationRepository.save(bankservConfigurationEntity1);
                        break;
                    case "CARDINAL_V2":
                        CardinalConfigurationEntity cardinalConfigurationEntity1 = new CardinalConfigurationEntity();
                        cardinalConfigurationEntity1.setCardConfigurationId(savedCardConfigurationEntity.getId());
                        cardinalConfigurationEntity1.setOrgUnitId(createCardFlowConfigRequest.getTdsProperties().getCardinalConfiguration().getOrgUnitId());
                        cardinalConfigurationRepository.save(cardinalConfigurationEntity1);
                        break;
                    default:
                        break;
                }

                //save gateway config
                switch (createCardFlowConfigRequest.getGateway()) {
                    case "STANDARD_BANK":
                        StandardBankConfigurationEntity standardBankConfigurationEntity = new StandardBankConfigurationEntity();
                        standardBankConfigurationEntity.setCardConfigurationId(savedCardConfigurationEntity.getId());
                        standardBankConfigurationEntity.setMerchantMid(createCardFlowConfigRequest.getGatewayProperties().getStandardBankConfiguration().getMerchantMid());
                        standardBankConfigurationEntity.setMerchantCategoryCode(createCardFlowConfigRequest.getGatewayProperties().getStandardBankConfiguration().getMerchantCategoryCode());
                        standardBankConfigurationEntity.setTerminalId(createCardFlowConfigRequest.getGatewayProperties().getStandardBankConfiguration().getTerminalId());
                        standardBankConfigurationRepository.save(standardBankConfigurationEntity);
                        break;
                    case "BW":
                        BankWindhoekConfigurationEntity bankWindhoekConfigurationEntity = new BankWindhoekConfigurationEntity();
                        bankWindhoekConfigurationEntity.setCardConfigurationId(savedCardConfigurationEntity.getId());
                        bankWindhoekConfigurationEntity.setMerchantMid(createCardFlowConfigRequest.getGatewayProperties().getBankWindhoekConfiguration().getMerchantMid());
                        bankWindhoekConfigurationEntity.setMerchantCategoryCode(createCardFlowConfigRequest.getGatewayProperties().getBankWindhoekConfiguration().getMerchantCategoryCode());
                        bankWindhoekConfigurationEntity.setTerminalId(createCardFlowConfigRequest.getGatewayProperties().getBankWindhoekConfiguration().getTerminalId());
                        bankWindhoekConfigurationRepository.save(bankWindhoekConfigurationEntity);
                        break;
                    case "ABSA":
                        AbsaConfigurationEntity absaConfigurationEntity = new AbsaConfigurationEntity();
                        absaConfigurationEntity.setCardConfigurationId(savedCardConfigurationEntity.getId());
                        absaConfigurationEntity.setMerchantMid(createCardFlowConfigRequest.getGatewayProperties().getAbsaConfiguration().getMerchantMid());
                        absaConfigurationEntity.setMerchantCategoryCode(createCardFlowConfigRequest.getGatewayProperties().getAbsaConfiguration().getMerchantCategoryCode());
                        absaConfigurationEntity.setTerminalId(createCardFlowConfigRequest.getGatewayProperties().getAbsaConfiguration().getTerminalId());
                        absaConfigurationRepository.save(absaConfigurationEntity);
                        break;
                    case "FNB":
                        FnbConfigurationEntity fnbConfigurationEntity = new FnbConfigurationEntity();
                        fnbConfigurationEntity.setCardConfigurationId(savedCardConfigurationEntity.getId());
                        fnbConfigurationEntity.setMerchantMid(createCardFlowConfigRequest.getGatewayProperties().getFnbConfiguration().getMerchantMid());
                        fnbConfigurationEntity.setMerchantCategoryCode(createCardFlowConfigRequest.getGatewayProperties().getFnbConfiguration().getMerchantCategoryCode());
                        fnbConfigurationEntity.setTerminalId(createCardFlowConfigRequest.getGatewayProperties().getFnbConfiguration().getTerminalId());
                        fnbConfigurationRepository.save(fnbConfigurationEntity);
                        break;
                    case "NEDBANK":
                        IveriConfigurationEntity iveriConfigurationEntity = new IveriConfigurationEntity();
                        iveriConfigurationEntity.setCardConfigurationId(savedCardConfigurationEntity.getId());
                        iveriConfigurationEntity.setApplicationId(createCardFlowConfigRequest.getGatewayProperties().getIveriConfiguration().getApplicationId());
                        iveriConfigurationEntity.setMerchantCategoryCode(createCardFlowConfigRequest.getGatewayProperties().getIveriConfiguration().getMerchantCategoryCode());
                        iveriConfigurationEntity.setTerminalId(createCardFlowConfigRequest.getGatewayProperties().getIveriConfiguration().getTerminalId());
                        iveriConfigurationRepository.save(iveriConfigurationEntity);
                        break;
                    case "VACP":
                        VacpConfigurationEntity vacpConfigurationEntity = new VacpConfigurationEntity();
                        vacpConfigurationEntity.setCardConfigurationId(savedCardConfigurationEntity.getId());
                        vacpConfigurationEntity.setIgnoreAvsResult(createCardFlowConfigRequest.getGatewayProperties().getVacpConfiguration().isIgnoreAvsResult());
                        vacpConfigurationEntity.setIgnoreCvResult(createCardFlowConfigRequest.getGatewayProperties().getVacpConfiguration().isIgnoreCvResult());
                        vacpConfigurationEntity.setPartialAuthIndicator(createCardFlowConfigRequest.getGatewayProperties().getVacpConfiguration().isPartialAuthIndicator());
                        vacpConfigurationEntity.setUsername(createCardFlowConfigRequest.getGatewayProperties().getVacpConfiguration().getUsername());
                        vacpConfigurationEntity.setPassword(createCardFlowConfigRequest.getGatewayProperties().getVacpConfiguration().getPassword());
                        vacpConfigurationRepository.save(vacpConfigurationEntity);
                        break;
                    case "BOW":
                        BwConfigurationEntity bwConfigurationEntity = new BwConfigurationEntity();
                        bwConfigurationEntity.setCardConfigurationId(savedCardConfigurationEntity.getId());
                        bwConfigurationEntity.setUsername(createCardFlowConfigRequest.getGatewayProperties().getBwConfiguration().getUsername());
                        bwConfigurationEntity.setPassword(createCardFlowConfigRequest.getGatewayProperties().getBwConfiguration().getPassword());
                        bwConfigurationEntity.setTerminalId(createCardFlowConfigRequest.getGatewayProperties().getBwConfiguration().getTerminalId());
                        bwConfigurationRepository.save(bwConfigurationEntity);
                        break;
                    case "PLANET":
                        PlanetConfigurationEntity planetConfigurationEntity = new PlanetConfigurationEntity();
                        planetConfigurationEntity.setCardConfigurationId(savedCardConfigurationEntity.getId());
                        planetConfigurationEntity.setBankPassword(createCardFlowConfigRequest.getGatewayProperties().getPlanetConfiguration().getBankPassword());
                        planetConfigurationEntity.setBankUsername(createCardFlowConfigRequest.getGatewayProperties().getPlanetConfiguration().getBankUsername());
                        planetConfigurationEntity.setDescriptorCity(createCardFlowConfigRequest.getGatewayProperties().getPlanetConfiguration().getDescriptorCity());
                        planetConfigurationEntity.setDescriptorCompany(createCardFlowConfigRequest.getGatewayProperties().getPlanetConfiguration().getDescriptorCompany());
                        planetConfigurationEntity.setDescriptorCountry(createCardFlowConfigRequest.getGatewayProperties().getPlanetConfiguration().getDescriptorCountry());
                        planetConfigurationEntity.setDescriptorInfo(createCardFlowConfigRequest.getGatewayProperties().getPlanetConfiguration().getDescriptorInfo());
                        planetConfigurationEntity.setDescriptorState(createCardFlowConfigRequest.getGatewayProperties().getPlanetConfiguration().getDescriptorState());
                        planetConfigurationEntity.setTerminalId(createCardFlowConfigRequest.getGatewayProperties().getPlanetConfiguration().getTerminalId());
                        planetConfigurationEntity.setThreedsecureOrdUnitId(createCardFlowConfigRequest.getGatewayProperties().getPlanetConfiguration().getThreedsecureOrdUnitId());
                        planetConfigurationRepository.save(planetConfigurationEntity);
                        break;
                    default:
                        break;
                }


                return ResponseEntity.ok().build();

            } else {
                throw new GenericException("Merchant not registered for product", HttpStatus.INTERNAL_SERVER_ERROR, "MerchantID: " + merchantId);
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e);
            throw new GenericException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, e.getLocalizedMessage());
        }
    }

    @Override
    public List<GetCardFlowResponse> getAllCardConfigurationsForApplication(long applicationId) {
        ApplicationEntity applicationEntity = applicationRepository.findById(applicationId)
        .orElseThrow(() -> new GenericException("Could not find application", HttpStatus.INTERNAL_SERVER_ERROR, "ApplicationID: " + applicationId));

        PaymentTypeEntity paymentTypeEntity = paymentTypeRepository.findByCode("CARD");

        ApplicationPaymentTypeEntity applicationPaymentTypeEntity = applicationPaymentTypeRepository.findDistinctFirstByApplicationByApplicationIdAndPaymentTypeByPaymentTypeId(applicationEntity, paymentTypeEntity)
                .orElseThrow(() -> new GenericException("Payment type not configured for application", HttpStatus.NOT_FOUND, "ApplicationID: " + applicationId));

        List<CardConfigurationEntity> cardConfigurationEntities = cardConfigurationRepository.findAllByInterfaceByInterfaceId(applicationPaymentTypeEntity.getInterfaceByInterfaceId());
        return cardConfigurationEntities
                .stream()
                .map(cardConfigurationEntity -> getCardConfigurationForApplication(applicationId, cardConfigurationEntity.getId()))
                .sorted(Comparator.comparing(GetCardFlowResponse::getName))
                .collect(Collectors.toList());


    }

    @Override
    public GetCardFlowResponse getCardConfigurationForApplication(long applicationId, long cardConfigurationId) {

//        ApplicationEntity applicationEntity = applicationRepository.findById(applicationId)
//                .orElseThrow(() -> new GenericException("Could not find application", HttpStatus.INTERNAL_SERVER_ERROR, "ApplicationID: " + applicationId));
        // PaymentTypeEntity paymentTypeEntity = paymentTypeRepository.findByCode("CARD");
//        ApplicationPaymentTypeEntity applicationPaymentTypeEntity = applicationPaymentTypeRepository.findDistinctFirstByApplicationByApplicationIdAndPaymentTypeByPaymentTypeId(applicationEntity, paymentTypeEntity)
//                .orElseThrow(() -> new GenericException("Payment type not configured for application", HttpStatus.NOT_FOUND, "ApplicationID: " + applicationId));
        CardConfigurationEntity cardConfigurationEntity = cardConfigurationRepository.findById(cardConfigurationId)
                .orElseThrow(() -> new GenericException("Could not find card configruation with id | " + cardConfigurationId, HttpStatus.NOT_FOUND, "Could not find card configuration with id | " + cardConfigurationId));

        GetCardFlowResponse getCardFlowResponse = new GetCardFlowResponse();

        getCardFlowResponse.setId(cardConfigurationEntity.getId());

        // InterfaceEntity interfaceEntity = applicationPaymentTypeEntity.getInterfaceByInterfaceId();
        getCardFlowResponse.setCurrencyCode(cardConfigurationEntity.getInterfaceByInterfaceId().getTradingCurrencyByTradingCurrencyId().getCurrencyByCurrencyId().getCode());
        getCardFlowResponse.setCountryCode(cardConfigurationEntity.getInterfaceByInterfaceId().getTradingCurrencyByTradingCurrencyId().getCountryByCountryId().getCode());

        getCardFlowResponse.setOnlyAuthenticatedToken(cardConfigurationEntity.isOnlyAuthenticatedToken());
        getCardFlowResponse.setOnlyVerifiedToken(cardConfigurationEntity.isOnlyVerifiedToken());
        getCardFlowResponse.setTdsEnabled(cardConfigurationEntity.isTdsRequired());
        getCardFlowResponse.setCvvRequired(cardConfigurationEntity.isCvvRequired());
        getCardFlowResponse.setAutoSettle(cardConfigurationEntity.isAutoSettle());

        getCardFlowResponse.setTokenizationEnabled(cardConfigurationEntity.isTokenizationEnabled());
        getCardFlowResponse.setCardFlow(cardConfigurationEntity.getCardFlowByCardFlowId().getCode());


        // Set status
        getCardFlowResponse.setStatus(cardConfigurationEntity.getCardConfigStatusByCardConfigStatusId());

        // Set name, description
        getCardFlowResponse.setName(cardConfigurationEntity.getName());
        getCardFlowResponse.setDescription(cardConfigurationEntity.getDescription());

        List<String> setCardTypeGroups = new ArrayList<>();
        List<CardTypeGroupEntity> cardTypeGroupEntities = cardTypeGroupRepository.findAllByCardConfigurationId(cardConfigurationEntity.getId());
        for (CardTypeGroupEntity cardTypeGroupEntity : cardTypeGroupEntities) {
            CardTypeEntity cardTypeEntity = cardTypeRepository.findById(cardTypeGroupEntity.getCardTypeId());
            setCardTypeGroups.add(cardTypeEntity.getCode());
        }
        getCardFlowResponse.setCardTypes(setCardTypeGroups);
        List<String> setTokenizationMethods = new ArrayList<>();
        List<CardConfigurationTokenizationMethodEntity> cardConfigurationTokenizationMethodEntities = cardConfigurationTokenizationMethodRepository.findAllByCardConfigurationId(cardConfigurationEntity.getId());
        for (CardConfigurationTokenizationMethodEntity cCTMEntity : cardConfigurationTokenizationMethodEntities) {
            TokenizationMethodEntity tokenizationMethodEntity = tokenizationMethodRepository.findById(cCTMEntity.getTokenizationMethodId());
            setTokenizationMethods.add(tokenizationMethodEntity.getCode());
        }
        getCardFlowResponse.setTokenizationMethods(setTokenizationMethods);
        GatewayEntity gatewayEntity = gatewayRepository.findById(cardConfigurationEntity.getGatewayId());
        getCardFlowResponse.setGateway(gatewayEntity.getCode());
        MerchantTypeEntity merchantTypeEntity = merchantTypeRepository.findById(cardConfigurationEntity.getMerchantTypeId());
        getCardFlowResponse.setMerchantType(merchantTypeEntity.getCode());
        TdsMethodEntity tdsMethodEntity = tdsMethodRepository.findById(cardConfigurationEntity.getTdsMethodId());
        getCardFlowResponse.setTdsMethod(tdsMethodEntity.getCode());
        TdsProperties tdsProperties = new TdsProperties();

        switch (tdsMethodEntity.getCode()) {
            case "BANKSERV_V1":
            case "BANKSERV_V2":
                BankservConfigurationEntity bankservConfigurationEntity = bankservConfigurationRepository.findByCardConfigurationId(cardConfigurationEntity.getId());
                logger.info("BANKSERV EXISTING CONFIG | " + bankservConfigurationEntity.toString());
                BankservConfiguration bankservConfiguration = new BankservConfiguration();
                if (bankservConfigurationEntity != null) {
                    logger.info("BANKSERV ENTITY NOT NULL");
                    bankservConfiguration.setMerchantId(bankservConfigurationEntity.getMerchantId());
                    bankservConfiguration.setOrgUnitId(bankservConfigurationEntity.getOrgUnitId());
                    bankservConfiguration.setThreeDsPassword(bankservConfigurationEntity.getThreeDsPassword());
                    bankservConfiguration.setTransactionType(bankservConfigurationEntity.getTransactionType());
                }
                logger.info("BANKSERV OBJ AFTER SET | " + bankservConfiguration.toString());
                tdsProperties.setBankservConfiguration(bankservConfiguration);
                break;
            case "CARDINAL_V2":
                CardinalConfigurationEntity cardinalConfigurationEntity = cardinalConfigurationRepository.findByCardConfigurationId(cardConfigurationEntity.getId());
                CardinalConfiguration cardinalConfiguration = new CardinalConfiguration();
                if (cardinalConfigurationEntity != null) {
                    cardinalConfiguration.setOrgUnitId(cardinalConfigurationEntity.getOrgUnitId());
                }
                tdsProperties.setCardinalConfiguration(cardinalConfiguration);
                break;
            default:
                break;
        }

        List<String> setEciList = new ArrayList<>();
        List<CardConfigurationEciEntity> cardConfigurationEciEntities = cardConfigurationEciRepository.findAllByCardConfigurationId(cardConfigurationEntity.getId());
        for (CardConfigurationEciEntity cardConfigurationEciEntity : cardConfigurationEciEntities) {
            EciEntity eciEntity = eciRepository.findById(cardConfigurationEciEntity.getEciId());
            setEciList.add(eciEntity.getEci());
        }
        getCardFlowResponse.setEcis(setEciList);
        GatewayProperties gatewayProperties = new GatewayProperties();

        switch (gatewayEntity.getCode()) {
            case "STANDARD_BANK":
                StandardBankConfigurationEntity standardBankConfigurationEntity = standardBankConfigurationRepository.findByCardConfigurationId(cardConfigurationEntity.getId());
                StandardBankConfiguration standardBankConfiguration = new StandardBankConfiguration();
                if (standardBankConfigurationEntity != null) {
                    standardBankConfiguration.setMerchantMid(standardBankConfigurationEntity.getMerchantMid());
                    standardBankConfiguration.setMerchantCategoryCode(standardBankConfigurationEntity.getMerchantCategoryCode());
                    standardBankConfiguration.setTerminalId(standardBankConfigurationEntity.getTerminalId());
                }
                gatewayProperties.setStandardBankConfiguration(standardBankConfiguration);
                break;
            case "BW":
                BankWindhoekConfigurationEntity bankWindhoekConfigurationEntity = bankWindhoekConfigurationRepository.findByCardConfigurationId(cardConfigurationEntity.getId());
                BankWindhoekConfiguration bankWindhoekConfiguration = new BankWindhoekConfiguration();
                if (bankWindhoekConfigurationEntity != null) {
                    bankWindhoekConfiguration.setMerchantMid(bankWindhoekConfigurationEntity.getMerchantMid());
                    bankWindhoekConfiguration.setMerchantCategoryCode(bankWindhoekConfigurationEntity.getMerchantCategoryCode());
                    bankWindhoekConfiguration.setTerminalId(bankWindhoekConfigurationEntity.getTerminalId());
                }
                gatewayProperties.setBankWindhoekConfiguration(bankWindhoekConfiguration);
                break;
            case "ABSA":
                AbsaConfigurationEntity absaConfigurationEntity = absaConfigurationRepository.findByCardConfigurationId(cardConfigurationEntity.getId());
                AbsaConfiguration absaConfiguration = new AbsaConfiguration();
                if (absaConfigurationEntity != null) {
                    absaConfiguration.setMerchantMid(absaConfigurationEntity.getMerchantMid());
                    absaConfiguration.setMerchantCategoryCode(absaConfigurationEntity.getMerchantCategoryCode());
                    absaConfiguration.setTerminalId(absaConfigurationEntity.getTerminalId());
                }
                gatewayProperties.setAbsaConfiguration(absaConfiguration);
                break;
            case "FNB":
                FnbConfigurationEntity fnbConfigurationEntity = fnbConfigurationRepository.findByCardConfigurationId(cardConfigurationEntity.getId());
                FnbConfiguration fnbConfiguration = new FnbConfiguration();
                if (fnbConfigurationEntity != null) {
                    fnbConfiguration.setMerchantMid(fnbConfigurationEntity.getMerchantMid());
                    fnbConfiguration.setMerchantCategoryCode(fnbConfigurationEntity.getMerchantCategoryCode());
                    fnbConfiguration.setTerminalId(fnbConfigurationEntity.getTerminalId());
                }
                gatewayProperties.setFnbConfiguration(fnbConfiguration);
                break;
            case "NEDBANK":
                IveriConfigurationEntity iveriConfigurationEntity = iveriConfigurationRepository.findByCardConfigurationId(cardConfigurationEntity.getId());
                IveriConfiguration iveriConfiguration = new IveriConfiguration();
                if (iveriConfigurationEntity != null) {
                    iveriConfiguration.setApplicationId(iveriConfigurationEntity.getApplicationId());
                    iveriConfiguration.setMerchantCategoryCode(iveriConfigurationEntity.getMerchantCategoryCode());
                    iveriConfiguration.setTerminalId(iveriConfigurationEntity.getTerminalId());
                }
                gatewayProperties.setIveriConfiguration(iveriConfiguration);
                break;
            case "VACP":
                VacpConfigurationEntity vacpConfigurationEntity = vacpConfigurationRepository.findByCardConfigurationId(cardConfigurationEntity.getId());
                VacpConfiguration vacpConfiguration = new VacpConfiguration();
                if (vacpConfigurationEntity != null) {
                    vacpConfiguration.setIgnoreAvsResult(vacpConfigurationEntity.isIgnoreAvsResult());
                    vacpConfiguration.setIgnoreCvResult(vacpConfigurationEntity.isIgnoreCvResult());
                    vacpConfiguration.setPartialAuthIndicator(vacpConfigurationEntity.isPartialAuthIndicator());
                    vacpConfiguration.setUsername(vacpConfigurationEntity.getUsername());
                    vacpConfiguration.setPassword(vacpConfigurationEntity.getPassword());
                }
                gatewayProperties.setVacpConfiguration(vacpConfiguration);
                break;
            case "BOW":
                BwConfigurationEntity bwConfigurationEntity = bwConfigurationRepository.findByCardConfigurationId(cardConfigurationEntity.getId());
                BwConfiguration bwConfiguration = new BwConfiguration();
                if (bwConfigurationEntity != null) {
                    bwConfiguration.setUsername(bwConfigurationEntity.getUsername());
                    bwConfiguration.setPassword(bwConfigurationEntity.getPassword());
                    bwConfiguration.setTerminalId(bwConfigurationEntity.getTerminalId());
                }
                gatewayProperties.setBwConfiguration(bwConfiguration);
                break;
            case "PLANET":
                PlanetConfigurationEntity planetConfigurationEntity = planetConfigurationRepository.findByCardConfigurationId(cardConfigurationEntity.getId());
                PlanetConfiguration planetConfiguration = new PlanetConfiguration();
                if (planetConfigurationEntity != null) {
                    planetConfiguration.setBankPassword(planetConfigurationEntity.getBankPassword());
                    planetConfiguration.setBankUsername(planetConfigurationEntity.getBankUsername());
                    planetConfiguration.setDescriptorCity(planetConfigurationEntity.getDescriptorCity());
                    planetConfiguration.setDescriptorCompany(planetConfigurationEntity.getDescriptorCompany());
                    planetConfiguration.setDescriptorCountry(planetConfigurationEntity.getDescriptorCountry());
                    planetConfiguration.setDescriptorInfo(planetConfigurationEntity.getDescriptorInfo());
                    planetConfiguration.setDescriptorState(planetConfigurationEntity.getDescriptorState());
                    planetConfiguration.setTerminalId(planetConfigurationEntity.getTerminalId());
                    planetConfiguration.setThreedsecureOrdUnitId(planetConfigurationEntity.getThreedsecureOrdUnitId());
                }
                gatewayProperties.setPlanetConfiguration(planetConfiguration);
                break;
            default:
                break;
        }

        getCardFlowResponse.setGatewayProperties(gatewayProperties);
        getCardFlowResponse.setTdsProperties(tdsProperties);

        return getCardFlowResponse;
    }

    private InterfaceEntity createInterface(MerchantEntity merchantEntity, ApplicationEntity applicationEntityParam, PaymentTypeEntity paymentTypeEntity, CardConfigurationEntity cardConfigurationEntity, CreateCardFlowConfigRequest createCardFlowConfigRequest) {
        //create interface entity
        InterfaceEntity newInterfaceEntity = new InterfaceEntity();
        newInterfaceEntity.setMerchantId(merchantEntity.getId());
        newInterfaceEntity.setPaymentTypeId(paymentTypeEntity.getId());
        CurrencyEntity currencyEntity = currencyRepository.findByCode(createCardFlowConfigRequest.getCurrencyCode());
        CountryEntity countryEntity = countryRepository.findByCode(createCardFlowConfigRequest.getCountryCode());
        TradingCurrencyEntity tradingCurrencyEntity =
                tradingCurrencyRepository.findByCurrencyByCurrencyIdAndCountryByCountryId(currencyEntity, countryEntity);
        // Set gateway and security method
        GatewayEntity gatewayEntity = gatewayRepository.findById(cardConfigurationEntity.getGatewayId());
        SecurityMethodEntity securityMethodEntity = securityMethodRepository.findByCode(createCardFlowConfigRequest.getTdsMethod());
        if (gatewayEntity != null) {
            newInterfaceEntity.setGatewayByGatewayId(gatewayEntity);
        }
        if (securityMethodEntity != null) {
            newInterfaceEntity.setSecurityMethodBySecurityMethodId(securityMethodEntity);
        }
        newInterfaceEntity.setFraudEnabled(true);
        newInterfaceEntity.setCardVersion(2);
        if (tradingCurrencyEntity != null) {
            newInterfaceEntity.setTradingCurrencyByTradingCurrencyId(tradingCurrencyEntity);
        } else {
            TradingCurrencyEntity newTradingCurrencyEntity = new TradingCurrencyEntity();
            newTradingCurrencyEntity.setCurrencyByCurrencyId(currencyEntity);
            newTradingCurrencyEntity.setCountryByCountryId(countryEntity);
            newTradingCurrencyEntity.setLastModified(new java.sql.Timestamp(new Date().getTime()));
            TradingCurrencyEntity savedTradingCurrencyEntity = tradingCurrencyRepository.save(newTradingCurrencyEntity);
            newInterfaceEntity.setTradingCurrencyByTradingCurrencyId(savedTradingCurrencyEntity);
        }

        ApplicationEntity applicationEntity = applicationRepository.findById(applicationEntityParam.getId())
                .orElseThrow(() -> new GenericException("Could not find application", HttpStatus.INTERNAL_SERVER_ERROR, "ApplicationID: " + applicationEntityParam.getId()));
        newInterfaceEntity.setName(applicationEntity.getName() + ": Card Interface");
        newInterfaceEntity.setLastModified(new java.sql.Timestamp(new Date().getTime()));
        newInterfaceEntity.setMerchantByMerchantId(merchantEntity);
        newInterfaceEntity.setPaymentTypeByPaymentTypeId(paymentTypeEntity);
        // newInterfaceEntity.setTradingCurrencyByTradingCurrencyId(tradingCurrencyEntity);
        // newInterfaceEntity.setMerchantMid(createCardFlowConfigRequest.getMerchantMid());
        InterfaceEntity savedInterfaceEntity = interfaceRepository.save(newInterfaceEntity);

        //create application payment type entity
        ApplicationPaymentTypeEntity newApplicationPaymentTypeEntity = new ApplicationPaymentTypeEntity();
        newApplicationPaymentTypeEntity.setPaymentTypeByPaymentTypeId(paymentTypeEntity);
        newApplicationPaymentTypeEntity.setInterfaceByInterfaceId(savedInterfaceEntity);
        newApplicationPaymentTypeEntity.setApplicationByApplicationId(applicationEntity);
        newApplicationPaymentTypeEntity.setActive(true);
        newApplicationPaymentTypeEntity.setCreatedDate(new java.sql.Timestamp(new Date().getTime()));
        newApplicationPaymentTypeEntity.setLastModified(new java.sql.Timestamp(new Date().getTime()));
        StatusEntity statusEntity = statusRepository.findByCode(Utils.MERCHANT_STATUS_DEVELOPING_CODE);
        newApplicationPaymentTypeEntity.setStatusByStatusId(statusEntity);
        ApplicationPaymentTypeEntity savedApplicationPaymentTypeEntity = applicationPaymentTypeRepository.save(newApplicationPaymentTypeEntity);
        return newInterfaceEntity;
    }

    @Override
    @Transactional
    public ResponseEntity<?> editCardConfigurationForApplication(long merchantId, long applicationId, long cardConfigurationId, CreateCardFlowConfigRequest createCardFlowConfigRequest) {
        ProductEntity walletProductEntity = productRepository.findByProductCode("CARD");
        // try {
            MerchantEntity merchantEntity = merchantRepository.findById(merchantId).orElseThrow(() -> new MerchantNotFoundException(merchantId));
            MerchantProductEntity merchantProductEntity
                    = merchantProductRepository.findByMerchantIdAndProductByProductId(merchantId, walletProductEntity);
            //find card configuration and edit
            CardConfigurationEntity cardConfigurationEntity = cardConfigurationRepository.findById(cardConfigurationId)
                    .orElseThrow(() -> new GenericException(String.format("No card configuration with id | %s | exists", cardConfigurationId), HttpStatus.NOT_FOUND, String.format("No card configuration with id | %s | exists", cardConfigurationId)));
            if(merchantProductEntity != null) {
                //check for tokenization product enabled if selected
                if(createCardFlowConfigRequest.isTokenizationEnabled() || createCardFlowConfigRequest.getTokenizationMethods().size() > 0) {
                    ProductEntity tokenizationProductEntity = productRepository.findByProductCode("TOKENIZATION");
                    MerchantProductEntity tokenizationMerchantProductEntity
                            = merchantProductRepository.findByMerchantIdAndProductByProductId(merchantId, tokenizationProductEntity);
                    if(tokenizationMerchantProductEntity == null) {
                        throw new GenericException("Merchant not registered for tokenization product", HttpStatus.INTERNAL_SERVER_ERROR, "MerchantID: " + merchantId);
                    }
                }

                //find interface entity by application payment type and edit
                PaymentTypeEntity paymentTypeEntity = paymentTypeRepository.findByCode("CARD");
                ApplicationEntity applicationEntity = applicationRepository.findById(applicationId)
                        .orElseThrow(() -> new GenericException("Could not find application", HttpStatus.INTERNAL_SERVER_ERROR, "ApplicationID: " + applicationId));
                ApplicationPaymentTypeEntity applicationPaymentTypeEntity = applicationPaymentTypeRepository.findDistinctFirstByApplicationByApplicationIdAndPaymentTypeByPaymentTypeId(applicationEntity, paymentTypeEntity)
                        .orElseThrow(() -> new GenericException("Payment type not configured for application", HttpStatus.NOT_FOUND, "ApplicationID: " + applicationId));
                InterfaceEntity interfaceEntity = applicationPaymentTypeEntity.getInterfaceByInterfaceId();

                CurrencyEntity currencyEntity = currencyRepository.findByCode(createCardFlowConfigRequest.getCurrencyCode());
                CountryEntity countryEntity = countryRepository.findByCode(createCardFlowConfigRequest.getCountryCode());
                TradingCurrencyEntity tradingCurrencyEntity =
                        tradingCurrencyRepository.findByCurrencyByCurrencyIdAndCountryByCountryId(currencyEntity, countryEntity);
                if(tradingCurrencyEntity != null) {
                    interfaceEntity.setTradingCurrencyId(tradingCurrencyEntity.getId());
                } else {
                    TradingCurrencyEntity newTradingCurrencyEntity = new TradingCurrencyEntity();
                    newTradingCurrencyEntity.setCurrencyByCurrencyId(currencyEntity);
                    newTradingCurrencyEntity.setCountryByCountryId(countryEntity);
                    newTradingCurrencyEntity.setLastModified(new java.sql.Timestamp(new Date().getTime()));
                    TradingCurrencyEntity savedTradingCurrencyEntity = tradingCurrencyRepository.save(newTradingCurrencyEntity);
                    interfaceEntity.setTradingCurrencyId(savedTradingCurrencyEntity.getId());
                }

                logger.info("!!! --- Save Interface --- !!!");

                interfaceEntity.setLastModified(new java.sql.Timestamp(new Date().getTime()));
                InterfaceEntity savedInterfaceEntity = interfaceRepository.save(interfaceEntity);

                logger.info("!!! --- Saved Interface --- !!!");

                cardConfigurationEntity.setName(createCardFlowConfigRequest.getName());
                cardConfigurationEntity.setDescription(createCardFlowConfigRequest.getDescription());
                TdsMethodEntity tdsMethodEntity = tdsMethodRepository.findByCode(createCardFlowConfigRequest.getTdsMethod());

                // Check if tds method details should be deleted
                if (EnumUtils.isValidEnum(TdsMethodEnum.class, tdsMethodEntity.getCode())) {
                    // If there is 3d secure data
                    TdsMethodEntity tdsMethodEntityOld = tdsMethodRepository.findById(cardConfigurationEntity.getTdsMethodId());
                    if (!tdsMethodEntity.getCode().equalsIgnoreCase(tdsMethodEntityOld.getCode())) {
                        // If it's not the same as the old one, delete the old one.
                        if (tdsMethodEntity.getCode().equalsIgnoreCase(TdsMethodEnum.BANKSERV_V1.value()) || tdsMethodEntity.getCode().equalsIgnoreCase(TdsMethodEnum.BANKSERV_V2.value())) {
                            // Delete Bankserv config entity
                            BankservConfigurationEntity bankservConfigurationEntity = bankservConfigurationRepository.findByCardConfigurationId(cardConfigurationEntity.getId());
                            if (bankservConfigurationEntity != null) {
                                bankservConfigurationRepository.delete(bankservConfigurationEntity);
                            }
                        } else if (tdsMethodEntity.getCode().equalsIgnoreCase(TdsMethodEnum.CARDINAL_V2.value())) {
                            // Delete Cardinal config entity
                            CardinalConfigurationEntity cardinalConfigurationEntity = cardinalConfigurationRepository.findByCardConfigurationId(cardConfigurationEntity.getId());
                            if (cardinalConfigurationEntity != null) {
                                cardinalConfigurationRepository.delete(cardinalConfigurationEntity);
                            }
                        }
                    }
                } else {
                    // if there isn't 3d secure data
                    BankservConfigurationEntity bankservConfigurationEntity = bankservConfigurationRepository.findByCardConfigurationId(cardConfigurationEntity.getId());
                    if (bankservConfigurationEntity != null) {
                        bankservConfigurationRepository.delete(bankservConfigurationEntity);
                    }
                    CardinalConfigurationEntity cardinalConfigurationEntity = cardinalConfigurationRepository.findByCardConfigurationId(cardConfigurationEntity.getId());
                    if (cardinalConfigurationEntity != null) {
                        cardinalConfigurationRepository.delete(cardinalConfigurationEntity);
                    }
                }
                cardConfigurationEntity.setTdsMethodId(tdsMethodEntity.getId());

                GatewayEntity gatewayEntity = gatewayRepository.findByCode(createCardFlowConfigRequest.getGateway());
                // Delete old gateway config if gateway is different
                GatewayEntity gatewayEntityOld = gatewayRepository.findById(cardConfigurationEntity.getGatewayId());
                if (!gatewayEntity.getCode().equalsIgnoreCase(gatewayEntityOld.getCode())) {
                    // If it's not the same as the old one, delete the old one.
                    if (gatewayEntity.getCode().equalsIgnoreCase(GatewayEnum.ABSA.value())) {
                        AbsaConfigurationEntity absaConfigurationEntity = absaConfigurationRepository.findByCardConfigurationId(cardConfigurationEntity.getId());
                        if (absaConfigurationEntity != null) {
                            absaConfigurationRepository.delete(absaConfigurationEntity);
                        }
                    } else if (gatewayEntity.getCode().equalsIgnoreCase(GatewayEnum.FNB.value())) {
                        FnbConfigurationEntity fnbConfigurationEntity = fnbConfigurationRepository.findByCardConfigurationId(cardConfigurationEntity.getId());
                        if (fnbConfigurationEntity != null) {
                            fnbConfigurationRepository.delete(fnbConfigurationEntity);
                        }
                    } else if (gatewayEntity.getCode().equalsIgnoreCase(GatewayEnum.STANDARD_BANK.value())) {
                        StandardBankConfigurationEntity standardBankConfigurationEntity = standardBankConfigurationRepository.findByCardConfigurationId(cardConfigurationEntity.getId());
                        if (standardBankConfigurationEntity != null) {
                            standardBankConfigurationRepository.delete(standardBankConfigurationEntity);
                        }
                    } else if (gatewayEntity.getCode().equalsIgnoreCase(GatewayEnum.BW.value())) {
                        BankWindhoekConfigurationEntity bankWindhoekConfigurationEntity = bankWindhoekConfigurationRepository.findByCardConfigurationId(cardConfigurationEntity.getId());
                        if (bankWindhoekConfigurationEntity != null) {
                            bankWindhoekConfigurationRepository.delete(bankWindhoekConfigurationEntity);
                        }
                    } else if (gatewayEntity.getCode().equalsIgnoreCase(GatewayEnum.NEDBANK.value())) {
                        IveriConfigurationEntity iveriConfigurationEntity = iveriConfigurationRepository.findByCardConfigurationId(cardConfigurationEntity.getId());
                        if (iveriConfigurationEntity != null) {
                            iveriConfigurationRepository.delete(iveriConfigurationEntity);
                        }
                    } else if (gatewayEntity.getCode().equalsIgnoreCase(GatewayEnum.PLANET_PAYMENT_MCP.value())) {
                        PlanetConfigurationEntity planetConfigurationEntity = planetConfigurationRepository.findByCardConfigurationId(cardConfigurationEntity.getId());
                        if (planetConfigurationEntity != null) {
                            planetConfigurationRepository.delete(planetConfigurationEntity);
                        }
                    } else if (gatewayEntity.getCode().equalsIgnoreCase(GatewayEnum.VACP.value())) {
                        VacpConfigurationEntity vacpConfigurationEntity = vacpConfigurationRepository.findByCardConfigurationId(cardConfigurationEntity.getId());
                        if (vacpConfigurationEntity != null) {
                            vacpConfigurationRepository.delete(vacpConfigurationEntity);
                        }
                    } else if (gatewayEntity.getCode().equalsIgnoreCase(GatewayEnum.BOW.value())) {
                        BwConfigurationEntity bwConfigurationEntity = bwConfigurationRepository.findByCardConfigurationId(cardConfigurationEntity.getId());
                        if (bwConfigurationEntity != null) {
                            bwConfigurationRepository.delete(bwConfigurationEntity);
                        }
                    }
                }

                cardConfigurationEntity.setGatewayId(gatewayEntity.getId());
                MerchantTypeEntity merchantTypeEntity = merchantTypeRepository.findByCode(createCardFlowConfigRequest.getMerchantType());
                cardConfigurationEntity.setMerchantTypeId(merchantTypeEntity.getId());


                CardConfigStatusEntity cardConfigStatusEntity = cardConfigStatusRepository.findByStatus("INACTIVE");
                String oldStatus = cardConfigurationEntity.getCardConfigStatusByCardConfigStatusId().getStatus();

                cardConfigurationEntity.setCardConfigStatusByCardConfigStatusId(cardConfigStatusEntity);
                cardConfigurationEntity.setTokenizationEnabled(createCardFlowConfigRequest.isTokenizationEnabled());
                cardConfigurationEntity.setOnlyAuthenticatedToken(createCardFlowConfigRequest.isOnlyAuthenticatedToken());
                cardConfigurationEntity.setOnlyVerifiedToken(createCardFlowConfigRequest.isOnlyVerifiedToken());
                cardConfigurationEntity.setTdsRequired(createCardFlowConfigRequest.isTdsEnabled());
                cardConfigurationEntity.setCvvRequired(createCardFlowConfigRequest.isCvvRequired());
                cardConfigurationEntity.setAutoSettle(createCardFlowConfigRequest.isAutoSettle());
                logger.info("!!! --- Save Card Config --- !!!");
                CardConfigurationEntity savedCardConfigurationEntity = cardConfigurationRepository.save(cardConfigurationEntity);
                logger.info("!!! --- Saved Card Config --- !!!");

                if (oldStatus.compareToIgnoreCase("ACTIVE") == 0) {
                    if (!checkConflictingCardConfigurations(savedCardConfigurationEntity.getId(), applicationId)) {
                        logger.warn("Similar card configuration already exist");
                    }
                }

                // TODO: Look at when able to dynamically change card types, use similar approach to ecis
                List<CardTypeGroupEntity> cardTypeGroupEntities = cardTypeGroupRepository.findAllByCardConfigurationId(savedCardConfigurationEntity.getId());
                cardTypeGroupRepository.deleteAll(cardTypeGroupEntities);
                for (String cardTypeCode : createCardFlowConfigRequest.getCardTypes()) {

                    CardTypeEntity cardTypeEntity = cardTypeRepository.findByCode(cardTypeCode);
                    CardTypeGroupEntity cardTypeGroupEntity = cardTypeGroupRepository.findByCardConfigurationIdAndCardTypeEntityByCardTypeId(savedCardConfigurationEntity.getId(), cardTypeEntity);
                    CardTypeGroupEntity newCardTypeGroupEntity = new CardTypeGroupEntity();
                    if (cardTypeGroupEntity == null) {
                        newCardTypeGroupEntity.setCardTypeId(cardTypeEntity.getId());
                        newCardTypeGroupEntity.setCardConfigurationId(savedCardConfigurationEntity.getId());
                        logger.info("!!! --- Save Card Type Code --- !!!");
                        CardTypeGroupEntity savedCardTypeGroupEntity = cardTypeGroupRepository.save(newCardTypeGroupEntity);
                        logger.info("!!! --- Saved Card Type Code --- !!!");
                    }
                }

//                List<CardConfigurationEciEntity> savedCardConfigEcis = cardConfigurationEciRepository.findAllByCardConfigurationId(savedCardConfigurationEntity.getId());
//                cardConfigurationEciRepository.deleteAll(savedCardConfigEcis);
//                cardConfigurationEciRepository.flush();

                cardConfigurationEciRepository.findAllByCardConfigurationId(savedCardConfigurationEntity.getId())
                        .forEach(cardConfigurationEciEntity -> {
                            if (createCardFlowConfigRequest.getEcis()
                                    .stream()
                                    .noneMatch(s -> s.equalsIgnoreCase(cardConfigurationEciEntity.getEciByEciId().getEci()))) {
                                logger.info("!!! --- Delete Card Config Eci Entity --- !!!");
                                cardConfigurationEciRepository.delete(cardConfigurationEciEntity);
                                logger.info("!!! --- Deleted Card Config Eci Entity --- !!!");
                            }
                        });

                for (String eci : createCardFlowConfigRequest.getEcis()) {
                    EciEntity eciEntity = eciRepository.findByEci(eci);
                    CardConfigurationEciEntity cardConfigurationEciEntity = cardConfigurationEciRepository.findByCardConfigurationIdAndEciByEciId(savedCardConfigurationEntity.getId(), eciEntity);
                    CardConfigurationEciEntity newCardConfigurationEciEntity = new CardConfigurationEciEntity();
                    if (cardConfigurationEciEntity == null) {
                        newCardConfigurationEciEntity.setEciId(eciEntity.getId());
                        newCardConfigurationEciEntity.setCardConfigurationId(savedCardConfigurationEntity.getId());
                        logger.info("!!! --- Save Card Config Eci Entity --- !!!");
                        CardConfigurationEciEntity savedCardConfigurationEciEntity = cardConfigurationEciRepository.save(newCardConfigurationEciEntity);
                        logger.info("!!! --- Saved Card Config Eci Entity --- !!!");
                    }
                }

//                List<CardConfigurationTokenizationMethodEntity> savedCardConfigTokenizations = cardConfigurationTokenizationMethodRepository.findAllByCardConfigurationId(savedCardConfigurationEntity.getId());
//                cardConfigurationTokenizationMethodRepository.deleteAll(savedCardConfigTokenizations);
//                cardConfigurationTokenizationMethodRepository.flush();
//                    if (savedCardConfigTokenizations.stream().anyMatch(cardConfigurationTokenizationMethodEntity -> tokenizationMethod.equalsIgnoreCase(cardConfigurationTokenizationMethodEntity.getTokenizationMethodByTokenizationMethodId().getCode()))) {
//
//                    }
                for (String tokenizationMethod : createCardFlowConfigRequest.getTokenizationMethods()) {
                    TokenizationMethodEntity tokenizationMethodEntity = tokenizationMethodRepository.findByCode(tokenizationMethod);
                    CardConfigurationTokenizationMethodEntity cardConfigurationTokenizationMethodEntity = cardConfigurationTokenizationMethodRepository.findByCardConfigurationIdAndTokenizationMethodByTokenizationMethodId(savedCardConfigurationEntity.getId(), tokenizationMethodEntity);
                    CardConfigurationTokenizationMethodEntity newCardConfigurationTokenizationMethodEntity = new CardConfigurationTokenizationMethodEntity();
                    if (cardConfigurationTokenizationMethodEntity == null) {
                        newCardConfigurationTokenizationMethodEntity.setCardConfigurationId(savedCardConfigurationEntity.getId());
                        newCardConfigurationTokenizationMethodEntity.setTokenizationMethodId(tokenizationMethodEntity.getId());
                        logger.info("!!! --- Save Card Config Tokenization Entity --- !!!");
                        CardConfigurationTokenizationMethodEntity savedCardConfigurationTokenizationMethodEntity = cardConfigurationTokenizationMethodRepository.save(newCardConfigurationTokenizationMethodEntity);
                        logger.info("!!! --- Saved Card Config Tokenization Entity --- !!!");
                    }

                }

                switch (tdsMethodEntity.getCode()) {
                    case "BANKSERV_V1":
                        BankservConfigurationEntity oldBankServConfigurationEntity = bankservConfigurationRepository.findByCardConfigurationId(savedCardConfigurationEntity.getId());
                        if(oldBankServConfigurationEntity != null) {
                            oldBankServConfigurationEntity.setMerchantId(createCardFlowConfigRequest.getTdsProperties().getBankservConfiguration().getMerchantId());
                            oldBankServConfigurationEntity.setOrgUnitId(createCardFlowConfigRequest.getTdsProperties().getBankservConfiguration().getOrgUnitId());
                            oldBankServConfigurationEntity.setThreeDsPassword(createCardFlowConfigRequest.getTdsProperties().getBankservConfiguration().getThreeDsPassword());
                            oldBankServConfigurationEntity.setTransactionType(createCardFlowConfigRequest.getTdsProperties().getBankservConfiguration().getTransactionType());
                            bankservConfigurationRepository.save(oldBankServConfigurationEntity);
                        } else {
                            BankservConfigurationEntity bankservConfigurationEntity = new BankservConfigurationEntity();
                            bankservConfigurationEntity.setCardConfigurationId(savedCardConfigurationEntity.getId());
                            bankservConfigurationEntity.setMerchantId(createCardFlowConfigRequest.getTdsProperties().getBankservConfiguration().getMerchantId());
                            bankservConfigurationEntity.setOrgUnitId(createCardFlowConfigRequest.getTdsProperties().getBankservConfiguration().getOrgUnitId());
                            bankservConfigurationEntity.setThreeDsPassword(createCardFlowConfigRequest.getTdsProperties().getBankservConfiguration().getThreeDsPassword());
                            bankservConfigurationEntity.setTransactionType(createCardFlowConfigRequest.getTdsProperties().getBankservConfiguration().getTransactionType());
                            bankservConfigurationRepository.save(bankservConfigurationEntity);
                        }
                        break;
                    case "BANKSERV_V2":
                        BankservConfigurationEntity oldBankServConfigurationEntity1 = bankservConfigurationRepository.findByCardConfigurationId(savedCardConfigurationEntity.getId());
                        if(oldBankServConfigurationEntity1 != null) {
                            oldBankServConfigurationEntity1.setMerchantId(createCardFlowConfigRequest.getTdsProperties().getBankservConfiguration().getMerchantId());
                            oldBankServConfigurationEntity1.setOrgUnitId(createCardFlowConfigRequest.getTdsProperties().getBankservConfiguration().getOrgUnitId());
                            oldBankServConfigurationEntity1.setThreeDsPassword(createCardFlowConfigRequest.getTdsProperties().getBankservConfiguration().getThreeDsPassword());
                            oldBankServConfigurationEntity1.setTransactionType(createCardFlowConfigRequest.getTdsProperties().getBankservConfiguration().getTransactionType());
                            bankservConfigurationRepository.save(oldBankServConfigurationEntity1);
                        } else {
                            BankservConfigurationEntity bankservConfigurationEntity1 = new BankservConfigurationEntity();
                            bankservConfigurationEntity1.setCardConfigurationId(savedCardConfigurationEntity.getId());
                            bankservConfigurationEntity1.setMerchantId(createCardFlowConfigRequest.getTdsProperties().getBankservConfiguration().getMerchantId());
                            bankservConfigurationEntity1.setOrgUnitId(createCardFlowConfigRequest.getTdsProperties().getBankservConfiguration().getOrgUnitId());
                            bankservConfigurationEntity1.setThreeDsPassword(createCardFlowConfigRequest.getTdsProperties().getBankservConfiguration().getThreeDsPassword());
                            bankservConfigurationEntity1.setTransactionType(createCardFlowConfigRequest.getTdsProperties().getBankservConfiguration().getTransactionType());
                            bankservConfigurationRepository.save(bankservConfigurationEntity1);
                        }
                        break;
                    case "CARDINAL_V2":
                        CardinalConfigurationEntity oldCardConfigurationEntity1 = cardinalConfigurationRepository.findByCardConfigurationId(savedCardConfigurationEntity.getId());
                        if(oldCardConfigurationEntity1 != null) {
                            oldCardConfigurationEntity1.setOrgUnitId(createCardFlowConfigRequest.getTdsProperties().getCardinalConfiguration().getOrgUnitId());
                            cardinalConfigurationRepository.save(oldCardConfigurationEntity1);
                        } else {
                            CardinalConfigurationEntity cardinalConfigurationEntity1 = new CardinalConfigurationEntity();
                            cardinalConfigurationEntity1.setCardConfigurationId(savedCardConfigurationEntity.getId());
                            cardinalConfigurationEntity1.setOrgUnitId(createCardFlowConfigRequest.getTdsProperties().getCardinalConfiguration().getOrgUnitId());
                            cardinalConfigurationRepository.save(cardinalConfigurationEntity1);
                        }
                        break;
                    default:
                        break;
                }

                //save gateway config
                switch (createCardFlowConfigRequest.getGateway()) {
                    case "STANDARD_BANK":
                        StandardBankConfigurationEntity oldStandardBankConfigurationEntity = standardBankConfigurationRepository.findByCardConfigurationId(savedCardConfigurationEntity.getId());
                        if(oldStandardBankConfigurationEntity != null) {
                            oldStandardBankConfigurationEntity.setMerchantMid(createCardFlowConfigRequest.getGatewayProperties().getStandardBankConfiguration().getMerchantMid());
                            oldStandardBankConfigurationEntity.setMerchantCategoryCode(createCardFlowConfigRequest.getGatewayProperties().getStandardBankConfiguration().getMerchantCategoryCode());
                            oldStandardBankConfigurationEntity.setTerminalId(createCardFlowConfigRequest.getGatewayProperties().getStandardBankConfiguration().getTerminalId());
                            standardBankConfigurationRepository.save(oldStandardBankConfigurationEntity);
                        } else {
                            StandardBankConfigurationEntity newStandardBankConfigurationEntity = new StandardBankConfigurationEntity();
                            newStandardBankConfigurationEntity.setCardConfigurationId(savedCardConfigurationEntity.getId());
                            newStandardBankConfigurationEntity.setMerchantMid(createCardFlowConfigRequest.getGatewayProperties().getStandardBankConfiguration().getMerchantMid());
                            newStandardBankConfigurationEntity.setMerchantCategoryCode(createCardFlowConfigRequest.getGatewayProperties().getStandardBankConfiguration().getMerchantCategoryCode());
                            newStandardBankConfigurationEntity.setTerminalId(createCardFlowConfigRequest.getGatewayProperties().getStandardBankConfiguration().getTerminalId());
                            standardBankConfigurationRepository.save(newStandardBankConfigurationEntity);
                        }
                        break;
                    case "BW":
                        BankWindhoekConfigurationEntity oldBankWindhoekConfigurationEntity = bankWindhoekConfigurationRepository.findByCardConfigurationId(savedCardConfigurationEntity.getId());
                        if(oldBankWindhoekConfigurationEntity != null) {
                            oldBankWindhoekConfigurationEntity.setMerchantMid(createCardFlowConfigRequest.getGatewayProperties().getBankWindhoekConfiguration().getMerchantMid());
                            oldBankWindhoekConfigurationEntity.setMerchantCategoryCode(createCardFlowConfigRequest.getGatewayProperties().getBankWindhoekConfiguration().getMerchantCategoryCode());
                            oldBankWindhoekConfigurationEntity.setTerminalId(createCardFlowConfigRequest.getGatewayProperties().getBankWindhoekConfiguration().getTerminalId());
                            bankWindhoekConfigurationRepository.save(oldBankWindhoekConfigurationEntity);
                        } else {
                            BankWindhoekConfigurationEntity newBankWindhoekConfigurationEntity = new BankWindhoekConfigurationEntity();
                            newBankWindhoekConfigurationEntity.setCardConfigurationId(savedCardConfigurationEntity.getId());
                            newBankWindhoekConfigurationEntity.setMerchantMid(createCardFlowConfigRequest.getGatewayProperties().getBankWindhoekConfiguration().getMerchantMid());
                            newBankWindhoekConfigurationEntity.setMerchantCategoryCode(createCardFlowConfigRequest.getGatewayProperties().getBankWindhoekConfiguration().getMerchantCategoryCode());
                            newBankWindhoekConfigurationEntity.setTerminalId(createCardFlowConfigRequest.getGatewayProperties().getBankWindhoekConfiguration().getTerminalId());
                            bankWindhoekConfigurationRepository.save(newBankWindhoekConfigurationEntity);
                        }
                        break;
                    case "ABSA":
                        AbsaConfigurationEntity oldAbsaConfigurationEntity = absaConfigurationRepository.findByCardConfigurationId(savedCardConfigurationEntity.getId());
                        if(oldAbsaConfigurationEntity != null) {
                            oldAbsaConfigurationEntity.setMerchantMid(createCardFlowConfigRequest.getGatewayProperties().getAbsaConfiguration().getMerchantMid());
                            oldAbsaConfigurationEntity.setMerchantCategoryCode(createCardFlowConfigRequest.getGatewayProperties().getAbsaConfiguration().getMerchantCategoryCode());
                            oldAbsaConfigurationEntity.setTerminalId(createCardFlowConfigRequest.getGatewayProperties().getAbsaConfiguration().getTerminalId());
                            absaConfigurationRepository.save(oldAbsaConfigurationEntity);
                        } else {
                            AbsaConfigurationEntity absaConfigurationEntity = new AbsaConfigurationEntity();
                            absaConfigurationEntity.setCardConfigurationId(savedCardConfigurationEntity.getId());
                            absaConfigurationEntity.setMerchantMid(createCardFlowConfigRequest.getGatewayProperties().getAbsaConfiguration().getMerchantMid());
                            absaConfigurationEntity.setMerchantCategoryCode(createCardFlowConfigRequest.getGatewayProperties().getAbsaConfiguration().getMerchantCategoryCode());
                            absaConfigurationEntity.setTerminalId(createCardFlowConfigRequest.getGatewayProperties().getAbsaConfiguration().getTerminalId());
                            absaConfigurationRepository.save(absaConfigurationEntity);
                        }
                        break;
                    case "FNB":
                        FnbConfigurationEntity oldFnbConfigurationEntity = fnbConfigurationRepository.findByCardConfigurationId(savedCardConfigurationEntity.getId());
                        if(oldFnbConfigurationEntity != null) {
                            oldFnbConfigurationEntity.setMerchantMid(createCardFlowConfigRequest.getGatewayProperties().getFnbConfiguration().getMerchantMid());
                            oldFnbConfigurationEntity.setMerchantCategoryCode(createCardFlowConfigRequest.getGatewayProperties().getFnbConfiguration().getMerchantCategoryCode());
                            oldFnbConfigurationEntity.setTerminalId(createCardFlowConfigRequest.getGatewayProperties().getFnbConfiguration().getTerminalId());
                            fnbConfigurationRepository.save(oldFnbConfigurationEntity);
                        } else {
                            FnbConfigurationEntity fnbConfigurationEntity = new FnbConfigurationEntity();
                            fnbConfigurationEntity.setCardConfigurationId(savedCardConfigurationEntity.getId());
                            fnbConfigurationEntity.setMerchantMid(createCardFlowConfigRequest.getGatewayProperties().getFnbConfiguration().getMerchantMid());
                            fnbConfigurationEntity.setMerchantCategoryCode(createCardFlowConfigRequest.getGatewayProperties().getFnbConfiguration().getMerchantCategoryCode());
                            fnbConfigurationEntity.setTerminalId(createCardFlowConfigRequest.getGatewayProperties().getFnbConfiguration().getTerminalId());
                            fnbConfigurationRepository.save(fnbConfigurationEntity);
                        }
                        break;
                    case "NEDBANK":
                        IveriConfigurationEntity oldIveriConfigurationEntity = iveriConfigurationRepository.findByCardConfigurationId(savedCardConfigurationEntity.getId());
                        if(oldIveriConfigurationEntity != null) {
                            oldIveriConfigurationEntity.setApplicationId(createCardFlowConfigRequest.getGatewayProperties().getIveriConfiguration().getApplicationId());
                            oldIveriConfigurationEntity.setMerchantCategoryCode(createCardFlowConfigRequest.getGatewayProperties().getIveriConfiguration().getMerchantCategoryCode());
                            oldIveriConfigurationEntity.setTerminalId(createCardFlowConfigRequest.getGatewayProperties().getIveriConfiguration().getTerminalId());
                            iveriConfigurationRepository.save(oldIveriConfigurationEntity);
                        } else {
                            IveriConfigurationEntity iveriConfigurationEntity = new IveriConfigurationEntity();
                            iveriConfigurationEntity.setCardConfigurationId(savedCardConfigurationEntity.getId());
                            iveriConfigurationEntity.setApplicationId(createCardFlowConfigRequest.getGatewayProperties().getIveriConfiguration().getApplicationId());
                            iveriConfigurationEntity.setMerchantCategoryCode(createCardFlowConfigRequest.getGatewayProperties().getIveriConfiguration().getMerchantCategoryCode());
                            iveriConfigurationEntity.setTerminalId(createCardFlowConfigRequest.getGatewayProperties().getIveriConfiguration().getTerminalId());
                            iveriConfigurationRepository.save(iveriConfigurationEntity);
                        }
                        break;
                    case "VACP":
                        VacpConfigurationEntity oldVacpConfigurationEntity = vacpConfigurationRepository.findByCardConfigurationId(savedCardConfigurationEntity.getId());
                        if(oldVacpConfigurationEntity != null) {
                            oldVacpConfigurationEntity.setIgnoreAvsResult(createCardFlowConfigRequest.getGatewayProperties().getVacpConfiguration().isIgnoreAvsResult());
                            oldVacpConfigurationEntity.setIgnoreCvResult(createCardFlowConfigRequest.getGatewayProperties().getVacpConfiguration().isIgnoreCvResult());
                            oldVacpConfigurationEntity.setPartialAuthIndicator(createCardFlowConfigRequest.getGatewayProperties().getVacpConfiguration().isPartialAuthIndicator());
                            oldVacpConfigurationEntity.setUsername(createCardFlowConfigRequest.getGatewayProperties().getVacpConfiguration().getUsername());
                            oldVacpConfigurationEntity.setPassword(createCardFlowConfigRequest.getGatewayProperties().getVacpConfiguration().getPassword());
                            vacpConfigurationRepository.save(oldVacpConfigurationEntity);
                        } else {
                            VacpConfigurationEntity vacpConfigurationEntity = new VacpConfigurationEntity();
                            vacpConfigurationEntity.setCardConfigurationId(savedCardConfigurationEntity.getId());
                            vacpConfigurationEntity.setIgnoreAvsResult(createCardFlowConfigRequest.getGatewayProperties().getVacpConfiguration().isIgnoreAvsResult());
                            vacpConfigurationEntity.setIgnoreCvResult(createCardFlowConfigRequest.getGatewayProperties().getVacpConfiguration().isIgnoreCvResult());
                            vacpConfigurationEntity.setPartialAuthIndicator(createCardFlowConfigRequest.getGatewayProperties().getVacpConfiguration().isPartialAuthIndicator());
                            vacpConfigurationEntity.setUsername(createCardFlowConfigRequest.getGatewayProperties().getVacpConfiguration().getUsername());
                            vacpConfigurationEntity.setPassword(createCardFlowConfigRequest.getGatewayProperties().getVacpConfiguration().getPassword());
                            vacpConfigurationRepository.save(vacpConfigurationEntity);
                        }
                        break;
                    case "BOW":
                        BwConfigurationEntity oldBwConfigurationEntity = bwConfigurationRepository.findByCardConfigurationId(savedCardConfigurationEntity.getId());
                        if(oldBwConfigurationEntity != null) {
                            oldBwConfigurationEntity.setUsername(createCardFlowConfigRequest.getGatewayProperties().getBwConfiguration().getUsername());
                            oldBwConfigurationEntity.setPassword(createCardFlowConfigRequest.getGatewayProperties().getBwConfiguration().getPassword());
                            oldBwConfigurationEntity.setTerminalId(createCardFlowConfigRequest.getGatewayProperties().getBwConfiguration().getTerminalId());
                            bwConfigurationRepository.save(oldBwConfigurationEntity);
                        } else {
                            BwConfigurationEntity bwConfigurationEntity = new BwConfigurationEntity();
                            bwConfigurationEntity.setCardConfigurationId(savedCardConfigurationEntity.getId());
                            bwConfigurationEntity.setUsername(createCardFlowConfigRequest.getGatewayProperties().getBwConfiguration().getUsername());
                            bwConfigurationEntity.setPassword(createCardFlowConfigRequest.getGatewayProperties().getBwConfiguration().getPassword());
                            bwConfigurationEntity.setTerminalId(createCardFlowConfigRequest.getGatewayProperties().getBwConfiguration().getTerminalId());
                            bwConfigurationRepository.save(bwConfigurationEntity);
                        }
                        break;
                    case "PLANET":
                        PlanetConfigurationEntity oldPlanetConfigurationEntity = planetConfigurationRepository.findByCardConfigurationId(savedCardConfigurationEntity.getId());
                        if(oldPlanetConfigurationEntity != null) {
                            oldPlanetConfigurationEntity.setBankPassword(createCardFlowConfigRequest.getGatewayProperties().getPlanetConfiguration().getBankPassword());
                            oldPlanetConfigurationEntity.setBankUsername(createCardFlowConfigRequest.getGatewayProperties().getPlanetConfiguration().getBankUsername());
                            oldPlanetConfigurationEntity.setDescriptorCity(createCardFlowConfigRequest.getGatewayProperties().getPlanetConfiguration().getDescriptorCity());
                            oldPlanetConfigurationEntity.setDescriptorCompany(createCardFlowConfigRequest.getGatewayProperties().getPlanetConfiguration().getDescriptorCompany());
                            oldPlanetConfigurationEntity.setDescriptorCountry(createCardFlowConfigRequest.getGatewayProperties().getPlanetConfiguration().getDescriptorCountry());
                            oldPlanetConfigurationEntity.setDescriptorInfo(createCardFlowConfigRequest.getGatewayProperties().getPlanetConfiguration().getDescriptorInfo());
                            oldPlanetConfigurationEntity.setDescriptorState(createCardFlowConfigRequest.getGatewayProperties().getPlanetConfiguration().getDescriptorState());
                            oldPlanetConfigurationEntity.setTerminalId(createCardFlowConfigRequest.getGatewayProperties().getPlanetConfiguration().getTerminalId());
                            oldPlanetConfigurationEntity.setThreedsecureOrdUnitId(createCardFlowConfigRequest.getGatewayProperties().getPlanetConfiguration().getThreedsecureOrdUnitId());
                            planetConfigurationRepository.save(oldPlanetConfigurationEntity);
                        } else {
                            PlanetConfigurationEntity planetConfigurationEntity = new PlanetConfigurationEntity();
                            planetConfigurationEntity.setCardConfigurationId(savedCardConfigurationEntity.getId());
                            planetConfigurationEntity.setBankPassword(createCardFlowConfigRequest.getGatewayProperties().getPlanetConfiguration().getBankPassword());
                            planetConfigurationEntity.setBankUsername(createCardFlowConfigRequest.getGatewayProperties().getPlanetConfiguration().getBankUsername());
                            planetConfigurationEntity.setDescriptorCity(createCardFlowConfigRequest.getGatewayProperties().getPlanetConfiguration().getDescriptorCity());
                            planetConfigurationEntity.setDescriptorCompany(createCardFlowConfigRequest.getGatewayProperties().getPlanetConfiguration().getDescriptorCompany());
                            planetConfigurationEntity.setDescriptorCountry(createCardFlowConfigRequest.getGatewayProperties().getPlanetConfiguration().getDescriptorCountry());
                            planetConfigurationEntity.setDescriptorInfo(createCardFlowConfigRequest.getGatewayProperties().getPlanetConfiguration().getDescriptorInfo());
                            planetConfigurationEntity.setDescriptorState(createCardFlowConfigRequest.getGatewayProperties().getPlanetConfiguration().getDescriptorState());
                            planetConfigurationEntity.setTerminalId(createCardFlowConfigRequest.getGatewayProperties().getPlanetConfiguration().getTerminalId());
                            planetConfigurationEntity.setThreedsecureOrdUnitId(createCardFlowConfigRequest.getGatewayProperties().getPlanetConfiguration().getThreedsecureOrdUnitId());
                            planetConfigurationRepository.save(planetConfigurationEntity);
                        }
                        break;
                    default:
                        break;
                }

                logger.info("!!! --- Saved Gateway & Tds --- !!!");

                return ResponseEntity.ok().build();

            } else {
                throw new GenericException("Merchant not registered for product", HttpStatus.INTERNAL_SERVER_ERROR, "MerchantID: " + merchantId);
            }
//        } catch (Exception e) {
//            logger.error(e);
//            throw new GenericException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, e.getLocalizedMessage());
//        }
    }

    @Override
    public void createEftConfig(PlatformCreateEftInterfaceConfigRequest eftConfig) throws ExceptionExtender {
        String url = paymentTypeConfigManagementUrl + "/eft-config";
        try {
            restTemplate.exchange(url, HttpMethod.POST, new HttpEntity<>(eftConfig, utilityService.getHttpHeaders()), Void.class);
        } catch (RestClientResponseException e) {
            throw new ExceptionExtender(e.getMessage(), HttpStatus.valueOf(e.getRawStatusCode()), e.getResponseBodyAsString());
        }
    }

    @Override
    public EftInterfaceConfig getEftConfigByInterfaceId(long interfaceId) throws ExceptionExtender {
        String url = paymentTypeConfigManagementUrl + "/eft-config/" + interfaceId;
        ResponseEntity<EftInterfaceConfig> eftInterfaceConfig;
        try {
            eftInterfaceConfig = restTemplate.exchange(url, HttpMethod.GET, new HttpEntity<>(null, utilityService.getHttpHeaders()), EftInterfaceConfig.class);
        } catch (RestClientResponseException e) {
            throw new ExceptionExtender(e.getMessage(), HttpStatus.valueOf(e.getRawStatusCode()), e.getResponseBodyAsString());
        }
        return eftInterfaceConfig.getBody();
    }

    @Override
    public void putEftConfigByInterfaceId(long interfaceId, EftInterfaceConfig eftInterfaceConfig) throws ExceptionExtender {
        String url = paymentTypeConfigManagementUrl + "/eft-config/" + interfaceId;
        try {
            restTemplate.exchange(url, HttpMethod.PUT, new HttpEntity<>(eftInterfaceConfig, utilityService.getHttpHeaders()), Void.class);
        } catch (RestClientResponseException e) {
            throw new ExceptionExtender(e.getMessage(), HttpStatus.valueOf(e.getRawStatusCode()), e.getResponseBodyAsString());
        }
    }

    @Override
    public void createMobicredConfig(PlatformCreateMobicredInterfaceConfigRequest mobicredConfig) throws ExceptionExtender {
        String url = paymentTypeConfigManagementUrl + "/mobicred-config";
        logger.info("Add Payment Type Config : " + mobicredConfig);
        try {
            restTemplate.exchange(url, HttpMethod.POST, new HttpEntity<>(mobicredConfig, utilityService.getHttpHeaders()), Void.class);
        } catch (RestClientResponseException e) {
            throw new ExceptionExtender(e.getMessage(), HttpStatus.valueOf(e.getRawStatusCode()), e.getResponseBodyAsString());
        }
    }

    @Override
    public void putMobicredConfigByInterfaceId(String interfaceId, MobicredInterfaceConfig mobicredInterfaceConfig) throws ExceptionExtender {
        String url = paymentTypeConfigManagementUrl + "/mobicred-config/" + interfaceId;
        try {
            restTemplate.exchange(url, HttpMethod.PUT, new HttpEntity<>(mobicredInterfaceConfig, utilityService.getHttpHeaders()), Void.class);
        } catch (RestClientResponseException e) {
            throw new ExceptionExtender(e.getMessage(), HttpStatus.valueOf(e.getRawStatusCode()), e.getResponseBodyAsString());
        }
    }

    @Override
    public MobicredInterfaceConfig getMobicredConfigByInterfaceId(String interfaceId) throws ExceptionExtender {
        String url = paymentTypeConfigManagementUrl + "/mobicred-config/" + interfaceId;
        ResponseEntity<MobicredInterfaceConfig> mobicredInterfaceConfig;
        try {
            mobicredInterfaceConfig = restTemplate.exchange(url, HttpMethod.GET, new HttpEntity<>(null, utilityService.getHttpHeaders()), MobicredInterfaceConfig.class);
        } catch (RestClientResponseException e) {
            throw new ExceptionExtender(e.getMessage(), HttpStatus.valueOf(e.getRawStatusCode()), e.getResponseBodyAsString());
        }
        return mobicredInterfaceConfig.getBody();
    }

    @Override
    public List<Eci> getEci() throws ExceptionExtender {
        String url = paymentTypeConfigManagementUrl + "/eci";
        ResponseEntity<List<Eci>> eci;
        try {
            eci = restTemplate.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<List<Eci>>() {
            });
        } catch (RestClientResponseException e) {
            throw new ExceptionExtender(e.getMessage(), HttpStatus.valueOf(e.getRawStatusCode()), e.getResponseBodyAsString());
        }
        logger.info("This is the Security Types Object that gets returned: " + eci.getBody());
        return eci.getBody();
    }

    @Override
    public List<Eci> getEciByInterfaceId(long interfaceId) throws ExceptionExtender {
        String url = paymentTypeConfigManagementUrl + "/eci/" + interfaceId;
        ResponseEntity<List<Eci>> listResponseEntity;
        try {
            listResponseEntity = restTemplate.exchange(url, HttpMethod.GET, new HttpEntity<>(null, utilityService.getHttpHeaders()), new ParameterizedTypeReference<List<Eci>>() {
            });
        } catch (RestClientResponseException e) {
            throw new ExceptionExtender(e.getMessage(), HttpStatus.valueOf(e.getRawStatusCode()), e.getResponseBodyAsString());
        }

        return listResponseEntity.getBody();
    }

    @Override
    public void putEciByInterfaceId(long interfaceId, List<PlatformCreateCardInterfaceConfigRequest> platformCreateCardInterfaceConfigRequests) throws ExceptionExtender {
        String url = paymentTypeConfigManagementUrl + "/eci/" + interfaceId;
        logger.info("PUT ECI INTERFACE FOR ID | " + interfaceId + " | URL: " + url);
        try {
            restTemplate.exchange(url, HttpMethod.PUT, new HttpEntity<>(platformCreateCardInterfaceConfigRequests, utilityService.getHttpHeaders()), String.class);
        } catch (RestClientResponseException e) {
            throw new ExceptionExtender(e.getMessage(), HttpStatus.valueOf(e.getRawStatusCode()), e.getResponseBodyAsString());
        }
    }

    @Override
    public void createCardConfig(List<PlatformCreateCardInterfaceConfigRequest> cardConfig) throws ExceptionExtender {
        String url = paymentTypeConfigManagementUrl + "/card-config";
        try {
            restTemplate.exchange(url, HttpMethod.POST, new HttpEntity<>(cardConfig, utilityService.getHttpHeaders()), Void.class);
        } catch (RestClientResponseException e) {
            throw new ExceptionExtender(e.getMessage(), HttpStatus.valueOf(e.getRawStatusCode()), e.getResponseBodyAsString());
        }
        logger.info("Add Payment Type Config :" + cardConfig);
    }

    @Override
    public void createMasterpassConfig(PlatformCreateMasterpassConfigRequest masterpassConfig) throws ExceptionExtender {
        String url = paymentTypeConfigManagementUrl + "/masterpass-config";
        logger.info("Add Payment Type Config : " + masterpassConfig);
        try {
            restTemplate.exchange(url, HttpMethod.POST, new HttpEntity<>(masterpassConfig, utilityService.getHttpHeaders()), Void.class);
        } catch (RestClientResponseException e) {
            throw new ExceptionExtender(e.getMessage(), HttpStatus.valueOf(e.getRawStatusCode()), e.getResponseBodyAsString());
        }
    }

    @Override
    public void putMasterpassByInterfaceId(String interfaceId, MasterpassConfig masterpassConfig) {
        String url = paymentTypeConfigManagementUrl + "/masterpass-config/" + interfaceId;
        try {
            restTemplate.exchange(url, HttpMethod.PUT, new HttpEntity<>(masterpassConfig, utilityService.getHttpHeaders()), Void.class);
        } catch (RestClientResponseException e) {
            throw new ExceptionExtender(e.getMessage(), HttpStatus.valueOf(e.getRawStatusCode()), e.getResponseBodyAsString());
        }
    }

    @Override
    public MasterpassConfig getMasterpassConfigByInterfaceId(String interfaceId) {
        String url = paymentTypeConfigManagementUrl + "/masterpass-config/" + interfaceId;
        ResponseEntity<MasterpassConfig> masterpassConfig;
        try {
            masterpassConfig = restTemplate.exchange(url, HttpMethod.GET, new HttpEntity<>(null, utilityService.getHttpHeaders()), MasterpassConfig.class);
        } catch (RestClientResponseException e) {
            throw new ExceptionExtender(e.getMessage(), HttpStatus.valueOf(e.getRawStatusCode()), e.getResponseBodyAsString());
        }
        return masterpassConfig.getBody();
    }

    @Override
    public void createZapperConfig(PlatformCreateZapperConfigRequest zapperConfig) {
        String url = paymentTypeConfigManagementUrl + "/zapper-config";
        logger.info("Add Payment Type Config : " + zapperConfig);
        try {
            restTemplate.exchange(url, HttpMethod.POST, new HttpEntity<>(zapperConfig, utilityService.getHttpHeaders()), Void.class);
        } catch (RestClientResponseException e) {
            throw new ExceptionExtender(e.getMessage(), HttpStatus.valueOf(e.getRawStatusCode()), e.getResponseBodyAsString());
        }
    }

    @Override
    public void putZapperByInterfaceId(String interfaceId, ZapperConfig zapperConfig) {
        String url = paymentTypeConfigManagementUrl + "/zapper-config/" + interfaceId;
        try {
            restTemplate.exchange(url, HttpMethod.PUT, new HttpEntity<>(zapperConfig, utilityService.getHttpHeaders()), Void.class);
        } catch (RestClientResponseException e) {
            throw new ExceptionExtender(e.getMessage(), HttpStatus.valueOf(e.getRawStatusCode()), e.getResponseBodyAsString());
        }
    }

    @Override
    public ZapperConfig getZapperConfigByInterfaceId(String interfaceId) {
        String url = paymentTypeConfigManagementUrl + "/zapper-config/" + interfaceId;
        ResponseEntity<ZapperConfig> zapperConfig;
        try {
            zapperConfig = restTemplate.exchange(url, HttpMethod.GET, new HttpEntity<>(null, utilityService.getHttpHeaders()), ZapperConfig.class);
        } catch (RestClientResponseException e) {
            throw new ExceptionExtender(e.getMessage(), HttpStatus.valueOf(e.getRawStatusCode()), e.getResponseBodyAsString());
        }
        return zapperConfig.getBody();
    }

    @Override
    public InterfaceConfigDataResponse getInterfaceConfigData() {
        List<Country> countries = countryRepository
                .findAll()
                .stream()
                .map(Country::new)
                .collect(Collectors.toList());

        List<Currency> currencies = currencyRepository
                .findAll()
                .stream()
                .map(Currency::new)
                .collect(Collectors.toList());

        return new InterfaceConfigDataResponse(countries, currencies);
    }

    @Override
    public List<CardFlowEntity> getCardFlows(long merchantId, long applicationId) {

        // Retrieve list of all card flow entities

        List<CardFlowEntity> cardFlowEntities = cardFlowRepository
                .findAll();

        // Recurring Flow Entity

        CardFlowEntity recurringFlow = cardFlowRepository.findByCode("RECURRING");

        // One click flow entity

        CardFlowEntity oneClickFlow = cardFlowRepository.findByCode("ONE_CLICK");

        // Recurring

        ProductEntity recurringProduct = productRepository.findByProductCode("REPEAT_PAYMENTS");
        MerchantProductEntity merchantRecurringProduct = merchantProductRepository.findByProductByProductIdAndMerchantId(recurringProduct, merchantId);

        // One Click
        ProductEntity oneClickProduct = productRepository.findByProductCode("TOKENIZATION");
        MerchantProductEntity merchantOneClickProduct = merchantProductRepository.findByProductByProductIdAndMerchantId(oneClickProduct, merchantId);

        if (merchantRecurringProduct == null) {
            cardFlowEntities.remove(recurringFlow);
        }

        if (merchantOneClickProduct == null) {
            cardFlowEntities.remove(oneClickFlow);
        }

        ApplicationEntity applicationEntity = applicationRepository
                .findById(applicationId)
                .orElseThrow(() -> new GenericException("Could not find application with id | " + applicationId, HttpStatus.NOT_FOUND, "Could not find application with id | " + applicationId));

        PaymentTypeEntity cardPaymentTypeEntity = paymentTypeRepository.findByCode("CARD");

        Optional<ApplicationPaymentTypeEntity> applicationPaymentTypeEntity = applicationPaymentTypeRepository.findDistinctFirstByApplicationByApplicationIdAndPaymentTypeByPaymentTypeId(applicationEntity, cardPaymentTypeEntity);
                // .orElseThrow(() -> new GenericException("Payment type not configured for application", HttpStatus.NOT_FOUND, "ApplicationID: " + applicationId));

        if (!applicationPaymentTypeEntity.isPresent()) {
            cardFlowEntities.clear();
            CardFlowEntity cardFlowEntity = cardFlowRepository.findByCode("CARD");
            cardFlowEntities.add(cardFlowEntity);
        }

        return cardFlowEntities;
    }

    @Override
    public List<CardTypeGroupEntity> getCardTypeGroupsByCardFlow(long cardFlowId, long applicationId) {

        CardFlowEntity cardFlowEntity = cardFlowRepository.findById(cardFlowId)
                .orElseThrow(() -> new GenericException("Could not find card flow with id | " + cardFlowId, HttpStatus.NOT_FOUND, "Could not find card flow with id | " + cardFlowId));

        logger.info("CARD FLOW | " + cardFlowEntity.toString());

//        ApplicationEntity applicationEntity = applicationRepository.findById(applicationId)
//                .orElseThrow(() -> new GenericException("Could not find application", HttpStatus.NOT_FOUND, "ApplicationID: " + applicationId));
//
//        PaymentTypeEntity paymentTypeEntity = paymentTypeRepository.findByCode(za.co.wirecard.channel.backoffice.constants.PaymentType.CARD.value());
//
//        ApplicationPaymentTypeEntity applicationPaymentTypeEntity = applicationPaymentTypeRepository.findDistinctFirstByApplicationByApplicationIdAndPaymentTypeByPaymentTypeId(applicationEntity, paymentTypeEntity);


//        List<CardConfigurationEntity> cardConfigurationEntities = cardConfigurationRepository
//                .findAllByCardFlowByCardFlowIdAndInterfaceByInterfaceId(cardFlowEntity, applicationPaymentTypeEntity.getInterfaceByInterfaceId());

        List<CardConfigurationEntity> cardConfigurationEntities = cardConfigurationRepository
                .findAllByCardFlowByCardFlowId(cardFlowEntity);

        logger.info("Card Configuration | " + cardConfigurationEntities.toString());

//        if (cardConfigurationEntities.size() == 0) {
//
//            throw new GenericException("No Card Type Groups found for card flow id | " + cardFlowId, HttpStatus.NOT_FOUND, "No Card Type Groups found for card flow id | " + cardFlowId);
//
//        }

//        List<CardTypeGroupEntity> cardTypeGroupEntities = cardConfigurationEntities
//                .stream()
//                .map(cardConfigurationEntity -> {
//                    return cardConfigurationEntity.getCardTypeGroupsByCardTypeGroupIds()
//                            .stream()
//                            .collect(Collectors.toList());
//                })
//                .collect(Collectors.toList());

        return cardTypeGroupRepository
                .findAll()
                .stream()
                .filter(cardTypeGroupEntity -> cardConfigurationEntities.contains(cardTypeGroupEntity.getCardConfigurationByCardConfigurationId()))
                .collect(Collectors.toList());
    }

    @Override
    public CardConfigurationEntity getCardConfigurationByCardTypeGroupId(long cardTypeGroupId) {

        CardTypeGroupEntity cardTypeGroupEntity =  cardTypeGroupRepository
                .findById(cardTypeGroupId)
                .orElseThrow(() -> new GenericException("No Card Type Configuration found for card type group id | " + cardTypeGroupId, HttpStatus.NOT_FOUND, "No Card Type Configuration found for card type group id | " + cardTypeGroupId));

        return cardTypeGroupEntity.getCardConfigurationByCardConfigurationId();

    }

    @Override
    public List<TdsMethodEntity> getTdsMethods() {
        return tdsMethodRepository
                .findAll();
    }

    @Override
    public List<Gateway> getGateways() {
        return gatewayRepository.findAll()
                .stream()
                .map(Gateway::new)
                .collect(Collectors.toList());
    }

    @Override
    public List<Gateway> getCardGateways() {
        return gatewayRepository.findAll()
                .stream()
                .map(Gateway::new)
                .filter(gateway -> EnumUtils.isValidEnum(GatewayEnum.class, gateway.getCode()))
                .collect(Collectors.toList());
    }

//    public boolean gatewayIsCardGateway(Gateway gateway) {
//        if (gateway.getCode().equalsIgnoreCase(GatewayEnum.STANDARD_BANK.value()) || gateway.getCode().equalsIgnoreCase(GatewayEnum.ABSA.value()) || gateway.getCode().equalsIgnoreCase(GatewayEnum.FNB.value()) || gateway.getCode().equalsIgnoreCase(GatewayEnum.VACP.value()) || gateway.getCode().equalsIgnoreCase(GatewayEnum.BOW.value()) || gateway.getCode().equalsIgnoreCase(GatewayEnum.NEDBANK.value())) {
//            return true;
//        }
//        return false;
//    }

    @Override
    public Boolean checkConflictingCardConfigurations(long cardConfigurationId, long applicationId) {
        CardConfigurationEntity cardConfigurationEntity = cardConfigurationRepository.findById(cardConfigurationId)
                .orElseThrow(() -> new GenericException(String.format("No card configuration with id | %s | exists", cardConfigurationId), HttpStatus.NOT_FOUND, String.format("No card configuration with id | %s | exists", cardConfigurationId)));
        CardConfigStatusEntity cardConfigStatusEntityActive;
            cardConfigStatusEntityActive = cardConfigStatusRepository.findByStatus("ACTIVE");

        ApplicationEntity applicationEntity = applicationRepository.findById(applicationId)
                .orElseThrow(() -> new GenericException(String.format("No application with id | %s | exists", applicationId), HttpStatus.NOT_FOUND, String.format("No application with id | %s | exists", applicationId)));

        PaymentTypeEntity paymentTypeEntity = paymentTypeRepository.findByCode(PaymentTypeEnum.CARD.value());

        ApplicationPaymentTypeEntity applicationPaymentTypeEntity = applicationPaymentTypeRepository.findDistinctFirstByApplicationByApplicationIdAndPaymentTypeByPaymentTypeId(applicationEntity, paymentTypeEntity)
                .orElseThrow(() -> new GenericException(String.format("No application payment type with payment type: CARD and application id | %s | exists", applicationId), HttpStatus.NOT_FOUND, String.format("No application payment type with payment type: CARD and application id | %s | exists", applicationId)));

        List<CardConfigurationEntity> cardConfigurationEntities = cardConfigurationRepository.findAllByCardConfigStatusByCardConfigStatusIdAndCardFlowByCardFlowIdAndInterfaceByInterfaceId(cardConfigStatusEntityActive, cardConfigurationEntity.getCardFlowByCardFlowId(), applicationPaymentTypeEntity.getInterfaceByInterfaceId());

        // Check if any active, matching card flow card config matches the card types of current card config attempting to activate.

        // Current active card type groups for card flow
        List<CardTypeGroupEntity> cardTypeGroupEntityCurrent = cardTypeGroupRepository.findAllByCardConfigurationByCardConfigurationIdIn(cardConfigurationEntities);
        // Card config attempting to activate card type groups
        List<CardTypeGroupEntity> cardTypeGroupEntityAttempting = cardTypeGroupRepository.findAllByCardConfigurationId(cardConfigurationId);

        List<CardTypeEntity> cardTypesCurrent = cardTypeGroupEntityCurrent
                .stream()
                .map(CardTypeGroupEntity::getCardTypeByCardTypeId)
                .collect(Collectors.toList());

        List<CardTypeEntity> cardTypesAttempting = cardTypeGroupEntityAttempting
                .stream()
                .map(CardTypeGroupEntity::getCardTypeByCardTypeId)
                .collect(Collectors.toList());

        Boolean conflicts = cardTypesCurrent
                .stream()
                .anyMatch(cardTypesAttempting::contains);

        if (!conflicts) {
            cardConfigurationEntity.setCardConfigStatusByCardConfigStatusId(cardConfigStatusEntityActive);
            cardConfigurationRepository.save(cardConfigurationEntity);
        }

        return conflicts;
    }

    @Override
    public void deactivateConflictingCardConfigsAndActivateCurrentCardConfig(long cardConfigurationId, long applicationId) {
        CardConfigurationEntity cardConfigurationEntity = cardConfigurationRepository.findById(cardConfigurationId)
                .orElseThrow(() -> new GenericException(String.format("No card configuration with id | %s | exists", cardConfigurationId), HttpStatus.NOT_FOUND, String.format("No card configuration with id | %s | exists", cardConfigurationId)));

        ApplicationEntity applicationEntity = applicationRepository.findById(applicationId)
                .orElseThrow(() -> new GenericException(String.format("No application with id | %s | exists", applicationId), HttpStatus.NOT_FOUND, String.format("No application with id | %s | exists", applicationId)));

        PaymentTypeEntity paymentTypeEntity = paymentTypeRepository.findByCode(PaymentTypeEnum.CARD.value());

        ApplicationPaymentTypeEntity applicationPaymentTypeEntity = applicationPaymentTypeRepository.findDistinctFirstByApplicationByApplicationIdAndPaymentTypeByPaymentTypeId(applicationEntity, paymentTypeEntity)
                .orElseThrow(() -> new GenericException(String.format("No application payment type with payment type: CARD and application id | %s | exists", applicationId), HttpStatus.NOT_FOUND, String.format("No application payment type with payment type: CARD and application id | %s | exists", applicationId)));


        CardConfigStatusEntity cardConfigStatusEntityActive = cardConfigStatusRepository.findByStatus("ACTIVE");
        CardConfigStatusEntity cardConfigStatusEntityInactive = cardConfigStatusRepository.findByStatus("INACTIVE");

        List<CardConfigurationEntity> cardConfigurationEntities = cardConfigurationRepository.findAllByCardConfigStatusByCardConfigStatusIdAndCardFlowByCardFlowIdAndInterfaceByInterfaceId(cardConfigStatusEntityActive, cardConfigurationEntity.getCardFlowByCardFlowId(), applicationPaymentTypeEntity.getInterfaceByInterfaceId());

        // Current active card type groups for card flow
        List<CardTypeGroupEntity> cardTypeGroupEntityCurrent = cardTypeGroupRepository.findAllByCardConfigurationByCardConfigurationIdIn(cardConfigurationEntities);
        // Card config attempting to activate card type groups
        List<CardTypeGroupEntity> cardTypeGroupEntityAttempting = cardTypeGroupRepository.findAllByCardConfigurationId(cardConfigurationId);

        cardTypeGroupEntityCurrent
                .forEach(cardTypeGroupEntityCurrentSingle -> {
                    cardTypeGroupEntityAttempting
                            .forEach(cardTypeGroupEntityAttemptingSingle -> {
                                if (cardTypeGroupEntityAttemptingSingle.getCardTypeByCardTypeId().equals(cardTypeGroupEntityCurrentSingle.getCardTypeByCardTypeId())) {
                                    CardConfigurationEntity cardConfigurationEntityDeactivate = cardTypeGroupEntityCurrentSingle.getCardConfigurationByCardConfigurationId();
                                    cardConfigurationEntityDeactivate.setCardConfigStatusByCardConfigStatusId(cardConfigStatusEntityInactive);
                                    cardConfigurationRepository.save(cardConfigurationEntityDeactivate);
                                }
                            });
                });

        cardConfigurationEntity.setCardConfigStatusByCardConfigStatusId(cardConfigStatusEntityActive);
        cardConfigurationRepository.save(cardConfigurationEntity);
    }

    @Override
    public void deactivateCardConfig(long cardConfigurationId, long applicationId) {
        CardConfigurationEntity cardConfigurationEntity = cardConfigurationRepository.findById(cardConfigurationId)
                .orElseThrow(() -> new GenericException(String.format("No card configuration with id | %s | exists", cardConfigurationId), HttpStatus.NOT_FOUND, String.format("No card configuration with id | %s | exists", cardConfigurationId)));

        CardConfigStatusEntity cardConfigStatusEntityInactive = cardConfigStatusRepository.findByStatus("INACTIVE");

        cardConfigurationEntity.setCardConfigStatusByCardConfigStatusId(cardConfigStatusEntityInactive);
        cardConfigurationRepository.save(cardConfigurationEntity);
    }

    @Override
    public Boolean interfaceExists(long merchantId, long applicationId) {
        ApplicationEntity applicationEntity = applicationRepository.findById(applicationId)
                .orElseThrow(() -> new GenericException(String.format("No application with id | %s | exists", applicationId), HttpStatus.NOT_FOUND, String.format("No application with id | %s | exists", applicationId)));

        PaymentTypeEntity paymentTypeEntity = paymentTypeRepository.findByCode(PaymentTypeEnum.CARD.value());

        return applicationPaymentTypeRepository
                .findDistinctFirstByApplicationByApplicationIdAndPaymentTypeByPaymentTypeId(applicationEntity, paymentTypeEntity)
                .isPresent();
    }
}
