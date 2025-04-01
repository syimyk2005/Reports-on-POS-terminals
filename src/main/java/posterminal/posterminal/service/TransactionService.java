package posterminal.posterminal.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import posterminal.posterminal.repository.AccountRepository;

@RequiredArgsConstructor
@Service
public class TransactionService {

    private final AccountRepository accountRepository;


        accountRepository.save(fromAccount);
        accountRepository.save(toAccount);

        return "Транзакция успешно выполнена";
    }
}

