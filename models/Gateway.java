package za.co.wirecard.channel.backoffice.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import za.co.wirecard.channel.backoffice.entities.GatewayEntity;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
public class Gateway {

    @NotNull
    private long id;
    private String name;
    private String code;

    public Gateway (GatewayEntity gatewayEntity){

        this.setId(gatewayEntity.getId());
        this.setName(gatewayEntity.getName());
        this.setCode(gatewayEntity.getCode());

    }

}
