package za.co.wirecard.channel.backoffice.dto.models.requests;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import za.co.wirecard.channel.backoffice.dto.models.UserRolesContainer;
import za.co.wirecard.channel.backoffice.models.User;

import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlatformCreateUserRequest {
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
    private boolean primaryContact;
    @NotNull
    private long groupId;
    @NotNull
    private long merchantId;
    @NotNull
    private long createdBy;
    @NotNull
    private long modifiedBy;
    private UserRolesContainer userRolesContainer;

}
