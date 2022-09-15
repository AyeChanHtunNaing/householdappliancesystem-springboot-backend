package com.chan.householdappliance.response;

import java.util.List;
import java.util.Map;

import com.chan.householdappliance.constant.Constant;
import com.chan.householdappliance.dto.HouseApplianceDto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ApiResponse<T> {

    private String code;
    private String message;
    private Object result;
    public ApiResponse(String notOk, String fail, List<HouseApplianceDto> houseApplianceDtoList) {
    	this.code =  Constant.OK;
    } 
    public ApiResponse(String notOk, String fail, HouseApplianceDto houseApplianceDto) {
    	this.code =  Constant.OK;
    } 
    public ApiResponse(String notOk, String fail, Map<String, String> errors) {
    	this.code =  Constant.OK;
    }
    public ApiResponse(String notOk, String fail, boolean b) {
    	this.code =  Constant.OK;
    }
    
    public ApiResponse() {
		super();
		// TODO Auto-generated constructor stub
	}

	public static ApiResponse create() {
    	ApiResponse response = new ApiResponse();
    	response.code = Constant.OK;
    	response.message = Constant.SUCCESS;
    	return response;
    	
    }
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Object getResult() {
		return result;
	}
	public void setResult(Object result) {
		this.result = result;
	}
}