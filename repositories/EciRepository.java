package za.co.wirecard.channel.backoffice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import za.co.wirecard.channel.backoffice.entities.EciEntity;

import java.util.List;

public interface EciRepository extends JpaRepository<EciEntity, Long> {
    @Query("SELECT e.eci FROM EciEntity e")
    List<String> findAllEci();
    EciEntity findByEci(String eci);
    EciEntity findById(long id);
}
