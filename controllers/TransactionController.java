package za.co.wirecard.channel.backoffice.controllers;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import za.co.wirecard.channel.backoffice.constants.SearchEnum;
import za.co.wirecard.channel.backoffice.dto.models.requests.GetTransactionHistory;
import za.co.wirecard.channel.backoffice.dto.models.requests.SetTransactionState;
import za.co.wirecard.channel.backoffice.dto.models.responses.GetCardTransactionResponse;
import za.co.wirecard.channel.backoffice.dto.models.responses.GetStateTotalsInHistoryResponse;
import za.co.wirecard.channel.backoffice.dto.models.responses.GetTransactionLegDetailResponse;
import za.co.wirecard.channel.backoffice.dto.models.responses.GetTransactionLegResponse;
import za.co.wirecard.channel.backoffice.entities.MerchantEntity;
import za.co.wirecard.channel.backoffice.entities.TransactionEntity;
import za.co.wirecard.channel.backoffice.models.*;
import za.co.wirecard.channel.backoffice.services.TransactionService;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static za.co.wirecard.channel.backoffice.builder.TransactionSpecifications.*;

@RestController
@CrossOrigin
@RequestMapping(value = "/channel-back-office/api/v1/transaction")
public class TransactionController {

    private final TransactionService transactionService;
    private static final Logger logger = LogManager.getLogger(TransactionController.class);

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    //call to retrieve gateways (acquiring banks)
    //call to retrieve issuing banks (distinct select)

