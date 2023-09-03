package za.co.wirecard.channel.backoffice.services;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import za.co.wirecard.channel.backoffice.dto.models.requests.PlatformCreateInterfaceRequest;
import za.co.wirecard.channel.backoffice.dto.models.responses.GetIntefaceByInterfaceIdResponse;
import za.co.wirecard.channel.backoffice.dto.models.responses.GetInterfacesByMerchantIdResponse;
import za.co.wirecard.channel.backoffice.models.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Service
public interface InterfaceService {
    List<Interface> getInterfaces(HttpServletRequest servletRequest);

    GetIntefaceByInterfaceIdResponse getInterfaceByInterfaceId(long interfaceId);

    void createInterface(PlatformCreateInterfaceRequest interfaceRequest, HttpServletRequest servletRequest);

    void editInterfaceByInterfaceId(long interfaceId, PlatformCreateInterfaceRequest interfaceRequest);

    List<SecurityMethod> getSecurityMethods(HttpServletRequest servletRequest);

    List<TradingCurrency> getTradingCurrencies(HttpServletRequest servletRequest);

    List<Currency> getCurrencies(HttpServletRequest servletRequest);

    List<Gateway> getGateways(HttpServletRequest servletRequest);

    List<TdsMerchantType> getTdsMerchantTypes(HttpServletRequest servletRequest);

    Page<GetInterfacesByMerchantIdResponse> getInterfacesByMerchantId(int page, int limit, long merchantId, HttpServletRequest servletRequest);

    Page<GetInterfacesByMerchantIdResponse> getInterfacesByApplicationId(int page, int limit, long applicationId, HttpServletRequest servletRequest);
}
