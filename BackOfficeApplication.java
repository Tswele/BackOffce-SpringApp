package za.co.wirecard.channel.backoffice;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import java.io.FileNotFoundException;
import java.security.SecureRandom;

@SpringBootApplication
public class BackOfficeApplication {

    public static void main(String[] args) throws FileNotFoundException {
        SpringApplication.run(BackOfficeApplication.class, args);
    }

}
