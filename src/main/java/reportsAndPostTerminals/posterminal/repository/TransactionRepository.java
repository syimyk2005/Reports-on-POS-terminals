package reportsAndPostTerminals.posterminal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import reportsAndPostTerminals.posterminal.model.entity.Transaction;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    @Query("SELECT t FROM Transaction t")
    List<Transaction> getAll();

    @Query("SELECT t.curr, SUM(t.amount) FROM Transaction t WHERE t.operationDateTime >= :startDate GROUP BY t.curr")
    List<Object[]> getTotalAmountByCurrency(@Param("startDate") LocalDateTime startDate);

}
