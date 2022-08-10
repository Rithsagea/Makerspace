package com.makerspace;

import java.util.ArrayList;
import java.util.LinkedHashMap;

public class Project {
	
	public static enum Category {
		SCIENCE;
	}
	
	public String name;
	public String url;
	
	public Category category;
	public Contact contact = new Contact();
	
	public LinkedHashMap<String, ProjectFile> files = new LinkedHashMap<>();
	public ArrayList<Print> prints = new ArrayList<>();
	
	
}
