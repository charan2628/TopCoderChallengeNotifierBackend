package com.app.converters;

import java.lang.invoke.MethodHandles;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.convert.converter.Converter;

import com.app.util.ChallengeType;

public class ChallengeTypeConverter implements Converter<String, ChallengeType>{

    private final static Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @Override
    public ChallengeType convert(String source) {
        logger.debug("Converting {} to CHALLENGE_TYPE", source);
        return ChallengeType.valueOf(source);
    }

}
