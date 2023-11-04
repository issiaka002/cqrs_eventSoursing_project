package ci.inphb.CommandApi.Exception;

public class NegativeBalanceException extends Exception {
    public NegativeBalanceException(String message){
        super(message);
    }
}
