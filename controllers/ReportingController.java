package za.co.wirecard.channel.backoffice.controllers;

import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import za.co.wirecard.channel.backoffice.models.query.ReportQueryResultCard;
import za.co.wirecard.channel.backoffice.models.query.ReportQueryResultPayment;
import za.co.wirecard.channel.backoffice.models.query.ReportQueryResultSettlement;
import za.co.wirecard.channel.backoffice.services.SqlService;
import za.co.wirecard.channel.backoffice.util.WriteCsvToResponse;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping(value = "/channel-back-office/api/v1/report")
public class ReportingController {

    private final SqlService sqlService;

    @Autowired
    public ReportingController(SqlService sqlService) {
        this.sqlService = sqlService;
    }

    @GetMapping(value = "/daily/{type}", produces = "text/csv")
    public void dailyTransactionReport(@PathVariable(value = "type") String type,
                                       @RequestParam(required = false) Long merchantId,
                                       @RequestParam Long paymentTypeId,
                                       @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date dateFrom,
                                       HttpServletResponse response)
                                            throws  SQLException,
                                            ClassNotFoundException,
                                            IOException,
                                            CsvDataTypeMismatchException,
                                            CsvRequiredFieldEmptyException {
        if (type.equalsIgnoreCase("other")) {
            List<ReportQueryResultPayment> reportQueryResultsPayment = sqlService.dailyReportOther(merchantId, paymentTypeId, dateFrom);
            WriteCsvToResponse.writeQueryResultsPayments(response.getWriter(), reportQueryResultsPayment);
        } else {
            List<ReportQueryResultCard> reportQueryResultsCard = sqlService.dailyReportCards(merchantId, paymentTypeId, dateFrom);
            WriteCsvToResponse.writeQueryResultsCard(response.getWriter(), reportQueryResultsCard);
        }
    }

    @GetMapping(value = "/settlement", produces = "text/csv")
    // Settlement Report
    public void bankReconReport(@RequestParam long responseCodeId,
                                @RequestParam(required = false) long merchantId, HttpServletResponse response)
                                    throws  SQLException,
                                    ClassNotFoundException,
                                    IOException {
        List<ReportQueryResultSettlement> reportQueryResultsSettlement = sqlService.getSettlementReport(responseCodeId, merchantId);
        WriteCsvToResponse.writeQueryResultsSettlement(response.getWriter(), reportQueryResultsSettlement);
    }

    @GetMapping(value = "/daily/raw/other")
    public ResponseEntity<List<ReportQueryResultPayment>> rawDailyTransactionReportPayments(@RequestParam(required = false) Long merchantId,
                                                                                            @RequestParam Long paymentTypeId,
                                                                                            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date dateFrom,
                                                                                            HttpServletResponse response)
                                                                                                throws SQLException,
                                                                                                ClassNotFoundException,
                                                                                                IOException,
                                                                                                CsvDataTypeMismatchException,
                                                                                                CsvRequiredFieldEmptyException {
        List<ReportQueryResultPayment> reportQueryResultsPayment = sqlService.dailyReportOther(merchantId, paymentTypeId, dateFrom);
        return ResponseEntity.ok(reportQueryResultsPayment);
    }

    @GetMapping(value = "/daily/raw/card")
    public ResponseEntity<List<ReportQueryResultCard>> rawDailyTransactionReportCard(@RequestParam(required = false) Long merchantId,
                                                                                     @RequestParam Long paymentTypeId,
                                                                                     @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date dateFrom,
                                                                                     HttpServletResponse response)
                                                                                        throws SQLException,
                                                                                        ClassNotFoundException,
                                                                                        IOException,
                                                                                        CsvDataTypeMismatchException,
                                                                                        CsvRequiredFieldEmptyException {
        List<ReportQueryResultCard> reportQueryResultsCard = sqlService.dailyReportCards(merchantId, paymentTypeId, dateFrom);
        return ResponseEntity.ok(reportQueryResultsCard);
    }

    @GetMapping(value = "/settlement/raw")
    public ResponseEntity<List<ReportQueryResultSettlement>> rawBankReconReport(@RequestParam Long responseCodeId,
                                                                                @RequestParam(required = false) Long merchantId,
                                                                                HttpServletResponse response)
                                                                                    throws SQLException,
                                                                                    ClassNotFoundException,
                                                                                    IOException {
        List<ReportQueryResultSettlement> reportQueryResultsSettlement = sqlService.getSettlementReport(responseCodeId, merchantId);
        return ResponseEntity.ok(reportQueryResultsSettlement);
    }

}
