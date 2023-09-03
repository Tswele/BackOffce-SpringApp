package za.co.wirecard.channel.backoffice.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import za.co.wirecard.channel.backoffice.entities.CardTypeEntity;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CardType {
    @NotNull
    private Long id;
    private String description;
    private String code;
    private String cardName;
    private Integer order;
    private String imageName;

    public CardType(CardTypeEntity cardTypeEntity) {
        this.setId(cardTypeEntity.getId());
        this.setCode(cardTypeEntity.getCode());
        this.setCardName(cardTypeEntity.getCardName());


    }
}
