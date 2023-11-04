package ci.inphb.Query.Repositories;

import ci.inphb.Query.Entities.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, String> {
}
