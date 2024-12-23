package com.tg.Doctor.models;



import jakarta.persistence.Embeddable;
import lombok.Data;
import lombok.NoArgsConstructor;


@Embeddable
@Data
@NoArgsConstructor
public class Clinic {

	private String clinicId;

	private String clinicName;

	private String address;

	private String state;

	private String city;
	
    private boolean status;


}
