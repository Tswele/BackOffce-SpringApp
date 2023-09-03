package za.co.wirecard.channel.backoffice.services;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import za.co.wirecard.channel.backoffice.dto.models.*;
import za.co.wirecard.channel.backoffice.dto.models.requests.*;
import za.co.wirecard.channel.backoffice.dto.models.responses.*;
import za.co.wirecard.channel.backoffice.entities.*;
import za.co.wirecard.channel.backoffice.exceptions.GenericException;
import za.co.wirecard.channel.backoffice.models.PricingModel;
import za.co.wirecard.channel.backoffice.models.email.EmailNotification;
import za.co.wirecard.channel.backoffice.mq.SendEmail;
import za.co.wirecard.channel.backoffice.repositories.*;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.*;

import static za.co.wirecard.channel.backoffice.builder.RateStructureSpecifications.*;


@Service
public class RateStructureServiceImpl implements RateStructureService{

    private final RateStructureRepository rateStructureRepository;
    private final RateStructureVersionRepository rateStructureVersionRepository;
    private final RateStructureStateRepository rateStructureStateRepository;
    private final BillingFrequencyRepository billingFrequencyRepository;
    private final BackOfficeUserRepository backOfficeUserRepository;
    private final RateStructureItemRepository rateStructureItemRepository;
    private final RateStructureItemClassificationRepository rateStructureItemClassificationRepository;
    private final RateStructureLineItemRepository rateStructureLineItemRepository;
    private final TransactionActionRepository transactionActionRepository;
    private final PricingModelRepository pricingModelRepository;
    private final CurrencyRepository currencyRepository;
    private final PricingModelVersionRepository pricingModelVersionRepository;
    private final FixedChargeRepository fixedChargeRepository;
    private final FixedChargeRateStructureLineItemRepository fixedChargeRateStructureLineItemRepository;
    private final VariableChargeRepository variableChargeRepository;
    private final VariableChargeRateStructureLineItemRepository variableChargeRateStructureLineItemRepository;
    private final MinimumBillingRepository minimumBillingRepository;
    private final MinimumBillingRateStructureLineItemRepository minimumBillingRateStructureLineItemRepository;
    private final ProductRepository productRepository;
    private final MerchantProductRepository merchantProductRepository;
    private final MerchantRepository merchantRepository;
    private final SendEmail sendEmail;
    private final BackOfficeGroupRepository backOfficeGroupRepository;
    private final BackOfficePermissionRepository backOfficePermissionRepository;
    private final BackOfficeGroupPermissionRepository backOfficeGroupPermissionRepository;


    private static final Logger logger = LogManager.getLogger(RateStructureServiceImpl.class);

    public RateStructureServiceImpl(RateStructureRepository rateStructureRepository,
                                    RateStructureVersionRepository rateStructureVersionRepository,
                                    RateStructureStateRepository rateStructureStateRepository,
                                    BillingFrequencyRepository billingFrequencyRepository,
                                    BackOfficeUserRepository backOfficeUserRepository,
                                    RateStructureItemRepository rateStructureItemRepository,
                                    RateStructureItemClassificationRepository rateStructureItemClassificationRepository,
                                    RateStructureLineItemRepository rateStructureLineItemRepository,
                                    TransactionActionRepository transactionActionRepository,
                                    PricingModelRepository pricingModelRepository,
                                    CurrencyRepository currencyRepository,
                                    PricingModelVersionRepository pricingModelVersionRepository,
                                    FixedChargeRepository fixedChargeRepository,
                                    FixedChargeRateStructureLineItemRepository fixedChargeRateStructureLineItemRepository,
                                    VariableChargeRepository variableChargeRepository,
                                    VariableChargeRateStructureLineItemRepository variableChargeRateStructureLineItemRepository,
                                    MinimumBillingRepository minimumBillingRepository,
                                    MinimumBillingRateStructureLineItemRepository minimumBillingRateStructureLineItemRepository,
                                    ProductRepository productRepository,
                                    MerchantProductRepository merchantProductRepository,
                                    MerchantRepository merchantRepository,
                                    SendEmail sendEmail,
                                    BackOfficeGroupRepository backOfficeGroupRepository,
                                    BackOfficePermissionRepository backOfficePermissionRepository,
                                    BackOfficeGroupPermissionRepository backOfficeGroupPermissionRepository) {
        this.rateStructureRepository = rateStructureRepository;
        this.rateStructureVersionRepository = rateStructureVersionRepository;
        this.rateStructureStateRepository = rateStructureStateRepository;
        this.billingFrequencyRepository = billingFrequencyRepository;
        this.backOfficeUserRepository = backOfficeUserRepository;
        this.rateStructureItemRepository = rateStructureItemRepository;
        this.rateStructureItemClassificationRepository = rateStructureItemClassificationRepository;
        this.rateStructureLineItemRepository = rateStructureLineItemRepository;
        this.transactionActionRepository = transactionActionRepository;
        this.pricingModelRepository = pricingModelRepository;
        this.currencyRepository = currencyRepository;
        this.pricingModelVersionRepository = pricingModelVersionRepository;
        this.fixedChargeRepository = fixedChargeRepository;
        this.fixedChargeRateStructureLineItemRepository = fixedChargeRateStructureLineItemRepository;
        this.variableChargeRepository = variableChargeRepository;
        this.variableChargeRateStructureLineItemRepository = variableChargeRateStructureLineItemRepository;
        this.minimumBillingRepository = minimumBillingRepository;
        this.minimumBillingRateStructureLineItemRepository = minimumBillingRateStructureLineItemRepository;
        this.productRepository = productRepository;
        this.merchantProductRepository = merchantProductRepository;
        this.merchantRepository = merchantRepository;
        this.sendEmail = sendEmail;
        this.backOfficeGroupRepository = backOfficeGroupRepository;
        this.backOfficePermissionRepository = backOfficePermissionRepository;
        this.backOfficeGroupPermissionRepository = backOfficeGroupPermissionRepository;
    }

    @Override
    public Page<RateStructure> getAllRateStructures(int page, int limit) {
        ArrayList<String> activeAndPendingStates = new ArrayList<>();
        activeAndPendingStates.add("ACTIVE");
        activeAndPendingStates.add("PENDING_APPROVAL");
        Specification<RateStructureEntity> specification = Specification.where(
                rateStructureStates(activeAndPendingStates)
        );
        Pageable pageable = PageRequest.of(page, limit);
        Page<RateStructureEntity> rateStructureEntities = rateStructureRepository.findAll(specification, pageable);

        return rateStructureEntities.map((rateStructureEntity) -> {
            String state = "";
            Long version = null;
            for (RateStructureVersionEntity rateStructureVersionEntity : rateStructureEntity.getRateStructureVersionsById()) {
                if(rateStructureVersionEntity.getRateStructureStateByRateStructureStateId().getCode().equalsIgnoreCase("ACTIVE") || rateStructureVersionEntity.getRateStructureStateByRateStructureStateId().getCode().equalsIgnoreCase("PENDING_APPROVAL")) {
                    state = rateStructureVersionEntity.getRateStructureStateByRateStructureStateId().getName();
                    version = rateStructureVersionEntity.getVersion();
                }
            }
            return new RateStructure(rateStructureEntity, state, version);
        });
    }

