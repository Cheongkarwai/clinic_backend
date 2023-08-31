package com.cheong.clinic_api.report.service;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

public interface IReportService {

	void generateCsv(String [] headers, List<List<String>> data) throws IOException;
	
	Object getCsv(String path) throws IOException;
	
	OutputStream downloadCsv(String path) throws IOException;
}
