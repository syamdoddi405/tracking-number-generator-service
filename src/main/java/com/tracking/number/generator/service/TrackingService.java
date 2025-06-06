package com.tracking.number.generator.service;

import com.tracking.number.generator.builder.TrackingParamsBuilder;
import com.tracking.number.generator.model.TrackingResponse;

public interface TrackingService {

	TrackingResponse generateTrackingNumber(TrackingParamsBuilder params);

}
