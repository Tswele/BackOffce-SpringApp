package za.co.wirecard.channel.backoffice.services;

import org.springframework.http.ResponseEntity;
import za.co.wirecard.channel.backoffice.dto.models.requests.CreateApplicationPaymentLinkSettingRequest;
import za.co.wirecard.channel.backoffice.dto.models.responses.GetApplicationPaymentLinkSettingResponse;

import javax.servlet.http.HttpServletRequest;

public interface PaymentLinkService {
    void createApplicationPaymentLinkSetting(CreateApplicationPaymentLinkSettingRequest createApplicationPaymentLinkSettingRequest, HttpServletRequest servletRequest);
    ResponseEntity<GetApplicationPaymentLinkSettingResponse> viewApplicationPaymentLinkSetting(String merchantUid, String applicationUid, HttpServletRequest servletRequest);
}
