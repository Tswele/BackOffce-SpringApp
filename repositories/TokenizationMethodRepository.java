package za.co.wirecard.channel.backoffice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import za.co.wirecard.channel.backoffice.entities.TokenizationMethodEntity;

public interface TokenizationMethodRepository extends JpaRepository<TokenizationMethodEntity, Long> {
    TokenizationMethodEntity findByCode(String code);
    TokenizationMethodEntity findById(long id);
}
