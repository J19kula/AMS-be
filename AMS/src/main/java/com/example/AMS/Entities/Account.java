package com.example.AMS.Entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
@Table(name = "bank_accounts")
@Entity
public class Account {
    @Id
    @SequenceGenerator(name="ACCOUNT_SEQUENCE_GENERATOR", sequenceName="ACCOUNT_SEQUENCE", initialValue=100000)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ACCOUNT_SEQUENCE_GENERATOR")
    Long accountNumber;
    String accountName;
    double currentBalance;

    @ManyToOne
    @JoinColumn(name = "customerId", referencedColumnName = "permanentAccountNumber")
    Customer customer;

    @JsonIgnore
    @OneToMany(mappedBy = "initiatorAccount")
    List<Transaction> transactionList;

    @ManyToMany(fetch = FetchType.EAGER)
    List<Customer> linkedCustomer;

}
