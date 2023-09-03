package za.co.wirecard.channel.backoffice.constants;

import java.util.ArrayList;
import java.util.List;

public enum GatewayEnum {

    STANDARD_BANK("STANDARD_BANK"),
    BW("BW"),
    ABSA("ABSA"),
    FNB("FNB"),
    NEDBANK("NEDBANK"),
    VACP("VACP"),
    PLANET_PAYMENT_MCP("PLANET_PAYMENT_MCP"),
    BOW("BOW");

    private String value;
    GatewayEnum(String value) {
        this.value = value;
    }
    public String value() {
        return value;
    }
    @Override
    public String toString() {
        return String.valueOf(value);
    }
    public static GatewayEnum fromValue(String v) {
        for (GatewayEnum b : GatewayEnum.values()) {
            if (String.valueOf(b.value).equals(v)) {
                return b;
            }
        }
        return null;
    }
    public static String[] getSearchValuesAsString() {
        List<String> transactionStates = new ArrayList<>();
        for (GatewayEnum b : GatewayEnum.values()) {
            transactionStates.add(b.value());
        }
        String[] returnList = new String[transactionStates.size()];
        return transactionStates.toArray(returnList);
    }

}
