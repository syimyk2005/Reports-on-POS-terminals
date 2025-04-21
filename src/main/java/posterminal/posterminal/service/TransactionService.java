package posterminal.posterminal.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import posterminal.posterminal.exception.customExceptions.BalanceNotEnoughException;
import posterminal.posterminal.exception.customExceptions.ScoreNotFoundException;
import posterminal.posterminal.model.ABankScore;
import posterminal.posterminal.model.BBankScore;
import posterminal.posterminal.model.BankDto;
import posterminal.posterminal.enums.BankEnum;
import posterminal.posterminal.repository.ABankScoreRepository;
import posterminal.posterminal.repository.BBankScoreRepository;

@Log4j2
@RequiredArgsConstructor
@Service
public class TransactionService {

    private final ABankScoreRepository aBankRepository;
    private final BBankScoreRepository bBankRepository;
    private final PaymentSystem paySystemService;

    @Value("${score.number}")
    private String posTerminalNumber;

    @Transactional
    public void transaction(BankDto bankDto) {
        if (bankDto.getBank() == BankEnum.BANK_A) {
            if (paySystemService.checkScoreBalanceA(bankDto)) {
                log.info("Transferring {} from {} to {}", bankDto.getAmount(), bankDto.getNumber(), posTerminalNumber);
                transactionBetweenAAndA(bankDto);

            } else {
                throw new BalanceNotEnoughException("Is not enough balance!");
            }
        } else {
            if (paySystemService.checkScoreBalanceB(bankDto)) {
                transactionBetweenAAndB(bankDto);
            } else {
                throw new BalanceNotEnoughException("Is not enough balance!");
            }
        }
    }

    public void transactionBetweenAAndA(BankDto bankDto) {
        ABankScore aBankProducer = getABankScore(posTerminalNumber);
        ABankScore aBankConsumer = getABankScore(bankDto.getNumber());
        aBankConsumer.setBalance(aBankConsumer.getBalance() - bankDto.getAmount());
        aBankProducer.setBalance(aBankProducer.getBalance() + bankDto.getAmount());
        aBankRepository.save(aBankProducer);
        aBankRepository.save(aBankConsumer);
    }

    public void transactionBetweenAAndB(BankDto bankDto) {
        ABankScore aBank = getABankScore(posTerminalNumber);
        BBankScore bBank = bBankRepository.findByNumberForUpdate(bankDto.getNumber()).orElseThrow(() -> new ScoreNotFoundException("Bank score B not found!"));
        bBank.setBalance(bBank.getBalance() - bankDto.getAmount());
        aBank.setBalance(aBank.getBalance() + bankDto.getAmount());
        bBankRepository.save(bBank);
        aBankRepository.save(aBank);
    }

    ABankScore getABankScore(String number) {
        return aBankRepository.findByNumberForUpdate(number).orElseThrow(() -> new ScoreNotFoundException("ABankScore not found with number: " + number));
    }
}