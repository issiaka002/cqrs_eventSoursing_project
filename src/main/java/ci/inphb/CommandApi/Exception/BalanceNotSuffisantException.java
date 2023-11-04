package ci.inphb.CommandApi.Exception;

public class BalanceNotSuffisantException extends Exception {
    public BalanceNotSuffisantException(String balanceNotSuffissant) {
        super(balanceNotSuffissant);
    }
}
