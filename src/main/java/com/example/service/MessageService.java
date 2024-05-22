package com.example.service;

import com.example.entity.Message;
import com.example.repository.MessageRepository;
import com.example.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * A service class for the Message entity using Spring framework
 */
@Service 
public class MessageService {

    MessageRepository messageRepository;
    AccountRepository accountRepository;

    @Autowired
    public MessageService(MessageRepository messageRepository, AccountRepository accountRepository) {
        this.messageRepository = messageRepository;
        this.accountRepository = accountRepository;
    }

    /**
     * Adds a given message to the message database. 
     * 
     * To successfully add a message, the message must be
     * 1. NOT blank
     * 2. LESS THAN 256 characters
     * 3. Posted by an EXISTING user
     * 
     * @param message The message entity to be created
     * @return The message with messageId if succesfully created, null otherwise
     */
    public Message createMessage(Message message) {
        if (accountRepository.findAccountByAccountId(message.getPostedBy()) != null
            && message.getMessageText().length() < 256
            && message.getMessageText().length() > 0) {
                return messageRepository.save(message);
        }

        return null;
    }

    /**
     * Retrieves all the messages in the database
     * 
     * @return All messages in the database in List form
     */
    public List<Message> retrieveAllMessages() {
        return messageRepository.findAll();
    }

    /**
     * Retrieves a message from the database using a message id
     * 
     * @param messageId Id of the message to be retrieved
     * @return the message
     */
    public Message retrieveMessageById(int messageId) {
        return messageRepository.findMessageByMessageId(messageId);
    }

    /**
     * Deletes a message from the database using a message id
     * 
     * @param messageId Id of the message to be deleted
     * @return number of rows deleted
     */
    public int deleteMessageFromId(int messageId) {

        Message message = messageRepository.findMessageByMessageId(messageId);
        
        //Check if message exists
        if (message != null) {
            //Delete the message from the database
            messageRepository.delete(message);
            return 1;
        }
        return 0;
    }

    /**
     * Updates the text body of a message in the database
     * 
     * A message is successfully updated if:
     * 1. Message body is NOT blank
     * 2. Message body is LESS THAN 256 characters
     * 
     * @param messageId Id of the message to be patched
     * @param messageText New message body for the existing message
     * @return number of rows affected
     */
    public int patchMessageFromId(int messageId, String messageText) {
        Message patchMessage = messageRepository.findMessageByMessageId(messageId);

        //Determine if the message body and original message's existence are valid
        if (patchMessage != null 
            && messageText != null
            && messageText.length() < 256 
            && messageText.length() > 1) {
                //Update the message with the new text body
                patchMessage.setMessageText(messageText);
                messageRepository.save(patchMessage);

                //Return the number of rows updated
                return 1;
        }

        return 0;
    }

    /**
     * Retrieve all of the messages from a particular account
     * 
     * @param accountId Id of the account to get messages from
     * @return All of the messages from the account, formatted as a List
     */
    public List<Message> retrievMessagesByAccountId(int accountId) {
        return messageRepository.findMessageByPostedBy(accountId);
    }
}
