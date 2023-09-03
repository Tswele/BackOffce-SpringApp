package za.co.wirecard.channel.backoffice.config;

import lombok.*;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.net.URL;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Configuration
@ConfigurationProperties(prefix = "application.external.vault.endpoint")
public class VaultConfig {
  private String otpSecretKey;
  private URL encrypt;
  private URL decrypt;
}
