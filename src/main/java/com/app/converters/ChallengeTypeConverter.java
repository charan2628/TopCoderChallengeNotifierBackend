package com.app.converters;

import org.springframework.core.convert.converter.Converter;

import com.app.util.CHALLENGE_TYPE;

public class ChallengeTypeConverter implements Converter<String, CHALLENGE_TYPE>{

	@Override
	public CHALLENGE_TYPE convert(String source) {
		return CHALLENGE_TYPE.valueOf(source);
	}

}
