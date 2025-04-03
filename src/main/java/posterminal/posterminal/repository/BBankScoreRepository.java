package posterminal.posterminal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import posterminal.posterminal.model.BBankScore;

@Repository
public interface BBankScoreRepository extends JpaRepository<BBankScore, Long> {
    BBankScore findByNumber(String number);
}