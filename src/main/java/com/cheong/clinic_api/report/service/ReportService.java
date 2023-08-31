package com.cheong.clinic_api.report.service;

import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ReportService implements IReportService {

	@Override
	public void generateCsv(String [] headers, List<List<String>> records) throws IOException {

		
		BufferedWriter writer = Files.newBufferedWriter(Paths.get("C:\\Users\\cheon\\sts-workspace\\test.csv"));
		
		CSVPrinter csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT.withHeader(headers));
		
		for(List<String> rowOfRecord : records) {
			csvPrinter.printRecord(rowOfRecord);
		}
		
		writer.flush();
	
	}

	@Override
	public OutputStream getCsv(String filePath) throws IOException {
		
		Path path = Paths.get(filePath);
		OutputStream outputStream = new ByteArrayOutputStream();
		Files.copy(path, outputStream);
		return outputStream;
	}

	@Override
	public OutputStream downloadCsv(String filePath) throws IOException {
		Path path = Paths.get(filePath);
		OutputStream outputStream = new ByteArrayOutputStream();
		Files.copy(path, outputStream);
		return outputStream;
	}
	


	
}
