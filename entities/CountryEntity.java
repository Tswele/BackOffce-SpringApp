package za.co.wirecard.channel.backoffice.entities;

import javax.persistence.*;

@Entity
@Table(name = "country", schema = "dbo", catalog = "transaction_db")
public class CountryEntity {
    private long id;
    private String alphaCode;
    private String code;
    private String name;
    private int isoNo;

    @Id
    @Column(name = "id", nullable = false)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "alpha_code", nullable = false, length = 50)
    public String getAlphaCode() {
        return alphaCode;
    }

    public void setAlphaCode(String alphaCode) {
        this.alphaCode = alphaCode;
    }

    @Basic
    @Column(name = "code", nullable = false, length = 50)
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Basic
    @Column(name = "name", nullable = false, length = 250)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "iso_no", nullable = false)
    public int getIsoNo() {
        return isoNo;
    }

    public void setIsoNo(int isoNo) {
        this.isoNo = isoNo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CountryEntity that = (CountryEntity) o;

        if (id != that.id) return false;
        if (isoNo != that.isoNo) return false;
        if (alphaCode != null ? !alphaCode.equals(that.alphaCode) : that.alphaCode != null) return false;
        if (code != null ? !code.equals(that.code) : that.code != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (alphaCode != null ? alphaCode.hashCode() : 0);
        result = 31 * result + (code != null ? code.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + isoNo;
        return result;
    }
}
