package com.tracking.number.generator.builder;

import java.util.UUID;

public class TrackingCriteriaSearchBuilder {
	private String originCountryId;
	private String destinationCountryId;
	private String weight;
	private String createdAt;
	private UUID customerId;
	private String customerName;
	private String customerSlug;
	private String trackingNumber;

	// Getters
	public String getOriginCountryId() {
		return originCountryId;
	}

	public String getDestinationCountryId() {
		return destinationCountryId;
	}

	public String getWeight() {
		return weight;
	}

	public String getCreatedAt() {
		return createdAt;
	}

	public UUID getCustomerId() {
		return customerId;
	}

	public String getCustomerName() {
		return customerName;
	}

	public String getCustomerSlug() {
		return customerSlug;
	}
	

	public String getTrackingNumber() {
		return trackingNumber;
	}

	@Override
	public String toString() {
		return "TrackingParamsBuilder [originCountryId=" + originCountryId + ", destinationCountryId="
				+ destinationCountryId + ", weight=" + weight + ", createdAt=" + createdAt + ", customerId="
				+ customerId + ", customerName=" + customerName + ", customerSlug=" + customerSlug + ", trackingNumber="
				+ trackingNumber + "]";
	}

	// Builder inner class
	public static class Builder {
		private final TrackingCriteriaSearchBuilder trackingCriteriaSearchBuilder = new TrackingCriteriaSearchBuilder();

		public Builder originCountryId(String originCountryId) {
			trackingCriteriaSearchBuilder.originCountryId = originCountryId;
			return this;
		}

		public Builder destinationCountryId(String destinationCountryId) {
			trackingCriteriaSearchBuilder.destinationCountryId = destinationCountryId;
			return this;
		}

		public Builder weight(String weight) {
			trackingCriteriaSearchBuilder.weight = weight;
			return this;
		}

		public Builder createdAt(String createdAt) {
			trackingCriteriaSearchBuilder.createdAt = createdAt;
			return this;
		}

		public Builder customerId(UUID customerId) {
			trackingCriteriaSearchBuilder.customerId = customerId;
			return this;
		}

		public Builder customerName(String customerName) {
			trackingCriteriaSearchBuilder.customerName = customerName;
			return this;
		}

		public Builder customerSlug(String customerSlug) {
			trackingCriteriaSearchBuilder.customerSlug = customerSlug;
			return this;
		}
		
		public Builder trackingNumber(String trackingNumber) {
			trackingCriteriaSearchBuilder.trackingNumber = trackingNumber;
			return this;
		}

		public TrackingCriteriaSearchBuilder build() {
			return trackingCriteriaSearchBuilder;
		}
	}

}
