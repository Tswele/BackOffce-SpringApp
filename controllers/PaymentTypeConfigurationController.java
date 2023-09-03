package za.co.wirecard.channel.backoffice.controllers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import za.co.wirecard.channel.backoffice.dto.models.*;
import za.co.wirecard.channel.backoffice.dto.models.requests.*;
import za.co.wirecard.channel.backoffice.dto.models.responses.CurrentCardTypeGroups;
import za.co.wirecard.channel.backoffice.entities.CardFlowEntity;
import za.co.wirecard.channel.backoffice.entities.CardTypeEntity;
import za.co.wirecard.channel.backoffice.entities.CardTypeGroupEntity;
import za.co.wirecard.channel.backoffice.entities.EciEntity;
import za.co.wirecard.channel.backoffice.models.CardTypeEntityGroupContainer;
import za.co.wirecard.channel.backoffice.repositories.CardFlowRepository;
import za.co.wirecard.channel.backoffice.repositories.CardTypeRepository;
import za.co.wirecard.channel.backoffice.services.PaymentTypeConfigurationService;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin
@RequestMapping(value = "/channel-back-office/api/v1/clients/application/payment-type")
public class PaymentTypeConfigurationController {

    private final PaymentTypeConfigurationService paymentTypeConfigurationService;
    private final CardTypeRepository cardTypeRepository;
    private final CardFlowRepository cardFlowRepository;

    private static final Logger logger = LogManager.getLogger(PaymentTypeConfigurationService.class);

    public PaymentTypeConfigurationController(PaymentTypeConfigurationService paymentTypeConfigurationService, CardTypeRepository cardTypeRepository, CardFlowRepository cardFlowRepository) {
        this.paymentTypeConfigurationService = paymentTypeConfigurationService;
        this.cardTypeRepository = cardTypeRepository;
        this.cardFlowRepository = cardFlowRepository;
    }

    @GetMapping("available-payment-types/{merchantId}/{applicationId}")
    public ResponseEntity<?> getAvailablePaymentTypes(@PathVariable long merchantId, @PathVariable long applicationId) {
        return ResponseEntity.ok(paymentTypeConfigurationService.getAvailablePaymentTypes(merchantId, applicationId));
    }

    @GetMapping("")
    public ResponseEntity<?> getApplicationPaymentTypes(@RequestParam long merchantId, @RequestParam long applicationId) {
        return ResponseEntity.ok(paymentTypeConfigurationService.getApplicationPaymentTypes(merchantId, applicationId));
    }

    @GetMapping("status")
    public ResponseEntity<?> getApplicationPaymentType(@RequestParam long paymentTypeId, @RequestParam long applicationId) {
        return ResponseEntity.ok(paymentTypeConfigurationService.getApplicationPaymentType(paymentTypeId, applicationId));
    }

    @PostMapping("wallet")
    public ResponseEntity<?> createWalletApplicationConfig(@RequestBody CreateWalletConfigRequest createWalletConfigRequest) {
        return ResponseEntity.ok(paymentTypeConfigurationService.createWalletApplicationConfig(createWalletConfigRequest));
    }

    @GetMapping("wallet")
    public ResponseEntity<?> getWalletApplicationConfig(@RequestParam long applicationId) {
        return ResponseEntity.ok(paymentTypeConfigurationService.getWalletApplicationConfig(applicationId));
    }

    @PutMapping("wallet")
    public ResponseEntity<?> editWalletApplicationConfig(@RequestBody EditWalletConfigRequest editWalletConfigRequest) {
        return ResponseEntity.ok(paymentTypeConfigurationService.editWalletApplicationConfig(editWalletConfigRequest));
    }

    @PostMapping("ott-voucher")
    public ResponseEntity<?> createOttVoucherApplicationConfig(@RequestBody CreateOttVoucherConfigRequest createOttVoucherConfigRequest) {
        return ResponseEntity.ok(paymentTypeConfigurationService.createOttVoucherApplicationConfig(createOttVoucherConfigRequest));
    }

    @GetMapping("ott-voucher")
    public ResponseEntity<?> getOttVoucherApplicationConfig(@RequestParam long applicationId) {
        return ResponseEntity.ok(paymentTypeConfigurationService.getOttVoucherApplicationConfig(applicationId));
    }

