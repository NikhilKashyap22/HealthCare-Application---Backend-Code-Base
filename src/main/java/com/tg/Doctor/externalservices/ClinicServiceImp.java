package com.tg.Doctor.externalservices;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import org.springframework.boot.json.JsonParser;
import org.springframework.boot.json.JsonParserFactory;
import com.tg.Doctor.models.Clinic;

import org.springframework.http.HttpMethod;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ClinicServiceImp implements IClinicService {


	@Autowired
	private RestTemplate restTemplate;
	private ResponseEntity<String> clinicResponse;

	@Value("${clinicApiUrl}")
	private String clinicApiUrl;

	

	@Override
	public boolean getClinicAvailabilityByClinicApi(Clinic clinic) {

		boolean status = false;
        
        // Call the clinic API to get clinic availability
        clinicResponse = restTemplate.exchange(clinicApiUrl + clinic.getClinicId(), HttpMethod.GET, null,
                String.class);

        // Log the response from the clinic API
        log.info("Response" + clinicResponse.getBody());

        // Parse the response and extract clinic availability status
        if (clinicResponse.getBody() != null) {
            JsonParser springParser = JsonParserFactory.getJsonParser();
            Map<String, Object> map = springParser.parseMap(clinicResponse.getBody());
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                if ("isActive".equals(entry.getKey())) {
                    status = Boolean.parseBoolean(entry.getValue().toString());
                    break;
                }
            }
        }
        return status;
	}
	

}
