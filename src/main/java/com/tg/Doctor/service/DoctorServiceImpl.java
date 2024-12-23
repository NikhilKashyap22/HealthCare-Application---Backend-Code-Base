package com.tg.Doctor.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tg.Doctor.dtos.DoctorDTO;
import com.tg.Doctor.exceptions.DoctorAppointmentException;
import com.tg.Doctor.models.Address;
import com.tg.Doctor.models.Doctor;
import com.tg.Doctor.models.Experience;
import com.tg.Doctor.models.Specialization;
import com.tg.Doctor.models.Status;
import com.tg.Doctor.repositories.DoctorRepository;

import lombok.extern.slf4j.Slf4j;

/**
 * Service implementation for managing doctors.
 * Provides methods for CRUD operations related to doctor information.
 */
@Service
@Slf4j
public class DoctorServiceImpl implements IDoctorService {

    @Autowired
    private DoctorRepository doctorRepository;
 
    /**
     * Retrieves all doctors from the database.
     * 
     * @return a list of all doctors
     * @throws DoctorAppointmentException if an error occurs while retrieving doctors
     */
    @Override
    public List<Doctor> getAllDoctors() {
        log.info("Getting all doctors...");
        try {
            return doctorRepository.findAll();

        } catch (Exception ex) {
            log.error("Error getting all doctors: {}", ex.getMessage(), ex);

            throw new DoctorAppointmentException("Error getting all doctors", ex);
        }
    }

    /**
     * Adds a new doctor to the database.
     * 
     * @param doctorDTO the doctor data transfer object containing the doctor's details
     * @return the saved doctor entity
     * @throws DoctorAppointmentException if an error occurs while adding the doctor
     */
    @Override
    public Doctor addDoctor(DoctorDTO doctorDTO) {
        log.info("Adding a new doctor...");
        try {
        	Doctor doctor = mapDTO(doctorDTO);
        	log.info("Saving doctor to Database");
            return doctorRepository.save(doctor); 
        } catch (Exception ex) {
            log.error("Error adding doctor: {}", ex.getMessage(), ex);

            throw new DoctorAppointmentException("Error adding doctor", ex);
        }
    }

    /**
     * Retrieves a doctor by their ID.
     * 
     * @param doctorId the ID of the doctor to be retrieved
     * @return the doctor entity, or null if the doctor is not found
     * @throws DoctorAppointmentException if an error occurs while retrieving the doctor
     */
    @Override
    public Doctor viewDoctorId(String doctorId) {
        try {
            log.info("Viewing doctor by ID: {}", doctorId);
            return doctorRepository.findById(doctorId).orElse(null);
        } catch (Exception ex) {
            log.error("Error viewing doctor by ID: {}", ex.getMessage(), ex);
            throw new DoctorAppointmentException("Error viewing doctor by ID", ex);
        }
    }
    
    /**
     * Updates the address of an existing doctor.
     * 
     * @param doctorId the ID of the doctor to be updated
     * @param address the new address to be set for the doctor
     * @return the updated doctor entity
     */
    @Override
    public Doctor updateAddress(String doctorId, Address address) {
        Doctor doctor = doctorRepository.findById(doctorId).orElse(null);
        doctor.setAddress(address);
        return doctorRepository.save(doctor);
    }
    
    /**
     * Deletes a doctor by updating their status to "NOT_WORKING".
     * 
     * @param doctorId the ID of the doctor to be deleted
     */
    @Override
    public void deleteDoctor(String doctorId) {
        Doctor doctor = doctorRepository.findById(doctorId).orElse(null);
        doctor.setStatus(Status.NOT_WORKING);
        doctorRepository.save(doctor);
    }
    
    /**
     * Updates the experience details of an existing doctor.
     * 
     * @param doctorId the ID of the doctor to be updated
     * @param experiences the new experience details to be set for the doctor
     * @return the updated doctor entity
     */
    @Override
    public Doctor updateExperience(String doctorId, List<Experience> experiences) {
        Doctor doctor = doctorRepository.findById(doctorId).orElse(null);
        doctor.setExperiences(experiences);
        return doctorRepository.save(doctor);
    }
    
    /**
     * Maps the provided doctor data transfer object (DTO) to a doctor entity.
     * 
     * @param dto the doctor DTO containing the doctor's details
     * @return the corresponding doctor entity
     */
    public Doctor mapDTO(DoctorDTO dto) {
    	Doctor doctor = new Doctor();
    	doctor.setFirstName(dto.getFirstName());
    	doctor.setLastName(dto.getLastName());
    	doctor.setPhoneNum(dto.getPhoneNum());
    	doctor.setDateOfBirth(dto.getDateOfBirth());
    	doctor.setEmail(dto.getEmail());
    	doctor.setExperienceInYears(dto.getExperienceInYears());
    	
    	//Specialization mapping
    	Specialization specialization = new Specialization();
    	specialization.setSpecializationType(dto.getSpecializationType());
    	specialization.setBriefDescription(dto.getSpecializationDescription());    	
    	
    	//Setting the specialization object for doctor object
    	doctor.setSpecialization(specialization);
    	
    	doctor.setGender(dto.getGender());
    	doctor.setStatus(dto.getStatus());
    	//Address Mapping
    	doctor.setAddress(dto.getAddress());
    	//Mode Mapping
    	doctor.setAvailabilityMode(dto.getAvailabilityMode());
    	
    	//Experience Mapping
    	doctor.setExperiences(dto.getExperiences());
    	
    	return doctor;
    }
}
