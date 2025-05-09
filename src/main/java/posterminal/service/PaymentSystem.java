package posterminal.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import posterminal.exception.customExceptions.ScoreNotFoundException;
import posterminal.model.entity.ABankScore;
import posterminal.model.entity.BBankScore;
import posterminal.model.dto.PaymentDto;
import posterminal.repository.ABankScoreRepository;
import posterminal.repository.BBankScoreRepository;

@RequiredArgsConstructor
@Service
public class PaymentSystem {

    private final ABankScoreRepository aBankRepository;
    private final BBankScoreRepository bBankRepository;

    public boolean checkScoreBalanceA(PaymentDto bankDto) {
        ABankScore bankScore = aBankRepository.findByNumberForUpdate(bankDto.getNumber()).orElseThrow(() -> new ScoreNotFoundException("Bank a score not found in checking balance!"));
        return bankScore.getBalance() >= bankDto.getAmount();
    }

    public boolean checkScoreBalanceB(PaymentDto bankDto) {
        BBankScore bankScore = bBankRepository.findByNumberForUpdate(bankDto.getNumber()).orElseThrow(() -> new ScoreNotFoundException("Bank b score not found in checking balance!"));
        return bankScore.getBalance() >= bankDto.getAmount();
    }

}