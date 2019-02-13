package com.googleMapsAPI.practice.controllers;




import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;
import javax.imageio.ImageIO;
import javax.websocket.server.PathParam;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.StaticMapsApi;
import com.google.maps.StaticMapsRequest;
import com.google.maps.errors.ApiException;
import com.google.maps.model.GeocodingResult;
import com.google.maps.model.LatLng;
import com.google.maps.model.Size;


@RestController
public class MapsController {
	
	
	private String apiKey = "API KEY GOES HERE";
	GeoApiContext context = new GeoApiContext.Builder().apiKey(apiKey).build(); // Build GeoAPI context
//	private final int WIDTH = 640;
//	private final int HEIGHT = 480;
	
	// Converts Street address into its coordinates
	@GetMapping("/getAddress")
	public String retrieveCoordinates(@PathParam("address)") String address) throws ApiException, InterruptedException, IOException 
	{

		GeocodingResult[] results = GeocodingApi.geocode(context, address).await();
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		System.out.println(gson.toJson(results[0].geometry.location));
		return gson.toJson(results[0].geometry.location);
	}
	 
	// Converts coordinates into its street address
	@GetMapping("/getCoordinates")
	public String retrieveAddress(@PathParam("lat") double lat, @PathParam("lng") double lng) throws ApiException, InterruptedException, IOException
	{
		LatLng coordinates = new LatLng(lat,lng);
		GeocodingResult[] results = GeocodingApi.reverseGeocode(context,coordinates).await();
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		System.out.println(gson.toJson(results[0].addressComponents));
		return gson.toJson(results[0].formattedAddress);
	}
	
	// Takes the coordinates and returns an map image of the location
	// Incomplete - Issue with returning map image
	@GetMapping("/getMapByCoordinates")
	public BufferedImage retrieveMap(@PathParam("lat") double lat, @PathParam("lng") double lng, @PathParam("zoom") int zoom,
			@PathParam("size") Size size)
			throws ApiException, InterruptedException, IOException 
	{	
		LatLng coordinates = new LatLng(lat,lng);
		StaticMapsRequest map = StaticMapsApi.newRequest(context, new Size(640, 480)).center(coordinates).zoom(zoom);
	
		ByteArrayInputStream in = new ByteArrayInputStream(map.await().imageData);
	    BufferedImage img = ImageIO.read(in);
	      
	    return img;
		
	}
	
	// Takes the street address and returns an map image of the location
	// Incomplete - Issue with returning map image
	@GetMapping("/getMapByAddress")
	public String retrieveMapByAddress(@PathParam("center") String center, @PathParam("zoom") int zoom)
			throws ApiException, InterruptedException, IOException 
	{	
		StaticMapsRequest map = StaticMapsApi.newRequest(context,new Size(640,480)).center(center).zoom(zoom);
		
		ByteArrayInputStream in = new ByteArrayInputStream(map.await().imageData);
		BufferedImage img = ImageIO.read(in);
		ByteArrayOutputStream out=new ByteArrayOutputStream();
		ImageIO.write(img, "png", out );
		String base64 = Base64.getEncoder().encodeToString(out.toByteArray());

	    return base64;
		
	}
	
}
