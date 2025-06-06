package com.tracking.number.generator.util;

import java.security.SecureRandom;

import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class TrackingNumberGenerator {

	private static final SecureRandom random = new SecureRandom();
    private static final String CHAR_POOL = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

 
    public static String generateTrackingNumber(int length) {
        if (length <= 0 || length > 16) {
            throw new IllegalArgumentException("Length must be between 1 and 16");
        }

        StringBuilder trackingNumber = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            int index = random.nextInt(CHAR_POOL.length());
            trackingNumber.append(CHAR_POOL.charAt(index));
        }
		log.debug("TrackingNumberGenerator.generateTrackingNumber(), Successfully generated the trackingNumber{} ", trackingNumber.toString());

        return trackingNumber.toString();
    }
}
