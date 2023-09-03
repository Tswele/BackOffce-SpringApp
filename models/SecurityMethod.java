package za.co.wirecard.channel.backoffice.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.Date;

@Setter
@Getter
@NoArgsConstructor
public class SecurityMethod {
    @NotNull
    private long id;
    @NotNull
    private String securityMethodName;
    @NotNull
    private String description;
    @NotNull
    private String code;
    @NotNull
    private Date lastModified;
}
