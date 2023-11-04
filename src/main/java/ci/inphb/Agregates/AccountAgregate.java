package ci.inphb.Agregates;

import ci.inphb.CommandApi.Commands.CreateAccountCommmand;
import ci.inphb.CommandApi.Commands.CreditAccountCommand;
import ci.inphb.CommandApi.Commands.DebitAccountCommand;
import ci.inphb.CommandApi.Enums.AccountStatus;
import ci.inphb.CommandApi.Exception.BalanceNotSuffisantException;
import ci.inphb.CommandApi.Exception.NegativeBalanceException;
import ci.inphb.Events.AccountCreatedEvent;
import ci.inphb.Events.AccountCreditedEvent;
import ci.inphb.Events.AccountDebitedEvent;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;

@Aggregate
public class AccountAgregate {

    @AggregateIdentifier
    private String accountID;
    private String currency;
    private double balance;
    private AccountStatus status;

    public AccountAgregate(){
        //..Required by axon
    }

    /**
     * cette fonction de decision fait un subcribe au bus et ecoute l'evenement CreateAccountCommand
     * @param accountCommmand : command qui est attendu
     */
    @CommandHandler
    public AccountAgregate(CreateAccountCommmand accountCommmand) throws NegativeBalanceException {
        if(accountCommmand.getInitialBalance()<0) throw new NegativeBalanceException("Negative balance");
        AggregateLifecycle.apply(new AccountCreatedEvent(
                accountCommmand.getId(),
                accountCommmand.getCurrency(),
                accountCommmand.getInitialBalance(),
                AccountStatus.CREATED
        ));
    }
    @EventSourcingHandler
    public void on(AccountCreatedEvent accountCreatedEvent){
        this.accountID = accountCreatedEvent.getId();
        this.currency = accountCreatedEvent.getCurrency();
        this.balance = accountCreatedEvent.getBalance();
        this.status = accountCreatedEvent.getStatus();
    }

    //###################################################################
    /**
     * cette fonction de decision fait un subcribe au bus et ecoute l'evenement DebitAccountCommand
     * @param debitaccountCommmand : command qui est attendu
     */
    @CommandHandler
    public void handle(DebitAccountCommand debitaccountCommmand) throws NegativeBalanceException, BalanceNotSuffisantException {
        if(debitaccountCommmand.getAmount()<0) throw new NegativeBalanceException("Negative amount");
        if(debitaccountCommmand.getAmount()>this.balance) throw new BalanceNotSuffisantException("Balance not suffissant");
        AggregateLifecycle.apply(new AccountDebitedEvent(
                debitaccountCommmand.getId(),
                debitaccountCommmand.getCurrency(),
                debitaccountCommmand.getAmount()
        ));
    }
    @EventSourcingHandler
    public void on(AccountDebitedEvent accountDebitedEvent){
        this.balance -= accountDebitedEvent.getAmount();
        this.currency = accountDebitedEvent.getCurrency();
    }

    //###################################################################
    /**
     * cette fonction de decision fait un subcribe au bus et ecoute l'evenement CreditAccountCommand
     * @param creditaccountCommmand : command qui est attendu
     */
    @CommandHandler
    public void handle(CreditAccountCommand creditaccountCommmand) throws NegativeBalanceException {
        if(creditaccountCommmand.getAmount()<0) throw new NegativeBalanceException("Negative amount");
        AggregateLifecycle.apply(new AccountCreditedEvent(
                creditaccountCommmand.getId(),
                creditaccountCommmand.getCurrency(),
                creditaccountCommmand.getAmount()
        ) );
    }
    @EventSourcingHandler
    public void on(AccountCreditedEvent creditedEvent){
        this.balance += creditedEvent.getAmount();
        this.currency = creditedEvent.getCurrency();
    }
}
