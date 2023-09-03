package za.co.wirecard.channel.backoffice.dto.models.responses;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import za.co.wirecard.channel.backoffice.dto.models.UserRolesContainer;
import za.co.wirecard.channel.backoffice.models.Client;
import za.co.wirecard.channel.backoffice.models.User;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PlatformGetUserByIdResponse {
    @NotNull
    private long id;
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
    //private Merchant merchant;
    private Client merchant;
    private GroupWithId group;
    private boolean isAccountManager;
    private boolean isSalePerson;
    private boolean isCreditController;
}
