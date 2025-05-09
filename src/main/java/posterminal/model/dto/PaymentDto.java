package posterminal.model.dto;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import posterminal.enums.BankEnum;
import posterminal.enums.Curr;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PaymentDto {
    private double amount;
    private String number;
    @Enumerated(EnumType.STRING)
    private BankEnum bank;
    @Enumerated(EnumType.STRING)
    private Curr curr;
    private String deviceCode;
}