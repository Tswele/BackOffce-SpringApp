package za.co.wirecard.channel.backoffice.controllers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import za.co.wirecard.channel.backoffice.services.SwitchLogService;

@RestController
@CrossOrigin
@RequestMapping(value = "/channel-back-office/api/v1/switch-logs")
public class SwitchLogsController {

    private static final Logger logger = LogManager.getLogger(SwitchLogsController.class);

    private final SwitchLogService switchLogService;

    public SwitchLogsController(SwitchLogService switchLogService) {
        this.switchLogService = switchLogService;
    }

    @GetMapping("/list")
    public ResponseEntity<?> getSwitchLogs(@RequestParam(defaultValue = "0") int page,
                                           @RequestParam(defaultValue = "25") int limit,
                                           @RequestParam(required = false) Long transactionLegId,
                                           @RequestParam(required = false) String startDate,
                                           @RequestParam(required = false) String endDate) {
        return ResponseEntity.ok(switchLogService.getSwitchLogs(page, limit, transactionLegId, startDate, endDate));
    }

    @GetMapping("/{transactionLegId}")
    public ResponseEntity<?> getSwitchLogs(@PathVariable long transactionLegId) {
        return ResponseEntity.ok(switchLogService.getSwitchLogByTransactionLegId(transactionLegId));
    }
}
