package com.cheong.clinic_api.config;

import java.util.Date;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.TriggerContext;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.FixedDelayTask;
import org.springframework.scheduling.config.IntervalTask;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.scheduling.config.TriggerTask;

@Configuration
@EnableScheduling
public class SchedulerConfig{

//	@Override
//	public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
//	
//		
//		TriggerTask triggerTask = new TriggerTask(()->{
//			
//		}, new Trigger() {
//			
//			@Override
//			public Date nextExecutionTime(TriggerContext triggerContext) {
//				// TODO Auto-generated method stub
//				return null;
//			}
//		});
//		
//		taskRegistrar.addTriggerTask(null);;
//	
//		
//	}

}
