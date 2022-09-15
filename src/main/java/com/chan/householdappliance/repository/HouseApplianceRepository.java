package com.chan.householdappliance.repository;


import java.util.List;


import org.springframework.data.jpa.domain.Specification;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.chan.householdappliance.model.HouseAppliance;



@Repository
public interface HouseApplianceRepository extends CrudRepository<HouseAppliance, Long> {
	
	 boolean existsBySerialNumber(String serialNumber);
	 
	 boolean existsByBrand(String brand);
	 
	 boolean existsByModel(String model);
	 
	 boolean existsBySerialNumberOrBrandOrModel(String serialNumber, String brand, String model);

	List<HouseAppliance> findAll(Specification<HouseAppliance> specification);
	
}
