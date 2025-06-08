package com.tracking.number.generator.service;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tracking.number.generator.api.model.TrackingInfo;
import com.tracking.number.generator.builder.TrackingCriteriaSearchBuilder;
import com.tracking.number.generator.builder.TrackingInfoBuilder;
import com.tracking.number.generator.data.DuplicateTrackingNumberException;
import com.tracking.number.generator.data.model.TrackingData;
import com.tracking.number.generator.repository.TrackingNumberRepository;
import com.tracking.number.generator.retryStrategy.TrackingRetryStrategy;
import com.tracking.number.generator.util.TrackingNumberGenerator;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class TrackingServiceImpl implements TrackingService {

	@Autowired
	private TrackingNumberRepository trackingNumberRepository;

	private static final int MAX_RETRY_ATTEMPTS = 3;

	@Autowired
	private TrackingRetryStrategy retryStrategy;

	@Override
	public TrackingInfo generateTrackingNumber(TrackingCriteriaSearchBuilder trackingCriteriaSearchBuilder) {
		log.info("Start generateTrackingNumber, params={}", trackingCriteriaSearchBuilder);

		return retryStrategy.execute(() -> {
			String trackingNumber = TrackingNumberGenerator.generateTrackingNumber(16);
			validateUniqueTrackingNumber(trackingNumber);
			String createdAt = OffsetDateTime.now(ZoneOffset.UTC).format(DateTimeFormatter.ISO_OFFSET_DATE_TIME);

			TrackingData trackingData = new TrackingData(trackingNumber, createdAt);
			trackingData = trackingNumberRepository.save(trackingData);

			log.debug("Generated tracking number={} ", trackingNumber);
			return TrackingInfoBuilder.build(trackingCriteriaSearchBuilder, trackingData);
		}, MAX_RETRY_ATTEMPTS);
	}

	private void validateUniqueTrackingNumber(String trackingNumber) {
		if (trackingNumberRepository.existsById(trackingNumber)) {
			throw new DuplicateTrackingNumberException("Tracking number already exists: " + trackingNumber);
		}
	}

}
