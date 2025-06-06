package com.tracking.number.generator.service;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

import org.springframework.stereotype.Service;

import com.tracking.number.generator.builder.TrackingParamsBuilder;
import com.tracking.number.generator.model.TrackingResponse;
import com.tracking.number.generator.util.TrackingNumberGenerator;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class TrackingServiceImpl implements TrackingService {

	private final ConcurrentHashMap<String, Boolean> generatedNumbers = new ConcurrentHashMap<>();

	@Override
	public TrackingResponse generateTrackingNumber(TrackingParamsBuilder params) {
		log.info("TrackingServiceImpl.generateTrackingNumber(), Start of generateTrackingNumber(), params{}", params);

		String trackingNumber;
		do {
			trackingNumber = TrackingNumberGenerator.generateTrackingNumber(16);
			log.debug("TrackingServiceImpl.generateTrackingNumber(), The trackingNumber number has been generated{}", trackingNumber);

		} while (generatedNumbers.putIfAbsent(trackingNumber, true) != null);

		String createdAt = OffsetDateTime.now(ZoneOffset.UTC).format(DateTimeFormatter.ISO_OFFSET_DATE_TIME);
		TrackingResponse trackingResponse = new TrackingResponse(trackingNumber, createdAt);
		
		  // Set fields only if present
		if (params.getCustomerId() != null) {
			trackingResponse.setCustomerId(params.getCustomerId());
		}
	    setIfNotNull(params.getCustomerName(), trackingResponse::setCustomerName);
	    setIfNotNull(params.getCustomerSlug(), trackingResponse::setCustomerSlug);
	    setIfNotNull(params.getDestinationCountryId(), trackingResponse::setDestinationCountryId);
	    setIfNotNull(params.getOriginCountryId(), trackingResponse::setOriginCountryId);
	    setIfNotNull(params.getWeight(), trackingResponse::setWeight);

		return trackingResponse;
	}
	
	private <T> void setIfNotNull(T value, Consumer<T> setter) {
	    if (value != null) {
	        setter.accept(value);
	    }
	}
	

}
