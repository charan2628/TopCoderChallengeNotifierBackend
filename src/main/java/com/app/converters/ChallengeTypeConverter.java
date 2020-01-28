package com.app.converters;

import java.lang.invoke.MethodHandles;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.convert.converter.Converter;

import com.app.util.CHALLENGE_TYPE;

public class ChallengeTypeConverter implements Converter<String, CHALLENGE_TYPE>{

	private final static Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

	@Override
	public CHALLENGE_TYPE convert(String source) {
		logger.debug("Converting {} to CHALLENGE_TYPE", source);
		return CHALLENGE_TYPE.valueOf(source);
	}

}
