package za.co.wirecard.channel.backoffice.dto.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRolesContainer {

    private boolean completed;
    private String name;
    private String color;
    private List<UserRole> userRoles;

}
