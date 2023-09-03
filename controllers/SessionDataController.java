package za.co.wirecard.channel.backoffice.controllers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import za.co.wirecard.channel.backoffice.services.SessionDataService;

import java.util.Date;

@RestController
@CrossOrigin
@RequestMapping(value = "/channel-back-office/api/v1/session-data")
public class SessionDataController {

    private static final Logger logger = LogManager.getLogger(SessionController.class);

    private final SessionDataService sessionDataService;

    public SessionDataController(SessionDataService sessionDataService) {
        this.sessionDataService = sessionDataService;
    }

    @GetMapping("/list")
    public ResponseEntity<?> getSessionList(@RequestParam(defaultValue = "0") int page,
                                            @RequestParam(defaultValue = "25") int limit,
                                            @RequestParam(required = false) String merchantUid,
                                            @RequestParam(required = false) String applicationUid,
                                            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date startDate,
                                            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date endDate) {
        return ResponseEntity.ok(sessionDataService.getSessionList(page, limit, merchantUid, applicationUid, startDate, endDate));
    }
    @GetMapping("/{sessionToken}")
    public ResponseEntity<?> getSessionDataForSession(@PathVariable String sessionToken) {
        return ResponseEntity.ok(sessionDataService.getSessionDataForSession(sessionToken));
    }
}
