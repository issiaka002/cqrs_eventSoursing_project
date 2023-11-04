package ci.inphb.CommandApi.Dto;

import lombok.Data;

@Data
public class CreateAccountRequestDT0 {
    private String currency;
    private double initialBalance;
}
