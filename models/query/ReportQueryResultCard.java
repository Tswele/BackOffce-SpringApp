package za.co.wirecard.channel.backoffice.models.query;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReportQueryResultCard {

    String cardholder_fullname;
    String merchant_reference;
    String transaction_uid;
    String initiation_date;
    String last_updated_date;
    String Card_Number;
    String settled_value;
    String refund_value;
    String code;
    String bank_error_description;
    String bank_error_code;
    String card_type;
    String ip_address;
    String eci;
    String card_number_hash;
    String error_code;
    String error_message;
    String authorisation_number;

}