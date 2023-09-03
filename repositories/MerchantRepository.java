package za.co.wirecard.channel.backoffice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import za.co.wirecard.channel.backoffice.entities.MerchantEntity;

import java.util.List;
import java.util.Optional;

@Repository
public interface MerchantRepository extends JpaRepository<MerchantEntity, Long>, JpaSpecificationExecutor<MerchantEntity> {
    public Optional<MerchantEntity> findByMerchantUid(String merchantUid);
    List<MerchantEntity> findAllByOrderByCompanyNameAsc();
    Optional<MerchantEntity> findById(long id);
}
