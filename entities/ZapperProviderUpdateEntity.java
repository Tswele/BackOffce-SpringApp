package za.co.wirecard.channel.backoffice.entities;

import lombok.*;

import javax.persistence.*;

@Data
@Entity
@Table(name = "zapper_provider_update", schema = "dbo", catalog = "transaction_db")
public class ZapperProviderUpdateEntity {
    @Id@Column(name = "id")
    private long id;
    @Basic@Column(name = "transaction_leg_id")
    private long transactionLegId;
    @Basic@Column(name = "status")
    private int status;
    @Basic@Column(name = "currency_iso_code")
    private String currencyIsoCode;
    @Basic@Column(name = "customer_first_name")
    private String customerFirstName;
    @Basic@Column(name = "customer_last_name")
    private String customerLastName;
    @Basic@Column(name = "invoiced_amount")
    private long invoicedAmount;
    @Basic@Column(name = "payment_reference")
    private String paymentReference;
    @Basic@Column(name = "payment_utc_date")
    private String paymentUtcDate;
    @Basic@Column(name = "invoice_external_reference")
    private String invoiceExternalReference;
    @Basic@Column(name = "invoice_reference")
    private String invoiceReference;
    @Basic@Column(name = "tendered_amount")
    private long tenderedAmount;
    @Basic@Column(name = "tip_amount")
    private long tipAmount;

}
