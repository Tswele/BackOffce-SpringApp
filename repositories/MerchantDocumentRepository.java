package za.co.wirecard.channel.backoffice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import za.co.wirecard.channel.backoffice.entities.MerchantDocumentEntity;
import za.co.wirecard.channel.backoffice.entities.MerchantEntity;

import java.util.List;
import java.util.Optional;

public interface MerchantDocumentRepository extends JpaRepository<MerchantDocumentEntity, Long> {
    Optional<List<MerchantDocumentEntity>> findAllByMerchantByMerchantId(MerchantEntity merchantEntity);
    Optional<MerchantDocumentEntity> findByLink(String link);
}
