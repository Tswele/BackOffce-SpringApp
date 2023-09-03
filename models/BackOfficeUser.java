package za.co.wirecard.channel.backoffice.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import za.co.wirecard.channel.backoffice.entities.BackOfficeUserEntity;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
public class BackOfficeUser {

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
    private String position;
    private boolean isAccountManager;
    private boolean isSalesPerson;
    private boolean isCreditController;

    public BackOfficeUser(BackOfficeUserEntity backOfficeUserEntity) {
        this.firstName = backOfficeUserEntity.getFirstName();
        this.lastName = backOfficeUserEntity.getLastName();
        this.id = backOfficeUserEntity.getId();
        this.email = backOfficeUserEntity.getEmail();
        this.cell = backOfficeUserEntity.getCell();
        this.landline = backOfficeUserEntity.getLandline();
        this.position = backOfficeUserEntity.getPosition();
        this.isAccountManager = backOfficeUserEntity.getIsAccountManager();
        this.isSalesPerson = backOfficeUserEntity.getIsSalesPerson();
        this.isCreditController = backOfficeUserEntity.getIsCreditController();
    }


}
