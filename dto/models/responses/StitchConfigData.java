package za.co.wirecard.channel.backoffice.dto.models.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import za.co.wirecard.channel.backoffice.entities.StitchEftBanksEntity;
import za.co.wirecard.channel.backoffice.models.Country;
import za.co.wirecard.channel.backoffice.models.Currency;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StitchConfigData {

    private List<StitchEftBanksEntity> banks;

}
