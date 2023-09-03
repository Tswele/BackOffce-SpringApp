package za.co.wirecard.channel.backoffice.services;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import za.co.wirecard.channel.backoffice.config.OAuthClientConfig;
import za.co.wirecard.channel.backoffice.config.RedisProperties;
import za.co.wirecard.channel.backoffice.entities.BackOfficeUserEntity;
import za.co.wirecard.channel.backoffice.exceptions.GenericException;
import za.co.wirecard.channel.backoffice.models.cache.TokenCache;
import za.co.wirecard.channel.backoffice.repositories.BackOfficeUserRepository;
import za.co.wirecard.channel.backoffice.repositories.TokenRedisRepository;
import za.co.wirecard.channel.backoffice.repositories.UserRepository;

import java.util.Optional;

import static java.lang.String.format;
import static java.util.Objects.nonNull;

@Service
public class TokenService {

    private static final Logger logger = LogManager.getLogger(TokenService.class);

    private final RedisProperties redisProperties;
    private final OAuthClientConfig oAuthClientConfig;

    private final RestTemplate restTemplate;
    private final TokenRedisRepository tokenRedisRepository;
    private final BackOfficeUserRepository backOfficeUserRepository;

    public TokenService(RedisProperties redisProperties, OAuthClientConfig oAuthClientConfig, RestTemplate restTemplate, TokenRedisRepository tokenRedisRepository, BackOfficeUserRepository backOfficeUserRepository) {
        this.redisProperties = redisProperties;
        this.oAuthClientConfig = oAuthClientConfig;
        this.restTemplate = restTemplate;
        this.tokenRedisRepository = tokenRedisRepository;

        this.backOfficeUserRepository = backOfficeUserRepository;
    }

    public Optional<TokenCache> getTokenById(int backOfficeUserId){

        TokenCache cachedToken = getCachedToken(backOfficeUserId);
        if(nonNull(cachedToken)){
            return Optional.of(cachedToken);
        }

        String redisUrl = redisProperties.getHost() + ":" + redisProperties.getPort() + "/token/{id}";
        ResponseEntity<TokenCache> token = restTemplate.exchange(redisUrl, HttpMethod.GET, null, TokenCache.class, backOfficeUserId);

        if(nonNull(token.getBody())){
            cacheToken(token.getBody());
        }

        return Optional.ofNullable(token.getBody());
    }

    private TokenCache getCachedToken(int backOfficeUserId){
        TokenCache address = null;
        address = tokenRedisRepository.findTokenById(backOfficeUserId);
        if (address == null) {
            throw new GenericException(format("No address was yet cached with the given id : %d!", backOfficeUserId), HttpStatus.NOT_FOUND, format("No address was yet cached with the given id : %d!", backOfficeUserId));
        }
        return address;
    }

    public void cacheToken(TokenCache tokenCache){
            logger.info("REDIS_LOG_1");
            UserRepository userRepository = new UserRepository(redisTemplate());
            logger.info("REDIS_LOG_2");
            BackOfficeUserEntity backOfficeUserEntity = backOfficeUserRepository.getOne((long) tokenCache.getBackOfficeUserId());
            logger.info("REDIS_LOG_3");
            userRepository.create(tokenCache);
            logger.info("REDIS_LOG_4");
            TokenCache user = userRepository.get(tokenCache.getBackOfficeUserId());
            logger.info("REDIS_LOG_5");
            userRepository.update(user);
            logger.info("REDIS_LOG_6");
            userRepository.delete(user.getBackOfficeUserId());
            logger.info("REDIS_LOG_7");
            tokenRedisRepository.saveToken(tokenCache);
            logger.info("REDIS_LOG_8");
    }

    public RedisTemplate<String, TokenCache> redisTemplate() {
        RedisTemplate<String, TokenCache> redisTemplate = new RedisTemplate<String, TokenCache>();
        redisTemplate.setConnectionFactory(oAuthClientConfig.redisConnectionFactory());
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }

}
