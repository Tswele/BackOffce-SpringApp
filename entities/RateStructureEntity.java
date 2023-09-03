package za.co.wirecard.channel.backoffice.entities;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.hibernate.annotations.LazyCollection;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Collection;

@Data
@Entity
@Table(name = "rate_structure", schema = "dbo", catalog = "transaction_db")
public class RateStructureEntity {
    @Id@GeneratedValue(strategy = GenerationType.IDENTITY)@Column(name = "id", nullable = false)
    private long id;
    @Basic@Column(name = "name", nullable = false, length = 250)
    private String name;
    @Basic@Column(name = "description", nullable = false, length = 500)
    private String description;
    @Basic@Column(name = "code", nullable = false, length = 50)
    private String code;
    @Basic@Column(name = "last_modified", nullable = false)
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Timestamp lastModified;
    @OneToMany(mappedBy = "rateStructureId", fetch = FetchType.LAZY)
    private Collection<RateStructureVersionEntity> rateStructureVersionsById;
    @OneToMany(mappedBy = "rateStructureId", fetch = FetchType.LAZY)
    private Collection<PricingModelEntity> pricingModelsById;

}
