package com.example.AMS.Controllers;

import com.example.AMS.Entities.Account;
import com.example.AMS.Entities.Customer;
import com.example.AMS.Services.CustomerServices;
import com.example.AMS.exceptions.AccountNotFoundException;
import com.example.AMS.exceptions.CustomerNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customer")
@CrossOrigin
public class CustomerController {

    private final CustomerServices customerServices;

    @Autowired
    public CustomerController(CustomerServices customerServices){
        this.customerServices = customerServices;
    }

    @PostMapping("/")
    public ResponseEntity<Customer> addCustomer(@RequestBody Customer customer){
        return new ResponseEntity<Customer>(customerServices.saveCustomer(customer), HttpStatus.OK);
    }

    @GetMapping("/{pan}")
    public ResponseEntity<Customer> getCustomerById(@PathVariable Long pan) throws CustomerNotFoundException {
        return new ResponseEntity<Customer>(customerServices.getCustomerById(pan), HttpStatus.OK);
    }

    @GetMapping("/{pan}/linked")
    public ResponseEntity<List<Account>> getAllLinkedAccounts(@PathVariable Long pan) {
        return new ResponseEntity<List<Account>>(customerServices.findAllLinkedAccountByCustomerPersonalAccountNumber(pan), HttpStatus.OK);
    }

    @GetMapping("/")
    public ResponseEntity<List<Customer>> getAllCustomers(){
        return new ResponseEntity<List<Customer>>(customerServices.getAllCustomers(),HttpStatus.OK);
    }

    @PutMapping("/")
    public ResponseEntity<Customer> updateCustomer(@RequestBody Customer customer) throws CustomerNotFoundException, AccountNotFoundException {
        return new ResponseEntity<Customer>(customerServices.updateCustomer(customer), HttpStatus.OK);
    }

    @PutMapping("/{pan}/{accountNumber}")
    public ResponseEntity<Customer> updateLinkedAccountForCustomer(@PathVariable Long pan, @PathVariable long accountNumber) throws CustomerNotFoundException, AccountNotFoundException {
        return new ResponseEntity<Customer>(customerServices.addLinkedAccountToCustomer(pan,accountNumber), HttpStatus.OK);
    }

    @DeleteMapping("/{pan}")
    public ResponseEntity<Customer> deleteCustomer(@PathVariable Long pan) throws CustomerNotFoundException{
        return new ResponseEntity<Customer>(customerServices.deleteCustomer(pan), HttpStatus.OK);
    }
}
