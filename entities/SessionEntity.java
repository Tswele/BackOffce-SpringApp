package za.co.wirecard.channel.backoffice.entities;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;

@Data
@Entity
@Table(name = "session", schema = "dbo", catalog = "transaction_db")
public class SessionEntity {
    @Id@Column(name = "id")
    private long id;
    @Basic@Column(name = "session_token")
    private String sessionToken;
    @Basic@Column(name = "date_logged")
    private Timestamp dateLogged;
    @Basic@Column(name = "session_active")
    private boolean sessionActive;
    @Basic@Column(name = "merchant_uid")
    private String merchantUid;
    @Basic@Column(name = "application_uid")
    private String applicationUid;
    @Basic@Column(name = "mode")
    private String mode;
    @Basic@Column(name = "merchant_reference")
    private String merchantReference;
    @Basic@Column(name = "amount")
    private BigDecimal amount;
    @Basic@Column(name = "currency_code")
    private String currencyCode;
    @Basic@Column(name = "redirect_successful_url")
    private String redirectSuccessfulUrl;
    @Basic@Column(name = "redirect_failed_url")
    private String redirectFailedUrl;
    private Boolean isPaymentLink;
    @Basic@Column(name = "validation_failed")
    private Boolean validationFailed;

    @Basic
    @Column(name = "is_payment_link")
    public Boolean getPaymentLink() {
        return isPaymentLink;
    }

    public void setPaymentLink(Boolean paymentLink) {
        isPaymentLink = paymentLink;
    }

}
