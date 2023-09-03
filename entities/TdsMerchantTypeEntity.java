package za.co.wirecard.channel.backoffice.entities;

import lombok.*;

import javax.persistence.*;

@Data
@Entity
@Table(name = "tds_merchant_type", schema = "dbo", catalog = "transaction_db")
public class TdsMerchantTypeEntity {
    @Id@Column(name = "id", nullable = false)
    private long id;
    @Basic@Column(name = "name", nullable = false, length = 50)
    private String name;
    @Basic@Column(name = "description", nullable = false, length = 50)
    private String description;
    @Basic@Column(name = "code", nullable = false, length = 50)
    private String code;
    @Basic@Column(name = "is_3dsecure", nullable = false)
    private boolean is3Dsecure;

}
