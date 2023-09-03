package za.co.wirecard.channel.backoffice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import za.co.wirecard.channel.backoffice.entities.ApplicationEntity;
import za.co.wirecard.channel.backoffice.entities.MerchantEntity;

import java.util.List;
import java.util.Optional;

public interface ApplicationRepository extends JpaRepository<ApplicationEntity, Long>, JpaSpecificationExecutor<ApplicationEntity> {
    List<ApplicationEntity> findAllByMerchantId(long merchantId);
    List<ApplicationEntity> findAllByMerchantByMerchantIdIn(List<MerchantEntity> merchantId);
    Optional<ApplicationEntity> findByApplicationUid(String applicationUid);
    ApplicationEntity findByApplicationUidEquals(String applicationUid);
//    ApplicationEntity findById(long id);
}
