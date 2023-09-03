package za.co.wirecard.channel.backoffice.dto.models.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import za.co.wirecard.channel.backoffice.entities.InvoiceEntity;
import za.co.wirecard.channel.backoffice.models.Invoice;
import za.co.wirecard.channel.backoffice.models.Merchant;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetInvoiceHistoryResponse {
    private Invoice invoice;
    private Merchant merchant;

    public GetInvoiceHistoryResponse (InvoiceEntity invoiceEntity) {
        this.setInvoice(new Invoice(invoiceEntity));
        this.setMerchant(new Merchant(invoiceEntity.getMerchantByMerchantId()));
    }
}
