package com.example.fileupload.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.example.fileupload.service.FileUploadService;

@Controller
public class FileUploadController {
	@Autowired
	private FileUploadService fileUploadService;
	
	@RequestMapping("/form")
	public String form() {
		return "form";
	}
	
	@RequestMapping("/upload")
	public String upload(
			@RequestParam(value="email", required=true, defaultValue="") String email,
			@RequestParam("file") MultipartFile[] file,
			Model model
			) {
		List<String> url = new ArrayList<String>();
		
		url = fileUploadService.restore(file);
		model.addAttribute("urlList", url);
		
		return "result";
	}
}
