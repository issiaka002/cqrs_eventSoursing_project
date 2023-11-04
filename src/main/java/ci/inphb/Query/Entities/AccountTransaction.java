package ci.inphb.Query.Entities;

import ci.inphb.CommandApi.Enums.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data @AllArgsConstructor @NoArgsConstructor @Builder
public class AccountTransaction
{
    @Id
    private Long id;
    private Date date;
    private double amount;
    @Enumerated(EnumType.STRING)
    private TransactionType type;
    @ManyToOne
    private Account account;
}
