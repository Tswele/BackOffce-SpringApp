package za.co.wirecard.channel.backoffice.entities;

import lombok.*;

import javax.persistence.*;

@Data
@Entity
@Table(name = "zapper_client_update", schema = "dbo", catalog = "transaction_db")
public class ZapperClientUpdateEntity {
    @Id@Column(name = "id")
    private long id;
    @Basic@Column(name = "transaction_leg_id")
    private long transactionLegId;
    @Basic@Column(name = "status")
    private int status;
    @Basic@Column(name = "paid_amount")
    private long paidAmount;
    @Basic@Column(name = "zapper_id")
    private String zapperId;
    @Basic@Column(name = "reference")
    private String reference;

}
