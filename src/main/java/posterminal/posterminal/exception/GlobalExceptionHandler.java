package posterminal.posterminal.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ControllerAdvice;
import posterminal.posterminal.exception.customExceptions.BalanceNotEnoughException;
import posterminal.posterminal.exception.customExceptions.ScoreNotFoundException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BalanceNotEnoughException.class)
    public ResponseEntity<String> handleNotEnoughBalance(BalanceNotEnoughException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }

    @ExceptionHandler(ScoreNotFoundException.class)
    public ResponseEntity<String> handleNotFoundScore(ScoreNotFoundException e){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }

}