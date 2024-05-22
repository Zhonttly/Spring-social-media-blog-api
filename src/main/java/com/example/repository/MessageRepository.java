package com.example.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.entity.Message;

public interface MessageRepository extends JpaRepository<Message, Integer> {

    /**
     * Searches the message database for a message through its ID
     * 
     * @param messageId the ID of the message to search for 
     * @return the message if found
     */
    public Message findMessageByMessageId(int messageId);

    public List<Message> findMessageByPostedBy(int postedBy);


} 
