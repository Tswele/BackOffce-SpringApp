package za.co.wirecard.channel.backoffice.repositories;

import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import za.co.wirecard.channel.backoffice.entities.BackOfficeUserEntity;
import za.co.wirecard.channel.backoffice.entities.BackOfficeUserPasswordHistoryEntity;

import java.util.List;

public interface BackOfficeUserPasswordHistoryRepository extends JpaRepository<BackOfficeUserPasswordHistoryEntity, Long>, PagingAndSortingRepository<BackOfficeUserPasswordHistoryEntity, Long> {
    // @Query("SELECT b FROM BackOfficeUserPasswordHistoryEntity b WHERE b.backOfficeUserByBackOfficeUserId = :backOfficeUserEntity")
    // @Param("backOfficeUserEntity")
    Page<BackOfficeUserPasswordHistoryEntity> findAllByBackOfficeUserByBackOfficeUserId(BackOfficeUserEntity backOfficeUserEntity, Pageable pageable);
    BackOfficeUserPasswordHistoryEntity findDistinctFirstByBackOfficeUserByBackOfficeUserIdOrderByIdDesc(BackOfficeUserEntity backOfficeUserEntity);
}
