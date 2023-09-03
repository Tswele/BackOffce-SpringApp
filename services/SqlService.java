package za.co.wirecard.channel.backoffice.services;

import za.co.wirecard.channel.backoffice.models.query.ReportQueryResultCard;
import za.co.wirecard.channel.backoffice.models.query.ReportQueryResultPayment;
import za.co.wirecard.channel.backoffice.models.query.ReportQueryResultSettlement;

import java.util.Date;
import java.util.List;

public interface SqlService {
    List<ReportQueryResultPayment> dailyReportOther(Long merchantId, Long paymentTypeId, Date dateFrom);
    List<ReportQueryResultCard> dailyReportCards(Long merchantId, Long paymentTypeId, Date dateFrom);
    List<ReportQueryResultSettlement> getSettlementReport(Long responseCodeId, Long merchantId);
}
