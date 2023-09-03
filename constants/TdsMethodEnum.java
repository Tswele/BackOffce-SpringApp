package za.co.wirecard.channel.backoffice.constants;

import java.util.ArrayList;
import java.util.List;

public enum TdsMethodEnum {
    
    BANKSERV_V1("BANKSERV_V1"),
    BANKSERV_V2("BANKSERV_V2"),
    CARDINAL_V2("CARDINAL_V2");

    private String value;
    TdsMethodEnum(String value) {
        this.value = value;
    }
    public String value() {
        return value;
    }
    @Override
    public String toString() {
        return String.valueOf(value);
    }
    public static TdsMethodEnum fromValue(String v) {
        for (TdsMethodEnum b : TdsMethodEnum.values()) {
            if (String.valueOf(b.value).equals(v)) {
                return b;
            }
        }
        return null;
    }
    public static String[] getSearchValuesAsString() {
        List<String> transactionStates = new ArrayList<>();
        for (TdsMethodEnum b : TdsMethodEnum.values()) {
            transactionStates.add(b.value());
        }
        String[] returnList = new String[transactionStates.size()];
        return transactionStates.toArray(returnList);
    }
}
