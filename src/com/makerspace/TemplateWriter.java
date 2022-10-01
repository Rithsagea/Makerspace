package com.makerspace;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

import org.apache.commons.text.StringSubstitutor;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.makerspace.types.Project;

public class TemplateWriter {
	private String pageTemplate;

	private String fileEntryTemplate;
	private String printEntryTemplate;
	private String printOptionEntryTemplate;

	private String indexTemplate;
	private String indexProjectTemplate;

	private Set<Project> projects = new TreeSet<>();

	private String getResource(String name) {
		try {
			return Files.readAllLines(Paths.get(getClass().getResource(name).toURI())).stream().collect(Collectors.joining("\n"));
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public TemplateWriter() {
		try {
			pageTemplate = getResource("pageTemplate.html");
			fileEntryTemplate = getResource("fileEntryTemplate.html");
			printEntryTemplate = getResource("printEntryTemplate.html");
			printOptionEntryTemplate = getResource("printOptionEntryTemplate.html");

			indexTemplate = getResource("indexTemplate.html");
			indexProjectTemplate = getResource("indexProjectTemplate.html");

		} catch (Exception e) {
			e.printStackTrace();
			System.exit(0); // program will not work if these don't load properly
		}
	}
	
	private void addToMap(String prefix, Map<String, String> valueMap, JsonObject json) {
		for(String key : json.keySet()) {
			JsonElement value = json.get(key);
			if(value.isJsonObject())
				addToMap(prefix + key + ".", valueMap, value.getAsJsonObject());
			else if(value.isJsonPrimitive())
				valueMap.put(prefix + key, value.getAsString());
		}
	}
	
	public String write(Project project) {
		projects.add(project);

		Gson gson = new Gson();
		Map<String, String> valueMap = new LinkedHashMap<>();
		addToMap("", valueMap, gson.toJsonTree(project).getAsJsonObject());
		
		StringBuilder files = new StringBuilder();
		project.files.entrySet().forEach(entry -> {
			StringBuilder sb = new StringBuilder(fileEntryTemplate);
			Map<String, String> map = new LinkedHashMap<>();
			addToMap("", map, gson.toJsonTree(entry.getValue()).getAsJsonObject());
			
			new StringSubstitutor(map).replaceIn(sb);
			files.append(sb);
			files.append("\n");
		});
		valueMap.put("fileData", files.toString());
		
		StringBuilder prints = new StringBuilder();
		project.prints.forEach(print -> {
			StringBuilder sb = new StringBuilder(printEntryTemplate);
			Map<String, String> map = new LinkedHashMap<>();
			addToMap("", map, gson.toJsonTree(print).getAsJsonObject());
			
			StringBuilder options = new StringBuilder();
			print.options.entrySet().forEach(entry -> {
				StringBuilder sb2 = new StringBuilder(printOptionEntryTemplate);
				Map<String, String> map2 = new LinkedHashMap<>();
				map2.put("option", entry.getKey());
				map2.put("value", entry.getValue());
				
				new StringSubstitutor(map2).replaceIn(sb2);
				options.append(sb2.toString());
				options.append("\n");
			});
			map.put("optionData", options.toString());
			
			new StringSubstitutor(map).replaceIn(sb);
			prints.append(sb);
			prints.append("\n");
		});
		valueMap.put("printData", prints.toString());
		
		StringBuilder page = new StringBuilder(pageTemplate);
		new StringSubstitutor(valueMap).replaceIn(page);
		
		return page.toString();
	}

	public String getIndex() {
		Map<String, String> valueMap = new LinkedHashMap<>();
		
		StringBuilder projects = new StringBuilder();
		this.projects.forEach(proj -> {
			StringBuilder sb = new StringBuilder(indexProjectTemplate);
			Map<String, String> map = new LinkedHashMap<>();
			map.put("projectUrl", "./" + proj.id + ".html");
			map.put("projectName", proj.name);

			new StringSubstitutor(map).replaceIn(sb);
			projects.append(sb.toString());
			projects.append("\n");
		});
		valueMap.put("projectData", projects.toString());

		StringBuilder page = new StringBuilder(indexTemplate);
		new StringSubstitutor(valueMap).replaceIn(page);
		return page.toString();		
	}
}
