package com.example.demo.entry;

import com.opencsv.bean.CsvBindByName;

public class Entry {
	
	@CsvBindByName
	private String type;
	
	@CsvBindByName
	private String content;
	
	@CsvBindByName
	private Boolean isOdd;
	
	
	public Entry() {

	}
	public Entry(String type, String content, Boolean isOdd) {
		this.type=type;
		this.content = content;
		this.isOdd= isOdd;
	}
	public void checkForOdd() {
		if(type.equals("number")) {
			this.isOdd = Integer.parseInt(content)%2 == 1;
		}else{
			this.isOdd=false;
		}
		
	}
	public void determineType(){
		if (content == null||content.equals("")) {
	        type = "error";
	        content = "user did not enter anything";
	    }else {
	    	try {
		        double num = Double.parseDouble(content);
		        type = "number";
		    } catch (NumberFormatException nfe) {
		    	type = "error";
		        content = "user did not enter an number";
		    }
	    }
	}
	
	public String[] getCSVForm() {
		return new String[] {type, content, Boolean.toString(isOdd)};
	}
	public String getContent() {
		return content;
	}
	
	public void setContent(String content) {
		this.content = content;
	}
	
	public String getType() {
		return type;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	
	public Boolean getIsOdd() {
		return isOdd;
	}
	public void setIsOdd(Boolean isOdd) {
		this.isOdd = isOdd;
	}
	
	public String toString() {
		return content;
	}

}
