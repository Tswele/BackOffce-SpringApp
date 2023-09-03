package za.co.wirecard.channel.backoffice.services;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import za.co.wirecard.channel.backoffice.dto.models.Session;
import za.co.wirecard.channel.backoffice.entities.MerchantEntity;
import za.co.wirecard.channel.backoffice.entities.SessionDataEntity;
import za.co.wirecard.channel.backoffice.entities.SessionEntity;
import za.co.wirecard.channel.backoffice.exceptions.GenericException;
import za.co.wirecard.channel.backoffice.repositories.MerchantRepository;
import za.co.wirecard.channel.backoffice.repositories.SessionDataRepository;
import za.co.wirecard.channel.backoffice.repositories.SessionRepository;
import za.co.wirecard.common.exceptions.MerchantNotFoundException;

import java.util.Date;
import java.util.List;

import static za.co.wirecard.channel.backoffice.builder.SessionSpecifications.*;

@Service
public class SessionDataServiceImpl implements SessionDataService {

    private static final Logger logger = LogManager.getLogger(SessionDataServiceImpl.class);

    private final MerchantRepository merchantRepository;
    private final SessionDataRepository sessionDataRepository;
    private final SessionRepository sessionRepository;

    public SessionDataServiceImpl(MerchantRepository merchantRepository,
                                  SessionRepository sessionRepository,
                                  SessionDataRepository sessionDataRepository) {
        this.merchantRepository = merchantRepository;
        this.sessionRepository = sessionRepository;
        this.sessionDataRepository = sessionDataRepository;
    }

    @Override
    public Page<Session> getSessionList(int page, int limit, String merchantUid, String applicationUid, Date startDate, Date endDate) {
        try {
            if (merchantUid != null) {
                MerchantEntity merchantEntity = merchantRepository.findByMerchantUid(merchantUid).orElseThrow(() -> new MerchantNotFoundException(merchantUid));
            }
        } catch (Exception e) {
            throw new MerchantNotFoundException(merchantUid);
        }

        Specification<SessionEntity> specification = Specification
                .where(merchantUid == null ? null : merchantUidEquals(merchantUid))
                .and(applicationUid == null ? null : applicationUidEquals(applicationUid))
                .and(startDate == null ? null : dateGreaterThan(startDate))
                .and(endDate == null ? null : dateLessThan(endDate));
        Pageable pageable = PageRequest.of(page, limit, Sort.by(Sort.Order.desc("dateLogged")));
        Page<SessionEntity> sessions = sessionRepository.findAll(specification, pageable);

        return sessions.map((sessionEntity) -> {
            return new Session(sessionEntity);
        });
    }

    @Override
    public List<SessionDataEntity> getSessionDataForSession(String sessionToken) {
        try {
            return sessionDataRepository.findAllBySessionBySessionId(sessionRepository.findBySessionToken(sessionToken));
        } catch(Exception e) {
            throw new GenericException("Error retrieving session data for session token: " + sessionToken + " ||| " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, e.getLocalizedMessage());
        }
    }

}
