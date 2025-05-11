package reportsAndPostTerminals.report_service.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reportsAndPostTerminals.posterminal.model.entity.Transaction;
import reportsAndPostTerminals.posterminal.repository.TransactionRepository;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Service
public class ReportsService {
    private final TransactionRepository transactionRepository;

    public List<Transaction> transactions(){
        return transactionRepository.getAll();
    }

    public List<Object[]> calculateAmountForLastMonth(){
        LocalDateTime startDate = LocalDateTime.now().minusMonths(1);
        return transactionRepository.getTotalAmountByCurrency(startDate);
    }

    public List<Object[]> calculateAmountForLastWeek(){
        LocalDateTime startDate = LocalDateTime.now().minusWeeks(1);
        return transactionRepository.getTotalAmountByCurrency(startDate);
    }

    public List<Object[]> calculateAmountForLastDay(){
        LocalDateTime startDate = LocalDateTime.now().minusDays(1);
        return transactionRepository.getTotalAmountByCurrency(startDate);
    }


}
