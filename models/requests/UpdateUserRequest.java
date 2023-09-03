package za.co.wirecard.channel.backoffice.models.requests;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import za.co.wirecard.channel.backoffice.dto.models.UserRolesContainer;
import za.co.wirecard.channel.backoffice.models.User;

import javax.validation.constraints.NotNull;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class UpdateUserRequest {
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
    private long createdBy;
    private long modifiedBy;
    private long groupId;
    private UserRolesContainer userRoles;
    private Boolean active;
}
