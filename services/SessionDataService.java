package za.co.wirecard.channel.backoffice.services;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import za.co.wirecard.channel.backoffice.dto.models.Session;
import za.co.wirecard.channel.backoffice.entities.SessionDataEntity;

import java.util.Date;
import java.util.List;

@Service
public interface SessionDataService {

    Page<Session> getSessionList(int page, int limit, String merchantUid, String applicationUid, Date startDate, Date endDate);
    List<SessionDataEntity> getSessionDataForSession(String sessionToken);
}
