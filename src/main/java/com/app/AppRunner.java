package com.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.app.config.AppConfig;

/**
 * Main Application Runner class.
 *
 * @author charan2628
 *
 */
@SpringBootApplication
public class AppRunner {

    /**
     * Starts the application by using {@code SpringApplication.run()}.
     *
     * <p> Uses the command line parameters if provided.
     *
     * @param args
     *        The command line arguments
     */
    public static void main(String[] args) {
        SpringApplication.run(AppConfig.class, args);
    }
}
