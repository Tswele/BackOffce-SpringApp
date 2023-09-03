package za.co.wirecard.channel.backoffice.models;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
public class Group {
    @NotNull
    private long id;
    private String name;
    private String code;
    private long createdBy;
    private long modifiedBy;
}
