package za.co.wirecard.channel.backoffice.entities;

import lombok.*;

import javax.persistence.*;
import java.util.Collection;

@Data
@Entity
@Table(name = "product_sales_group", schema = "dbo", catalog = "transaction_db")
public class ProductSalesGroupEntity {
    @Id@Column(name = "id", nullable = false)
    private Long id;
    @Basic@Column(name = "name", nullable = false, length = 100)
    private String name;
    @Basic@Column(name = "description", nullable = false, length = 500)
    private String description;
//    @OneToMany(mappedBy = "productSalesGroupByProductSalesGroupId")
//    private Collection<ProductEntity> productsById;

}
