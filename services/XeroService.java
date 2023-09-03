package za.co.wirecard.channel.backoffice.services;

import org.springframework.stereotype.Service;
import za.co.wirecard.channel.backoffice.dto.models.responses.XeroAuthorizationResponse;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Service
public interface XeroService {
    XeroAuthorizationResponse xeroAuthorization() throws IOException;
}
