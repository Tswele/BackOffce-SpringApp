package za.co.wirecard.channel.backoffice.services;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import za.co.wirecard.channel.backoffice.dto.models.BatchTransaction;
import za.co.wirecard.channel.backoffice.dto.models.CardBatchRecord;
import za.co.wirecard.channel.backoffice.entities.BatchTransactionEntity;
import za.co.wirecard.channel.backoffice.entities.CardBatchRecordEntity;
import za.co.wirecard.channel.backoffice.repositories.ApplicationRepository;
import za.co.wirecard.channel.backoffice.repositories.BatchTransactionRepository;
import za.co.wirecard.channel.backoffice.repositories.CardBatchRecordRepository;
import za.co.wirecard.channel.backoffice.repositories.MerchantRepository;
import za.co.wirecard.common.exceptions.NotFoundException;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static za.co.wirecard.channel.backoffice.builder.BatchSpecifications .*;

@Service
public class BatchService {

    private static final Logger logger = LogManager.getLogger(BatchService.class);
    private final RabbitTemplate rabbitTemplate;

    private final MerchantRepository merchantRepository;
    private final ApplicationRepository applicationRepository;
    private final BatchTransactionRepository batchTransactionRepository;
    private final CardBatchRecordRepository cardBatchRecordRepository;

    public BatchService(MerchantRepository merchantRepository,
                        ApplicationRepository applicationRepository,
                        BatchTransactionRepository batchTransactionRepository,
                        CardBatchRecordRepository cardBatchRecordRepository, RabbitTemplate template) {
        this.merchantRepository = merchantRepository;
        this.applicationRepository = applicationRepository;
        this.batchTransactionRepository = batchTransactionRepository;
        this.cardBatchRecordRepository = cardBatchRecordRepository;
        this.rabbitTemplate = template;
        rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
    }

    public Page<BatchTransaction> getBatchTransactions(int page, int limit, String merchantUid, String applicationUid, Date startDate, Date endDate) {
//        MerchantEntity merchantEntity = merchantRepository.findByMerchantUid(merchantUid).orElseThrow(() -> new MerchantNotFoundException(merchantUid));
//        ApplicationEntity applicationEntity = applicationRepository.findByApplicationUid(applicationUid).orElseThrow(() -> new MerchantNotFoundException(applicationUid));
        Specification<BatchTransactionEntity> specification = Specification
                .where(merchantUid == null ? null : merchantUidEquals(merchantUid))
                .and(applicationUid == null ? null : applicationUidEquals(applicationUid))
                .and(startDate == null ? null : dateGreaterThan(startDate))
                .and(endDate == null ? null : dateLessThan(endDate));
        Pageable pageable = PageRequest.of(page, limit, Sort.by(Sort.Order.desc("date")));
        Page<BatchTransactionEntity> batches = batchTransactionRepository.findAll(specification, pageable);
        return batches.map(BatchTransaction::new);
    }

    public Page<CardBatchRecord> getCardBatchRecordsByBatchId(int page, int limit, String batchUid) {
        BatchTransactionEntity batchTransactionEntity = batchTransactionRepository.findBatchTransactionEntityByBatchUid(batchUid);
        if (batchTransactionEntity == null) {
            throw new NotFoundException("Batch Transaction not found");
        }
        Pageable pageable = PageRequest.of(page, limit, Sort.by(Sort.Order.asc("id")));
        Page<CardBatchRecordEntity> records = cardBatchRecordRepository.findCardBatchRecordEntitiesByBatchTransactionByBatchId(batchTransactionEntity, pageable);
        return records.map((cardBatchRecordEntity) -> {
            return new CardBatchRecord(cardBatchRecordEntity);
        });
    }

    public List<CardBatchRecord> getCardBatchRecordsByBatchIdReport(String batchUid) {
        BatchTransactionEntity batchTransactionEntity = batchTransactionRepository.findBatchTransactionEntityByBatchUid(batchUid);
        if (batchTransactionEntity == null) {
            throw new NotFoundException("Batch Transaction not found");
        }
        List<CardBatchRecordEntity> cardBatchRecordEntities = cardBatchRecordRepository.findCardBatchRecordEntitiesByBatchTransactionByBatchId(batchTransactionEntity);
        List<CardBatchRecord> cardBatchRecords = new ArrayList<>();
        for (CardBatchRecordEntity cardBatchRecordEntity: cardBatchRecordEntities) {
            cardBatchRecords.add(new CardBatchRecord(cardBatchRecordEntity));
        }
        return cardBatchRecords;
    }

    public void replayBatch(String batchUid) {
        rabbitTemplate.convertAndSend("batchCardTransactionProcessor", batchUid);
    }
}
