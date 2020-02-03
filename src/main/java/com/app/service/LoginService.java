package com.app.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.app.exception.UnConfirmedRegistrationExcpetion;
import com.app.model.Login;
import com.app.model.User;
import com.app.util.AppUtil;

@Service
public class LoginService {
    
    private UserService userService;
    private String salt;
    
    public LoginService(
            UserService userService,
            @Value("${SALT}") String salt) {
        this.userService = userService;
        this.salt = salt;
    }
    
    public boolean verifyLogin(Login login) throws Exception {
        login.setPassword(
                AppUtil.sha256(login.getPassword() + salt));
        User user = this.userService.getUser(login.getEmail());
        if(!user.isConfirmed()) {
            throw new UnConfirmedRegistrationExcpetion();
        }
        if(user.getPassword().equals(login.getPassword())) {
            return true;
        }
        return false;
    }
}
