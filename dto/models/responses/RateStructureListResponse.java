package za.co.wirecard.channel.backoffice.dto.models.responses;

import lombok.Data;
import za.co.wirecard.channel.backoffice.dto.models.RateStructure;

import java.util.ArrayList;

@Data
public class RateStructureListResponse {
    ArrayList<RateStructure> rateStructureList;
}
