package za.co.wirecard.channel.backoffice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import za.co.wirecard.channel.backoffice.entities.CardConfigStatusEntity;

import java.util.Optional;

public interface CardConfigStatusRepository extends JpaRepository<CardConfigStatusEntity, Long> {
    CardConfigStatusEntity findByStatus(String status);
    CardConfigStatusEntity findCardConfigStatusEntityById(long statusId);
}
