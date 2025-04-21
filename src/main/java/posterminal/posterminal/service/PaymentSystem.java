package posterminal.posterminal.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import posterminal.posterminal.exception.customExceptions.ScoreNotFoundException;
import posterminal.posterminal.model.ABankScore;
import posterminal.posterminal.model.BBankScore;
import posterminal.posterminal.model.BankDto;
import posterminal.posterminal.repository.ABankScoreRepository;
import posterminal.posterminal.repository.BBankScoreRepository;

@RequiredArgsConstructor
@Service
public class PaymentSystem {

    private final ABankScoreRepository aBankRepository;
    private final BBankScoreRepository bBankRepository;

    public boolean checkScoreBalanceA(BankDto bankDto) {
        ABankScore bankScore = aBankRepository.findByNumberForUpdate(bankDto.getNumber()).orElseThrow(() -> new ScoreNotFoundException("Bank a score not found in checking balance!"));
        return bankScore.getBalance() >= bankDto.getAmount();
    }

    public boolean checkScoreBalanceB(BankDto bankDto) {
        BBankScore bankScore = bBankRepository.findByNumberForUpdate(bankDto.getNumber()).orElseThrow(() -> new ScoreNotFoundException("Bank b score not found in checking balance!"));
        return bankScore.getBalance() >= bankDto.getAmount();
    }

}