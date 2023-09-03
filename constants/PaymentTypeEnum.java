package za.co.wirecard.channel.backoffice.constants;

import java.util.ArrayList;
import java.util.List;

public enum PaymentTypeEnum {

    CARD("CARD");

    private String value;
    PaymentTypeEnum(String value) {
        this.value = value;
    }
    public String value() {
        return value;
    }
    @Override
    public String toString() {
        return String.valueOf(value);
    }
    public static PaymentTypeEnum fromValue(String v) {
        for (PaymentTypeEnum b : PaymentTypeEnum.values()) {
            if (String.valueOf(b.value).equals(v)) {
                return b;
            }
        }
        return null;
    }
    public static String[] getSearchValuesAsString() {
        List<String> transactionStates = new ArrayList<>();
        for (PaymentTypeEnum b : PaymentTypeEnum.values()) {
            transactionStates.add(b.value());
        }
        String[] returnList = new String[transactionStates.size()];
        return transactionStates.toArray(returnList);
    }

}
