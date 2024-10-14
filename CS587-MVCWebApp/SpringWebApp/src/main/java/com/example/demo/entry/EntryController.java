package com.example.demo.entry;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import jakarta.servlet.http.HttpServletResponse;


import com.opencsv.CSVWriter; 
import java.io.File;
import java.io.FileWriter;
import java.io.FileInputStream;
import java.util.List;
import java.util.ArrayList;
@Controller

public class EntryController {
	public List<Entry> currentEntries;
	@GetMapping("/")
	public String ShowHome(Model model) {
		return "index";
	}
	
	@GetMapping("/entry_form")
	public String showEntryForm(Model model) {
		Entry entry = new Entry();
		model.addAttribute("entry", entry);
		return "entry_form";
	}
	
	@PostMapping("/save")
	public String saveEntry(@ModelAttribute Entry entry, Model model) {
		if(currentEntries ==null) {
			currentEntries = new ArrayList<Entry>();
		}
		entry.determineType();
		entry.checkForOdd();
		currentEntries.add(entry);
		model.addAttribute("entries", currentEntries);
        model.addAttribute("status", true);
				
		return "display_form";
	}
	@GetMapping("/download_CSV")
    public void downloadFile(HttpServletResponse response) {
        try {
        	//File tempFile = File.createTempFile("Entries", ".csv");  // Create a temporary CSV file
        	File tempDir = new File(System.getProperty("java.io.tmpdir"));
            File tempFile = File.createTempFile("Entries", ".csv", tempDir);
            System.out.print(tempFile.getPath());
            
            
            FileWriter fileWriter = new FileWriter(tempFile, true);
            CSVWriter writer =new CSVWriter(fileWriter);
            
            writer.writeNext(new String[]{"type","content","isOdd"});
            for(int i=0;i<currentEntries.size();i++) {
            	writer.writeNext(currentEntries.get(i).getCSVForm());
                System.out.println("duck");
            }
            writer.close();            
            
            
            response.setContentType("text/csv");
            response.setHeader("Content-Disposition", "attachment; filename=" + "Entries");
            InputStream inputStream =  new FileInputStream(tempFile);

            OutputStream outputStream = response.getOutputStream();

            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
            

            // Close streams
            inputStream.close();
            outputStream.close();

        } catch (IOException e) {
            e.printStackTrace(); 
        }
    }	

	public static void rewriteEntryToCSV(List<Entry> entries) throws Exception{
		try {
			CSVWriter writer = new CSVWriter(new FileWriter(new ClassPathResource("Entries1.csv").getPath()));
            writer.writeNext(new String[]{"type","content","isOdd"});
            for(int i=0;i<entries.size();i++) {
            	writer.writeNext(entries.get(i).getCSVForm());
                System.out.println("duck");
            }
            writer.writeNext(new Entry("number", "1345678", false).getCSVForm());
            writer.close(); 
            System.out.println("duck");
		}catch(Exception ex){
			throw ex; 
		}
	}
}
	
	

