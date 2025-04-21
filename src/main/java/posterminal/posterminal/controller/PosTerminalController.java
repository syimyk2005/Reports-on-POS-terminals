package posterminal.posterminal.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import posterminal.posterminal.model.BankDto;
import posterminal.posterminal.service.TransactionService;

@RequiredArgsConstructor
@RequestMapping("/pos-terminal")
@RestController
public class PosTerminalController {

    private final TransactionService transactionService;

    @PostMapping ("/transaction")
    ResponseEntity<String> transaction(@RequestBody BankDto bankDto){
        transactionService.transaction(bankDto);
        return ResponseEntity.ok("Transaction was successfully!");
    }
    @GetMapping()
        ResponseEntity <String> rt(){
            return ResponseEntity.ok("hello");
        }

}
