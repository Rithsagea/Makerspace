package com.makerspace.types;

public class ProjectFile {
	
	public ProjectFile() {
		
	}
	
	public ProjectFile(String name, String fileUrl, String imageUrl) {
		this.name = name;
		this.fileUrl = fileUrl;
		this.imageUrl = imageUrl;
	}
	
	public String name = "";
	public String fileUrl = "";
	public String imageUrl = "";
}
