package com.app.service;

import java.lang.invoke.MethodHandles;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.app.dao.UserConfigDao;
import com.app.model.UserConfig;
import com.app.util.AppUtil;

@Service
public class UserConfigService {

    private static final Logger LOGGER = LoggerFactory
            .getLogger(MethodHandles.lookup().lookupClass());

    private UserConfigDao userConfigDao;
    private StatusService statusService;
    private ErrorLogService errorLogService;

    public UserConfigService(UserConfigDao userConfigDao,
            StatusService statusService,
            ErrorLogService errorLogService) {
        super();
        this.userConfigDao = userConfigDao;
        this.statusService = statusService;
        this.errorLogService = errorLogService;
    }

    public UserConfig getUserConfig(String email) {
        try {
            return this.userConfigDao.getUserConfig(email);
        } catch (Exception e) {
            LOGGER.error("Error getting userconfig: {} {}", email, e);
            this.errorLogService.addErrorLog(
                    String.format("Error getting userconfig %s",
                            LocalDateTime.now().toString()));
            this.statusService.error();
            throw e;
        }
    }

    public void addUserConfig(UserConfig userConfig) {
        try {
           this.userConfigDao.addUserCOnfig(userConfig);
        } catch (Exception e) {
            LOGGER.error("Error adding userconfig: {} {}", userConfig, e);
            this.errorLogService.addErrorLog(
                    String.format("Error adding userconfig %s",
                            LocalDateTime.now().toString()));
            this.statusService.error();
            throw e;
        }
    }

    public void updateTags(String email, List<String> tags) {
        try {
            UserConfig userConfig = this.getUserConfig(email);
            tags = AppUtil.removeDups(tags);
            if(userConfig == null) {
                userConfig = new UserConfig(email, tags, 0, new ArrayList<String>());
                this.addUserConfig(userConfig);
            } else {
                tags.addAll(userConfig.getTags());
                this.userConfigDao.updateTags(email, tags);
            }
        } catch (Exception e) {
            LOGGER.error("Error updating userconfig tags: {} {}", email, e);
            this.errorLogService.addErrorLog(
                    String.format("Error updating userconfig tags %s",
                            LocalDateTime.now().toString()));
            this.statusService.error();
            throw e;
        }
    }

    public void updateSchedule(String email, long epochMilli) {
        try {
            this.userConfigDao.scheduleTime(email, epochMilli);
        } catch (Exception e) {
            LOGGER.error("Error updating userconfig schedule: {} {}", email, e);
            this.errorLogService.addErrorLog(
                    String.format("Error updating userconfig schedule %s",
                            LocalDateTime.now().toString()));
            this.statusService.error();
            throw e;
        }
    }

    public void addChallenges(String email, List<String> challenges) {
        try {
            this.userConfigDao.addChallenges(email, challenges);
        } catch (Exception e) {
            LOGGER.error("Error adding userconfig challenges: {} {}", email, e);
            this.errorLogService.addErrorLog(
                    String.format("Error adding userconfig challenges tags %s",
                            LocalDateTime.now().toString()));
            this.statusService.error();
            throw e;
        }
    }

    public void _deleteAll() {
        this.userConfigDao._deleteAll();
    }
}
