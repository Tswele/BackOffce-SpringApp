package za.co.wirecard.channel.backoffice.services;

import org.springframework.http.ResponseEntity;
import za.co.wirecard.channel.backoffice.dto.models.*;
import za.co.wirecard.channel.backoffice.dto.models.requests.*;
import za.co.wirecard.channel.backoffice.dto.models.responses.*;
import za.co.wirecard.channel.backoffice.entities.*;
import za.co.wirecard.channel.backoffice.exceptions.ExceptionExtender;
import za.co.wirecard.channel.backoffice.models.*;

import java.util.List;

public interface PaymentTypeConfigurationService {

    List<PaymentType> getAvailablePaymentTypes(long merchantId, long applicationId);

    ApplicationPaymentTypes getApplicationPaymentTypes(long merchantId, long applicationId);

    ApplicationPaymentTypeStatus getApplicationPaymentType(long paymentTypeId, long applicationId);

    ResponseEntity<?> createWalletApplicationConfig(CreateWalletConfigRequest createWalletConfigRequest);

    WalletConfigResponse getWalletApplicationConfig(long applicationId);

    ResponseEntity<?> editWalletApplicationConfig(EditWalletConfigRequest editWalletConfigRequest);

    ResponseEntity<?> createOttVoucherApplicationConfig(CreateOttVoucherConfigRequest createOttVoucherConfigRequest);

    OttVoucherConfigResponse getOttVoucherApplicationConfig(long applicationId);

    ResponseEntity<?> editOttVoucherApplicationConfig(EditOttVoucherConfigRequest editOttVoucherConfigRequest);

    ResponseEntity<?> createDisbursementApplicationConfig(CreateDisbursementConfigRequest createDisbursementConfigRequest);

    DisbursementConfigResponse getDisbursementApplicationConfig(long applicationId);

    ResponseEntity<?> editDisbursementApplicationConfig(EditDisbursementConfigRequest editDisbursementConfigRequest);

    ResponseEntity<?> createEftApplicationConfig(CreateEftConfigRequest createEftConfigRequest);

    EftConfigResponse getEftApplicationConfig(long applicationId);

    ResponseEntity<?> editEftApplicationConfig(EditEftConfigRequest editEftConfigRequest);

    ResponseEntity<?> createMobicredApplicationConfig(CreateMobicredConfigRequest createMobicredConfigRequest);

    MobicredConfigResponse getMobicredApplicationConfig(long applicationId);

    ResponseEntity<?> editMobicredApplicationConfig(EditMobicredConfigRequest editMobicredConfigRequest);

    ResponseEntity<?> createMasterpassApplicationConfig(CreateMasterpassConfigRequest createMasterpassConfigRequest);

    MasterpassConfigResponse getMasterpassApplicationConfig(long applicationId);

    ResponseEntity<?> editMasterpassApplicationConfig(EditMasterpassConfigRequest editMasterpassConfigRequest);

    ResponseEntity<?> createZapperApplicationConfig(CreateZapperConfigRequest createZapperConfigRequest);

    ZapperConfigResponse getZapperApplicationConfig(long applicationId);

    ResponseEntity<?> editZapperApplicationConfig(EditZapperConfigRequest editZapperConfigRequest);

    ResponseEntity<?> createOzowApplicationConfig(CreateOzowConfigRequest createOzowConfigRequest);

    OzowConfigResponse getOzowApplicationConfig(long applicationId);

    ResponseEntity<?> editOzowApplicationConfig(EditOzowConfigRequest editOzowConfigRequest);

    ResponseEntity<?> createStitchApplicationConfig(CreateStitchConfigRequest createStitchConfigRequest);

    StitchConfigResponse getStitchApplicationConfig(long applicationId);

    ResponseEntity<?> editStitchApplicationConfig(EditStitchConfigRequest editStitchConfigRequest);

    List<CardTypeEntity> getCardTypes();

    List<MerchantTypeEntity> getMerchantTypes();

    List<TokenizationMethodEntity> getTokenizationMethods();

    List<EciEntity> getEcis();

