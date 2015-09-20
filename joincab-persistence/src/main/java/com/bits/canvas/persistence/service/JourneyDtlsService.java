/**
 * This class will provide the services for User journey details(hop/post)
 */
package com.bits.canvas.persistence.service;

import javax.transaction.Transactional;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bits.canvas.common.constant.HopStatus;
import com.bits.canvas.common.constant.PostStatus;
import com.bits.canvas.persistence.entity.CabHopDetails;
import com.bits.canvas.persistence.entity.CabPostDetails;
import com.bits.canvas.persistence.repo.CabHopDetailsRepo;
import com.bits.canvas.persistence.repo.CabPostDetailsRepo;

/**
 * 
 * @author bk
 *
 */

@Component
public class JourneyDtlsService {

	private static final Logger LOG = Logger.getLogger(JourneyDtlsService.class);
	
	@Autowired 
	CabPostDetailsRepo cabPostDetailsRepo;
	
	@Autowired
	CabHopDetailsRepo cabHopDetailsRepo;
	
	
	@Transactional
	public boolean postRequestDelete(CabPostDetails cabPostDetails) throws Exception{
		boolean succeeded = false;
		try{
			/*soft delete for post*/
			int postStatus = cabPostDetails.getPostStatus();
			cabPostDetails.setPostStatus(PostStatus.POST_CANCELLED.getCode());
			cabPostDetails = cabPostDetailsRepo.save(cabPostDetails);
			
			if(postStatus == PostStatus.REQUEST_RECEIVED.getCode()){
				CabHopDetails cabHopDetails = cabHopDetailsRepo.findByPostIdAndStatus(cabPostDetails.getId(), HopStatus.REQUESTED.getCode());
				cabHopDetails.setStatus(HopStatus.CANCELLED.getCode());
				cabHopDetailsRepo.save(cabHopDetails);
				CabHopDetails cabHopDetails2 = prepareNewHopRequest(cabHopDetails);
				cabHopDetailsRepo.save(cabHopDetails2);
			}else if(postStatus == PostStatus.REQUEST_CONFIRMED.getCode()){
				CabHopDetails cabHopDetails = cabHopDetailsRepo.findByPostIdAndStatus(cabPostDetails.getId(), HopStatus.JOINED.getCode());
				cabHopDetails.setStatus(HopStatus.CANCELLED.getCode());
				cabHopDetailsRepo.save(cabHopDetails);
				CabHopDetails cabHopDetails2 = prepareNewHopRequest(cabHopDetails);
				cabHopDetailsRepo.save(cabHopDetails2);
			}
			
		}catch(Exception ex){
			LOG.error("Exception encountered in updateDeletionInCabPostAndCabHop of JourneyDtlsService"+ex);
			throw ex;
		}
		return succeeded;
	}
	
	public CabHopDetails prepareNewHopRequest(CabHopDetails cabHopDetails){
		CabHopDetails cabHopDetails2 = new CabHopDetails();
		cabHopDetails2.setHopFormattedAddress(cabHopDetails.getHopFormattedAddress());
		cabHopDetails2.setHopperCount(cabHopDetails.getHopperCount());
		cabHopDetails2.setStatus(HopStatus.WATCH.getCode());
		cabHopDetails2.setUserId(cabHopDetails.getUserId());
		return cabHopDetails2;
	}
}
