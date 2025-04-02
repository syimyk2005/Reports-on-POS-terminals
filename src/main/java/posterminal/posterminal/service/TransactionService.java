package posterminal.posterminal.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import posterminal.posterminal.repository.BankScoreRepository;

@RequiredArgsConstructor
@Service
public class TransactionService {

    private final BankScoreRepository accountRepository;


}

