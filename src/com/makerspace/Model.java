package com.makerspace;

public class Model {
	public String fileName;
	public String fileImageUrl;
	public String fileDescription;
	
	public double layerHeight;
	public double initialLayerHeight;
	
	public int wallCount;
	public int topLayerCount;
	public int bottomLayerCount;
	
	public int infillPercent;
	
	public String supports; //TODO come up with better representation
	public String bedAdhesion;
	public boolean enableRetraction;
	
	public String notes;
}
