package za.co.wirecard.channel.backoffice.models.requests;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.sql.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateAdminUserRequest {
    @NotNull
    private long groupId;
    @NotNull
    private long merchantId;
    private Long createdBy;
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
    private Date birthDate;
    private String position;
    @NotNull
    private boolean primaryContact;
    @NotNull
    private boolean isContact;
}
