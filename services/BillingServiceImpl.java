package za.co.wirecard.channel.backoffice.services;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import za.co.wirecard.channel.backoffice.constants.SearchEnum;
import za.co.wirecard.channel.backoffice.dto.models.requests.CreateBillingRunRequest;
import za.co.wirecard.channel.backoffice.dto.models.responses.GetBillingRunHistoryResponse;
import za.co.wirecard.channel.backoffice.dto.models.responses.GetInvoiceDetailsResponse;
import za.co.wirecard.channel.backoffice.dto.models.responses.GetInvoiceHistoryResponse;
import za.co.wirecard.channel.backoffice.dto.models.responses.GetLineItemHistoryResponse;
import za.co.wirecard.channel.backoffice.entities.*;
import za.co.wirecard.channel.backoffice.exceptions.GenericException;
import za.co.wirecard.channel.backoffice.models.Transaction;
import za.co.wirecard.channel.backoffice.repositories.*;
import za.co.wirecard.channel.backoffice.entities.BillingRunEntity;
import za.co.wirecard.channel.backoffice.mq.SendAudit;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static za.co.wirecard.channel.backoffice.builder.BillingRunSpecifications.*;
import static za.co.wirecard.channel.backoffice.builder.InvoiceSpecifications.*;
import static za.co.wirecard.channel.backoffice.builder.LineItemSpecifications.*;
import static za.co.wirecard.channel.backoffice.builder.LineItemTransactionSpecifications.*;

@Service
public class BillingServiceImpl implements BillingService {

    private static final Logger logger = LogManager.getLogger(BillingServiceImpl.class);

    private final BillingRunRepository billingRunRepository;
    private final SendAudit sendAudit;
    private final BillingRunStatusRepository billingRunStatusRepository;
    private final BackOfficeUserRepository backOfficeUserRepository;
    private final InvoiceRepository invoiceRepository;
    private final RateStructureHistoryRepository rateStructureHistoryRepository;
    private final LineItemRepository lineItemRepository;
    private final LineItemTransactionRepository lineItemTransactionRepository;
    private final ApplicationPaymentTypeRepository applicationPaymentTypeRepository;
    private final CardTransactionRepository cardTransactionRepository;
    private final ThreeDsAuthRepository threeDsAuthRepository;
    private final ThreeDsTransactionRepository threeDsTransactionRepository;

    public BillingServiceImpl(BillingRunRepository billingRunRepository,
                              BillingRunStatusRepository billingRunStatusRepository,
                              BackOfficeUserRepository backOfficeUserRepository,
                              SendAudit sendAudit,
                              InvoiceRepository invoiceRepository,
                              RateStructureHistoryRepository rateStructureHistoryRepository,
                              LineItemRepository lineItemRepository,
                              LineItemTransactionRepository lineItemTransactionRepository,
                              ApplicationPaymentTypeRepository applicationPaymentTypeRepository,
                              CardTransactionRepository cardTransactionRepository,
                              ThreeDsAuthRepository threeDsAuthRepository,
                              ThreeDsTransactionRepository threeDsTransactionRepository) {
        this.billingRunRepository = billingRunRepository;
        this.billingRunStatusRepository = billingRunStatusRepository;
        this.backOfficeUserRepository = backOfficeUserRepository;
        this.sendAudit = sendAudit;
        this.invoiceRepository = invoiceRepository;
        this.rateStructureHistoryRepository = rateStructureHistoryRepository;
        this.lineItemRepository = lineItemRepository;
        this.lineItemTransactionRepository = lineItemTransactionRepository;
        this.applicationPaymentTypeRepository = applicationPaymentTypeRepository;
        this.cardTransactionRepository = cardTransactionRepository;
        this.threeDsAuthRepository = threeDsAuthRepository;
        this.threeDsTransactionRepository = threeDsTransactionRepository;
    }

