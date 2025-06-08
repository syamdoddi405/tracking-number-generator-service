package com.tracking.number.generator.service;

import com.tracking.number.generator.api.model.TrackingInfo;
import com.tracking.number.generator.builder.TrackingCriteriaSearchBuilder;

public interface TrackingService {

	TrackingInfo generateTrackingNumber(TrackingCriteriaSearchBuilder params);

}
