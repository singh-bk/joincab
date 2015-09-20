package com.bits.canvas.mobile.helper;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bits.canvas.mobile.request.HopRequest;
import com.bits.canvas.mobile.transformer.HopTransformer;
import com.bits.canvas.persistence.entity.HopDetails;

@Component
public class HopHelper {

	@Autowired
	HopTransformer hopTransformer;
	/**
	 * populate hopdetails from hoprequest
	 * @param hopRequest
	 * @return
	 */
	public HopDetails populateHopDetails(HopRequest hopRequest){
		HopDetails hopDetails = new HopDetails();
		hopDetails.setHopId(UUID.randomUUID().toString());
		hopDetails = hopTransformer.transform(hopDetails, hopRequest);
		return hopDetails;
	}
}
