package com.app.controller;

import java.lang.invoke.MethodHandles;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.model.Challenge;
import com.app.service.ChallengeService;

/**
 * Controller for /challenge requests.
 *
 * @author charan2628
 *
 */
@CrossOrigin
@RestController
@RequestMapping("/challenges")
public class ChallengeController {

    /**
     * Logger.
     */
    private static final Logger LOGGER = LoggerFactory
            .getLogger(MethodHandles.lookup().lookupClass());

    /**
     * ChallengeService.
     */
    private final ChallengeService challengeService;

    /**
     *
     * @param challengeService
     */
    public ChallengeController(@Autowired final ChallengeService challengeService) {
        this.challengeService = challengeService;
    }

    /**
     * GET request mapping.
     *
     * @return list of challenges saved in db
     */
    @GetMapping(
            path = "",
            produces = "application/json")
    public List<Challenge> getChallenges() {
        LOGGER.info("GET /challenges");
        return this.challengeService.getChallenges();
    }

    /**
     * DELETE request mapping.
     */
//    @DeleteMapping(
//            path = "",
//            produces = "application/json")
//    @ResponseStatus(code = HttpStatus.OK)
    public void deleteOldChallenges() {
        LOGGER.info("DELETE /challenges");
        this.challengeService.deleteAll();
    }

}
