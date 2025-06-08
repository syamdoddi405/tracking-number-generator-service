package com.tracking.number.generator.data;

public class DuplicateTrackingNumberException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public DuplicateTrackingNumberException(String message, Throwable cause) {
		super(message, cause);
	}

	public DuplicateTrackingNumberException(String message) {
		super(message);
	}

}
