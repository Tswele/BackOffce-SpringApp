package za.co.wirecard.channel.backoffice.repositories;

import org.springframework.data.repository.PagingAndSortingRepository;
import za.co.wirecard.channel.backoffice.entities.BackOfficeUserEntity;

public interface BackOfficeUserRepositoryPaging extends PagingAndSortingRepository<BackOfficeUserEntity, Long> {
}
