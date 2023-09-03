package za.co.wirecard.channel.backoffice.entities;

import lombok.*;

import javax.persistence.*;
import java.util.Collection;


@Data
@Entity
@Table(name = "billing_detail", schema = "dbo", catalog = "transaction_db")
public class BillingDetailEntity {

    @Id
    @Basic
    @Column(name = "id")
    private long id;

    @Basic
    @Column(name = "merchant_id")
    private long merchantId;

    @ManyToOne
    @JoinColumn(name = "payment_method_id", referencedColumnName = "id")
    private PaymentTypeEntity paymentTypeEntity;

    // @ManyToOne
    // @JoinColumn
    // billing.dbo.application_rate_structures_LI
    @ManyToOne
    @JoinColumn(name = "rate_structure_id", referencedColumnName = "id")
    private RateStructureEntity rateStructure;

    @Basic
    @Column(name = "invoice_day")
    private int invoiceDay;

    @ManyToOne
    @JoinColumn(name = "back_office_user_credit_controller_id", referencedColumnName = "id")
    private BackOfficeUserEntity creditController;

    @ManyToOne
    @JoinColumn(name = "bank_branch_code_id", referencedColumnName = "id")
    private BankBranchCodeEntity bankBranchCodeEntity;

    @Basic
    @Column(name = "account_number")
    private String accountNumber;

    @Basic
    @Column(name = "account_holder")
    private String accountHolder;

    @ManyToOne
    @JoinColumn(name = "bank_account_type_id", referencedColumnName = "id")
    private BankAccountTypeEntity bankAccountTypeEntity;

    @ManyToOne
    @JoinColumn(name = "bank_id", referencedColumnName = "id")
    private BankEntity bankEntity;

    @Basic
    @Column(name = "xero_tenant_id")
    private String xeroTenantId;

    @Basic
    @Column(name = "xero_contact_id")
    private String xeroContactId;

    @Basic
    @Column(name = "invoice_due_date_offset")
    private Long invoiceDueDateOffset;
}