    @PreAuthorize("@sessionSecurityEvaluator.hasSessionToAuthority(#httpSession, #httpServletRequest, 'TRANSACTION_VIEW')")
    @PostMapping("/transaction-history")
    public Page<Transaction> getTransactionsHistory(@RequestParam(defaultValue = "0") int page,
                                                    @RequestParam(defaultValue = "25") int limit,
                                                    @RequestBody(required = false) GetTransactionHistory getTransactionHistory,
                                                    HttpServletRequest httpServletRequest) {

        String merchantRef = null;
        String cardHolder = null;
        String transactionValue = null;
        String settledValue = null;
        String refundValue = null;
        String authorisedValue = null;
        String authorisationCode = null;
        String transactionUid = null;
        String sessionUid = null;
        String errorMessage = null;
//        Long bankReference = null;
        String cardBinSearch = null;
        String lastFourSearch = null;
        GetCardTransactionResponse getCardTransactionResponse = new GetCardTransactionResponse();
        List<Long> transactionIds = new ArrayList<>();

        if (getTransactionHistory != null) {
            if (getTransactionHistory.getStringSearch() != null) {
                if (getTransactionHistory.getStringCriteria().equals(SearchEnum.MERCHANT_REF.value())) {
                    merchantRef = getTransactionHistory.getStringSearch();
                } else if (getTransactionHistory.getStringCriteria().equals(SearchEnum.CARD_HOLDER.value())) {
                    cardHolder = getTransactionHistory.getStringSearch();
                } else if (getTransactionHistory.getStringCriteria().equals(SearchEnum.TRANSACTION_VALUE.value())) {
                    transactionValue = getTransactionHistory.getStringSearch();
                } else if (getTransactionHistory.getStringCriteria().equals(SearchEnum.SETTLED_VALUE.value())) {
                    settledValue = getTransactionHistory.getStringSearch();
                } else if (getTransactionHistory.getStringCriteria().equals(SearchEnum.REFUNDED_VALUE.value())) {
                    refundValue = getTransactionHistory.getStringSearch();
                } else if (getTransactionHistory.getStringCriteria().equals(SearchEnum.AUTHORISED_VALUE.value())) {
                    authorisedValue = getTransactionHistory.getStringSearch();
                } else if (getTransactionHistory.getStringCriteria().equals(SearchEnum.AUTHORISATION_CODE.value())) {
                    authorisationCode = getTransactionHistory.getStringSearch();
                    getCardTransactionResponse = transactionService.getCardTransactionByAuthorisationId(getTransactionHistory.getStringSearch(), getTransactionHistory.getStartDate(), getTransactionHistory.getEndDate());
                } else if (getTransactionHistory.getStringCriteria().equals(SearchEnum.TRANSACTION_UID.value())) {
                    transactionUid = getTransactionHistory.getStringSearch();
                } else if (getTransactionHistory.getStringCriteria().equals(SearchEnum.SESSION_UID.value())) {
                    sessionUid = getTransactionHistory.getStringSearch();
                } else if (getTransactionHistory.getStringCriteria().equals(SearchEnum.CARD_NUMBER.value())) {

                    if (getTransactionHistory.getStringSearch().length() > 6) {
                        cardBinSearch = getTransactionHistory.getStringSearch().substring(0, 6);
                    } else {
                        cardBinSearch = null;
                    }
                    if (getTransactionHistory.getStringSearch().length() > 4) {
                        lastFourSearch = getTransactionHistory.getStringSearch().substring(getTransactionHistory.getStringSearch().length() - 4);
                    } else {
                        lastFourSearch = null;
                    }
                    transactionIds = transactionService.getCardTransactionByCardBinAndLastFour(cardBinSearch, lastFourSearch, getTransactionHistory.getStartDate(), getTransactionHistory.getEndDate());
                }
            }

            if (getTransactionHistory.getMerchantIds() != null) {
                logger.info("\nMerchant UIDS\n" + getTransactionHistory.getMerchantIds().toString());
            } else {
                logger.info("\nMerchant UIDS\n" + getTransactionHistory.getMerchantIds());
            }
            if (getTransactionHistory.getApplicationIds() != null) {
                logger.info("\nApplication UIDS\n" + getTransactionHistory.getApplicationIds().toString());
            } else {
                logger.info("\nApplication UIDS\n" + getTransactionHistory.getApplicationIds());
            }

            logger.info("TEST APP_UID!! | " + getTransactionHistory.getApplicationUid());

            logger.info(
                    "\ngetPaymentTypeCodes\n"
                            + getTransactionHistory.getPaymentTypeCodes()  +
                            "\ngetTransactionStatusCodes\n"
                            + getTransactionHistory.getTransactionStatusCodes() +
                            "\ngetStartDate\n"
                            + getTransactionHistory.getStartDate() +
                            "\ngetEndDate\n"
                            + getTransactionHistory.getEndDate() +
                            "\nmerchantRef\n"
                            + merchantRef +
                            "\ncardHolder | \n"
                            + cardHolder +
                            "\ntransactionUid\n"
                            + transactionUid +
                            "\ntransactionValue\n"
                            + transactionValue +
                            "\nrefundValue\n"
                            + refundValue +
                            "\nsettledValue\n"
                            + settledValue +
                            "\nauthorisedValue\n"
                            + authorisedValue +
                            "\nauthorisationCode\n"
                            + authorisationCode +
                            "\ncardBinSearch\n"
                            + cardBinSearch
            );

            Specification<TransactionEntity> specification = Specification
                    // firstName from parameter
                    .where(getTransactionHistory.getMerchantIds() == null || getTransactionHistory.getMerchantIds().size() == 0 ? null : merchantIdsEquals(getTransactionHistory.getMerchantIds()))
                    // lastName from parameter
                    .and(getTransactionHistory.getApplicationIds() == null || getTransactionHistory.getApplicationIds().size() == 0 ? null : applicationIdsEquals(getTransactionHistory.getApplicationIds()))
                    .and(getTransactionHistory.getMerchantUid() == null || StringUtils.isBlank(getTransactionHistory.getMerchantUid()) ? null : merchantUidEquals(getTransactionHistory.getMerchantUid()))
                    .and(getTransactionHistory.getApplicationUid() == null || StringUtils.isBlank(getTransactionHistory.getApplicationUid()) ? null : applicationUidEquals(getTransactionHistory.getApplicationUid()))
                    .and(getTransactionHistory.getPaymentTypeCodes() == null || getTransactionHistory.getPaymentTypeCodes().size() == 0 ? null : paymentTypeCodeEquals(getTransactionHistory.getPaymentTypeCodes()))
                    .and(getTransactionHistory.getTransactionStatusCodes() == null || getTransactionHistory.getTransactionStatusCodes().size() == 0 ? null : transactionStateCodeEquals(getTransactionHistory.getTransactionStatusCodes()))
                    .and(getTransactionHistory.getStartDate() == null || StringUtils.isBlank(getTransactionHistory.getStartDate().toString()) ? null : initiationDateGreaterThan(getTransactionHistory.getStartDate()))
                    .and(getTransactionHistory.getEndDate() == null || StringUtils.isBlank(getTransactionHistory.getEndDate().toString()) ? null : initiationDateLessThan(getTransactionHistory.getEndDate()))
                    //.and(getTransactionHistory.getEcis() == null || getTransactionHistory.getEcis().size() == 0 ? null : ecisEquals(getTransactionHistory.getEcis()))
                    .and(getTransactionHistory.getIssuingBank() == null || StringUtils.isBlank(getTransactionHistory.getIssuingBank()) ? null : issuingBankEquals(getTransactionHistory.getIssuingBank()))
                    //added cardtype
                    .and(getTransactionHistory.getCardTypes() == null || getTransactionHistory.getCardTypes().size() == 0 ? null : cardTypesEquals(getTransactionHistory.getCardTypes())
                    .and(getTransactionHistory.getAcquiringBankCode() == null || StringUtils.isBlank(getTransactionHistory.getAcquiringBankCode()) ? null : acquiringBankCodeEquals(getTransactionHistory.getAcquiringBankCode()))
                    .and(getTransactionHistory.getErrorMessage() == null || StringUtils.isBlank(getTransactionHistory.getErrorMessage()) ? null : errorMessageLike(getTransactionHistory.getErrorMessage()))
                    // .and(filterOnDisplayedTransactionStates())
                    .and(transactionByRelatedTransactionIdIsNull())
                    .and(merchantRef == null || StringUtils.isBlank(merchantRef) ? null : stringSearchMerchantReferenceLike(merchantRef))
                    .and(cardHolder == null || StringUtils.isBlank(cardHolder) ? null : stringSearchPurchaserNameLike(getTransactionHistory.getStringSearch()))
                    .and(transactionUid == null || StringUtils.isBlank(transactionUid) ? null : stringSearchTransactionUidLike(transactionUid))
                    .and(sessionUid == null || StringUtils.isBlank(sessionUid) ? null : stringSearchSessionUidLike(sessionUid))
                    .and(transactionValue == null || StringUtils.isBlank(transactionValue) ? null : stringSearchTransactionValueLike(transactionValue))
                    .and(refundValue == null || StringUtils.isBlank(refundValue) ? null :  stringSearchRefundValueLike(refundValue))
                    .and(settledValue == null || StringUtils.isBlank(settledValue) ? null :  stringSearchSettledValueLike(settledValue))
                    .and(authorisedValue == null || StringUtils.isBlank(authorisedValue) ? null :  stringSearchAuthorisedValueLike(authorisedValue))
//                        .and(bankReference == null ? null :  stringSearchTransactionIdLike(bankReference))
                    .and(authorisationCode == null || StringUtils.isBlank(authorisationCode) ? null :  stringSearchTransactionIdLike(getCardTransactionResponse.getTransactionId()))
                    .and(cardBinSearch == null || StringUtils.isBlank(cardBinSearch) ? null : cardTransactionIdsEquals(transactionIds)));
            Page<Transaction> transaction = transactionService.findAllTransactions(page, limit, specification);
            return transaction;
        }

        Page<Transaction> transaction = transactionService.findAllTransactions(page, limit, null);
        return transaction;

//        if (getTransactionHistory.getMerchantUids() != null && getTransactionHistory.getMerchantUids().size() > 0) {
//            if (getTransactionHistory.getApplicationUids() != null && getTransactionHistory.getApplicationUids().size() > 0) {
//                // List<InterfaceEntity> interfaces = transactionService.getInterfacesByApplicationId(getTransactionHistory.getApplicationUids());
////                List<Long> interfaceIds = new ArrayList<>();
////                for (InterfaceEntity interface_ : interfaces) {
////                    interfaceIds.add(interface_.getId());
////                }
//                // List<Long> merchants = transactionService.getMerchantIds(getTransactionHistory.getMerchantUids());
////                if (getTransactionHistory.getPaymentTypeCodes() != null && getTransactionHistory.getPaymentTypeCodes().size() > 0) {
////                    // List<Long> paymentTypes = transactionService.getPaymentTypeIds(getTransactionHistory.getPaymentTypeCodes());
////                }
////                if (getTransactionHistory.getTransactionStatusCodes() != null && getTransactionHistory.getTransactionStatusCodes().size() > 0) {
////                    // List<Long> transactionStateIds = transactionService.getTransactionStateIds(getTransactionHistory.getTransactionStatusCodes());
////                }
//
//                Specification<TransactionEntity> specification = Specification
//                        // firstName from parameter
//                        .where(getTransactionHistory.getMerchantUids() == null ? null : merchantIdsEquals(getTransactionHistory.getMerchantUids()))
//                        // lastName from parameter
//                        // .and(interfaceIdsEquals(interfaceIds))
//                        .and(getTransactionHistory.getApplicationUids() == null ? null : applicationIdsEquals(getTransactionHistory.getApplicationUids()))
//                        .and(getTransactionHistory.getPaymentTypeCodes() == null ? null : paymentTypeCodeEquals(getTransactionHistory.getPaymentTypeCodes()))
//                        .and(getTransactionHistory.getTransactionStatusCodes() == null ? null : transactionStateCodeEquals(getTransactionHistory.getTransactionStatusCodes()))
//                        .and(getTransactionHistory.getStartDate() == null ? null : initiationDateGreaterThan(getTransactionHistory.getStartDate()))
//                        .and(getTransactionHistory.getEndDate() == null ? null : initiationDateLessThan(getTransactionHistory.getEndDate()))
//                        .and(filterOnDisplayedTransactionStates())
//                        .and(transactionByRelatedTransactionIdIsNull())
//                        .and(merchantRef == null ? null : stringSearchMerchantReferenceLike(merchantRef))
//                        .and(cardHolder == null ? null : stringSearchPurchaserNameLike(getTransactionHistory.getStringSearch()))
//                        .and(transactionUid == null ? null : stringSearchTransactionUidLike(transactionUid))
//                        .and(transactionValue == null ? null : stringSearchTransactionValueLike(transactionValue))
//                        .and(refundValue == null ? null :  stringSearchRefundValueLike(refundValue))
//                        .and(settledValue == null ? null :  stringSearchSettledValueLike(settledValue))
//                        .and(authorisedValue == null ? null :  stringSearchAuthorisedValueLike(authorisedValue))
////                        .and(bankReference == null ? null :  stringSearchTransactionIdLike(bankReference))
//                        .and(authorisationCode == null ? null :  stringSearchTransactionIdLike(getCardTransactionResponse.getTransactionId()))
//                        .and(cardBinSearch == null ? null : cardTransactionIdsEquals(transactionIds));
//                Page<Transaction> transaction = transactionService.findAllTransactions(page, limit, specification);
//                return transaction;
//
//            } else {
//
//                // List<Long> merchants = transactionService.getMerchantIds(getTransactionHistory.getMerchantUids());
//                // List<Long> paymentTypes = transactionService.getPaymentTypeIds(getTransactionHistory.getPaymentTypeCodes());
//                // List<Long> transactionStateIds = transactionService.getTransactionStateIds(getTransactionHistory.getTransactionStatusCodes());
//
//                Specification<TransactionEntity> specification = Specification
//                        // firstName from parameter
//                        .where(getTransactionHistory.getMerchantUids() == null ? null : merchantIdsEquals(getTransactionHistory.getMerchantUids()))
//                        // lastName from parameter
//                        .and(getTransactionHistory.getPaymentTypeCodes() == null ? null : paymentTypeCodeEquals(getTransactionHistory.getPaymentTypeCodes()))
//                        .and(getTransactionHistory.getTransactionStatusCodes() == null ? null : transactionStateCodeEquals(getTransactionHistory.getTransactionStatusCodes()))
//                        .and(getTransactionHistory.getStartDate() == null ? null : initiationDateGreaterThan(getTransactionHistory.getStartDate()))
//                        .and(getTransactionHistory.getEndDate() == null ? null : initiationDateLessThan(getTransactionHistory.getEndDate()))
//                        .and(filterOnDisplayedTransactionStates())
//                        .and(transactionByRelatedTransactionIdIsNull())
//                        .and(merchantRef == null ? null : stringSearchMerchantReferenceLike(merchantRef))
//                        .and(cardHolder == null ? null : stringSearchPurchaserNameLike(getTransactionHistory.getStringSearch()))
//                        .and(transactionUid == null ? null : stringSearchTransactionUidLike(transactionUid))
//                        .and(transactionValue == null ? null : stringSearchTransactionValueLike(transactionValue))
//                        .and(refundValue == null ? null :  stringSearchRefundValueLike(refundValue))
//                        .and(settledValue == null ? null :  stringSearchSettledValueLike(settledValue))
//                        .and(authorisedValue == null ? null :  stringSearchAuthorisedValueLike(authorisedValue))
////                        .and(bankReference == null ? null :  stringSearchTransactionIdLike(bankReference))
//                        .and(authorisationCode == null ? null :  stringSearchTransactionIdLike(getCardTransactionResponse.getTransactionId()))
//                        .and(cardBinSearch == null ? null : cardTransactionIdsEquals(transactionIds));
//                Page<Transaction> transaction = transactionService.findAllTransactions(page, limit, specification);
//                return transaction;
//            }
//
//        } else {
//
//            if (getTransactionHistory.getApplicationUids() != null && getTransactionHistory.getApplicationUids().size() > 0) {
////                List<InterfaceEntity> interfaces = transactionService.getInterfacesByApplicationId(getTransactionHistory.getApplicationUids());
////                List<Long> interfaceIds = new ArrayList<>();
////                for (InterfaceEntity interface_ : interfaces) {
////                    interfaceIds.add(interface_.getId());
////                }
//
//                // List<Long> paymentTypes = transactionService.getPaymentTypeIds(getTransactionHistory.getPaymentTypeCodes());
//                // List<Long> transactionStateIds = transactionService.getTransactionStateIds(getTransactionHistory.getTransactionStatusCodes());
//
//                Specification<TransactionEntity> specification = Specification
//                        // firstName from parameter
//                        // lastName from parameter
//                        // .where(interfaceIdsEquals(interfaceIds))
//                        .where(getTransactionHistory.getPaymentTypeCodes() == null ? null : paymentTypeCodeEquals(getTransactionHistory.getPaymentTypeCodes()))
//                        .and(getTransactionHistory.getTransactionStatusCodes() == null ? null : transactionStateCodeEquals(getTransactionHistory.getTransactionStatusCodes()))
//                        .and(getTransactionHistory.getStartDate() == null ? null : initiationDateGreaterThan(getTransactionHistory.getStartDate()))
//                        .and(getTransactionHistory.getEndDate() == null ? null : initiationDateLessThan(getTransactionHistory.getEndDate()))
//                        .and(filterOnDisplayedTransactionStates())
//                        .and(transactionByRelatedTransactionIdIsNull())
//                        .and(merchantRef == null ? null : stringSearchMerchantReferenceLike(merchantRef))
//                        .and(cardHolder == null ? null : stringSearchPurchaserNameLike(getTransactionHistory.getStringSearch()))
//                        .and(transactionUid == null ? null : stringSearchTransactionUidLike(transactionUid))
//                        .and(transactionValue == null ? null : stringSearchTransactionValueLike(transactionValue))
//                        .and(refundValue == null ? null :  stringSearchRefundValueLike(refundValue))
//                        .and(settledValue == null ? null :  stringSearchSettledValueLike(settledValue))
//                        .and(authorisedValue == null ? null :  stringSearchAuthorisedValueLike(authorisedValue))
////                        .and(bankReference == null ? null :  stringSearchTransactionIdLike(bankReference))
//                        .and(authorisationCode == null ? null :  stringSearchTransactionIdLike(getCardTransactionResponse.getTransactionId()))
//                        .and(cardBinSearch == null ? null : cardTransactionIdsEquals(transactionIds));
//                Page<Transaction> transaction = transactionService.findAllTransactions(page, limit, specification);
//                return transaction;
//
//            } else {
//                // List<Long> paymentTypes = transactionService.getPaymentTypeIds(getTransactionHistory.getPaymentTypeCodes());
//                // List<Long> transactionStateIds = transactionService.getTransactionStateIds(getTransactionHistory.getTransactionStatusCodes());
//
//                Specification<TransactionEntity> specification = Specification
//                        // firstName from parameter
//                        // lastName from parameter
//                        .where(getTransactionHistory.getPaymentTypeCodes() == null ? null : paymentTypeCodeEquals(getTransactionHistory.getPaymentTypeCodes()))
//                        .and(getTransactionHistory.getTransactionStatusCodes() == null ? null : transactionStateCodeEquals(getTransactionHistory.getTransactionStatusCodes()))
//                        .and(getTransactionHistory.getStartDate() == null ? null : initiationDateGreaterThan(getTransactionHistory.getStartDate()))
//                        .and(getTransactionHistory.getEndDate() == null ? null : initiationDateLessThan(getTransactionHistory.getEndDate()))
//                        .and(filterOnDisplayedTransactionStates())
//                        .and(transactionByRelatedTransactionIdIsNull())
//                        .and(merchantRef == null ? null : stringSearchMerchantReferenceLike(merchantRef))
//                        .and(cardHolder == null ? null : stringSearchPurchaserNameLike(getTransactionHistory.getStringSearch()))
//                        .and(transactionUid == null ? null : stringSearchTransactionUidLike(transactionUid))
//                        .and(transactionValue == null ? null : stringSearchTransactionValueLike(transactionValue))
//                        .and(refundValue == null ? null :  stringSearchRefundValueLike(refundValue))
//                        .and(settledValue == null ? null :  stringSearchSettledValueLike(settledValue))
//                        .and(authorisedValue == null ? null :  stringSearchAuthorisedValueLike(authorisedValue))
////                        .and(bankReference == null ? null :  stringSearchTransactionIdLike(bankReference))
//                        .and(authorisationCode == null ? null :  stringSearchTransactionIdLike(getCardTransactionResponse.getTransactionId()))
//                        .and(cardBinSearch == null ? null : cardTransactionIdsEquals(transactionIds));
//                Page<Transaction> transaction = transactionService.findAllTransactions(page, limit, specification);
//                return transaction;
//            }
//        }
    }

