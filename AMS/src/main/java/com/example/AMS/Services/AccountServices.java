package com.example.AMS.Services;

import com.example.AMS.Entities.Account;
import com.example.AMS.Entities.Customer;
import com.example.AMS.Repositories.AccountRepo;
import com.example.AMS.exceptions.AccountNotFoundException;
import com.example.AMS.exceptions.CustomerNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AccountServices {
    private final AccountRepo accountRepo;
    private final CustomerServices customerServices;

    @Autowired
    public AccountServices(AccountRepo accountRepo, CustomerServices customerServices){
        this.accountRepo = accountRepo;
        this.customerServices = customerServices;
    }

    public Account saveAccount(Account account) throws CustomerNotFoundException {
        Customer customer = customerServices.getCustomerById(account.getCustomer().getPermanentAccountNumber());
        account.setCustomer(customer);
        return accountRepo.save(account);
    }

    public List<Account> getAllAccounts(){
        return this.accountRepo.findAll();
    }

    public Account getAccountById(Long accountNumber) throws AccountNotFoundException {
        Optional<Account> accountOptional = accountRepo.findById(accountNumber);
        if (!accountOptional.isPresent()){
            throw new AccountNotFoundException("Account not found accountNumber: "+ accountNumber);
        }
        return accountOptional.get();
    }

    public Account updateAccount(Account account) throws AccountNotFoundException {
        Optional<Account> accountOptional = accountRepo.findById(account.getAccountNumber());
        if (!accountOptional.isPresent()){
            throw new AccountNotFoundException("Account not found with accountNumber: "+account.getAccountNumber());
        }
        return accountRepo.save(account);
    }

    public Account deleteAccount(Long accountNumber) throws AccountNotFoundException {
        Optional<Account> accountOptional = accountRepo.findById(accountNumber);
        if (!accountOptional.isPresent()){
            throw new AccountNotFoundException("Account not found with accountNumber: "+accountNumber);
        }
        Account account = accountOptional.get();
        accountRepo.deleteById(account.getAccountNumber());
        return account;
    }

    public List<Account> findAllAccountsByPersonalAccessNumber(Long personalAccessNumber){
        List<Account> accountList = getAllAccounts();
        List<Account> accountListFiltered = accountList.stream()
                .filter(account -> Objects.equals(account.getCustomer().getPermanentAccountNumber(), personalAccessNumber))
                .collect(Collectors.toList());
        return accountListFiltered;
    }
}
