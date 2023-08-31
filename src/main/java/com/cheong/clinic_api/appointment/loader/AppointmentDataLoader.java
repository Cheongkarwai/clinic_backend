package com.cheong.clinic_api.appointment.loader;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

import org.dataloader.BatchLoader;

import com.cheong.clinic_api.appointment.domain.Appointment;
import com.cheong.clinic_api.appointment.service.IAppointmentService;
import com.netflix.graphql.dgs.DgsDataLoader;

import lombok.RequiredArgsConstructor;

@DgsDataLoader(name = "appointments")
@RequiredArgsConstructor
public class AppointmentDataLoader implements BatchLoader<Long, Appointment>{

	private final IAppointmentService appointmentService;
	
	@Override
	public CompletionStage<List<Appointment>> load(List<Long> keys) {
		return CompletableFuture.supplyAsync(()->appointmentService.loadAppointments(keys));
	}

}
