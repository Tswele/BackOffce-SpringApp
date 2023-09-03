package za.co.wirecard.channel.backoffice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import za.co.wirecard.channel.backoffice.entities.BankEntity;

public interface BankRepository extends JpaRepository<BankEntity, Long> {
}
