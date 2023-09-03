package za.co.wirecard.channel.backoffice.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import za.co.wirecard.channel.backoffice.entities.StatusEntity;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MerchantStatus {
    private long id;
    private String name;
    private String code;
    public MerchantStatus(StatusEntity statusEntity) {
        this.id = statusEntity.getId();
        this.name = statusEntity.getName();
        this.code = statusEntity.getCode();
    }

    @Override
    public String toString() {
        return "MerchantStatus{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", code='" + code + '\'' +
                '}';
    }

}
