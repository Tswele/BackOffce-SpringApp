package za.co.wirecard.channel.backoffice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import za.co.wirecard.channel.backoffice.entities.CountryEntity;

public interface CountryRepository extends JpaRepository<CountryEntity, Long> {
    CountryEntity findByCode(String code);
}
