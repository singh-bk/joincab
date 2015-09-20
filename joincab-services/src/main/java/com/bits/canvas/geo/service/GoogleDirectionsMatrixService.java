package com.bits.canvas.geo.service;

import org.springframework.stereotype.Component;

import com.google.maps.DistanceMatrixApi;
import com.google.maps.DistanceMatrixApiRequest;
import com.google.maps.GeoApiContext;
import com.google.maps.model.DistanceMatrix;

@Component
public class GoogleDirectionsMatrixService {

	public Long calculateDrivingDistance(String[] origins, String[] destinations){
		long distance = -1;
		GeoApiContext context = new GeoApiContext().setApiKey("AIzaSyBZTfYnlykYSrfdmNIPleMG7Iq3kGO9R5Q");
		
		DistanceMatrixApiRequest matrixApiRequest = DistanceMatrixApi.getDistanceMatrix(context, origins, destinations);
		try {
			DistanceMatrix distanceMatrix = matrixApiRequest.await();
			distance = distanceMatrix.rows[0].elements[0].distance.inMeters;
		} catch (Exception e) {
			distance = -1;
		}
		return distance;
	}
}
