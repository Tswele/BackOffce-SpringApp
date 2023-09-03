package za.co.wirecard.channel.backoffice.services;


import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import za.co.wirecard.channel.backoffice.dto.models.SwitchLog;

import java.util.List;

@Service
public interface SwitchLogService {

    Page<SwitchLog> getSwitchLogs(int page, int limit, Long transactionLegId, String startDate, String endDate);

    List<SwitchLog> getSwitchLogByTransactionLegId(long transactionLegId);
}
