package com.example.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.entity.Account;
import com.example.entity.Message;
import com.example.repository.AccountRepository;
import com.example.repository.MessageRepository;
import com.example.service.AccountService;
import com.example.service.MessageService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.*;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller using Spring. The endpoints you will need can be
 * found in readme.md as well as the test cases. You be required to use the @GET/POST/PUT/DELETE/etc Mapping annotations
 * where applicable as well as the @ResponseBody and @PathVariable annotations. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
@RestController
public class SocialMediaController {

    AccountService accountService;
    AccountRepository accountRepository;
    MessageService messageService;
    MessageRepository messageRepository;

    public SocialMediaController(AccountService accountService, AccountRepository accountRepository,
                                    MessageService messageService, MessageRepository messageRepository) {
        this.accountService = accountService;
        this.accountRepository = accountRepository;
        this.messageService = messageService;
        this.messageRepository = messageRepository;
    }

    /**
     * Create a new account. Returns an HTTP Response Status of 409 if username already
     * exists within the database and 400 for other errors.
     * 
     * @param account account to be posted
     * @return the created account
     */
    @PostMapping("/register")
    public ResponseEntity<?> postAccountToRegister(@RequestBody Account account){

        //Check for duplicate username - Http 409
        if (accountRepository.findAccountByUsername(account.getUsername()) != null) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }

        //Attempt to add the account to the database
        Account addedAccount = accountService.addAccount(account);

        //Check if account creation failed - Http 400
        if(addedAccount == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        //Return the account
        return new ResponseEntity<>(account, HttpStatus.OK);
    }

    /**
     * Validate the account credentials to the login endpoint
     * 
     * @param account account to verify
     * @return The account with account id if successfully verified, Http response of 401 otherwise 
     */
    @PostMapping("/login")
    public ResponseEntity<?> postAccountToLogin(@RequestBody Account account) {

        Account valAccount = accountService.validateAccount(account);

        //Validate the account credentnials
        if (valAccount != null) {
            return new ResponseEntity<>(valAccount, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    // 3. post message to messages
    /**
     * Post a message to the messages endpoint
     * 
     * @param message message to be posted
     * @return The message if successfully posted, Http response of 400 otherwise
     */
    @PostMapping("/messages")
    public ResponseEntity<?> postNewMessage(@RequestBody Message message) {
        Message postMessage = messageService.createMessage(message);

        //Check if message was successfully posted
        if(postMessage != null) {
            return new ResponseEntity<>(postMessage, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    // 4. get message from messages
    /**
     * Gets all messages from the database
     * 
     * @return All messages as a list
     */
    @GetMapping("/messages")
    public List<Message> getAllMessages() {
        return messageService.retrieveAllMessages();
    }

    /**
     * Retrieves a message by its id
     * 
     * @param messageId Id of the message to be retrieved
     * @return The message
     */
    @GetMapping("/messages/{messageId}")
    public Message getMessageById(@PathVariable int messageId) {
        return messageService.retrieveMessageById(messageId);
    }

    /**
     * Deletes a message, determined by a message id
     * 
     * @param messageId Id of the message to be deleted
     * @return The number of rows deleted (1), or an empty response if no messages were deleted
     */
    @DeleteMapping("/messages/{messageId}")
    public ResponseEntity<?> deleteMessageById(@PathVariable int messageId) {
        //
        if (messageService.deleteMessageFromId(messageId) == 1) {
            return new ResponseEntity<>(1, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * Update the text body of a particular message 
     * 
     * @param messageId Id of message to patch
     * @param jSonString Message text body, formatted as a Json body
     * @return The number of rows updated (max of 1), or a Http 400 error if unsuccessful
     * @throws JsonProcessingException
     */
    @PatchMapping("messages/{messageId}")
    public ResponseEntity<?> patchMessage(@PathVariable int messageId, @RequestBody String jSonString) 
        throws JsonProcessingException {
        
        //Retrieve Message text from request body
        ObjectMapper mapper = new JsonMapper();
        JsonNode json = mapper.readTree(jSonString);
        String messageText = json.get("messageText").asText();
        
        //Attempt to patch the message
        if (messageService.patchMessageFromId(messageId, messageText) == 1) {
            return new ResponseEntity<>(1, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    // 8. get message by accountId from accounts/{accountId}/messages
    @GetMapping("accounts/{accountId}/messages")
    public List<Message> getAllMessagesByAccountId(@PathVariable int accountId) {
        return messageService.retrievMessagesByAccountId(accountId);
    }
}
