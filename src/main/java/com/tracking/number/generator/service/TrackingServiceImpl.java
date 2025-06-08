package com.tracking.number.generator.service;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.function.Consumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mongodb.DuplicateKeyException;
import com.tracking.number.generator.api.model.TrackingResponse;
import com.tracking.number.generator.builder.TrackingParamsBuilder;
import com.tracking.number.generator.data.model.TrackingData;
import com.tracking.number.generator.repository.TrackingNumberRepository;
import com.tracking.number.generator.util.TrackingNumberGenerator;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class TrackingServiceImpl implements TrackingService {

    @Autowired
    private TrackingNumberRepository trackingNumberRepository;

    private static final int MAX_ATTEMPTS = 5;
    

    @Override
    public TrackingResponse generateTrackingNumber(TrackingParamsBuilder params) {
        log.info("Start generateTrackingNumber, params={}", params);

        String trackingNumber = null;
        String createdAt = null;
        TrackingData trackingData = null;

        for (int attempt = 1; attempt <= MAX_ATTEMPTS; attempt++) {
            trackingNumber = TrackingNumberGenerator.generateTrackingNumber(16);
            createdAt = OffsetDateTime.now(ZoneOffset.UTC).format(DateTimeFormatter.ISO_OFFSET_DATE_TIME);
            try {
            	trackingData = new TrackingData(trackingNumber, createdAt);
            	trackingData = trackingNumberRepository.save(trackingData);
                log.debug("Generated tracking number={} on attempt {}", trackingNumber, attempt);
                break;
            } catch (DuplicateKeyException e) {
                log.error("Duplicate tracking number detected ({}). Retrying attempt {}/{}", trackingNumber, attempt, MAX_ATTEMPTS);
                if (attempt == MAX_ATTEMPTS) {
                    throw new IllegalStateException("Failed to generate unique tracking number after " + MAX_ATTEMPTS + " attempts", e);
                }
            }
        }

        
        TrackingResponse trackingResponse = new TrackingResponse(trackingNumber, createdAt);
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
