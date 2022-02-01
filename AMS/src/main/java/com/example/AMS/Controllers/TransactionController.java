package com.example.AMS.Controllers;

import com.example.AMS.Entities.Transaction;
import com.example.AMS.Services.TransactionServices;
import com.example.AMS.exceptions.AccountNotFoundException;
import com.example.AMS.exceptions.InsufficientFundsException;
import com.example.AMS.exceptions.TransactionNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/transaction")
@CrossOrigin
public class TransactionController {

    private final TransactionServices transactionServices;

    @Autowired
    public TransactionController(TransactionServices transactionServices){
        this.transactionServices = transactionServices;
    }

    @PostMapping("/")
    public ResponseEntity<Transaction> addTransaction(@RequestBody Transaction transaction) throws AccountNotFoundException, InsufficientFundsException {
        return new ResponseEntity<Transaction>(transactionServices.saveTransaction(transaction), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Transaction> getTransactionById(@PathVariable Long id) throws TransactionNotFoundException {
        return new ResponseEntity<Transaction>(transactionServices.getTransactionById(id), HttpStatus.OK);
    }

    @GetMapping("/")
    public ResponseEntity<List<Transaction>> getAllTransactions(){
        return new ResponseEntity<List<Transaction>>(transactionServices.getAllTransactions(), HttpStatus.OK);
    }

    @GetMapping("/account/{accountNumber")
    public ResponseEntity<List<Transaction>> getAllTransactionsByAccountNumber(@PathVariable Long accountNumber){
        return new ResponseEntity<List<Transaction>>(transactionServices.getAllTransactionsByAccountNumber(accountNumber), HttpStatus.OK);
    }

    @PutMapping("/")
    public ResponseEntity<Transaction> updateTransaction(@RequestBody Transaction transaction) throws TransactionNotFoundException{
        return new ResponseEntity<Transaction>(transactionServices.updateTransaction(transaction), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Transaction> deleteTransaction(@PathVariable Long id) throws  TransactionNotFoundException{
        return new ResponseEntity<Transaction>(transactionServices.deleteTransaction(id), HttpStatus.OK);
    }
}
