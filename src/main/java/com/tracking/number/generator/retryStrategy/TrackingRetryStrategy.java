package com.tracking.number.generator.retryStrategy;

import java.util.function.Supplier;

public interface TrackingRetryStrategy {

	<T> T execute(Supplier<T> operation, int maxAttempts);
}
