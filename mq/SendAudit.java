package za.co.wirecard.channel.backoffice.mq;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;
import za.co.wirecard.channel.backoffice.config.Utils;
import za.co.wirecard.channel.backoffice.dto.models.requests.CreateBillingRunRequest;

@Component
public class SendAudit {

    private static final Logger logger = LogManager.getLogger(SendAudit.class);

    private final RabbitTemplate rabbitTemplate;

    public SendAudit(RabbitTemplate template) {
        this.rabbitTemplate = template;
    }

    public void createMockBillingRun(CreateBillingRunRequest createBillingRunRequest) {
        logger.info("Creating Billing Run : "+ createBillingRunRequest.getName());
        rabbitTemplate.convertAndSend(Utils.BILLING_RUN_QUEUE_NAME, createBillingRunRequest);
    }

}
