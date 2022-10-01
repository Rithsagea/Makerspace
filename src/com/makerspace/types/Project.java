package com.makerspace.types;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.types.ObjectId;

public class Project implements Comparable<Project> {

	public static enum Category {
		NONE,
		SCIENCE;
	}

	@BsonId
	public ObjectId id = new ObjectId();
	
	public String name = "";
	public String url = "";

	public Category category = Category.NONE;
	public Contact contact = new Contact();

	public LinkedHashMap<String, ProjectFile> files = new LinkedHashMap<>();
	public ArrayList<Print> prints = new ArrayList<>();

	@Override
	public int compareTo(Project o) {
		return name.compareTo(o.name);
	}

}
