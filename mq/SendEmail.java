package za.co.wirecard.channel.backoffice.mq;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;
import za.co.wirecard.channel.backoffice.config.Utils;
import za.co.wirecard.channel.backoffice.models.email.EmailNotification;

import java.util.Arrays;

@Component
public class SendEmail {

    private final RabbitTemplate rabbitTemplate;
    private static final Logger logger = LogManager.getLogger(SendEmail.class);

    public SendEmail(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void send(EmailNotification email) {
        logger.info("Sending email...");
        rabbitTemplate.convertAndSend(Utils.EMAIL_NOTIFICATION_QUEUE_NAME, email);
        System.out.println(" [x] Sent '" + email.getToAdress() + "'");
    }

}
