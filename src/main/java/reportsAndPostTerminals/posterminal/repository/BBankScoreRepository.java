package reportsAndPostTerminals.posterminal.repository;

import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import reportsAndPostTerminals.posterminal.model.entity.BBankScore;
import java.util.Optional;

@Repository
public interface BBankScoreRepository extends JpaRepository<BBankScore, Long> {
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT b FROM BBankScore b WHERE b.number = :number")
    Optional<BBankScore> findByNumberForUpdate(@Param("number") String number);
}