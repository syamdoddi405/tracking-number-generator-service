package com.tracking.number.generator.builder;

import java.util.UUID;

public class TrackingParamsBuilder {
	private String originCountryId;
	private String destinationCountryId;
	private String weight;
	private String createdAt;
	private UUID customerId;
	private String customerName;
	private String customerSlug;

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

	// Optional: toString()
	@Override
	public String toString() {
		return "TrackingParams{" + "originCountryId='" + originCountryId + '\'' + ", destinationCountryId='"
				+ destinationCountryId + '\'' + ", weight='" + weight + '\'' + ", createdAt='" + createdAt + '\''
				+ ", customerId='" + customerId + '\'' + ", customerName='" + customerName + '\'' + ", customerSlug='"
				+ customerSlug + '\'' + '}';
	}

	// Builder inner class
	public static class Builder {
		private final TrackingParamsBuilder params = new TrackingParamsBuilder();

		public Builder originCountryId(String originCountryId) {
			params.originCountryId = originCountryId;
			return this;
		}

		public Builder destinationCountryId(String destinationCountryId) {
			params.destinationCountryId = destinationCountryId;
			return this;
		}

		public Builder weight(String weight) {
			params.weight = weight;
			return this;
		}

		public Builder createdAt(String createdAt) {
			params.createdAt = createdAt;
			return this;
		}

		public Builder customerId(UUID customerId) {
			params.customerId = customerId;
			return this;
		}

		public Builder customerName(String customerName) {
			params.customerName = customerName;
			return this;
		}

		public Builder customerSlug(String customerSlug) {
			params.customerSlug = customerSlug;
			return this;
		}

		public TrackingParamsBuilder build() {
			return params;
		}
	}

}
