package com.tg.Doctor.externalservices;

import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.json.JsonParser;
import org.springframework.boot.json.JsonParserFactory;
import org.springframework.web.client.RestClient;

@Service
@Slf4j
public class ClinicServiceImp implements IClinicService {

    private final RestClient restClient;

    @Value("${clinicApiUrl}")
    private String clinicApiUrl;

    private static final String CLINIC_SERVICE = "clinicService";

    public ClinicServiceImp() {
        this.restClient = RestClient.builder().build();
    }

    @Override
    @CircuitBreaker(name = CLINIC_SERVICE, fallbackMethod = "getClinicAvailabilityFallBack")
    public boolean getClinicAvailabilityByClinicApi(String clinicId) {
        boolean status = false;
        System.out.println("================================================================="+clinicApiUrl);

        // Build the request URL
        String url = clinicApiUrl + "/api/Clinic/service/" + clinicId;

        // Call the clinic API to get clinic availability
        ResponseEntity<String> clinicResponse = restClient
                .get()
                .uri(url)
                .retrieve()
                .toEntity(String.class);

        // Log the response from the clinic API
        log.info("Response: " + clinicResponse.getBody());

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

    public boolean getClinicAvailabilityFallBack(String clinicId, Throwable throwable) {
        log.info("Fallback triggered for clinicId: {} due to: {}", clinicId, throwable.getMessage());
        return false;
    }
}
