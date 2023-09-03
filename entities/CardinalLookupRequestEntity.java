package za.co.wirecard.channel.backoffice.entities;

import lombok.*;

import javax.persistence.*;

@Data
@Entity
@Table(name = "cardinal_lookup_request", schema = "dbo", catalog = "transaction_db")
public class CardinalLookupRequestEntity {
    @Id@Column(name = "id")
    private long id;
    @Basic@Column(name = "amount")
    private long amount;
    @Basic@Column(name = "billing_address_1")
    private String billingAddress1;
    @Basic@Column(name = "billing_city")
    private String billingCity;
    @Basic@Column(name = "billing_country_code")
    private String billingCountryCode;
    @Basic@Column(name = "billing_first_name")
    private String billingFirstName;
    @Basic@Column(name = "billing_last_name")
    private String billingLastName;
    @Basic@Column(name = "billing_postal_code")
    private String billingPostalCode;
    @Basic@Column(name = "card_exp_month")
    private String cardExpMonth;
    @Basic@Column(name = "card_exp_year")
    private String cardExpYear;
    @Basic@Column(name = "card_number")
    private String cardNumber;
    @Basic@Column(name = "currency_code")
    private String currencyCode;
    @Basic@Column(name = "df_reference_id")
    private String dfReferenceId;
    @Basic@Column(name = "email")
    private String email;
    @Basic@Column(name = "mobile_phone")
    private String mobilePhone;
    @Basic@Column(name = "version")
    private String version;
    @Basic@Column(name = "transaction_mode")
    private String transactionMode;
    @Basic@Column(name = "msg_type")
    private String msgType;
    @Basic@Column(name = "transaction_type")
    private String transactionType;
    @Basic@Column(name = "order_number")
    private String orderNumber;
    private boolean isBrowserJavascriptEnabled;
    @Basic@Column(name = "algorithm")
    private String algorithm;
    @Basic@Column(name = "identifier")
    private String identifier;
    @Basic@Column(name = "org_unit")
    private String orgUnit;
    @Basic@Column(name = "signature")
    private String signature;
    @Basic@Column(name = "date_logged")
    private String dateLogged;
    @Basic@Column(name = "transaction_leg_id")
    private long transactionLegId;

    @Basic
    @Column(name = "is_browser_javascript_enabled")
    public boolean isBrowserJavascriptEnabled() {
        return isBrowserJavascriptEnabled;
    }

    public void setBrowserJavascriptEnabled(boolean browserJavascriptEnabled) {
        isBrowserJavascriptEnabled = browserJavascriptEnabled;
    }

}
