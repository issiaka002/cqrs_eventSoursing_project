package ci.inphb.Query.Services;


import ci.inphb.Events.AccountCreatedEvent;
import ci.inphb.Query.Entities.Account;
import ci.inphb.Query.Queries.GetAllAccount;
import ci.inphb.Query.Repositories.AccountRepository;
import ci.inphb.Query.Repositories.AccountTransactionRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.eventhandling.EventMessage;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service @Transactional @Slf4j @AllArgsConstructor
public class AccountEventHandlerService {

    private AccountRepository accountRepository;
    private AccountTransactionRepository transactionRepository;

    // ########################################################################

    @EventHandler
    public void on(AccountCreatedEvent event, EventMessage<AccountCreatedEvent> eventMessage)
    {
        log.info("***********************************");
        log.info(" AccountCreatedEvent received .....");
        Account account = Account.builder()
                .accountID(event.getId())
                .balance(event.getBalance())
                .createdAt(eventMessage.getTimestamp())
                .currency(event.getCurrency())
                .status(event.getStatus())
                .build();
        log.info("Account "+event.getId()+" created");
        log.info("***********************************");

    }

    // ############################### QUERY ####################################
    @QueryHandler
    public List<Account> on(GetAllAccount query){
        return accountRepository.findAll();
    }
}