    @Override
    public ArrayList<RateStructure> getHistoricalRateStructures(Long id) {
        ArrayList<RateStructure> response = new ArrayList<>();
        RateStructureStateEntity historicalStateEntity = rateStructureStateRepository.findByCode("HISTORICAL");
        ArrayList<RateStructureVersionEntity> rateStructureVersionEntities = rateStructureVersionRepository.findAllByRateStructureIdAndRateStructureStateByRateStructureStateId(id, historicalStateEntity);

        for (RateStructureVersionEntity rateStructureVersionEntity : rateStructureVersionEntities) {
            RateStructureEntity rateStructureEntity = rateStructureRepository.findById(rateStructureVersionEntity.getRateStructureId()).orElseThrow(() -> new GenericException("Rate Structure Not Found", HttpStatus.NOT_FOUND, "No rate structure found for id | " + id));
            response.add(new RateStructure(rateStructureEntity, rateStructureVersionEntity.getRateStructureStateByRateStructureStateId().getName(), rateStructureVersionEntity.getVersion()));
        }

        return response;
    }

    @Override
    public List<BillingFrequency> getBillingFrequencies() {
        List<BillingFrequencyEntity> billingFrequencyEntities = billingFrequencyRepository.findAll();
        List<BillingFrequency> billingFrequencies = new ArrayList<>();
        for (BillingFrequencyEntity billingFrequencyEntity: billingFrequencyEntities) {
            billingFrequencies.add(new BillingFrequency(billingFrequencyEntity));
        }
        return billingFrequencies;
    }

    @Override
    public GetRateStructureLineItemsResponse getRateStructureLineItems() {
        List<RateStructureLineItemEntity> rateStructureLineItemEntities = rateStructureLineItemRepository.findAll();
        List<RateStructureLineItem> fixedCharges = new ArrayList<>();
        List<RateStructureLineItem> variableCharges = new ArrayList<>();
        List<RateStructureLineItem> minimumBilling = new ArrayList<>();
        for (RateStructureLineItemEntity rateStructureLineItemEntity: rateStructureLineItemEntities) {
            if (rateStructureLineItemEntity.getIsfixedcharge()) {
                fixedCharges.add(
                    new RateStructureLineItem(
                            rateStructureLineItemEntity,
                            rateStructureLineItemEntity.getTransactionActionByTransactionActionId() != null ? new TransactionAction(rateStructureLineItemEntity.getTransactionActionByTransactionActionId()) : null
                    )
                );
            } else {
                variableCharges.add(
                    new RateStructureLineItem(
                            rateStructureLineItemEntity,
                            rateStructureLineItemEntity.getTransactionActionByTransactionActionId() != null ? new TransactionAction(rateStructureLineItemEntity.getTransactionActionByTransactionActionId()) : null
                    )
                );
                minimumBilling.add(
                        new RateStructureLineItem(
                                rateStructureLineItemEntity,
                                rateStructureLineItemEntity.getTransactionActionByTransactionActionId() != null ? new TransactionAction(rateStructureLineItemEntity.getTransactionActionByTransactionActionId()) : null
                        )
                );
            }
        }
        return new GetRateStructureLineItemsResponse(fixedCharges, variableCharges, minimumBilling);
    }

    @Override
    public GetRateStructureResponse getRateStructureById(long rateStructureId, long rateStructureVersion) {
        GetRateStructureResponse getRateStructureResponse = new GetRateStructureResponse();

        RateStructureEntity rateStructureEntity = rateStructureRepository.getOne(rateStructureId);
        RateStructureVersionEntity rateStructureVersionEntity = new RateStructureVersionEntity();
        for (RateStructureVersionEntity versionEntity: rateStructureEntity.getRateStructureVersionsById()) {
            if (versionEntity.getVersion() == rateStructureVersion) {
                rateStructureVersionEntity = versionEntity;
                break;
            }
        }
        getRateStructureResponse.setRateStructure(
                new RateStructure(
                        rateStructureEntity,
                        rateStructureVersionEntity.getVersion(),
                        rateStructureVersionEntity.getRateStructureStateByRateStructureStateId().getName()
                )
        );

        List<RateStructureLineItem> fixedCharges = new ArrayList<>();
        List<RateStructureLineItem> variableCharges = new ArrayList<>();
        List<RateStructureLineItem> minimumBilling = new ArrayList<>();

        for (RateStructureItemEntity rateStructureItemEntity: rateStructureVersionEntity.getRateStructureItemsById()) {
            if (rateStructureItemEntity.getRateStructureItemClassificationByRateStructureItemClassificationId().getCode().equalsIgnoreCase("FIXED_CHARGE")) {
                fixedCharges.add(
                    new RateStructureLineItem(
                            rateStructureItemEntity.getRateStructureLineItemByRateStructureLineItemId(),
                            rateStructureItemEntity.getRateStructureLineItemByRateStructureLineItemId().getTransactionActionByTransactionActionId() != null ?
                                    new TransactionAction(rateStructureItemEntity.getRateStructureLineItemByRateStructureLineItemId().getTransactionActionByTransactionActionId()) : null
                    )
                );
            } else if (rateStructureItemEntity.getRateStructureItemClassificationByRateStructureItemClassificationId().getCode().equalsIgnoreCase("VARIABLE_CHARGE")) {
                variableCharges.add(
                    new RateStructureLineItem(
                            rateStructureItemEntity.getRateStructureLineItemByRateStructureLineItemId(),
                            rateStructureItemEntity.getRateStructureLineItemByRateStructureLineItemId().getTransactionActionByTransactionActionId() != null ?
                                    new TransactionAction(rateStructureItemEntity.getRateStructureLineItemByRateStructureLineItemId().getTransactionActionByTransactionActionId()) : null
                    )
                );
            } else if (rateStructureItemEntity.getRateStructureItemClassificationByRateStructureItemClassificationId().getCode().equalsIgnoreCase("MINIMUM_BILLING")) {
                minimumBilling.add(
                        new RateStructureLineItem(
                                rateStructureItemEntity.getRateStructureLineItemByRateStructureLineItemId(),
                                rateStructureItemEntity.getRateStructureLineItemByRateStructureLineItemId().getTransactionActionByTransactionActionId() != null ?
                                        new TransactionAction(rateStructureItemEntity.getRateStructureLineItemByRateStructureLineItemId().getTransactionActionByTransactionActionId()) : null
                        )
                );
            }
        }

        getRateStructureResponse.setFixedCharges(fixedCharges);
        getRateStructureResponse.setVariableCharges(variableCharges);
        getRateStructureResponse.setMinimumBilling(minimumBilling);

        List<PricingModelEntity> pricingModelEntities = pricingModelRepository.findAllByRateStructureId(rateStructureId);
        List<PricingModel> pricingModels = new ArrayList<>();
        for (PricingModelEntity pricingModelEntity: pricingModelEntities) {
            PricingModelVersionEntity pricingModelVersionEntity = pricingModelVersionRepository.findFirstByPricingModelIdOrderByVersionDesc(pricingModelEntity.getId());
            pricingModels.add(new PricingModel(pricingModelEntity, pricingModelVersionEntity));
        }
        getRateStructureResponse.setPricingModels(pricingModels);

        return getRateStructureResponse;
    }

