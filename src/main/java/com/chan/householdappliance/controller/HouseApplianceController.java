package com.chan.householdappliance.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.chan.householdappliance.dto.HouseApplianceDto;
import com.chan.householdappliance.dto.SearchHouseApplianceDto;
import com.chan.householdappliance.exception.BadRequestException;
import com.chan.householdappliance.exception.ResourceNotFoundException;
import com.chan.householdappliance.response.ApiResponse;
import com.chan.householdappliance.service.HouseApplianceService;



@RestController
@RequestMapping("/houseappliance/api/v1")
public class HouseApplianceController {
	
	@Autowired
	private HouseApplianceService houseApplianceService;
	
	@GetMapping("/{id}")
    public ResponseEntity<ApiResponse<HouseApplianceDto>> getHouseApplianceById(@PathVariable(value = "id") Long id)
      throws ResourceNotFoundException {
		return new ResponseEntity<ApiResponse<HouseApplianceDto>>(houseApplianceService.findById(id), HttpStatus.OK);
    }
	
	@GetMapping("/list")
	ResponseEntity<ApiResponse<List<HouseApplianceDto>>> filter(SearchHouseApplianceDto searchHouseApplianceDto) {
	    return new ResponseEntity<ApiResponse<List<HouseApplianceDto>>>(houseApplianceService.findAll(searchHouseApplianceDto), HttpStatus.OK);
	}

	@PostMapping
    public ResponseEntity<ApiResponse<HouseApplianceDto>> createOrUpdateHouseAppliance(@RequestBody @Validated HouseApplianceDto HouseApplianceDto) throws BadRequestException {
        return new ResponseEntity<ApiResponse<HouseApplianceDto>>(houseApplianceService.createOrUpdate(HouseApplianceDto), HttpStatus.OK);
    }
	
	@DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Boolean>> deleteHouseApplianceById(@PathVariable("id") Long id) throws ResourceNotFoundException {
        return new ResponseEntity<ApiResponse<Boolean>>(houseApplianceService.deleteById(id), HttpStatus.OK);
    }

}