package ci.inphb.Query.Repositories;

import ci.inphb.Query.Entities.AccountTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountTransactionRepository extends JpaRepository<AccountTransaction, Long> {
}
