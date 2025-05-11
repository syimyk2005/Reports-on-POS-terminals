package reportsAndPostTerminals.posterminal.repository;

import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import reportsAndPostTerminals.posterminal.model.entity.ABankScore;

import java.util.Optional;

@Repository
public interface ABankScoreRepository extends JpaRepository<ABankScore, Long> {
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT a FROM ABankScore a WHERE a.number = :number")
    Optional<ABankScore> findByNumberForUpdate(@Param("number") String number);
}