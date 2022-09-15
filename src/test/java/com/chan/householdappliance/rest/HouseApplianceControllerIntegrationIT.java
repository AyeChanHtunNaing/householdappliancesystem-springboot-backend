package com.chan.householdappliance.rest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.OffsetDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.HttpClientErrorException;

import com.chan.householdappliance.HouseHoldApplianceApplication;
import com.chan.householdappliance.constant.Constant;
import com.chan.householdappliance.controller.HouseApplianceController;
import com.chan.householdappliance.dto.HouseApplianceDto;
import com.chan.householdappliance.enumtype.Status;
import com.chan.householdappliance.response.ApiResponse;
import com.google.gson.Gson;

import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;

@ContextConfiguration(classes = { HouseHoldApplianceApplication.class })
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class HouseApplianceControllerIntegrationIT {
		
	@InjectMocks
	HouseApplianceController houseApplianceController;
	
	@Autowired
	private TestRestTemplate restTemplate;


    @LocalServerPort
    private int port;

    private String getRootUrl() {
        return "http://localhost:" + port;
    }
    
    @Test
	public void contextLoads() {
	}
	
	@Test
	public void list() {
		Map<String, String> urlParams = new HashMap<>();
		urlParams.put("serialNumber", "");
		urlParams.put("brand", "");
		urlParams.put("model", "");
		urlParams.put("status", "");
		urlParams.put("boughtDate", "");
		
        ResponseEntity<ApiResponse> result = restTemplate.getForEntity(getRootUrl() + "/houseappliance/api/v1/list", ApiResponse.class, urlParams );
        assertNotNull(result.getBody());
        List<HouseApplianceDto> HouseApplianceDtos = (List<HouseApplianceDto>) result.getBody().getResult();
        assertTrue(HouseApplianceDtos.size() > 0);
	}
	
	@Test
	public void deleteById() {
		int id = 1;
		ResponseEntity<ApiResponse> result = restTemplate.getForEntity(getRootUrl() + "/houseappliance/api/v1/" + id, ApiResponse.class);
        assertNotNull(result.getBody());
        restTemplate.delete(getRootUrl() + "/houseappliance/api/v1/" + id);
        result = restTemplate.getForEntity(getRootUrl() + "/houseappliance/api/v1/" + id, ApiResponse.class);
        assertEquals(result.getStatusCode(), HttpStatus.NOT_FOUND);
	}
	
	@Test
	public void createHouseAppliance() throws URISyntaxException {
		HouseApplianceDto HouseApplianceDto = new HouseApplianceDto();
		HouseApplianceDto.setBoughtDate(OffsetDateTime.now());
		HouseApplianceDto.setBrand("ABC");
		HouseApplianceDto.setModel("A1");
		HouseApplianceDto.setSerialNumber("A123");
		HouseApplianceDto.setStatus(Status.NEW);
		RequestEntity request = RequestEntity.post(new URI(getRootUrl() + "/houseappliance/api/v1/")).accept(MediaType.APPLICATION_JSON).body(HouseApplianceDto);
		ResponseEntity<ApiResponse> response = restTemplate.exchange(request, ApiResponse.class);
		assertNotNull(response);
		assertEquals(Constant.OK, response.getBody().getCode());
	}
	
	@Test
	public void updateHouseAppliance() throws URISyntaxException {
		HouseApplianceDto HouseApplianceDto = new HouseApplianceDto();
		HouseApplianceDto.setBoughtDate(OffsetDateTime.now());
		HouseApplianceDto.setId(1L);
		HouseApplianceDto.setBrand("XYZ");
		HouseApplianceDto.setModel("Z1");
		HouseApplianceDto.setSerialNumber("Z123");
		HouseApplianceDto.setStatus(Status.OLD);
		RequestEntity request = RequestEntity.post(new URI(getRootUrl() + "/houseappliance/api/v1/")).accept(MediaType.APPLICATION_JSON).body(HouseApplianceDto);
		ResponseEntity<ApiResponse> response = restTemplate.exchange(request, ApiResponse.class);
		assertNotNull(response);
		assertEquals(Constant.OK, response.getBody().getCode());
	}

}