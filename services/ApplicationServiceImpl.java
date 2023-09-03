package za.co.wirecard.channel.backoffice.services;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;
import za.co.wirecard.channel.backoffice.config.ApplicationConstants;
import za.co.wirecard.channel.backoffice.config.UtilityService;
import za.co.wirecard.channel.backoffice.config.Utils;
import za.co.wirecard.channel.backoffice.dto.models.requests.CreateDefaultApplicationWithConfiguration;
import za.co.wirecard.channel.backoffice.dto.models.requests.PlatformCreateApplicationConfigurationRequest;
import za.co.wirecard.channel.backoffice.dto.models.requests.PlatformCreateApplicationRequest;
import za.co.wirecard.channel.backoffice.dto.models.responses.PlatformCreateApplicationResponse;
import za.co.wirecard.channel.backoffice.entities.*;
import za.co.wirecard.channel.backoffice.exceptions.ApplicationException;
import za.co.wirecard.channel.backoffice.exceptions.EmailAlreadyInUseException;
import za.co.wirecard.channel.backoffice.exceptions.ExceptionExtender;
import za.co.wirecard.channel.backoffice.exceptions.GenericException;
import za.co.wirecard.channel.backoffice.models.*;
import za.co.wirecard.channel.backoffice.repositories.*;
import za.co.wirecard.common.exceptions.MerchantNotFoundException;
import za.co.wirecard.common.exceptions.NotFoundException;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class ApplicationServiceImpl implements ApplicationService {
    @Value("${api.applicationmanagement.url}")
    private String applicationManagementUrl;
    @Value("${api.configurationmanagement.url}")
    private String configurationManagementUrl;
    @Value("${api.applicationconfiguration.url}")
    private String applicationConfigurationUrl;

    private static final Logger logger = LogManager.getLogger(ApplicationService.class);

    private final RestTemplate restTemplate;
    private final UtilityService utilityService;

    private final ApplicationRepository applicationRepository;
    private final MerchantRepository merchantRepository;
    private final ApplicationConfigurationRepository applicationConfigurationRepository;
    private final ApplicationSecuritySettingRepository applicationSecuritySettingRepository;
    private final ApplicationJwtSettingRepository applicationJwtSettingRepository;
    private final ConfigurationTypeRepository configurationTypeRepository;
    private final SecurityTypeRepository securityTypeRepository;
    private final ProductRepository productRepository;
    private final MerchantProductRepository merchantProductRepository;
    private StatusRepository statusRepository;

    public ApplicationServiceImpl(RestTemplate restTemplate, UtilityService utilityService, ApplicationRepository applicationRepository,
                                  MerchantRepository merchantRepository, ApplicationConfigurationRepository applicationConfigurationRepository,
                                  ApplicationSecuritySettingRepository applicationSecuritySettingRepository,
                                  ConfigurationTypeRepository configurationTypeRepository, SecurityTypeRepository securityTypeRepository,
                                  ApplicationJwtSettingRepository applicationJwtSettingRepository, ProductRepository productRepository, MerchantProductRepository merchantProductRepository, StatusRepository statusRepository) {
        this.restTemplate = restTemplate;
        this.utilityService = utilityService;
        this.applicationRepository = applicationRepository;
        this.merchantRepository = merchantRepository;
        this.applicationConfigurationRepository = applicationConfigurationRepository;
        this.applicationSecuritySettingRepository = applicationSecuritySettingRepository;
        this.configurationTypeRepository = configurationTypeRepository;
        this.securityTypeRepository = securityTypeRepository;
        this.applicationJwtSettingRepository = applicationJwtSettingRepository;
        this.productRepository = productRepository;
        this.merchantProductRepository = merchantProductRepository;
        this.statusRepository = statusRepository;
    }

    @Override
    public PlatformCreateApplicationResponse createApplication(PlatformCreateApplicationRequest application) throws ApplicationException {
        String url = applicationManagementUrl;
        ResponseEntity<PlatformCreateApplicationResponse> responseEntity;
        logger.info("Add Application" + "URL" + url + " APPLICATION " + application);
        try {
            responseEntity = restTemplate.postForEntity(url, application, PlatformCreateApplicationResponse.class);
            logger.info("APPLICATION_ADDED");
        } catch (RestClientResponseException e) {
            throw new ApplicationException(e.getMessage(), HttpStatus.valueOf(e.getRawStatusCode()), e.getResponseBodyAsString());
        }
        return responseEntity.getBody();
    }

    @Override
    public ResponseEntity<Application> getApplication(long applicationId) throws NotFoundException, EmailAlreadyInUseException {
        String url = applicationManagementUrl + "/" + applicationId;
        ResponseEntity<Application> applicationResponseEntity;
        try {
            applicationResponseEntity = restTemplate.exchange(url, HttpMethod.GET, new HttpEntity<>(utilityService.getHttpHeaders()), Application.class);
        } catch (RestClientResponseException e) {
            if (HttpStatus.valueOf(e.getRawStatusCode()) == HttpStatus.NOT_FOUND) {
                throw new NotFoundException("Application with id | " + applicationId);
            }
            throw new EmailAlreadyInUseException(e.getMessage(), HttpStatus.valueOf(e.getRawStatusCode()), e.getResponseBodyAsString());
        }
        return applicationResponseEntity;
    }

    @Override
    public PlatformCreateApplicationResponse editApplication(PlatformCreateApplicationRequest application, long applicationId) throws ExceptionExtender {
        String url = applicationManagementUrl + "/" + applicationId;
        logger.info("Add Application" + "URL" + url + " APPLICATION " + application);
        ResponseEntity<PlatformCreateApplicationResponse> editApplicationResponseEntity;
        try {
            editApplicationResponseEntity = restTemplate.exchange(url, HttpMethod.PUT, new HttpEntity<>(application, utilityService.getHttpHeaders()), PlatformCreateApplicationResponse.class);
        } catch (RestClientResponseException e) {
            throw new ExceptionExtender(e.getMessage(), HttpStatus.valueOf(e.getRawStatusCode()), e.getResponseBodyAsString());
        }
        return editApplicationResponseEntity.getBody();
    }

    @Override
    public void deleteApplication(long merchantId) {

    }

    @Override
    public List<SecurityType> getSecurityTypes() throws ApplicationException {
        String url = configurationManagementUrl + "/security-types";
        logger.info("GET_SECURITY_TYPES_URL: " + url);
        ParameterizedTypeReference<List<SecurityType>> responseType = new ParameterizedTypeReference<List<SecurityType>>() {
        };
        ResponseEntity<List<SecurityType>> securityTypes;
        try {
            securityTypes = restTemplate.exchange(url, HttpMethod.GET, new HttpEntity<>(null, utilityService.getHttpHeaders()), responseType);
            logger.info("SECURITY_TYPES_RETURNED: " + securityTypes.getBody());
        } catch (RestClientResponseException e) {
            throw new ApplicationException(e.getMessage(), HttpStatus.valueOf(e.getRawStatusCode()), e.getResponseBodyAsString());
        }
        return securityTypes.getBody();
    }

    @Override
    public Page<Application> getApplicationsByMerchantId(int page, int limit, Specification specification) throws ApplicationException {
        Pageable pageable = PageRequest.of(page, limit, Sort.by(Sort.Order.desc("lastModified")));
        Page<ApplicationEntity> applicationEntities = applicationRepository.findAll(specification, pageable);
        return applicationEntities.map((applicationEntity) -> new Application(applicationEntity.getId(), new Merchant(applicationEntity.getMerchantByMerchantId()),
                applicationEntity.getName(), applicationEntity.isAutoSettle(), applicationEntity.getApplicationUid(),
                applicationEntity.getLastModified(), null, null, applicationEntity.getStatusByStatusId()));
    }

    @Override
    public List<ConfigurationType> getConfigurationTypes() throws ApplicationException {
        String url = configurationManagementUrl + "/configuration-types";
        ParameterizedTypeReference<List<ConfigurationType>> responseType = new ParameterizedTypeReference<List<ConfigurationType>>() {
        };
        ResponseEntity<List<ConfigurationType>> configurationTypes;
        try {
            configurationTypes = restTemplate.exchange(url, HttpMethod.GET, null, responseType);
        } catch (RestClientResponseException e) {
            throw new ApplicationException(e.getMessage(), HttpStatus.valueOf(e.getRawStatusCode()), e.getResponseBodyAsString());
        }
        logger.info("This is the Security Types Object that gets returned: " + configurationTypes.getBody());
        return configurationTypes.getBody();
    }

    @Override
    public void createSecurityTypes() {

    }

    @Override
    public void createApplicationConfiguration(PlatformCreateApplicationConfigurationRequest applicationConfiguration) throws ApplicationException {
        String url = applicationConfigurationUrl;
        logger.info("Add Application Configuration" + "URL" + url + " APPLICATION_CONFIGURATION " + applicationConfiguration);
        try {
            restTemplate.postForEntity(url, new HttpEntity<>(applicationConfiguration, utilityService.getHttpHeaders()), PlatformCreateApplicationResponse.class);
            logger.info("APPLICATION_CONFIGURATION_ADDED");
        } catch (RestClientResponseException e) {
            throw new ApplicationException(e.getMessage(), HttpStatus.valueOf(e.getRawStatusCode()), e.getResponseBodyAsString());
        }

    }

    @Override
    public ApplicationEntity createDefaultApplicationWithConfiguration(CreateDefaultApplicationWithConfiguration createDefaultApplicationWithConfiguration, long merchantId, HttpServletRequest servletRequest) {
        try {
            MerchantEntity merchantEntity = merchantRepository.findById(merchantId).orElseThrow(() -> new MerchantNotFoundException(merchantId));
            ApplicationEntity applicationEntity = new ApplicationEntity();
            applicationEntity.setName(createDefaultApplicationWithConfiguration.getName());
            applicationEntity.setLastModified(new java.sql.Timestamp(new Date().getTime()));
            applicationEntity.setApplicationUid(UUID.randomUUID().toString().toUpperCase());
            applicationEntity.setAutoSettle(createDefaultApplicationWithConfiguration.isAutoSettle());
            applicationEntity.setMerchantByMerchantId(merchantEntity);
            StatusEntity statusEntity = statusRepository.findByCode(Utils.MERCHANT_STATUS_DEVELOPING_CODE);
            applicationEntity.setStatusByStatusId(statusEntity);
            applicationEntity.setActive(true);
            applicationEntity.setCreatedDate(new java.sql.Timestamp(new Date().getTime()));
            applicationEntity.setLastModifiedBy(Utils.getUserFromSession(servletRequest));
            ApplicationEntity applicationEntitySaved = applicationRepository.save(applicationEntity);
            logger.info("saved new application: " + applicationEntitySaved.getName());

            // Configure configuration,security settings based on products

            List<ConfigurationTypeEntity> configurationTypeEntities = configurationTypeRepository.findAll();
            SecurityTypeEntity jwtSecurityType = securityTypeRepository.findByCode("JWTPostback");

            ApplicationSecuritySettingEntity applicationSecuritySettingEntity = new ApplicationSecuritySettingEntity();
            applicationSecuritySettingEntity.setApplicationId(applicationEntitySaved.getId());
            applicationSecuritySettingEntity.setSecurityTypeId(jwtSecurityType.getId());
            applicationSecuritySettingEntity.setIsActive(true);
            ApplicationSecuritySettingEntity appSecuritySettingSaved = applicationSecuritySettingRepository.save(applicationSecuritySettingEntity);
            logger.info("saved jwt as security type for application: "  + applicationEntitySaved.getName());

            ApplicationJwtSettingEntity applicationJwtSettingEntity = new ApplicationJwtSettingEntity();
            applicationJwtSettingEntity.setLastModified(new java.sql.Timestamp(new Date().getTime()));
            applicationJwtSettingEntity.setApplicationSecuritySettingId(appSecuritySettingSaved.getId());
            applicationJwtSettingEntity.setInboundEnabled(true);
            applicationJwtSettingEntity.setSecretKey(UUID.randomUUID().toString());
            ApplicationJwtSettingEntity applicationJwtSettingSaved = applicationJwtSettingRepository.save(applicationJwtSettingEntity);
            logger.info("saved application jwt security setting for application: "  + applicationEntitySaved.getName());

            List<SecurityTypeEntity> securityTypeEntities = securityTypeRepository.findAll();

            for (ConfigurationTypeEntity configEntity : configurationTypeEntities) {
                if (configEntity.getCode().equals("WEBHOOK") || configEntity.getCode().equals("AUTO_REDIRECT") || configEntity.getCode().equals("PAYMENT_LINK")) {
                    ApplicationConfigurationEntity applicationConfigurationEntity = new ApplicationConfigurationEntity();
                    applicationConfigurationEntity.setApplicationId(applicationEntitySaved.getId());
                    applicationConfigurationEntity.setConfigurationTypeId(configEntity.getId());
                    applicationConfigurationEntity.setIsActive(true);
                    applicationConfigurationRepository.save(applicationConfigurationEntity);
                    logger.info("Saved app config for application: " + applicationEntitySaved.getName() + "with config code: " + configEntity.getCode());
                }
            }

            securityTypeEntities
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
                });

            return applicationEntitySaved;
        } catch (Exception e) {
            logger.error(e);
            throw new GenericException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, e.getLocalizedMessage());
        }
    }

    private void setApplicationSecuritySetting(String type, ApplicationEntity applicationEntity, SecurityTypeEntity securityTypeEntity, Boolean active) {
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
}
