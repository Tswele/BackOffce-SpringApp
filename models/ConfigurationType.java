package za.co.wirecard.channel.backoffice.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor

public class ConfigurationType {
    @NotNull
    private long id;
    private String name;
    private String description;
    private String code;
    @JsonFormat(pattern="yyyy-MM-dd")
    private Timestamp lastModified;
}
