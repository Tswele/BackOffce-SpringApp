package za.co.wirecard.channel.backoffice.dto.models;


import lombok.*;
import za.co.wirecard.channel.backoffice.entities.BackOfficeUserEntity;
import za.co.wirecard.channel.backoffice.models.*;
import za.co.wirecard.channel.backoffice.models.RateStructure;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class OnboardingSecondStep {
    private List<RateStructure> rateStructureList;
    private List<BankAccountType> bankAccountTypeList;
//    private List<BankBranchCode> bankBranchCodeList;
    private List<Bank> bankList;
    private List<PaymentType> paymentTypeList;
    private List<BackOfficeUserEntity> creditControllers;
}
