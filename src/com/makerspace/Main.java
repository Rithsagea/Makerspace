package com.makerspace;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.makerspace.Print.FilamentBrand;
import com.makerspace.Print.FilamentType;
import com.makerspace.Print.PrinterType;

public class Main {
	public static void main(String[] args) {
		GsonBuilder builder = new GsonBuilder();
		builder.setPrettyPrinting();
		
		Gson gson = builder.create();
		Project proj = null;
		
		try {
			FileReader reader = new FileReader("projects.json");
			proj = gson.fromJson(reader, Project.class);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
//		proj = new Project();
//		proj.name = "Tactile DNA";
//		proj.url = "https://www.thingiverse.com/thing:4780524";
//		proj.category = Category.SCIENCE;
//		proj.contact.name = "Ian Matty";
//		proj.contact.email = "LakerMakerClub@gmail.com";
		
//		ProjectFile pf = null;
//		pf = new ProjectFile();
//		pf.name = "Phosphate";
//		pf.fileUrl = "https://cdn.thingiverse.com/assets/a7/ed/04/7a/fc/Phosphate_2mm.stl";
//		pf.imageUrl = "https://cdn.thingiverse.com/assets/6f/7a/e6/af/ea/featured_preview_Phosphate_2mm.png";
//		proj.files.put("phosphate", pf);
//		pf = new ProjectFile();
//		pf.name = "Thymine";
//		pf.fileUrl = "https://cdn.thingiverse.com/assets/46/fb/02/70/0c/Thymine_2mm.stl";
//		pf.imageUrl = "https://cdn.thingiverse.com/assets/94/ae/98/4b/c7/featured_preview_Thymine_2mm.png";
//		proj.files.put("thymine", pf);
//		pf = new ProjectFile();
//		pf.name = "Deoxyribose";
//		pf.fileUrl = "https://cdn.thingiverse.com/assets/0e/36/e6/89/a2/Deoxyribose_Solid.stl";
//		pf.imageUrl = "https://cdn.thingiverse.com/assets/58/1b/8d/cf/a7/featured_preview_Deoxyribose_Solid.png";
//		proj.files.put("deoxyribose", pf);
		
//		proj.prints.add(new Print());
		Print print = proj.prints.get(0);
		print.printId = "deoxyribose";
		print.timestamp = System.currentTimeMillis();
		print.notes = "Printing at 60% angle prevents Braille from having supports. Take tweezers and gently remove supports. Use mild sandpaper to smooth area where supports were.";
		print.printerType = PrinterType.MONOPRICE_ULTIMATE_2;
		print.filamentType = FilamentType.PLA;
		print.filamentBrand = FilamentBrand.HATCHBOX;
		print.extruderTemperature = 205;
		print.printBedTemperature = 53;
		
		print.options.put("Print Orientation", "vertical, as is");
		print.options.put("Layer Height", "0.20mm");
		print.options.put("Initial Layer Height", "0.20mm");
		print.options.put("Number of Walls", "2");
		
		System.out.println(gson.toJson(proj));
		try {
			FileWriter writer = new FileWriter("projects.json");
			gson.toJson(proj, writer);
			writer.flush();
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
}
