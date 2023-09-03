//package za.co.wirecard.channel.backoffice.mq;
//
//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;
//import org.springframework.amqp.rabbit.annotation.RabbitListener;
//import org.springframework.stereotype.Component;
//import za.co.wirecard.channel.backoffice.config.Utils;
//import za.co.wirecard.channel.backoffice.dto.models.requests.CreateBillingRunRequest;
//import za.co.wirecard.channel.backoffice.repositories.BillingRunRepository;
//
//@Component
//public class BillingRunProcessor {
//    private static final Logger logger = LogManager.getLogger(BillingRunProcessor.class);
//
//    private final BillingRunRepository billingRunRepository;
//
//    public BillingRunProcessor(BillingRunRepository billingRunRepository) {
//        this.billingRunRepository = billingRunRepository;
//    }
//
//    @RabbitListener(queues = Utils.BILLING_RUN_QUEUE_NAME)
//    public void mockBillingRun(CreateBillingRunRequest createBillingRunRequest) {
//        logger.info("Reading create bill run message: " +  createBillingRunRequest.getName());
//
//    }
//
//}
