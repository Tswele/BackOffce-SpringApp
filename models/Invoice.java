package za.co.wirecard.channel.backoffice.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import za.co.wirecard.channel.backoffice.entities.InvoiceEntity;

import java.sql.Timestamp;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Invoice {

    private Long id;
    private String invoiceNumber;
    @JsonFormat(pattern="yyyy-MM-dd kk:mm:ss")
    private Date dateCreated;
    @JsonFormat(pattern="yyyy-MM-dd kk:mm:ss")
    private Date fromDate;
    @JsonFormat(pattern="yyyy-MM-dd kk:mm:ss")
    private Date toDate;
    private InvoiceType invoiceType;
    private String calculationString;

    public Invoice(InvoiceEntity invoiceEntity) {
        this.setId(invoiceEntity.getId());
        this.setInvoiceNumber(invoiceEntity.getInvoiceNumber());
        this.setDateCreated(invoiceEntity.getDateCreated());
        this.setFromDate(invoiceEntity.getFromDate());
        this.setToDate(invoiceEntity.getToDate());
        this.setInvoiceType(new InvoiceType(invoiceEntity.getInvoiceTypeByInvoiceTypeId()));
        this.setCalculationString(invoiceEntity.getCalculationString());
    }
}
