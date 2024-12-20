package com.oliveiradev.picpaysimplificado.services;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.oliveiradev.picpaysimplificado.domain.transactions.Transaction;
import com.oliveiradev.picpaysimplificado.domain.user.User;
import com.oliveiradev.picpaysimplificado.dtos.TransactionDTO;
import com.oliveiradev.picpaysimplificado.repositories.TransactionRepository;

@Service
public class TransactionService {
    @Autowired
    private UserService userService;

    @Autowired
    private TransactionRepository repository;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private NotificationService notificationService;

    public Transaction createTransaction(TransactionDTO transaction) throws Exception {
        System.out.println("Iniciando transação...");
        User sender = this.userService.findUserById(transaction.senderId());
        User receiver = this.userService.findUserById(transaction.receiverID());
    
        System.out.println("Usuário remetente encontrado: " + sender.getFirstName());
        System.out.println("Usuário destinatário encontrado: " + receiver.getFirstName());
    
        userService.validateTransaction(sender, transaction.value());
    
        boolean isAuthorized = this.authorizeTransaction(sender, transaction.value());
        if(!isAuthorized) {
            throw new Exception("Transação não autorizada");
        }
    
        Transaction newTransaction = new Transaction();
        newTransaction.setAmount(transaction.value());
        newTransaction.setSender(sender);
        newTransaction.setReceiver(receiver);
        newTransaction.setTimestamp(LocalDateTime.now());
    
        sender.setBalance(sender.getBalance().subtract(transaction.value()));
        receiver.setBalance(receiver.getBalance().add(transaction.value()));
    
        this.repository.save(newTransaction);
        this.userService.saveUser(sender);
        this.userService.saveUser(receiver);
    
        System.out.println("Transação concluída com sucesso!");
    
        this.notificationService.sendNotification(sender, "Transação realizada com sucesso!");
        this.notificationService.sendNotification(receiver, "Transação realizada com sucesso!");
    
        return newTransaction;
    }
    
        public boolean authorizeTransaction(User sender, BigDecimal value) {
            ResponseEntity<Map> authResponse= restTemplate.getForEntity("https://run.mocky.io/v3/8fafdd68-a090-496f-8c9a-3442cf30dae6", Map.class);

            if (authResponse.getStatusCode() == HttpStatus.OK) {
            @SuppressWarnings("null")
            String message = (String) authResponse.getBody().get("message");
                return "Autorizado".equalsIgnoreCase(message);
        }  else 
        return false;
    }
}