    @GetMapping("/filter-options")
    public ResponseEntity<?> getFilterOptions(@RequestParam(required = false) List<Long> merchantIds, @RequestParam(required = false) List<Long> applicationIds) {
        FilterOptions filterOptions = transactionService.findFilterOptions(merchantIds, applicationIds);
        return ResponseEntity.ok(filterOptions);
    }

    @GetMapping("/transaction-totals")
    public ResponseEntity<?> getTransactionsTotals( @RequestParam(required = false) Long merchantId,
                                                    @RequestParam(required = false) Long applicationId,
                                                    @RequestParam(required = false) Long paymentTypeId,
                                                    @RequestParam(required = false) Long transactionStateId,
                                                    @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date startDate,
                                                    @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date endDate,
                                                    @RequestParam(required = false) String stringCriteria,
                                                    @RequestParam(required = false) String stringSearch) {
        return ResponseEntity.ok(transactionService.calculateTotals(merchantId, applicationId, paymentTypeId, transactionStateId, startDate, endDate, stringCriteria, stringSearch));
    }

    @PostMapping("/transaction-state")
    public void setTransactionState(@RequestBody SetTransactionState setTransactionState) {
        transactionService.setTransactionState(setTransactionState);
    }

    @GetMapping("/transaction-state-totals")
    public ResponseEntity<List<GetStateTotalsInHistoryResponse>> getTransactionStateTotalsByMerchantId(@RequestParam Long merchantId,
                                                                                                       @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date startDate) {
        return ResponseEntity.ok(transactionService.calculateStateTotalsByMerchantId(merchantId, startDate));
    }

