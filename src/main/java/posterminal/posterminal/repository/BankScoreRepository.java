package posterminal.posterminal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import posterminal.posterminal.model.BankScore;

public interface BankScoreRepository extends JpaRepository<BankScore, Long> {
    BankScore findByNumber(String number);
}

