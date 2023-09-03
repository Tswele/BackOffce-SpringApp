package za.co.wirecard.channel.backoffice.dto.models.responses;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import za.co.wirecard.channel.backoffice.models.*;

import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetIntefaceByInterfaceIdResponse {

    @NotNull
    private long id;
    private String name;
    private String description;
    @JsonFormat(pattern="yyyy-MM-dd")
    private Timestamp lastModified;
    private String merchantMid;
    private Merchant merchant;
    private Gateway gateway;
    private PaymentType paymentType;
    private SecurityMethod securityMethod;
    private TradingCurrency tradingCurrency;
    private TdsMerchantType tdsMerchantType;
    private String threeDSecurePassword;
    private String merchantCategoryCode;
    private String terminalId;

}
