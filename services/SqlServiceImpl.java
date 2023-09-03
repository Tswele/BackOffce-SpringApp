package za.co.wirecard.channel.backoffice.services;

import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Service;
import za.co.wirecard.channel.backoffice.models.query.*;
import za.co.wirecard.channel.backoffice.repositories.PaymentTypeRepository;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

@Service
public class SqlServiceImpl implements SqlService {
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private final PaymentTypeRepository paymentTypeRepository;

    public SqlServiceImpl(NamedParameterJdbcTemplate namedParameterJdbcTemplate, PaymentTypeRepository paymentTypeRepository) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
        this.paymentTypeRepository = paymentTypeRepository;
    }

    @Override
    public List<ReportQueryResultPayment> dailyReportOther(Long merchantId, Long paymentTypeId, Date dateFrom) {
        LocalDate date = dateFrom.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
        SqlQueryPayment sqlQueryPayment = new SqlQueryPayment();
        if (merchantId != null) {
            String PAYMENT_TRANSACTION_LIST_QUERY =
                    "DECLARE @FromDate DATETIME;\n" +
                            "SET @FromDate = " + "'" + date + "'" + ";\n" +
                            "select t.purchaser_full_name AS Full_Name, t.merchant_reference AS Merchant_Reference, t.transaction_uid AS Transaction_UID, t.initiation_date AS initiation_Date, t.settled_value AS Settled_Value,\n" +
                            "t.refund_value AS Refund_value, ts.code AS CODE, t.last_updated_date\n" +
                            "from transaction_db.dbo.[transaction] t , transaction_state ts\n" +
                            "where t.merchant_id = :merchantId\n" +
                            "and t.payment_type_id = :paymentTypeId\n" +
                            "and t.transaction_state_id = ts.id\n" +
                            "and ts.code in ('SETTLED', 'REFUNDED')\n" +
                            "and t.initiation_date > @FromDate\n" +
                            "and t.initiation_date < DATEADD(DAY,1, @FromDate)\n" +
                            "order by t.initiation_date asc";
            sqlQueryPayment.setMerchantId(merchantId);
            sqlQueryPayment.setPaymentTypeId(paymentTypeId);
            SqlParameterSource namedParameters = new BeanPropertySqlParameterSource(sqlQueryPayment);
            List<ReportQueryResultPayment> resultList = namedParameterJdbcTemplate.query(
                    PAYMENT_TRANSACTION_LIST_QUERY, namedParameters, (rs, rowNum) ->
                            new ReportQueryResultPayment(
                                    rs.getString("Full_Name"),
                                    rs.getString("Merchant_Reference"),
                                    rs.getString("Transaction_UID"),
                                    rs.getString("initiation_Date"),
                                    rs.getString("Settled_Value"),
                                    rs.getString("Refund_value"),
                                    rs.getString("CODE"),
                                    rs.getString("last_updated_date")
                            ));
            return resultList;
        } else {
            String PAYMENT_TRANSACTION_LIST_QUERY =
                    "DECLARE @FromDate DATETIME;\n" +
                            "SET @FromDate = " + "'" + date + "'" + ";\n" +
                            "select t.purchaser_full_name AS Full_Name, t.merchant_reference AS Merchant_Reference, t.transaction_uid AS Transaction_UID, t.initiation_date AS initiation_Date, t.settled_value AS Settled_Value,\n" +
                            "t.refund_value AS Refund_value, ts.code AS CODE, t.last_updated_date\n" +
                            "from transaction_db.dbo.[transaction] t , transaction_state ts\n" +
                            "where t.payment_type_id = :paymentTypeId\n" +
                            "and t.transaction_state_id = ts.id\n" +
                            "and ts.code in ('SETTLED', 'REFUNDED')\n" +
                            "and t.initiation_date > @FromDate\n" +
                            "and t.initiation_date < DATEADD(DAY,1, @FromDate)\n" +
                            "order by t.initiation_date asc";
            sqlQueryPayment.setPaymentTypeId(paymentTypeId);
            SqlParameterSource namedParameters = new BeanPropertySqlParameterSource(sqlQueryPayment);
            List<ReportQueryResultPayment> resultList = namedParameterJdbcTemplate.query(
                    PAYMENT_TRANSACTION_LIST_QUERY, namedParameters, (rs, rowNum) ->
                            new ReportQueryResultPayment(
                                    rs.getString("Full_Name"),
                                    rs.getString("Merchant_Reference"),
                                    rs.getString("Transaction_UID"),
                                    rs.getString("initiation_Date"),
                                    rs.getString("Settled_Value"),
                                    rs.getString("Refund_value"),
                                    rs.getString("CODE"),
                                    rs.getString("last_updated_date")
                            ));
            return resultList;
        }

    }

    @Override
    public List<ReportQueryResultCard> dailyReportCards(Long merchantId, Long paymentTypeId, Date dateFrom) {
        LocalDate date = dateFrom.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
        SqlQueryPayment sqlQueryPayment = new SqlQueryPayment();
        if (merchantId != null) {
            String PAYMENT_TRANSACTION_LIST_QUERY =
                    "DECLARE @FromDate DATETIME;\n" +
                            "SET @FromDate = " + "'" + date + "'" + "\n" +
                            "select card_transaction.cardholder_fullname\n" +
                            ",[transaction].merchant_reference\n" +
                            ",[transaction].transaction_uid\n" +
                            ",[transaction].initiation_date\n" +
                            ",[transaction].last_updated_date\n" +
                            ",CONCAT(card_transaction.card_bin,'**',card_transaction.card_last_four) as Card_Number\n" +
                            ", [transaction].settled_value\n" +
                            ", [transaction].refund_value\n" +
                            ", transaction_state.code\n" +
                            ", card_transaction.bank_error_description\n" +
                            ", card_transaction.bank_error_code\n" +
                            ", card_transaction.card_type\n" +
                            ", card_transaction.ip_address\n" +
                            ", three_ds_auth.eci\n" +
                            ", card_transaction.card_number_hash\n" +
                            ", [transaction].error_code\n" +
                            ", [transaction].error_message\n" +
                            ", card_transaction.authorisation_id as authorisation_number\n" +
                            "from transaction_leg\n" +
                            "inner join transaction_action\n" +
                            "on transaction_action.id = transaction_leg.transaction_action_id\n" +
                            "inner join [transaction]\n" +
                            "on [transaction].id = transaction_leg.transaction_id\n" +
                            "inner join card_transaction\n" +
                            "on [transaction].id = card_transaction.transaction_id\n" +
                            "inner join three_ds_transaction\n" +
                            "on three_ds_transaction.transaction_id = [transaction].id\n" +
                            "inner join three_ds_auth\n" +
                            "on three_ds_auth.three_ds_transaction_id = three_ds_transaction.id\n" +
                            "inner join transaction_state\n" +
                            "on [transaction].transaction_state_id = transaction_state.id\n" +
                            "inner join card_bigiso_response\n" +
                            "on card_bigiso_response.transaction_leg_id = transaction_leg.id\n" +
                            "where transaction_leg.date_logged BETWEEN DATEADD(MINUTE,105, @FromDate) AND DATEADD(MINUTE,105, DATEADD(DAY,1, @FromDate))\n" +
                            "AND transaction_action.code in ('CARD_SETTLE', 'CARD_REFUND')\n" +
                            "and [transaction].merchant_id = :merchantId\n" +
                            "and [transaction].payment_type_id = :paymentTypeId";
            sqlQueryPayment.setMerchantId(merchantId);
            sqlQueryPayment.setPaymentTypeId(paymentTypeId);
            SqlParameterSource namedParameters = new BeanPropertySqlParameterSource(sqlQueryPayment);
            List<ReportQueryResultCard> resultList = namedParameterJdbcTemplate.query(
                    PAYMENT_TRANSACTION_LIST_QUERY, namedParameters, (rs, rowNum) ->
                            new ReportQueryResultCard(
                                    rs.getString("cardholder_fullname"),
                                    rs.getString("merchant_reference"),
                                    rs.getString("transaction_uid"),
                                    rs.getString("initiation_date"),
                                    rs.getString("last_updated_date"),
                                    rs.getString("Card_Number"),
                                    rs.getString("settled_value"),
                                    rs.getString("refund_value"),
                                    rs.getString("code"),
                                    rs.getString("bank_error_description"),
                                    rs.getString("bank_error_code"),
                                    rs.getString("card_type"),
                                    rs.getString("ip_address"),
                                    rs.getString("eci"),
                                    rs.getString("card_number_hash"),
                                    rs.getString("error_code"),
                                    rs.getString("error_message"),
                                    rs.getString("authorisation_number")
                            ));
            return resultList;
        } else {
            String PAYMENT_TRANSACTION_LIST_QUERY =
                    "DECLARE @FromDate DATETIME;\n" +
                            "SET @FromDate = " + "'" + date + "'" + "\n" +
                            "select card_transaction.cardholder_fullname\n" +
                            ",[transaction].merchant_reference\n" +
                            ",[transaction].transaction_uid\n" +
                            ",[transaction].initiation_date\n" +
                            ",[transaction].last_updated_date\n" +
                            ",CONCAT(card_transaction.card_bin,'**',card_transaction.card_last_four) as Card_Number\n" +
                            ", [transaction].settled_value\n" +
                            ", [transaction].refund_value\n" +
                            ", transaction_state.code\n" +
                            ", card_transaction.bank_error_description\n" +
                            ", card_transaction.bank_error_code\n" +
                            ", card_transaction.card_type\n" +
                            ", card_transaction.ip_address\n" +
                            ", three_ds_auth.eci\n" +
                            ", card_transaction.card_number_hash\n" +
                            ", [transaction].error_code\n" +
                            ", [transaction].error_message\n" +
                            ", card_transaction.authorisation_id as authorisation_number\n" +
                            "from transaction_leg\n" +
                            "inner join transaction_action\n" +
                            "on transaction_action.id = transaction_leg.transaction_action_id\n" +
                            "inner join [transaction]\n" +
                            "on [transaction].id = transaction_leg.transaction_id\n" +
                            "inner join card_transaction\n" +
                            "on [transaction].id = card_transaction.transaction_id\n" +
                            "inner join three_ds_transaction\n" +
                            "on three_ds_transaction.transaction_id = [transaction].id\n" +
                            "inner join three_ds_auth\n" +
                            "on three_ds_auth.three_ds_transaction_id = three_ds_transaction.id\n" +
                            "inner join transaction_state\n" +
                            "on [transaction].transaction_state_id = transaction_state.id\n" +
                            "inner join card_bigiso_response\n" +
                            "on card_bigiso_response.transaction_leg_id = transaction_leg.id\n" +
                            "where transaction_leg.date_logged BETWEEN DATEADD(MINUTE,105, @FromDate) AND DATEADD(MINUTE,105, DATEADD(DAY,1, @FromDate))\n" +
                            "AND transaction_action.code in ('CARD_SETTLE', 'CARD_REFUND')\n" +
                            "and [transaction].payment_type_id = :paymentTypeId";
            sqlQueryPayment.setPaymentTypeId(paymentTypeId);
            SqlParameterSource namedParameters = new BeanPropertySqlParameterSource(sqlQueryPayment);
            List<ReportQueryResultCard> resultList = namedParameterJdbcTemplate.query(
                    PAYMENT_TRANSACTION_LIST_QUERY, namedParameters, (rs, rowNum) ->
                            new ReportQueryResultCard(
                                    rs.getString("cardholder_fullname"),
                                    rs.getString("merchant_reference"),
                                    rs.getString("transaction_uid"),
                                    rs.getString("initiation_date"),
                                    rs.getString("last_updated_date"),
                                    rs.getString("Card_Number"),
                                    rs.getString("settled_value"),
                                    rs.getString("refund_value"),
                                    rs.getString("code"),
                                    rs.getString("bank_error_description"),
                                    rs.getString("bank_error_code"),
                                    rs.getString("card_type"),
                                    rs.getString("ip_address"),
                                    rs.getString("eci"),
                                    rs.getString("card_number_hash"),
                                    rs.getString("error_code"),
                                    rs.getString("error_message"),
                                    rs.getString("authorisation_number")
                            ));
            return resultList;
        }

    }

    @Override
    public List<ReportQueryResultSettlement> getSettlementReport(Long responseCodeId, Long merchantId) {
//        if (dateFrom != null) {
//            LocalDate date = dateFrom.toInstant()
//                    .atZone(ZoneId.systemDefault())
//                    .toLocalDate();
//        }
        SqlQuerySettlement sqlQuerySettlement = new SqlQuerySettlement(responseCodeId, merchantId);
        if (merchantId != null) {
            String SETTLEMENT_TRANSACTION_LIST_QUERY =
                    "select CONVERT(DATE,DATEADD(MINUTE,-105,transaction_leg.date_logged)) AS Date\n" +
                            ",SUM(CASE WHEN transaction_action.code = 'CARD_SETTLE' THEN 1 else 0 END) as Settled_Count\n" +
                            ",SUM(CASE WHEN transaction_action.code = 'CARD_SETTLE' THEN transaction_leg.transaction_value else 0.00 END) as Settled\n" +
                            ",SUM(CASE WHEN transaction_action.code = 'CARD_REFUND' THEN 1 else 0 END) as Refunded_Count\n" +
                            ",SUM(CASE WHEN transaction_action.code = 'CARD_REFUND' THEN transaction_leg.transaction_value else 0.00 END) as Refunded\n" +
                            ",SUM(CASE WHEN transaction_action.code = 'CARD_SETTLE' THEN transaction_leg.transaction_value else (-1 * transaction_leg.transaction_value) END) AS Net_Settlement\n" +
                            "from transaction_leg\n" +
                            "inner join transaction_action\n" +
                            "on transaction_action.id = transaction_leg.transaction_action_id\n" +
                            "inner join [transaction]\n" +
                            "on [transaction].id = transaction_leg.transaction_id\n" +
                            "where transaction_action.code in ('CARD_SETTLE', 'CARD_REFUND')\n" +
                            "and response_code_id = :responseCodeId\n" +
                            "and merchant_id = :merchantId\n" +
                            "group by CONVERT(DATE,DATEADD(MINUTE,-105,transaction_leg.date_logged))\n" +
                            "HAVING CONVERT(DATE,DATEADD(MINUTE,-105,transaction_leg.date_logged)) >= CONVERT(DATE,DATEADD(DAY,-20,GETDATE()))\n" +
                            "AND CONVERT(DATE,DATEADD(MINUTE,-105,transaction_leg.date_logged)) < CONVERT(DATE,GETDATE())\n" +
                            "order by CONVERT(DATE,DATEADD(MINUTE,-105,transaction_leg.date_logged))";
            sqlQuerySettlement.setResponseCodeId(responseCodeId);
            sqlQuerySettlement.setMerchantId(merchantId);
            SqlParameterSource namedParameters = new BeanPropertySqlParameterSource(sqlQuerySettlement);
            List<ReportQueryResultSettlement> resultList = namedParameterJdbcTemplate.query(
                    SETTLEMENT_TRANSACTION_LIST_QUERY, namedParameters, (rs, rowNum) ->
                            new ReportQueryResultSettlement(
                                    rs.getString("Date"),
                                    rs.getString("Settled_Count"),
                                    rs.getString("Settled"),
                                    rs.getString("Refunded_Count"),
                                    rs.getString("Refunded"),
                                    rs.getString("Net_Settlement")
                            ));
            return resultList;
        } else {
            String SETTLEMENT_TRANSACTION_LIST_QUERY =
                    "select CONVERT(DATE,DATEADD(MINUTE,-105,transaction_leg.date_logged)) AS Date\n" +
                            ",SUM(CASE WHEN transaction_action.code = 'CARD_SETTLE' THEN 1 else 0 END) as Settled_Count\n" +
                            ",SUM(CASE WHEN transaction_action.code = 'CARD_SETTLE' THEN transaction_leg.transaction_value else 0.00 END) as Settled\n" +
                            ",SUM(CASE WHEN transaction_action.code = 'CARD_REFUND' THEN 1 else 0 END) as Refunded_Count\n" +
                            ",SUM(CASE WHEN transaction_action.code = 'CARD_REFUND' THEN transaction_leg.transaction_value else 0.00 END) as Refunded\n" +
                            ",SUM(CASE WHEN transaction_action.code = 'CARD_SETTLE' THEN transaction_leg.transaction_value else (-1 * transaction_leg.transaction_value) END) AS Net_Settlement\n" +
                            "from transaction_leg\n" +
                            "inner join transaction_action\n" +
                            "on transaction_action.id = transaction_leg.transaction_action_id\n" +
                            "inner join [transaction]\n" +
                            "on [transaction].id = transaction_leg.transaction_id\n" +
                            "where transaction_action.code in ('CARD_SETTLE', 'CARD_REFUND')\n" +
                            "and response_code_id = :responseCodeId\n" +
                            "group by CONVERT(DATE,DATEADD(MINUTE,-105,transaction_leg.date_logged))\n" +
                            "HAVING CONVERT(DATE,DATEADD(MINUTE,-105,transaction_leg.date_logged)) >= CONVERT(DATE,DATEADD(DAY,-20,GETDATE()))\n" +
                            "AND CONVERT(DATE,DATEADD(MINUTE,-105,transaction_leg.date_logged)) < CONVERT(DATE,GETDATE())\n" +
                            "order by CONVERT(DATE,DATEADD(MINUTE,-105,transaction_leg.date_logged))";
            sqlQuerySettlement.setResponseCodeId(responseCodeId);
            SqlParameterSource namedParameters = new BeanPropertySqlParameterSource(sqlQuerySettlement);
            List<ReportQueryResultSettlement> resultList = namedParameterJdbcTemplate.query(
                    SETTLEMENT_TRANSACTION_LIST_QUERY, namedParameters, (rs, rowNum) ->
                            new ReportQueryResultSettlement(
                                    rs.getString("Date"),
                                    rs.getString("Settled_Count"),
                                    rs.getString("Settled"),
                                    rs.getString("Refunded_Count"),
                                    rs.getString("Refunded"),
                                    rs.getString("Net_Settlement")
                            ));
            return resultList;
        }
    }
}
