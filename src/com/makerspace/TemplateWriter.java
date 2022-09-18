package com.makerspace;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedHashMap;
import java.util.Map;
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

	public TemplateWriter() {
		try {
			pageTemplate = Files.readAllLines(Paths.get(getClass().getResource("pageTemplate.html").toURI())).stream().collect(Collectors.joining("\n"));
			fileEntryTemplate = Files.readAllLines(Paths.get(getClass().getResource("fileEntryTemplate.html").toURI())).stream().collect(Collectors.joining("\n"));
			printEntryTemplate = Files.readAllLines(Paths.get(getClass().getResource("printEntryTemplate.html").toURI())).stream().collect(Collectors.joining("\n"));
			printOptionEntryTemplate = Files.readAllLines(Paths.get(getClass().getResource("printOptionEntryTemplate.html").toURI())).stream().collect(Collectors.joining("\n"));
		} catch (IOException | URISyntaxException e) {
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
}
