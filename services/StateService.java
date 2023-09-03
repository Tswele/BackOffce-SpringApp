package za.co.wirecard.channel.backoffice.services;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import za.co.wirecard.channel.backoffice.config.Utils;
import za.co.wirecard.channel.backoffice.dto.models.requests.StateChangeRequest;
import za.co.wirecard.channel.backoffice.entities.*;
import za.co.wirecard.channel.backoffice.models.Application;
import za.co.wirecard.channel.backoffice.models.Merchant;
import za.co.wirecard.channel.backoffice.models.MerchantStatus;
import za.co.wirecard.channel.backoffice.models.TransactionState;
import za.co.wirecard.channel.backoffice.repositories.*;
import za.co.wirecard.common.exceptions.NotFoundException;

import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class StateService {

    private static final Logger logger = LogManager.getLogger(StateService.class);
    private MerchantRepository merchantRepository;
    private BackOfficeUserRepository backOfficeUserRepository;
    private StatusRepository statusRepository;
    private StateChangeRequestRepository stateChangeRequestRepository;
    private LevelRepository levelRepository;
    private ApplicationRepository applicationRepository;
    private MerchantProductRepository merchantProductRepository;
    private ApplicationPaymentTypeRepository applicationPaymentTypeRepository;
    private final String LEVEL_CODE_MERCHANT = "MERCHANT";
    private final String LEVEL_CODE_APPLICATION = "APPLICATION";
    private final String LEVEL_CODE_PRODUCT = "PRODUCT";
    private final String LEVEL_CODE_PAYMENT_TYPE= "PAYMENT_TYPE";
    private final String STATUS_CODE_CANCELLED = "CANCELLED";
    private final String STATUS_CODE_SUSPENDED = "SUSPENDED";
    private final String STATUS_CODE_ACTIVE = "ACTIVE";
    private final String STATUS_CODE_PENDING = "PENDING";


    public StateService(MerchantRepository merchantRepository, BackOfficeUserRepository backOfficeUserRepository, StatusRepository statusRepository, StateChangeRequestRepository stateChangeRequestRepository, LevelRepository levelRepository, ApplicationRepository applicationRepository, MerchantProductRepository merchantProductRepository, ApplicationPaymentTypeRepository applicationPaymentTypeRepository) {
        this.merchantRepository = merchantRepository;
        this.backOfficeUserRepository = backOfficeUserRepository;
        this.statusRepository = statusRepository;
        this.stateChangeRequestRepository = stateChangeRequestRepository;
        this.levelRepository = levelRepository;
        this.applicationRepository = applicationRepository;
        this.merchantProductRepository = merchantProductRepository;
        this.applicationPaymentTypeRepository = applicationPaymentTypeRepository;
    }

    /*Merchant State Change Methods*/
    public void cancelMerchant(long merchantId, long userId) {
        MerchantEntity merchantEntity = merchantRepository.findById(merchantId).orElseThrow(() -> new NotFoundException("Could not find merchant with id | " + merchantId));
        logger.info("Cancelling Merchant [MerchantUID: {}]", merchantEntity.getMerchantUid());
        merchantEntity.setActive(false);
        merchantEntity.setLastModified(new Timestamp(new Date().getTime()));
        merchantEntity.setCancelledDate(new Timestamp(new Date().getTime()));
        BackOfficeUserEntity modifiedBy = backOfficeUserRepository.getOne(userId);
        merchantEntity.setBackOfficeUserByModifiedBy(modifiedBy);
        merchantEntity.setMerchantStatusByMerchantStatusId(statusRepository.findByCode(Utils.MERCHANT_STATUS_CANCELLED_CODE));
        merchantRepository.save(merchantEntity);
        logger.info("Merchant Cancelled [MerchantUID: {}]", merchantEntity.getMerchantUid());
    }

    public void suspendMerchant(long merchantId, long userId) {//todo Find out about using UID in request vs ID
        MerchantEntity merchantEntity = merchantRepository.findById(merchantId).orElseThrow(() -> new NotFoundException("Could not find merchant with id | " + merchantId));
        logger.info("Suspending Merchant [MerchantUID: {}]", merchantEntity.getMerchantUid());
        merchantEntity.setActive(false);
        merchantEntity.setLastModified(new Timestamp(new Date().getTime()));// TODO Lose Suspended date when other modifications are made
        BackOfficeUserEntity modifiedBy = backOfficeUserRepository.getOne(userId);
        merchantEntity.setBackOfficeUserByModifiedBy(modifiedBy);
        merchantEntity.setMerchantStatusByMerchantStatusId(statusRepository.findByCode(Utils.MERCHANT_STATUS_SUSPENDED_CODE));
        merchantRepository.save(merchantEntity);
        logger.info("Merchant Suspended [MerchantUID: {}]", merchantEntity.getMerchantUid());
    }

    public void activateMerchant(long merchantId, long userId) { //TODO on re-activate recreate the entity instead of overwriting the old one
        MerchantEntity merchantEntity = merchantRepository.findById(merchantId).orElseThrow(() -> new NotFoundException("Could not find merchant with id | " + merchantId));
        logger.info("Activating Merchant [MerchantUID: {}]", merchantEntity.getMerchantUid());
        merchantEntity.setDeleted(false);
        merchantEntity.setActive(true);
        //merchantEntity.setActivationDate(new java.sql.Date(new Date().getTime()));
        merchantEntity.setLastModified(new Timestamp(new Date().getTime()));
        BackOfficeUserEntity modifiedBy = backOfficeUserRepository.getOne(userId);
        merchantEntity.setBackOfficeUserByModifiedBy(modifiedBy);
        merchantEntity.setMerchantStatusByMerchantStatusId(statusRepository.findByCode(Utils.MERCHANT_STATUS_ACTIVE_CODE));
        merchantRepository.save(merchantEntity);
        logger.info("Merchant Suspended [MerchantUID: {}]", merchantEntity.getMerchantUid());
    }

    public void activateMerchantPending(long merchantId, long userId) {
        MerchantEntity merchantEntity = merchantRepository.findById(merchantId).orElseThrow(() -> new NotFoundException("Could not find merchant with id | " + merchantId));
        logger.info("Setting Merchant to Pending [MerchantUID: {}]", merchantEntity.getMerchantUid());
        merchantEntity.setDeleted(false);
        merchantEntity.setActive(true);
        merchantEntity.setActivationDate(new java.sql.Date(new Date().getTime()));
        merchantEntity.setLastModified(new Timestamp(new Date().getTime()));
        BackOfficeUserEntity modifiedBy = backOfficeUserRepository.getOne(userId);
        merchantEntity.setBackOfficeUserByModifiedBy(modifiedBy);
        merchantEntity.setMerchantStatusByMerchantStatusId(statusRepository.findByCode(Utils.MERCHANT_STATUS_PENDING_CODE));
        merchantRepository.save(merchantEntity);
        logger.info("Merchant set to Pending [MerchantUID: {}]", merchantEntity.getMerchantUid());
    }

    /*Application State Change Methods*/
    public void cancelApplication(long applicationId, long userId) {
        ApplicationEntity applicationEntity = applicationRepository.findById(applicationId).orElseThrow(() -> new NotFoundException("Could not find application with id: " + applicationId));
        logger.info("Cancelling Application [ApplicationUID: {}]", applicationEntity.getApplicationUid());
        applicationEntity.setActive(false);
        applicationEntity.setLastModified(new Timestamp(new Date().getTime()));
        applicationEntity.setCancelledDate(new Timestamp(new Date().getTime()));
        applicationEntity.setStatusByStatusId(statusRepository.findByCode(STATUS_CODE_CANCELLED));
        applicationEntity.setLastModifiedBy(userId);
        applicationRepository.save(applicationEntity);
        logger.info("Application Cancelled [ApplicationUID: {}]", applicationEntity.getApplicationUid());
    }

    public void suspendApplication(long applicationId, long userId) {
        ApplicationEntity applicationEntity = applicationRepository.findById(applicationId).orElseThrow(() -> new NotFoundException("Could not find application with id: " + applicationId));
        logger.info("Suspending Application [ApplicationUID: {}]", applicationEntity.getApplicationUid());
        applicationEntity.setActive(false);
        applicationEntity.setLastModified(new Timestamp(new Date().getTime()));
        applicationEntity.setStatusByStatusId(statusRepository.findByCode(STATUS_CODE_SUSPENDED));
        applicationEntity.setLastModifiedBy(userId);
        applicationRepository.save(applicationEntity);
        logger.info("Application Suspended [ApplicationUID: {}]", applicationEntity.getApplicationUid());
    }

    public void activateApplicationPending(long applicationId, long userId) {
        ApplicationEntity applicationEntity = applicationRepository.findById(applicationId).orElseThrow(() -> new NotFoundException("Could not find application with id: " + applicationId));
        logger.info("Setting Application to Pending [ApplicationUID: {}]", applicationEntity.getApplicationUid());
        applicationEntity.setActive(true);
        applicationEntity.setLastModified(new Timestamp(new Date().getTime()));
        applicationEntity.setActivatedDate(new Timestamp(new Date().getTime()));
        applicationEntity.setStatusByStatusId(statusRepository.findByCode(STATUS_CODE_PENDING));
        applicationEntity.setLastModifiedBy(userId);
        applicationRepository.save(applicationEntity);
        logger.info("Application set to Pending [ApplicationUID: {}]", applicationEntity.getApplicationUid());
    }

    public void activateApplication(long applicationId, long userId) {
        ApplicationEntity applicationEntity = applicationRepository.findById(applicationId).orElseThrow(() -> new NotFoundException("Could not find application with id: " + applicationId));
        logger.info("Activating Application [ApplicationUID: {}]", applicationEntity.getApplicationUid());
        applicationEntity.setActive(true);
        applicationEntity.setLastModified(new Timestamp(new Date().getTime()));
        applicationEntity.setStatusByStatusId(statusRepository.findByCode(STATUS_CODE_ACTIVE));
        applicationEntity.setLastModifiedBy(userId);
        applicationRepository.save(applicationEntity);
        logger.info("Application Activated [ApplicationUID: {}]", applicationEntity.getApplicationUid());
    }
    /*Merchant Product State Change Methods*/
    public void cancelProduct(long merchantProductId,long userId){
        MerchantProductEntity merchantProductEntity = merchantProductRepository.findById(merchantProductId).orElseThrow(() -> new NotFoundException("Could not find merchant product with id: " + merchantProductId));
        logger.info("Cancelling Merchant Product [Merchant Product ID: {} ]",merchantProductId);
        merchantProductEntity.setActive(false);
        merchantProductEntity.setLastModified(new Timestamp(new Date().getTime()));
        merchantProductEntity.setDateInactive(new Timestamp(new Date().getTime()));//Todo change to cancelled_date
        merchantProductEntity.setStatusByStatusId(statusRepository.findByCode(STATUS_CODE_CANCELLED));
        merchantProductEntity.setLastModifiedBy(userId);
        merchantProductRepository.save(merchantProductEntity);
        logger.info("Merchant Product Cancelled [Merchant Product ID: {} ]",merchantProductId);
    }
    private void activateProduct(long merchantProductId, long userId) {
        MerchantProductEntity merchantProductEntity = merchantProductRepository.findById(merchantProductId).orElseThrow(() -> new NotFoundException("Could not find merchant product with id: " + merchantProductId));
        logger.info("Activating Merchant Product [Merchant Product ID: {} ]",merchantProductId);
        merchantProductEntity.setActive(true);
        merchantProductEntity.setLastModified(new Timestamp(new Date().getTime()));
        merchantProductEntity.setStatusByStatusId(statusRepository.findByCode(STATUS_CODE_ACTIVE));
        merchantProductEntity.setLastModifiedBy(userId);
        merchantProductRepository.save(merchantProductEntity);
        logger.info("Application Activated [Merchant Product ID: {} ]",merchantProductId);
    }

    private void activateProductPending(long merchantProductId, long userId) {
        MerchantProductEntity merchantProductEntity = merchantProductRepository.findById(merchantProductId).orElseThrow(() -> new NotFoundException("Could not find merchant product with id: " + merchantProductId));
        logger.info("Setting Merchant Product to Pending [Merchant Product ID: {} ]",merchantProductId);
        merchantProductEntity.setActive(true);
        merchantProductEntity.setLastModified(new Timestamp(new Date().getTime()));
        merchantProductEntity.setActivatedDate(new Timestamp(new Date().getTime()));
        merchantProductEntity.setStatusByStatusId(statusRepository.findByCode(STATUS_CODE_PENDING));
        merchantProductEntity.setLastModifiedBy(userId);
        merchantProductRepository.save(merchantProductEntity);
        logger.info("Merchant Product set to Pending [Merchant Product ID: {} ]",merchantProductId);
    }
    private void suspendProduct(long merchantProductId, long userId) {
        MerchantProductEntity merchantProductEntity = merchantProductRepository.findById(merchantProductId).orElseThrow(() -> new NotFoundException("Could not find merchant product with id: " + merchantProductId));
        logger.info("Suspending Merchant Product [Merchant Product ID: {} ]",merchantProductId);
        merchantProductEntity.setActive(false);
        merchantProductEntity.setLastModified(new Timestamp(new Date().getTime()));
        merchantProductEntity.setStatusByStatusId(statusRepository.findByCode(STATUS_CODE_SUSPENDED));
        merchantProductEntity.setLastModifiedBy(userId);
        merchantProductRepository.save(merchantProductEntity);
        logger.info("Merchant Product Suspended [Merchant Product ID: {} ]",merchantProductId);
    }
    /*Application Payment Type State Change Methods*/
    private void activatePaymentType(long paymentTypeId, long userId) {
        ApplicationPaymentTypeEntity applicationPaymentTypeEntity = applicationPaymentTypeRepository.findById(paymentTypeId).orElseThrow(() -> new NotFoundException("Could not find application payment type with id: " + paymentTypeId));
        logger.info("Activating Application Payment Type [Application Payment Type ID: {} ]",paymentTypeId);
        applicationPaymentTypeEntity.setActive(true);
        applicationPaymentTypeEntity.setLastModified(new Timestamp(new Date().getTime()));
        applicationPaymentTypeEntity.setStatusByStatusId(statusRepository.findByCode(STATUS_CODE_ACTIVE));
        applicationPaymentTypeEntity.setLastModifiedBy(userId);
        applicationPaymentTypeRepository.save(applicationPaymentTypeEntity);
        logger.info("Application Payment Type Activated [Application Payment Type: {} ]",paymentTypeId);
    }

    private void activatePaymentTypePending(long paymentTypeId, long userId) {
        ApplicationPaymentTypeEntity applicationPaymentTypeEntity = applicationPaymentTypeRepository.findById(paymentTypeId).orElseThrow(() -> new NotFoundException("Could not find application payment type with id: " + paymentTypeId));
        logger.info("Setting Application Payment Type to Pending [Application Payment Type ID: {} ]",paymentTypeId);
        applicationPaymentTypeEntity.setActive(true);
        applicationPaymentTypeEntity.setLastModified(new Timestamp(new Date().getTime()));
        applicationPaymentTypeEntity.setActivatedDate(new Timestamp(new Date().getTime()));
        applicationPaymentTypeEntity.setStatusByStatusId(statusRepository.findByCode(STATUS_CODE_PENDING));
        applicationPaymentTypeEntity.setLastModifiedBy(userId);
        applicationPaymentTypeRepository.save(applicationPaymentTypeEntity);
        logger.info("Application Payment Type set to Pending [Merchant Product ID: {} ]",paymentTypeId);
    }

    private void suspendPaymentType(long paymentTypeId, long userId) {
        ApplicationPaymentTypeEntity applicationPaymentTypeEntity = applicationPaymentTypeRepository.findById(paymentTypeId).orElseThrow(() -> new NotFoundException("Could not find application payment type with id: " + paymentTypeId));
        logger.info("Suspending Application Payment Type [Application Payment Type ID: {} ]",paymentTypeId);
        applicationPaymentTypeEntity.setActive(false);
        applicationPaymentTypeEntity.setLastModified(new Timestamp(new Date().getTime()));
        applicationPaymentTypeEntity.setStatusByStatusId(statusRepository.findByCode(STATUS_CODE_SUSPENDED));
        applicationPaymentTypeEntity.setLastModifiedBy(userId);
        applicationPaymentTypeRepository.save(applicationPaymentTypeEntity);
        logger.info("Application Payment Type Suspended [Application Payment Type ID: {} ]",paymentTypeId);
    }

    private void cancelPaymentType(long paymentTypeId, long userId) {
        ApplicationPaymentTypeEntity applicationPaymentTypeEntity = applicationPaymentTypeRepository.findById(paymentTypeId).orElseThrow(() -> new NotFoundException("Could not find application payment type with id: " + paymentTypeId));
        logger.info("Cancelling Merchant Product [Merchant Product ID: {} ]",paymentTypeId);
        applicationPaymentTypeEntity.setActive(false);
        applicationPaymentTypeEntity.setLastModified(new Timestamp(new Date().getTime()));
        applicationPaymentTypeEntity.setCancelledDate(new Timestamp(new Date().getTime()));
        applicationPaymentTypeEntity.setStatusByStatusId(statusRepository.findByCode(STATUS_CODE_CANCELLED));
        applicationPaymentTypeEntity.setLastModifiedBy(userId);
        applicationPaymentTypeRepository.save(applicationPaymentTypeEntity);
        logger.info("Merchant Product Cancelled [Merchant Product ID: {} ]",paymentTypeId);
    }

    public void updateState(StateChangeRequest stateChangeRequest, HttpServletRequest servletRequest) {
        long userId = Utils.getUserFromSession(servletRequest);
        logger.info("Saving State Change Request: {}", stateChangeRequest);
        StateChangeRequestEntity stateChangeRequestEntity = new StateChangeRequestEntity();
        stateChangeRequestEntity.setLevel(levelRepository.findByCode(stateChangeRequest.getLevelCode().toUpperCase()).getId());
        stateChangeRequestEntity.setCurrentStateId(statusRepository.findByCode(stateChangeRequest.getCurrentStateCode().toUpperCase()).getId());//This is needed in the front end to display, so we use it instead of getting it from the DB.
        stateChangeRequestEntity.setRequestedId(stateChangeRequest.getRequestedId());
        stateChangeRequestEntity.setRequestedStateId(statusRepository.findByCode(stateChangeRequest.getRequestedStateCode()).getId());
        stateChangeRequestEntity.setRequestedDate(new Timestamp(new Date().getTime()));
        stateChangeRequestEntity.setBackOfficeUserId(userId);
        stateChangeRequestRepository.save(stateChangeRequestEntity);
        switch (stateChangeRequest.getLevelCode()) {// Level Code is set by front end on each level's function to invoke the dialog
            /*Merchant Level*/
            case LEVEL_CODE_MERCHANT:
                switch (stateChangeRequest.getRequestedStateCode()) {
                    case STATUS_CODE_CANCELLED:
                        cancelMerchant(stateChangeRequest.getRequestedId(), userId);
                        break;
                    case STATUS_CODE_SUSPENDED:
                        suspendMerchant(stateChangeRequest.getRequestedId(), userId);
                        break;
                    case STATUS_CODE_ACTIVE:
                        activateMerchant(stateChangeRequest.getRequestedId(), userId);//TODO add if check here to check if the current state is cancelled, then call reactivate method. Should this be done in the Pending state rather? With more strict flow control it might become necessary
                        break;
                    case STATUS_CODE_PENDING:
                        activateMerchantPending(stateChangeRequest.getRequestedId(), userId);
                        break;
                    default:
                        throw new IllegalStateException("Status Code Unexpected value: " + stateChangeRequest.getRequestedStateCode());
                }
                break;
            /*Application Level*/
            case LEVEL_CODE_APPLICATION:
                switch (stateChangeRequest.getRequestedStateCode()) {
                    case STATUS_CODE_CANCELLED:
                        cancelApplication(stateChangeRequest.getRequestedId(), userId);
                        break;
                    case STATUS_CODE_SUSPENDED:
                        suspendApplication(stateChangeRequest.getRequestedId(), userId);
                        break;
                    case STATUS_CODE_PENDING:
                        activateApplicationPending(stateChangeRequest.getRequestedId(), userId);
                        break;
                    case STATUS_CODE_ACTIVE:
                        activateApplication(stateChangeRequest.getRequestedId(), userId);
                        break;
                    default:
                        throw new IllegalStateException("Status Code Unexpected value: " + stateChangeRequest.getRequestedStateCode());
                }
                break;
            case LEVEL_CODE_PRODUCT:
                switch (stateChangeRequest.getRequestedStateCode()) {
                    case STATUS_CODE_CANCELLED:
                        cancelProduct(stateChangeRequest.getRequestedId(),userId);
                        break;
                    case STATUS_CODE_SUSPENDED:
                        suspendProduct(stateChangeRequest.getRequestedId(),userId);
                        break;
                    case STATUS_CODE_PENDING:
                        activateProductPending(stateChangeRequest.getRequestedId(),userId);
                        break;
                    case STATUS_CODE_ACTIVE:
                        activateProduct(stateChangeRequest.getRequestedId(),userId);
                        break;
                    default:
                        throw new IllegalStateException("Status Code Unexpected value: " + stateChangeRequest.getRequestedStateCode());
                }
                break;
            case LEVEL_CODE_PAYMENT_TYPE:
                switch (stateChangeRequest.getRequestedStateCode()) {
                    case STATUS_CODE_CANCELLED:
                        cancelPaymentType(stateChangeRequest.getRequestedId(),userId);
                        break;
                    case STATUS_CODE_SUSPENDED:
                        suspendPaymentType(stateChangeRequest.getRequestedId(),userId);
                        break;
                    case STATUS_CODE_PENDING:
                        activatePaymentTypePending(stateChangeRequest.getRequestedId(),userId);
                        break;
                    case STATUS_CODE_ACTIVE:
                        activatePaymentType(stateChangeRequest.getRequestedId(),userId);
                        break;
                    default:
                        throw new IllegalStateException("Status Code Unexpected value: " + stateChangeRequest.getRequestedStateCode());
                }
                break;
            default:
                throw new IllegalStateException("Level Code Unexpected value: " + stateChangeRequest.getLevelCode());
        }
    }         // todo add logs

    public List<MerchantStatus> getStates() {
        logger.info("Retrieving States");
        List<MerchantStatus> merchantStatuses = statusRepository
                .findAll()
                .stream()
                .map(MerchantStatus::new)
                .collect(Collectors.toList());
        logger.info("Retrieving States {}", merchantStatuses);
        merchantStatuses.removeIf(merchantStatus -> Objects.equals(merchantStatus.getCode(), "ONBOARDING"));
        merchantStatuses.removeIf(merchantStatus -> Objects.equals(merchantStatus.getCode(), "DEVELOPING"));
        return merchantStatuses;
    }
//TODO One call for state change or one call per state?

    // /getAllowedStatesForChange
    // /revertPreviousStateChange
    // history for state changes? currentState requestedState BackOfficeUser timestamp level(Merchant/Product/Application)

    //button in front end on each level that brings up a modal to change the state.
    // Request is sent to back end with the requested state, the level and requester is then determined
    // main method to determine the correct submethod to use /suspendMerchant /cancelMerchant
    // ? use method to determine level as well or have seperate method for each.

    /* Design Front end to be simple and cater back end or create back end first and model front end around it.
     * */
}
