package ci.inphb.CommandApi.Commands;

import lombok.Getter;

public class CreateAccountCommmand extends BaseCommand<String>{

    @Getter
    private String currency;
    @Getter
    private double initialBalance;

    public CreateAccountCommmand(String id, String currency, double initialBalance) {
        super(id);
        this.currency = currency;
        this.initialBalance = initialBalance;
    }
}
