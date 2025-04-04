package posterminal.posterminal.model;

import jakarta.persistence.*;
import lombok.*;
import posterminal.posterminal.enums.Curr;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Report {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    private Curr curr;
    private int amount;
    private String number;
}
