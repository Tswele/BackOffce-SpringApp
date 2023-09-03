package za.co.wirecard.channel.backoffice.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import za.co.wirecard.channel.backoffice.entities.StatusEntity;

import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class Application {
    @NotNull
    private long id;
    @NotNull
    private Merchant merchant;
    @NotNull
    private String name;
    private boolean autoSettle;
    @NotNull
    private String applicationUid;
    @JsonFormat(pattern="yyyy-MM-dd")
    private Timestamp lastModified;
    private List<SecurityType> securityTypes;
    private List<ConfigurationType> configurationTypes;
    private StatusEntity statusId;
}
