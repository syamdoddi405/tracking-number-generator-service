package com.tracking.number.generator.api.model;

import java.util.UUID;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TrackingResponse {
    private String trackingNumber;
    private String createdAt;
    private String originCountryId;
	private String destinationCountryId;
	private String weight;
	private UUID customerId;
	private String customerName;
	private String customerSlug;
	
	public TrackingResponse(String trackingNumber, String createdAt) {
		super();
		this.trackingNumber = trackingNumber;
		this.createdAt = createdAt;
	}

	
	
	
}