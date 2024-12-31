package com.tg.Doctor.validators;

import java.time.LocalDate;
import java.time.Period;


import com.tg.Doctor.exceptions.InvalidAgeException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Validator {

	public static void validAge(LocalDate dateOfBirth) throws InvalidAgeException {
	    if (dateOfBirth == null) {
	        throw new InvalidAgeException("Date of birth cannot be null");
	    }

	    int age = Period.between(dateOfBirth, LocalDate.now()).getYears();
	    if (age < 23) {
	        throw new InvalidAgeException("Age must be at least 23 years");
	    }
	} 
}
   