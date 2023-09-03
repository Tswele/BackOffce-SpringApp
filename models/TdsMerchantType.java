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
public class TdsMerchantType {
    @NotNull
    private long id;
    private String name;
    private String description;
    private String code;
    private boolean is3Dsecure;
}
