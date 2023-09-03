package za.co.wirecard.channel.backoffice.entities;

import lombok.*;

import javax.persistence.*;

@Data
@Entity
@Table(name = "currency", schema = "dbo", catalog = "transaction_db")
public class CurrencyEntity {
    @Id@GeneratedValue(strategy = GenerationType.IDENTITY)@Column(name = "id", nullable = false)
    private long id;
    @Basic@Column(name = "name", nullable = false, length = 250)
    private String name;
    @Basic@Column(name = "alpha_code", nullable = false, length = 50)
    private String alphaCode;
    @Basic@Column(name = "code", nullable = false, length = 50)
    private String code;
    @Basic@Column(name = "symbol", nullable = false, length = 50)
    private String symbol;
    @Basic@Column(name = "minor_unit", nullable = false)
    private byte minorUnit;
    @Basic@Column(name = "currency_number", nullable = true)
    private Integer currencyNumber;

}
