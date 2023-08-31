package com.cheong.clinic_api.report.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.cheong.clinic_api.report.service.IReportService;

@RestController
@RequestMapping("/api/report")
public class ReportController {

	private IReportService reportService;

	public ReportController(IReportService reportService) {
		this.reportService = reportService;
	}

	@PostMapping("/stock/csv")
	@ResponseStatus(code = HttpStatus.CREATED)
	public void generateCsv() throws IOException {
		reportService.generateCsv(new String [] {"No", "Name"},
				Arrays.asList(Arrays.asList("1", "Cheong Kar Wai"), Arrays.asList("2", "Cheong Pui Yi")));
	}
	
	@PostMapping("/download")
	public HttpEntity<?> downloadFile() throws IOException{
		MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
		headers.put(HttpHeaders.CONTENT_TYPE, Arrays.asList(MediaType.APPLICATION_OCTET_STREAM_VALUE));
		headers.put(HttpHeaders.CONTENT_DISPOSITION,Arrays.asList("attachment; filename=report.csv"));
		return ResponseEntity.ok().headers(e->e.addAll(headers)).body(Files.readAllBytes(Path.of("C:\\Users\\cheon\\sts-workspace\\test.csv")));
	}
	
}
