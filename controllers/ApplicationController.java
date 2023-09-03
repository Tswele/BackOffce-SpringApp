package za.co.wirecard.channel.backoffice.controllers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.platform.commons.util.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import za.co.wirecard.channel.backoffice.config.UtilityService;
import za.co.wirecard.channel.backoffice.dto.models.requests.CreateDefaultApplicationWithConfiguration;
import za.co.wirecard.channel.backoffice.dto.models.requests.PlatformCreateApplicationConfigurationRequest;
import za.co.wirecard.channel.backoffice.dto.models.requests.PlatformCreateApplicationRequest;
import za.co.wirecard.channel.backoffice.entities.ApplicationEntity;
import za.co.wirecard.channel.backoffice.models.Application;
import za.co.wirecard.channel.backoffice.services.ApplicationService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static za.co.wirecard.channel.backoffice.builder.ApplicationSpecifications.*;

@RestController
@CrossOrigin
@RequestMapping(value = "/channel-back-office/api/v1/clients/application")
public class ApplicationController {
    private final ApplicationService applicationService;

    private static final Logger logger = LogManager.getLogger(ApplicationService.class);

    public ApplicationController(ApplicationService applicationService) {
        this.applicationService = applicationService;
    }

    @GetMapping("/{applicationId}")
    public ResponseEntity<?> getApplication(@PathVariable long applicationId) {
        logger.info("This is the Response Entity: " + ResponseEntity.ok(applicationService.getApplication(applicationId)));
        return ResponseEntity.ok(applicationService.getApplication(applicationId).getBody());
    }

//    @GetMapping("/{page}/{limit}/{merchantId}")
//    public ResponseEntity<?> getApplicationsByMerchantId(@PathVariable int page, @PathVariable int limit, @PathVariable long merchantId, @RequestParam(required = false) String searchString) {
//        logger.info("This is the Response Entity: " + ResponseEntity.ok(applicationService.getApplicationsByMerchantId(page, limit, merchantId, searchString)));
//        return ResponseEntity.ok(applicationService.getApplicationsByMerchantId(page, limit, merchantId,searchString));
//    }
    @GetMapping("")
    public ResponseEntity<?> getApplicationsByMerchantId(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "25") int limit, @RequestParam long merchantId, @RequestParam(required = false) String searchCriteria, @RequestParam(required = false) String searchString) {

        String applicationName = null;
        String applicationUid = null;

        if(searchCriteria != null){
            if(searchCriteria.equals("APPLICATION_NAME")){
                applicationName = searchString;
                logger.info("APPLICATION NAME | " + applicationName);
            } else if(searchCriteria.equals("APPLICATION_UID")){
                applicationUid = searchString;
                logger.info("APPLICATION UID | " + applicationUid);
            }
        }


        Specification<ApplicationEntity> specification = Specification
                .where(merchantIdEquals(merchantId))
                .and(applicationName == null || StringUtils.isBlank(applicationName) ? null : stringSearchApplicationNameLike(applicationName))
                .and(applicationUid == null || StringUtils.isBlank(applicationUid) ? null : stringSearchApplicationUidLike(applicationUid));

        Page<Application> applications = applicationService.getApplicationsByMerchantId(page, limit, specification);
        return ResponseEntity.ok(applications);
    }

    @PostMapping("/{applicationId}")
    public ResponseEntity<?> editApplication(@PathVariable(name = "applicationId") long applicationId, @RequestBody PlatformCreateApplicationRequest application) {
        applicationService.editApplication(application, applicationId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("")
    public ResponseEntity<?> createApplication(@RequestBody PlatformCreateApplicationRequest application) {
        return ResponseEntity.ok(applicationService.createApplication(application));
    }

    @DeleteMapping("/{applicationId}")
    public ResponseEntity<?> deleteApplication(@PathVariable long applicationId) {
        applicationService.deleteApplication(applicationId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/security-types")
    public ResponseEntity<?> getSecurityTypes() {
        logger.info("This is the Response Entity: " + ResponseEntity.ok(applicationService.getSecurityTypes()));
        return ResponseEntity.ok(applicationService.getSecurityTypes());
    }

    @GetMapping("/configuration-types")
    public ResponseEntity<?> getConfigurationTypes() {
        logger.info("This is the Response Entity: " + ResponseEntity.ok(applicationService.getConfigurationTypes()));
        return ResponseEntity.ok(applicationService.getConfigurationTypes());
    }

    @PostMapping("/configuration")
    public ResponseEntity<?> createApplicationConfiguration(@RequestBody PlatformCreateApplicationConfigurationRequest applicationConfiguration) {
        applicationService.createApplicationConfiguration(applicationConfiguration);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/configuration/default/{merchantId}")
    public ResponseEntity<?> createDefaultApplicationWithConfiguration(@RequestBody CreateDefaultApplicationWithConfiguration createDefaultApplicationWithConfiguration, @PathVariable long merchantId, HttpServletRequest servletRequest) {
        return ResponseEntity.ok(applicationService.createDefaultApplicationWithConfiguration(createDefaultApplicationWithConfiguration, merchantId, servletRequest));
    }
}
