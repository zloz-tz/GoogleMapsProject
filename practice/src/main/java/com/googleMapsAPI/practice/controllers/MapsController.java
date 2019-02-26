package com.googleMapsAPI.practice.controllers;




import javax.websocket.server.PathParam;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import com.classes.LocationInfo;
import com.google.maps.StaticMapsRequest.Markers.MarkersSize;
import com.googleMapsAPI.practice.service.mapsService;


@RestController
public class MapsController {
	
	
	mapsService mapService = new mapsService();
	
	// Converts Street address into its coordinates
	@GetMapping("/getCoordinates")
	public LocationInfo retrieveCoordinates(@PathParam("address)") String address)
	{
		LocationInfo location = new LocationInfo();
		
		try {
			location.setCoordinates(
				mapService.getCoordinatesFromAddress(address));
			location.setAddressData(address);
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		return location;
	}
	 
	// Converts coordinates into its street address
	@GetMapping("/getAddress")
	public LocationInfo retrieveAddress(@PathParam("lat") double lat, @PathParam("lng") double lng) 
	{
		LocationInfo location = new LocationInfo();
	
		try {
			location.setAddressData(
				mapService.getAddressFromCoordinates(lat, lng));
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		return location;
	}
	
	// Takes coordinates and returns a string image of the location in the map
	@GetMapping("/getMapByCoordinates")
	public LocationInfo retrieveMap(@PathParam("lat") double lat, @PathParam("lng") double lng, @PathParam("zoom") int zoom
			, @PathParam ("color") String color, @PathParam ("label") String label)
	{	
		LocationInfo location = new LocationInfo();
		
		try {
			String mapString = mapService.getMapStringByCoordinates(lat, lng, zoom, color, label);
			location.setImageData(mapString);
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		
		return location;
		
	}
	
	// Takes the street address and returns an map image of the location
	// Upgraded - returns a labeled marker on the map 
	@GetMapping("/getMapByAddress")
	public LocationInfo retrieveMapByAddress(@PathParam("address") String address, @PathParam("zoom") int zoom
			, @PathParam ("color") String color, @PathParam ("label") String label)
	{	
		LocationInfo location = new LocationInfo();
		
		try {
			String mapString = mapService.getMapStringByAddress(address, zoom, color, label);
			location.setImageData(mapString);
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		
		return location;
		
	}
	///////////////////////////////// check about it
	@GetMapping("/getMarkersByAddress")
	public LocationInfo retrieveMarkersByAddress(@PathParam("address") String address, 
			@PathParam("size") MarkersSize size, @PathParam ("color") String color)
			
	{	
		LocationInfo location = new LocationInfo();
		
		try {
			String mapString = mapService.getMarkersByAddress(address, size, color);
			location.setImageData(mapString);
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		
		return location;
		
	}
	
}
