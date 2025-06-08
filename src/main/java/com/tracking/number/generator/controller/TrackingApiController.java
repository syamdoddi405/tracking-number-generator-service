package com.tracking.number.generator.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.tracking.number.generator.api.model.TrackingInfo;
import com.tracking.number.generator.builder.TrackingCriteriaSearchBuilder;
import com.tracking.number.generator.service.TrackingService;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class TrackingApiController implements TrackingApi{
	
	@Autowired
	private TrackingService trackingService;

    @Override
    public ResponseEntity<TrackingInfo> generateTrackingNumber(
    		String originCountryId,
    		String destinationCountryId,
    		String weight,
    		String createdAt,
    		String customerId, 
    		String customerName, 
    		String customerSlug) {
		log.info(
				"Starting the generateTrackingNumber(), originCountryId{}, destinationCountryId{}, weight{}, createdAt{}, customerId{}, customerName{}, customerSlug{}",
				originCountryId, destinationCountryId, weight, createdAt, customerId, customerName, customerSlug);
    	try {
    		TrackingCriteriaSearchBuilder criteriaSearchBuilder = new TrackingCriteriaSearchBuilder.Builder()
        	        .originCountryId(originCountryId)
        	        .destinationCountryId(destinationCountryId)
        	        .weight(weight)
        	        .createdAt(createdAt)
    				.customerId(customerId != null ? UUID.fromString(customerId) : null)
        	        .customerName(customerName)
        	        .customerSlug(customerSlug)
        	        .build();

            TrackingInfo response = trackingService.generateTrackingNumber(criteriaSearchBuilder);
			log.debug("Successfully generated the tracking number response{}", response);
            return ResponseEntity.ok(response);
		} catch (Exception e) {
			log.error("generateTrackingNumber(),An error occured while generating the tracker number {}", e);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);

		}
    }
}
