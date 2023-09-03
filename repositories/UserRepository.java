package za.co.wirecard.channel.backoffice.repositories;

import org.apache.logging.log4j.Logger;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;
import za.co.wirecard.channel.backoffice.dto.models.User;
import za.co.wirecard.channel.backoffice.models.cache.TokenCache;

import java.util.Map;

@Repository
public class UserRepository {

    private static final Logger logger = org.apache.logging.log4j.LogManager.getLogger(UserRepository.class);

    private final RedisTemplate<String, TokenCache> redisTemplate;

    public UserRepository(RedisTemplate<String, TokenCache> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void create(TokenCache user) {
        HashOperations hashOperations = redisTemplate.opsForHash();
        logger.info("SET USER | " + user.getToken().getFirstName() + " | " + user.getBackOfficeUserId());
        hashOperations.put("USER", user.getBackOfficeUserId(), user);
        logger.info("User with ID %s saved", user.getBackOfficeUserId());
    }

    public TokenCache get(long userId) {
        HashOperations hashOperations = redisTemplate.opsForHash();
        return (TokenCache) hashOperations.get("USER", userId);
    }

    public Map<String, TokenCache> getAll(){
        HashOperations hashOperations = redisTemplate.opsForHash();
        return hashOperations.entries("USER");
    }

    public void update(TokenCache user) {
        HashOperations hashOperations = redisTemplate.opsForHash();
        hashOperations.put("USER", user.getBackOfficeUserId(), user);
        logger.info(String.format("User with ID %s updated", user.getBackOfficeUserId()));
    }


    public void delete(long userId) {
        HashOperations hashOperations = redisTemplate.opsForHash();
        hashOperations.delete("USER", userId);
        logger.info(String.format("User with ID %s deleted", userId));
    }
}
