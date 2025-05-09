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
public class ABankScore {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String number;
    private double balance;
    @Enumerated(EnumType.STRING)
    private BankEnum bank;
    @Enumerated(EnumType.STRING)
    private Curr curr;

}