package com.chan.householdappliance.model;

import java.io.Serializable;
import java.time.OffsetDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.chan.householdappliance.enumtype.Status;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@NoArgsConstructor
@Entity
@Table(name = "HOUSE_APPLIANCE")
public class HouseAppliance extends BaseEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5476482464399669287L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Long id;
	@Column(name = "SERIAL_NUMBER")
	private String serialNumber;
	@Column(name = "BARND")
	private String brand;
	@Column(name = "MODEL")
	private String model;
	@Enumerated(EnumType.STRING)
	@Column(name = "STATUS")
	private Status status;
	@Column(name = "BOUGHT_DATE")
	private OffsetDateTime boughtDate;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getSerialNumber() {
		return serialNumber;
	}
	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}
	public String getBrand() {
		return brand;
	}
	public void setBrand(String brand) {
		this.brand = brand;
	}
	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
	}
	public Status getStatus() {
		return status;
	}
	public void setStatus(Status status) {
		this.status = status;
	}
	public OffsetDateTime getBoughtDate() {
		return boughtDate;
	}
	public void setBoughtDate(OffsetDateTime boughtDate) {
		this.boughtDate = boughtDate;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}