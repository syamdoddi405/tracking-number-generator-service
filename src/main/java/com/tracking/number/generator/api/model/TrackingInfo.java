package com.tracking.number.generator.api.model;

import java.util.UUID;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@EqualsAndHashCode
@NoArgsConstructor
public class TrackingInfo {
	private String trackingNumber;
	private String createdAt;
	private String originCountryId;
	private String destinationCountryId;
	private String weight;
	private UUID customerId;
	private String customerName;
	private String customerSlug;

	public TrackingInfo(String trackingNumber, String createdAt) {
		super();
		this.trackingNumber = trackingNumber;
		this.createdAt = createdAt;
	}

}