    @PutMapping("ott-voucher")
    public ResponseEntity<?> editOttVoucherApplicationConfig(@RequestBody EditOttVoucherConfigRequest editOttVoucherConfigRequest) {
        return ResponseEntity.ok(paymentTypeConfigurationService.editOttVoucherApplicationConfig(editOttVoucherConfigRequest));
    }

    @PostMapping("disbursement")
    public ResponseEntity<?> createDisbursementApplicationConfig(@RequestBody CreateDisbursementConfigRequest createDisbursementConfigRequest) {
        return ResponseEntity.ok(paymentTypeConfigurationService.createDisbursementApplicationConfig(createDisbursementConfigRequest));
    }

    @GetMapping("disbursement")
    public ResponseEntity<?> getDisbursementApplicationConfig(@RequestParam long applicationId) {
        return ResponseEntity.ok(paymentTypeConfigurationService.getDisbursementApplicationConfig(applicationId));
    }

    @PutMapping("disbursement")
    public ResponseEntity<?> editDisbursementApplicationConfig(@RequestBody EditDisbursementConfigRequest editDisbursementConfigRequest) {
        return ResponseEntity.ok(paymentTypeConfigurationService.editDisbursementApplicationConfig(editDisbursementConfigRequest));
    }

    @PostMapping("eft")
    public ResponseEntity<?> createEftApplicationConfig(@RequestBody CreateEftConfigRequest createEftConfigRequest) {
        return ResponseEntity.ok(paymentTypeConfigurationService.createEftApplicationConfig(createEftConfigRequest));
    }

    @GetMapping("eft")
    public ResponseEntity<?> getEftApplicationConfig(@RequestParam long applicationId) {
        return ResponseEntity.ok(paymentTypeConfigurationService.getEftApplicationConfig(applicationId));
    }

    @PutMapping("eft")
    public ResponseEntity<?> editEftApplicationConfig(@RequestBody EditEftConfigRequest editEftConfigRequest) {
        return ResponseEntity.ok(paymentTypeConfigurationService.editEftApplicationConfig(editEftConfigRequest));
    }

    @PostMapping("mobicred")
    public ResponseEntity<?> createMobicredApplicationConfig(@RequestBody CreateMobicredConfigRequest createMobicredConfigRequest) {
        return ResponseEntity.ok(paymentTypeConfigurationService.createMobicredApplicationConfig(createMobicredConfigRequest));
    }

    @GetMapping("mobicred")
    public ResponseEntity<?> getMobicredApplicationConfig(@RequestParam long applicationId) {
        return ResponseEntity.ok(paymentTypeConfigurationService.getMobicredApplicationConfig(applicationId));
    }

    @PutMapping("mobicred")
    public ResponseEntity<?> editMobicredApplicationConfig(@RequestBody EditMobicredConfigRequest editMobicredConfigRequest) {
        logger.info("yyy " + editMobicredConfigRequest.toString());
        logger.info("yyy " + editMobicredConfigRequest.getCMerchantId() + " | " + editMobicredConfigRequest.getCMerchantKey());
        return ResponseEntity.ok(paymentTypeConfigurationService.editMobicredApplicationConfig(editMobicredConfigRequest));
    }

    @PostMapping("masterpass")
    public ResponseEntity<?> createMasterpassApplicationConfig(@RequestBody CreateMasterpassConfigRequest createMasterpassConfigRequest) {
        return ResponseEntity.ok(paymentTypeConfigurationService.createMasterpassApplicationConfig(createMasterpassConfigRequest));
    }

    @GetMapping("masterpass")
    public ResponseEntity<?> getMasterpassApplicationConfig(@RequestParam long applicationId) {
        return ResponseEntity.ok(paymentTypeConfigurationService.getMasterpassApplicationConfig(applicationId));
    }

    @PutMapping("masterpass")
    public ResponseEntity<?> editMasterpassApplicationConfig(@RequestBody EditMasterpassConfigRequest editMasterpassConfigRequest) {
        return ResponseEntity.ok(paymentTypeConfigurationService.editMasterpassApplicationConfig(editMasterpassConfigRequest));
    }

