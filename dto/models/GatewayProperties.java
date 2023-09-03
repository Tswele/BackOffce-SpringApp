package za.co.wirecard.channel.backoffice.dto.models;

import lombok.Data;

@Data
public class GatewayProperties {
    private AbsaConfiguration absaConfiguration;
    private BwConfiguration bwConfiguration;
    private FnbConfiguration fnbConfiguration;
    private IveriConfiguration iveriConfiguration;
    private PlanetConfiguration planetConfiguration;
    private StandardBankConfiguration standardBankConfiguration;
    private BankWindhoekConfiguration bankWindhoekConfiguration;
    private VacpConfiguration vacpConfiguration;
}
