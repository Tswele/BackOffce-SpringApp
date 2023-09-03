package za.co.wirecard.channel.backoffice.controllers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import za.co.wirecard.channel.backoffice.dto.models.BatchTransaction;
import za.co.wirecard.channel.backoffice.dto.models.CardBatchRecord;
import za.co.wirecard.channel.backoffice.services.BatchService;
import za.co.wirecard.channel.backoffice.util.WriteCsvToResponse;


import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

@RestController
@CrossOrigin
@RequestMapping(value = "/channel-back-office/api/v1/batch")
public class BatchController {

    private static final Logger logger = LogManager.getLogger(BatchController.class);

    private final BatchService batchService;

    public BatchController(BatchService batchService) {
        this.batchService = batchService;
    }

    @GetMapping("/transactions")
    public ResponseEntity<Page<BatchTransaction>> getBatchTransactions(@RequestParam(defaultValue = "0") int page,
                                                                       @RequestParam(defaultValue = "25") int limit,
                                                                       @RequestParam(required = false) String merchantUid,
                                                                       @RequestParam(required = false) String applicationUid,
                                                                       @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date startDate,
                                                                       @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date endDate) {
        return ResponseEntity.ok(batchService.getBatchTransactions(page, limit, merchantUid, applicationUid, startDate, endDate));
    }

    @GetMapping("/records")
    public ResponseEntity<Page<CardBatchRecord>> getCardBatchRecordsByBatchId(@RequestParam(defaultValue = "0") int page,
                                                                              @RequestParam(defaultValue = "25") int limit,
                                                                              @RequestParam String batchUid) {
        return ResponseEntity.ok(batchService.getCardBatchRecordsByBatchId(page, limit, batchUid));
    }

    @GetMapping(value = "/records/report/{batchUid}", produces = "text/csv")
    public void getCardBatchRecordsByBatchIdReport(@PathVariable String batchUid, HttpServletResponse response) throws IOException {
        WriteCsvToResponse.writeResultsCardBatchRecord(response.getWriter(), batchService.getCardBatchRecordsByBatchIdReport(batchUid));
    }

    @GetMapping("/replay/{batchUid}")
    public ResponseEntity<?> replayBatch(@PathVariable String batchUid) {
        batchService.replayBatch(batchUid);
        return ResponseEntity.ok().build();
    }

}
