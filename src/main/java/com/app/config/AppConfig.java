package com.app.config;

import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

import java.lang.invoke.MethodHandles;

import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.app.converters.ChallengeTypeConverter;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoClientSettings;

@Configuration
@ComponentScan(basePackages = {"com.app"})
public class AppConfig implements WebMvcConfigurer{
	
	private final static Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

	@Bean
	public MongoClient mongoClient() {
		CodecRegistry pojoCodecRegistry = fromRegistries(MongoClientSettings.getDefaultCodecRegistry(),
                fromProviders(PojoCodecProvider.builder().automatic(true).build()));
		MongoClientOptions options = MongoClientOptions.builder().codecRegistry(pojoCodecRegistry).build();
		MongoClient mongoClient = new MongoClient("localhost:27017", options);
		logger.info("Mongo Client created successfully");
		return mongoClient;
	}
	
	@Override
	public void addFormatters(FormatterRegistry registry) {
		registry.addConverter(new ChallengeTypeConverter());
		logger.debug("Adding Custom Converters");
	}
}
