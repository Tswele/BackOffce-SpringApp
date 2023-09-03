package za.co.wirecard.channel.backoffice.entities;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;

@Data
@Entity
@Table(name = "card_bigiso_transaction", schema = "dbo", catalog = "transaction_db")
public class CardBigisoTransactionEntity {
    @Id@Column(name = "id", nullable = false)
    private long id;
    @Basic@Column(name = "date_logged", nullable = true)
    private Timestamp dateLogged;
    @Basic@Column(name = "tokendata_20_life_cycle_ind", nullable = true, length = 50)
    private String tokendata20LifeCycleInd;
    @Basic@Column(name = "tokendata_20_trace_id", nullable = true, length = 50)
    private String tokendata20TraceId;
    @Basic@Column(name = "tokendata_20_valid_cde", nullable = true)
    private String tokendata20ValidCde;
    @Basic@Column(name = "tokendata_20_monitoring_stat", nullable = true, length = 50)
    private String tokendata20MonitoringStat;
    @Basic@Column(name = "tokendata_20_err_ind", nullable = true, length = 50)
    private String tokendata20ErrInd;
    @Basic@Column(name = "tokendata_qk_mg_trx_idx", nullable = true, length = 2147483647)
    private String tokendataQkMgTrxIdx;
    @Basic@Column(name = "transaction_id", nullable = true)
    private Long transactionId;
    @Basic@Column(name = "systems_trace_audit_num", nullable = true, length = 2147483647)
    private String systemsTraceAuditNum;
    @Basic@Column(name = "retrieval_reference_num", nullable = true, length = 2147483647)
    private String retrievalReferenceNum;
    @Basic@Column(name = "tokendata_f4_wallet_ind_flg", nullable = true, length = 50)
    private String tokendataF4WalletIndFlg;
    @Basic@Column(name = "tokendata_f4_visa_dgtl_entity_04_id", nullable = true, length = 50)
    private String tokendataF4VisaDgtlEntity04Id;
    @Basic@Column(name = "processing_code_transaction_type", nullable = true, length = 50)
    private String processingCodeTransactionType;
    @Basic@Column(name = "pos_auth_indicators_auth_indicators1", nullable = true, length = 50)
    private String posAuthIndicatorsAuthIndicators1;
    @Basic@Column(name = "transaction_type", nullable = true, length = 50)
    private String transactionType;
    @Basic@Column(name = "pos_settlement_data_draft_capture_flag", nullable = true, length = 50)
    private String posSettlementDataDraftCaptureFlag;
    @Basic@Column(name = "tokendata_ch_resp_src_rsn_cde", nullable = true, length = 50)
    private String tokendataChRespSrcRsnCde;
    @Basic@Column(name = "tokendata_ch_crd_vrfy_flg2", nullable = true, length = 50)
    private String tokendataChCrdVrfyFlg2;
    @Basic@Column(name = "tokendata_ch_online_lmt", nullable = true, length = 50)
    private String tokendataChOnlineLmt;
    @Basic@Column(name = "tokendata_ch_retl_class_cde", nullable = true, length = 50)
    private String tokendataChRetlClassCde;
    @Basic@Column(name = "tokendata_ch_emv_capable_outlet", nullable = true, length = 50)
    private String tokendataChEmvCapableOutlet;
    @Basic@Column(name = "tokendata_ch_recur_pmnt_ind", nullable = true, length = 50)
    private String tokendataChRecurPmntInd;
    @Basic@Column(name = "tokendata_ch_num_instl", nullable = true, length = 50)
    private String tokendataChNumInstl;
    @Basic@Column(name = "tokendata_ch_num_mm_gratuity", nullable = true, length = 50)
    private String tokendataChNumMmGratuity;
    @Basic@Column(name = "tokendata_ch_pmnt_plan", nullable = true, length = 50)
    private String tokendataChPmntPlan;
    @Basic@Column(name = "tokendata_ch_term_output_cap_ind", nullable = true, length = 50)
    private String tokendataChTermOutputCapInd;
    @Basic@Column(name = "tokendata_ch_crdhldr_authn_cap_ind", nullable = true, length = 50)
    private String tokendataChCrdhldrAuthnCapInd;
    @Basic@Column(name = "tokendata_ch_partial_auth_opt", nullable = true, length = 50)
    private String tokendataChPartialAuthOpt;
    @Basic@Column(name = "tokendata_ch_instl_plan_typ", nullable = true, length = 50)
    private String tokendataChInstlPlanTyp;
    @Basic@Column(name = "tokendata_ch_instl_gratuity_prd", nullable = true, length = 50)
    private String tokendataChInstlGratuityPrd;
    @Basic@Column(name = "tokendata_ch_rvsl_rsn_ind", nullable = true, length = 50)
    private String tokendataChRvslRsnInd;
    @Basic@Column(name = "tokendata_ch_failed_cvm_alwd", nullable = true, length = 50)
    private String tokendataChFailedCvmAlwd;
    @Basic@Column(name = "tokendata_ch_dup_chk_req", nullable = true, length = 50)
    private String tokendataChDupChkReq;
    @Basic@Column(name = "tokendata_ch_auth_msg_ind", nullable = true, length = 50)
    private String tokendataChAuthMsgInd;
    @Basic@Column(name = "tokendata_ch_term_typ", nullable = true, length = 50)
    private String tokendataChTermTyp;
    @Basic@Column(name = "tokendata_ch_pmnt_info", nullable = true, length = 50)
    private String tokendataChPmntInfo;
    @Basic@Column(name = "processing_code_account_type_from", nullable = true, length = 50)
    private String processingCodeAccountTypeFrom;
    @Basic@Column(name = "processing_code_account_type_to", nullable = true, length = 50)
    private String processingCodeAccountTypeTo;
    @Basic@Column(name = "pan", nullable = true, length = 2147483647)
    private String pan;
    @Basic@Column(name = "track2", nullable = true, length = 2147483647)
    private String track2;
    @Basic@Column(name = "pos_settlement_data_services", nullable = true, length = 50)
    private String posSettlementDataServices;
    @Basic@Column(name = "pos_settlement_data_originator", nullable = true, length = 50)
    private String posSettlementDataOriginator;
    @Basic@Column(name = "pos_settlement_data_destination", nullable = true, length = 50)
    private String posSettlementDataDestination;
    @Basic@Column(name = "pos_settlement_data_settlement_flag", nullable = true, length = 50)
    private String posSettlementDataSettlementFlag;
    @Basic@Column(name = "settlement_date", nullable = true, length = 50)
    private String settlementDate;
    @Basic@Column(name = "purchase_time", nullable = true, length = 50)
    private String purchaseTime;
    @Basic@Column(name = "purchase_date", nullable = true, length = 50)
    private String purchaseDate;
    @Basic@Column(name = "capture_date", nullable = true, length = 50)
    private String captureDate;
    @Basic@Column(name = "pos_settlement_data", nullable = true, length = 50)
    private String posSettlementData;
    @Basic@Column(name = "amount", nullable = true, precision = 2)
    private BigDecimal amount;
    @Basic@Column(name = "expiry_date", nullable = true, length = 50)
    private String expiryDate;
    @Basic@Column(name = "merchant_type", nullable = true, length = 50)
    private String merchantType;
    @Basic@Column(name = "tokendata_ci_e_comm_goods_ind", nullable = true, length = 50)
    private String tokendataCiECommGoodsInd;
    @Basic@Column(name = "tokendata_ci_deferred_billing_ind", nullable = true, length = 50)
    private String tokendataCiDeferredBillingInd;
    @Basic@Column(name = "tokendata_ci_reln_participant_ind", nullable = true, length = 50)
    private String tokendataCiRelnParticipantInd;
    @Basic@Column(name = "tokendata_ci_dpc_num", nullable = true, length = 50)
    private String tokendataCiDpcNum;
    @Basic@Column(name = "tokendata_ci_pinpad_id", nullable = true, length = 2147483647)
    private String tokendataCiPinpadId;
    @Basic@Column(name = "tokendata_ci_acq_term_id", nullable = true, length = 2147483647)
    private String tokendataCiAcqTermId;
    @Basic@Column(name = "tokendata_ci_rcncl_ent", nullable = true, length = 50)
    private String tokendataCiRcnclEnt;
    @Basic@Column(name = "tokendata_ci_acq_termpost_dat", nullable = true, length = 50)
    private String tokendataCiAcqTermpostDat;
    @Basic@Column(name = "tokendata_ci_pre_auth_chrgbk", nullable = true, length = 50)
    private String tokendataCiPreAuthChrgbk;
    @Basic@Column(name = "tokendata_ci_enhanced_pre_auth", nullable = true, length = 50)
    private String tokendataCiEnhancedPreAuth;
    @Basic@Column(name = "tokendata_ci_adnl_resp_cde", nullable = true, length = 50)
    private String tokendataCiAdnlRespCde;
    @Basic@Column(name = "tokendata_ci_mc_elec_accpt_ind", nullable = true, length = 50)
    private String tokendataCiMcElecAccptInd;
    @Basic@Column(name = "tokendata_ci_mrch_id", nullable = true, length = 2147483647)
    private String tokendataCiMrchId;
    @Basic@Column(name = "tokendata_ci_pgm_ind", nullable = true, length = 50)
    private String tokendataCiPgmInd;
    @Basic@Column(name = "tokendata_ci_user_fld1", nullable = true, length = 50)
    private String tokendataCiUserFld1;
    @Basic@Column(name = "tokendata_ci_spcl_pos_cond", nullable = true, length = 50)
    private String tokendataCiSpclPosCond;
    @Basic@Column(name = "pos_entry_mode_pan_entry_mode", nullable = true, length = 50)
    private String posEntryModePanEntryMode;
    @Basic@Column(name = "pos_condition_code", nullable = true, length = 50)
    private String posConditionCode;
    @Basic@Column(name = "acquiring_institution_id_code", nullable = true, length = 2147483647)
    private String acquiringInstitutionIdCode;
    @Basic@Column(name = "card_acceptor_terminal_id", nullable = true, length = 2147483647)
    private String cardAcceptorTerminalId;
    @Basic@Column(name = "card_acceptor_id_code", nullable = true, length = 2147483647)
    private String cardAcceptorIdCode;
    @Basic@Column(name = "card_acceptor_name", nullable = true, length = 50)
    private String cardAcceptorName;
    @Basic@Column(name = "card_acceptor_city", nullable = true, length = 50)
    private String cardAcceptorCity;
    @Basic@Column(name = "card_acceptor_state", nullable = true, length = 50)
    private String cardAcceptorState;
    @Basic@Column(name = "card_acceptor_country", nullable = true, length = 50)
    private String cardAcceptorCountry;
    @Basic@Column(name = "additional_data_retailer_id", nullable = true, length = 2147483647)
    private String additionalDataRetailerId;
    @Basic@Column(name = "currency_code", nullable = true, length = 2147483647)
    private String currencyCode;
    @Basic@Column(name = "pos_terminal_data_fiid", nullable = true, length = 2147483647)
    private String posTerminalDataFiid;
    @Basic@Column(name = "pos_terminal_data_dev", nullable = true, length = 2147483647)
    private String posTerminalDataDev;
    @Basic@Column(name = "pos_terminal_data_time_offset", nullable = true, length = 2147483647)
    private String posTerminalDataTimeOffset;
    @Basic@Column(name = "pos_card_issuer_fiid", nullable = true, length = 2147483647)
    private String posCardIssuerFiid;
    @Basic@Column(name = "pos_card_issuer_dev", nullable = true, length = 2147483647)
    private String posCardIssuerDev;
    @Basic@Column(name = "receiving_institution_id_code", nullable = true, length = 11)
    private String receivingInstitutionIdCode;
    @Basic@Column(name = "pos_auth_indicators_clerk_id", nullable = true, length = 50)
    private String posAuthIndicatorsClerkId;
    @Basic@Column(name = "pos_auth_indicators_crt_auth_group", nullable = true, length = 50)
    private String posAuthIndicatorsCrtAuthGroup;
    @Basic@Column(name = "pos_auth_indicators_crt_auth_user_id", nullable = true, length = 50)
    private String posAuthIndicatorsCrtAuthUserId;
    @Basic@Column(name = "pos_auth_indicators_auth_indicators", nullable = true, length = 50)
    private String posAuthIndicatorsAuthIndicators;
    @Basic@Column(name = "pos_batch_data_batch_seq_num", nullable = true, length = 50)
    private String posBatchDataBatchSeqNum;
    @Basic@Column(name = "pos_batch_data_batch_num", nullable = true, length = 50)
    private String posBatchDataBatchNum;
    @Basic@Column(name = "pos_batch_data_shift_num", nullable = true, length = 50)
    private String posBatchDataShiftNum;
    @Basic@Column(name = "pos_pre_auth_chargeback_reason_for_chargeback", nullable = true, length = 50)
    private String posPreAuthChargebackReasonForChargeback;
    @Basic@Column(name = "pos_pre_auth_chargeback_num_of_chargeback", nullable = true, length = 50)
    private String posPreAuthChargebackNumOfChargeback;
    @Basic@Column(name = "electronic_coerceindicator", nullable = true, length = 50)
    private String electronicCoerceindicator;
    @Basic@Column(name = "cardholder_authenticationid", nullable = true, length = 2147483647)
    private String cardholderAuthenticationid;
    @Basic@Column(name = "cardholder_authenticationdata", nullable = true, length = 2147483647)
    private String cardholderAuthenticationdata;
    @Basic@Column(name = "budget", nullable = true, length = 50)
    private String budget;
    @Basic@Column(name = "budget_period", nullable = true, length = 50)
    private String budgetPeriod;
    @Basic@Column(name = "pos_terminal_data_budget_period", nullable = true, length = 50)
    private String posTerminalDataBudgetPeriod;
    @Basic@Column(name = "tokendata_c0_adnl_data_ind", nullable = true, length = 50)
    private String tokendataC0AdnlDataInd;
    @Basic@Column(name = "pos_entry_mode_pin_capability", nullable = true, length = 50)
    private String posEntryModePinCapability;
    @Basic@Column(name = "tokendata_c6_xid", nullable = true, length = 2147483647)
    private String tokendataC6Xid;
    @Basic@Column(name = "tokendata_c6_trans_stain", nullable = true, length = 2147483647)
    private String tokendataC6TransStain;
    @Basic@Column(name = "tokendata_ce_mastercard_aav", nullable = true, length = 2147483647)
    private String tokendataCeMastercardAav;
    @Basic@Column(name = "tokendata_ce_auth_ind_flg", nullable = true, length = 50)
    private String tokendataCeAuthIndFlg;
    @Basic@Column(name = "tokendata_c4_term_attend_ind", nullable = true, length = 50)
    private String tokendataC4TermAttendInd;
    @Basic@Column(name = "tokendata_c4_term_loc_ind", nullable = true, length = 50)
    private String tokendataC4TermLocInd;
    @Basic@Column(name = "tokendata_c4_crdhldr_present_ind", nullable = true, length = 50)
    private String tokendataC4CrdhldrPresentInd;
    @Basic@Column(name = "tokendata_c4_crdhldr_actvt_term_ind", nullable = true, length = 50)
    private String tokendataC4CrdhldrActvtTermInd;
    @Basic@Column(name = "tokendata_c4_term_input_cap_ind", nullable = true, length = 50)
    private String tokendataC4TermInputCapInd;
    @Basic@Column(name = "tokendata_c4_term_oper_ind", nullable = true, length = 50)
    private String tokendataC4TermOperInd;
    @Basic@Column(name = "tokendata_c4_crd_present_ind", nullable = true, length = 50)
    private String tokendataC4CrdPresentInd;
    @Basic@Column(name = "tokendata_c4_crd_captr_ind", nullable = true, length = 50)
    private String tokendataC4CrdCaptrInd;
    @Basic@Column(name = "tokendata_c4_txn_stat_ind", nullable = true, length = 50)
    private String tokendataC4TxnStatInd;
    @Basic@Column(name = "tokendata_c4_txn_sec_ind", nullable = true, length = 50)
    private String tokendataC4TxnSecInd;
    @Basic@Column(name = "tokendata_c4_txn_rtn_ind", nullable = true, length = 50)
    private String tokendataC4TxnRtnInd;
    @Basic@Column(name = "tokendata_c4_crdhldr_id_method", nullable = true, length = 50)
    private String tokendataC4CrdhldrIdMethod;
    @Basic@Column(name = "tokendata_c0_e_com_flg", nullable = true, length = 50)
    private String tokendataC0EComFlg;
    @Basic@Column(name = "tokendata_c0_frd_prn_flg", nullable = true, length = 50)
    private String tokendataC0FrdPrnFlg;
    @Basic@Column(name = "tokendata_c0_authn_coll_ind", nullable = true, length = 50)
    private String tokendataC0AuthnCollInd;
    @ManyToOne@JoinColumn(name = "transaction_leg_id", referencedColumnName = "id", nullable = false)
    private TransactionLegEntity transactionLegByTransactionLegId;

}
