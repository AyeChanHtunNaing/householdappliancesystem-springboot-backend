package com.chan.householdappliance.service;

import java.util.List;

import com.chan.householdappliance.dto.HouseApplianceDto;
import com.chan.householdappliance.dto.SearchHouseApplianceDto;
import com.chan.householdappliance.exception.BadRequestException;
import com.chan.householdappliance.exception.ResourceNotFoundException;
import com.chan.householdappliance.response.ApiResponse;



public interface HouseApplianceService {
	

	ApiResponse<HouseApplianceDto> createOrUpdate(HouseApplianceDto HouseApplianceDto) throws BadRequestException;
	
	ApiResponse<List<HouseApplianceDto>> findAll(SearchHouseApplianceDto searchHouseApplianceDto);
	
	ApiResponse<Boolean> deleteById(Long id) throws ResourceNotFoundException;

	ApiResponse<HouseApplianceDto> findById(Long id) throws ResourceNotFoundException;
	

}