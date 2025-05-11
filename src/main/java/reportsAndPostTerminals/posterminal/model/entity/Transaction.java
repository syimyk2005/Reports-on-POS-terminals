package reportsAndPostTerminals.posterminal.model.entity;

import jakarta.persistence.*;
import lombok.*;
import reportsAndPostTerminals.posterminal.model.enums.Curr;

import java.time.LocalDateTime;


@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime operationDateTime;
    @Enumerated(EnumType.STRING)
    private Curr curr;
    private double amount;
    private String fromScore;
    private String toDevice;

    public Transaction(LocalDateTime operationDateTime, Curr curr, double amount, String fromScore, String toDevice) {
        this.operationDateTime = operationDateTime;
        this.curr = curr;
        this.amount = amount;
        this.fromScore = fromScore;
        this.toDevice = toDevice;
    }

}
