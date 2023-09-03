package za.co.wirecard.channel.backoffice.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import za.co.wirecard.channel.backoffice.entities.TransactionStateEntity;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
public class TransactionState {

    @NotNull
    private long id;
    private String name;
    private String code;

    public TransactionState (TransactionStateEntity transactionStateEntity){

        this.setId(transactionStateEntity.getId());
        this.setName(transactionStateEntity.getName());
        this.setCode(transactionStateEntity.getCode());

    }

}
