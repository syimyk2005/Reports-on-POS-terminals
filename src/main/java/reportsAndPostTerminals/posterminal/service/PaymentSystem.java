package reportsAndPostTerminals.posterminal.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reportsAndPostTerminals.posterminal.exception.customExceptions.ScoreNotFoundException;
import reportsAndPostTerminals.posterminal.model.entity.ABankScore;
import reportsAndPostTerminals.posterminal.model.entity.BBankScore;
import reportsAndPostTerminals.posterminal.model.dto.PaymentDto;
import reportsAndPostTerminals.posterminal.repository.ABankScoreRepository;
import reportsAndPostTerminals.posterminal.repository.BBankScoreRepository;

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