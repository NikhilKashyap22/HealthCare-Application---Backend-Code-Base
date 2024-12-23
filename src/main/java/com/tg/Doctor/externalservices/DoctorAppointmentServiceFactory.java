package com.tg.Doctor.externalservices;

import com.tg.Doctor.exceptions.BadChoiceException;

public class DoctorAppointmentServiceFactory {
	
	public static IDoctorAppointmentService create(String choice) {

        IDoctorAppointmentService service = null;

        // Check the choice and create the appropriate implementation
        if ("mock".equals(choice)) {
            service = new DoctorAppointmentServiceMockImpl();
        } else if ("service".equals(choice)) {
            service = new DoctorAppointmentServiceImpl();
        } else {
            // Handle the case when the choice is not recognized
            // You can throw an exception or handle it in another way
            throw new BadChoiceException("Bad choice, choose between 'mock' and 'service'");
        }

        return service;
    }

}
