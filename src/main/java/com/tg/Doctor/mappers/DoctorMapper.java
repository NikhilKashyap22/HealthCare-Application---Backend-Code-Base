package com.tg.Doctor.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.tg.Doctor.dtos.DoctorDTO;
import com.tg.Doctor.dtos.DoctorScheduleDTO;
import com.tg.Doctor.models.Doctor;
import com.tg.Doctor.models.DoctorSchedule;

@Mapper(componentModel = "spring")
public interface DoctorMapper {
	
	DoctorMapper mapperInstance = Mappers.getMapper(DoctorMapper.class);
	
	@Mapping(source = "specialization.specializationType", target = "specializationType")
	@Mapping(source = "specialization.briefDescription", target = "specializationDescription")
	@Mapping(source = "qualifications", target = "qualifications")
	DoctorDTO toDoctorDTO(Doctor doctor);
	
	
	
	@Mapping(source = "specializationType", target = "specialization.specializationType")
    @Mapping(source = "specializationDescription", target = "specialization.briefDescription")
	@Mapping(source = "qualifications", target = "qualifications")
	Doctor toDoctor(DoctorDTO doctorDTO);
	
	
	DoctorScheduleDTO toDoctorScheduleDTO(DoctorSchedule doctorSchedule);
	
	
	DoctorSchedule toDoctorSchedule(DoctorScheduleDTO doctorScheduleDTO);
	

}
