package za.co.wirecard.channel.backoffice.repositories;

import za.co.wirecard.channel.backoffice.models.Token;
import za.co.wirecard.channel.backoffice.models.cache.TokenCache;

public interface TokenRedisRepository {
    TokenCache findTokenById(int backOfficeUserId);
    void deleteTokenById(int backOfficeUserId);
    void updateTokenById(TokenCache tokenCache);
    void saveToken(TokenCache tokenCache);
}
