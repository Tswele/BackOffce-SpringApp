package za.co.wirecard.channel.backoffice.constants;

import java.util.ArrayList;
import java.util.List;

public enum ClientSearchEnum {

    MERCHANT_UID("MERCHANT_UID"),
    APPLICATION_UID("APPLICATION_UID"),
    CLIENT_NAME("CLIENT_NAME"),
    ACCOUNT_NO("ACCOUNT_NO");


    private String value;
    ClientSearchEnum(String value) {
        this.value = value;
    }
    public String value() {
        return value;
    }
    @Override
    public String toString() {
        return String.valueOf(value);
    }
    public static ClientSearchEnum fromValue(String v) {
        for (ClientSearchEnum b : ClientSearchEnum.values()) {
            if (String.valueOf(b.value).equals(v)) {
                return b;
            }
        }
        return null;
    }
    public static String[] getSearchValuesAsString() {
        List<String> transactionStates = new ArrayList<>();
        for (ClientSearchEnum b : ClientSearchEnum.values()) {
            transactionStates.add(b.value());
        }
        String[] returnList = new String[transactionStates.size()];
        return transactionStates.toArray(returnList);
    }
}
