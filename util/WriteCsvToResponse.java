package za.co.wirecard.channel.backoffice.util;

import com.opencsv.CSVWriter;
import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.HeaderColumnNameMappingStrategy;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import za.co.wirecard.channel.backoffice.dto.models.CardBatchRecord;
import za.co.wirecard.channel.backoffice.models.query.ReportQueryResultCard;
import za.co.wirecard.channel.backoffice.models.query.ReportQueryResultPayment;
import za.co.wirecard.channel.backoffice.models.query.ReportQueryResultSettlement;

import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class WriteCsvToResponse {

    private static class ClassFieldOrderComparator implements Comparator<String> {

        List<String> fieldNamesInOrderWithinClass;

        public ClassFieldOrderComparator(Class<?> clazz) {
            fieldNamesInOrderWithinClass = Arrays.stream(clazz.getDeclaredFields())
                    .filter(field -> field.getAnnotation(CsvBindByName.class) != null)
                    // Handle order by your custom annotation here
                    //.sorted((field1, field2) -> {
                    //   int field1Order = field1.getAnnotation(YourCustomOrderAnnotation.class).getOrder();
                    //   int field2Order = field2.getAnnotation(YourCustomOrderAnnotation.class).getOrder();
                    //   return Integer.compare(field1Order, field2Order);
                    //})
                    .map(field -> field.getName())
                    .collect(Collectors.toList());
        }

        @Override
        public int compare(String o1, String o2) {
            int fieldIndexo1 = fieldNamesInOrderWithinClass.indexOf(o1);
            int fieldIndexo2 = fieldNamesInOrderWithinClass.indexOf(o2);
            return Integer.compare(fieldIndexo1, fieldIndexo2);
        }
    }

    private static final Logger LOGGER = LoggerFactory.getLogger(WriteCsvToResponse.class);

    public static void writeQueryResultsPayments(PrintWriter writer, List<ReportQueryResultPayment> reportQueryResultsPayment) {

        try {
            HeaderColumnNameMappingStrategy<ReportQueryResultPayment> mappingStrategy
                    = new HeaderColumnNameMappingStrategy<>();
            mappingStrategy.setType(ReportQueryResultPayment.class);
            mappingStrategy.setColumnOrderOnWrite(new ClassFieldOrderComparator(ReportQueryResultPayment.class));

            StatefulBeanToCsv<ReportQueryResultPayment> btcsv = new StatefulBeanToCsvBuilder<ReportQueryResultPayment>(writer)
                    .withQuotechar(CSVWriter.NO_QUOTE_CHARACTER)
                    .withMappingStrategy(mappingStrategy)
                    .withSeparator(',')
                    .build();

            btcsv.write(reportQueryResultsPayment);

        } catch (CsvException ex) {

        }
    }

    public static void writeQueryResultsCard(PrintWriter writer, List<ReportQueryResultCard> reportQueryResultsCard) {

        try {
            HeaderColumnNameMappingStrategy<ReportQueryResultCard> mappingStrategy
                    = new HeaderColumnNameMappingStrategy<>();
            mappingStrategy.setType(ReportQueryResultCard.class);
            mappingStrategy.setColumnOrderOnWrite(new ClassFieldOrderComparator(ReportQueryResultCard.class));

            StatefulBeanToCsv<ReportQueryResultCard> btcsv = new StatefulBeanToCsvBuilder<ReportQueryResultCard>(writer)
                    .withQuotechar(CSVWriter.NO_QUOTE_CHARACTER)
                    .withMappingStrategy(mappingStrategy)
                    .withSeparator(',')
                    .build();

            btcsv.write(reportQueryResultsCard);

        } catch (CsvException ex) {

        }
    }

    public static void writeQueryResultsSettlement(PrintWriter writer, List<ReportQueryResultSettlement> reportQueryResultsSettlement) {

        try {

            HeaderColumnNameMappingStrategy<ReportQueryResultSettlement> mappingStrategy
                    = new HeaderColumnNameMappingStrategy<>();
            mappingStrategy.setType(ReportQueryResultSettlement.class);
            mappingStrategy.setColumnOrderOnWrite(new ClassFieldOrderComparator(ReportQueryResultSettlement.class));

//            ColumnPositionMappingStrategy<ReportQueryResultSettlement> mapStrategy
//                    = new ColumnPositionMappingStrategy<>();
//            mapStrategy.setType(ReportQueryResultSettlement.class);
//            String[] columns = new String[]{"Date", "Settled_Count", "Settled", "Refunded_Count", "Refunded", "Net_Settlement"};
//            mapStrategy.setColumnOrderOnWrite(new ClassFieldOrderComparator(ReportQueryResultSettlement.class));
            // mapStrategy.setColumnMapping(columns);

            StatefulBeanToCsv<ReportQueryResultSettlement> btcsv = new StatefulBeanToCsvBuilder<ReportQueryResultSettlement>(writer)
                    .withQuotechar(CSVWriter.NO_QUOTE_CHARACTER)
                    .withMappingStrategy(mappingStrategy)
                    .withSeparator(',')
                    .build();

            btcsv.write(reportQueryResultsSettlement);

        } catch (CsvException ex) {

        }
    }

    public static void writeResultsCardBatchRecord(PrintWriter writer, List<CardBatchRecord> cardBatchRecords) {
        try {

            HeaderColumnNameMappingStrategy<CardBatchRecord> mappingStrategy
                    = new HeaderColumnNameMappingStrategy<>();
            mappingStrategy.setType(CardBatchRecord.class);
            mappingStrategy.setColumnOrderOnWrite(new ClassFieldOrderComparator(CardBatchRecord.class));

            StatefulBeanToCsv<CardBatchRecord> btcsv = new StatefulBeanToCsvBuilder<CardBatchRecord>(writer)
                    .withQuotechar(CSVWriter.NO_QUOTE_CHARACTER)
                    .withMappingStrategy(mappingStrategy)
                    .withSeparator(',')
                    .build();

            btcsv.write(cardBatchRecords);

        } catch (CsvException ex) {

            LOGGER.error("Error mapping Bean to CSV", ex);
        }
    }

}
