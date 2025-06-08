package com.tracking.number.generator.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.tracking.number.generator.api.model.TrackingInfo;

public interface TrackingApi {

    @GetMapping(value="/next-tracking-number")
	ResponseEntity<TrackingInfo> generateTrackingNumber(
			@RequestParam(value="originCountryId",required = false) String originCountryId,
			@RequestParam(value="destinationCountryId", required = false) String destinationCountryId,
			@RequestParam(value="weight", required = false) String weight,
			@RequestParam(value="createdAt", required = false) String createdAt,
			@RequestParam(value="customerId", required = false) String customerId,
			@RequestParam(value="customerName", required = false) String customerName,
			@RequestParam(value="customerSlug", required = false) String customerSlug
);

}



