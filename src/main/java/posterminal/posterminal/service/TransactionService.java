package posterminal.posterminal.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import posterminal.posterminal.model.Account;
import posterminal.posterminal.repository.AccountRepository;

@Service
public class TransactionService {

    @Autowired
    private AccountRepository accountRepository;

    @Transactional
    public String transferMoney(String fromAccountName, String toAccountName, double amount) {
        Account fromAccount = accountRepository.findByName(fromAccountName);
        Account toAccount = accountRepository.findByName(toAccountName);

        if (fromAccount == null || toAccount == null) {
            throw new IllegalArgumentException("Один из счетов не найден");
        }

        if (fromAccount.getBalance() < amount) {
            throw new IllegalArgumentException("Недостаточно средств для перевода");
        }

        fromAccount.withdraw(amount);
        toAccount.deposit(amount);

        accountRepository.save(fromAccount);
        accountRepository.save(toAccount);

        return "Транзакция успешно выполнена";
    }
}

