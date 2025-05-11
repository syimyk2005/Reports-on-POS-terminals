package reportsAndPostTerminals.posterminal.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import reportsAndPostTerminals.posterminal.exception.customExceptions.BalanceNotEnoughException;
import reportsAndPostTerminals.posterminal.exception.customExceptions.ScoreNotFoundException;
import reportsAndPostTerminals.posterminal.model.entity.ABankScore;
import reportsAndPostTerminals.posterminal.model.entity.BBankScore;
import reportsAndPostTerminals.posterminal.model.dto.PaymentDto;
import reportsAndPostTerminals.posterminal.model.enums.BankEnum;
import reportsAndPostTerminals.posterminal.model.entity.Transaction;
import reportsAndPostTerminals.posterminal.repository.ABankScoreRepository;
import reportsAndPostTerminals.posterminal.repository.BBankScoreRepository;
import reportsAndPostTerminals.posterminal.repository.TransactionRepository;

import java.time.LocalDateTime;

@Log4j2
@RequiredArgsConstructor
@Service
public class TransactionService {

    private final ABankScoreRepository aBankRepository;
    private final BBankScoreRepository bBankRepository;
    private final PaymentSystem paySystemService;
    private final TransactionRepository transactionRepository;

    @Transactional
    public void transaction(PaymentDto bankDto) {
        if (bankDto.getBank() == BankEnum.BANK_A) {
            if (paySystemService.checkScoreBalanceA(bankDto)) {
                log.info("Transferring {} from {} to {}", bankDto.getAmount(), bankDto.getNumber(), bankDto.getDeviceCode());
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

    public void transactionBetweenAAndA(PaymentDto bankDto) {
        ABankScore aBankProducer = getABankScore(bankDto.getDeviceCode());
        ABankScore aBankConsumer = getABankScore(bankDto.getNumber());
        aBankConsumer.setBalance(aBankConsumer.getBalance() - bankDto.getAmount());
        aBankProducer.setBalance(aBankProducer.getBalance() + bankDto.getAmount());
        aBankRepository.save(aBankProducer);
        aBankRepository.save(aBankConsumer);
        Transaction transaction = new Transaction(LocalDateTime.now(), bankDto.getCurr(), bankDto.getAmount(), bankDto.getNumber(), bankDto.getDeviceCode());
        transactionRepository.save(transaction);
    }

    public void transactionBetweenAAndB(PaymentDto bankDto) {
        ABankScore aBank = getABankScore(bankDto.getDeviceCode());
        BBankScore bBank = bBankRepository.findByNumberForUpdate(bankDto.getNumber()).orElseThrow(() -> new ScoreNotFoundException("Bank score B not found!"));
        bBank.setBalance(bBank.getBalance() - bankDto.getAmount());
        aBank.setBalance(aBank.getBalance() + bankDto.getAmount());
        bBankRepository.save(bBank);
        aBankRepository.save(aBank);
        Transaction transaction = new Transaction(LocalDateTime.now(), bankDto.getCurr(), bankDto.getAmount(), bankDto.getNumber(), bankDto.getDeviceCode());
        transactionRepository.save(transaction);
    }

    ABankScore getABankScore(String number) {
        return aBankRepository.findByNumberForUpdate(number).orElseThrow(() -> new ScoreNotFoundException("ABankScore not found with number: " + number));
    }


}