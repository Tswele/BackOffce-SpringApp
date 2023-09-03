package za.co.wirecard.channel.backoffice.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import za.co.wirecard.channel.backoffice.entities.CardTypeEntity;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CardTypeEntityGroupContainer {

    List<CardTypeEntity> cardTypeGroup;

}
