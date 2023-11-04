package ci.inphb.CommandApi.Dto;

import lombok.Data;

@Data
public class CreditAccountRequestDT0 {
    private String accountID;
    private String currency;
    private double amount;
}
