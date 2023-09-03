package za.co.wirecard.channel.backoffice.entities;

import lombok.*;

import javax.persistence.*;

@Data
@Entity
@Table(name = "cardinal_lookup_response", schema = "dbo", catalog = "transaction_db")
public class CardinalLookupResponseEntity {
    @Id@Column(name = "id")
    private long id;
    @Basic@Column(name = "three_ds_version")
    private String threeDsVersion;
    @Basic@Column(name = "enrolled")
    private String enrolled;
    @Basic@Column(name = "error_desc")
    private String errorDesc;
    @Basic@Column(name = "error_no")
    private String errorNo;
    @Basic@Column(name = "eci_flag")
    private String eciFlag;
    @Basic@Column(name = "order_id")
    private String orderId;
    @Basic@Column(name = "transaction_id")
    private String transactionId;
    @Basic@Column(name = "transaction_type")
    private String transactionType;
    @Basic@Column(name = "signature_verification")
    private String signatureVerification;
    @Basic@Column(name = "card_brand")
    private String cardBrand;
    @Basic@Column(name = "card_bin")
    private String cardBin;
    @Basic@Column(name = "ds_transaction_id")
    private String dsTransactionId;
    @Basic@Column(name = "raw_acs_url")
    private String rawAcsUrl;
    @Basic@Column(name = "acs_url")
    private String acsUrl;
    @Basic@Column(name = "step_up_url")
    private String stepUpUrl;
    @Basic@Column(name = "cavv")
    private String cavv;
    @Basic@Column(name = "pa_res_status")
    private String paResStatus;
    @Basic@Column(name = "payload")
    private String payload;
    @Basic@Column(name = "xid")
    private String xid;
    @Basic@Column(name = "cavv_algorithm")
    private String cavvAlgorithm;
    @Basic@Column(name = "merchant_reference_number")
    private String merchantReferenceNumber;
    @Basic@Column(name = "ucaf_indicator")
    private String ucafIndicator;
    @Basic@Column(name = "decoupled_indicator")
    private String decoupledIndicator;
    @Basic@Column(name = "reason_code")
    private String reasonCode;
    @Basic@Column(name = "reason_desc")
    private String reasonDesc;
    @Basic@Column(name = "warning")
    private String warning;
    @Basic@Column(name = "card_holder_info")
    private String cardHolderInfo;
    @Basic@Column(name = "acs_rendering_type")
    private String acsRenderingType;
    @Basic@Column(name = "authentication_type")
    private String authenticationType;
    @Basic@Column(name = "challenge_required")
    private String challengeRequired;
    @Basic@Column(name = "status_reason")
    private String statusReason;
    @Basic@Column(name = "acs_transaction_id")
    private String acsTransactionId;
    @Basic@Column(name = "three_ds_server_transaction_id")
    private String threeDsServerTransactionId;
    @Basic@Column(name = "sdk_flow_type")
    private String sdkFlowType;
    @Basic@Column(name = "third_party_token")
    private String thirdPartyToken;
    @Basic@Column(name = "token")
    private String token;
    @Basic@Column(name = "white_list_status")
    private String whiteListStatus;
    @Basic@Column(name = "white_list_status_source")
    private String whiteListStatusSource;
    @Basic@Column(name = "network_score")
    private String networkScore;
    @Basic@Column(name = "idci_reason_code_2")
    private String idciReasonCode2;
    @Basic@Column(name = "idci_reason_code_1")
    private String idciReasonCode1;
    @Basic@Column(name = "authorization_payload")
    private String authorizationPayload;
    @Basic@Column(name = "ivr_enabled_message")
    private Boolean ivrEnabledMessage;
    @Basic@Column(name = "ivr_encryption_key")
    private String ivrEncryptionKey;
    @Basic@Column(name = "ivr_encryption_mandatory")
    private Boolean ivrEncryptionMandatory;
    @Basic@Column(name = "ivr_encryption_type")
    private String ivrEncryptionType;
    @Basic@Column(name = "ivr_label")
    private String ivrLabel;
    @Basic@Column(name = "ivr_prompt")
    private String ivrPrompt;
    @Basic@Column(name = "ivr_status_message")
    private String ivrStatusMessage;
    @Basic@Column(name = "idci_score")
    private String idciScore;
    @Basic@Column(name = "idci_decision")
    private String idciDecision;
    @Basic@Column(name = "transaction_leg_id")
    private long transactionLegId;

}
