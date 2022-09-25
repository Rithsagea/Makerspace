package com.makerspace;

import com.makerspace.database.Config;
import com.makerspace.database.Database;

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
//		TemplateWriter templateWriter = new TemplateWriter();
//		try {
//			Files.write(new File("output.html").toPath(), Arrays.asList(templateWriter.write(proj).split("\n")), StandardCharsets.UTF_8);
//		} catch (IOException e1) {
//			e1.printStackTrace();
//		}
		
		db.save();

	}
}
