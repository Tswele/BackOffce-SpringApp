package za.co.wirecard.channel.backoffice.dto.models.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import za.co.wirecard.channel.backoffice.entities.CardTypeEntity;
import za.co.wirecard.channel.backoffice.entities.CardTypeGroupEntity;
import za.co.wirecard.channel.backoffice.models.CardTypeEntityGroupContainer;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CurrentCardTypeGroups {

    List<CardTypeGroupEntity> cardTypeGroupEntityList;
    List<CardTypeEntity> availableCardTypes;
    List<CardTypeEntityGroupContainer> usedCardTypes;

}
