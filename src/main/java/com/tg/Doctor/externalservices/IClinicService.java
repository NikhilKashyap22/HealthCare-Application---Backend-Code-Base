package com.tg.Doctor.externalservices;

import java.util.List;

import com.tg.Doctor.models.Clinic;
import com.tg.Doctor.models.Doctor;

public interface IClinicService {

	boolean getClinicAvailabilityByClinicApi(Clinic clinic);

}
