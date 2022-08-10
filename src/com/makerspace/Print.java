package com.makerspace;

import java.util.LinkedHashMap;

public class Print {
	public static enum PrinterType {
		MONOPRICE_ULTIMATE_2;
	}
	
	public static enum FilamentType {
		PLA;
	}
	
	public static enum FilamentBrand {
		HATCHBOX;
	}
	
	public String printId;
	public long timestamp;
	public String notes;
	
	public PrinterType printerType;
	public FilamentType filamentType;
	public FilamentBrand filamentBrand;
	
	public int extruderTemperature;
	public int printBedTemperature;
	
	public LinkedHashMap<String, String> options = new LinkedHashMap<>();
}
