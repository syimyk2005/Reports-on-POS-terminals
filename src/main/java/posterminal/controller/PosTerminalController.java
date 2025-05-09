package posterminal.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import posterminal.model.dto.PaymentDto;
import posterminal.service.TransactionService;

@RequiredArgsConstructor
@RequestMapping("/pos-terminal")
@RestController
public class PosTerminalController {

    private final TransactionService transactionService;

    @PostMapping ("/transaction")
    ResponseEntity<String> transaction(@RequestBody PaymentDto bankDto){
        transactionService.transaction(bankDto);
        return ResponseEntity.ok("Transaction was successfully!");
    }
}
