package za.co.wirecard.channel.backoffice.entities;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Objects;

@Data
@Entity
@Table(name = "sub_line_item", schema = "dbo", catalog = "transaction_db")
public class SubLineItemEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private long id;
    @Basic
    @Column(name = "name", nullable = false, length = 250)
    private String name;
    @Basic
    @Column(name = "description", nullable = false, length = 500)
    private String description;
    @Basic
    @Column(name = "unit_price", nullable = true, precision = 2)
    private BigDecimal unitPrice;
    @Basic
    @Column(name = "line_item_id", nullable = false)
    private Long lineItemId;
    @OneToMany(mappedBy = "subLineItemId")
    private Collection<LineItemTransactionEntity> lineItemTransactionsById;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    public long getLineItemId() {
        return lineItemId;
    }

    public void setLineItemId(long lineItemId) {
        this.lineItemId = lineItemId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SubLineItemEntity that = (SubLineItemEntity) o;
        return id == that.id && Objects.equals(name, that.name) && Objects.equals(description, that.description) && Objects.equals(unitPrice, that.unitPrice) && Objects.equals(lineItemId, that.lineItemId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, lineItemId, name, description, unitPrice);
    }

    public Collection<LineItemTransactionEntity> getLineItemTransactionsById() {
        return lineItemTransactionsById;
    }

    public void setLineItemTransactionsById(Collection<LineItemTransactionEntity> lineItemTransactionsById) {
        this.lineItemTransactionsById = lineItemTransactionsById;
    }
}
