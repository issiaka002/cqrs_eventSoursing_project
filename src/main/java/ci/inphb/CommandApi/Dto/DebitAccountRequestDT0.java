package ci.inphb.CommandApi.Dto;

import lombok.Data;

@Data
public class DebitAccountRequestDT0 {
    private String accountID;
    private String currency;
    private double amount;
}
