package za.co.wirecard.channel.backoffice.dto.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import za.co.wirecard.channel.backoffice.entities.*;
import za.co.wirecard.channel.backoffice.models.MerchantStatus;

import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
public class ApplicationPaymentTypeStatus {

    private long id;
//    private ApplicationEntity applicationByApplicationId;
//    private PaymentTypeEntity paymentTypeByPaymentTypeId;
//    private InterfaceEntity interfaceByInterfaceId;
    private long applicationId;
    private long paymentTypeId;
    private long interfaceId;
    private boolean isActive;
    private StatusEntity statusId;
    private Timestamp createdDate;
    private Timestamp activatedDate;
    private Timestamp cancelledDate;
    private Long lastModifiedBy;
    private Timestamp lastModified;

    public ApplicationPaymentTypeStatus(ApplicationPaymentTypeEntity applicationPaymentTypeEntity){
        this.id = applicationPaymentTypeEntity.getId();
//        this.applicationByApplicationId = applicationPaymentTypeEntity.getApplicationByApplicationId();
//        this.paymentTypeByPaymentTypeId = applicationPaymentTypeEntity.getPaymentTypeByPaymentTypeId();
//        this.interfaceByInterfaceId = applicationPaymentTypeEntity.getInterfaceByInterfaceId();
        this.applicationId = applicationPaymentTypeEntity.getApplicationId();
        this.paymentTypeId = applicationPaymentTypeEntity.getPaymentTypeId();
        this.interfaceId = applicationPaymentTypeEntity.getInterfaceId();
        this.isActive = applicationPaymentTypeEntity.isActive();
        this.statusId = applicationPaymentTypeEntity.getStatusByStatusId();
        this.createdDate = applicationPaymentTypeEntity.getCreatedDate();
        this.activatedDate = applicationPaymentTypeEntity.getActivatedDate();
        this.cancelledDate = applicationPaymentTypeEntity.getCancelledDate();
        this.lastModifiedBy = applicationPaymentTypeEntity.getLastModifiedBy();
        this.lastModified = applicationPaymentTypeEntity.getLastModified();
    }

}
