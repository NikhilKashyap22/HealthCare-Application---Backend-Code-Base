package com.tg.Doctor.externalservices;

import com.tg.Doctor.models.DoctorSchedule;

public interface IDoctorAppointmentService {
	
	public boolean getDoctorAppointmentDetailsByApi(DoctorSchedule doctorSchedule);
}
