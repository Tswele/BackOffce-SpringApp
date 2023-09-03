package za.co.wirecard.channel.backoffice.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Collection;

@Data
@Entity
@Table(name = "pricing_model", schema = "dbo", catalog = "transaction_db")
public class PricingModelEntity {
    @Id@GeneratedValue(strategy = GenerationType.IDENTITY)@Column(name = "id", nullable = false)
    private Long id;
    @Basic@Column(name = "name", nullable = false, length = 250)
    private String name;
    @Basic@Column(name = "description", nullable = false, length = 500)
    private String description;
    @Basic@Column(name = "code", nullable = false, length = 50)
    private String code;
    @Basic@Column(name = "last_modified", nullable = false)
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Timestamp lastModified;
    @Basic@Column(name = "global_pricing_model", nullable = false)
    private Boolean globalPricingModel;
    @ManyToOne@JoinColumn(name = "currency_id", referencedColumnName = "id", nullable = false)
    private CurrencyEntity currencyByCurrencyId;
    @OneToMany(mappedBy = "pricingModelId")
    private Collection<PricingModelVersionEntity> pricingModelVersionsById;
    @Basic@Column(name = "rate_structure_id", nullable = true)
    private Long rateStructureId;

}
