package com.cheong.clinic_api.exception_handler;

//@RestControllerAdvice
public class ApiExceptionHandler {

//	@ExceptionHandler
//	public HttpEntity<?> handleNoValuePresentException(NoSuchElementException noSuchElementException,
//			HttpServletRequest httpServletRequest){
//		Map<String,Object> responseMap = new LinkedHashMap<>();
//		responseMap.put("status",HttpStatus.NOT_FOUND.value());
//		responseMap.put("error",noSuchElementException.getMessage());
//		responseMap.put("path",httpServletRequest.getRequestURI());
//		
//		return ResponseEntity.status(HttpStatus.NOT_FOUND)
//				.location(URI.create(httpServletRequest.getRequestURI()))
//				.body(responseMap);
//	}
}
