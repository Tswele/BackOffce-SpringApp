package za.co.wirecard.channel.backoffice.repositories;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;
import za.co.wirecard.channel.backoffice.models.Token;
import za.co.wirecard.channel.backoffice.models.cache.TokenCache;

import javax.annotation.PostConstruct;

@Repository
@RequiredArgsConstructor
public class TokenRedisRepositoryImpl implements TokenRedisRepository {

    private static final String REDIS_ENTITY = "TOKEN";

    private final RedisTemplate<String, TokenCache> redisTemplate;

    private HashOperations<String, Integer, TokenCache> hashOperations;

    @PostConstruct
    private void init(){
        hashOperations = redisTemplate.opsForHash();
    }

    @Override
    public TokenCache findTokenById(int backOfficeUserId) {
        return hashOperations.get(REDIS_ENTITY, backOfficeUserId);
    }

    @Override
    public void deleteTokenById(int addressId) {
        hashOperations.delete(REDIS_ENTITY, addressId);
    }

    @Override
    public void updateTokenById(TokenCache tokenCache) {
        hashOperations.put(REDIS_ENTITY, tokenCache.getBackOfficeUserId(), tokenCache);
    }

    @Override
    public void saveToken(TokenCache tokenCache) {
        hashOperations.put(REDIS_ENTITY, tokenCache.getBackOfficeUserId(), tokenCache);
    }

}
