package za.co.wirecard.channel.backoffice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import za.co.wirecard.channel.backoffice.entities.CurrencyEntity;

public interface CurrencyRepository extends JpaRepository<CurrencyEntity, Long> {
    CurrencyEntity findByCode(String code);
}
