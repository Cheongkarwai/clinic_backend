package com.cheong.clinic_api.appointment.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cheong.clinic_api.appointment.domain.Appointment;
import com.cheong.clinic_api.appointment.repository.AppointmentRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class AppointmentService implements IAppointmentService{

	private final AppointmentRepository appointmentRepository;
	
	@Override
	public List<Appointment> loadAppointments(List<Long> keys) {
		return appointmentRepository.findAllById(keys);
	}

}
