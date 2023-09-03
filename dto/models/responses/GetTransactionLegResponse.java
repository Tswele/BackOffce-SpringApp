package za.co.wirecard.channel.backoffice.dto.models.responses;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class GetTransactionLegResponse {
    private long id;
    private Timestamp dateLogged;
    private BigDecimal transactionValue;
    private long transactionID;
    private String transactionActionName;
}
