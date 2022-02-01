package com.example.AMS.Services;

import com.example.AMS.Entities.Account;
import com.example.AMS.Entities.Customer;
import com.example.AMS.Repositories.AccountRepo;
import com.example.AMS.Repositories.CustomerRepo;
import com.example.AMS.exceptions.AccountNotFoundException;
import com.example.AMS.exceptions.CustomerNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CustomerServices {
    private final CustomerRepo customerRepo;
    private final AccountRepo accountRepo;

    @Autowired
    public CustomerServices(CustomerRepo customerRepo, AccountRepo accountRepo){
        this.customerRepo = customerRepo;
        this.accountRepo = accountRepo;
    }

    public Customer saveCustomer(Customer customer){
        return this.customerRepo.save(customer);
    }

    public List<Customer> getAllCustomers(){
        return this.customerRepo.findAll();
    }

    public Customer getCustomerById(Long personalAccountNumber) throws CustomerNotFoundException {
        Optional<Customer> customerOptional = customerRepo.findById(personalAccountNumber);
        if(!customerOptional.isPresent()){
            throw new CustomerNotFoundException("Customer not found with personalAccountNumber: "+ personalAccountNumber);
        }
        return customerOptional.get();
    }

    public Customer updateCustomer(Customer customer) throws CustomerNotFoundException {
        Optional<Customer> customerOptional = customerRepo.findById(customer.getPermanentAccountNumber());
        if(!customerOptional.isPresent()){
            throw new CustomerNotFoundException("Customer not found with personalAccountNumber: "+ customer.getPermanentAccountNumber());
        }
        return customerRepo.save(customer);
    }

    public Customer deleteCustomer(Long personalAccountNumber) throws CustomerNotFoundException {
        Optional<Customer> customerOptional = customerRepo.findById(personalAccountNumber);
        if(!customerOptional.isPresent()){
            throw new CustomerNotFoundException("Customer not found with personalAccountNumber: "+ personalAccountNumber);
        }
        Customer customer = customerOptional.get();
        customerRepo.deleteById(personalAccountNumber);
        return customer;
    }

    public Customer addLinkedAccountToCustomer(Long personalAccountNumber, long accountNumber) throws CustomerNotFoundException, AccountNotFoundException {
        Optional<Customer> customerOptional = customerRepo.findById(personalAccountNumber);
        if (!customerOptional.isPresent()){
            throw new CustomerNotFoundException("Customer not found with personalAccountNumber: "+personalAccountNumber);
        }

        Optional<Account> accountOptional = accountRepo.findById(accountNumber);
        if (!accountOptional.isPresent()){
            throw new AccountNotFoundException("Account not found with accountNumber: "+accountNumber);
        }

        Customer customer = customerOptional.get();
        Account account = accountOptional.get();

        List<Account> accountLinkedList = customer.getLinkedAccounts();
        accountLinkedList.add(account);

        List<Customer> customerLinkedList = account.getLinkedCustomer();
        customerLinkedList.add(customer);

        customer.setLinkedAccounts(accountLinkedList);
        account.setLinkedCustomer(customerLinkedList);

        customerRepo.save(customer);
        accountRepo.save(account);

        return customer;
    }

    public List<Account> findAllLinkedAccountByCustomerPersonalAccountNumber(Long personalAccountNumber){
        List<Account> accountList = accountRepo.findAll();
        List<Account> linkedAccounts = new ArrayList<>();

        accountList.forEach(account -> {
            if (account.getLinkedCustomer().stream().anyMatch(customer -> customer.getPermanentAccountNumber().equals(personalAccountNumber))){
                linkedAccounts.add(account);
            }
        });

        return linkedAccounts;
    }
}
