package posterminal.posterminal.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
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
        ABankScore bankScore = aBankRepository.findByNumber(bankDto.getNumber());
        return bankScore.getBalance() < bankDto.getSumma();
    }

    public boolean checkScoreBalanceB(BankDto bankDto) {
        BBankScore bankScore = bBankRepository.findByNumber(bankDto.getNumber());
        return bankScore.getBalance() < bankDto.getSumma();
    }
}