package za.co.wirecard.channel.backoffice.entities;

import lombok.*;

import javax.persistence.*;

@Data
@Entity
@ToString
@Table(name = "merchant_onboarding", schema = "dbo", catalog = "transaction_db")
public class MerchantOnboardingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private long id;

    @Basic
    @Column(name = "merchant_id")
    private Long merchantId;

    @Basic
    @Column(name = "physical_address_id")
    private Long merchantPhysicalAddressId;

    @Basic
    @Column(name = "postal_address_id")
    private Long merchantPostalAddressId;

    @Basic
    @Column(name = "billing_detail_id")
    private Long billingDetailId;

    @Basic
    @Column(name = "admin_contact_id")
    private Long adminContactId;

//    @ManyToOne@JoinColumn(name = "admin_contact_id", referencedColumnName = "id", insertable = false, updatable = false)
//    private AuthUserEntity authUserByAdminContact;
//    @ManyToOne@JoinColumn(name = "physical_address_id", referencedColumnName = "id", insertable = false, updatable = false)
//    private MerchantAddressEntity merchantAddressByPhysicalAddressId;
//    @ManyToOne@JoinColumn(name = "postal_address_id", referencedColumnName = "id", insertable = false, updatable = false)
//    private MerchantAddressEntity merchantAddressByPostalAddressId;
//    @ManyToOne@JoinColumn(name = "billing_detail_id", referencedColumnName = "id", insertable = false, updatable = false)
//    private BillingDetailEntity billingDetailByBillingDetailId;
//    @ManyToOne@JoinColumn(name = "merchant_id", referencedColumnName = "id", insertable = false, updatable = false)
//    private MerchantEntity merchantByMerchantId;

}
