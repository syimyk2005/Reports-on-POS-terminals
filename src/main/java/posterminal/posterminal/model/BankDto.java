package posterminal.posterminal.model;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import posterminal.posterminal.enums.BankEnum;
import posterminal.posterminal.enums.Curr;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class BankDto {
    private double amount;
    private String number;
    @Enumerated(EnumType.STRING)
    private BankEnum bank;
    @Enumerated(EnumType.STRING)
    private Curr curr;
}