    @GetMapping("/transaction-states")
    public ResponseEntity<List<TransactionState>> getAllTransactionStates() {
        return ResponseEntity.ok(transactionService.getAllTransactionStates());
    }

    @GetMapping("/payment-states/{paymentTypeId}")
    public ResponseEntity<List<PaymentState>> getPaymentStatesByPaymentTypeId(@PathVariable long paymentTypeId) {
        return ResponseEntity.ok(transactionService.getPaymentStatesByPaymentTypeId(paymentTypeId));
    }

    @PostMapping("/payment-states/{paymentTypeId}")
    public ResponseEntity<?> postPaymentState(@PathVariable long paymentTypeId, @RequestBody List<PostPaymentState> postPaymentState) {
        transactionService.postPaymentState(paymentTypeId, postPaymentState);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/merchants")
    public ResponseEntity<List<MerchantEntity>> getMerchants() {
        return ResponseEntity.ok(transactionService.getMerchants());
    }

    @GetMapping("/transaction-legs/{transactionId}")
    public ResponseEntity<List<GetTransactionLegResponse>> getTransactionLegs(@PathVariable long transactionId) {
        return ResponseEntity.ok(transactionService.getTransactionLegs(transactionId));
    }

    @GetMapping("/transaction-leg-detail/{transactionLegId}")
    public ResponseEntity<List<GetTransactionLegDetailResponse>> getTransactionLegDetail(@PathVariable long transactionLegId) {
        return ResponseEntity.ok(transactionService.getTransactionLegDetail(transactionLegId));
    }
}
