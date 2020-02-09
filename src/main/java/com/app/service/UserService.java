package com.app.service;

import java.lang.invoke.MethodHandles;
import java.time.LocalDateTime;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.app.dao.UserDao;
import com.app.exception.UnAuthorizedException;
import com.app.model.User;
import com.app.util.MailSubject;

@Service
public class UserService {

    private static final Logger LOGGER = LoggerFactory
            .getLogger(MethodHandles.lookup().lookupClass());

    private UserDao userDao;
    private MailService mailService;
    private StatusService statusService;
    private ErrorLogService errorLogService;

    public UserService(
            UserDao userDao,
            MailService mailService,
            StatusService statusService, 
            ErrorLogService errorLogService) {
        super();
        this.userDao = userDao;
        this.statusService = statusService;
        this.mailService = mailService;
        this.errorLogService = errorLogService;
    }
    
    public User getUser(String email) {
        try {
            return this.userDao.getUser(email);
        } catch (Exception e) {
            LOGGER.error("Error getting user: {} {}", email, e);
            this.errorLogService.addErrorLog(
                    String.format("Error getting user %s",
                            LocalDateTime.now().toString()));
            this.statusService.error();
            throw e;
        }
    }

    public void addUser(User user) {
        try {
            String cfrmCode = String.valueOf(Math.abs(new Random().nextInt(10000)));
            user.setConfirmToken(cfrmCode);
            this.userDao.addUser(user);
            this.mailService.confirmRegistration(user.getEmail(), cfrmCode)
                .send(MailSubject.CONFORM_REGISTRATION, user.getEmail());
        } catch (Exception e) {
            LOGGER.error("Error adding user: {} {}", user, e);
            this.errorLogService.addErrorLog(
                    String.format("Error adding user %s",
                            LocalDateTime.now().toString()));
            this.statusService.error();
            throw e;
        }
        LOGGER.debug("Added user {}", user);
    }
    
    public boolean isConfirmed(String email) {
        try {
            User user = this.userDao.getUser(email);
            return user.isConfirmed();
        } catch (Exception e) {
            LOGGER.error("Error checking confirm status user: {} {}", email, e);
            this.errorLogService.addErrorLog(
                    String.format("Error checking confirm status user %s",
                            LocalDateTime.now().toString()));
            this.statusService.error();
            throw e;
        }
    }
    
    public boolean confirmUser(String email, String code) {
        try {
            User user = this.getUser(email);
            if(user == null) {
                throw new UnAuthorizedException();
            }
            if(!user.getConfirmToken().equals(code)) {
                return false;
            }
            this.userDao.confirmUser(user.getId());
            return true;
        } catch (Exception e) {
            LOGGER.error("Error confirming user: {} {}", email, e);
            this.errorLogService.addErrorLog(
                    String.format("Error confirming user %s",
                            LocalDateTime.now().toString()));
            this.statusService.error();
            throw e;
        }
    }
    
    public boolean isUserPresent(String email) {
        try {
            return this.userDao.isUserPresent(email);
        } catch (Exception e) {
            LOGGER.error("Error checking user present: {} {}", email, e);
            this.errorLogService.addErrorLog(
                    String.format("Error checking user present %s",
                            LocalDateTime.now().toString()));
            this.statusService.error();
            throw e;
        }
    }

    void _deleteAll() {
        this.userDao._deleteAll();
    }
}
