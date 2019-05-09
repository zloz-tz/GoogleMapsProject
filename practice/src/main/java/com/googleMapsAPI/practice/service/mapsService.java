package com.googleMapsAPI.practice.service;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;

import javax.imageio.ImageIO;

import com.google.gson.Gson;
import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.StaticMapsApi;
import com.google.maps.StaticMapsRequest;
import com.google.maps.StaticMapsRequest.Markers;
import com.google.maps.StaticMapsRequest.Markers.MarkersSize;
import com.google.maps.errors.ApiException;
import com.google.maps.model.GeocodingResult;
import com.google.maps.model.LatLng;
import com.google.maps.model.Size;

public class MapsService {

	//private static String apiKey = "APIKEY_GOES_HERE";
	private GeoApiContext context = null;
	private Gson gson;
	
	// Constructor 
//	public MapsService() 
//	{
//		context = new GeoApiContext.Builder().apiKey(apiKey).build();
//		gson = new Gson();
//	}
	public MapsService(String key)
	{
		context = new GeoApiContext.Builder().apiKey(key).build();
		gson = new Gson();
	}
	
	// Converts Street address into coordinates
	public String getCoordinatesFromAddress(String address) throws ApiException, InterruptedException, IOException {
		GeocodingResult[] results = GeocodingApi.geocode(context, address).await();
		return gson.toJson(results[0].geometry.location);
	}
	
	public String getCoordinatesFromAddress2(String address) throws ApiException, InterruptedException, IOException {
		GeocodingResult[] results = GeocodingApi.geocode(context, address).await();
		return gson.toJson(results[0].geometry.location);
	}
	
	// Converts coordinates into street address
	public String getAddressFromCoordinates(double lat,  double lng) throws ApiException, InterruptedException, IOException {
		LatLng coordinates = new LatLng(lat,lng);
		GeocodingResult[] results = GeocodingApi.reverseGeocode(context,coordinates).await();
		return gson.toJson(results[0].formattedAddress);
	}
	
	// Takes coordinates and returns a string image of the location in the map
	public String getMapStringByCoordinates(double lat, double lng, int zoom, String color, String label)
			throws IOException, ApiException, InterruptedException {
		
		LatLng coordinates = new LatLng(lat,lng);
		Markers markers = new Markers();
		markers.addLocation(coordinates);
		markers.color(color);
		markers.label(label);
		
		StaticMapsRequest map = StaticMapsApi.newRequest(context,new Size(640,480)).center(coordinates).zoom(zoom).markers(markers);
		ByteArrayInputStream in = new ByteArrayInputStream(map.await().imageData);
		String base64 = base64Converter(in);
		
	    return base64;
	}
	
	// Takes coordinates and returns a map string of the location
	public String getMapStringByAddress(String address, int zoom, String color, String label) 
			throws IOException, ApiException, InterruptedException {
		
		Markers markers = new Markers();
		markers.addLocation(address);
		markers.color(color);
		markers.label(label);
		
		StaticMapsRequest map = StaticMapsApi.newRequest(context,new Size(640,480)).center(address).zoom(zoom).markers(markers);
		ByteArrayInputStream in = new ByteArrayInputStream(map.await().imageData);
		String base64 = base64Converter(in);

	    return base64;
	}
	
	/// Getting markers on the map, computing the center of the map by according to the markers
	public String getMarkersByAddress(String address, MarkersSize size, String color) 
			throws IOException, ApiException, InterruptedException  {
		
		Markers markers = new Markers();
		markers.color(color);
		markers.size(size);
		markers.addLocation(address);
		StaticMapsRequest map = StaticMapsApi.newRequest(context,new Size(640,480)).markers(markers);
		
		ByteArrayInputStream in = new ByteArrayInputStream(map.await().imageData);
		String base64 = base64Converter(in);
		 
	    return base64;
	
	}
	
	public String getMarkersByCoords(double lat, double lng, MarkersSize size, String color) 
			throws IOException, ApiException, InterruptedException  {
		
		LatLng coordinates = new LatLng(lat,lng);
		Markers markers = new Markers();
		markers.color(color);
		markers.size(size);
		markers.addLocation(coordinates);
		StaticMapsRequest map = StaticMapsApi.newRequest(context,new Size(640,480)).markers(markers);
		
		ByteArrayInputStream in = new ByteArrayInputStream(map.await().imageData);
		String base64 = base64Converter(in);
		
	    return base64;
	}
	
	private String base64Converter(ByteArrayInputStream in) throws IOException
	{
		BufferedImage img = ImageIO.read(in);
		ByteArrayOutputStream out=new ByteArrayOutputStream();
		ImageIO.write(img, "png", out );
		String base64 = Base64.getEncoder().encodeToString(out.toByteArray());
		 
	    return base64;
	}
	
}
