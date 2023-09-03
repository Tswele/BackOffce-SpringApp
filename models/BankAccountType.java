package za.co.wirecard.channel.backoffice.models;

import lombok.*;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BankAccountType {
    @NotNull
    private long id;
    private String name;
}