    @PostMapping("zapper")
    public ResponseEntity<?> createZapperApplicationConfig(@RequestBody CreateZapperConfigRequest createZapperConfigRequest) {
        return ResponseEntity.ok(paymentTypeConfigurationService.createZapperApplicationConfig(createZapperConfigRequest));
    }

    @GetMapping("zapper")
    public ResponseEntity<?> getZapperApplicationConfig(@RequestParam long applicationId) {
        return ResponseEntity.ok(paymentTypeConfigurationService.getZapperApplicationConfig(applicationId));
    }

    @PutMapping("zapper")
    public ResponseEntity<?> editZapperApplicationConfig(@RequestBody EditZapperConfigRequest editZapperConfigRequest) {
        return ResponseEntity.ok(paymentTypeConfigurationService.editZapperApplicationConfig(editZapperConfigRequest));
    }

    @PostMapping("ozow")
    public ResponseEntity<?> createOzowApplicationConfig(@RequestBody CreateOzowConfigRequest createOzowConfigRequest) {
        return ResponseEntity.ok(paymentTypeConfigurationService.createOzowApplicationConfig(createOzowConfigRequest));
    }

    @GetMapping("ozow")
    public ResponseEntity<?> getOzowApplicationConfig(@RequestParam long applicationId) {
        return ResponseEntity.ok(paymentTypeConfigurationService.getOzowApplicationConfig(applicationId));
    }

    @PutMapping("ozow")
    public ResponseEntity<?> editOzowApplicationConfig(@RequestBody EditOzowConfigRequest editOzowConfigRequest) {
        return ResponseEntity.ok(paymentTypeConfigurationService.editOzowApplicationConfig(editOzowConfigRequest));
    }

    @PostMapping("stitch")
    public ResponseEntity<?> createStitchApplicationConfig(@RequestBody CreateStitchConfigRequest createStitchConfigRequest) {
        return ResponseEntity.ok(paymentTypeConfigurationService.createStitchApplicationConfig(createStitchConfigRequest));
    }

    @GetMapping("stitch")
    public ResponseEntity<?> getStitchApplicationConfig(@RequestParam long applicationId) {
        return ResponseEntity.ok(paymentTypeConfigurationService.getStitchApplicationConfig(applicationId));
    }

    @PutMapping("stitch")
    public ResponseEntity<?> editStitchApplicationConfig(@RequestBody EditStitchConfigRequest editStitchConfigRequest) {
        return ResponseEntity.ok(paymentTypeConfigurationService.editStitchApplicationConfig(editStitchConfigRequest));
    }

    @GetMapping("card-types")
    public ResponseEntity<?> getCardTypes() {
        return ResponseEntity.ok(paymentTypeConfigurationService.getCardTypes());
    }

    @GetMapping("merchant-types")
    public ResponseEntity<?> getMerchantTypes() {
        return ResponseEntity.ok(paymentTypeConfigurationService.getMerchantTypes());
    }

    @GetMapping("gateways")
    public ResponseEntity<?> getGateways() {
        return ResponseEntity.ok(paymentTypeConfigurationService.getCardGateways());
    }

    //gateways are on interface controller=====

    //tds merchant types are on interface controller=====

    @GetMapping("tokenization-methods")
    public ResponseEntity<?> getTokenizationMethods() {
        return ResponseEntity.ok(paymentTypeConfigurationService.getTokenizationMethods());
    }

    //ecis are on config management under payment type config controller
    @GetMapping("ecis")
    public ResponseEntity<?> getEcis() {
        List<EciEntity> eciEntities = paymentTypeConfigurationService.getEcis();
        logger.info("List of ecis | " + eciEntities.toString());
        return ResponseEntity.ok(eciEntities);
    }

    @GetMapping("tds-methods")
    public ResponseEntity<?> getTdsMethods() {
        return ResponseEntity.ok(paymentTypeConfigurationService.getTdsMethods());
    }

    @GetMapping("interface-exists/{merchantId}/{applicationId}")
    public ResponseEntity<?> interfaceExists(@PathVariable long merchantId, @PathVariable long applicationId) {
        return ResponseEntity.ok(paymentTypeConfigurationService.interfaceExists(merchantId, applicationId));
    }

