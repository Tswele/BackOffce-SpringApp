package za.co.wirecard.channel.backoffice.dto.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import za.co.wirecard.channel.backoffice.entities.StatusEntity;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductData {
    private boolean activeForMerchant;
    private Long id;
    private String name;
    private String description;
    private StatusEntity statusId;
}
