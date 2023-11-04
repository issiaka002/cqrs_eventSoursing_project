package ci.inphb.Query.Controllers;


import ci.inphb.Query.Entities.Account;
import ci.inphb.Query.Queries.GetAllAccount;
import lombok.AllArgsConstructor;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@AllArgsConstructor
@RequestMapping("/query/account")
public class AccountQueryController {

    private QueryGateway gateway;

    @GetMapping("/accounts")
    public CompletableFuture<List<Account>> accountList(){
         return gateway.query(new GetAllAccount(), ResponseTypes.multipleInstancesOf(Account.class));
    }
}