    //post method for a card flow + card scheme (group) combo + settings
    @PostMapping("card-config/{merchantId}/{applicationId}/{cardFlowId}")
    public ResponseEntity<?> createCardConfigurationForApplication(@PathVariable long merchantId, @PathVariable long applicationId, @PathVariable long cardFlowId, @RequestBody @Valid CreateCardFlowConfigRequest createCardFlowConfigRequest) {
        paymentTypeConfigurationService.createCardConfigurationForApplication(merchantId, applicationId, cardFlowId, createCardFlowConfigRequest);
        return ResponseEntity.ok().build();
    }

//    @PostMapping("card-on-file-config/{merchantId}/{applicationId}")
//    public ResponseEntity<?> createCardOnFileConfigurationForApplication(@PathVariable long merchantId, @PathVariable long applicationId, @RequestBody @Valid CreateCardFlowConfigRequest createCardFlowConfigRequest) {
//        paymentTypeConfigurationService.createCardOnFileConfigurationForApplication(merchantId, applicationId, createCardFlowConfigRequest);
//        return ResponseEntity.ok().build();
//    }
//
//    @PostMapping("one-click-config/{merchantId}/{applicationId}")
//    public ResponseEntity<?> createOneClickConfigurationForApplication(@PathVariable long merchantId, @PathVariable long applicationId, @RequestBody @Valid CreateCardFlowConfigRequest createCardFlowConfigRequest) {
//        paymentTypeConfigurationService.createOneClickConfigurationForApplication(merchantId, applicationId, createCardFlowConfigRequest);
//        return ResponseEntity.ok().build();
//    }
//
//    @PostMapping("recurring-config/{merchantId}/{applicationId}")
//    public ResponseEntity<?> createRecurringConfigurationForApplication(@PathVariable long merchantId, @PathVariable long applicationId, @RequestBody @Valid CreateCardFlowConfigRequest createCardFlowConfigRequest) {
//        paymentTypeConfigurationService.createRecurringConfigurationForApplication(merchantId, applicationId, createCardFlowConfigRequest);
//        return ResponseEntity.ok().build();
//    }

    @GetMapping("/card-flows/{merchantId}/{applicationId}")
    public ResponseEntity<?> getCardFlows(@PathVariable long merchantId, @PathVariable long applicationId) {
        return ResponseEntity.ok(paymentTypeConfigurationService.getCardFlows(merchantId, applicationId));
    }

    @GetMapping("card-config/existing/{applicationId}")
    public ResponseEntity<?> getAllCardConfigurationsForApplication(@PathVariable long applicationId) {
        return ResponseEntity.ok(paymentTypeConfigurationService.getAllCardConfigurationsForApplication(applicationId));
    }

