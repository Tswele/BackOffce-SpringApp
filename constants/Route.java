package za.co.wirecard.channel.backoffice.constants;

import java.util.ArrayList;
import java.util.List;

public enum Route {

    USERS("CARD_HOLDER"),
    TRANSACTION_MANAGER("MERCHANT_REF"),
    SESSION_MANAGER("BANK_REFERENCE"),
    BATCH_MANAGER("TRANSACTION_UID"),
    GROUPS("SETTLED_VALUE"),
    PERMISSIONS("REFUNDED_VALUE"),
    CLIENT_MANAGER("CLIENT_MANAGER"),
    RATE_STRUCTURES("RATE_STRUCTURES"),
    PRODUCT_DEFAULT("PRODUCT_DEFAULT"),
    BILLING("BILLING");

    private String value;
    Route(String value) {
        this.value = value;
    }
    public String value() {
        return value;
    }
    @Override
    public String toString() {
        return String.valueOf(value);
    }
    public static Route fromValue(String v) {
        for (Route b : Route.values()) {
            if (String.valueOf(b.value).equals(v)) {
                return b;
            }
        }
        return null;
    }
    public static String[] getSearchValuesAsString() {
        List<String> transactionStates = new ArrayList<>();
        for (Route b : Route.values()) {
            transactionStates.add(b.value());
        }
        String[] returnList = new String[transactionStates.size()];
        return transactionStates.toArray(returnList);
    }

}
