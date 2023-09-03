package za.co.wirecard.channel.backoffice.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import za.co.wirecard.channel.backoffice.dto.models.UserRolesContainer;

import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private Long id;
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
    @JsonFormat(pattern="yyyy-MM-dd")
    private Timestamp birthDate;
    private String position;
    private long createdBy;
    private long modifiedBy;
    private long groupId;
    private UserRolesContainer userRoles;
}
