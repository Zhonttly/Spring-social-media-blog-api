package com.example.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import com.example.entity.Account;
import com.example.entity.Message;
import com.example.service.AccountService;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller using Spring. The endpoints you will need can be
 * found in readme.md as well as the test cases. You be required to use the @GET/POST/PUT/DELETE/etc Mapping annotations
 * where applicable as well as the @ResponseBody and @PathVariable annotations. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
@RestController
public class SocialMediaController {

    AccountService accountService;

    public SocialMediaController(AccountService accountService) {
        this.accountService = accountService;
    }

    // 1. post account to register
    @GetMapping("/register/")
    public Account postAccount(@RequestBody Account account){
        System.out.println("*********INSIDE MY POST METHOD*********");
        return account;
    }
    // 2. post account to login

    // 3. post message to messages

    // 4. get message from messages

    // 5. get message by messageId from messages/{messageId}

    // 6. delete message by messageId from messages/{messageId}

    // 7. patch message by messageId from messages/{messageId}

    // 8. get message by accountId from accounts/{accountId}/messages
}
