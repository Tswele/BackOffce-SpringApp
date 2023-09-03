package za.co.wirecard.channel.backoffice.dto.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlanetConfiguration {
    private String bankUsername;
    private String bankPassword;
    private String descriptorCompany;
    private String descriptorInfo;
    private String descriptorCountry;
    private String descriptorState;
    private String descriptorCity;
    private String threedsecureOrdUnitId;
    private String terminalId;
}
