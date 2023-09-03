package za.co.wirecard.channel.backoffice.constants;

import java.util.ArrayList;
import java.util.List;

public enum SearchEnum {

    CARD_HOLDER("CARD_HOLDER"),
    MERCHANT_REF("MERCHANT_REF"),
    BANK_REFERENCE("BANK_REFERENCE"),
    SESSION_UID("SESSION_UID"),
    TRANSACTION_UID("TRANSACTION_UID"),
    SETTLED_VALUE("SETTLED_VALUE"),
    REFUNDED_VALUE("REFUNDED_VALUE"),
    AUTHORISED_VALUE("AUTHORISED_VALUE"),
    TRANSACTION_VALUE("TRANSACTION_VALUE"),
    AUTHORISATION_CODE("AUTHORISATION_CODE"),
    CARD_NUMBER("CARD_NUMBER"),
    NAME("NAME"),
    INVOICE_NUMBER("INVOICE_NUMBER"),
    LINE_ITEM_NAME("LINE_ITEM_NAME");


    private String value;
    SearchEnum(String value) {
        this.value = value;
    }
    public String value() {
        return value;
    }
    @Override
    public String toString() {
        return String.valueOf(value);
    }
    public static SearchEnum fromValue(String v) {
        for (SearchEnum b : SearchEnum.values()) {
            if (String.valueOf(b.value).equals(v)) {
                return b;
            }
        }
        return null;
    }
    public static String[] getSearchValuesAsString() {
        List<String> transactionStates = new ArrayList<>();
        for (SearchEnum b : SearchEnum.values()) {
            transactionStates.add(b.value());
        }
        String[] returnList = new String[transactionStates.size()];
        return transactionStates.toArray(returnList);
    }
}
