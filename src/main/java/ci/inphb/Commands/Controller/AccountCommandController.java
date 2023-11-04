package ci.inphb.Commands.Controller;


import ci.inphb.CommandApi.Commands.CreateAccountCommmand;
import ci.inphb.CommandApi.Commands.DebitAccountCommand;
import ci.inphb.CommandApi.Dto.CreateAccountRequestDT0;
import ci.inphb.CommandApi.Dto.CreditAccountRequestDT0;
import ci.inphb.CommandApi.Dto.DebitAccountRequestDT0;
import lombok.AllArgsConstructor;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.eventsourcing.eventstore.EventStore;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;

@RestController
@RequestMapping("/command/account")
@AllArgsConstructor
public class AccountCommandController {

    private CommandGateway commandGateway;
    private EventStore eventStore;

    @PostMapping("/create")
    public CompletableFuture<String> createNewAccount(@RequestBody CreateAccountRequestDT0 requestDT0)
    {
         return commandGateway.send(new CreateAccountCommmand(
                UUID.randomUUID().toString(),
                requestDT0.getCurrency(),
                requestDT0.getInitialBalance()
        ));
    }
    @PostMapping("/debit")
    public CompletableFuture<String> debitAccount(@RequestBody DebitAccountRequestDT0 debitAccountRequestDT0)
    {
        return commandGateway.send(new DebitAccountCommand(
                debitAccountRequestDT0.getAccountID(),
                debitAccountRequestDT0.getCurrency(),
                debitAccountRequestDT0.getAmount()
        ));
    }

    @PostMapping("/credit")
    public CompletableFuture<String> creditAccount(@RequestBody CreditAccountRequestDT0 creditAccountRequestDT0)
    {
        return commandGateway.send(new CreateAccountCommmand(
                creditAccountRequestDT0.getAccountID(),
                creditAccountRequestDT0.getCurrency(),
                creditAccountRequestDT0.getAmount()
        ));
    }

    @GetMapping("/event/{id}")
    public Stream getEvents(@PathVariable String id)
    {
        return eventStore.readEvents(id).asStream();
    }





    /**
     * fonction qui ecoute tout exception
     * @param ex n'import quel evenement
     * @return le message d'erreur correspond et le code http
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> exeptionHandler(Exception ex){
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }


}
