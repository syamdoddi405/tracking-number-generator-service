package com.tracking.number.generator.data.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@NoArgsConstructor
@Document(collection = "tracking_info")
public class TrackingData {
	
	@Id
	@NotBlank
	@Size(max = 16)
	@JsonProperty("trackingNumber")
	private String trackingNumber;
	
	@NotBlank
	@JsonProperty("createdAt")
	private String createdAt;

	public TrackingData(@NotBlank @Size(max = 16) String trackingNumber, @NotBlank String createdAt) {
		super();
		this.trackingNumber = trackingNumber;
		this.createdAt = createdAt;
	}
	
	

}