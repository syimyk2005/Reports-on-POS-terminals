package posterminal.posterminal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import posterminal.posterminal.model.ABankScore;

@Repository
public interface ABankScoreRepository extends JpaRepository<ABankScore, Long> {
    ABankScore findByNumber(String number);
}