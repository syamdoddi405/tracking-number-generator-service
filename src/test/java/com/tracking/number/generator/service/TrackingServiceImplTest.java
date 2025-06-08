package com.tracking.number.generator.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Supplier;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.tracking.number.generator.api.model.TrackingInfo;
import com.tracking.number.generator.builder.TrackingCriteriaSearchBuilder;
import com.tracking.number.generator.data.DuplicateTrackingNumberException;
import com.tracking.number.generator.data.model.TrackingData;
import com.tracking.number.generator.repository.TrackingNumberRepository;
import com.tracking.number.generator.retryStrategy.TrackingRetryStrategy;
import com.tracking.number.generator.util.TrackingNumberGenerator;

@ExtendWith(SpringExtension.class)
public class TrackingServiceImplTest {

	@InjectMocks
	TrackingServiceImpl trackingServiceImpl;

	@Mock
	TrackingRetryStrategy retryStrategy;

	@Mock
	TrackingNumberRepository trackingNumberRepository;

	@BeforeEach
	void init() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	public void generateTrackingNumberTest() {

		Mockito.when(retryStrategy.execute(any(), anyInt())).thenAnswer(invocation -> {
			Supplier<TrackingInfo> supplier = invocation.getArgument(0);
			return supplier.get();
		});
		String trackingNumber = TrackingNumberGenerator.generateTrackingNumber(16);

		Mockito.when(trackingNumberRepository.existsById(anyString())).thenReturn(false);

		TrackingData trackingData = new TrackingData(trackingNumber, OffsetDateTime.now().toString());
		Mockito.when(trackingNumberRepository.save(any())).thenReturn(trackingData);

		TrackingCriteriaSearchBuilder criteriaSearchBuilder = new TrackingCriteriaSearchBuilder.Builder()
				.originCountryId("IN").destinationCountryId("IN").weight("12.1KG").createdAt(null)
				.customerId(UUID.randomUUID()).customerName("Syam Doddi").customerSlug("red-box").build();
		trackingServiceImpl.generateTrackingNumber(criteriaSearchBuilder);

		verify(trackingNumberRepository, times(1)).existsById(anyString());
		verify(trackingNumberRepository, times(1)).save(any());

	}

	@Test
	void testGenerateTrackingNumber_RetryOnDuplicate() {
		AtomicInteger attempt = new AtomicInteger(0);
		String createdAt = OffsetDateTime.now(ZoneOffset.UTC).format(DateTimeFormatter.ISO_OFFSET_DATE_TIME);

		Mockito.when(retryStrategy.execute(any(), anyInt())).thenAnswer(invocation -> {
			Supplier<TrackingInfo> supplier = invocation.getArgument(0);
			if (attempt.getAndIncrement() == 0) {
				throw new DuplicateTrackingNumberException("Duplicate");
			}
			return supplier.get();
		});

		Mockito.when(trackingNumberRepository.existsById(anyString())).thenReturn(true);
		Mockito.when(trackingNumberRepository.save(any())).thenReturn(new TrackingData("TRACK123", createdAt));

		TrackingCriteriaSearchBuilder criteriaSearchBuilder = new TrackingCriteriaSearchBuilder.Builder()
				.originCountryId("IN").destinationCountryId("IN").weight("12.1KG").createdAt(null)
				.customerId(UUID.randomUUID()).customerName("Syam Doddi").customerSlug("red-box").build();

		assertThrows(DuplicateTrackingNumberException.class, () -> {
			TrackingInfo result = trackingServiceImpl.generateTrackingNumber(criteriaSearchBuilder);
			assertNotNull(result);

		});
	}
	

    @Test
    void testGenerateTrackingNumber_ThrowsAfterMaxRetry() {
        Mockito.when(retryStrategy.execute(any(), anyInt()))
                .thenThrow(new DuplicateTrackingNumberException("Max retries exceeded"));
        
		TrackingCriteriaSearchBuilder criteriaSearchBuilder = new TrackingCriteriaSearchBuilder.Builder()
				.originCountryId("IN").destinationCountryId("IN").weight("12.1KG").createdAt(null)
				.customerId(UUID.randomUUID()).customerName("Syam Doddi").customerSlug("red-box").build();

        assertThrows(DuplicateTrackingNumberException.class, () -> {
        	trackingServiceImpl.generateTrackingNumber(criteriaSearchBuilder);
        });
    }
}
