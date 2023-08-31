package com.cheong.clinic_api.appointment.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cheong.clinic_api.appointment.domain.Appointment;

public interface AppointmentRepository extends JpaRepository<Appointment, Long>{

}
