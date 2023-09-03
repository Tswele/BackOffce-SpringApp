package za.co.wirecard.channel.backoffice.entities;

import lombok.*;

import javax.persistence.*;

@Data
@Entity
@ToString
@Table(name = "card_type", schema = "dbo", catalog = "transaction_db")
public class CardTypeEntity {
    @Id@Column(name = "id")
    private long id;
    @Basic@Column(name = "card_name")
    private String cardName;
    @Basic@Column(name = "order")
    private Integer order;
    @Basic@Column(name = "image_name")
    private String imageName;
    @Basic@Column(name = "bank_recon_group")
    private Short bankReconGroup;
    @Basic@Column(name = "bypass_card_expiry_date")
    private boolean bypassCardExpiryDate;
    @Basic@Column(name = "code")
    private String code;

}
