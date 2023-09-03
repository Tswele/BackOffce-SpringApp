package za.co.wirecard.channel.backoffice.entities;

import lombok.*;

import javax.persistence.*;
import java.util.Collection;

@Data
@Entity
@ToString
@Table(name = "merchant_address", schema = "dbo", catalog = "transaction_db")
public class MerchantAddressEntity {
    @Id
    @Column(name = "id")
    private long id;
    @Basic@Column(name = "address_line_1")
    private String addressLine1;
    @Basic@Column(name = "address_line_2")
    private String addressLine2;
    @Basic@Column(name = "address_line_3")
    private String addressLine3;
    @Basic@Column(name = "address_line_4")
    private String addressLine4;
    @Basic@Column(name = "postal_code")
    private String postalCode;
    @Basic@Column(name = "city_id", insertable = false, updatable = false)
    private long cityId;
    @Basic@Column(name = "merchant_id")
    private long merchantId;
    @ManyToOne@JoinColumn(name = "city_id", referencedColumnName = "id", nullable = false)
    private CityEntity city;
    @Basic@Column(name = "postal_address")
    private boolean postalAddress;
}
