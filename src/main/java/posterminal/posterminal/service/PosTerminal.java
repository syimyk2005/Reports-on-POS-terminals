package posterminal.posterminal.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import posterminal.posterminal.model.BankDto;
import posterminal.posterminal.model.BankEnum;

@RequiredArgsConstructor
@Service
public class PosTerminal {

    private final PaymentSystem paySystemService;
    private final TransactionService transactionService;

    public void sendRequestToBank(BankDto bankDto){
        transactionService.transaction(bankDto);
    }

}