package com.tg.Doctor.externalservices;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.json.JsonParser;
import org.springframework.boot.json.JsonParserFactory;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.tg.Doctor.models.DoctorSchedule;
import com.tg.Doctor.repositories.DoctorScheduleRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class DoctorAppointmentServiceImpl implements IDoctorAppointmentService {

	@Autowired
	DoctorScheduleRepository doctorScheduleRepository;

	@Autowired
	RestTemplate restTemplate;

	@Value("${appointmentApiUrl}")
	private String appointmentApiUrl;

	@Override
	public boolean getDoctorAppointmentDetailsByApi(DoctorSchedule doctorSchedule) {
		ResponseEntity<String> appointmentResponse = restTemplate.exchange(
				appointmentApiUrl + "/doctor/" + doctorSchedule.getDoctorId(), HttpMethod.GET, null, String.class);

		log.info("Response: " + appointmentResponse.getBody());

		// Extract the appointment details from the response
		if (appointmentResponse.getBody() != null) {
			JsonParser springParser = JsonParserFactory.getJsonParser();
			Map<String, Object> map = springParser.parseMap(appointmentResponse.getBody());
			for (Map.Entry<String, Object> entry : map.entrySet()) {
				// Example: If the key for appointment status is "status"
				if ("status".equals(entry.getKey())) {
					return "confirmed".equalsIgnoreCase(entry.getValue().toString());
				}
			}
		}
		return false;
	}

}
