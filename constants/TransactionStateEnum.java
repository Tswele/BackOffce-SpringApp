package za.co.wirecard.channel.backoffice.constants;

import java.util.ArrayList;
import java.util.List;

public enum TransactionStateEnum {

    SETTLED("SETTLED"),
    REFUNDED("REFUNDED"),
    PARTIALLY_REFUNDED("PARTIALLY_REFUNDED"),
    AUTHORISED("AUTHORISED"),
    TIMED_OUT("TIMED_OUT"),
    REVERSED("REVERSED"),
    COMPLETED("COMPLETED"),
    FAILED("FAILED"),
    TDS_AUTH_FAILED("TDS_AUTH_FAILED"),
    TDS_AUTH_REQUIRED("TDS_AUTH_REQUIRED"),
    PAYMENT_FAILED("PAYMENT_FAILED");
    private String value;
    TransactionStateEnum (String value) {
        this.value = value;
    }
    public String value() {
        return value;
    }
    @Override
    public String toString() {
        return String.valueOf(value);
    }
    public static TransactionStateEnum fromValue(String v) {
        for (TransactionStateEnum b : TransactionStateEnum.values()) {
            if (String.valueOf(b.value).equals(v)) {
                return b;
            }
        }
        return null;
    }
    public static String[] getTransactionStateValuesAsString() {
        List<String> transactionStates = new ArrayList<>();
        for (TransactionStateEnum b : TransactionStateEnum.values()) {
            transactionStates.add(b.value());
        }
        String[] returnList = new String[transactionStates.size()];
        return transactionStates.toArray(returnList);
    }
}
