package com.tracking.number.generator.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.tracking.number.generator.data.model.TrackingData;

@Repository
public interface TrackingNumberRepository extends MongoRepository<TrackingData, String> {

}
