package za.co.wirecard.channel.backoffice.entities;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name = "city", schema = "dbo", catalog = "transaction_db")
public class CityEntity {
    private long id;
    private String name;
    private String code;
    private long provinceId;
    private ProvinceEntity province;
    // private Collection<AddressEntity> AddressesById;

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

        CityEntity that = (CityEntity) o;

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
    @Column(name = "province_id", insertable = false, updatable = false)
    public long getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(long provinceId) {
        this.provinceId = provinceId;
    }

    @ManyToOne
    @JoinColumn(name = "province_id", referencedColumnName = "id", nullable = false)
    public ProvinceEntity getProvince() {
        return province;
    }

    public void setProvince(ProvinceEntity province) {
        this.province = province;
    }

//    @OneToMany(mappedBy = "cityByCityId")
//    public Collection<AddressEntity> getAddressesById() {
//        return AddressesById;
//    }
//
//    public void setAddressesById(Collection<AddressEntity> addressesById) {
//        AddressesById = addressesById;
//    }
}
