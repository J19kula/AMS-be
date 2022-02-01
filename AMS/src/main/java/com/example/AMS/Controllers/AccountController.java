package com.example.AMS.Controllers;

import com.example.AMS.Entities.Account;
import com.example.AMS.Services.AccountServices;
import com.example.AMS.exceptions.AccountNotFoundException;
import com.example.AMS.exceptions.CustomerNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/account")
@CrossOrigin
public class AccountController {

    private final AccountServices accountServices;

    @Autowired
    public AccountController(AccountServices accountServices){
        this.accountServices = accountServices;
    }

    @GetMapping("/{accountNumber}")
    public ResponseEntity<Account> getAccountById(@PathVariable Long accountNumber) throws AccountNotFoundException {
        return new ResponseEntity<Account>(accountServices.getAccountById(accountNumber), HttpStatus.OK);
    }

    @GetMapping("/personal/{pan}")
    public ResponseEntity<List<Account>> getAllAccountByPersonalAccountNumber(@PathVariable Long personalAccountNumber){
        return new ResponseEntity<List<Account>>(accountServices.findAllAccountsByPersonalAccessNumber(personalAccountNumber), HttpStatus.OK);
    }

    @PostMapping("/")
    public ResponseEntity<Account> addAccount(@RequestBody Account account) throws CustomerNotFoundException {
        return new ResponseEntity<Account>(accountServices.saveAccount(account), HttpStatus.OK);
    }

    @GetMapping("/")
    public ResponseEntity<List<Account>> getAllAccounts(){
        return new ResponseEntity<List<Account>>(accountServices.getAllAccounts(), HttpStatus.OK);
    }

    @PutMapping("/")
    public ResponseEntity<Account> updateAccount(@RequestBody Account account) throws AccountNotFoundException{
        return new ResponseEntity<Account>(accountServices.updateAccount(account), HttpStatus.OK);
    }

    @DeleteMapping("/{accountNumber}")
    public ResponseEntity<Account> deleteAccount(@PathVariable Long accountNumber) throws AccountNotFoundException{
        return new ResponseEntity<Account>(accountServices.deleteAccount(accountNumber), HttpStatus.OK);
    }
}
