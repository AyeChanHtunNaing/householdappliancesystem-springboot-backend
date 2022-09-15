package com.chan.householdappliance.service.impl;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.chan.householdappliance.constant.Constant;
import com.chan.householdappliance.dto.HouseApplianceDto;
import com.chan.householdappliance.dto.SearchHouseApplianceDto;
import com.chan.householdappliance.enumtype.Status;
import com.chan.householdappliance.exception.BadRequestException;
import com.chan.householdappliance.exception.ResourceNotFoundException;
import com.chan.householdappliance.model.HouseAppliance;
import com.chan.householdappliance.repository.HouseApplianceRepository;
import com.chan.householdappliance.response.ApiResponse;
import com.chan.householdappliance.service.HouseApplianceService;
import com.chan.householdappliance.util.OffsetDateTimeUtils;


@Service
public class HouseApplianceServiceImpl implements HouseApplianceService {

	@Autowired
	private HouseApplianceRepository houseApplianceRepository;

	@Override
	public ApiResponse<HouseApplianceDto> createOrUpdate(HouseApplianceDto houseApplianceDto) throws BadRequestException {
		HouseAppliance houseApplicance = new HouseAppliance();
		Optional<HouseAppliance> optionalHouseApplicance = Optional.ofNullable(houseApplianceDto.getId()).isPresent() ? houseApplianceRepository.findById(houseApplianceDto.getId()) : Optional.empty();
        if(optionalHouseApplicance.isPresent() ) {
        	houseApplicance = optionalHouseApplicance.get();
            BeanUtils.copyProperties(houseApplianceDto, houseApplicance);
            houseApplicance = houseApplianceRepository.save(houseApplicance);
            
        } else {
        	boolean houseApplianceExist = houseApplianceRepository.existsBySerialNumberOrBrandOrModel(houseApplianceDto.getSerialNumber(), houseApplianceDto.getBrand(), houseApplicance.getModel());
        	if(houseApplianceExist) {
        		throw new BadRequestException(Constant.ITEM_ALREADY_EXISTS);
        	}
        	BeanUtils.copyProperties(houseApplianceDto, houseApplicance);
        	houseApplicance = houseApplianceRepository.save(houseApplicance);
        	BeanUtils.copyProperties(houseApplicance, houseApplianceDto);
        }
        return new ApiResponse<HouseApplianceDto>(Constant.OK, Constant.SUCCESS, houseApplianceDto);
	}

	@Override
	public ApiResponse<List<HouseApplianceDto>> findAll(SearchHouseApplianceDto searchHouseApplianceDto) {

	List<HouseAppliance> houseApplicaceList = houseApplianceRepository.findAll(new Specification<HouseAppliance>() {
	    @Override
	    public Predicate toPredicate(Root<HouseAppliance> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb) {
	    List<Predicate> predicates = new LinkedList<>();
	    
	    if (StringUtils.isNotBlank(searchHouseApplianceDto.getSerialNumber())) {
            predicates.add(cb.like(cb.lower(root.get("serialNumber")), "%" + searchHouseApplianceDto.getSerialNumber().toLowerCase() + "%"));
        }
        
        if (StringUtils.isNotBlank(searchHouseApplianceDto.getModel())) {
            predicates.add(cb.like(cb.lower(root.get("model")), "%" + searchHouseApplianceDto.getModel().toLowerCase() + "%"));
        }
        
        if (StringUtils.isNotBlank(searchHouseApplianceDto.getBrand())) {
            predicates.add(cb.like(cb.lower(root.get("brand")), "%" + searchHouseApplianceDto.getBrand().toLowerCase() + "%"));
        }
        
        if (StringUtils.isNotBlank(searchHouseApplianceDto.getStatus())) {
            predicates.add(cb.equal(root.get("status"), Status.fromString(searchHouseApplianceDto.getStatus())));
        }
        
        if (StringUtils.isNotBlank(searchHouseApplianceDto.getBoughtDate())) {
        	OffsetDateTime boughtDate = OffsetDateTimeUtils.convertToOffsetDateTime(searchHouseApplianceDto.getBoughtDate());
            predicates.add(cb.equal(root.get("boughtDate"),boughtDate));
        }
	    
	    return criteriaQuery.where(cb.and(predicates.toArray(new Predicate[0]))).distinct(true).getRestriction();
	    }
	});
	List<HouseApplianceDto> HouseApplianceDtoList = new ArrayList<HouseApplianceDto>();
		for(HouseAppliance houseApplicance : houseApplicaceList) {
			HouseApplianceDto HouseApplianceDto = new HouseApplianceDto();
			BeanUtils.copyProperties(houseApplicance, HouseApplianceDto);
			HouseApplianceDtoList.add(HouseApplianceDto);
		}
		return new ApiResponse<List<HouseApplianceDto>>(Constant.OK, Constant.SUCCESS, HouseApplianceDtoList);
	}

	@Override
	public ApiResponse<Boolean> deleteById(Long id) throws ResourceNotFoundException {
		Optional<HouseAppliance> optHouseApplicance = houseApplianceRepository.findById(id);
		if(optHouseApplicance.isPresent()) {
			houseApplianceRepository.delete(optHouseApplicance.get());	
		} else {
			throw new ResourceNotFoundException(Constant.ITEM_NOT_FOUND);
		}
		return new ApiResponse<Boolean>(Constant.OK, Constant.SUCCESS, true);
	}

	@Override
	public ApiResponse<HouseApplianceDto> findById(Long id) throws ResourceNotFoundException {
		Optional<HouseAppliance> optionalHouseApplicance = houseApplianceRepository.findById(id);
		HouseApplianceDto houseApplianceDto = new HouseApplianceDto();
        if(optionalHouseApplicance.isPresent() ) {
            BeanUtils.copyProperties(optionalHouseApplicance.get(), houseApplianceDto);
        }else {
			throw new ResourceNotFoundException(Constant.ITEM_NOT_FOUND);
		}
        return new ApiResponse<HouseApplianceDto>(Constant.OK, Constant.SUCCESS, houseApplianceDto);
	}
}