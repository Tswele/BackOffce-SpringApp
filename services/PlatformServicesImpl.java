package za.co.wirecard.channel.backoffice.services;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import za.co.wirecard.channel.backoffice.config.Utils;
import za.co.wirecard.channel.backoffice.config.VaultConfig;
import za.co.wirecard.channel.backoffice.dto.models.VaultRequest;

@Service
public class PlatformServicesImpl implements PlatformServices {

    private final VaultConfig vaultConfig;
    private final RestTemplate restTemplate;

    public PlatformServicesImpl(
            VaultConfig vaultConfig,
            RestTemplateBuilder builder
    ) {
        this.vaultConfig = vaultConfig;
        this.restTemplate = builder.build();
    }

    @Override
    public String decrypt(String key, String value) {
        VaultRequest vaultRequest = new VaultRequest(key, value);
        HttpEntity<VaultRequest> request = new HttpEntity<>(vaultRequest, Utils.getPlatformHeaders());
        return restTemplate.postForObject(vaultConfig.getDecrypt().toString(), request, String.class);
    }

    @Override
    public String encrypt(String key, String value) {
        VaultRequest vaultRequest = new VaultRequest(key, value);
        HttpEntity<VaultRequest> newRequest = new HttpEntity<>(vaultRequest, Utils.getPlatformHeaders());
        return restTemplate.postForObject(vaultConfig.getEncrypt().toString(), newRequest, String.class);
    }

}
