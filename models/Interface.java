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

public class Interface {

    @NotNull
    private String name;
    @NotNull
    private String description;
    @JsonFormat(pattern="yyyy-MM-dd")
    private Timestamp lastModified;
    @NotNull
    private String merchantMid;

}