    ResponseEntity<?> createCardConfigurationForApplication(long merchantId, long applicationId, long cardFlowId, CreateCardFlowConfigRequest createCardFlowConfigRequest);

//    ResponseEntity<?> createCardOnFileConfigurationForApplication(long merchantId, long applicationId, CreateCardFlowConfigRequest createCardFlowConfigRequest);
//
//    public ResponseEntity<?> createOneClickConfigurationForApplication(long merchantId, long applicationId, CreateCardFlowConfigRequest createCardFlowConfigRequest);
//
//    ResponseEntity<?> createRecurringConfigurationForApplication(long merchantId, long applicationId, CreateCardFlowConfigRequest createCardFlowConfigRequest);

    List<GetCardFlowResponse> getAllCardConfigurationsForApplication(long applicationId);

    GetCardFlowResponse getCardConfigurationForApplication(long applicationId, long cardConfigurationId);

//    GetCardFlowResponse getCardOnFileConfigurationForApplication(long applicationId);
//
//    GetCardFlowResponse getOneClickConfigurationForApplication(long applicationId);
//
//    GetCardFlowResponse getRecurringConfigurationForApplication(long applicationId);

    ResponseEntity<?> editCardConfigurationForApplication(long merchantId, long applicationId, long cardFlowId, CreateCardFlowConfigRequest createCardFlowConfigRequest);

//    ResponseEntity<?> editCardOnFileConfigurationForApplication(long merchantId, long applicationId, CreateCardFlowConfigRequest createCardFlowConfigRequest);
//
//    ResponseEntity<?> editOneClickConfigurationForApplication(long merchantId, long applicationId, CreateCardFlowConfigRequest createCardFlowConfigRequest);
//
//    ResponseEntity<?> editRecurringConfigurationForApplication(long merchantId, long applicationId, CreateCardFlowConfigRequest createCardFlowConfigRequest);


    void createEftConfig(PlatformCreateEftInterfaceConfigRequest eftConfig);

    EftInterfaceConfig getEftConfigByInterfaceId(long interfaceId);

    void putEftConfigByInterfaceId(long interfaceId, EftInterfaceConfig eftInterfaceConfig);

    void createMobicredConfig(PlatformCreateMobicredInterfaceConfigRequest mobicredConfig);

    void putMobicredConfigByInterfaceId(String interfaceId, MobicredInterfaceConfig mobicredInterfaceConfig);

    MobicredInterfaceConfig getMobicredConfigByInterfaceId(String interfaceId);

    List<Eci> getEci();

    List<Eci> getEciByInterfaceId(long interfaceId);

    void putEciByInterfaceId(long interfaceId, List<PlatformCreateCardInterfaceConfigRequest> platformCreateCardInterfaceConfigRequests);

    void createCardConfig(List<PlatformCreateCardInterfaceConfigRequest> cardConfig);

    void createMasterpassConfig(PlatformCreateMasterpassConfigRequest masterpassConfig) throws ExceptionExtender;

    void putMasterpassByInterfaceId(String interfaceId, MasterpassConfig masterpassConfig);

    MasterpassConfig getMasterpassConfigByInterfaceId(String interfaceId);

    void createZapperConfig(PlatformCreateZapperConfigRequest platformCreateZapperConfigRequest);

    void putZapperByInterfaceId(String interfaceId, ZapperConfig zapperConfig);

    ZapperConfig getZapperConfigByInterfaceId(String interfaceId);

    InterfaceConfigDataResponse getInterfaceConfigData();

    List<CardFlowEntity> getCardFlows(long merchantId, long applicationId);

    List<CardTypeGroupEntity> getCardTypeGroupsByCardFlow(long cardFlowId, long applicationId);

    CardConfigurationEntity getCardConfigurationByCardTypeGroupId(long cardTypeGroupId);

    List<TdsMethodEntity> getTdsMethods();

    List<Gateway> getGateways();

    Boolean checkConflictingCardConfigurations(long cardConfigurationId, long applicationId);

    void deactivateConflictingCardConfigsAndActivateCurrentCardConfig(long cardConfigurationId, long applicationId);

    void deactivateCardConfig(long cardConfigurationId, long applicationId);

    Boolean interfaceExists(long merchantId, long applicationId);

    List<Gateway> getCardGateways();

    StitchConfigData getStitchBanks();
}
