package com.cheong.clinic_api.appointment.fetcher;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.dataloader.DataLoader;

import com.cheong.clinic_api.appointment.domain.Appointment;
import com.cheong.clinic_api.auth.domain.User;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsData;

import graphql.schema.DataFetchingEnvironment;

@DgsComponent
public class AppointmentDataFetcher {

	@DgsData(parentType = "User",field = "appointments")
	public CompletableFuture<List<Appointment>> appointment(DataFetchingEnvironment dfe){
		
		DataLoader<Long, Appointment> dataLoader = dfe.getDataLoader("appointments");
		User user = dfe.getSource();
		System.out.println(user);
		return dataLoader.loadMany(dfe.getArgument("id"));
	}
}
