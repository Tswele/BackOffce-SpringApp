package za.co.wirecard.channel.backoffice.services;

import org.apache.commons.text.StringEscapeUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.domain.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;
import za.co.wirecard.channel.backoffice.dto.models.SwitchLog;
import za.co.wirecard.channel.backoffice.repositories.TStandardbankSecondarySwitchLogRepository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class SwitchLogServiceImpl implements SwitchLogService{

    private static final Logger logger = LogManager.getLogger(SwitchLogServiceImpl.class);

    private final TStandardbankSecondarySwitchLogRepository tStandardbankSecondarySwitchLogRepository;
    private final JdbcTemplate jdbcTemplate;
    
//    @Autowired
//    @Qualifier("jdbcTemplate")
//    private JdbcTemplate jdbcTemplate;

    public SwitchLogServiceImpl(TStandardbankSecondarySwitchLogRepository tStandardbankSecondarySwitchLogRepository, JdbcTemplate jdbcTemplate) {
        this.tStandardbankSecondarySwitchLogRepository = tStandardbankSecondarySwitchLogRepository;
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Page<SwitchLog> getSwitchLogs(int page, int limit, Long transactionLegId, String startDate, String endDate) {

        StringBuilder tableNameQuery
                = new StringBuilder("select table_name " +
                                    "from switch_information_schema.tables " +
                                    "where table_type = 'BASE TABLE' AND table_name like '%switch_log'");

        List<String> tableNameArray = new ArrayList<>();

        List<Map<String, Object>> tableNames =
                jdbcTemplate.queryForList(
                        tableNameQuery.toString());

        logger.info("<<<<<<<<<< SIZE OF TABLE NAME QUERY MAP: " + tableNames.size());

        for (Map tableNameMap : tableNames) {
            tableNameArray.add((String) tableNameMap.get("TABLE_NAME"));
        }

        logger.info("<<<<<<<<<< SIZE OF TABLE NAME ARRAY: " + tableNameArray.size());

        for (String tableName : tableNameArray) {
            logger.info("<<<<<<<<<< NAMES OF TABLES: " + tableName);
        }

        List<Object> queryArgs = new ArrayList<>();

//        StringBuilder sqlQueryCount = new StringBuilder("SELECT count(*) AS row_count FROM (" +
//                " (select " +
//                "LogID " +
//                //",priority " +
//                ",TransactionID AS transaction_id " +
//                /*",msg " +
//                ",request " +
//                ",timestamp " +*/
//                "from switch_public." + tableNameArray.get(0) + " LIMIT 100 ) ");
//        if(transactionLegId != null) {
//            //sqlQueryCount.append(" WHERE transaction_id = ").append(transactionLegId);
//            //sqlQueryCount.append(" ");
//        }
//        //sqlQueryCount.append(" LIMIT 1000 ");
//
//
//
//        for (int i = 1; i < tableNameArray.size(); i++) {
//            sqlQueryCount.append("UNION ALL " +
//                    "(select " +
//                    "LogID " +
//                    //",priority " +
//                    ",TransactionID AS transaction_id " +
//                    /*",msg " +
//                    ",request " +
//                    ",timestamp " +*/
//                    "from switch_public." + tableNameArray.get(i) + " LIMIT 100 ) ");
//        }
//        //if(transactionLegId != null) {
//        //    sqlQueryCount.append(" WHERE transaction_id = ").append(transactionLegId);
//            //sqlQueryCount.append(" ");
//        //}
//
//        sqlQueryCount.append(" LIMIT 1000 ) AS switch_data");//ORDER BY LogID DESC
//
//        if(transactionLegId != null) {
//            sqlQueryCount.append(" WHERE transaction_id = ").append(transactionLegId);
//        }
//
//        logger.info("<<<<<<<<<< SQL QUERY: " + sqlQueryCount.toString());

        List<Integer> switchLogsFromQueryCount = new ArrayList<>();
        switchLogsFromQueryCount.add(2000);//TODO find better solution for this
                //jdbcTemplate.query(sqlQueryCount.toString(), queryArgs.toArray(), new RowMapper<Integer>(){
                //    public Integer mapRow(ResultSet rs, int rowNum) throws SQLException{
                //        return rs.getInt("row_count");
                //    }
               // });

        StringBuilder sqlQuery = new StringBuilder(" SELECT * FROM ((select " +
                "LogID " +
                ",priority " +
                ",TransactionID" +
                ",msg " +
                ",request " +
                ",timestamp " +
                "from switch_public." + tableNameArray.get(0) + ") ");
//        sqlQuery.append(" LIMIT ").append("1000").append(") ");
//        if (switchLogsFromQueryCount.get(0) > limit) {
//            sqlQuery.append(" OFFSET ").append(page).append(") ");
//        }

        for (int i = 1; i < tableNameArray.size(); i++) {
            sqlQuery.append("UNION ALL " +
                    "(select " +
                    "LogID " +
                    ",priority " +
                    ",TransactionID" +
                    ",msg " +
                    ",request " +
                    ",timestamp " +
                    "from switch_public." + tableNameArray.get(i) + ") ");
//            sqlQuery.append(" LIMIT ").append("1000").append(") ");
//            if (switchLogsFromQueryCount.get(0) > limit) {
//                sqlQuery.append(" OFFSET ").append(page).append(") ");
//            }
        }

        sqlQuery.append(") AS switch_data");//ORDER BY LogID DESC
        if(transactionLegId != null) {
            sqlQuery.append(" WHERE transactionid = ").append(transactionLegId);
            if(startDate != null) {
                sqlQuery.append(" AND timestamp >= ").append("'" + startDate + "'");
                sqlQuery.append(" AND timestamp < ").append("'" + endDate + "'");
            }
        }
        else if(startDate != null){
            sqlQuery.append(" WHERE timestamp >= ").append("'"+startDate+"'");
            if(endDate != null){
                sqlQuery.append(" AND timestamp < ").append("'"+endDate+"'");
            }
        }
        else{
            sqlQuery.append(" WHERE transactionid > 0 ");
        }
        sqlQuery.append(" ORDER BY transactionid desc ");
        sqlQuery.append(" LIMIT ").append(limit);
        if (switchLogsFromQueryCount.get(0) > limit) {
            sqlQuery.append(" OFFSET ").append(page*limit);
        }

        logger.info("<<<<<<<<<< SQL QUERY: " + sqlQuery.toString());

        List<SwitchLog> switchLogsFromQuery =
                jdbcTemplate.query(sqlQuery.toString(), queryArgs.toArray(), new RowMapper<SwitchLog>(){
                    public SwitchLog mapRow(ResultSet rs, int rowNum) throws SQLException{
                        SwitchLog sw = new SwitchLog();
                        sw.setLogID(rs.getLong("LogID"));
                        sw.setPriority(rs.getString("priority"));
                        sw.setTransactionId(rs.getLong("TransactionID"));
                        sw.setMsg(rs.getString("msg"));
                        sw.setRequest(rs.getString("request"));
                        sw.setTimestamp(rs.getTimestamp("timestamp").toLocalDateTime());
                        return sw;
                    }
                });

        Pageable pageable = PageRequest.of(page, limit, Sort.by(Sort.Order.desc("timestamp")));
        return new PageImpl<SwitchLog>(switchLogsFromQuery, pageable, Long.parseLong(switchLogsFromQueryCount.get(0).toString()));

    }

    @Override
    public List<SwitchLog> getSwitchLogByTransactionLegId(long transactionLegId) {

        StringBuilder tableNameQuery
                = new StringBuilder("select table_name " +
                "from switch_information_schema.tables " +
                "where table_type = 'BASE TABLE' AND table_name like '%switch_log'");

        List<String> tableNameArray = new ArrayList<>();

        List<Map<String, Object>> tableNames =
                jdbcTemplate.queryForList(
                        tableNameQuery.toString());

        logger.info("<<<<<<<<<< SIZE OF TABLE NAME QUERY MAP: " + tableNames.size());

        for (Map tableNameMap : tableNames) {
            tableNameArray.add((String) tableNameMap.get("TABLE_NAME"));
        }

        logger.info("<<<<<<<<<< SIZE OF TABLE NAME ARRAY: " + tableNameArray.size());

        for (String tableName : tableNameArray) {
            logger.info("<<<<<<<<<< NAMES OF TABLES: " + tableName);
        }

        List<Object> queryArgs = new ArrayList<>();

        StringBuilder sqlQuery = new StringBuilder(" SELECT * FROM ((select " +
                "LogID " +
                ",priority " +
                ",TransactionID AS transaction_id " +
                ",msg " +
                ",request " +
                ",timestamp " +
                "from switch_public." + tableNameArray.get(0)  + ") ");

        for (int i = 1; i < tableNameArray.size(); i++) {
            sqlQuery.append("UNION ALL " +
                    "(select " +
                    "LogID " +
                    ",priority " +
                    ",TransactionID AS transaction_id " +
                    ",msg " +
                    ",request " +
                    ",timestamp " +
                    "from switch_public." + tableNameArray.get(i) + ") ");
        }

        sqlQuery.append(") AS switch_data");

        sqlQuery.append(" WHERE transaction_id = ?");
        queryArgs.add(transactionLegId);

        logger.info("<<<<<<<<<< SQL QUERY: " + sqlQuery.toString());

        List<SwitchLog> switchLogsFromQuery =
                jdbcTemplate.query(sqlQuery.toString(), queryArgs.toArray(), new RowMapper<SwitchLog>(){
                    public SwitchLog mapRow(ResultSet rs, int rowNum) throws SQLException{
                        SwitchLog sw = new SwitchLog();
                        sw.setLogID(rs.getLong("LogID"));
                        sw.setPriority(rs.getString("priority"));
                        sw.setTransactionId(rs.getLong("transaction_id"));
                        sw.setMsg(rs.getString("msg"));
                        sw.setRequest(rs.getString("request"));
                        sw.setTimestamp(rs.getTimestamp("timestamp").toLocalDateTime());
                        return sw;
                    }
                });

        return switchLogsFromQuery;
    }
}
