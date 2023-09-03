package za.co.wirecard.channel.backoffice.dto.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductSalesGroupData {
    private Long id;
    private String name;
    private String description;
    private List<ProductData> products;

    public ProductSalesGroupData(long id, String name, String description){
        this.id = id;
        this.name = name;
        this.description = description;
        this.products = new ArrayList<>();
    }
}
