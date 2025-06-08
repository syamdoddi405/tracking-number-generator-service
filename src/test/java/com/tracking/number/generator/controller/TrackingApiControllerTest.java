package com.tracking.number.generator.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.time.OffsetDateTime;
import java.util.UUID;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.annotation.RequestParam;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.tracking.number.generator.api.model.TrackingInfo;
import com.tracking.number.generator.builder.TrackingCriteriaSearchBuilder;
import com.tracking.number.generator.data.DuplicateTrackingNumberException;
import com.tracking.number.generator.service.TrackingService;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
@AutoConfigureMockMvc
public class TrackingApiControllerTest {

	ObjectMapper objectMapper = new ObjectMapper();

	private MockMvc mockMvc;

	@Mock
	private HttpServletRequest request;

	@InjectMocks
	TrackingApiController trackingApiController;

	@Mock
	TrackingService trackingService;

	@BeforeEach
	void setup() {
		log.trace("init()");
		this.mockMvc = MockMvcBuilders.standaloneSetup(trackingApiController)
				.setControllerAdvice(TrackingExceptionHandler.class).build();
		MockitoAnnotations.openMocks(this);

		objectMapper = Jackson2ObjectMapperBuilder.json().modules(new JavaTimeModule())
				.featuresToDisable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS,
						DeserializationFeature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE)
				.build();

	}

	@Test
	public void testGenerateTrackingNumber() throws Exception {
		TrackingInfo info = new TrackingInfo("4B5BZJZ6NDBK7XSF", OffsetDateTime.now().toString());

		Mockito.when(trackingService.generateTrackingNumber(any())).thenReturn(info);
		MvcResult result = mockMvc
				.perform(MockMvcRequestBuilders.get("/next-tracking-number")
						.contentType(MediaType.APPLICATION_JSON_VALUE).accept(MediaType.APPLICATION_JSON_VALUE))
				.andReturn();

		Assertions.assertEquals(200, result.getResponse().getStatus());
		verify(trackingService, times(1)).generateTrackingNumber(any());
	}

	@Test
	public void testGenerateTrackingNumber_withRequestParameters() throws Exception {
		TrackingInfo info = new TrackingInfo("4B5BZJZ6NDBK7XSF", OffsetDateTime.now().toString());

		Mockito.when(trackingService.generateTrackingNumber(any())).thenReturn(info);
		String originCountryId = "IN";
		String destinationCountryId = "IN";
		String weight = "2.4KG";
		String customerId = UUID.randomUUID().toString();
		String customerName = "Syam Doddi";
		String customerSlug = "redbox";
		MvcResult result = mockMvc
				.perform(MockMvcRequestBuilders.get("/next-tracking-number").param("originCountryId", originCountryId)
						.param("destinationCountryId", destinationCountryId).param("weight", weight)
						.param("customerName", customerName).param("customerId", customerId)
						.param("customerSlug", customerSlug)

						.contentType(MediaType.APPLICATION_JSON_VALUE).accept(MediaType.APPLICATION_JSON_VALUE))
				.andReturn();

		Assertions.assertEquals(200, result.getResponse().getStatus());
		verify(trackingService, times(1)).generateTrackingNumber(any());
	}

	@Test
	public void testGenerateTrackingNumber_500_Internal_Server_Error() throws Exception {

		Mockito.when(trackingService.generateTrackingNumber(any()))
				.thenThrow(new DuplicateTrackingNumberException("Error occured while generating the tracking number"));
		MvcResult result = mockMvc
				.perform(MockMvcRequestBuilders.get("/next-tracking-number")
						.contentType(MediaType.APPLICATION_JSON_VALUE).accept(MediaType.APPLICATION_JSON_VALUE))
				.andReturn();

		Assertions.assertEquals(500, result.getResponse().getStatus());
		verify(trackingService, times(1)).generateTrackingNumber(any());
	}
}
