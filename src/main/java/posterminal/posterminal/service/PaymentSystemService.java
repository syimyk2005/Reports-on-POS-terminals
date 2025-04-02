package posterminal.posterminal.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import posterminal.posterminal.model.BankScore;
import posterminal.posterminal.repository.BankScoreRepository;

@RequiredArgsConstructor
@Service
public class PaymentSystemService {

    private final BankScoreRepository bankScoreRepository;

    public boolean checkScoreBalance(Long id) {
        BankScore bankScore = bankScoreRepository.findById(id).orElseThrow(() -> new RuntimeException("Score not found!"));
        return bankScore.getBalance() <= 0;
    }

}
