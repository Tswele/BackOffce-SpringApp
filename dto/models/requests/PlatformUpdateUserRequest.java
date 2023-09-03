package za.co.wirecard.channel.backoffice.dto.models.requests;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import za.co.wirecard.channel.backoffice.dto.models.UserRolesContainer;
import za.co.wirecard.channel.backoffice.models.User;

import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PlatformUpdateUserRequest {
    @NotNull
    private long id;
    @NotNull
    private String firstName;
    @NotNull
    private String lastName;
    private String knownAs;
    @NotNull
    private String email;
    @NotNull
    private String cell;
    private String landline;
    @NotNull
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date birthDate;
    private String position;
    @NotNull
    private long groupId;
    @NotNull
    private long modifiedBy;
    @NotNull
    private long createdBy;
    @NotNull
    private boolean active;
    private UserRolesContainer userRolesContainer;
}
