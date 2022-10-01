package com.makerspace;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.List;

import com.makerspace.database.Config;
import com.makerspace.database.Database;
import com.makerspace.types.Project;
public class Main {
	
	public static void main(String[] args) {
		Config.init("config.properties");
		Database db = Database.getInstance();
		db.load();
		
		Project proj = db.listProjects().stream()
				.filter(p -> p.name.equals("Human Skeleton"))
				.findFirst().get();
		
		//do stuff
		
		//export site
		TemplateWriter templateWriter = new TemplateWriter();
		db.listProjects().forEach(p -> {
			try {
				List<String> content = Arrays.asList(templateWriter.write(p).split("\n"));
				Files.write(new File("output/" + p.id + ".html").toPath(), content, StandardCharsets.UTF_8);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});

		try {
			Files.copy(Paths.get(Main.class.getResource("style.css").toURI()), Paths.get("output/style.css"), StandardCopyOption.REPLACE_EXISTING);
			Files.write(Paths.get("output/index.html"), Arrays.asList(templateWriter.getIndex().split("\n")), StandardCharsets.UTF_8);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		db.save();

	}
}
