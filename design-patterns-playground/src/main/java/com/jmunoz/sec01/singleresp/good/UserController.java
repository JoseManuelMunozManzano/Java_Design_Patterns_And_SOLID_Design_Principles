package com.jmunoz.sec01.singleresp.good;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

// Handles incoming JSON requests that work on User
public class UserController {

    private final UserPersistenceService persistenceService = new UserPersistenceService();
    
    //Create a new user
    public String createUser(String userJson) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        User user = mapper.readValue(userJson, User.class);

        var validator = new UserValidator();
        var isValid = validator.validateUser(user);

        if(!isValid) {
            return "ERROR";
        }

        persistenceService.saveUser(user);
        
        return "SUCCESS";
    }
}