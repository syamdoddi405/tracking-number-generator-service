package com.tracking.number.generator.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.tracking.number.generator.builder.TrackingParamsBuilder;
import com.tracking.number.generator.model.TrackingResponse;
import com.tracking.number.generator.service.TrackingService;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class TrackingController implements TrackingControllerApi{
	@Autowired
	private TrackingService trackingService;

    @Override
    public ResponseEntity<TrackingResponse> getNextTrackingNumber(
    		String originCountryId,
    		String destinationCountryId,
    		String weight,
    		String createdAt,
    		String customerId, 
    		String customerName, 
    		String customerSlug) {
		log.info(
				"Starting the getNextTrackingNumber(), originCountryId{}, destinationCountryId{}, weight{}, createdAt{}, customerId{}, customerName{}, customerSlug{}",
				originCountryId, destinationCountryId, weight, createdAt, customerId, customerName, customerSlug);
    	try {
    		TrackingParamsBuilder params = new TrackingParamsBuilder.Builder()
        	        .originCountryId(originCountryId)
        	        .destinationCountryId(destinationCountryId)
        	        .weight(weight)
        	        .createdAt(createdAt)
    				.customerId(customerId != null ? UUID.fromString(customerId) : null)
        	        .customerName(customerName)
        	        .customerSlug(customerSlug)
        	        .build();

            TrackingResponse response = trackingService.generateTrackingNumber(params);
			log.debug("Successfully generated the tracking number response{}", response);
            return ResponseEntity.ok(response);
		} catch (Exception e) {
			log.debug("getNextTrackingNumber(),An error occured while generating the tracker number {}", e);
			throw e;
		}
    }
}
