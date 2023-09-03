package za.co.wirecard.channel.backoffice.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MobicredInterfaceConfig {
    @NotNull
    public long interfaceId;
    @NotNull
    public String cmerchantId;
    @NotNull
    public String cmerchantKey;
}
