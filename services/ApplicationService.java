package za.co.wirecard.channel.backoffice.services;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import za.co.wirecard.channel.backoffice.dto.models.requests.CreateDefaultApplicationWithConfiguration;
import za.co.wirecard.channel.backoffice.dto.models.requests.PlatformCreateApplicationConfigurationRequest;
import za.co.wirecard.channel.backoffice.dto.models.requests.PlatformCreateApplicationRequest;
import za.co.wirecard.channel.backoffice.dto.models.responses.GetApplicationsByMerchantIdResponse;
import za.co.wirecard.channel.backoffice.dto.models.responses.PlatformCreateApplicationResponse;
import za.co.wirecard.channel.backoffice.entities.ApplicationEntity;
import za.co.wirecard.channel.backoffice.exceptions.ApplicationException;
import za.co.wirecard.channel.backoffice.models.Application;
import za.co.wirecard.channel.backoffice.models.ConfigurationType;
import za.co.wirecard.channel.backoffice.models.SecurityType;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Service
public interface ApplicationService {

    PlatformCreateApplicationResponse createApplication(PlatformCreateApplicationRequest application);

    ResponseEntity<Application> getApplication(long applicationId);

    PlatformCreateApplicationResponse editApplication(PlatformCreateApplicationRequest application, long applicationId);

    void deleteApplication(long merchantId);

    List<SecurityType> getSecurityTypes() throws ApplicationException ;

    Page<Application> getApplicationsByMerchantId(int page, int limit, Specification specification) throws ApplicationException;

    List<ConfigurationType> getConfigurationTypes() throws ApplicationException;

    void createSecurityTypes();

    void createApplicationConfiguration(PlatformCreateApplicationConfigurationRequest applicationConfiguration) throws ApplicationException;

    ApplicationEntity createDefaultApplicationWithConfiguration(CreateDefaultApplicationWithConfiguration createDefaultApplicationWithConfiguration, long merchantId, HttpServletRequest servletRequest);
}