    @Override
    public GetRateStructureLineItemsResponse getRateStructureLineItemsByRateStructureId(long rateStructureId) {

        List<String> activeAndPending = new ArrayList<>();
        activeAndPending.add("ACTIVE");
        activeAndPending.add("PENDING_APPROVAL");
        List<RateStructureStateEntity> rateStructureStateEntities = rateStructureStateRepository.findByCodeIn(activeAndPending);
        RateStructureVersionEntity rateStructureVersionEntity = rateStructureVersionRepository.findOneByRateStructureIdAndRateStructureStateByRateStructureStateIdIn(rateStructureId, rateStructureStateEntities);

        List<RateStructureLineItem> fixedCharges = new ArrayList<>();
        List<RateStructureLineItem> variableCharges = new ArrayList<>();
        List<RateStructureLineItem> minimumBilling = new ArrayList<>();

        for (RateStructureItemEntity rateStructureItemEntity: rateStructureVersionEntity.getRateStructureItemsById()) {
            if (rateStructureItemEntity.getRateStructureItemClassificationByRateStructureItemClassificationId().getCode().equalsIgnoreCase("FIXED_CHARGE")) {
                fixedCharges.add(
                        new RateStructureLineItem(
                                rateStructureItemEntity.getRateStructureLineItemByRateStructureLineItemId(),
                                rateStructureItemEntity.getRateStructureLineItemByRateStructureLineItemId().getTransactionActionByTransactionActionId() != null ?
                                        new TransactionAction(rateStructureItemEntity.getRateStructureLineItemByRateStructureLineItemId().getTransactionActionByTransactionActionId()) : null
                        )
                );
            } else if (rateStructureItemEntity.getRateStructureItemClassificationByRateStructureItemClassificationId().getCode().equalsIgnoreCase("VARIABLE_CHARGE")) {
                variableCharges.add(
                        new RateStructureLineItem(
                                rateStructureItemEntity.getRateStructureLineItemByRateStructureLineItemId(),
                                rateStructureItemEntity.getRateStructureLineItemByRateStructureLineItemId().getTransactionActionByTransactionActionId() != null ?
                                        new TransactionAction(rateStructureItemEntity.getRateStructureLineItemByRateStructureLineItemId().getTransactionActionByTransactionActionId()) : null
                        )
                );
            } else if (rateStructureItemEntity.getRateStructureItemClassificationByRateStructureItemClassificationId().getCode().equalsIgnoreCase("MINIMUM_BILLING")) {
                minimumBilling.add(
                        new RateStructureLineItem(
                                rateStructureItemEntity.getRateStructureLineItemByRateStructureLineItemId(),
                                rateStructureItemEntity.getRateStructureLineItemByRateStructureLineItemId().getTransactionActionByTransactionActionId() != null ?
                                        new TransactionAction(rateStructureItemEntity.getRateStructureLineItemByRateStructureLineItemId().getTransactionActionByTransactionActionId()) : null
                        )
                );
            }
        }


        return new GetRateStructureLineItemsResponse(fixedCharges, variableCharges, minimumBilling);
    }

