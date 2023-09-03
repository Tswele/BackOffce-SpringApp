package za.co.wirecard.channel.backoffice.entities;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name = "province", schema = "dbo", catalog = "transaction_db")
public class ProvinceEntity {
    private long id;
    private String name;
    private String code;
    private long countryId;
    // private Collection<CityEntity> citiesById;
    private CountryEntity country;

    @Id
    @Column(name = "id")
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "code")
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ProvinceEntity that = (ProvinceEntity) o;

        if (id != that.id) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (code != null ? !code.equals(that.code) : that.code != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (code != null ? code.hashCode() : 0);
        return result;
    }

    @Basic
    @Column(name = "country_id", insertable = false, updatable = false)
    public long getCountryId() {
        return countryId;
    }

    public void setCountryId(long countryId) {
        this.countryId = countryId;
    }

//    @OneToMany(mappedBy = "provinceByProvinceId")
//    public Collection<CityEntity> getCitiesById() {
//        return citiesById;
//    }
//
//    public void setCitiesById(Collection<CityEntity> citiesById) {
//        this.citiesById = citiesById;
//    }

    @ManyToOne
    @JoinColumn(name = "country_id", referencedColumnName = "id", nullable = false)
    public CountryEntity getCountry() {
        return country;
    }

    public void setCountry(CountryEntity country) {
        this.country = country;
    }
}

