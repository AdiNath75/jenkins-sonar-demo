package com.nath.counsellors_portal.service;

import java.util.List;

import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import com.nath.counsellors_portal.dto.ViewEnqFilterRequest;
import com.nath.counsellors_portal.entity.Counsellor;
import com.nath.counsellors_portal.entity.Enquiry;
import com.nath.counsellors_portal.repository.CounsellorRepository;
import com.nath.counsellors_portal.repository.EnquiryRepository;

import io.micrometer.common.util.StringUtils;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class EnquiryServiceImpl implements EnquiryService {

	private EnquiryRepository enquiryRepo;

	private CounsellorRepository counsellorRepo;

	@Override
	public boolean addEnquiry(Enquiry enq, Long counsellorId) throws Exception {

		Counsellor counsellor = counsellorRepo.findById(counsellorId).orElse(null);

		if (counsellor == null) {
			throw new Exception("Counsellor not found");
		}
		enq.setCounsellor(counsellor);
		Enquiry save = enquiryRepo.save(enq);

		if (save.getEnqId() != null) {
			return true;
		}
		return false;
	}

	@Override
	public Enquiry getEnquiryById(Long enqId) {
		return enquiryRepo.findById(enqId).orElse(null);
	}

	@Override
	public List<Enquiry> getAllEnquiries(Long counsellorld) {
		return enquiryRepo.getEnquriesByCounsellorId(counsellorld);
	}

	@Override
	public List<Enquiry> getEnquiresWithFilter(ViewEnqFilterRequest filterReq, Long counsellorld) {

		// QBE implementation(Query By Example
		Enquiry enq = new Enquiry();

		if (StringUtils.isNotEmpty(filterReq.getClassMode())) {
			enq.setClassMode(filterReq.getClassMode());
		}

		if (StringUtils.isNotEmpty(filterReq.getCourseName())) {
			enq.setCourseName(filterReq.getCourseName());
		}

		if (StringUtils.isNotEmpty(filterReq.getEnqStatus())) {
			enq.setEnqStatus(filterReq.getEnqStatus());
		}
		Counsellor couns = counsellorRepo.findById(counsellorld).orElse(null);

		enq.setCounsellor(couns);

		Example<Enquiry> ex = Example.of(enq);

		List<Enquiry> listEnq = enquiryRepo.findAll(ex);

		return listEnq;
	}

}
