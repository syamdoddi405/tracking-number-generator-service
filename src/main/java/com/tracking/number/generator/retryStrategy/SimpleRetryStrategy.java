package com.tracking.number.generator.retryStrategy;

import java.util.function.Supplier;

import org.springframework.stereotype.Component;

import com.tracking.number.generator.data.DuplicateTrackingNumberException;

@Component
public class SimpleRetryStrategy implements TrackingRetryStrategy {

    @Override
    public <T> T execute(Supplier<T> operation, int maxAttempts) {
        int attempts = 0;
        while (attempts < maxAttempts) {
            try {
                return operation.get();
            } catch (DuplicateTrackingNumberException e) {
                attempts++;
                if (attempts >= maxAttempts) throw e;
            }
        }
        throw new IllegalStateException("Unreachable code in retry strategy");
    }
}