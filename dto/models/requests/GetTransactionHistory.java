package za.co.wirecard.channel.backoffice.dto.models.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetTransactionHistory {
    private List<Long> merchantIds;
    private List<Long> applicationIds;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private Date startDate;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private Date endDate;
    private List<String> paymentTypeCodes;
    private List<String> transactionStatusCodes;
    private List<String> ecis;
    private String acquiringBankCode;
    private String issuingBank;
    private String errorMessage;
    private String stringCriteria;
    private String stringSearch;
    private String merchantUid;
    private String applicationUid;
    //Added cardtype
    private List<String> cardTypes;
}
