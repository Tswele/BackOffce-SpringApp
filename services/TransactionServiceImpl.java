package za.co.wirecard.channel.backoffice.services;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import za.co.wirecard.channel.backoffice.constants.SearchEnum;
import za.co.wirecard.channel.backoffice.dto.models.GetTotalsInHistoryResponse;
import za.co.wirecard.channel.backoffice.dto.models.requests.SetTransactionState;
import za.co.wirecard.channel.backoffice.dto.models.responses.GetCardTransactionResponse;
import za.co.wirecard.channel.backoffice.dto.models.responses.GetStateTotalsInHistoryResponse;
import za.co.wirecard.channel.backoffice.dto.models.responses.GetTransactionLegDetailResponse;
import za.co.wirecard.channel.backoffice.dto.models.responses.GetTransactionLegResponse;
import za.co.wirecard.channel.backoffice.entities.*;
import za.co.wirecard.channel.backoffice.exceptions.GenericException;
import za.co.wirecard.channel.backoffice.models.*;
import za.co.wirecard.channel.backoffice.repositories.*;
import za.co.wirecard.common.exceptions.MerchantNotFoundException;
import za.co.wirecard.common.exceptions.NotFoundException;

import javax.transaction.Transactional;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TransactionServiceImpl implements TransactionService {

    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

    private final MerchantRepository merchantRepository;
    private final JdbcTemplate jdbcTemplate;
    private final TransactionRepository transactionRepository;
    private final TransactionLegRepository transactionLegRepository;
    private final InterfaceRepository interfaceRepository;
    private final ApplicationPaymentTypeRepository applicationPaymentTypeRepository;
    private final CardTransactionRepository cardTransactionRepository;
    private final ThreeDsAuthRepository threeDsAuthRepository;
    private final ThreeDsTransactionRepository threeDsTransactionRepository;
    private final ApplicationRepository applicationRepository;
    private final TransactionStateRepository transactionStateRepository;
    private final MobicredPurchseCreateRepository mobicredPurchseCreateRepository;
    private final MobicredValidateRepository mobicredValidateRepository;
    private final MobicredOtpRepository mobicredOtpRepository;
    private final SecureEftPaymentKeyRepository secureEftPaymentKeyRepository;
    private final SecureEftStatusRepository secureEftStatusRepository;
    private final MasterPassTxRepository masterPassTxRepository;
    private final BankservLookupRequestRepository bankservLookupRequestRepository;
    private final BankservLookupResponseRepository bankservLookupResponseRepository;
    private final BankservAuthenticateRequestRepository bankservAuthenticateRequestRepository;
    private final BankservAuthenticateResponseRepository bankservAuthenticateResponseRepository;
    private final CardBigisoResponseRepository cardBigisoResponseRepository;
    private final CardBigisoTransactionRepository cardBigisoTransactionRepository;
    private final CardIveriResponseRepository cardIveriResponseRepository;
    private final CardIveriTransactionRepository cardIveriTransactionRepository;
    private final CardPostillionResponseRepository cardPostillionResponseRepository;
    private final CardPostillionTransactionRepository cardPostillionTransactionRepository;
    private final PaymentStateRepository paymentStateRepository;
    private final PaymentTypeRepository paymentTypeRepository;
    private final GatewayRepository gatewayRepository;
    private final EciRepository eciRepository;
    //added card type repository
    private final CardTypeRepository cardTypeRepository;
    private final CardinalLookupRequestRepository cardinalLookupRequestRepository;
    private final CardinalLookupResponseRepository cardinalLookupResponseRepository;
    private final CardinalAuthRepository cardinalAuthRepository;
    private final CardVacpRequestRepository cardVacpRequestRepository;
    private final CardVacpResponseRepository cardVacpResponseRepository;
    private final CardBowRequestRepository cardBowRequestRepository;
    private final CardBowResponseRepository cardBowResponseRepository;
    private final ZapperClientUpdateRepository zapperClientUpdateRepository;
    private final ZapperProviderUpdateRepository zapperProviderUpdateRepository;
    private final OzowEftPaymentUpdateRepository ozowEftPaymentUpdateRepository;
    private final StitchEftCreatePaymentRequestRepository stitchEftCreatePaymentRequestRepository;
    private final StitchEftCreatePaymentResponseRepository stitchEftCreatePaymentResponseRepository;
    private final StitchEftGetPaymentResponseRepository stitchEftGetPaymentResponseRepository;
    private static final Logger logger = LogManager.getLogger(TransactionServiceImpl.class);


    public TransactionServiceImpl(MerchantRepository merchantRepository,
                                  JdbcTemplate jdbcTemplate,
                                  TransactionRepository transactionRepository,
                                  TransactionLegRepository transactionLegRepository,
                                  InterfaceRepository interfaceRepository,
                                  ApplicationPaymentTypeRepository applicationPaymentTypeRepository,
                                  CardTransactionRepository cardTransactionRepository,
                                  ThreeDsAuthRepository threeDsAuthRepository,
                                  ThreeDsTransactionRepository threeDsTransactionRepository,
                                  ApplicationRepository applicationRepository,
                                  TransactionStateRepository transactionStateRepository,
                                  MobicredPurchseCreateRepository mobicredPurchseCreateRepository,
                                  MobicredValidateRepository mobicredValidateRepository,
                                  MobicredOtpRepository mobicredOtpRepository,
                                  SecureEftPaymentKeyRepository secureEftPaymentKeyRepository,
                                  SecureEftStatusRepository secureEftStatusRepository,
                                  MasterPassTxRepository masterPassTxRepository,
                                  BankservLookupRequestRepository bankservLookupRequestRepository,
                                  BankservLookupResponseRepository bankservLookupResponseRepository,
                                  BankservAuthenticateRequestRepository bankservAuthenticateRequestRepository,
                                  BankservAuthenticateResponseRepository bankservAuthenticateResponseRepository,
                                  CardBigisoResponseRepository cardBigisoResponseRepository,
                                  CardBigisoTransactionRepository cardBigisoTransactionRepository,
                                  CardIveriResponseRepository cardIveriResponseRepository,
                                  CardIveriTransactionRepository cardIveriTransactionRepository,
                                  CardPostillionResponseRepository cardPostillionResponseRepository,
                                  CardPostillionTransactionRepository cardPostillionTransactionRepository,
                                  PaymentStateRepository paymentStateRepository,
                                  PaymentTypeRepository paymentTypeRepository,
                                  GatewayRepository gatewayRepository,
                                  EciRepository eciRepository,
                                  CardTypeRepository cardTypeRepository, CardinalLookupRequestRepository cardinalLookupRequestRepository,
                                  CardinalLookupResponseRepository cardinalLookupResponseRepository,
                                  CardinalAuthRepository cardinalAuthRepository,
                                  CardVacpRequestRepository cardVacpRequestRepository,
                                  CardVacpResponseRepository cardVacpResponseRepository,
                                  CardBowRequestRepository cardBowRequestRepository,
                                  CardBowResponseRepository cardBowResponseRepository,
                                  ZapperClientUpdateRepository zapperClientUpdateRepository,
                                  ZapperProviderUpdateRepository zapperProviderUpdateRepository,
                                  OzowEftPaymentUpdateRepository ozowEftPaymentUpdateRepository,
                                  StitchEftCreatePaymentRequestRepository stitchEftCreatePaymentRequestRepository,
                                  StitchEftCreatePaymentResponseRepository stitchEftCreatePaymentResponseRepository,
                                  StitchEftGetPaymentResponseRepository stitchEftGetPaymentResponseRepository) {
        this.merchantRepository = merchantRepository;
        this.jdbcTemplate = jdbcTemplate;
        this.transactionRepository = transactionRepository;
        this.transactionLegRepository = transactionLegRepository;
        this.interfaceRepository = interfaceRepository;
        this.applicationPaymentTypeRepository = applicationPaymentTypeRepository;
        this.cardTransactionRepository = cardTransactionRepository;
        this.threeDsAuthRepository = threeDsAuthRepository;
        this.threeDsTransactionRepository = threeDsTransactionRepository;
        this.applicationRepository = applicationRepository;
        this.transactionStateRepository = transactionStateRepository;
        this.mobicredPurchseCreateRepository = mobicredPurchseCreateRepository;
        this.mobicredValidateRepository = mobicredValidateRepository;
        this.mobicredOtpRepository = mobicredOtpRepository;
        this.secureEftPaymentKeyRepository = secureEftPaymentKeyRepository;
        this.secureEftStatusRepository = secureEftStatusRepository;
        this.masterPassTxRepository = masterPassTxRepository;
        this.bankservLookupRequestRepository = bankservLookupRequestRepository;
        this.bankservLookupResponseRepository = bankservLookupResponseRepository;
        this.bankservAuthenticateRequestRepository = bankservAuthenticateRequestRepository;
        this.bankservAuthenticateResponseRepository = bankservAuthenticateResponseRepository;
        this.cardBigisoResponseRepository = cardBigisoResponseRepository;
        this.cardBigisoTransactionRepository = cardBigisoTransactionRepository;
        this.cardIveriResponseRepository = cardIveriResponseRepository;
        this.cardIveriTransactionRepository = cardIveriTransactionRepository;
        this.cardPostillionResponseRepository = cardPostillionResponseRepository;
        this.cardPostillionTransactionRepository = cardPostillionTransactionRepository;
        this.paymentStateRepository = paymentStateRepository;
        this.paymentTypeRepository = paymentTypeRepository;
        this.gatewayRepository = gatewayRepository;
        this.eciRepository = eciRepository;
        //added card type
        this.cardTypeRepository = cardTypeRepository;
        this.cardinalLookupRequestRepository = cardinalLookupRequestRepository;
        this.cardinalLookupResponseRepository = cardinalLookupResponseRepository;
        this.cardinalAuthRepository = cardinalAuthRepository;
        this.cardVacpRequestRepository = cardVacpRequestRepository;
        this.cardVacpResponseRepository = cardVacpResponseRepository;
        this.cardBowRequestRepository = cardBowRequestRepository;
        this.cardBowResponseRepository = cardBowResponseRepository;
        this.zapperClientUpdateRepository = zapperClientUpdateRepository;
        this.zapperProviderUpdateRepository = zapperProviderUpdateRepository;
        this.ozowEftPaymentUpdateRepository = ozowEftPaymentUpdateRepository;
        this.stitchEftCreatePaymentRequestRepository = stitchEftCreatePaymentRequestRepository;
        this.stitchEftCreatePaymentResponseRepository = stitchEftCreatePaymentResponseRepository;
        this.stitchEftGetPaymentResponseRepository = stitchEftGetPaymentResponseRepository;
    }

    @Override
    public Page<Transaction> findAllTransactions(int page, int limit, Specification specification) {
//        if (merchantIds != null && merchantIds.size() > 0) {
//            for (Long merchantId : merchantIds) {
//                if (merchantId != null) {
//                    MerchantEntity valid = merchantRepository.findById(merchantId).orElseThrow(() -> new MerchantNotFoundException(merchantId));
//                }
//            }
//        }
        Pageable pageable = PageRequest.of(page, limit, Sort.by(Sort.Order.desc("initiationDate")));
        Page<TransactionEntity> transactions = transactionRepository.findAll(specification, pageable);
        return transactions.map((transactionEntity) -> {
            CardTransactionEntity cardTransactionEntity = cardTransactionRepository.findByTransactionByTransactionId(transactionEntity);
            ThreeDsTransactionEntity threeDsTransactionEntity = null;
            ThreeDsAuthEntity threeDsAuthEntity = null;
            List<CardConfigurationEciEntity> cardConfigurationEciEntities = null;
            ApplicationPaymentTypeEntity applicationPaymentTypeEntity = applicationPaymentTypeRepository.findFirstByInterfaceByInterfaceIdAndPaymentTypeByPaymentTypeId(transactionEntity.getInterfaceByInterfaceId(), transactionEntity.getPaymentTypeByPaymentTypeId());
//            ApplicationEntity applicationEntity = null;
//            if (applicationPaymentTypeEntity.isPresent()) {
//                applicationEntity = applicationPaymentTypeEntity.get().getApplicationByApplicationId();
//            }
            ApplicationEntity applicationEntity = applicationPaymentTypeEntity.getApplicationByApplicationId();
            if (transactionEntity.getPaymentTypeByPaymentTypeId().getCode().equalsIgnoreCase("card")) {
                threeDsTransactionEntity = threeDsTransactionRepository.findByTransactionByTransactionId(transactionEntity);
                threeDsAuthEntity = threeDsAuthRepository.findByThreeDsTransactionByThreeDsTransactionId(threeDsTransactionEntity);
            }
            return new Transaction(transactionEntity, cardTransactionEntity, threeDsAuthEntity, applicationEntity);
        });
    }

    @Override
    public FilterOptions findFilterOptions(List<Long> merchantIds, List<Long> applicationIds) {

        FilterOptions filterOptions = new FilterOptions();
        List<MerchantEntity> merchantEntities;
        List<ApplicationEntity> applicationEntities;
        logger.info("Filter Options | Merchant IDS | " + merchantIds);
        logger.info("Filter Options | Application IDS | " + applicationIds);
        if (merchantIds != null) {
            merchantEntities = merchantRepository.findAllById(merchantIds);
            if (applicationIds == null) {
                applicationEntities = applicationRepository.findAllByMerchantByMerchantIdIn(merchantEntities);
                filterOptions.setApplicationEntities(applicationEntities.stream().map(ApplicationType::new).collect(Collectors.toList()));
                List<InterfacesPayment> interfacesPayments = applicationPaymentTypeRepository.findByApplicationByApplicationIdIn(applicationEntities)
                        .stream()
                        .map(InterfacesPayment::new)
                        .collect(Collectors.toList());
                filterOptions.setPaymentInterfaces(interfacesPayments.stream().map(InterfacesPayment::getInterfaceEntity).collect(Collectors.toList()));
                filterOptions.setPaymentTypes(interfacesPayments.stream().map(InterfacesPayment::getPaymentType).collect(Collectors.toList()));
            } else {
                applicationEntities = applicationRepository.findAllById(applicationIds);
                filterOptions.setApplicationEntities(applicationEntities.stream().map(ApplicationType::new).collect(Collectors.toList()));
                List<InterfacesPayment> interfacesPayments = applicationPaymentTypeRepository.findByApplicationByApplicationIdIn(applicationEntities)
                        .stream()
                        .map(InterfacesPayment::new)
                        .collect(Collectors.toList());
                filterOptions.setPaymentInterfaces(interfacesPayments.stream().map(InterfacesPayment::getInterfaceEntity).collect(Collectors.toList()));
                filterOptions.setPaymentTypes(interfacesPayments.stream().map(InterfacesPayment::getPaymentType).collect(Collectors.toList()));
            }
        } else {
            filterOptions.setPaymentTypes(paymentTypeRepository.findAll().stream().map(PaymentType::new).collect(Collectors.toList()));
        }

        filterOptions.setTransactionStates(transactionStateRepository.findAll().stream().map(TransactionState::new).collect(Collectors.toList()));
        filterOptions.setAcquiringBanks(gatewayRepository.findAll().stream().map(Gateway::new).collect(Collectors.toList()));
        filterOptions.setIssuingBanks(cardTransactionRepository.findDistinctIssuingBanks());
        filterOptions.setEcis(eciRepository.findAllEci());
        //addedd card type
       // CardTypeEntity cardType = new CardTypeEntity();
       // filterOptions.setCardTypes(cardTypeRepository.findAll().stream().map(CardType::new).collect(Collectors.toList()));
       // filterOptions.setCardTypes(cardTransactionRepository.findDistinctCardType());
        filterOptions.setCardTypes(cardTypeRepository.findAllCardType());
        logger.info("Return filter options...");
        return filterOptions;

//        if (merchantIds != null) {
//            merchantRepository.findById(merchantIds).orElseThrow(() -> new MerchantNotFoundException(merchantId));
//
//            if (applicationId == null) {
//                List<ApplicationEntity> applicationEntities = applicationRepository.findAllByMerchantId(merchantId);
//                List<InterfaceEntity> interfaceEntities = interfaceRepository.findAllByMerchantId(merchantId);
//                List<PaymentInterface> paymentInterfaces = new ArrayList<>();
//                List<PaymentType> paymentTypes = new ArrayList<>();
//
//                for ( InterfaceEntity interfaceEntity : interfaceEntities ) {
//                    paymentInterfaces.add(new PaymentInterface(interfaceEntity));
//                    paymentTypes.add(new PaymentType(interfaceEntity.getPaymentTypeByPaymentTypeId()));
//                }
//
//                List<TransactionStateEntity> transactionStateEntities = transactionStateRepository.findByCodeIn(TransactionStateEnum.getTransactionStateValuesAsString());
//                List<TransactionState> transactionStates = new ArrayList<>();
//                for (TransactionStateEntity transactionStateEntity : transactionStateEntities) {
//                    transactionStates.add(new TransactionState(transactionStateEntity));
//
//                }
//
//                List<ApplicationType> applicationTypes = new ArrayList<>();
//                for (ApplicationEntity applicationEntity: applicationEntities) {
//                    applicationTypes.add(new ApplicationType(applicationEntity));
//                }
//                return new FilterOptions(applicationTypes, paymentInterfaces, paymentTypes, transactionStates);
//
//            } else {
//
//                List<ApplicationEntity> applicationEntities = applicationRepository.findAllByMerchantId(merchantId);
//                List<ApplicationPaymentTypeEntity> applicationPaymentTypeEntities = applicationPaymentTypeRepository.findByApplicationId(applicationId);
//                List<InterfaceEntity> interfaceEntities = new ArrayList<>();
//                List<PaymentInterface> paymentInterfaces = new ArrayList<>();
//                List<PaymentType> paymentTypes = new ArrayList<>();
//
//                for (ApplicationPaymentTypeEntity applicationPaymentTypeEntity : applicationPaymentTypeEntities) {
//                    interfaceEntities.add(applicationPaymentTypeEntity.getInterfaceByInterfaceId());
//                }
//
//                for ( InterfaceEntity interfaceEntity : interfaceEntities ) {
//                    paymentInterfaces.add(new PaymentInterface(interfaceEntity));
//                    paymentTypes.add(new PaymentType(interfaceEntity.getPaymentTypeByPaymentTypeId()));
//                }
//
//                List<TransactionStateEntity> transactionStateEntities = transactionStateRepository.findByCodeIn(TransactionStateEnum.getTransactionStateValuesAsString());
//                List<TransactionState> transactionStates = new ArrayList<>();
//                for (TransactionStateEntity transactionStateEntity : transactionStateEntities) {
//                    transactionStates.add(new TransactionState(transactionStateEntity));
//
//                }
//
//                List<ApplicationType> applicationTypes = new ArrayList<>();
//                for (ApplicationEntity applicationEntity: applicationEntities) {
//                    applicationTypes.add(new ApplicationType(applicationEntity));
//                }
//                return new FilterOptions(applicationTypes, paymentInterfaces, paymentTypes, transactionStates);
//            }
//        } else {
//
//            // The commented out code in the next bit is an alternative faster way to get payment types, however is static instead of dynamic
//            // (retrieves list of all payment types instead of checking which ones are used)
//
//            List<ApplicationEntity> applicationEntities = applicationRepository.findAll();
//            List<InterfaceEntity> interfaceEntities = interfaceRepository.findAll();
////            List<PaymentTypeEntity> paymentTypeEntities = paymentTypeRepository.findAll();
//            List<PaymentInterface> paymentInterfaces = new ArrayList<>();
//            List<PaymentType> paymentTypes = new ArrayList<>();
//
////            for (PaymentTypeEntity paymentTypeEntity : paymentTypeEntities) {
////                paymentTypes.add(new PaymentType(paymentTypeEntity));
////            }
//
////            for ( InterfaceEntity interfaceEntity : interfaceEntities ) {
////                //paymentInterfaces.add(new PaymentInterface(interfaceEntity));
////                PaymentType paymentType = new PaymentType(interfaceEntity.getPaymentTypeByPaymentTypeId());
////                if (!paymentTypes.contains(paymentType)){
////                    paymentTypes.add(paymentType);
////                }
////            }
//
//            List<String> paymentTypeNames = new ArrayList<>();
//            for ( InterfaceEntity interfaceEntity : interfaceEntities ) {
//                PaymentType paymentType = new PaymentType(interfaceEntity.getPaymentTypeByPaymentTypeId());
//                if (!paymentTypeNames.contains(paymentType.getPaymentTypeName())){
//                    paymentTypeNames.add(paymentType.getPaymentTypeName());
//                    paymentTypes.add(paymentType);
//                }
//            }
//
//
//
//
//            List<TransactionStateEntity> transactionStateEntities = transactionStateRepository.findByCodeIn(TransactionStateEnum.getTransactionStateValuesAsString());
//            List<TransactionState> transactionStates = new ArrayList<>();
//            for (TransactionStateEntity transactionStateEntity : transactionStateEntities) {
//                transactionStates.add(new TransactionState(transactionStateEntity));
//            }
//
//            List<ApplicationType> applicationTypes = new ArrayList<>();
//            for (ApplicationEntity applicationEntity: applicationEntities) {
//                applicationTypes.add(new ApplicationType(applicationEntity));
//            }
//            return new FilterOptions(applicationTypes, paymentInterfaces, paymentTypes, transactionStates);
//
//        }
    }

    @Override
    public GetTotalsInHistoryResponse calculateTotals(Long merchantId, Long applicationId, Long paymentTypeId, Long transactionStateId, Date startDate, Date endDate, String stringCriteria, String stringSearch) {
        if (merchantId != null) {
            merchantRepository.findById(merchantId).orElseThrow(() -> new MerchantNotFoundException(merchantId));
        }

        String cardBinSearch = null;
        String lastFourSearch = null;

        StringBuilder sqlQuery = new StringBuilder("select count(*) as tx_count, COALESCE(sum(settled_value),0) as settled_sum, " +
                "COALESCE(sum(authorised_value),0) as authorised_sum from dbo.[transaction] t, dbo.[transaction_state] s, dbo.[application_payment_type] a");

        if (stringCriteria != null) {
            if (stringCriteria.equals(SearchEnum.AUTHORISATION_CODE.value()) || stringCriteria.equals(SearchEnum.CARD_NUMBER.value())) {
                sqlQuery.append(", dbo.[card_transaction] c");
            }
        }
        sqlQuery.append(" WHERE s.id = t.transaction_state_id AND " +
                "(s.code = 'SETTLED' OR s.code = 'REFUNDED' OR s.code = 'PARTIALLY_REFUNDED' OR s.code = 'AUTHORISED' OR s.code = 'TIMED_OUT' OR s.code = 'REVERSED' " +
                "OR s.code = 'TDS_AUTH_REQUIRED' OR s.code = 'REVERSED' OR s.code = 'TDS_AUTHENTICATED' OR s.code = 'AUTHORISING' OR s.code = 'SETTLING' " +
                "OR s.code = 'AUTHENTICATING' OR s.code = 'REFUNDING' OR s.code = 'PAYMENT_FAILED') AND t.related_transaction_id IS NULL AND CAST(t.initiation_date AS DATE)" +
                " BETWEEN CAST(? AS DATE) and CAST(? AS DATE) AND t.interface_id = a.interface_id ");

        List<Object> queryArgs = new ArrayList<>();
        queryArgs.add(startDate);
        queryArgs.add(endDate);

        if (merchantId != null) {
            sqlQuery.append("AND t.merchant_id = ? ");
            queryArgs.add(merchantId.toString());
        }
        if (applicationId != null) {
            sqlQuery.append("AND a.application_id = ? ");
            queryArgs.add(applicationId.toString());
        }
        if (paymentTypeId != null) {
            sqlQuery.append("AND t.payment_type_id = ? ");
            queryArgs.add(paymentTypeId.toString());
        }
        if (transactionStateId != null) {
            sqlQuery.append("AND t.transaction_state_id = ? ");
            queryArgs.add(transactionStateId.toString());
        }
        if (stringCriteria != null) {
            if (stringCriteria.equals(SearchEnum.MERCHANT_REF.value()) && stringSearch != null) {
                sqlQuery.append("AND t.merchant_reference like ?");
                queryArgs.add("%" + stringSearch + "%");
            } else if (stringCriteria.equals(SearchEnum.CARD_HOLDER.value()) && stringSearch != null) {
                sqlQuery.append("AND t.purchaser_full_name like ?");
                queryArgs.add("%" + stringSearch + "%");
            } else if (stringCriteria.equals(SearchEnum.TRANSACTION_VALUE.value()) && stringSearch != null) {
                sqlQuery.append("AND t.transaction_value like ?");
                queryArgs.add("%" + stringSearch + "%");
            } else if (stringCriteria.equals(SearchEnum.SETTLED_VALUE.value()) && stringSearch != null) {
                sqlQuery.append("AND t.settled_value like ?");
                queryArgs.add("%" + stringSearch + "%");
            } else if (stringCriteria.equals(SearchEnum.REFUNDED_VALUE.value()) && stringSearch != null) {
                sqlQuery.append("AND t.refund_value like ?");
                queryArgs.add("%" + stringSearch + "%");
            } else if (stringCriteria.equals(SearchEnum.AUTHORISED_VALUE.value()) && stringSearch != null) {
                sqlQuery.append("AND t.authorised_value like ?");
                queryArgs.add("%" + stringSearch + "%");
            } else if (stringCriteria.equals(SearchEnum.AUTHORISATION_CODE.value()) && stringSearch != null) {
                sqlQuery.append("AND t.id = c.transaction_id AND c.authorisation_id like ?");
                queryArgs.add("%" + stringSearch + "%");
            } else if (stringCriteria.equals(SearchEnum.TRANSACTION_UID.value()) && stringSearch != null) {
                sqlQuery.append("AND t.transaction_uid like ?");
                queryArgs.add("%" + stringSearch + "%");
            } else if (stringCriteria.equals(SearchEnum.BANK_REFERENCE.value()) && stringSearch != null) {
                sqlQuery.append("AND t.id like ?");
                queryArgs.add("%" + stringSearch + "%");
            } else if (stringCriteria.equals(SearchEnum.CARD_NUMBER.value()) && stringSearch != null) {
                if (stringSearch.length() > 6) {
                    cardBinSearch = stringSearch.substring(0, 6);
                } else {
                    cardBinSearch = null;
                }
                if (stringSearch.length() > 4) {
                    lastFourSearch = stringSearch.substring(stringSearch.length() - 4);
                } else {
                    lastFourSearch = null;
                }
                sqlQuery.append("AND t.id = c.transaction_id AND c.card_bin like ? AND c.card_last_four like ?");
                queryArgs.add("%" + cardBinSearch + "%");
                queryArgs.add("%" + lastFourSearch + "%");
            }
        }
        Object[] preparedStatementArgs = new Object[queryArgs.size()];
        for(int i = 0; i < preparedStatementArgs.length; i++) {
            preparedStatementArgs[i] = queryArgs.get(i);
        }

        GetTotalsInHistoryResponse getTotalsInHistoryResponse = (GetTotalsInHistoryResponse) jdbcTemplate.queryForObject(
                sqlQuery.toString(), preparedStatementArgs,
                new BeanPropertyRowMapper(GetTotalsInHistoryResponse.class));

        if (applicationId != null) {
            String queryC = "select distinct c.symbol from currency c, trading_currency tc, interface i, application_payment_type a where a.application_id = ? AND i.id = a.interface_id AND i.trading_currency_id = tc.id AND tc.currency_id = c.id";

            String symbol = jdbcTemplate.queryForObject(
                    queryC,new Object[] { applicationId },String.class);


            getTotalsInHistoryResponse.setSymbol(symbol);
        } else {
            getTotalsInHistoryResponse.setSymbol("na");
        }



        return getTotalsInHistoryResponse;

    }

    @Override
    public List<GetStateTotalsInHistoryResponse> calculateStateTotalsByMerchantId(Long merchantId, Date startDate) {
        merchantRepository.findById(merchantId).orElseThrow(() -> new MerchantNotFoundException(merchantId));

        StringBuilder sqlQuery = new StringBuilder(
                "SELECT merchant_reference, MAX(t.id) AS id, MAX(t.initiation_date) AS initiation_date " +
                        "INTO #tmpMerchant_reference " +
                        "from [transaction] t " +
                        "where t.merchant_id = ? " +
                        "and t.initiation_date > CAST(? AS DATE) " +
                        "GROUP BY merchant_reference " +
                        "SELECT ts.code, " +
                        "COUNT(*) AS totals " +
                        "from [transaction] t, transaction_state ts, #tmpMerchant_reference tt " +
                        "where t.transaction_state_id = ts.id " +
                        "and t.id = tt.id " +
                        "GROUP BY ts.code");

        List<Object> queryArgs = new ArrayList<>();
        queryArgs.add(merchantId);
        queryArgs.add(startDate);

        Object[] preparedStatementArgs = new Object[queryArgs.size()];
        for(int i = 0; i < preparedStatementArgs.length; i++) {
            preparedStatementArgs[i] = queryArgs.get(i);
        }

        List<GetStateTotalsInHistoryResponse> getStateTotalsInHistoryResponses = jdbcTemplate.query(
                sqlQuery.toString(),
                preparedStatementArgs,
                (rs, rowNum) -> new GetStateTotalsInHistoryResponse(rs.getString("code"), rs.getInt("totals"))
        );

        return getStateTotalsInHistoryResponses;
    }

    @Override
    public List<MerchantEntity> getMerchants() {
        return merchantRepository.findAll();
    }

    @Override
    public List<Long> getMerchantIds(List<String> merchantUids){
        List<Long> merchantIds = new ArrayList<>();

        for (String merchantUid : merchantUids) {
            Optional<MerchantEntity> merchantEntity = merchantRepository.findByMerchantUid(merchantUid);
            merchantEntity.ifPresent(entity -> merchantIds.add(entity.getId()));
        }

        return merchantIds;
    }

    @Override
    public List<Long> getPaymentTypeIds(List<String> paymentTypeCodes){
        List<Long> paymentTypeIds = new ArrayList<>();

        for (String paymentTypeCode : paymentTypeCodes) {
            PaymentTypeEntity paymentTypeEntity = paymentTypeRepository.findByCode(paymentTypeCode);
            paymentTypeIds.add(paymentTypeEntity.getId());
        }

        return paymentTypeIds;
    }

    @Override
    public List<Long> getTransactionStateIds(List<String> transactionStateCodes){
        List<Long> transactionStateIds = new ArrayList<>();

        for (String transactionStateCode : transactionStateCodes) {
            TransactionStateEntity transactionStateEntity = transactionStateRepository.findByCode(transactionStateCode).orElseThrow(() -> new GenericException("Could not find transaction state code", HttpStatus.NOT_FOUND, "No transaction state found for code | " + transactionStateCode));
            transactionStateIds.add(transactionStateEntity.getId());
        }

        return transactionStateIds;
    }

    @Override
    public List<GetTransactionLegResponse> getTransactionLegs(long transactionId) {
        TransactionEntity transactionEntity = transactionRepository.findById(transactionId).orElseThrow(() -> new GenericException("Transaction not found", HttpStatus.NOT_FOUND, "No transaction with id | " + transactionId + " exists"));
        if (transactionEntity == null) {
            throw new NotFoundException("Transaction not found while retrieving transaction legs");
        }
        List<TransactionLegEntity> transactionLegEntities = transactionLegRepository.findTransactionLegEntitiesByTransactionByTransactionId(transactionEntity);
        List<GetTransactionLegResponse> getTransactionLegResponses = new ArrayList<>();
        for (TransactionLegEntity transactionLegEntity : transactionLegEntities) {
            logger.info("TRANSACTION_LEG_DATE_LOGGED " + transactionLegEntity.getDateLogged());
            GetTransactionLegResponse getTransactionLegResponse = new GetTransactionLegResponse();
            getTransactionLegResponse.setId(transactionLegEntity.getId());
            getTransactionLegResponse.setDateLogged(transactionLegEntity.getDateLogged());
            getTransactionLegResponse.setTransactionValue(transactionLegEntity.getTransactionValue());
            getTransactionLegResponse.setTransactionID(transactionLegEntity.getTransactionByTransactionId().getId());
            getTransactionLegResponse.setTransactionActionName(transactionLegEntity.getTransactionActionByTransactionActionId().getName());
            logger.info("GET_LEG_DATE_LOGGED " + getTransactionLegResponse.getDateLogged());
            getTransactionLegResponses.add(getTransactionLegResponse);

        }
        return getTransactionLegResponses;
    }

    @Override
    public List<InterfaceEntity> getInterfacesByApplicationId(List<String> applicationUids) throws NotFoundException {
        List<InterfaceEntity> interfaces = new ArrayList<>();

        for (String applicationUid : applicationUids) {
            Optional<ApplicationEntity> applicationEntity = applicationRepository.findByApplicationUid(applicationUid);
            applicationEntity.ifPresent(applicationEntity1 -> {
                List<ApplicationPaymentTypeEntity> applicationPaymentTypeEntities = applicationPaymentTypeRepository.findByApplicationId(applicationEntity1.getId());
                for ( ApplicationPaymentTypeEntity applicationPaymentTypeEntity : applicationPaymentTypeEntities ) {
                    interfaces.add(interfaceRepository.getOne(applicationPaymentTypeEntity.getInterfaceId()));
                }
            });
        }

        return interfaces;
    }

    @Override
    public List<Long> getCardTransactionByCardBinAndLastFour(String cardBin, String lastFour, Date startDate, Date endDate) throws NotFoundException {
        try {
            String queryI = "select * from card_transaction i, dbo.[transaction] t where i.card_bin = ? AND i.card_last_four = ? AND t.id = i.transaction_id " +
                    "AND CAST(t.initiation_date AS DATE) BETWEEN CAST(? AS DATE) and CAST(? AS DATE) ";
            List<Object> queryArgs = new ArrayList<>();
            queryArgs.add(cardBin);
            queryArgs.add(lastFour);
            queryArgs.add(startDate);
            queryArgs.add(endDate);
            Object[] preparedStatementArgs = new Object[queryArgs.size()];
            for(int i = 0; i < preparedStatementArgs.length; i++) {
                preparedStatementArgs[i] = queryArgs.get(i);
            }
            List<CardTransactionEntity> cardTransactionEntities = jdbcTemplate.query(
                    queryI.toString(), preparedStatementArgs,
                    new BeanPropertyRowMapper(CardTransactionEntity.class));
            List<Long> transactionIds = new ArrayList<>();
            for (CardTransactionEntity cardTransactionEntity : cardTransactionEntities) {
                transactionIds.add(cardTransactionEntity.getTransactionId());
            }
            return transactionIds;
        } catch (Exception e) {
            List<Long> transactionIds = new ArrayList<>();
            return transactionIds;
        }

    }

    @Override
    public GetCardTransactionResponse getCardTransactionByAuthorisationId(String authorisationId, Date startDate, Date endDate) throws NotFoundException {
        try {
            String queryI = "select distinct i.transaction_id from card_transaction i, dbo.[transaction] t where i.authorisation_id = ? AND t.id = i.transaction_id " +
                    "AND CAST(t.initiation_date AS DATE) BETWEEN CAST(? AS DATE) and CAST(? AS DATE) ";
            List<Object> queryArgs = new ArrayList<>();
            queryArgs.add(authorisationId);
            queryArgs.add(startDate);
            queryArgs.add(endDate);
            Object[] preparedStatementArgs = new Object[queryArgs.size()];
            for(int i = 0; i < preparedStatementArgs.length; i++) {
                preparedStatementArgs[i] = queryArgs.get(i);
            }
            GetCardTransactionResponse getCardTransactionResponse = (GetCardTransactionResponse) jdbcTemplate.queryForObject(
                    queryI.toString(), preparedStatementArgs,
                    new BeanPropertyRowMapper(GetCardTransactionResponse.class));
            return getCardTransactionResponse;
        } catch (Exception e) {
            GetCardTransactionResponse getCardTransactionResponse = new GetCardTransactionResponse();
            getCardTransactionResponse.setTransactionId(null);
            return getCardTransactionResponse;
        }
    }

    @Override
    public List<GetTransactionLegDetailResponse> getTransactionLegDetail(long transactionLegId) {
        logger.info("Transaction leg ID received: " + String.valueOf(transactionLegId));
        TransactionLegEntity transactionLegEntity = transactionLegRepository.findById(transactionLegId).orElseThrow(() -> new GenericException("Transaction leg not found", HttpStatus.NOT_FOUND, "No transaction leg with id | " + transactionLegId + " exists"));
        List<GetTransactionLegDetailResponse> responses = new ArrayList<>();
        switch (transactionLegEntity.getTransactionActionByTransactionActionId().getCode()) {
            case "MOBICRED_INITIATE":
                logger.info("Mobicred Initiate leg found");
                List<MobicredPurchseCreateEntity> mobicredPurchseCreateEntities =
                        mobicredPurchseCreateRepository.findMobicredPurchaseCreateEntitiesByTransactionLegByTransactionLegId(transactionLegEntity);
                for (MobicredPurchseCreateEntity mobicredPurchseCreateEntity : mobicredPurchseCreateEntities) {
                    responses.add(new GetTransactionLegDetailResponse("Payment Creation Merchant Reference", mobicredPurchseCreateEntity.getPaymentCreationMerchantReference()));
                    responses.add(new GetTransactionLegDetailResponse("Payment Date Time", mobicredPurchseCreateEntity.getPaymentDateTime()));
                    responses.add(new GetTransactionLegDetailResponse("Payment Instruction Response Code", mobicredPurchseCreateEntity.getPaymentInstructionResponseCode().toString()));
                    responses.add(new GetTransactionLegDetailResponse("Payment Creation Status", mobicredPurchseCreateEntity.getPaymentCreationStatus()));
                    responses.add(new GetTransactionLegDetailResponse("Payment Creation Reason", mobicredPurchseCreateEntity.getPaymentCreationReason()));
                    responses.add(new GetTransactionLegDetailResponse("Mobicred Request UID", mobicredPurchseCreateEntity.getMobicredRequestUid()));
                    responses.add(new GetTransactionLegDetailResponse("", ""));

                }
            break;
            case "MOBICRED_VALIDATE":
                logger.info("Mobicred Validate leg found");
                MobicredValidateEntity mobicredValidateEntity = mobicredValidateRepository.findMobicredValidateEntityByTransactionLegByTransactionLegId(transactionLegEntity);
                responses.add(new GetTransactionLegDetailResponse("Payment Date Time", mobicredValidateEntity.getPaymentDateTime()));
                responses.add(new GetTransactionLegDetailResponse("Payment Instruction Response Code", mobicredValidateEntity.getPaymentInstructionResponseCode().toString()));
                responses.add(new GetTransactionLegDetailResponse("Payment Creation Status", mobicredValidateEntity.getPaymentCreationStatus()));
                responses.add(new GetTransactionLegDetailResponse("Payment Creation Reason", mobicredValidateEntity.getPaymentCreationReason()));
                responses.add(new GetTransactionLegDetailResponse("Mobicred Request UID", mobicredValidateEntity.getMobicredRequestUid()));
                break;
            case "MOBICRED_OTP_REQUEST":
                logger.info("Mobicred OTP Request leg found");
                MobicredOtpEntity mobicredOtpEntity = mobicredOtpRepository.findMobicredOtpEntityByTransactionLegByTransactionLegId(transactionLegEntity);
                responses.add(new GetTransactionLegDetailResponse("Payment Date Time", mobicredOtpEntity.getPaymentDateTime()));
                responses.add(new GetTransactionLegDetailResponse("Payment Instruction Response Code", mobicredOtpEntity.getPaymentInstructionResponseCode().toString()));
                responses.add(new GetTransactionLegDetailResponse("Payment Creation Status", mobicredOtpEntity.getPaymentCreationStatus()));
                responses.add(new GetTransactionLegDetailResponse("Payment Creation Reason", mobicredOtpEntity.getPaymentCreationReason()));
                responses.add(new GetTransactionLegDetailResponse("Mobicred Request UID", mobicredOtpEntity.getMobicredRequestUid()));
            break;
            case "SECURE_EFT_PAYMENT_KEY":
                logger.info("EFT Payment Key leg found");
                SecureEftPaymentKeyEntity secureEftPaymentKeyEntity = secureEftPaymentKeyRepository.findSecureEftPaymentKeyEntityByTransactionLegByTransactionLegId(transactionLegEntity);
                responses.add(new GetTransactionLegDetailResponse("Payment Key", secureEftPaymentKeyEntity.getPaymentKey()));
                responses.add(new GetTransactionLegDetailResponse("URL", secureEftPaymentKeyEntity.getUrl()));
            break;
            case "SECURE_EFT_STATUS":
                logger.info("EFT Status leg found");
                SecureEftStatusEntity secureEftStatusEntity = secureEftStatusRepository.findSecureEftStatusEntityByTransactionLegByTransactionLegId(transactionLegEntity);
                responses.add(new GetTransactionLegDetailResponse("CallPay ID", secureEftStatusEntity.getCallpayId().toString()));
                responses.add(new GetTransactionLegDetailResponse("Successful", secureEftStatusEntity.getSuccessful().toString()));
                responses.add(new GetTransactionLegDetailResponse("Status", secureEftStatusEntity.getStatus()));
                responses.add(new GetTransactionLegDetailResponse("Amount", secureEftStatusEntity.getAmount().toString()));
                responses.add(new GetTransactionLegDetailResponse("Reason", secureEftStatusEntity.getReason()));
                responses.add(new GetTransactionLegDetailResponse("Currency", secureEftStatusEntity.getCurrency()));
                responses.add(new GetTransactionLegDetailResponse("Merchant Reference", secureEftStatusEntity.getMerchantReference()));
                responses.add(new GetTransactionLegDetailResponse("Gateway Reference", secureEftStatusEntity.getGatewayReference()));
                responses.add(new GetTransactionLegDetailResponse("Payment Key", secureEftStatusEntity.getPaymentKey()));
                responses.add(new GetTransactionLegDetailResponse("Created", secureEftStatusEntity.getCreated()));
                responses.add(new GetTransactionLegDetailResponse("Display Amount", secureEftStatusEntity.getDisplayAmount()));
                responses.add(new GetTransactionLegDetailResponse("Refunded Amount", secureEftStatusEntity.getRefundedAmount().toString()));
                responses.add(new GetTransactionLegDetailResponse("Refunded", secureEftStatusEntity.getRefunded().toString()));
                responses.add(new GetTransactionLegDetailResponse("Service", secureEftStatusEntity.getService()));
                responses.add(new GetTransactionLegDetailResponse("Gateway", secureEftStatusEntity.getGateway()));
                responses.add(new GetTransactionLegDetailResponse("Customer Account", secureEftStatusEntity.getCustomerAccount()));
                responses.add(new GetTransactionLegDetailResponse("Customer Back", secureEftStatusEntity.getCustomerBank()));
                responses.add(new GetTransactionLegDetailResponse("Is Demo Transaction", secureEftStatusEntity.getIsDemoTransaction().toString()));
            break;
            case "ZAPPER_UPDATE":
                logger.info("Zapper update leg found");
                Optional<ZapperProviderUpdateEntity> zapperProviderUpdateEntityOptional = zapperProviderUpdateRepository.findByTransactionLegId(transactionLegEntity.getId());
                if (zapperProviderUpdateEntityOptional.isPresent()) {
                    ZapperProviderUpdateEntity zapperProviderUpdateEntity = zapperProviderUpdateEntityOptional.get();
                    responses.add(new GetTransactionLegDetailResponse("Status", String.valueOf(zapperProviderUpdateEntity.getStatus())));
                    responses.add(new GetTransactionLegDetailResponse("Currency ISO Code", zapperProviderUpdateEntity.getCurrencyIsoCode()));
                    responses.add(new GetTransactionLegDetailResponse("Customer First Name", zapperProviderUpdateEntity.getCustomerFirstName()));
                    responses.add(new GetTransactionLegDetailResponse("Customer Last Name", zapperProviderUpdateEntity.getCustomerLastName()));
                    responses.add(new GetTransactionLegDetailResponse("Invoiced Amount", String.valueOf(zapperProviderUpdateEntity.getInvoicedAmount())));
                    responses.add(new GetTransactionLegDetailResponse("Payment Reference", zapperProviderUpdateEntity.getPaymentReference()));
                    responses.add(new GetTransactionLegDetailResponse("Payment UTC Date", zapperProviderUpdateEntity.getPaymentUtcDate()));
                    responses.add(new GetTransactionLegDetailResponse("Invoice External Reference", zapperProviderUpdateEntity.getInvoiceExternalReference()));
                    responses.add(new GetTransactionLegDetailResponse("Invoice Reference", zapperProviderUpdateEntity.getInvoiceReference()));
                    responses.add(new GetTransactionLegDetailResponse("Tendered Amount", String.valueOf(zapperProviderUpdateEntity.getTenderedAmount())));
                    responses.add(new GetTransactionLegDetailResponse("Tip Amount", String.valueOf(zapperProviderUpdateEntity.getTipAmount())));
                }
                break;
            case "ZAPPER_CLIENT_UPDATE":
                logger.info("Zapper client update leg found");
                Optional<ZapperClientUpdateEntity> zapperClientUpdateEntityOptional = zapperClientUpdateRepository.findByTransactionLegId(transactionLegEntity.getId());
                if (zapperClientUpdateEntityOptional.isPresent()) {
                    ZapperClientUpdateEntity zapperClientUpdateEntity = zapperClientUpdateEntityOptional.get();
                    responses.add(new GetTransactionLegDetailResponse("Status", String.valueOf(zapperClientUpdateEntity.getStatus())));
                    responses.add(new GetTransactionLegDetailResponse("Paid Amount", String.valueOf(zapperClientUpdateEntity.getPaidAmount())));
                    responses.add(new GetTransactionLegDetailResponse("Zapper ID", zapperClientUpdateEntity.getZapperId()));
                    responses.add(new GetTransactionLegDetailResponse("Reference", zapperClientUpdateEntity.getReference()));
                }
                break;
            case "MASTER_PASS_INITIATE":
                logger.info("Mobicred Initiate leg found");
                MasterPassTxEntity masterPassTxEntity = masterPassTxRepository.findMasterPassTxEntityByTransactionLegByTransactionLegId(transactionLegEntity);
                responses.add(new GetTransactionLegDetailResponse("Callback URL", masterPassTxEntity.getCallbackUrl()));
                responses.add(new GetTransactionLegDetailResponse("Amount", masterPassTxEntity.getAmount().toString()));
                responses.add(new GetTransactionLegDetailResponse("Reference", masterPassTxEntity.getReference()));
                responses.add(new GetTransactionLegDetailResponse("Description", masterPassTxEntity.getDescription()));
                responses.add(new GetTransactionLegDetailResponse("Response Reference", masterPassTxEntity.getResponseReference()));
                responses.add(new GetTransactionLegDetailResponse("Redirect Url", masterPassTxEntity.getRedirectUrl()));
                responses.add(new GetTransactionLegDetailResponse("Status", masterPassTxEntity.getStatus()));
                responses.add(new GetTransactionLegDetailResponse("Last Modified", masterPassTxEntity.getLastModified().toString()));
            break;
            case "CARD_CARDINAL_LOOKUP":
                logger.info("Cardinal Lookup leg found");
                CardinalLookupRequestEntity cardinalLookupRequestEntity = cardinalLookupRequestRepository.findByTransactionLegId(transactionLegEntity.getId());
                CardinalLookupResponseEntity cardinalLookupResponseEntity = cardinalLookupResponseRepository.findByTransactionLegId(transactionLegEntity.getId());
                responses.add(new GetTransactionLegDetailResponse("Request", ""));
                responses.add(new GetTransactionLegDetailResponse("Amount", String.valueOf(cardinalLookupRequestEntity.getAmount())));
                responses.add(new GetTransactionLegDetailResponse("Billing Address 1", cardinalLookupRequestEntity.getBillingAddress1()));
                responses.add(new GetTransactionLegDetailResponse("Billing City", cardinalLookupRequestEntity.getBillingCity()));
                responses.add(new GetTransactionLegDetailResponse("Billing Country Code", cardinalLookupRequestEntity.getBillingCountryCode()));
                responses.add(new GetTransactionLegDetailResponse("Billing First Name", cardinalLookupRequestEntity.getBillingFirstName()));
                responses.add(new GetTransactionLegDetailResponse("Billing Last Name", cardinalLookupRequestEntity.getBillingLastName()));
                responses.add(new GetTransactionLegDetailResponse("Billing Postal Code", cardinalLookupRequestEntity.getBillingPostalCode()));
                responses.add(new GetTransactionLegDetailResponse("Card Expiry Month", cardinalLookupRequestEntity.getCardExpMonth()));
                responses.add(new GetTransactionLegDetailResponse("Card Expiry Year", cardinalLookupRequestEntity.getCardExpYear()));
                responses.add(new GetTransactionLegDetailResponse("Card Number", cardinalLookupRequestEntity.getCardNumber()));
                responses.add(new GetTransactionLegDetailResponse("Currency Code", cardinalLookupRequestEntity.getCurrencyCode()));
                responses.add(new GetTransactionLegDetailResponse("DF Reference ID", cardinalLookupRequestEntity.getDfReferenceId()));
                responses.add(new GetTransactionLegDetailResponse("Email", cardinalLookupRequestEntity.getEmail()));
                responses.add(new GetTransactionLegDetailResponse("Mobile Phone", cardinalLookupRequestEntity.getMobilePhone()));
                responses.add(new GetTransactionLegDetailResponse("Version", cardinalLookupRequestEntity.getVersion()));
                responses.add(new GetTransactionLegDetailResponse("Transaction Mode", cardinalLookupRequestEntity.getTransactionMode()));
                responses.add(new GetTransactionLegDetailResponse("Message Type", cardinalLookupRequestEntity.getMsgType()));
                responses.add(new GetTransactionLegDetailResponse("Transaction Type", cardinalLookupRequestEntity.getTransactionType()));
                responses.add(new GetTransactionLegDetailResponse("Order Number", cardinalLookupRequestEntity.getOrderNumber()));
                responses.add(new GetTransactionLegDetailResponse("Is Browser Javascript Enabled", String.valueOf(cardinalLookupRequestEntity.isBrowserJavascriptEnabled())));
                responses.add(new GetTransactionLegDetailResponse("Algorithm", cardinalLookupRequestEntity.getAlgorithm()));
                responses.add(new GetTransactionLegDetailResponse("Identifier", cardinalLookupRequestEntity.getIdentifier()));
                responses.add(new GetTransactionLegDetailResponse("Org Unit", cardinalLookupRequestEntity.getOrgUnit()));
                responses.add(new GetTransactionLegDetailResponse("Signature", cardinalLookupRequestEntity.getSignature()));
                responses.add(new GetTransactionLegDetailResponse("Date Logged", cardinalLookupRequestEntity.getDateLogged()));
                responses.add(new GetTransactionLegDetailResponse("", ""));
                responses.add(new GetTransactionLegDetailResponse("Response", ""));
                responses.add(new GetTransactionLegDetailResponse("Three DS Version", cardinalLookupResponseEntity.getThreeDsVersion()));
                responses.add(new GetTransactionLegDetailResponse("Enrolled", cardinalLookupResponseEntity.getEnrolled()));
                responses.add(new GetTransactionLegDetailResponse("Error Description", cardinalLookupResponseEntity.getErrorDesc()));
                responses.add(new GetTransactionLegDetailResponse("Error Number", cardinalLookupResponseEntity.getErrorNo()));
                responses.add(new GetTransactionLegDetailResponse("Eci Flag", cardinalLookupResponseEntity.getEciFlag()));
                responses.add(new GetTransactionLegDetailResponse("Order ID", cardinalLookupResponseEntity.getOrderId()));
                responses.add(new GetTransactionLegDetailResponse("Transaction ID", cardinalLookupResponseEntity.getTransactionId()));
                responses.add(new GetTransactionLegDetailResponse("Transaction Type", cardinalLookupResponseEntity.getTransactionType()));
                responses.add(new GetTransactionLegDetailResponse("Signature Verification", cardinalLookupResponseEntity.getSignatureVerification()));
                responses.add(new GetTransactionLegDetailResponse("Card Brand", cardinalLookupResponseEntity.getCardBrand()));
                responses.add(new GetTransactionLegDetailResponse("Card BIN", cardinalLookupResponseEntity.getCardBin()));
                responses.add(new GetTransactionLegDetailResponse("DS Transaction ID", cardinalLookupResponseEntity.getDsTransactionId()));
                responses.add(new GetTransactionLegDetailResponse("Raw ACS URL", cardinalLookupResponseEntity.getRawAcsUrl()));
                responses.add(new GetTransactionLegDetailResponse("ACS URL", cardinalLookupResponseEntity.getAcsUrl()));
                responses.add(new GetTransactionLegDetailResponse("Step Up URL", cardinalLookupResponseEntity.getStepUpUrl()));
                responses.add(new GetTransactionLegDetailResponse("CAVV", cardinalLookupResponseEntity.getCavv()));
                responses.add(new GetTransactionLegDetailResponse("PA Res Status", cardinalLookupResponseEntity.getPaResStatus()));
                responses.add(new GetTransactionLegDetailResponse("Payload", cardinalLookupResponseEntity.getPayload()));
                responses.add(new GetTransactionLegDetailResponse("XID", cardinalLookupResponseEntity.getXid()));
                responses.add(new GetTransactionLegDetailResponse("CAVV Algorithm", cardinalLookupResponseEntity.getCavvAlgorithm()));
                responses.add(new GetTransactionLegDetailResponse("Merchant Reference Number", cardinalLookupResponseEntity.getMerchantReferenceNumber()));
                responses.add(new GetTransactionLegDetailResponse("UCAF Indicator", cardinalLookupResponseEntity.getUcafIndicator()));
                responses.add(new GetTransactionLegDetailResponse("Decoupled Indicator", cardinalLookupResponseEntity.getDecoupledIndicator()));
                responses.add(new GetTransactionLegDetailResponse("Reason Code", cardinalLookupResponseEntity.getReasonCode()));
                responses.add(new GetTransactionLegDetailResponse("Reason Description", cardinalLookupResponseEntity.getReasonDesc()));
                responses.add(new GetTransactionLegDetailResponse("Warning", cardinalLookupResponseEntity.getWarning()));
                responses.add(new GetTransactionLegDetailResponse("Card Holder Info", cardinalLookupResponseEntity.getCardHolderInfo()));
                responses.add(new GetTransactionLegDetailResponse("ACS Rendering Type", cardinalLookupResponseEntity.getAcsRenderingType()));
                responses.add(new GetTransactionLegDetailResponse("Authentication Type", cardinalLookupResponseEntity.getAuthenticationType()));
                responses.add(new GetTransactionLegDetailResponse("Challenge Required", cardinalLookupResponseEntity.getChallengeRequired()));
                responses.add(new GetTransactionLegDetailResponse("Status Reason", cardinalLookupResponseEntity.getStatusReason()));
                responses.add(new GetTransactionLegDetailResponse("ACS Transaction ID", cardinalLookupResponseEntity.getAcsTransactionId()));
                responses.add(new GetTransactionLegDetailResponse("Three DS Server Transaction ID", cardinalLookupResponseEntity.getThreeDsServerTransactionId()));
                responses.add(new GetTransactionLegDetailResponse("SDK Flow Type", cardinalLookupResponseEntity.getSdkFlowType()));
                responses.add(new GetTransactionLegDetailResponse("Third Party Token", cardinalLookupResponseEntity.getThirdPartyToken()));
                responses.add(new GetTransactionLegDetailResponse("Token", cardinalLookupResponseEntity.getToken()));
                responses.add(new GetTransactionLegDetailResponse("White List Status", cardinalLookupResponseEntity.getWhiteListStatus()));
                responses.add(new GetTransactionLegDetailResponse("White List Status Source", cardinalLookupResponseEntity.getWhiteListStatusSource()));
                responses.add(new GetTransactionLegDetailResponse("Network Score", cardinalLookupResponseEntity.getNetworkScore()));
                responses.add(new GetTransactionLegDetailResponse("IDCI Reason Code 1", cardinalLookupResponseEntity.getIdciReasonCode1()));
                responses.add(new GetTransactionLegDetailResponse("IDCI Reason Code 2", cardinalLookupResponseEntity.getIdciReasonCode2()));
                responses.add(new GetTransactionLegDetailResponse("Authorization Payload", cardinalLookupResponseEntity.getAuthorizationPayload()));
                responses.add(new GetTransactionLegDetailResponse("IVR Enabled Message", cardinalLookupResponseEntity.getIvrEnabledMessage().toString()));
                responses.add(new GetTransactionLegDetailResponse("IVR Encryption Key", cardinalLookupResponseEntity.getIvrEncryptionKey()));
                responses.add(new GetTransactionLegDetailResponse("IVR Encryption Mandatory", cardinalLookupResponseEntity.getIvrEncryptionMandatory().toString()));
                responses.add(new GetTransactionLegDetailResponse("IVR Encryption Type", cardinalLookupResponseEntity.getIvrEncryptionType()));
                responses.add(new GetTransactionLegDetailResponse("IVR Label", cardinalLookupResponseEntity.getIvrLabel()));
                responses.add(new GetTransactionLegDetailResponse("IVR Prompt", cardinalLookupResponseEntity.getIvrPrompt()));
                responses.add(new GetTransactionLegDetailResponse("IVR Status Message", cardinalLookupResponseEntity.getIvrStatusMessage()));
                responses.add(new GetTransactionLegDetailResponse("IDCI Score", cardinalLookupResponseEntity.getIdciScore()));
                responses.add(new GetTransactionLegDetailResponse("IDCI Decision", cardinalLookupResponseEntity.getIdciDecision()));
            break;
            case "CARD_CARDINAL_AUTH":
                logger.info("Cardinal Auth leg found");
                ThreeDsTransactionEntity threeDsTransactionEntity = threeDsTransactionRepository.findByTransactionByTransactionId(transactionLegEntity.getTransactionByTransactionId());
                CardinalAuthEntity cardinalAuthEntity = cardinalAuthRepository.findByThreeDsTransactionId(threeDsTransactionEntity.getId());
                responses.add(new GetTransactionLegDetailResponse("Response", ""));
                responses.add(new GetTransactionLegDetailResponse("Action Code", cardinalAuthEntity.getActionCode()));
                responses.add(new GetTransactionLegDetailResponse("Authorize Account", cardinalAuthEntity.getAuthorizeAccount().toString()));
                responses.add(new GetTransactionLegDetailResponse("Processor Order ID", cardinalAuthEntity.getProcessorOrderId()));
                responses.add(new GetTransactionLegDetailResponse("Processor Transaction ID", cardinalAuthEntity.getProcessorTransactionId()));
                responses.add(new GetTransactionLegDetailResponse("Reason Code", cardinalAuthEntity.getReasonCode()));
                responses.add(new GetTransactionLegDetailResponse("Reason Description", cardinalAuthEntity.getReasonDescription()));
                responses.add(new GetTransactionLegDetailResponse("Email 1", cardinalAuthEntity.getEmail1()));
                responses.add(new GetTransactionLegDetailResponse("Email 2", cardinalAuthEntity.getEmail2()));
                responses.add(new GetTransactionLegDetailResponse("Shipping Full Name", cardinalAuthEntity.getShippingFullName()));
                responses.add(new GetTransactionLegDetailResponse("Shipping First Name", cardinalAuthEntity.getShippingFirstName()));
                responses.add(new GetTransactionLegDetailResponse("Shipping Middle Name", cardinalAuthEntity.getShippingMiddleName()));
                responses.add(new GetTransactionLegDetailResponse("Shipping Last Name", cardinalAuthEntity.getShippingLastName()));
                responses.add(new GetTransactionLegDetailResponse("Shipping Address 1", cardinalAuthEntity.getShippingAddress1()));
                responses.add(new GetTransactionLegDetailResponse("Shipping Address 2", cardinalAuthEntity.getShippingAddress2()));
                responses.add(new GetTransactionLegDetailResponse("Shipping Address 3", cardinalAuthEntity.getShippingAddress3()));
                responses.add(new GetTransactionLegDetailResponse("Shipping City", cardinalAuthEntity.getShippingCity()));
                responses.add(new GetTransactionLegDetailResponse("Shipping State", cardinalAuthEntity.getShippingState()));
                responses.add(new GetTransactionLegDetailResponse("Shipping Postal Code", cardinalAuthEntity.getShippingPostalCode()));
                responses.add(new GetTransactionLegDetailResponse("Shipping Country Code", cardinalAuthEntity.getShippingCountryCode()));
                responses.add(new GetTransactionLegDetailResponse("Shipping Phone 1", cardinalAuthEntity.getShippingPhone1()));
                responses.add(new GetTransactionLegDetailResponse("Shipping Phone 2", cardinalAuthEntity.getShippingPhone2()));
                responses.add(new GetTransactionLegDetailResponse("Billing Full Name", cardinalAuthEntity.getBillingFullName()));
                responses.add(new GetTransactionLegDetailResponse("Billing First Name", cardinalAuthEntity.getBillingFirstName()));
                responses.add(new GetTransactionLegDetailResponse("Billing Middle Name", cardinalAuthEntity.getBillingMiddleName()));
                responses.add(new GetTransactionLegDetailResponse("Billing Last Name", cardinalAuthEntity.getBillingLastName()));
                responses.add(new GetTransactionLegDetailResponse("Billing Address 1", cardinalAuthEntity.getBillingAddress1()));
                responses.add(new GetTransactionLegDetailResponse("Billing Address 2", cardinalAuthEntity.getBillingAddress2()));
                responses.add(new GetTransactionLegDetailResponse("Billing Address 3", cardinalAuthEntity.getBillingAddress3()));
                responses.add(new GetTransactionLegDetailResponse("Billing City", cardinalAuthEntity.getBillingCity()));
                responses.add(new GetTransactionLegDetailResponse("Billing State", cardinalAuthEntity.getBillingState()));
                responses.add(new GetTransactionLegDetailResponse("Billing Postal Code", cardinalAuthEntity.getBillingPostalCode()));
                responses.add(new GetTransactionLegDetailResponse("Billing Country Code", cardinalAuthEntity.getBillingCountryCode()));
                responses.add(new GetTransactionLegDetailResponse("Billing Phone 1", cardinalAuthEntity.getBillingPhone1()));
                responses.add(new GetTransactionLegDetailResponse("Billing Phone 2", cardinalAuthEntity.getBillingPhone2()));
                responses.add(new GetTransactionLegDetailResponse("Account Number", cardinalAuthEntity.getAccountNumber()));
                responses.add(new GetTransactionLegDetailResponse("Expiration Month", cardinalAuthEntity.getExpirationMonth()));
                responses.add(new GetTransactionLegDetailResponse("Expiration Year", cardinalAuthEntity.getExpirationYear()));
                responses.add(new GetTransactionLegDetailResponse("Name On Account", cardinalAuthEntity.getNameOnAccount()));
                responses.add(new GetTransactionLegDetailResponse("Card Code", cardinalAuthEntity.getCardCode()));
                responses.add(new GetTransactionLegDetailResponse("Error Number", cardinalAuthEntity.getErrorNumber()));
                responses.add(new GetTransactionLegDetailResponse("Error Description", cardinalAuthEntity.getErrorDescription()));
                responses.add(new GetTransactionLegDetailResponse("Order ID", cardinalAuthEntity.getOrderId()));
                responses.add(new GetTransactionLegDetailResponse("Order Number", cardinalAuthEntity.getOrderNumber()));
                responses.add(new GetTransactionLegDetailResponse("Type", cardinalAuthEntity.getType()));
                responses.add(new GetTransactionLegDetailResponse("Payment Reason Description", cardinalAuthEntity.getPaymentReasonDescription()));
                responses.add(new GetTransactionLegDetailResponse("Enrolled", cardinalAuthEntity.getEnrolled()));
                responses.add(new GetTransactionLegDetailResponse("CAVV", cardinalAuthEntity.getCavv()));
                responses.add(new GetTransactionLegDetailResponse("Eci Flag", cardinalAuthEntity.getEciFlag()));
                responses.add(new GetTransactionLegDetailResponse("PA Res Status", cardinalAuthEntity.getPaResStatus()));
                responses.add(new GetTransactionLegDetailResponse("Signature Verification", cardinalAuthEntity.getSignatureVerification()));
                responses.add(new GetTransactionLegDetailResponse("XID", cardinalAuthEntity.getXid()));
                responses.add(new GetTransactionLegDetailResponse("UCAF Indicator", cardinalAuthEntity.getUcafIndicator()));
                responses.add(new GetTransactionLegDetailResponse("ACS Transaction ID", cardinalAuthEntity.getAcsTransactionId()));
                responses.add(new GetTransactionLegDetailResponse("Three DS Server Transaction ID", cardinalAuthEntity.getThreeDsServerTransactionId()));
                responses.add(new GetTransactionLegDetailResponse("DS Transaction ID", cardinalAuthEntity.getDsTransactionId()));
                responses.add(new GetTransactionLegDetailResponse("Authorization Payload", cardinalAuthEntity.getAuthorizationPayload()));
                responses.add(new GetTransactionLegDetailResponse("CAVV Algorithm", cardinalAuthEntity.getCavvAlgorithm()));
                responses.add(new GetTransactionLegDetailResponse("Challenge Cancel", cardinalAuthEntity.getChallengeCancel()));
                break;
            case "CARD_TDS_LOOKUP":
                logger.info("Card TDS Lookup leg found");
                BankservLookupRequestEntity bankservLookupRequestEntity = bankservLookupRequestRepository.findBankservLookupRequestEntityByTransactionLegByTransactionLegId(transactionLegEntity);
                BankservLookupResponseEntity bankservLookupResponseEntity = bankservLookupResponseRepository.findBankservLookupResponseEntityByTransactionLegByTransactionLegId(transactionLegEntity);
                responses.add(new GetTransactionLegDetailResponse("Request", ""));
                responses.add(new GetTransactionLegDetailResponse("Date Logged", bankservLookupRequestEntity.getDateLogged().toString()));
                responses.add(new GetTransactionLegDetailResponse("Message Type", bankservLookupRequestEntity.getMsgType()));
                responses.add(new GetTransactionLegDetailResponse("Version", bankservLookupRequestEntity.getVersion()));
                responses.add(new GetTransactionLegDetailResponse("Processor ID", bankservLookupRequestEntity.getProcessorId()));
                responses.add(new GetTransactionLegDetailResponse("Merchant ID", bankservLookupRequestEntity.getMerchantId()));
                responses.add(new GetTransactionLegDetailResponse("Transaction Password", bankservLookupRequestEntity.getTransactionPwd()));
                responses.add(new GetTransactionLegDetailResponse("Transaction Type", bankservLookupRequestEntity.getTransactionType()));
                responses.add(new GetTransactionLegDetailResponse("Amount", String.valueOf(bankservLookupRequestEntity.getAmount())));
                responses.add(new GetTransactionLegDetailResponse("Currency Code", bankservLookupRequestEntity.getCurrencyCode()));
                responses.add(new GetTransactionLegDetailResponse("Card Number", bankservLookupRequestEntity.getCardNumber()));
                responses.add(new GetTransactionLegDetailResponse("Card Expiry Month", bankservLookupRequestEntity.getCardExpMonth()));
                responses.add(new GetTransactionLegDetailResponse("Card Expiry Year", bankservLookupRequestEntity.getCardExpYear()));
                responses.add(new GetTransactionLegDetailResponse("User Agent", bankservLookupRequestEntity.getUserAgent()));
                responses.add(new GetTransactionLegDetailResponse("Browser Header", bankservLookupRequestEntity.getBrowserHeader()));
                responses.add(new GetTransactionLegDetailResponse("IP Address", bankservLookupRequestEntity.getIpAddress()));
                responses.add(new GetTransactionLegDetailResponse("Order Number", bankservLookupRequestEntity.getOrderNumber()));
                responses.add(new GetTransactionLegDetailResponse("Order Description", bankservLookupRequestEntity.getOrderDescription()));
                responses.add(new GetTransactionLegDetailResponse("", ""));
                responses.add(new GetTransactionLegDetailResponse("Response", ""));
                responses.add(new GetTransactionLegDetailResponse("Date Logged", bankservLookupResponseEntity.getDateLogged().toString()));
                responses.add(new GetTransactionLegDetailResponse("Error Description", bankservLookupResponseEntity.getErrorDesc()));
                responses.add(new GetTransactionLegDetailResponse("Error Number", bankservLookupResponseEntity.getErrorNo()));
                responses.add(new GetTransactionLegDetailResponse("Transaction ID", bankservLookupResponseEntity.getTransactionId()));
                responses.add(new GetTransactionLegDetailResponse("Payload", bankservLookupResponseEntity.getPayload()));
                responses.add(new GetTransactionLegDetailResponse("Enrolled", bankservLookupResponseEntity.getEnrolled()));
                responses.add(new GetTransactionLegDetailResponse("Order ID", bankservLookupResponseEntity.getOrderId()));
                responses.add(new GetTransactionLegDetailResponse("ACS URL", bankservLookupResponseEntity.getAcsUrl()));
                responses.add(new GetTransactionLegDetailResponse("ECI Flag", bankservLookupResponseEntity.getEciFlag()));
            break;
            case "CARD_TDS_AUTH":
                logger.info("Card TDS Auth leg found");
                BankservAuthenticateRequestEntity bankservAuthenticateRequestEntity =
                        bankservAuthenticateRequestRepository.findBankservAuthenticateRequestEntityByTransactionLegByTransactionLegId(transactionLegEntity);
                BankservAuthenticateResponseEntity bankservAuthenticateResponseEntity =
                        bankservAuthenticateResponseRepository.findBankservAuthenticateResponseEntityByTransactionLegByTransactionLegId(transactionLegEntity);
                responses.add(new GetTransactionLegDetailResponse("Request", ""));
                responses.add(new GetTransactionLegDetailResponse("Date Logged", bankservAuthenticateRequestEntity.getDateLogged().toString()));
                responses.add(new GetTransactionLegDetailResponse("Version", bankservAuthenticateRequestEntity.getVersion()));
                responses.add(new GetTransactionLegDetailResponse("Message Type", bankservAuthenticateRequestEntity.getMsgType()));
                responses.add(new GetTransactionLegDetailResponse("Processor ID", bankservAuthenticateRequestEntity.getProcessorId()));
                responses.add(new GetTransactionLegDetailResponse("Merchant ID", bankservAuthenticateRequestEntity.getMerchantId()));
                responses.add(new GetTransactionLegDetailResponse("Transaction Type", bankservAuthenticateRequestEntity.getTransactionType()));
                responses.add(new GetTransactionLegDetailResponse("Transaction Password", bankservAuthenticateRequestEntity.getTransactionPwd()));
                responses.add(new GetTransactionLegDetailResponse("Transaction ID", bankservAuthenticateRequestEntity.getTransactionId()));
                responses.add(new GetTransactionLegDetailResponse("Pares Payload", bankservAuthenticateRequestEntity.getParesPayload()));
                responses.add(new GetTransactionLegDetailResponse("", ""));
                responses.add(new GetTransactionLegDetailResponse("Response", ""));
                responses.add(new GetTransactionLegDetailResponse("Date Logged", bankservAuthenticateResponseEntity.getDateLogged().toString()));
                responses.add(new GetTransactionLegDetailResponse("Error Description", bankservAuthenticateResponseEntity.getErrorDesc()));
                responses.add(new GetTransactionLegDetailResponse("Error Number", bankservAuthenticateResponseEntity.getErrorNo()));
                responses.add(new GetTransactionLegDetailResponse("CAVV", bankservAuthenticateResponseEntity.getCavv()));
                responses.add(new GetTransactionLegDetailResponse("XID", bankservAuthenticateResponseEntity.getXid()));
                responses.add(new GetTransactionLegDetailResponse("ECI FLAG", bankservAuthenticateResponseEntity.getEciFlag()));
                responses.add(new GetTransactionLegDetailResponse("Pares Status", bankservAuthenticateResponseEntity.getParesStatus()));
                responses.add(new GetTransactionLegDetailResponse("Signature Verification", bankservAuthenticateResponseEntity.getSignatureVerification()));
            break;
            case "CARD_AUTHORISE":
            case "CARD_SETTLE":
            case "CARD_REVERSE":
            case "CARD_REFUND":
                logger.info("Card Auth/Settle/Reverse/Refund leg found");
                TransactionEntity transactionEntity = transactionLegEntity.getTransactionByTransactionId();
                GatewayEntity gatewayEntity = transactionEntity.getGatewayByGatewayId();

                if (gatewayEntity.getCode().equals("FNB") || gatewayEntity.getCode().equals("ABSA")) {
                    CardBigisoTransactionEntity cardBigisoTransactionEntity = cardBigisoTransactionRepository.findCardBigisoTransactionEntityByTransactionLegByTransactionLegId(transactionLegEntity);
                    CardBigisoResponseEntity cardBigisoResponseEntity = cardBigisoResponseRepository.findCardBigisoResponseEntityByTransactionLegByTransactionLegId(transactionLegEntity);
                    responses.add(new GetTransactionLegDetailResponse("Transaction", ""));
                    if (cardBigisoTransactionEntity != null) {
                        responses.add(new GetTransactionLegDetailResponse("Date Logged", cardBigisoTransactionEntity.getDateLogged().toString()));
                        responses.add(new GetTransactionLegDetailResponse("Tokendata QK MG TRX IDX", cardBigisoTransactionEntity.getTokendataQkMgTrxIdx()));
                        responses.add(new GetTransactionLegDetailResponse("Transaction ID", cardBigisoTransactionEntity.getTransactionId().toString()));
                        responses.add(new GetTransactionLegDetailResponse("Systems Trace Audit Number", cardBigisoTransactionEntity.getSystemsTraceAuditNum()));
                        responses.add(new GetTransactionLegDetailResponse("Retrieval Reference Number", cardBigisoTransactionEntity.getRetrievalReferenceNum()));
                        responses.add(new GetTransactionLegDetailResponse("Processing Code Transaction Type", cardBigisoTransactionEntity.getProcessingCodeTransactionType()));
                        responses.add(new GetTransactionLegDetailResponse("Pos Auth Indicators Auth Indicators1", cardBigisoTransactionEntity.getPosAuthIndicatorsAuthIndicators1()));
                        responses.add(new GetTransactionLegDetailResponse("Transaction Type", cardBigisoTransactionEntity.getTransactionType()));
                        responses.add(new GetTransactionLegDetailResponse("Pos Settlement Data Draft Capture Flag", cardBigisoTransactionEntity.getPosSettlementDataDraftCaptureFlag()));
                        responses.add(new GetTransactionLegDetailResponse("Processing Code Account Type From", cardBigisoTransactionEntity.getProcessingCodeAccountTypeFrom()));
                        responses.add(new GetTransactionLegDetailResponse("Processing Code Account Type To", cardBigisoTransactionEntity.getProcessingCodeAccountTypeTo()));
                        responses.add(new GetTransactionLegDetailResponse("PAN", cardBigisoTransactionEntity.getPan()));
                        responses.add(new GetTransactionLegDetailResponse("Track2", cardBigisoTransactionEntity.getTrack2()));
                        responses.add(new GetTransactionLegDetailResponse("Pos Settlement Data Services", cardBigisoTransactionEntity.getPosSettlementDataServices()));
                        responses.add(new GetTransactionLegDetailResponse("Pos Settlement Data Originator", cardBigisoTransactionEntity.getPosSettlementDataOriginator()));
                        responses.add(new GetTransactionLegDetailResponse("Pos Settlement Data Destination", cardBigisoTransactionEntity.getPosSettlementDataDestination()));
                        responses.add(new GetTransactionLegDetailResponse("Settlement Date", cardBigisoTransactionEntity.getSettlementDate()));
                        responses.add(new GetTransactionLegDetailResponse("Purchase Time", cardBigisoTransactionEntity.getPurchaseTime()));
                        responses.add(new GetTransactionLegDetailResponse("Purchase Date", cardBigisoTransactionEntity.getPurchaseTime()));
                        responses.add(new GetTransactionLegDetailResponse("Capture Date", cardBigisoTransactionEntity.getCaptureDate()));
                        responses.add(new GetTransactionLegDetailResponse("Amount", cardBigisoTransactionEntity.getAmount().toString()));
                        responses.add(new GetTransactionLegDetailResponse("Expiry Date", cardBigisoTransactionEntity.getExpiryDate()));
                        responses.add(new GetTransactionLegDetailResponse("Merchant Type", cardBigisoTransactionEntity.getMerchantType()));
                        responses.add(new GetTransactionLegDetailResponse("Pos Entry Mode Pan Entry Mode", cardBigisoTransactionEntity.getPosEntryModePanEntryMode()));
                        responses.add(new GetTransactionLegDetailResponse("Pos Condition Code", cardBigisoTransactionEntity.getPosConditionCode()));
                        responses.add(new GetTransactionLegDetailResponse("Acquiring Institution ID Code", cardBigisoTransactionEntity.getAcquiringInstitutionIdCode()));
                        responses.add(new GetTransactionLegDetailResponse("Card Acceptor Terminal ID", cardBigisoTransactionEntity.getCardAcceptorTerminalId()));
                        responses.add(new GetTransactionLegDetailResponse("Card Acceptor ID Code", cardBigisoTransactionEntity.getCardAcceptorIdCode()));
                        responses.add(new GetTransactionLegDetailResponse("Card Acceptor Name", cardBigisoTransactionEntity.getCardAcceptorName()));
                        responses.add(new GetTransactionLegDetailResponse("Card Acceptor City", cardBigisoTransactionEntity.getCardAcceptorCity()));
                        responses.add(new GetTransactionLegDetailResponse("Card Acceptor State", cardBigisoTransactionEntity.getCardAcceptorState()));
                        responses.add(new GetTransactionLegDetailResponse("Card Acceptor Country", cardBigisoTransactionEntity.getCardAcceptorCountry()));
                        responses.add(new GetTransactionLegDetailResponse("Additional Data Retailer ID", cardBigisoTransactionEntity.getAdditionalDataRetailerId()));
                        responses.add(new GetTransactionLegDetailResponse("Currency Code", cardBigisoTransactionEntity.getCurrencyCode()));
                        responses.add(new GetTransactionLegDetailResponse("Pos Terminal Data FIID", cardBigisoTransactionEntity.getPosTerminalDataFiid()));
                        responses.add(new GetTransactionLegDetailResponse("Pos Terminal Data Dev", cardBigisoTransactionEntity.getPosTerminalDataDev()));
                        responses.add(new GetTransactionLegDetailResponse("Pos Terminal Data Time Offset", cardBigisoTransactionEntity.getPosTerminalDataTimeOffset()));
                        responses.add(new GetTransactionLegDetailResponse("Pos Card Issuer FIID", cardBigisoTransactionEntity.getPosCardIssuerFiid()));
                        responses.add(new GetTransactionLegDetailResponse("Pos Card Issuer Dev", cardBigisoTransactionEntity.getPosCardIssuerDev()));
                        responses.add(new GetTransactionLegDetailResponse("Receiving Institution ID Code", cardBigisoTransactionEntity.getReceivingInstitutionIdCode()));
                        responses.add(new GetTransactionLegDetailResponse("Pos Batch Data Batch Seq Num", cardBigisoTransactionEntity.getPosBatchDataBatchSeqNum()));
                        responses.add(new GetTransactionLegDetailResponse("Pos Batch Data Batch Num", cardBigisoTransactionEntity.getPosBatchDataBatchNum()));
                        responses.add(new GetTransactionLegDetailResponse("Pos Batch Data Shift Num", cardBigisoTransactionEntity.getPosBatchDataShiftNum()));
                        responses.add(new GetTransactionLegDetailResponse("Pos Pre Auth Chargeback Reason For Chargeback", cardBigisoTransactionEntity.getPosPreAuthChargebackReasonForChargeback()));
                        responses.add(new GetTransactionLegDetailResponse("Pos Pre Auth Chargeback Num For Chargeback", cardBigisoTransactionEntity.getPosPreAuthChargebackNumOfChargeback()));
                        responses.add(new GetTransactionLegDetailResponse("Electronic Coerceindicator", cardBigisoTransactionEntity.getElectronicCoerceindicator()));
                        responses.add(new GetTransactionLegDetailResponse("Cardholder Authenticationid", cardBigisoTransactionEntity.getCardholderAuthenticationid()));
                        responses.add(new GetTransactionLegDetailResponse("Cardholder Authenticationdata", cardBigisoTransactionEntity.getCardholderAuthenticationdata()));
                        responses.add(new GetTransactionLegDetailResponse("Budget", cardBigisoTransactionEntity.getBudget()));
                        responses.add(new GetTransactionLegDetailResponse("Budget Period", cardBigisoTransactionEntity.getBudgetPeriod()));
                        responses.add(new GetTransactionLegDetailResponse("Tokendata C0 ADNL Data Ind", cardBigisoTransactionEntity.getTokendataC0AdnlDataInd()));
                        responses.add(new GetTransactionLegDetailResponse("Pos Entry Mode Pin Capability", cardBigisoTransactionEntity.getPosEntryModePinCapability()));
                        responses.add(new GetTransactionLegDetailResponse("Tokendata C6 XID", cardBigisoTransactionEntity.getTokendataC6Xid()));
                        responses.add(new GetTransactionLegDetailResponse("Tokendata C6 Trans Stain", cardBigisoTransactionEntity.getTokendataC6TransStain()));
                        responses.add(new GetTransactionLegDetailResponse("Tokendata CE Mastercard AAV", cardBigisoTransactionEntity.getTokendataCeMastercardAav()));
                        responses.add(new GetTransactionLegDetailResponse("Tokendata CE Auth Ind Flg", cardBigisoTransactionEntity.getTokendataCeAuthIndFlg()));
                        responses.add(new GetTransactionLegDetailResponse("Tokendata C4 Term Attend Ind", cardBigisoTransactionEntity.getTokendataC4TermAttendInd()));
                        responses.add(new GetTransactionLegDetailResponse("Tokendata C4 Term Loc Ind", cardBigisoTransactionEntity.getTokendataC4TermLocInd()));
                        responses.add(new GetTransactionLegDetailResponse("Tokendata C4 Crdhldr Present Ind", cardBigisoTransactionEntity.getTokendataC4CrdhldrPresentInd()));
                        responses.add(new GetTransactionLegDetailResponse("Tokendata C4 Crdhldr Actvt Term Ind", cardBigisoTransactionEntity.getTokendataC4CrdhldrActvtTermInd()));
                        responses.add(new GetTransactionLegDetailResponse("Tokendata C4 Term Input Cap Ind", cardBigisoTransactionEntity.getTokendataC4TermInputCapInd()));
                        responses.add(new GetTransactionLegDetailResponse("Tokendata C4 Term Oper Ind", cardBigisoTransactionEntity.getTokendataC4TermOperInd()));
                        responses.add(new GetTransactionLegDetailResponse("Tokendata C4 Crd Present Ind", cardBigisoTransactionEntity.getTokendataC4CrdPresentInd()));
                        responses.add(new GetTransactionLegDetailResponse("Tokendata C4 Crd Captr Ind", cardBigisoTransactionEntity.getTokendataC4CrdCaptrInd()));
                        responses.add(new GetTransactionLegDetailResponse("Tokendata C4 Txn Stat Ind", cardBigisoTransactionEntity.getTokendataC4TxnStatInd()));
                        responses.add(new GetTransactionLegDetailResponse("Tokendata C4 Txn Sec Ind", cardBigisoTransactionEntity.getTokendataC4TxnSecInd()));
                        responses.add(new GetTransactionLegDetailResponse("Tokendata C4 Txn Rtn Ind", cardBigisoTransactionEntity.getTokendataC4TxnRtnInd()));
                        responses.add(new GetTransactionLegDetailResponse("Tokendata C4 Crdhldr ID Method", cardBigisoTransactionEntity.getTokendataC4CrdhldrIdMethod()));
                        responses.add(new GetTransactionLegDetailResponse("Tokendata C0 E Com Flg", cardBigisoTransactionEntity.getTokendataC0EComFlg()));
                        responses.add(new GetTransactionLegDetailResponse("Tokendata C0 Frd Prn Flg", cardBigisoTransactionEntity.getTokendataC0FrdPrnFlg()));
                        responses.add(new GetTransactionLegDetailResponse("Tokendata C0 Authn Coll Ind", cardBigisoTransactionEntity.getTokendataC0AuthnCollInd()));
                    }
                    responses.add(new GetTransactionLegDetailResponse("", ""));
                    if (cardBigisoResponseEntity != null) {
                        responses.add(new GetTransactionLegDetailResponse("Response", ""));
                        responses.add(new GetTransactionLegDetailResponse("Date Logged", cardBigisoResponseEntity.getDateLogged().toString()));
                        responses.add(new GetTransactionLegDetailResponse("Pos Batch Data", cardBigisoResponseEntity.getPosBatchData()));
                        responses.add(new GetTransactionLegDetailResponse("Auth ID Response", cardBigisoResponseEntity.getAuthIdResponse()));
                        responses.add(new GetTransactionLegDetailResponse("Pos Settlement Data", cardBigisoResponseEntity.getPosSettlementData()));
                        responses.add(new GetTransactionLegDetailResponse("Pos Card Issuer", cardBigisoResponseEntity.getPosCardIssuer()));
                        responses.add(new GetTransactionLegDetailResponse("Purchase Date", cardBigisoResponseEntity.getPurchaseDate()));
                        responses.add(new GetTransactionLegDetailResponse("Merchant Type", cardBigisoResponseEntity.getMerchantType()));
                        responses.add(new GetTransactionLegDetailResponse("Pos Entry Mode", cardBigisoResponseEntity.getPosEntryMode()));
                        responses.add(new GetTransactionLegDetailResponse("Pos Pre Auth Chargeback", cardBigisoResponseEntity.getPosPreAuthChargeback()));
                        responses.add(new GetTransactionLegDetailResponse("Transmission Datetime", cardBigisoResponseEntity.getTransmissionDatetime()));
                        responses.add(new GetTransactionLegDetailResponse("Receiving Institution ID Code", cardBigisoResponseEntity.getReceivingInstitutionIdCode()));
                        responses.add(new GetTransactionLegDetailResponse("Result Description", cardBigisoResponseEntity.getResultDescription()));
                        responses.add(new GetTransactionLegDetailResponse("Card Acceptor Name Location", cardBigisoResponseEntity.getCardAcceptorNameLocation()));
                        responses.add(new GetTransactionLegDetailResponse("Card Acceptor ID Code", cardBigisoResponseEntity.getCardAcceptorIdCode()));
                        responses.add(new GetTransactionLegDetailResponse("Amount", cardBigisoResponseEntity.getAmount().toString()));
                        responses.add(new GetTransactionLegDetailResponse("Track2", cardBigisoResponseEntity.getTrack2()));
                        responses.add(new GetTransactionLegDetailResponse("Processing Code", cardBigisoResponseEntity.getProcessingCode()));
                        responses.add(new GetTransactionLegDetailResponse("Result Code", cardBigisoResponseEntity.getResultCode()));
                        responses.add(new GetTransactionLegDetailResponse("Pos Auth Indicators", cardBigisoResponseEntity.getPosAuthIndicators()));
                        responses.add(new GetTransactionLegDetailResponse("Expiry Data", cardBigisoResponseEntity.getExpiryData()));
                        responses.add(new GetTransactionLegDetailResponse("Capture Date", cardBigisoResponseEntity.getCaptureDate()));
                        responses.add(new GetTransactionLegDetailResponse("Additional Data Retailer Data", cardBigisoResponseEntity.getAdditionalDataRetailerData()));
                        responses.add(new GetTransactionLegDetailResponse("Transaction ID", cardBigisoResponseEntity.getTransactionId().toString()));
                        responses.add(new GetTransactionLegDetailResponse("Result Source", cardBigisoResponseEntity.getResultSource()));
                        responses.add(new GetTransactionLegDetailResponse("Currency Code", cardBigisoResponseEntity.getCurrencyCode()));
                        responses.add(new GetTransactionLegDetailResponse("Systems Trace Audit Num", cardBigisoResponseEntity.getSystemsTraceAuditNum()));
                        responses.add(new GetTransactionLegDetailResponse("Purchase Time", cardBigisoResponseEntity.getPurchaseTime()));
                        responses.add(new GetTransactionLegDetailResponse("Acquiring Institution ID Code", cardBigisoResponseEntity.getAcquiringInstitutionIdCode()));
                        responses.add(new GetTransactionLegDetailResponse("Pos Terminal Data", cardBigisoResponseEntity.getPosTerminalData()));
                        responses.add(new GetTransactionLegDetailResponse("Settlement Date", cardBigisoResponseEntity.getSettlementDate()));
                        responses.add(new GetTransactionLegDetailResponse("Card Acceptor Terminal ID", cardBigisoResponseEntity.getCardAcceptorTerminalId()));
                        responses.add(new GetTransactionLegDetailResponse("Result Status", cardBigisoResponseEntity.getResultStatus()));
                        responses.add(new GetTransactionLegDetailResponse("Retrieval Reference Num", cardBigisoResponseEntity.getRetrievalReferenceNum()));
                        responses.add(new GetTransactionLegDetailResponse("Tokendata", cardBigisoResponseEntity.getTokendata()));
                    }
                } else if (gatewayEntity.getCode().equals("NEDBANK")) {
                    CardIveriTransactionEntity cardIveriTransactionEntity = cardIveriTransactionRepository.findCardIveriTransactionEntityByTransactionLegByTransactionLegId(transactionLegEntity);
                    CardIveriResponseEntity cardIveriResponseEntity = cardIveriResponseRepository.findCardIveriResponseEntityByTransactionLegByTransactionLegId(transactionLegEntity);
                    responses.add(new GetTransactionLegDetailResponse("Transaction", ""));
                    if (cardIveriTransactionEntity != null) {
                        responses.add(new GetTransactionLegDetailResponse("Version", cardIveriTransactionEntity.getVersion()));
                        responses.add(new GetTransactionLegDetailResponse("Certificate ID", cardIveriTransactionEntity.getCertificateId()));
                        responses.add(new GetTransactionLegDetailResponse("Direction", cardIveriTransactionEntity.getDirection()));
                        responses.add(new GetTransactionLegDetailResponse("CC Number", cardIveriTransactionEntity.getCcNumber()));
                        responses.add(new GetTransactionLegDetailResponse("Expiry Date", cardIveriTransactionEntity.getExpiryDate()));
                        responses.add(new GetTransactionLegDetailResponse("Currency", cardIveriTransactionEntity.getCurrency()));
                        responses.add(new GetTransactionLegDetailResponse("Terminal", cardIveriTransactionEntity.getTerminal()));
                        responses.add(new GetTransactionLegDetailResponse("Amount", cardIveriTransactionEntity.getAmount()));
                        responses.add(new GetTransactionLegDetailResponse("Budget Period", cardIveriTransactionEntity.getBudgetPeriod()));
                        responses.add(new GetTransactionLegDetailResponse("Merchant Reference", cardIveriTransactionEntity.getMerchantReference()));
                        responses.add(new GetTransactionLegDetailResponse("Merchant Trace ", cardIveriTransactionEntity.getMerchantTrace()));
                        responses.add(new GetTransactionLegDetailResponse("Electronic Commerce Indicator", cardIveriTransactionEntity.getElectronicCommerceIndicator()));
                        responses.add(new GetTransactionLegDetailResponse("Card Holder Authentication ID", cardIveriTransactionEntity.getCardHolderAuthenticationId()));
                        responses.add(new GetTransactionLegDetailResponse("Application ID", cardIveriTransactionEntity.getApplicationId()));
                        responses.add(new GetTransactionLegDetailResponse("Command", cardIveriTransactionEntity.getCommand()));
                        responses.add(new GetTransactionLegDetailResponse("Mode", cardIveriTransactionEntity.getMode()));
                        responses.add(new GetTransactionLegDetailResponse("Validate Request", cardIveriTransactionEntity.getValidateRequest()));
                        responses.add(new GetTransactionLegDetailResponse("Protocol", cardIveriTransactionEntity.getProtocol()));
                        responses.add(new GetTransactionLegDetailResponse("Protocol Version", cardIveriTransactionEntity.getProtocolVersion()));
                    }
                    if (cardIveriResponseEntity != null) {
                        responses.add(new GetTransactionLegDetailResponse("", ""));
                        responses.add(new GetTransactionLegDetailResponse("Response", ""));
                        responses.add(new GetTransactionLegDetailResponse("Version", cardIveriResponseEntity.getVersion()));
                        responses.add(new GetTransactionLegDetailResponse("Direction", cardIveriResponseEntity.getDirection()));
                        responses.add(new GetTransactionLegDetailResponse("Application ID", cardIveriResponseEntity.getApplicationId()));
                        responses.add(new GetTransactionLegDetailResponse("Command", cardIveriResponseEntity.getCommand()));
                        responses.add(new GetTransactionLegDetailResponse("Mode", cardIveriResponseEntity.getMode()));
                        responses.add(new GetTransactionLegDetailResponse("Request ID", cardIveriResponseEntity.getRequestId()));
                        responses.add(new GetTransactionLegDetailResponse("Merchant Trace", cardIveriResponseEntity.getMerchantTrace()));
                        responses.add(new GetTransactionLegDetailResponse("Amount", cardIveriResponseEntity.getAmount()));
                        responses.add(new GetTransactionLegDetailResponse("Authorisation Code", cardIveriResponseEntity.getAuthorisationCode()));
                        responses.add(new GetTransactionLegDetailResponse("CC Number", cardIveriResponseEntity.getCcNumber()));
                        responses.add(new GetTransactionLegDetailResponse("Currency", cardIveriResponseEntity.getCurrency()));
                        responses.add(new GetTransactionLegDetailResponse("Expiry Date", cardIveriResponseEntity.getExpiryDate()));
                        responses.add(new GetTransactionLegDetailResponse("Merchant Reference", cardIveriResponseEntity.getMerchantReference()));
                        responses.add(new GetTransactionLegDetailResponse("Terminal", cardIveriResponseEntity.getTerminal()));
                        responses.add(new GetTransactionLegDetailResponse("Transaction Index", cardIveriResponseEntity.getTransactionIndex()));
                        responses.add(new GetTransactionLegDetailResponse("Merchant Name", cardIveriResponseEntity.getMerchantName()));
                        responses.add(new GetTransactionLegDetailResponse("Merchant Usn", cardIveriResponseEntity.getMerchantUsn()));
                        responses.add(new GetTransactionLegDetailResponse("Acquirer", cardIveriResponseEntity.getAcquirer()));
                        responses.add(new GetTransactionLegDetailResponse("Acquirer Reference", cardIveriResponseEntity.getAcquirerReference()));
                        responses.add(new GetTransactionLegDetailResponse("Acquirer Date", cardIveriResponseEntity.getAcquirerDate()));
                        responses.add(new GetTransactionLegDetailResponse("Acquirer Time", cardIveriResponseEntity.getAcquirerTime()));
                        responses.add(new GetTransactionLegDetailResponse("Display Amount", cardIveriResponseEntity.getDisplayAmount()));
                        responses.add(new GetTransactionLegDetailResponse("Bin", cardIveriResponseEntity.getBin()));
                        responses.add(new GetTransactionLegDetailResponse("Association", cardIveriResponseEntity.getAssociation()));
                        responses.add(new GetTransactionLegDetailResponse("Card Type", cardIveriResponseEntity.getCardType()));
                        responses.add(new GetTransactionLegDetailResponse("Issuer", cardIveriResponseEntity.getIssuer()));
                        responses.add(new GetTransactionLegDetailResponse("Jurisdiction", cardIveriResponseEntity.getJurisdiction()));
                        responses.add(new GetTransactionLegDetailResponse("PAN", cardIveriResponseEntity.getPan()));
                        responses.add(new GetTransactionLegDetailResponse("PAN Mode", cardIveriResponseEntity.getPanMode()));
                        responses.add(new GetTransactionLegDetailResponse("Recon Reference", cardIveriResponseEntity.getReconReference()));
                        responses.add(new GetTransactionLegDetailResponse("Card Holder Presence", cardIveriResponseEntity.getCardHolderPresence()));
                        responses.add(new GetTransactionLegDetailResponse("Merchant Address", cardIveriResponseEntity.getMerchantAddress()));
                        responses.add(new GetTransactionLegDetailResponse("Merchant City", cardIveriResponseEntity.getMerchantCity()));
                        responses.add(new GetTransactionLegDetailResponse("Merchant Country Code", cardIveriResponseEntity.getMerchantCountryCode()));
                        responses.add(new GetTransactionLegDetailResponse("Merchant Country", cardIveriResponseEntity.getMerchantCountry()));
                        responses.add(new GetTransactionLegDetailResponse("Distributor Name", cardIveriResponseEntity.getDistributorName()));
                    }
                } else if (gatewayEntity.getCode().equals("STANDARD_BANK") || gatewayEntity.getCode().equals("BW")) {
                    CardPostillionTransactionEntity cardPostillionTransactionEntity = cardPostillionTransactionRepository.findCardPostillionTransactionEntityByTransactionLegByTransactionLegId(transactionLegEntity);
                    CardPostillionResponseEntity cardPostillionResponseEntity = cardPostillionResponseRepository.findCardPostillionResponseEntityByTransactionLegByTransactionLegId(transactionLegEntity);
                    responses.add(new GetTransactionLegDetailResponse("Transaction", ""));
                    if (cardPostillionTransactionEntity != null) {
                        responses.add(new GetTransactionLegDetailResponse("Date Logged", cardPostillionTransactionEntity.getDateLogged().toString()));
                        responses.add(new GetTransactionLegDetailResponse("Tokendata QK MG TX IDX", cardPostillionTransactionEntity.getTokendataQkMgTrxIdx()));
                        responses.add(new GetTransactionLegDetailResponse("Transaction ID", cardPostillionTransactionEntity.getTransactionId().toString()));
                        responses.add(new GetTransactionLegDetailResponse("Systems Trace Audit Num", cardPostillionTransactionEntity.getSystemsTraceAuditNum()));
                        responses.add(new GetTransactionLegDetailResponse("Retrieval Reference Num", cardPostillionTransactionEntity.getRetrievalReferenceNum()));
                        responses.add(new GetTransactionLegDetailResponse("Transaction Type", cardPostillionTransactionEntity.getTransactionType()));
                        responses.add(new GetTransactionLegDetailResponse("Structured Data", cardPostillionTransactionEntity.getStructuredData()));
                        responses.add(new GetTransactionLegDetailResponse("Processing Code Transaction Type", cardPostillionTransactionEntity.getProcessingCodeTransactionType()));
                        responses.add(new GetTransactionLegDetailResponse("Transmission Datetime", cardPostillionTransactionEntity.getTransmissionDatetime()));
                        responses.add(new GetTransactionLegDetailResponse("Original Transmission Date And Time", cardPostillionTransactionEntity.getOriginalTransmissionDateAndTime()));
                        responses.add(new GetTransactionLegDetailResponse("Processing Code Account Type From", cardPostillionTransactionEntity.getProcessingCodeAccountTypeFrom()));
                        responses.add(new GetTransactionLegDetailResponse("Processing Code Account Type To", cardPostillionTransactionEntity.getProcessingCodeAccountTypeTo()));
                        responses.add(new GetTransactionLegDetailResponse("PAN", cardPostillionTransactionEntity.getPan()));
                        responses.add(new GetTransactionLegDetailResponse("Amount", cardPostillionTransactionEntity.getAmount().toString()));
                        responses.add(new GetTransactionLegDetailResponse("Purchase Time", cardPostillionTransactionEntity.getPurchaseTime()));
                        responses.add(new GetTransactionLegDetailResponse("Purchase Date", cardPostillionTransactionEntity.getPurchaseDate()));
                        responses.add(new GetTransactionLegDetailResponse("Merchant Type", cardPostillionTransactionEntity.getMerchantType()));
                        responses.add(new GetTransactionLegDetailResponse("Pos Entry Mode PAN Entry Mode", cardPostillionTransactionEntity.getPosEntryModePanEntryMode()));
                        responses.add(new GetTransactionLegDetailResponse("Pos Entry Mode Pin Capability", cardPostillionTransactionEntity.getPosEntryModePinCapability()));
                        responses.add(new GetTransactionLegDetailResponse("Acquiring Institution ID Code", cardPostillionTransactionEntity.getAcquiringInstitutionIdCode()));
                        responses.add(new GetTransactionLegDetailResponse("Card Acceptor Terminal ID", cardPostillionTransactionEntity.getCardAcceptorTerminalId()));
                        responses.add(new GetTransactionLegDetailResponse("Card Acceptor ID Code", cardPostillionTransactionEntity.getCardAcceptorIdCode()));
                        responses.add(new GetTransactionLegDetailResponse("Card Acceptor Name", cardPostillionTransactionEntity.getCardAcceptorName()));
                        responses.add(new GetTransactionLegDetailResponse("Card Acceptor City", cardPostillionTransactionEntity.getCardAcceptorCity()));
                        responses.add(new GetTransactionLegDetailResponse("Card Acceptor State", cardPostillionTransactionEntity.getCardAcceptorState()));
                        responses.add(new GetTransactionLegDetailResponse("Card Acceptor Country", cardPostillionTransactionEntity.getCardAcceptorCountry()));
                        responses.add(new GetTransactionLegDetailResponse("Additional Data Retailer ID", cardPostillionTransactionEntity.getAdditionalDataRetailerId()));
                        responses.add(new GetTransactionLegDetailResponse("Currency Code", cardPostillionTransactionEntity.getCurrencyCode()));
                        responses.add(new GetTransactionLegDetailResponse("Budget", cardPostillionTransactionEntity.getBudget()));
                        responses.add(new GetTransactionLegDetailResponse("Pos Condition Code", cardPostillionTransactionEntity.getPosConditionCode()));
                        responses.add(new GetTransactionLegDetailResponse("Cardholder Authentication Capability", cardPostillionTransactionEntity.getCardholderAuthenticationCapability()));
                        responses.add(new GetTransactionLegDetailResponse("Cardholder Authentication Method", cardPostillionTransactionEntity.getCardholderAuthenticationMethod()));
                        responses.add(new GetTransactionLegDetailResponse("Cardholder Authentication Entity", cardPostillionTransactionEntity.getCardholderAuthenticationEntity()));
                        responses.add(new GetTransactionLegDetailResponse("Card Data Input Capability Position1", cardPostillionTransactionEntity.getCardDataInputCapabilityPosition1()));
                        responses.add(new GetTransactionLegDetailResponse("Card Capture Capability", cardPostillionTransactionEntity.getCardCaptureCapability()));
                        responses.add(new GetTransactionLegDetailResponse("Operating Environment", cardPostillionTransactionEntity.getOperatingEnvironment()));
                        responses.add(new GetTransactionLegDetailResponse("Cardholder Is Present", cardPostillionTransactionEntity.getCardholderIsPresent()));
                        responses.add(new GetTransactionLegDetailResponse("Card Is Present", cardPostillionTransactionEntity.getCardIsPresent()));
                        responses.add(new GetTransactionLegDetailResponse("Card Data Input Capability Position7", cardPostillionTransactionEntity.getCardDataInputCapabilityPosition7()));
                        responses.add(new GetTransactionLegDetailResponse("Card Data Output Capability", cardPostillionTransactionEntity.getCardDataOutputCapability()));
                        responses.add(new GetTransactionLegDetailResponse("Terminal Output Capability", cardPostillionTransactionEntity.getTerminalOutputCapability()));
                        responses.add(new GetTransactionLegDetailResponse("Pin Capture Capability", cardPostillionTransactionEntity.getPinCaptureCapability()));
                        responses.add(new GetTransactionLegDetailResponse("Terminal Operator", cardPostillionTransactionEntity.getTerminalOperator()));
                        responses.add(new GetTransactionLegDetailResponse("Authorization Profile", cardPostillionTransactionEntity.getAuthorizationProfile()));
                        responses.add(new GetTransactionLegDetailResponse("Terminal Type", cardPostillionTransactionEntity.getTerminalType()));
                        responses.add(new GetTransactionLegDetailResponse("Card Verification Result", cardPostillionTransactionEntity.getCardVerificationResult()));
                        responses.add(new GetTransactionLegDetailResponse("UCAF Collection", cardPostillionTransactionEntity.getUcafCollection()));
                        responses.add(new GetTransactionLegDetailResponse("UCAF Authentication", cardPostillionTransactionEntity.getUcafAuthentication()));
                        responses.add(new GetTransactionLegDetailResponse("Cardholder Authenticationid", cardPostillionTransactionEntity.getCardholderAuthenticationid()));
                        responses.add(new GetTransactionLegDetailResponse("Cardholder AuthenticationData", cardPostillionTransactionEntity.getCardholderAuthenticationdata()));
                    }
                    if (cardPostillionResponseEntity != null) {
                        responses.add(new GetTransactionLegDetailResponse("", ""));
                        responses.add(new GetTransactionLegDetailResponse("Response", ""));
                        responses.add(new GetTransactionLegDetailResponse("Date Logged", cardPostillionResponseEntity.getDateLogged().toString()));
                        responses.add(new GetTransactionLegDetailResponse("POS Batch Data", cardPostillionResponseEntity.getPosBatchData()));
                        responses.add(new GetTransactionLegDetailResponse("Auth ID Response", cardPostillionResponseEntity.getAuthIdResponse()));
                        responses.add(new GetTransactionLegDetailResponse("POS Settlement Data", cardPostillionResponseEntity.getPosSettlementData()));
                        responses.add(new GetTransactionLegDetailResponse("POS Card Issuer", cardPostillionResponseEntity.getPosCardIssuer()));
                        responses.add(new GetTransactionLegDetailResponse("Purchase Date", cardPostillionResponseEntity.getPurchaseDate()));
                        responses.add(new GetTransactionLegDetailResponse("Merchant Type", cardPostillionResponseEntity.getMerchantType()));
                        responses.add(new GetTransactionLegDetailResponse("POS Entry Mode", cardPostillionResponseEntity.getPosEntryMode()));
                        responses.add(new GetTransactionLegDetailResponse("POS Pre Auth Chargeback", cardPostillionResponseEntity.getPosPreAuthChargeback()));
                        responses.add(new GetTransactionLegDetailResponse("Transmission Datetime", cardPostillionResponseEntity.getTransmissionDatetime()));
                        responses.add(new GetTransactionLegDetailResponse("Receiving Institution ID Code", cardPostillionResponseEntity.getReceivingInstitutionIdCode()));
                        responses.add(new GetTransactionLegDetailResponse("Result Description", cardPostillionResponseEntity.getResultDescription()));
                        responses.add(new GetTransactionLegDetailResponse("Card Acceptor Name Location", cardPostillionResponseEntity.getCardAcceptorNameLocation()));
                        responses.add(new GetTransactionLegDetailResponse("Card Acceptor ID Code", cardPostillionResponseEntity.getCardAcceptorIdCode()));
                        responses.add(new GetTransactionLegDetailResponse("Amount", cardPostillionResponseEntity.getAmount().toString()));
                        responses.add(new GetTransactionLegDetailResponse("Track2", cardPostillionResponseEntity.getTrack2()));
                        responses.add(new GetTransactionLegDetailResponse("Processing Code", cardPostillionResponseEntity.getProcessingCode()));
                        responses.add(new GetTransactionLegDetailResponse("Result Code", cardPostillionResponseEntity.getResultCode()));
                        responses.add(new GetTransactionLegDetailResponse("POS Auth Indicators", cardPostillionResponseEntity.getPosAuthIndicators()));
                        responses.add(new GetTransactionLegDetailResponse("Expiry Data", cardPostillionResponseEntity.getExpiryData()));
                        responses.add(new GetTransactionLegDetailResponse("Capture Date", cardPostillionResponseEntity.getCaptureDate()));
                        responses.add(new GetTransactionLegDetailResponse("Additional Data Retailer Data", cardPostillionResponseEntity.getAdditionalDataRetailerData()));
                        responses.add(new GetTransactionLegDetailResponse("Transaction ID", cardPostillionResponseEntity.getTransactionId().toString()));
                        responses.add(new GetTransactionLegDetailResponse("Result Source", cardPostillionResponseEntity.getResultSource()));
                        responses.add(new GetTransactionLegDetailResponse("Currency Code", cardPostillionResponseEntity.getCurrencyCode()));
                        responses.add(new GetTransactionLegDetailResponse("Systems Trade Audit Num", cardPostillionResponseEntity.getSystemsTraceAuditNum()));
                        responses.add(new GetTransactionLegDetailResponse("Purchase Time", cardPostillionResponseEntity.getPurchaseTime()));
                        responses.add(new GetTransactionLegDetailResponse("Acquiring Institution ID Code", cardPostillionResponseEntity.getAcquiringInstitutionIdCode()));
                        responses.add(new GetTransactionLegDetailResponse("POS Terminal Data", cardPostillionResponseEntity.getPosTerminalData()));
                        responses.add(new GetTransactionLegDetailResponse("Settlement Date", cardPostillionResponseEntity.getSettlementDate()));
                        responses.add(new GetTransactionLegDetailResponse("Card Acceptor Terminal ID", cardPostillionResponseEntity.getCardAcceptorTerminalId()));
                        responses.add(new GetTransactionLegDetailResponse("Result Status", cardPostillionResponseEntity.getResultStatus()));
                        responses.add(new GetTransactionLegDetailResponse("Retrieval Reference Number", cardPostillionResponseEntity.getRetrievalReferenceNum()));
                        responses.add(new GetTransactionLegDetailResponse("Tokendata", cardPostillionResponseEntity.getTokendata()));
                    }

                } else if (gatewayEntity.getCode().equals("VACP")) {
                    CardVacpRequestEntity cardVacpRequestEntity = cardVacpRequestRepository.findByTransactionLegId(transactionLegEntity.getId());
                    CardVacpResponseEntity cardVacpResponseEntity = cardVacpResponseRepository.findByTransactionLegId(transactionLegEntity.getId());
                    if (cardVacpRequestEntity != null) {
                        responses.add(new GetTransactionLegDetailResponse("Date Logged", cardVacpRequestEntity.getDateLogged().toString()));
                        responses.add(new GetTransactionLegDetailResponse("Merchant ID", cardVacpRequestEntity.getMerchantId()));
                        responses.add(new GetTransactionLegDetailResponse("Merchant Reference Code", cardVacpRequestEntity.getMerchantReferenceCode()));
                        responses.add(new GetTransactionLegDetailResponse("Bill To First Name", cardVacpRequestEntity.getBillToFirstName()));
                        responses.add(new GetTransactionLegDetailResponse("Bill To Last Name", cardVacpRequestEntity.getBillToLastName()));
                        responses.add(new GetTransactionLegDetailResponse("Bill To Street 1", cardVacpRequestEntity.getBillToStreet1()));
                        responses.add(new GetTransactionLegDetailResponse("Bill To City", cardVacpRequestEntity.getBillToCity()));
                        responses.add(new GetTransactionLegDetailResponse("Bill To Postal Code", cardVacpRequestEntity.getBillToPostalCode()));
                        responses.add(new GetTransactionLegDetailResponse("Bill To Country", cardVacpRequestEntity.getBillToCountry()));
                        responses.add(new GetTransactionLegDetailResponse("Bill To Email", cardVacpRequestEntity.getBillToEmail()));
                        responses.add(new GetTransactionLegDetailResponse("Purchase Totals Currency", cardVacpRequestEntity.getPurchaseTotalsCurrency()));
                        responses.add(new GetTransactionLegDetailResponse("Purchase Totals Grand Total Amount", cardVacpRequestEntity.getPurchaseTotalsGrandTotalAmount()));
                        responses.add(new GetTransactionLegDetailResponse("Card Account Number", cardVacpRequestEntity.getCardAccountNumber()));
                        responses.add(new GetTransactionLegDetailResponse("Card Expiration Month", cardVacpRequestEntity.getCardExpirationMonth()));
                        responses.add(new GetTransactionLegDetailResponse("Card Expiration Year", cardVacpRequestEntity.getCardExpirationYear()));
                        responses.add(new GetTransactionLegDetailResponse("Card Type", cardVacpRequestEntity.getCardType()));
                        responses.add(new GetTransactionLegDetailResponse("CC Auth Service CAVV", cardVacpRequestEntity.getCcAuthServiceCavv()));
                        responses.add(new GetTransactionLegDetailResponse("CC Auth Service Commerce Indicator", cardVacpRequestEntity.getCcAuthServiceCommerceIndicator()));
                        responses.add(new GetTransactionLegDetailResponse("CC Auth Service ECI Raw", cardVacpRequestEntity.getCcAuthServiceEciRaw()));
                        responses.add(new GetTransactionLegDetailResponse("CC Auth Service XID", cardVacpRequestEntity.getCcAuthServiceXid()));
                        responses.add(new GetTransactionLegDetailResponse("CC Auth Service Partial Auth Indicator", cardVacpRequestEntity.getCcAuthServicePartialAuthIndicator()));
                        responses.add(new GetTransactionLegDetailResponse("CC Capture Service Auth Request ID", cardVacpRequestEntity.getCcCaptureServiceAuthRequestId()));
                        responses.add(new GetTransactionLegDetailResponse("CC Reversal Service Auth Request ID", cardVacpRequestEntity.getCcReversalServiceAuthRequestId()));
                        responses.add(new GetTransactionLegDetailResponse("CC Credit Service Capture Request ID", cardVacpRequestEntity.getCcCreditServiceCaptureRequestId()));
                        responses.add(new GetTransactionLegDetailResponse("Business Rules Ignore AVS Result", cardVacpRequestEntity.getBusinessRulesIgnoreAvsResult()));
                        responses.add(new GetTransactionLegDetailResponse("Business Rules Ignore CV Result", cardVacpRequestEntity.getBusinessRulesIgnoreCvResult()));
                        responses.add(new GetTransactionLegDetailResponse("Merchant Transaction Identifier", cardVacpRequestEntity.getMerchantTransactionIdentifier()));
                    }
                    if (cardVacpResponseEntity != null) {
                        responses.add(new GetTransactionLegDetailResponse("", ""));
                        responses.add(new GetTransactionLegDetailResponse("Response", ""));
                        responses.add(new GetTransactionLegDetailResponse("Date Logged", cardVacpResponseEntity.getDateLogged().toString()));
                        responses.add(new GetTransactionLegDetailResponse("Merchant Reference Code", cardVacpResponseEntity.getMerchantReferenceCode()));
                        responses.add(new GetTransactionLegDetailResponse("Request ID", cardVacpResponseEntity.getRequestId()));
                        responses.add(new GetTransactionLegDetailResponse("Direction", cardVacpResponseEntity.getDirection()));
                        responses.add(new GetTransactionLegDetailResponse("Reason Code", cardVacpResponseEntity.getReasonCode().toString()));
                        responses.add(new GetTransactionLegDetailResponse("Request Token", cardVacpResponseEntity.getRequestToken()));
                        responses.add(new GetTransactionLegDetailResponse("Currency", cardVacpResponseEntity.getCurrency()));
                        responses.add(new GetTransactionLegDetailResponse("Amount", cardVacpResponseEntity.getAmount()));
                        responses.add(new GetTransactionLegDetailResponse("Reply Reason Code", cardVacpResponseEntity.getReplyReasonCode().toString()));
                        responses.add(new GetTransactionLegDetailResponse("Request Date Time", cardVacpResponseEntity.getRequestDateTime()));
                        responses.add(new GetTransactionLegDetailResponse("Reconciliation ID", cardVacpResponseEntity.getReconciliationId()));
                        responses.add(new GetTransactionLegDetailResponse("Processor Response", cardVacpResponseEntity.getProcessorResponse()));
                        responses.add(new GetTransactionLegDetailResponse("Authorisation Code", cardVacpResponseEntity.getAuthorisationCode()));
                        responses.add(new GetTransactionLegDetailResponse("AVS Code", cardVacpResponseEntity.getAvsCode()));
                        responses.add(new GetTransactionLegDetailResponse("AVS Code Raw", cardVacpResponseEntity.getAvsCodeRaw()));
                        responses.add(new GetTransactionLegDetailResponse("CV Code", cardVacpResponseEntity.getCvCode()));
                        responses.add(new GetTransactionLegDetailResponse("CV Code Raw", cardVacpResponseEntity.getCvCodeRaw()));
                        responses.add(new GetTransactionLegDetailResponse("Merchant Advice Code", cardVacpResponseEntity.getMerchantAdviceCode()));
                        responses.add(new GetTransactionLegDetailResponse("Merchant Advice Code Raw", cardVacpResponseEntity.getMerchantAdviceCodeRaw()));
                        responses.add(new GetTransactionLegDetailResponse("CAVV Response Code", cardVacpResponseEntity.getCavvResponseCode()));
                        responses.add(new GetTransactionLegDetailResponse("CAVV Response Code Raw", cardVacpResponseEntity.getCavvResponseCodeRaw()));
                        responses.add(new GetTransactionLegDetailResponse("Payment Network Transaction ID", cardVacpResponseEntity.getPaymentNetworkTransactionId()));
                        responses.add(new GetTransactionLegDetailResponse("Card Category", cardVacpResponseEntity.getCardCategory()));
                        responses.add(new GetTransactionLegDetailResponse("Card Group", cardVacpResponseEntity.getCardGroup()));
                        responses.add(new GetTransactionLegDetailResponse("Request Currency", cardVacpResponseEntity.getRequestCurrency()));
                        responses.add(new GetTransactionLegDetailResponse("Receipt Number", cardVacpResponseEntity.getReceiptNumber()));
                        responses.add(new GetTransactionLegDetailResponse("Additional Data", cardVacpResponseEntity.getAdditionalData()));
                    }
                } else if (gatewayEntity.getCode().equals("BOW")) {
                    CardBowRequestEntity cardBowRequestEntity = cardBowRequestRepository.findByTransactionLegId(transactionLegEntity.getId());
                    CardBowResponseEntity cardBowResponseEntity = cardBowResponseRepository.findByTransactionLegId(transactionLegEntity.getId());
                    if (cardBowRequestEntity != null) {
                        responses.add(new GetTransactionLegDetailResponse("Date Logged", cardBowRequestEntity.getDateLogged().toString()));
                        responses.add(new GetTransactionLegDetailResponse("Merchant ID", cardBowRequestEntity.getMerchantId()));
                        responses.add(new GetTransactionLegDetailResponse("Merchant Reference Code", cardBowRequestEntity.getMerchantReferenceCode()));
                        responses.add(new GetTransactionLegDetailResponse("Bill To First Name", cardBowRequestEntity.getBillToFirstName()));
                        responses.add(new GetTransactionLegDetailResponse("Bill To Last Name", cardBowRequestEntity.getBillToLastName()));
                        responses.add(new GetTransactionLegDetailResponse("Bill To Street 1", cardBowRequestEntity.getBillToStreet1()));
                        responses.add(new GetTransactionLegDetailResponse("Bill To City", cardBowRequestEntity.getBillToCity()));
                        responses.add(new GetTransactionLegDetailResponse("Bill To Postal Code", cardBowRequestEntity.getBillToPostalCode()));
                        responses.add(new GetTransactionLegDetailResponse("Bill To Country", cardBowRequestEntity.getBillToCountry()));
                        responses.add(new GetTransactionLegDetailResponse("Bill To Email", cardBowRequestEntity.getBillToEmail()));
                        responses.add(new GetTransactionLegDetailResponse("Purchase Totals Currency", cardBowRequestEntity.getPurchaseTotalsCurrency()));
                        responses.add(new GetTransactionLegDetailResponse("Purchase Totals Grand Total Amount", cardBowRequestEntity.getPurchaseTotalsGrandTotalAmount()));
                        responses.add(new GetTransactionLegDetailResponse("Card Account Number", cardBowRequestEntity.getCardAccountNumber()));
                        responses.add(new GetTransactionLegDetailResponse("Card Expiration Month", cardBowRequestEntity.getCardExpirationMonth()));
                        responses.add(new GetTransactionLegDetailResponse("Card Expiration Year", cardBowRequestEntity.getCardExpirationYear()));
                        responses.add(new GetTransactionLegDetailResponse("Card Type", cardBowRequestEntity.getCardType()));
                        responses.add(new GetTransactionLegDetailResponse("CC Auth Service CAVV", cardBowRequestEntity.getCcAuthServiceCavv()));
                        responses.add(new GetTransactionLegDetailResponse("CC Auth Service Commerce Indicator", cardBowRequestEntity.getCcAuthServiceCommerceIndicator()));
                        responses.add(new GetTransactionLegDetailResponse("CC Auth Service XID", cardBowRequestEntity.getCcAuthServiceXid()));
                        responses.add(new GetTransactionLegDetailResponse("CC Auth Service Partial Auth Indicator", cardBowRequestEntity.getCcAuthServicePartialAuthIndicator()));
                        responses.add(new GetTransactionLegDetailResponse("CC Capture Service Auth Request ID", cardBowRequestEntity.getCcCaptureServiceAuthRequestId()));
                        responses.add(new GetTransactionLegDetailResponse("CC Reversal Service Auth Request ID", cardBowRequestEntity.getCcReversalServiceAuthRequestId()));
                        responses.add(new GetTransactionLegDetailResponse("CC Credit Service Capture Request ID", cardBowRequestEntity.getCcCreditServiceCaptureRequestId()));
                        responses.add(new GetTransactionLegDetailResponse("Merchant Transaction Identifier", cardBowRequestEntity.getMerchantTransactionIdentifier()));
                    }
                    if (cardBowResponseEntity != null) {
                        responses.add(new GetTransactionLegDetailResponse("", ""));
                        responses.add(new GetTransactionLegDetailResponse("Response", ""));
                        responses.add(new GetTransactionLegDetailResponse("Date Logged", cardBowResponseEntity.getDateLogged().toString()));
                        responses.add(new GetTransactionLegDetailResponse("Merchant Reference Code", cardBowResponseEntity.getMerchantReferenceCode()));
                        responses.add(new GetTransactionLegDetailResponse("Request ID", cardBowResponseEntity.getRequestId()));
                        responses.add(new GetTransactionLegDetailResponse("Direction", cardBowResponseEntity.getDirection()));
                        responses.add(new GetTransactionLegDetailResponse("Reason Code", cardBowResponseEntity.getReasonCode().toString()));
                        responses.add(new GetTransactionLegDetailResponse("Currency", cardBowResponseEntity.getCurrency()));
                        responses.add(new GetTransactionLegDetailResponse("Amount", cardBowResponseEntity.getAmount()));
                        responses.add(new GetTransactionLegDetailResponse("Reply Reason Code", cardBowResponseEntity.getReplyReasonCode().toString()));
                        responses.add(new GetTransactionLegDetailResponse("Request Date Time", cardBowResponseEntity.getRequestDateTime()));
                        responses.add(new GetTransactionLegDetailResponse("Reconciliation ID", cardBowResponseEntity.getReconciliationId()));
                        responses.add(new GetTransactionLegDetailResponse("Processor Response", cardBowResponseEntity.getProcessorResponse()));
                        responses.add(new GetTransactionLegDetailResponse("Authorisation Code", cardBowResponseEntity.getAuthorisationCode()));
                        responses.add(new GetTransactionLegDetailResponse("AVS Code", cardBowResponseEntity.getAvsCode()));
                        responses.add(new GetTransactionLegDetailResponse("AVS Code Raw", cardBowResponseEntity.getAvsCodeRaw()));
                        responses.add(new GetTransactionLegDetailResponse("CV Code", cardBowResponseEntity.getCvCode()));
                        responses.add(new GetTransactionLegDetailResponse("CV Code Raw", cardBowResponseEntity.getCvCodeRaw()));
                        responses.add(new GetTransactionLegDetailResponse("Merchant Advice Code", cardBowResponseEntity.getMerchantAdviceCode()));
                        responses.add(new GetTransactionLegDetailResponse("Merchant Advice Code Raw", cardBowResponseEntity.getMerchantAdviceCodeRaw()));
                        responses.add(new GetTransactionLegDetailResponse("CAVV Response Code", cardBowResponseEntity.getCavvResponseCode()));
                        responses.add(new GetTransactionLegDetailResponse("CAVV Response Code Raw", cardBowResponseEntity.getCavvResponseCodeRaw()));
                        responses.add(new GetTransactionLegDetailResponse("Request Currency", cardBowResponseEntity.getRequestCurrency()));
                        responses.add(new GetTransactionLegDetailResponse("Receipt Number", cardBowResponseEntity.getReceiptNumber()));
                        responses.add(new GetTransactionLegDetailResponse("Additional Data", cardBowResponseEntity.getAdditionalData()));
                    }
                }
            break;
            case "CARD_TDS_ENROLLMENT":
                logger.info("Card TDS Enrollment leg found");
                ThreeDsTransactionEntity threeDsTransactionEntityEnrollment = threeDsTransactionRepository.findByTransactionByTransactionId(transactionLegEntity.getTransactionByTransactionId());
                responses.add(new GetTransactionLegDetailResponse("Three DS Transaction ID", String.valueOf(threeDsTransactionEntityEnrollment.getId())));
                responses.add(new GetTransactionLegDetailResponse("Transaction ID", String.valueOf(threeDsTransactionEntityEnrollment.getTransactionByTransactionId().getId())));
                responses.add(new GetTransactionLegDetailResponse("Auth Required", String.valueOf(threeDsTransactionEntityEnrollment.isAuthRequired())));
                responses.add(new GetTransactionLegDetailResponse("Amount", String.valueOf(threeDsTransactionEntityEnrollment.getAmount())));
                responses.add(new GetTransactionLegDetailResponse("Description", threeDsTransactionEntityEnrollment.getDescription()));
                responses.add(new GetTransactionLegDetailResponse("Version", String.valueOf(threeDsTransactionEntityEnrollment.getVersion())));
                responses.add(new GetTransactionLegDetailResponse("Callback Url", String.valueOf(threeDsTransactionEntityEnrollment.getCallbackUrl())));
                responses.add(new GetTransactionLegDetailResponse("ECI", String.valueOf(threeDsTransactionEntityEnrollment.getEci())));
                responses.add(new GetTransactionLegDetailResponse("Security Method", String.valueOf(threeDsTransactionEntityEnrollment.getSecurityMethod())));
                responses.add(new GetTransactionLegDetailResponse("Date Authenticated", String.valueOf(threeDsTransactionEntityEnrollment.getDateAuthenticated())));
                responses.add(new GetTransactionLegDetailResponse("CAVV", String.valueOf(threeDsTransactionEntityEnrollment.getCavv())));
                responses.add(new GetTransactionLegDetailResponse("XID", String.valueOf(threeDsTransactionEntityEnrollment.getXid())));
                responses.add(new GetTransactionLegDetailResponse("Previous Authentication", String.valueOf(threeDsTransactionEntityEnrollment.getPreviousAuthentication())));
                responses.add(new GetTransactionLegDetailResponse("DS Transaction ID", String.valueOf(threeDsTransactionEntityEnrollment.getDsTransactionId())));
                responses.add(new GetTransactionLegDetailResponse("Merchant MD", String.valueOf(threeDsTransactionEntityEnrollment.getMerchantMd())));

                break;

            case "EFT_OZOW_UPDATE":
                logger.info("EFT_OZOW_UPDATE leg found");
                OzowEftPaymentUpdateEntity ozowEftPaymentUpdateEntity = ozowEftPaymentUpdateRepository.findByTransactionLegId(transactionLegEntity.getId());
                responses.add(new GetTransactionLegDetailResponse("OZOW Transaction ID", String.valueOf(ozowEftPaymentUpdateEntity.getTransactionId())));
                responses.add(new GetTransactionLegDetailResponse("Amount", String.valueOf(ozowEftPaymentUpdateEntity.getAmount())));
                responses.add(new GetTransactionLegDetailResponse("Currency Code", String.valueOf(ozowEftPaymentUpdateEntity.getCurrencyCode())));
                responses.add(new GetTransactionLegDetailResponse("Site Code", String.valueOf(ozowEftPaymentUpdateEntity.getSiteCode())));
                responses.add(new GetTransactionLegDetailResponse("Status", String.valueOf(ozowEftPaymentUpdateEntity.getStatus())));
                responses.add(new GetTransactionLegDetailResponse("Status Message", String.valueOf(ozowEftPaymentUpdateEntity.getStatusMessage())));
                responses.add(new GetTransactionLegDetailResponse("Is Test", String.valueOf(ozowEftPaymentUpdateEntity.getTest())));
                responses.add(new GetTransactionLegDetailResponse("Transaction Reference", String.valueOf(ozowEftPaymentUpdateEntity.getTransactionReference())));
                break;
            case "EFT_ADUMO_CREATE":
                logger.info("EFT_ADUMO_CREATE leg found");
                StitchEftCreatePaymentRequestEntity stitchEftCreatePaymentRequestEntity = stitchEftCreatePaymentRequestRepository.findByTransactionLegId(transactionLegEntity.getId());
                StitchEftCreatePaymentResponseEntity stitchEftCreatePaymentResponseEntity = stitchEftCreatePaymentResponseRepository.findByTransactionLegId(transactionLegEntity.getId());
                responses.add(new GetTransactionLegDetailResponse("Beneficiary Bank ID", String.valueOf(stitchEftCreatePaymentRequestEntity.getBeneficiaryBankId())));
                responses.add(new GetTransactionLegDetailResponse("Amount", String.valueOf(stitchEftCreatePaymentRequestEntity.getAmount())));
                responses.add(new GetTransactionLegDetailResponse("Beneficiary Account Number", String.valueOf(stitchEftCreatePaymentRequestEntity.getBeneficiaryAccountNumber())));
                responses.add(new GetTransactionLegDetailResponse("Beneficiary Name", String.valueOf(stitchEftCreatePaymentRequestEntity.getBeneficiaryName())));
                responses.add(new GetTransactionLegDetailResponse("Beneficiary Reference", String.valueOf(stitchEftCreatePaymentRequestEntity.getBeneficiaryReference())));
                responses.add(new GetTransactionLegDetailResponse("Currency", String.valueOf(stitchEftCreatePaymentRequestEntity.getCurrency())));
                responses.add(new GetTransactionLegDetailResponse("External Reference", String.valueOf(stitchEftCreatePaymentRequestEntity.getExternalReference())));
                responses.add(new GetTransactionLegDetailResponse("Payer Reference", String.valueOf(stitchEftCreatePaymentRequestEntity.getPayerReference())));
                responses.add(new GetTransactionLegDetailResponse("Payment ID", String.valueOf(stitchEftCreatePaymentResponseEntity.getPaymentId())));
                responses.add(new GetTransactionLegDetailResponse("Payment URL", String.valueOf(stitchEftCreatePaymentResponseEntity.getPaymentUrl())));
                break;
            case "EFT_ADUMO_STATUS":
                logger.info("EFT_ADUMO_STATUS leg found");
                StitchEftGetPaymentResponseEntity stitchEftGetPaymentResponseEntity = stitchEftGetPaymentResponseRepository.findByTransactionLegId(transactionLegEntity.getId());
                responses.add(new GetTransactionLegDetailResponse("Payment ID", String.valueOf(stitchEftGetPaymentResponseEntity.getPaymentId())));
                responses.add(new GetTransactionLegDetailResponse("Payment URL", String.valueOf(stitchEftGetPaymentResponseEntity.getPaymentUrl())));
                responses.add(new GetTransactionLegDetailResponse("Beneficiary Bank ID", String.valueOf(stitchEftGetPaymentResponseEntity.getBeneficiaryBankId())));
                responses.add(new GetTransactionLegDetailResponse("Payer Bank ID", String.valueOf(stitchEftGetPaymentResponseEntity.getPayerBankId())));
                responses.add(new GetTransactionLegDetailResponse("Amount", String.valueOf(stitchEftGetPaymentResponseEntity.getAmount())));
                responses.add(new GetTransactionLegDetailResponse("Currency", String.valueOf(stitchEftGetPaymentResponseEntity.getCurrency())));
                responses.add(new GetTransactionLegDetailResponse("Payer Account Number", String.valueOf(stitchEftGetPaymentResponseEntity.getPayerAccountNumber())));
                responses.add(new GetTransactionLegDetailResponse("Payment Date", String.valueOf(stitchEftGetPaymentResponseEntity.getPaymentDate())));
                responses.add(new GetTransactionLegDetailResponse("State", String.valueOf(stitchEftGetPaymentResponseEntity.getState())));
                responses.add(new GetTransactionLegDetailResponse("User Reference", String.valueOf(stitchEftGetPaymentResponseEntity.getUserReference())));

                break;
            //TODO more cases, many more cases...

        }
        return responses;
    }

    @Override
    public List<TransactionState> getAllTransactionStates() {
        return transactionStateRepository
                .findAll()
                .stream()
                .map(TransactionState::new)
                .collect(Collectors.toList());
    }

    @Override
    public List<PaymentState> getPaymentStatesByPaymentTypeId(long paymentTypeId) {
        return paymentStateRepository.findAllByPaymentTypeId(paymentTypeId)
                .stream()
                .map(PaymentState::new)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void postPaymentState(long paymentTypeId, List<PostPaymentState> postPaymentState) {
        paymentStateRepository.deleteAllByPaymentTypeId(paymentTypeId);
        List<PaymentStateEntity> paymentStateEntities = postPaymentState.stream().map(postPaymentState1 -> {
            PaymentStateEntity paymentStateEntity = new PaymentStateEntity();
            paymentStateEntity.setTransactionStateId(postPaymentState1.getTransactionStateId());
            paymentStateEntity.setPaymentTypeId(postPaymentState1.getPaymentTypeId());
            logger.info("TRANSACTION_ID | " + paymentStateEntity.getTransactionStateId());
            logger.info("PAYMENT_TYPE_ID | " + paymentStateEntity.getTransactionStateId());
            return paymentStateEntity;
        }).collect(Collectors.toList());

        paymentStateRepository.saveAll(paymentStateEntities);
    }
    @Override
    public void setTransactionState(SetTransactionState setTransactionState){
        TransactionEntity transactionEntity = transactionRepository.findTransactionEntityByTransactionUid(setTransactionState.getTransactionUid()).orElseThrow(() -> new GenericException("Could not find transaction", HttpStatus.NOT_FOUND, "No transaction found for UID | " + setTransactionState.getTransactionUid()));
        TransactionStateEntity transactionStateEntity = transactionStateRepository.findByCode(setTransactionState.getTransactionState()).orElseThrow(() -> new GenericException("Could not find transaction state", HttpStatus.NOT_FOUND, "No transaction state found with code | " + setTransactionState.getTransactionState()));
        transactionEntity.setTransactionStateByTransactionStateId(transactionStateEntity);
        transactionRepository.save(transactionEntity);
        transactionRepository.flush();
    }
}
