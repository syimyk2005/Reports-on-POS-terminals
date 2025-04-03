package posterminal.posterminal.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import posterminal.posterminal.model.ABankScore;
import posterminal.posterminal.model.BBankScore;
import posterminal.posterminal.model.BankDto;
import posterminal.posterminal.model.BankEnum;
import posterminal.posterminal.repository.ABankScoreRepository;
import posterminal.posterminal.repository.BBankScoreRepository;


@RequiredArgsConstructor
@Service
public class TransactionService {

    private final ABankScoreRepository aBankRepository;
    private final BBankScoreRepository bBankRepository;
    private final PaymentSystem paySystemService;

    public void transaction(BankDto bankDto) {
        if (bankDto.getBank() == BankEnum.BANK_A){
            paySystemService.checkScoreBalanceA(bankDto);
            transactionBetweenAAndA(bankDto);
        }else {
            paySystemService.checkScoreBalanceB(bankDto);
            transactionBetweenAAndB(bankDto);
        }
    }

    public void transactionBetweenAAndA(BankDto bankDto) {
        ABankScore aBankProducer = aBankRepository.findByNumber("12345678");
        ABankScore aBankConsumer = aBankRepository.findByNumber(bankDto.getNumber());
        aBankConsumer.setBalance(aBankConsumer.getBalance() - bankDto.getSumma());
        aBankProducer.setBalance(aBankProducer.getBalance() + bankDto.getSumma());
        aBankRepository.save(aBankProducer);
        aBankRepository.save(aBankConsumer);

    }

    public void transactionBetweenAAndB(BankDto bankDto) {
        ABankScore aBank = aBankRepository.findByNumber("12345678");
        BBankScore bBank = bBankRepository.findByNumber(bankDto.getNumber());
        bBank.setBalance(bBank.getBalance() - bankDto.getSumma());
        aBank.setBalance(aBank.getBalance() + bankDto.getSumma());
        bBankRepository.save(bBank);
        aBankRepository.save(aBank);
    }
}