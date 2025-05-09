package posterminal.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import posterminal.enums.BankEnum;
import posterminal.enums.Curr;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Getter
@Setter
public class BBankScore {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private double balance;
    private String number;
    @Enumerated(EnumType.STRING)
    private BankEnum bank;
    @Enumerated(EnumType.STRING)
    private Curr curr;

}