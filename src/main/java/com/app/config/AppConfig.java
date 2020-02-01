package com.app.config;

import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

import java.lang.invoke.MethodHandles;

import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.app.converters.ChallengeTypeConverter;
import com.app.interceptors.AuthInterceptor;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoClientSettings;

/**
 * Main Application configuration class.
 *
 * @author charan2628
 *
 */
@Configuration
@EnableAsync
@ComponentScan(basePackages = {"com.app"})
public class AppConfig implements WebMvcConfigurer {

    /**
     * Logger.
     */
    private static final Logger LOGGER = LoggerFactory
            .getLogger(MethodHandles.lookup().lookupClass());

    /**
     * Creates MongoClient configures it with Codec for POJO.
     *
     * @param host
     *        Database host
     * @param port
     *        Database port
     * @return MongoClient
     */
    @Bean
    public MongoClient mongoClient(
            @Value("${db.host}") final String host,
            @Value("${db.port}") final String port) {

        String url = String.format("%s:%s", host, port);

        CodecRegistry pojoCodecRegistry = fromRegistries(
                MongoClientSettings.getDefaultCodecRegistry(),
                fromProviders(PojoCodecProvider.builder()
                        .automatic(true)
                        .build()));
        MongoClientOptions options = MongoClientOptions.builder()
                .codecRegistry(pojoCodecRegistry)
                .build();
        MongoClient mongoClient = new MongoClient(url, options);

        LOGGER.info("Mongo Client created successfully");
        return mongoClient;
    }

    /**
     * Creates AuthInterceptor and registers it as Spring bean.
     *
     * @return AuthInterceptor
     */
    @Bean
    public AuthInterceptor authInterceptor() {
        return new AuthInterceptor();
    }
    
    @Bean("asnc_executor")
    public ThreadPoolTaskExecutor threadPoolTaskExecutor() {
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        taskExecutor.setMaxPoolSize(5);
        taskExecutor.setAwaitTerminationSeconds(5);
        taskExecutor.setDaemon(true);
        return taskExecutor;
    }

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new ChallengeTypeConverter());
        LOGGER.debug("Adding Custom Converters");
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authInterceptor())
            .addPathPatterns("/**")
            .excludePathPatterns("/login/**", "/error/**");
    }
}
