package com.cheong.clinic_api.appointment.service;

import java.util.List;

import com.cheong.clinic_api.appointment.domain.Appointment;

public interface IAppointmentService {

	List<Appointment> loadAppointments(List<Long> keys);
}
