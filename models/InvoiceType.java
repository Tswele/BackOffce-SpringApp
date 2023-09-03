package za.co.wirecard.channel.backoffice.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import za.co.wirecard.channel.backoffice.entities.InvoiceTypeEntity;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InvoiceType {
    private String code;
    private String description;
    private String status;

    public InvoiceType(InvoiceTypeEntity invoiceTypeByInvoiceTypeId) {
        this.setCode(invoiceTypeByInvoiceTypeId.getCode());
        this.setDescription(invoiceTypeByInvoiceTypeId.getDescription());
        this.setStatus(invoiceTypeByInvoiceTypeId.getStatus());
    }
}
