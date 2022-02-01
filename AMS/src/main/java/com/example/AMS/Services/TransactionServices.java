package com.example.AMS.Services;

import com.example.AMS.Entities.Account;
import com.example.AMS.Entities.Transaction;
import com.example.AMS.Repositories.TransactionRepo;
import com.example.AMS.exceptions.AccountNotFoundException;
import com.example.AMS.exceptions.InsufficientFundsException;
import com.example.AMS.exceptions.TransactionNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TransactionServices {
    private final TransactionRepo transactionRepo;
    private final AccountServices accountServices;

    @Autowired
    public TransactionServices(TransactionRepo transactionRepo, AccountServices accountServices){
        this.transactionRepo = transactionRepo;
        this.accountServices = accountServices;
    }

    public Transaction saveTransaction(Transaction transaction) throws AccountNotFoundException, InsufficientFundsException {
        Account account = accountServices.getAccountById(transaction.getInitiatorAccount().getAccountNumber());
        transaction.setInitiatorAccount(account);

        if (transaction.getTransactionType().equals("WITHDRAWL")){
            if (account.getCurrentBalance() < transaction.getAmount()){
                throw new InsufficientFundsException("Failed to withdraw "+transaction.getAmount() + " from account: "
                        + account.getAccountName()+" , The account only has " + account.getCurrentBalance());
            } else {
                account.setCurrentBalance(account.getCurrentBalance() - transaction.getAmount());
                accountServices.updateAccount(account);
            }
        } else if (transaction.getTransactionType().equals("DEPOSIT")){
            account.setCurrentBalance(account.getCurrentBalance() + transaction.getAmount());
            accountServices.updateAccount(account);
        } else if (transaction.getTransactionType().equals("TRANSFER")) {
            if (account.getCurrentBalance() < transaction.getAmount()){
                throw new InsufficientFundsException("Failed to transfer "+transaction.getAmount() + " from account: "
                        + account.getAccountName()+" , The account only has " + account.getCurrentBalance());
            } else {
                Account otherAccount = accountServices.getAccountById(transaction.getRecipientAccountNumber());
                account.setCurrentBalance(account.getCurrentBalance()-transaction.getAmount());
                otherAccount.setCurrentBalance(otherAccount.getCurrentBalance()+transaction.getAmount());
                accountServices.updateAccount(account);
                accountServices.updateAccount(otherAccount);
                transactionRepo.save(transaction);
            }
        }
        return transactionRepo.save(transaction);
    }

    public List<Transaction> getAllTransactions(){
        return this.transactionRepo.findAll();
    }

    public Transaction getTransactionById(Long id) throws TransactionNotFoundException {
        Optional<Transaction> transactionOptional = transactionRepo.findById(id);
        if (!transactionOptional.isPresent()){
            throw new TransactionNotFoundException("Transaction not found with id: "+id);
        }
        return transactionOptional.get();
    }

    public Transaction updateTransaction(Transaction transaction) throws  TransactionNotFoundException{
        Optional<Transaction> optionalTransaction = transactionRepo.findById(transaction.getId());
        if (!optionalTransaction.isPresent()){
            throw new TransactionNotFoundException("Transaction not found with id: " + transaction.getId());
        }
        return transactionRepo.save(transaction);
    }

    public Transaction deleteTransaction(Long id) throws TransactionNotFoundException{
        Optional<Transaction> optionalTransaction = transactionRepo.findById(id);
        if (optionalTransaction.isPresent()){
            throw new TransactionNotFoundException("Transfer not found with id: " + id);
        }
        Transaction transaction = optionalTransaction.get();
        transactionRepo.deleteById(id);
        return transaction;
    }

    public List<Transaction> getAllTransactionsByAccountNumber(Long accountNumber) {
        List<Transaction> transactionList = getAllTransactions();
        List<Transaction> transactionListFiltered = transactionList
                .stream()
                .filter(transaction ->
                        Objects.equals(transaction.getInitiatorAccount().getAccountNumber(), accountNumber) ||
                Objects.equals(transaction.getRecipientAccountNumber(), accountNumber)).collect(Collectors.toList());
        return transactionListFiltered;
    }
}
