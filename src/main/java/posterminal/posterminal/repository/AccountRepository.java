package posterminal.posterminal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import posterminal.posterminal.model.Account;

public interface AccountRepository extends JpaRepository<Account, Long> {
    Account findByName(String name);
}

