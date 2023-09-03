package za.co.wirecard.channel.backoffice.dto.models.responses;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import za.co.wirecard.channel.backoffice.entities.BillingRunEntity;
import za.co.wirecard.channel.backoffice.models.BackOfficeUser;
import za.co.wirecard.channel.backoffice.models.BillingRunStatus;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetBillingRunHistoryResponse {
    private Long id;
    private String name;
    private BackOfficeUser requestedBy;
    private BackOfficeUser approvedBy;
    @JsonFormat(pattern="yyyy-MM-dd kk:mm:ss")
    private Date dateRequested;
    @JsonFormat(pattern="yyyy-MM-dd kk:mm:ss")
    private Date dateApproved;
    @JsonFormat(pattern="yyyy-MM-dd kk:mm:ss")
    private Date fromDate;
    @JsonFormat(pattern="yyyy-MM-dd kk:mm:ss")
    private Date toDate;
    private BillingRunStatus billingRunStatus;

    public GetBillingRunHistoryResponse(BillingRunEntity billingRunEntity) {
        Calendar c = Calendar.getInstance();
        this.setId(billingRunEntity.getId());
        this.setName(billingRunEntity.getName());
        this.setRequestedBy(new BackOfficeUser(billingRunEntity.getBackOfficeUserByRequestedBy()));
        if (billingRunEntity.getBackOfficeUserByApprovedBy() != null) {
            this.setApprovedBy(new BackOfficeUser(billingRunEntity.getBackOfficeUserByApprovedBy()));
        }
        c.setTime(billingRunEntity.getDateRequested());
        c.set(Calendar.HOUR_OF_DAY, 2);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        Date requestDate = c.getTime();
        this.setDateRequested(requestDate);
        if (billingRunEntity.getDateApproved() != null) {
            this.setDateApproved(billingRunEntity.getDateApproved());
        }
        c.setTime(billingRunEntity.getFromDate());
        c.set(Calendar.HOUR_OF_DAY, 2);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        Date startDate = c.getTime();
        this.setFromDate(startDate);
        c.setTime(billingRunEntity.getToDate());
        c.set(Calendar.HOUR_OF_DAY, 2);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        Date endDate = c.getTime();
        this.setToDate(endDate);
        this.setBillingRunStatus(new BillingRunStatus(billingRunEntity.getBillingRunStatusByBillingRunStatusId()));
    }
}
