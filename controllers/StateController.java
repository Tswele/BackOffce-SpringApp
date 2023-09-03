package za.co.wirecard.channel.backoffice.controllers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import za.co.wirecard.channel.backoffice.dto.models.requests.StateChangeRequest;
import za.co.wirecard.channel.backoffice.services.StateService;

import javax.servlet.http.HttpServletRequest;

@RestController
@CrossOrigin
@RequestMapping(value = "/channel-back-office/api/v1/clients/states")
public class StateController {

    private final StateService stateService;
    private static final Logger logger = LogManager.getLogger(StateController.class);
    public StateController(StateService stateService) {
        this.stateService = stateService;
    }

//    @PutMapping(value = "cancel/{merchantId}")
//    public ResponseEntity<?> cancelMerchant(@PathVariable long merchantId, HttpServletRequest servletRequest) {
//        stateService.cancelMerchant(merchantId, servletRequest);
//        return ResponseEntity.ok().build();
//    }

    @PostMapping(value = "change-status")
    public ResponseEntity<?> updateState(@RequestBody StateChangeRequest stateChangeRequest, HttpServletRequest servletRequest) {
        stateService.updateState(stateChangeRequest, servletRequest);
        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "")
    public ResponseEntity<?> getStates(){
        ResponseEntity<?> responseEntity = ResponseEntity.ok(stateService.getStates());
        logger.info("Controller getState Response: {}",responseEntity);
        return responseEntity;
    }

}
