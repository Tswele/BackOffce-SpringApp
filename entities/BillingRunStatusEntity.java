package za.co.wirecard.channel.backoffice.entities;

import lombok.*;

import javax.persistence.*;
import java.util.Collection;
import java.util.Objects;

@Data
@Entity
@Table(name = "billing_run_status", schema = "dbo", catalog = "transaction_db")
public class BillingRunStatusEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private long id;
    @Basic
    @Column(name = "name", nullable = false, length = 250)
    private String name;
    @Basic
    @Column(name = "code", nullable = false, length = 100)
    private String code;
    @Basic
    @Column(name = "description", nullable = false, length = 500)
    private String description;

}
