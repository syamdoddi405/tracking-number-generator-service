package com.tracking.number.generator.builder;

import com.tracking.number.generator.api.model.TrackingInfo;
import com.tracking.number.generator.data.model.TrackingData;

public class TrackingInfoBuilder {

    public static TrackingInfo build(TrackingCriteriaSearchBuilder params, TrackingData trackingData) {
        TrackingInfo info = new TrackingInfo(trackingData.getTrackingNumber(), trackingData.getCreatedAt());

        if (params.getCustomerId() != null) info.setCustomerId(params.getCustomerId());
        if (params.getCustomerName() != null) info.setCustomerName(params.getCustomerName());
        if (params.getCustomerSlug() != null) info.setCustomerSlug(params.getCustomerSlug());
        if (params.getDestinationCountryId() != null) info.setDestinationCountryId(params.getDestinationCountryId());
        if (params.getOriginCountryId() != null) info.setOriginCountryId(params.getOriginCountryId());
        if (params.getWeight() != null) info.setWeight(params.getWeight());

        return info;
    }
}
