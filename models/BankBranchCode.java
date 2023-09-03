package za.co.wirecard.channel.backoffice.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor

public class BankBranchCode {

    @NotNull
    private long id;
    @NotNull
    private String code;

    //private Bank bank;
}
