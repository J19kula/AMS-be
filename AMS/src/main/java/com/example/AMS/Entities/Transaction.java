package com.example.AMS.Entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
@Table(name = "transactions")
@Entity
public class Transaction {
    @Id
    @SequenceGenerator(name="TRANSACTION_SEQUENCE_GENERATOR", sequenceName="TRANSACTION_SEQUENCE", initialValue=1000000000)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TRANSACTION_SEQUENCE_GENERATOR")
    Long id;
    String initiatorAccountName;
    Long timeOfTransaction;
    String transactionType;
    float amount;
    Long recipientAccountNumber;
    String recipientAccountName;

    @ManyToOne
    @JoinColumn(name = "initiatorAccount")
    Account initiatorAccount;

}
