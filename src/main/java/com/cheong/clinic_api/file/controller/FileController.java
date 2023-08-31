package com.cheong.clinic_api.file.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
//
//import net.sourceforge.tess4j.Tesseract;
//import net.sourceforge.tess4j.TesseractException;

//@RestController
//@RequestMapping("/api/v1/files")
//public class FileController {
//
//	@PostMapping("/upload")
//	public HttpEntity<?> uploadFiles(@RequestParam("file") MultipartFile multipartFile) throws TesseractException, IOException {
//
//		Tesseract tesseract = new Tesseract();
//
//
//		tesseract.setDatapath("src/main/resources/tessdata");
//		String text = tesseract.doOCR(convert(multipartFile));
//		
//		Pattern pattern = Pattern.compile("Total Charges:\\s([A-Za-z0-9]+( [A-Za-z0-9]+)+)", Pattern.CASE_INSENSITIVE);
//	    Matcher matcher = pattern.matcher(text);
//	    
//	    matcher.find();
//	    System.out.println(matcher.group(0));
//
//		return ResponseEntity.ok(text);
//	}
//	
//	 private static File convert(MultipartFile file) throws IOException {
//	        File convFile = new File(file.getOriginalFilename());
//	        FileOutputStream fos = new FileOutputStream(convFile);
//	        fos.write(file.getBytes());
//	        fos.close();
//	        return convFile;
//	    }
//}
