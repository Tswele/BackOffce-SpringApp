package za.co.wirecard.channel.backoffice.entities;

import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;

@Data
@Entity
@Table(name = "trading_currency", schema = "dbo", catalog = "transaction_db")
public class TradingCurrencyEntity {
    @Id@GeneratedValue(strategy = GenerationType.IDENTITY)@Column(name = "id", nullable = false)
    private long id;
    @Basic@Column(name = "last_modified", nullable = false)
    private Timestamp lastModified;
    @ManyToOne@JoinColumn(name = "currency_id", referencedColumnName = "id", nullable = false)
    private CurrencyEntity currencyByCurrencyId;
    @ManyToOne@JoinColumn(name = "country_id", referencedColumnName = "id", nullable = false)
    private CountryEntity countryByCountryId;

}
