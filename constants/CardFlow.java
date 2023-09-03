package za.co.wirecard.channel.backoffice.constants;

import java.util.ArrayList;
import java.util.List;

public enum CardFlow {
    CARD("CARD"),
    ONE_CLICK("ONE_CLICK"),
    RECURRING("RECURRING"),
    CARD_ON_FILE("CARD_ON_FILE");

    private String value;
    CardFlow(String value) {
        this.value = value;
    }
    public String value() {
        return value;
    }
    @Override
    public String toString() {
        return String.valueOf(value);
    }
    public static CardFlow fromValue(String v) {
        for (CardFlow b : CardFlow.values()) {
            if (String.valueOf(b.value).equals(v)) {
                return b;
            }
        }
        return null;
    }
    public static String[] getSearchValuesAsString() {
        List<String> transactionStates = new ArrayList<>();
        for (CardFlow b : CardFlow.values()) {
            transactionStates.add(b.value());
        }
        String[] returnList = new String[transactionStates.size()];
        return transactionStates.toArray(returnList);
    }
}