    public ResponseEntity<?> createRateStructure(CreateRateStructureRequest createRateStructureRequest) {

        if(createRateStructureRequest.getVariableCharges().size() < createRateStructureRequest.getMinimumBilling().size()) {
            throw new GenericException("Minimum Billing contains more items than Variable Charges", HttpStatus.PRECONDITION_FAILED, "Minimum Billing Exceeds Variable Charges ");
        }

        for (RateStructureLineItem minimumBillingCharge : createRateStructureRequest.getMinimumBilling()) {
            boolean flag = false;
            for (RateStructureLineItem variableCharge : createRateStructureRequest.getVariableCharges()) {
                if(minimumBillingCharge.getCode().equalsIgnoreCase(variableCharge.getCode())) {
                    flag = true;
                }
            }
            if(!flag) {
                throw new GenericException("Minimum Billing line items do not match Variable Charges", HttpStatus.PRECONDITION_FAILED, "Minimum Billing - Variable Charges mismatch");
            }
        }

        RateStructureEntity savedRateStructureEntity = null;
        RateStructureVersionEntity savedRateStructureVersionEntity = null;
        try {
            RateStructureEntity newRateStructureEntity = new RateStructureEntity();
            newRateStructureEntity.setName(createRateStructureRequest.getName());
            newRateStructureEntity.setDescription(createRateStructureRequest.getDescription());
            newRateStructureEntity.setCode(UUID.randomUUID().toString());
            newRateStructureEntity.setLastModified(new Timestamp(new Date().getTime()));
            savedRateStructureEntity = rateStructureRepository.save(newRateStructureEntity);
            rateStructureRepository.flush();

            RateStructureVersionEntity newRateStructureVersionEntity = new RateStructureVersionEntity();
            newRateStructureVersionEntity.setVersion(Long.valueOf(1));
            newRateStructureVersionEntity.setLastModified(new Timestamp(new Date().getTime()));
            BackOfficeUserEntity backOfficeUser = backOfficeUserRepository.findOneById(createRateStructureRequest.getLastModifiedBy());
            newRateStructureVersionEntity.setBackOfficeUserByLastModifiedBy(backOfficeUser);
            newRateStructureVersionEntity.setRateStructureId(savedRateStructureEntity.getId());
            RateStructureStateEntity rateStructureStateEntity = rateStructureStateRepository.findByCode("PENDING_APPROVAL");
            newRateStructureVersionEntity.setRateStructureStateByRateStructureStateId(rateStructureStateEntity);
            savedRateStructureVersionEntity = rateStructureVersionRepository.save(newRateStructureVersionEntity);
            rateStructureVersionRepository.flush();

            for (RateStructureLineItem fixedChargeObject : createRateStructureRequest.getFixedCharges()) {
                RateStructureItemClassificationEntity rateStructureItemClassificationEntity = rateStructureItemClassificationRepository.findByCode("FIXED_CHARGE");
                RateStructureLineItemEntity rateStructureLineItemEntity = rateStructureLineItemRepository.findByCode(fixedChargeObject.getCode());
                RateStructureItemEntity newRateStructureItemEntity = new RateStructureItemEntity();
                newRateStructureItemEntity.setRateStructureLineItemByRateStructureLineItemId(rateStructureLineItemEntity);
                newRateStructureItemEntity.setRateStructureItemClassificationByRateStructureItemClassificationId(rateStructureItemClassificationEntity);
                newRateStructureItemEntity.setRateStructureVersionId(savedRateStructureVersionEntity.getId());
                rateStructureItemRepository.save(newRateStructureItemEntity);
                rateStructureItemRepository.flush();
            }

            for (RateStructureLineItem variableChargeObject : createRateStructureRequest.getVariableCharges()) {
                RateStructureItemClassificationEntity rateStructureItemClassificationEntity = rateStructureItemClassificationRepository.findByCode("VARIABLE_CHARGE");
                RateStructureLineItemEntity rateStructureLineItemEntity = rateStructureLineItemRepository.findByCode(variableChargeObject.getCode());
                RateStructureItemEntity newRateStructureItemEntity = new RateStructureItemEntity();
                newRateStructureItemEntity.setRateStructureLineItemByRateStructureLineItemId(rateStructureLineItemEntity);
                newRateStructureItemEntity.setRateStructureItemClassificationByRateStructureItemClassificationId(rateStructureItemClassificationEntity);
                newRateStructureItemEntity.setRateStructureVersionId(savedRateStructureVersionEntity.getId());
                rateStructureItemRepository.save(newRateStructureItemEntity);
                rateStructureItemRepository.flush();
            }

            for (RateStructureLineItem minimumBillingObject : createRateStructureRequest.getMinimumBilling()) {
                RateStructureItemClassificationEntity rateStructureItemClassificationEntity = rateStructureItemClassificationRepository.findByCode("MINIMUM_BILLING");
                RateStructureLineItemEntity rateStructureLineItemEntity = rateStructureLineItemRepository.findByCode(minimumBillingObject.getCode());
                RateStructureItemEntity newRateStructureItemEntity = new RateStructureItemEntity();
                newRateStructureItemEntity.setRateStructureLineItemByRateStructureLineItemId(rateStructureLineItemEntity);
                newRateStructureItemEntity.setRateStructureItemClassificationByRateStructureItemClassificationId(rateStructureItemClassificationEntity);
                newRateStructureItemEntity.setRateStructureVersionId(savedRateStructureVersionEntity.getId());
                rateStructureItemRepository.save(newRateStructureItemEntity);
                rateStructureItemRepository.flush();
            }
        } catch(Exception e) {
            throw new GenericException("Error saving Rate Structure Entity", HttpStatus.INTERNAL_SERVER_ERROR, "Error saving Rate Structure Entity: " + e.getMessage());
        }

        //send email notifications to those with permission to approve RATE_STRUCTURES_APPROVE
        BackOfficePermissionEntity backOfficeApproveRateStructurePermissionEntity = backOfficePermissionRepository.findByCode("RATE_STRUCTURES_APPROVE");
        List<BackOfficeGroupPermissionEntity> backOfficeGroupPermissionEntityList = backOfficeGroupPermissionRepository.findAllByBackOfficePermissionByBackOfficePermissionId(backOfficeApproveRateStructurePermissionEntity);
        for (BackOfficeGroupPermissionEntity groupPermissionEntity : backOfficeGroupPermissionEntityList) {
            List<BackOfficeUserEntity> backOfficeUserEntities = backOfficeUserRepository.findAllByBackOfficeGroupId(groupPermissionEntity.getBackOfficeGroupByBackOfficeGroupId().getId());
            for (BackOfficeUserEntity backOfficeUser : backOfficeUserEntities) {
                EmailNotification emailNotification = new EmailNotification(backOfficeUser.getEmail(), "New Rate Structure", "A new Rate Structure has been created: " + savedRateStructureEntity.getName() + "- Version: " + savedRateStructureVersionEntity.getVersion() + " and is awaiting approval");
                sendEmail.send(emailNotification);
            }
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }

    public ResponseEntity<?> editRateStructure(EditRateStructureRequest editRateStructureRequest) {

        if(editRateStructureRequest.getVariableCharges().size() < editRateStructureRequest.getMinimumBilling().size()) {
            throw new GenericException("Minimum Billing contains more items than Variable Charges", HttpStatus.PRECONDITION_FAILED, "Minimum Billing Exceeds Variable Charges ");
        }

        for (RateStructureLineItem minimumBillingCharge : editRateStructureRequest.getMinimumBilling()) {
            boolean flag = false;
            for (RateStructureLineItem variableCharge : editRateStructureRequest.getVariableCharges()) {
                if(minimumBillingCharge.getCode().equalsIgnoreCase(variableCharge.getCode())) {
                    flag = true;
                }
            }
            if(!flag) {
                throw new GenericException("Minimum Billing line items do not match Variable Charges", HttpStatus.PRECONDITION_FAILED, "Minimum Billing - Variable Charges mismatch");
            }
        }

        //first handle copying and saving
        RateStructureEntity rateStructureEntity = rateStructureRepository.findById(editRateStructureRequest.getId()).orElseThrow(() -> new GenericException("Rate Structure Not Found", HttpStatus.NOT_FOUND, "No rate structure found for id | " + editRateStructureRequest.getId()));
        List<String> activeAndPending = new ArrayList<>();
        activeAndPending.add("ACTIVE");
        activeAndPending.add("PENDING_APPROVAL");
        List<RateStructureStateEntity> rateStructureStateEntities = rateStructureStateRepository.findByCodeIn(activeAndPending);
        RateStructureVersionEntity rateStructureVersionEntity = rateStructureVersionRepository.findOneByRateStructureIdAndRateStructureStateByRateStructureStateIdIn(rateStructureEntity.getId(), rateStructureStateEntities);
        try {
//            RateStructureEntity rateStructureEntity = rateStructureRepository.findById(editRateStructureRequest.getId()).orElseThrow(() -> new GenericException("Rate Structure Not Found", HttpStatus.INTERNAL_SERVER_ERROR, "No rate structure found for id | " + editRateStructureRequest.getId()));
//            List<String> activeAndPending = new ArrayList<>();
//            activeAndPending.add("ACTIVE");
//            activeAndPending.add("PENDING_APPROVAL");
//            List<RateStructureStateEntity> rateStructureStateEntities = rateStructureStateRepository.findByCodeIn(activeAndPending);
//            RateStructureVersionEntity rateStructureVersionEntity = rateStructureVersionRepository.findOneByRateStructureIdAndRateStructureStateByRateStructureStateIdIn(rateStructureEntity.getId(), rateStructureStateEntities);
            RateStructureVersionEntity newRateStructureVersionEntity = new RateStructureVersionEntity();
            newRateStructureVersionEntity.setRateStructureId(rateStructureEntity.getId());
            newRateStructureVersionEntity.setVersion(rateStructureVersionEntity.getVersion());
            RateStructureStateEntity rateStructureStateEntityHistorical = rateStructureStateRepository.findByCode("HISTORICAL");
            newRateStructureVersionEntity.setRateStructureStateByRateStructureStateId(rateStructureStateEntityHistorical);
            newRateStructureVersionEntity.setBackOfficeUserByLastModifiedBy(backOfficeUserRepository.findOneById(editRateStructureRequest.getLastModifiedBy()));
            newRateStructureVersionEntity.setLastModified(new Timestamp(new Date().getTime()));

            RateStructureVersionEntity savedRateStructureVersionEntity = rateStructureVersionRepository.save(newRateStructureVersionEntity);
            rateStructureVersionRepository.flush();
            for (RateStructureItemEntity rateStructureItemEntity : rateStructureVersionEntity.getRateStructureItemsById()) {
                RateStructureItemEntity newRateStructureItemEntity = new RateStructureItemEntity();
                newRateStructureItemEntity.setRateStructureVersionId(savedRateStructureVersionEntity.getId());
                newRateStructureItemEntity.setRateStructureLineItemByRateStructureLineItemId(rateStructureItemEntity.getRateStructureLineItemByRateStructureLineItemId());
                newRateStructureItemEntity.setRateStructureItemClassificationByRateStructureItemClassificationId(rateStructureItemEntity.getRateStructureItemClassificationByRateStructureItemClassificationId());
                rateStructureItemRepository.save(newRateStructureItemEntity);
                rateStructureItemRepository.flush();
            }

        } catch(Exception e) {
            throw new GenericException("Error saving Rate Structure Entity Copy", HttpStatus.INTERNAL_SERVER_ERROR, "Error saving Rate Structure Entity: " + e.getMessage());
        }

        //second handle editing of rate structure and saving
        RateStructureVersionEntity editedRateStructureVersionEntity = null;
        try {
            rateStructureVersionEntity.setRateStructureStateByRateStructureStateId(rateStructureStateRepository.findByCode("PENDING_APPROVAL"));
            rateStructureVersionEntity.setVersion(rateStructureVersionEntity.getVersion() + 1);
//            BillingFrequencyEntity billingFrequency = billingFrequencyRepository.findByCode(editRateStructureRequest.getBillingFrequency().getCode());
//            rateStructureVersionEntity.setBillingFrequencyByBillingFrequencyId(billingFrequency);
            rateStructureVersionEntity.setBackOfficeUserByLastModifiedBy(backOfficeUserRepository.findOneById(editRateStructureRequest.getLastModifiedBy()));
            rateStructureVersionEntity.setLastModified(new Timestamp(new Date().getTime()));
            editedRateStructureVersionEntity = rateStructureVersionRepository.save(rateStructureVersionEntity);
            rateStructureVersionRepository.flush();

            for (RateStructureItemEntity rateStructureItemEntity : editedRateStructureVersionEntity.getRateStructureItemsById()) {
                rateStructureItemRepository.delete(rateStructureItemEntity);
                rateStructureItemRepository.flush();
            }

            for (RateStructureLineItem fixedChargeObject : editRateStructureRequest.getFixedCharges()) {
                RateStructureItemClassificationEntity rateStructureItemClassificationEntity = rateStructureItemClassificationRepository.findByCode("FIXED_CHARGE");
                RateStructureLineItemEntity rateStructureLineItemEntity = rateStructureLineItemRepository.findByCode(fixedChargeObject.getCode());
                RateStructureItemEntity newRateStructureItemEntity = new RateStructureItemEntity();
                newRateStructureItemEntity.setRateStructureLineItemByRateStructureLineItemId(rateStructureLineItemEntity);
                newRateStructureItemEntity.setRateStructureItemClassificationByRateStructureItemClassificationId(rateStructureItemClassificationEntity);
                newRateStructureItemEntity.setRateStructureVersionId(editedRateStructureVersionEntity.getId());
                rateStructureItemRepository.save(newRateStructureItemEntity);
                rateStructureItemRepository.flush();
            }

            for (RateStructureLineItem variableChargeObject : editRateStructureRequest.getVariableCharges()) {
                RateStructureItemClassificationEntity rateStructureItemClassificationEntity = rateStructureItemClassificationRepository.findByCode("VARIABLE_CHARGE");
                RateStructureLineItemEntity rateStructureLineItemEntity = rateStructureLineItemRepository.findByCode(variableChargeObject.getCode());
                RateStructureItemEntity newRateStructureItemEntity = new RateStructureItemEntity();
                newRateStructureItemEntity.setRateStructureLineItemByRateStructureLineItemId(rateStructureLineItemEntity);
                newRateStructureItemEntity.setRateStructureItemClassificationByRateStructureItemClassificationId(rateStructureItemClassificationEntity);
                newRateStructureItemEntity.setRateStructureVersionId(editedRateStructureVersionEntity.getId());
                rateStructureItemRepository.save(newRateStructureItemEntity);
                rateStructureItemRepository.flush();
            }

            for (RateStructureLineItem minimumBillingObject : editRateStructureRequest.getMinimumBilling()) {
                RateStructureItemClassificationEntity rateStructureItemClassificationEntity = rateStructureItemClassificationRepository.findByCode("MINIMUM_BILLING");
                RateStructureLineItemEntity rateStructureLineItemEntity = rateStructureLineItemRepository.findByCode(minimumBillingObject.getCode());
                RateStructureItemEntity newRateStructureItemEntity = new RateStructureItemEntity();
                newRateStructureItemEntity.setRateStructureLineItemByRateStructureLineItemId(rateStructureLineItemEntity);
                newRateStructureItemEntity.setRateStructureItemClassificationByRateStructureItemClassificationId(rateStructureItemClassificationEntity);
                newRateStructureItemEntity.setRateStructureVersionId(editedRateStructureVersionEntity.getId());
                rateStructureItemRepository.save(newRateStructureItemEntity);
                rateStructureItemRepository.flush();
            }


        } catch(Exception e) {
            throw new GenericException("Error saving Edited Rate Structure Entity", HttpStatus.INTERNAL_SERVER_ERROR, "Error saving Rate Structure Entity: " + e.getMessage());
        }

        //send email notifications to those with permission to approve RATE_STRUCTURES_APPROVE
        BackOfficePermissionEntity backOfficeApproveRateStructurePermissionEntity = backOfficePermissionRepository.findByCode("RATE_STRUCTURES_APPROVE");
        List<BackOfficeGroupPermissionEntity> backOfficeGroupPermissionEntityList = backOfficeGroupPermissionRepository.findAllByBackOfficePermissionByBackOfficePermissionId(backOfficeApproveRateStructurePermissionEntity);
        for (BackOfficeGroupPermissionEntity groupPermissionEntity : backOfficeGroupPermissionEntityList) {
            List<BackOfficeUserEntity> backOfficeUserEntities = backOfficeUserRepository.findAllByBackOfficeGroupId(groupPermissionEntity.getBackOfficeGroupByBackOfficeGroupId().getId());
            for (BackOfficeUserEntity backOfficeUser : backOfficeUserEntities) {
                EmailNotification emailNotification = new EmailNotification(backOfficeUser.getEmail(), "Edited Rate Structure", "A Rate Structure has been edited: " + rateStructureEntity.getName() + "- Version: " + editedRateStructureVersionEntity.getVersion() + " and is awaiting approval");
                sendEmail.send(emailNotification);
            }
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }

    public ResponseEntity<?> approveRateStructure(Long rateStructureId, Long modifiedById) {

        RateStructureEntity rateStructureEntity = rateStructureRepository.findById(rateStructureId).orElseThrow(() -> new GenericException("Rate Structure Not Found", HttpStatus.INTERNAL_SERVER_ERROR, "No rate structure found for id | " + rateStructureId));
        RateStructureStateEntity rateStructureStateEntityPending = rateStructureStateRepository.findByCode("PENDING_APPROVAL");
        RateStructureVersionEntity rateStructureVersionEntity = rateStructureVersionRepository.findByRateStructureIdAndRateStructureStateByRateStructureStateId(rateStructureEntity.getId(), rateStructureStateEntityPending);
        if(rateStructureVersionEntity == null) {
            throw new GenericException("Rate Structure not pending approval", HttpStatus.NOT_FOUND, "Rate Structure not pending approval");
        }
        RateStructureStateEntity rateStructureStateEntityActive = rateStructureStateRepository.findByCode("ACTIVE");
        rateStructureVersionEntity.setRateStructureStateByRateStructureStateId(rateStructureStateEntityActive);
        rateStructureVersionEntity.setLastModified(new Timestamp(new Date().getTime()));
        rateStructureVersionEntity.setBackOfficeUserByLastModifiedBy(backOfficeUserRepository.findOneById(modifiedById));
        rateStructureVersionRepository.save(rateStructureVersionEntity);
        rateStructureVersionRepository.flush();
        return new ResponseEntity<>(HttpStatus.OK);
    }

    public ResponseEntity<?> createPricingModel(Long rateStructureId, CreatePricingModelRequest createPricingModelRequest) {

        try {
            PricingModelEntity pricingModelEntity = new PricingModelEntity();
            pricingModelEntity.setName(createPricingModelRequest.getName());
            pricingModelEntity.setDescription(createPricingModelRequest.getDescription());
            pricingModelEntity.setCode(UUID.randomUUID().toString());
            pricingModelEntity.setGlobalPricingModel(createPricingModelRequest.isGlobalPricingModel());
            pricingModelEntity.setCurrencyByCurrencyId(currencyRepository.findByCode(createPricingModelRequest.getCurrency().getCode()));
            pricingModelEntity.setRateStructureId(rateStructureId);
            pricingModelEntity.setLastModified(new Timestamp(new Date().getTime()));
            PricingModelEntity savedPricingModelEntity = pricingModelRepository.save(pricingModelEntity);
            pricingModelRepository.flush();

            PricingModelVersionEntity pricingModelVersionEntity = new PricingModelVersionEntity();
            pricingModelVersionEntity.setPricingModelId(savedPricingModelEntity.getId());
            pricingModelVersionEntity.setVersion(Long.valueOf(1));
            pricingModelVersionEntity.setLastModified(new Timestamp(new Date().getTime()));
            pricingModelVersionEntity.setBackOfficeUserByLastModifiedBy(backOfficeUserRepository.findOneById(createPricingModelRequest.getLastModifiedBy()));
            PricingModelVersionEntity savedPricingModelVersionEntity = pricingModelVersionRepository.save(pricingModelVersionEntity);
            pricingModelVersionRepository.flush();

            for (FixedCharge fixedCharge : createPricingModelRequest.getFixedChargeList()) {
//                if(fixedCharge.getValue().compareTo(new BigDecimal(0)) == 1) {
                if(fixedCharge.getValue() != null && fixedCharge.getValue().compareTo(new BigDecimal(0)) >= 0) {
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

            }

            for (VariableCharge variableCharge : createPricingModelRequest.getVariableChargeList()) {
                if(variableCharge.getValue() != null && variableCharge.getValue().compareTo(new BigDecimal(0)) >= 0
                    && variableCharge.getFromAmount() != null && variableCharge.getFromAmount() >= 0
                    && variableCharge.getToAmount() != null && variableCharge.getToAmount() >= 0) {
                    VariableChargeEntity variableChargeEntity = new VariableChargeEntity();
                    variableChargeEntity.setPricingModelVersionId(savedPricingModelVersionEntity.getId());
                    variableChargeEntity.setVatable(variableCharge.isVatable());
                    variableChargeEntity.setVatInclusive(variableCharge.isVatInclusive());
                    variableChargeEntity.setFromAmount(variableCharge.getFromAmount() != null ? variableCharge.getFromAmount() : 0);
                    variableChargeEntity.setToAmount(variableCharge.getToAmount() != null ? variableCharge.getToAmount() : 0);
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

            }

            if(createPricingModelRequest.getMinimumBilling() != null) {
                if(createPricingModelRequest.getMinimumBilling().getValue() != null && createPricingModelRequest.getMinimumBilling().getValue().compareTo(new BigDecimal(0)) >= 0) {
                    MinimumBillingEntity minimumBillingEntity = new MinimumBillingEntity();
                    minimumBillingEntity.setValue(createPricingModelRequest.getMinimumBilling().getValue());
                    minimumBillingEntity.setVatable(createPricingModelRequest.getMinimumBilling().isVatable());
                    minimumBillingEntity.setVatInclusive(createPricingModelRequest.getMinimumBilling().isVatInclusive());
                    minimumBillingEntity.setPricingModelVersionId(savedPricingModelVersionEntity.getId());
                    MinimumBillingEntity savedMinimumBillingEntity = minimumBillingRepository.save(minimumBillingEntity);
                    minimumBillingRepository.flush();

                    for (RateStructureLineItem rateStructureLineItem : createPricingModelRequest.getMinimumBilling().getRateStructureLineItem()) {
                        MinimumBillingRateStructureLineItemEntity minimumBillingRateStructureLineItemEntity = new MinimumBillingRateStructureLineItemEntity();
                        minimumBillingRateStructureLineItemEntity.setMinimumBillingId(savedMinimumBillingEntity.getId());
                        RateStructureLineItemEntity rateStructureLineItemEntity = rateStructureLineItemRepository.findByCode(rateStructureLineItem.getCode());
                        minimumBillingRateStructureLineItemEntity.setRateStructureLineItemByRateStructureLineItemId(rateStructureLineItemEntity);
                        MinimumBillingRateStructureLineItemEntity savedMinimumBillingRateStructureLineItemEntity = minimumBillingRateStructureLineItemRepository.save(minimumBillingRateStructureLineItemEntity);
                        minimumBillingRateStructureLineItemRepository.flush();
                    }
                }

            }

        } catch(Exception e) {
            throw new GenericException("Error saving Pricing Model", HttpStatus.INTERNAL_SERVER_ERROR, "Error saving Pricing Model: " + e.getMessage());
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    public ResponseEntity<?> editGlobalPricingModel(Long pricingModelId, EditPricingModelRequest editPricingModelRequest) {

        PricingModelEntity pricingModelEntity = pricingModelRepository.findById(pricingModelId).orElseThrow(() -> new GenericException("Pricing Model Not Found", HttpStatus.INTERNAL_SERVER_ERROR, "No Pricing Model found for id | " + pricingModelId));
        pricingModelEntity.setLastModified(new Timestamp(new Date().getTime()));
        PricingModelEntity savedPricingModel = pricingModelRepository.save(pricingModelEntity);
        pricingModelRepository.flush();

        PricingModelVersionEntity pricingModelVersionEntity = pricingModelVersionRepository.findFirstByPricingModelIdOrderByVersionDesc(savedPricingModel.getId());
        PricingModelVersionEntity newPricingModelVersionEntity = new PricingModelVersionEntity();
        newPricingModelVersionEntity.setVersion(pricingModelVersionEntity.getVersion() + 1);
        newPricingModelVersionEntity.setBackOfficeUserByLastModifiedBy(backOfficeUserRepository.findOneById(editPricingModelRequest.getLastModifiedBy()));
        newPricingModelVersionEntity.setLastModified(new Timestamp(new Date().getTime()));
        newPricingModelVersionEntity.setPricingModelId(savedPricingModel.getId());
        PricingModelVersionEntity savedPricingModelVersionEntity = pricingModelVersionRepository.save(newPricingModelVersionEntity);
        pricingModelVersionRepository.flush();

        for (FixedCharge fixedCharge : editPricingModelRequest.getFixedChargeList()) {
            if(fixedCharge.getValue() != null && fixedCharge.getValue().compareTo(new BigDecimal(0)) >= 0) {
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
        }

        for (VariableCharge variableCharge : editPricingModelRequest.getVariableChargeList()) {
            if(variableCharge.getValue() != null && variableCharge.getValue().compareTo(new BigDecimal(0)) >= 0
                    && variableCharge.getFromAmount() != null  && variableCharge.getFromAmount() >= 0
                    && variableCharge.getToAmount() != null  && variableCharge.getToAmount() >= 0) {
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
        }

        if(editPricingModelRequest.getMinimumBilling() != null) {
            if(editPricingModelRequest.getMinimumBilling().getValue() != null && editPricingModelRequest.getMinimumBilling().getValue().compareTo(new BigDecimal(0)) >= 0) {
                MinimumBillingEntity minimumBillingEntity = new MinimumBillingEntity();
                minimumBillingEntity.setValue(editPricingModelRequest.getMinimumBilling().getValue());
                minimumBillingEntity.setVatable(editPricingModelRequest.getMinimumBilling().isVatable());
                minimumBillingEntity.setVatInclusive(editPricingModelRequest.getMinimumBilling().isVatInclusive());
                minimumBillingEntity.setPricingModelVersionId(savedPricingModelVersionEntity.getId());
                MinimumBillingEntity savedMinimumBillingEntity = minimumBillingRepository.save(minimumBillingEntity);
                minimumBillingRepository.flush();

                for (RateStructureLineItem rateStructureLineItem : editPricingModelRequest.getMinimumBilling().getRateStructureLineItem()) {
                    MinimumBillingRateStructureLineItemEntity minimumBillingRateStructureLineItemEntity = new MinimumBillingRateStructureLineItemEntity();
                    minimumBillingRateStructureLineItemEntity.setMinimumBillingId(savedMinimumBillingEntity.getId());
                    RateStructureLineItemEntity rateStructureLineItemEntity = rateStructureLineItemRepository.findByCode(rateStructureLineItem.getCode());
                    minimumBillingRateStructureLineItemEntity.setRateStructureLineItemByRateStructureLineItemId(rateStructureLineItemEntity);
                    MinimumBillingRateStructureLineItemEntity savedMinimumBillingRateStructureLineItemEntity = minimumBillingRateStructureLineItemRepository.save(minimumBillingRateStructureLineItemEntity);
                    minimumBillingRateStructureLineItemRepository.flush();
                }
            }
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }

    public PricingModel getPricingModelById(Long pricingModelId) {
        PricingModelEntity pricingModelEntity = pricingModelRepository.findById(pricingModelId).orElseThrow(() -> new GenericException("Pricing Model Not Found", HttpStatus.NOT_FOUND, "No pricing model found for id | " + pricingModelId));
        PricingModelVersionEntity pricingModelVersionEntity = pricingModelVersionRepository.findFirstByPricingModelIdOrderByVersionDesc(pricingModelEntity.getId());
        return new PricingModel(pricingModelEntity, pricingModelVersionEntity);
    }

    public RateStructureDropdownResponse rateStructureDropdown() {

        RateStructureDropdownResponse rateStructureDropdownResponse = new RateStructureDropdownResponse();

        ArrayList<String> activeState = new ArrayList<>();
        activeState.add("ACTIVE");
        Specification<RateStructureEntity> specification = Specification.where(
                rateStructureStates(activeState)
        );
        List<RateStructureEntity> rateStructureEntities = rateStructureRepository.findAll(specification);

        ArrayList<RateStructure> responseArray = new ArrayList<>();
        for (RateStructureEntity rateStructureEntity : rateStructureEntities) {
            responseArray.add(new RateStructure(rateStructureEntity));
        }
        rateStructureDropdownResponse.setRateStructures(responseArray);

        return rateStructureDropdownResponse;
    }

    public PricingModelDropdownResponse pricingModelDropdown(Long rateStructureId) {

        PricingModelDropdownResponse pricingModelDropdownResponse = new PricingModelDropdownResponse();

        List<PricingModelEntity> pricingModelEntities = pricingModelRepository.findAllByRateStructureIdAndGlobalPricingModel(rateStructureId, true);

        ArrayList<PricingModel> responseArray = new ArrayList<>();

        for (PricingModelEntity pricingModelEntity : pricingModelEntities) {
            responseArray.add(new PricingModel(pricingModelEntity));
        }
        pricingModelDropdownResponse.setPricingModels(responseArray);

        return pricingModelDropdownResponse;
    }

    public ArrayList<ProductDefaultResponse> getAllCurrentProductDefaults() {
        ArrayList<ProductDefaultResponse> productDefaultResponseArrayList = new ArrayList<>();

        List<ProductEntity> productEntities = productRepository.findAll();
        for (ProductEntity productEntity : productEntities) {
            ProductDefaultResponse productDefaultResponse = new ProductDefaultResponse();
            productDefaultResponse.setProductId(productEntity.getId());

            if (productEntity.getPricingModelId() != null) {
                Optional<PricingModelEntity> pricingModelEntity = pricingModelRepository.findById(productEntity.getPricingModelId());
                if(pricingModelEntity.isPresent()) {
                    productDefaultResponse.setPricingModelId(pricingModelEntity.get().getId());
                    Optional<RateStructureEntity> rateStructureEntity = rateStructureRepository.findById(pricingModelEntity.get().getRateStructureId());
                    if(rateStructureEntity.isPresent()) {
                        productDefaultResponse.setRateStructureId(rateStructureEntity.get().getId());
                    }
                }
            }

            productDefaultResponseArrayList.add(productDefaultResponse);
        }

        return productDefaultResponseArrayList;
    }

    public ResponseEntity<?> setAllCurrentProductDefaults(ProductDefaultSetRequest productDefaultSetRequest) {

        for (ProductDefaultRequest productDefaultRequest : productDefaultSetRequest.getProductDefaultRequestArrayList()) {
            Optional<ProductEntity> productEntity = productRepository.findById(productDefaultRequest.getProductId());
            if(productEntity.isPresent()) {
                Optional<PricingModelEntity> pricingModelEntity = pricingModelRepository.findById(productDefaultRequest.getPricingModelId());
                if(pricingModelEntity.isPresent()) {
                    Optional<RateStructureEntity> rateStructureEntity = rateStructureRepository.findById(productDefaultRequest.getRateStructureId());
                    if(rateStructureEntity.isPresent()) {
                        if(pricingModelEntity.get().getRateStructureId() != rateStructureEntity.get().getId()) {
                            throw new GenericException("Error saving Default Pricing Model and Rate Structure", HttpStatus.INTERNAL_SERVER_ERROR, "Error saving Default Pricing Model and Rate Structure for product: " + productDefaultRequest.getProductId());
                        } else {
                            productEntity.get().setPricingModelId(productDefaultRequest.getPricingModelId());
                        }
                    }
                }
                productRepository.save(productEntity.get());
            }
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }

    public ArrayList<MerchantProductDetail> getMerchantProductDetails(Long merchantId) {
        ArrayList<MerchantProductDetail> merchantProductDetails = new ArrayList<>();

        List<MerchantProductEntity> merchantProductEntities = merchantProductRepository.findByMerchantId(merchantId);
        for (MerchantProductEntity merchantProductEntity : merchantProductEntities) {
            MerchantProductDetail merchantProductDetail = new MerchantProductDetail();

            //set product id
            merchantProductDetail.setProductId(merchantProductEntity.getProductByProductId().getId());

            //set selected or default rate structure id
            if(merchantProductEntity.getCustomRateStructure() != null) {
                merchantProductDetail.setSelectedRateStructureId(merchantProductEntity.getCustomRateStructure());
            } else {
                ProductEntity productEntity = merchantProductEntity.getProductByProductId();
                if (productEntity.getPricingModelId() != null) {
                    PricingModelEntity pricingModelEntity = pricingModelRepository.findById(productEntity.getPricingModelId()).orElseThrow(() -> new GenericException("Pricing Model Not Found", HttpStatus.NOT_FOUND, "No pricing model found for id | " + productEntity.getId()));
                    RateStructureEntity rateStructureEntity = rateStructureRepository.findById(pricingModelEntity.getRateStructureId()).orElseThrow(() -> new GenericException("Rate Structure Not Found", HttpStatus.NOT_FOUND, "No Rate Structure found for id | " + pricingModelEntity.getRateStructureId()));
                    merchantProductDetail.setSelectedRateStructureId(rateStructureEntity.getId());
                }
            }

            //set selected or default pricing model id
            if(merchantProductEntity.getSelectedPricingModelId() != null) {
                merchantProductDetail.setSelectedPricingModelId(merchantProductEntity.getSelectedPricingModelId());
            } else {
                ProductEntity productEntity = merchantProductEntity.getProductByProductId();
                if (productEntity.getPricingModelId() != null) {
                    PricingModelEntity pricingModelEntity = pricingModelRepository.findById(productEntity.getPricingModelId()).orElseThrow(() -> new GenericException("Pricing Model Not Found", HttpStatus.NOT_FOUND, "No pricing model found for id | " + productEntity.getId()));
                    merchantProductDetail.setSelectedPricingModelId(pricingModelEntity.getId());
                }
            }

            //set custom pricing model id
            merchantProductDetail.setCustomPricingModelId(merchantProductEntity.getCustomPricingModel());

            merchantProductDetails.add(merchantProductDetail);
        }

        return merchantProductDetails;
    }

    public ResponseEntity<?> setMerchantProductDetails(LinkMerchantProductToPricingModelRequest linkMerchantProductToPricingModelRequest) {
        //just merchant exists check
        MerchantEntity merchantEntity = merchantRepository.findById(linkMerchantProductToPricingModelRequest.getMerchantId()).orElseThrow(() -> new GenericException("Merchant Not Found", HttpStatus.NOT_FOUND, "No merchant found for id | " + linkMerchantProductToPricingModelRequest.getMerchantId()));

        //loop through merchant product entities
        for (LinkedProduct linkedProduct : linkMerchantProductToPricingModelRequest.getProducts()) {
            ProductEntity productEntity = productRepository.findById(linkedProduct.getProductId()).orElseThrow(() -> new GenericException("Product Not Found", HttpStatus.NOT_FOUND, "No product found for id | " + linkedProduct.getProductId()));
            MerchantProductEntity merchantProductEntity = merchantProductRepository.findByMerchantIdAndProductByProductId(linkMerchantProductToPricingModelRequest.getMerchantId(), productEntity);
            //set selected pricing model to null, allow logic to fill it or not later
            //same with custom rate structure
            merchantProductEntity.setSelectedPricingModelId(null);
            merchantProductEntity.setCustomRateStructure(null);

            //check if pricingModelId is default, if not then set the merchant product selected_pricing_model
            if(!(productEntity.getPricingModelId() == linkedProduct.getPricingModelId())) {
                merchantProductEntity.setSelectedPricingModelId(linkedProduct.getPricingModelId());

                //check if the rate structure is the default, if not then save custom_rate_structure (could be default rate structure just not default pricing model)
                PricingModelEntity defaultPricingModelEntity = pricingModelRepository.findById(productEntity.getPricingModelId()).orElseThrow(() -> new GenericException("Pricing Model Not Found", HttpStatus.NOT_FOUND, "Error finding pricing model with ID | " + productEntity.getPricingModelId()));
                RateStructureEntity defaultRateStructure = rateStructureRepository.findById(defaultPricingModelEntity.getRateStructureId()).orElseThrow(() -> new GenericException("Rate Structure Not Found", HttpStatus.NOT_FOUND, "Error finding rate structure with ID | " + defaultPricingModelEntity.getRateStructureId()));
                PricingModelEntity selectedPricingModelEntity = pricingModelRepository.findById(linkedProduct.getPricingModelId()).orElseThrow(() -> new GenericException("Pricing Model Not Found", HttpStatus.NOT_FOUND, "Error finding pricing model with ID | " + linkedProduct.getPricingModelId()));
                if(!(selectedPricingModelEntity.getRateStructureId() == defaultRateStructure.getId())) {
                    merchantProductEntity.setCustomRateStructure(selectedPricingModelEntity.getRateStructureId());
                }
            }

            //check if there is a custom pricing model already, if customLineItems is null then make value null in merchant product entity. un-link the custom pricing model essentially
            //this is only the case when setting the merchant product back to a default pricing model, if there is still a custom then a new custom is saved below
            if(merchantProductEntity.getCustomPricingModel() != null && linkedProduct.getCustomLineItems() == null) {
                merchantProductEntity.setCustomPricingModel(null);
            }

            //check if customLineItems is not null, if not then save the new custom pricing model and set in merchant product entity.
            if(linkedProduct.getCustomLineItems() != null) {
                //if there is a custom pricing model already existing, handle differently to if there is not.
                if(merchantProductEntity.getCustomPricingModel() != null) {
                    //logic to add a new version to custom pricing model
                    PricingModelEntity pricingModelEntity = pricingModelRepository.findById(merchantProductEntity.getCustomPricingModel()).orElseThrow(() -> new GenericException("Pricing Model Not Found", HttpStatus.INTERNAL_SERVER_ERROR, "No Pricing Model found for id | " + merchantProductEntity.getCustomPricingModel()));
                    pricingModelEntity.setLastModified(new Timestamp(new Date().getTime()));
                    PricingModelEntity savedPricingModel = pricingModelRepository.save(pricingModelEntity);
                    pricingModelRepository.flush();

                    PricingModelVersionEntity pricingModelVersionEntity = pricingModelVersionRepository.findFirstByPricingModelIdOrderByVersionDesc(savedPricingModel.getId());
                    PricingModelVersionEntity newPricingModelVersionEntity = new PricingModelVersionEntity();
                    newPricingModelVersionEntity.setVersion(pricingModelVersionEntity.getVersion() + 1);
                    newPricingModelVersionEntity.setBackOfficeUserByLastModifiedBy(backOfficeUserRepository.findOneById(linkMerchantProductToPricingModelRequest.getBackOfficeUserId()));
                    newPricingModelVersionEntity.setLastModified(new Timestamp(new Date().getTime()));
                    newPricingModelVersionEntity.setPricingModelId(savedPricingModel.getId());
                    PricingModelVersionEntity savedPricingModelVersionEntity = pricingModelVersionRepository.save(newPricingModelVersionEntity);
                    pricingModelVersionRepository.flush();

                    for (FixedCharge fixedCharge : linkedProduct.getCustomLineItems().getFixedChargeList()) {
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

                    for (VariableCharge variableCharge : linkedProduct.getCustomLineItems().getVariableChargeList()) {
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

                    if(linkedProduct.getCustomLineItems().getMinimumBilling() != null) {
                        MinimumBillingEntity minimumBillingEntity = new MinimumBillingEntity();
                        minimumBillingEntity.setValue(linkedProduct.getCustomLineItems().getMinimumBilling().getValue());
                        minimumBillingEntity.setVatable(linkedProduct.getCustomLineItems().getMinimumBilling().isVatable());
                        minimumBillingEntity.setVatInclusive(linkedProduct.getCustomLineItems().getMinimumBilling().isVatInclusive());
                        minimumBillingEntity.setPricingModelVersionId(savedPricingModelVersionEntity.getId());
                        MinimumBillingEntity savedMinimumBillingEntity = minimumBillingRepository.save(minimumBillingEntity);
                        minimumBillingRepository.flush();

                        for (RateStructureLineItem rateStructureLineItem : linkedProduct.getCustomLineItems().getMinimumBilling().getRateStructureLineItem()) {
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
                    PricingModelEntity selectedPricingModelEntity = pricingModelRepository.findById(linkedProduct.getPricingModelId()).orElseThrow(() -> new GenericException("Pricing Model Not Found", HttpStatus.NOT_FOUND, "Error finding pricing model with ID | " + linkedProduct.getPricingModelId()));
                    PricingModelEntity newCustomPricingModel = new PricingModelEntity();

                    try {
                        newCustomPricingModel.setName(merchantEntity.getCompanyName() + " custom pricing model for product: " + productEntity.getName());
                        newCustomPricingModel.setDescription("The custom pricing model generated for " + merchantEntity.getCompanyName() + " for modified pricing values for the product: " + productEntity.getName());
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
                        pricingModelVersionEntity.setBackOfficeUserByLastModifiedBy(backOfficeUserRepository.findOneById(linkMerchantProductToPricingModelRequest.getBackOfficeUserId()));
                        PricingModelVersionEntity savedPricingModelVersionEntity = pricingModelVersionRepository.save(pricingModelVersionEntity);
                        pricingModelVersionRepository.flush();

                        for (FixedCharge fixedCharge : linkedProduct.getCustomLineItems().getFixedChargeList()) {
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

                        for (VariableCharge variableCharge : linkedProduct.getCustomLineItems().getVariableChargeList()) {
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

                        if(linkedProduct.getCustomLineItems().getMinimumBilling() != null) {
                            MinimumBillingEntity minimumBillingEntity = new MinimumBillingEntity();
                            minimumBillingEntity.setValue(linkedProduct.getCustomLineItems().getMinimumBilling().getValue());
                            minimumBillingEntity.setVatable(linkedProduct.getCustomLineItems().getMinimumBilling().isVatable());
                            minimumBillingEntity.setVatInclusive(linkedProduct.getCustomLineItems().getMinimumBilling().isVatInclusive());
                            minimumBillingEntity.setPricingModelVersionId(savedPricingModelVersionEntity.getId());
                            MinimumBillingEntity savedMinimumBillingEntity = minimumBillingRepository.save(minimumBillingEntity);
                            minimumBillingRepository.flush();

                            for (RateStructureLineItem rateStructureLineItem : linkedProduct.getCustomLineItems().getMinimumBilling().getRateStructureLineItem()) {
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
                    merchantProductEntity.setCustomPricingModel(savedCustomPricingModel.getId());
                }

            }

            //save merchant product entity
            merchantProductRepository.save(merchantProductEntity);
            merchantProductRepository.flush();
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }

}
