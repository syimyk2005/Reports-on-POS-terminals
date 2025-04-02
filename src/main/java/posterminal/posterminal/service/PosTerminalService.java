package posterminal.posterminal.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class PosTerminalService {

    private final PaymentSystemService paySystemService;

    public void sendRequestToBank(Long id){
        if (!paySystemService.checkScoreBalance(id)){

        }
    }


    public void withdrawalRequest(){

    }

}
