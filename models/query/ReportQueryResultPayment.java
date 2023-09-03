package za.co.wirecard.channel.backoffice.models.query;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReportQueryResultPayment {
    String Full_Name;
    String Merchant_Reference;
    String Transaction_UID;
    String initiation_Date;
    String Settled_Value;
    String Refund_value;
    String CODE;
    String last_updated_date;
}