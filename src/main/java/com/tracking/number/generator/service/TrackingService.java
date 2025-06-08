package com.tracking.number.generator.service;

import com.tracking.number.generator.api.model.TrackingResponse;
import com.tracking.number.generator.builder.TrackingParamsBuilder;

public interface TrackingService {

	TrackingResponse generateTrackingNumber(TrackingParamsBuilder params);

}
