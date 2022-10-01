package com.makerspace.types;

import java.util.LinkedHashMap;

public class Print {
	public static enum PrinterType {
		NONE, MONOPRICE_ULTIMATE_2, PRUSA_MINI_PLUS, PRUSA_MK3_PLUS, CREALITY_ENDER;
	}

	public static enum FilamentType {
		NONE, PLA;
	}

	public static enum FilamentBrand {
		NONE, HATCHBOX;
	}

	public static enum SlicerType {
		NONE, CURA, PRUSA;
	}

	public String printId = "";
	public long timestamp = -1;

	public PrinterType printerType = PrinterType.NONE;
	public FilamentType filamentType = FilamentType.NONE;
	public FilamentBrand filamentBrand = FilamentBrand.NONE;
	public SlicerType slicerType = SlicerType.NONE;

	public int extruderTemperature = -1;
	public int printBedTemperature = -1;

	public LinkedHashMap<String, String> options = new LinkedHashMap<>();
	public String notes = "";
}
