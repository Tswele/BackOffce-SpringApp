package za.co.wirecard.channel.backoffice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import za.co.wirecard.channel.backoffice.entities.CardTypeEntity;

import java.util.List;

public interface CardTypeRepository extends JpaRepository<CardTypeEntity, Long> {
   @Query("SELECT ct.code FROM CardTypeEntity ct")
    List<String> findAllCardType();
    CardTypeEntity findByCode(String code);
    CardTypeEntity findById(long Id);
}