    @GetMapping("card-config/conflict/{cardConfigurationId}/{applicationId}")
    public ResponseEntity<?> checkConflictingCardConfigurations(@PathVariable long cardConfigurationId, @PathVariable long applicationId) {
        return ResponseEntity.ok(paymentTypeConfigurationService.checkConflictingCardConfigurations(cardConfigurationId, applicationId));
    }
    @GetMapping("card-config/deactivate-conflicts/{cardConfigurationId}/{applicationId}")
    public ResponseEntity<?> deactivateConflictingCardConfigsAndActivateCurrentCardConfig(@PathVariable long cardConfigurationId, @PathVariable long applicationId) {
        paymentTypeConfigurationService.deactivateConflictingCardConfigsAndActivateCurrentCardConfig(cardConfigurationId, applicationId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("card-config/deactivate/{cardConfigurationId}/{applicationId}")
    public ResponseEntity<?> deactivateCardConfig(@PathVariable long cardConfigurationId, @PathVariable long applicationId) {
        paymentTypeConfigurationService.deactivateCardConfig(cardConfigurationId, applicationId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("card-config")
    public ResponseEntity<?> getCardConfigurationForApplication(@RequestParam long applicationId, @RequestParam long cardConfigurationId) {
        return ResponseEntity.ok(paymentTypeConfigurationService.getCardConfigurationForApplication(applicationId, cardConfigurationId));
    }

//    @GetMapping("card-on-file-config")
//    public ResponseEntity<?> getCardOnFileConfigurationForApplication(@RequestParam long applicationId) {
//        return ResponseEntity.ok(paymentTypeConfigurationService.getCardOnFileConfigurationForApplication(applicationId));
//    }
//
//    @GetMapping("one-click-config")
//    public ResponseEntity<?> getOneClickConfigurationForApplication(@RequestParam long applicationId) {
//        return ResponseEntity.ok(paymentTypeConfigurationService.getOneClickConfigurationForApplication(applicationId));
//    }
//
//    @GetMapping("recurring-config")
//    public ResponseEntity<?> getRecurringConfigurationForApplication(@RequestParam long applicationId) {
//        return ResponseEntity.ok(paymentTypeConfigurationService.getRecurringConfigurationForApplication(applicationId));
//    }

    @PutMapping("card-config/{merchantId}/{applicationId}/{cardConfigurationId}")
    public ResponseEntity<?> editCardConfigurationForApplication(@PathVariable long merchantId, @PathVariable long applicationId, @PathVariable long cardConfigurationId, @RequestBody @Valid CreateCardFlowConfigRequest createCardFlowConfigRequest) {
        paymentTypeConfigurationService.editCardConfigurationForApplication(merchantId, applicationId, cardConfigurationId, createCardFlowConfigRequest);
        logger.info("Return card-config void | ");
        return ResponseEntity.ok().build();
    }

//    @PutMapping("card-on-file-config/{merchantId}/{applicationId}")
//    public ResponseEntity<?> editCardOnFileConfigurationForApplication(@PathVariable long merchantId, @PathVariable long applicationId, @RequestBody @Valid CreateCardFlowConfigRequest createCardFlowConfigRequest) {
//        paymentTypeConfigurationService.editCardOnFileConfigurationForApplication(merchantId, applicationId, createCardFlowConfigRequest);
//        return ResponseEntity.ok().build();
//    }
//
//    @PutMapping("one-click-config/{merchantId}/{applicationId}")
//    public ResponseEntity<?> editOneClickConfigurationForApplication(@PathVariable long merchantId, @PathVariable long applicationId, @RequestBody @Valid CreateCardFlowConfigRequest createCardFlowConfigRequest) {
//        paymentTypeConfigurationService.editOneClickConfigurationForApplication(merchantId, applicationId, createCardFlowConfigRequest);
//        return ResponseEntity.ok().build();
//    }
//
//    @PutMapping("recurring-config/{merchantId}/{applicationId}")
//    public ResponseEntity<?> editRecurringConfigurationForApplication(@PathVariable long merchantId, @PathVariable long applicationId, @RequestBody @Valid CreateCardFlowConfigRequest createCardFlowConfigRequest) {
//        paymentTypeConfigurationService.editRecurringConfigurationForApplication(merchantId, applicationId, createCardFlowConfigRequest);
//        return ResponseEntity.ok().build();
//    }

    @GetMapping("/card-config/{cardTypeGroupId}")
    public ResponseEntity<?> getCardConfigurationByCardTypeGroupId(@PathVariable long cardTypeGroupId) {
        return ResponseEntity.ok(paymentTypeConfigurationService.getCardConfigurationByCardTypeGroupId(cardTypeGroupId));
    }

    @GetMapping("/interface-config")
    public ResponseEntity<?> getInterfaceConfigData() {
        return ResponseEntity.ok(paymentTypeConfigurationService.getInterfaceConfigData());
    }

    @GetMapping("/card-type-group-config/{applicationId}")
    public ResponseEntity<CurrentCardTypeGroups> getCardTypeGroupsByCardFlow(@PathVariable long applicationId, @RequestParam String cardFlow) {
        CardFlowEntity cardFlowEntity = cardFlowRepository.findByCode(cardFlow);
        List<CardTypeGroupEntity> cardTypeGroupEntities = paymentTypeConfigurationService.getCardTypeGroupsByCardFlow(cardFlowEntity.getId(), applicationId);
        logger.info("Got card type group entities...");
        List<CardTypeEntity> cardTypeEntities = cardTypeRepository.findAll();
        List<CardTypeEntity> usedCardTypes = cardTypeGroupEntities
                .stream()
                .map(CardTypeGroupEntity::getCardTypeByCardTypeId)
                .collect(Collectors.toList());
        List<CardTypeEntity> availableCardTypes = new ArrayList<>();
        for (CardTypeEntity cardTypeEntity: cardTypeEntities) {
            logger.info("Potential card type | " + cardTypeEntity.getCode());
            if (!usedCardTypes.contains(cardTypeEntity)) {
                logger.info("Card type is available, add to list | " + cardTypeEntity.getCode());
                availableCardTypes.add(cardTypeEntity);
            } else {
                logger.info("Card type is not available, do not add to list | " + cardTypeEntity.getCode());
            }
        }

        List<CardTypeGroupEntity> usedCardTypeGroupsForEach = new ArrayList<>();
        List<CardTypeEntityGroupContainer> usedCardTypesContainer = new ArrayList<>();
        cardTypeGroupEntities
                .forEach(cardTypeGroupEntity -> {
                    logger.info("For each card type group entity | CARD CODE | " + cardTypeGroupEntity.getCardTypeByCardTypeId().getCode());
                    logger.info("for card config id | " + cardTypeGroupEntity.getCardConfigurationByCardConfigurationId().getId());
//                    CardTypeEntity cardTypeEntity = new CardTypeEntity();
//                    cardTypeE
//                    CardTypeGroupEntity cardTypeGroupEntity1 = new CardTypeGroupEntity();
//                    cardTypeGroupEntity1.setCardConfigurationId();
//                    cardTypeGroupEntity1.setCardTypeId();
//                    cardTypeGroupEntity1.setCardConfigurationByCardConfigurationId();
//                    cardTypeGroupEntity1.setCardTypeByCardTypeId();
//                    cardTypeGroupEntity1.setId();
//                    cardTypeEntity.set

                    if (usedCardTypeGroupsForEach.stream().allMatch(cardTypeGroupEntity1 -> cardTypeGroupEntity1.getCardConfigurationByCardConfigurationId() == cardTypeGroupEntity.getCardConfigurationByCardConfigurationId())) {
                        logger.info("if card config matches previous card config, add it to array " + cardTypeGroupEntity.getCardTypeByCardTypeId().getCode());
                        usedCardTypeGroupsForEach.add(cardTypeGroupEntity);
                    } else {
                        logger.info("else if new card config, complete array and push array to container, then clear it | CARD CODE | " + cardTypeGroupEntity.getCardTypeByCardTypeId().getCode());
                        CardTypeEntityGroupContainer cardTypeEntityGroupContainer = new CardTypeEntityGroupContainer(usedCardTypeGroupsForEach.stream().map(CardTypeGroupEntity::getCardTypeByCardTypeId).collect(Collectors.toList()));
                        usedCardTypesContainer.add(cardTypeEntityGroupContainer);
                        usedCardTypeGroupsForEach.clear();
                        // add new
                        usedCardTypeGroupsForEach.add(cardTypeGroupEntity);
                    }

                    // if there is only 1 configuration
                    logger.info("current index | " + cardTypeGroupEntities.indexOf(cardTypeGroupEntity) + " size of arr | " + cardTypeGroupEntities.size());
                    if (cardTypeGroupEntities.indexOf(cardTypeGroupEntity) == cardTypeGroupEntities.size() - 1) {
                        logger.info("else if last index, complete array and push array to container, then clear it | CARD CODE | " + cardTypeGroupEntity.getCardTypeByCardTypeId().getCode());
                        CardTypeEntityGroupContainer cardTypeEntityGroupContainer = new CardTypeEntityGroupContainer(usedCardTypeGroupsForEach.stream().map(CardTypeGroupEntity::getCardTypeByCardTypeId).collect(Collectors.toList()));
                        usedCardTypesContainer.add(cardTypeEntityGroupContainer);
                        usedCardTypeGroupsForEach.clear();
                    }
                });

        CurrentCardTypeGroups currentCardTypeGroups = new CurrentCardTypeGroups(cardTypeGroupEntities, availableCardTypes, usedCardTypesContainer);
        return ResponseEntity.ok(currentCardTypeGroups);
    }


    @GetMapping("stitch-banks")
    public ResponseEntity<?> getStitchBanks() {
        return ResponseEntity.ok(paymentTypeConfigurationService.getStitchBanks());
    }

}
