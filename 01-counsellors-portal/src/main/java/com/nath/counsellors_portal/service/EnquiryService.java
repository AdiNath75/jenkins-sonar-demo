package com.nath.counsellors_portal.service;

import java.util.List;

import com.nath.counsellors_portal.dto.ViewEnqFilterRequest;
import com.nath.counsellors_portal.entity.Enquiry;

public interface EnquiryService {
	
	public boolean addEnquiry(Enquiry enq, Long coId) throws Exception;
	
	public Enquiry getEnquiryById(Long enqId);
	
	public List<Enquiry> getAllEnquiries(Long counsellorld);
	
	public List<Enquiry> getEnquiresWithFilter(ViewEnqFilterRequest filterReq, Long counsellorld);

}