    @Override
    public Page<GetBillingRunHistoryResponse> viewBillingRunHistory(int page, int limit, String stringCriteria, String stringSearch) {
        logger.info("Attempting to retrieve billing run history");
        String name = null;
        if (StringUtils.isNotBlank(stringCriteria)) {
            if (stringCriteria.equals(SearchEnum.NAME.value())) {
                name = stringSearch;
            }
        }

        Specification<BillingRunEntity> specification = Specification
                .where(StringUtils.isNotBlank(name) ? stringSearchNameLike(name) : null);
//                .and(startDate == null ? null : dateCreatedGreaterThan(startDate))
//                .and(endDate == null ? null : dateCreatedLessThan(endDate));
        Pageable pageable = PageRequest.of(page, limit, Sort.by(Sort.Order.desc("dateRequested")));
        Page<BillingRunEntity> billingRunEntities = billingRunRepository.findAll(specification, pageable);

        Page<GetBillingRunHistoryResponse> getBillingRunHistoryResponses = billingRunEntities.map(GetBillingRunHistoryResponse::new);
        logger.info("Billing run history successfully retrieved");
        return getBillingRunHistoryResponses;
    }

    @Override
    public ResponseEntity<?> setBillingRunApprovedBy(Long billingRunId, Long approvedById) {
        logger.info("Attempting to set approved by for billing run id | " + billingRunId);
        BillingRunEntity billingRunEntity = billingRunRepository.findById(billingRunId).orElseThrow(() ->
                new GenericException("Billing Run Not Found", HttpStatus.INTERNAL_SERVER_ERROR, "No billing run found for id | " + billingRunId));
//        BillingRunStatusEntity billingRunStatusEntity = billingRunStatusRepository.findBillingRunStatusEntityByCode("APPROVED");
//        billingRunEntity.setBillingRunStatusByBillingRunStatusId(billingRunStatusEntity);
        billingRunEntity.setBackOfficeUserByApprovedBy(backOfficeUserRepository.findOneById(approvedById));
        billingRunEntity.setDateApproved(new Timestamp(new Date().getTime()));
        billingRunRepository.save(billingRunEntity);

        logger.info("Successfully set approved by for billing run id | " + billingRunId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> createBillingRun(CreateBillingRunRequest createBillingRunRequest) {
        //update any pending billing runs to REJECTED
        List<BillingRunEntity> pendingBillingRuns = billingRunRepository.findAllByBillingRunStatusByBillingRunStatusId(billingRunStatusRepository.findBillingRunStatusEntityByCode("PENDING"));
        for (BillingRunEntity billingRunEntity : pendingBillingRuns) {
            billingRunEntity.setBillingRunStatusByBillingRunStatusId(billingRunStatusRepository.findBillingRunStatusEntityByCode("REJECTED"));
            billingRunRepository.save(billingRunEntity);
            billingRunRepository.flush();
        }

        //drop message on queue to create new billing run
        try {
            logger.info("Dropping mock bill run message: " + createBillingRunRequest.getName());
            sendAudit.createMockBillingRun(createBillingRunRequest);
        } catch(Exception e) {
            logger.info("Error adding message to queue: " + e.getMessage());
            throw new GenericException("Error adding message to queue", HttpStatus.INTERNAL_SERVER_ERROR, "Error starting billing run: " + e.getMessage());
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
    
    @Override
    public Page<GetInvoiceHistoryResponse> viewInvoiceHistory(int page, int limit, Long billingRunId, String stringCriteria, String stringSearch) {
        logger.info("Attempting to retrieve invoice history");
        String invoiceNumber = null;
        if (StringUtils.isNotBlank(stringCriteria)) {
            if (stringCriteria.equals(SearchEnum.INVOICE_NUMBER.value())) {
                invoiceNumber = stringSearch;
            }
        }

        Specification<InvoiceEntity> specification = Specification
                    .where(billingRunIdEquals(billingRunId))
                    .and(StringUtils.isNotBlank(invoiceNumber) ? stringSearchInvoiceNumberLike(invoiceNumber) : null);
        Pageable pageable = PageRequest.of(page, limit, Sort.by(Sort.Order.desc("dateCreated")));
        Page<InvoiceEntity> invoiceEntities = invoiceRepository.findAll(specification, pageable);

        Page<GetInvoiceHistoryResponse> getInvoiceHistoryResponses = invoiceEntities.map(GetInvoiceHistoryResponse::new);
        logger.info("Invoice history successfully retrieved");
        return getInvoiceHistoryResponses;
    }

    @Override
    public List<GetInvoiceDetailsResponse> getInvoiceDetails(Long invoiceId) {
        logger.info("Attempting to retrieve invoice details for invoice | " + invoiceId);
        InvoiceEntity invoiceEntity = invoiceRepository.findById(invoiceId)
                .orElseThrow(() -> new GenericException("Invoice not found", HttpStatus.NOT_FOUND, "No invoice found for id | " + invoiceId));
        List<RateStructureHistoryEntity> rateStructureHistoryEntities = rateStructureHistoryRepository.findRateStructureHistoryEntitiesByInvoiceByInvoiceId(invoiceEntity);
        List<GetInvoiceDetailsResponse> getInvoiceDetailsResponses = new ArrayList<>();
        for (RateStructureHistoryEntity rateStructureHistoryEntity: rateStructureHistoryEntities) {
            getInvoiceDetailsResponses.add(new GetInvoiceDetailsResponse(rateStructureHistoryEntity));
        }
        logger.info("Invoice details successfully retrieved for invoice | " + invoiceId);
        return getInvoiceDetailsResponses;
    }

    @Override
    public Page<GetLineItemHistoryResponse> viewLineItemHistory(int page, int limit, Long invoiceId, String stringCriteria, String stringSearch) {
        logger.info("Attempting to retrieve line item history");
        String lineItemName = null;
        if (StringUtils.isNotBlank(stringCriteria)) {
            if (stringCriteria.equals(SearchEnum.LINE_ITEM_NAME.value())) {
                lineItemName = stringSearch;
            }
        }

        Specification<LineItemEntity> specification = Specification
                .where(invoiceIdEquals(invoiceId))
                .and(StringUtils.isNotBlank(lineItemName) ? stringSearchLineItemNameLike(lineItemName) : null);
        Pageable pageable = PageRequest.of(page, limit, Sort.by(Sort.Order.desc("id")));
        Page<LineItemEntity> lineItemEntities = lineItemRepository.findAll(specification, pageable);

        Page<GetLineItemHistoryResponse> getLineItemHistoryResponsesPage = lineItemEntities.map(GetLineItemHistoryResponse::new);
        logger.info("Line item history successfully retrieved");
        return getLineItemHistoryResponsesPage;
    }

    @Override
    public Page<Transaction> viewTransactionHistory(int page, int limit, Long id, Boolean isLineItem, String stringCriteria, String stringSearch) {
        logger.info("Attempting to retrieve billing transaction history");
        String merchantReference = null;
        if (StringUtils.isNotBlank(stringCriteria)) {
            if (stringCriteria.equals(SearchEnum.MERCHANT_REF.value())) {
                merchantReference = stringSearch;
            }
        }

        Specification<LineItemTransactionEntity> specification = Specification
                .where(isLineItem ? lineItemIdEquals(id) : subLineItemIdEquals(id))
                .and(StringUtils.isNotBlank(merchantReference) ? merchantReferenceEquals(merchantReference) : null);
        Pageable pageable = PageRequest.of(page, limit, Sort.by(Sort.Order.desc("id")));
        Page<LineItemTransactionEntity> lineItemTransactionEntities = lineItemTransactionRepository.findAll(specification, pageable);
        Page<TransactionEntity> transactions = lineItemTransactionEntities.map(LineItemTransactionEntity::getTransactionByTransactionId);

        logger.info("Successfully retrieved billing transaction history");
        return transactions.map((transactionEntity) -> {
            CardTransactionEntity cardTransactionEntity = cardTransactionRepository.findByTransactionByTransactionId(transactionEntity);
            ThreeDsTransactionEntity threeDsTransactionEntity = null;
            ThreeDsAuthEntity threeDsAuthEntity = null;
            List<CardConfigurationEciEntity> cardConfigurationEciEntities = null;
            ApplicationPaymentTypeEntity applicationPaymentTypeEntity =
                    applicationPaymentTypeRepository.findFirstByInterfaceByInterfaceIdAndPaymentTypeByPaymentTypeId(
                            transactionEntity.getInterfaceByInterfaceId(),
                            transactionEntity.getPaymentTypeByPaymentTypeId());
            ApplicationEntity applicationEntity = applicationPaymentTypeEntity.getApplicationByApplicationId();
            if (transactionEntity.getPaymentTypeByPaymentTypeId().getCode().equalsIgnoreCase("card")) {
                threeDsTransactionEntity = threeDsTransactionRepository.findByTransactionByTransactionId(transactionEntity);
                threeDsAuthEntity = threeDsAuthRepository.findByThreeDsTransactionByThreeDsTransactionId(threeDsTransactionEntity);
            }
            return new Transaction(transactionEntity, cardTransactionEntity, threeDsAuthEntity, applicationEntity);
        });


    }
}
