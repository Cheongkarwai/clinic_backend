package com.cheong.clinic_api.common.logging;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import com.cheong.clinic_api.product.domain.Product;

@Aspect
@Component
public class InfrastructureLogging {
	
//	@Before("within(com.cheong.clinic_api.service..*)")
//	public void logService() {
//		System.out.println("Hi");
//	}
//	
//	@Before("execution(* com.cheong.clinic_api.service..*.*(..))")
//	public void logAllService() {
//		System.out.println("LOL");
//	}
	
//	@Before("@annotation(com.cheong.clinic_api.common.annotation.Logging)")
//	public void logMethodWithAnnotation() {
//		System.out.println("Hello");
//	}
	
//	@After("execution(* com.cheong.clinic_api.service.impl.ProductService.findAll(..)) && args(pageable,..)")
//	public void returnedValue(Pageable pageable) {
//		System.out.println(pageable.getPageNumber());
//	}

//	@AfterReturning(pointcut = "execution(* com.cheong.clinic_api.service.impl.ProductService.findAll(..)) && args(pageable,..)",
//			returning = "returnedValue")
//	public void logArgumentWithReturnedValue(Page<Product> returnedValue,Pageable pageable) {
//		
//		System.out.println(returnedValue.getContent());
//	}
	
	@Around("execution(* com.cheong.clinic_api.product.service.ProductService.findAll(..))")
	public Object setPage(ProceedingJoinPoint pjp) throws Throwable {
		
		Object [] args = pjp.getArgs();
		
		args[0] = PageRequest.of(3, 2);
		
		return pjp.proceed(args);
	}
	
//	@Around("execution(* com.cheong.clinic_api.service.impl.ProductService.findAll(..))")
//	public Object logProductServiceFindAll(ProceedingJoinPoint pjp) throws Throwable {
//		
//		System.out.println("Hi");
//		
//		for(Object object : pjp.getArgs()) {
//			System.out.println(object.getClass().getTypeName());
//		}
//		
//		return pjp.proceed();
//	}
	
	
}
