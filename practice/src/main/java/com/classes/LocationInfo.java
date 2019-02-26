package com.classes;

//import java.util.ArrayList;

public class LocationInfo {
	private String addressData = "";
//	private ArrayList<Double> coordinates = new ArrayList<Double>();
//	private ArrayList<Double> center = new ArrayList<Double>();
	private String coordinates = "";
	private String imageData = "";
	
	public String getImageData() {
		return imageData;
	}
	public void setImageData(String imageData) {
		this.imageData = imageData;
	}
	public String getAddressData() {
		return addressData;
	}
	public void setAddressData(String addressData) {
		this.addressData = addressData;
	}
	public String getCoordinates() {
		return coordinates;
	}
	public void setCoordinates(String coordinates) {
		this.coordinates = coordinates;
	}
	
	
}
