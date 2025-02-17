package com.nath.counsellors_portal.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.nath.counsellors_portal.dto.DashboardResponse;
import com.nath.counsellors_portal.entity.Counsellor;
import com.nath.counsellors_portal.entity.Enquiry;
import com.nath.counsellors_portal.repository.CounsellorRepository;
import com.nath.counsellors_portal.repository.EnquiryRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CounsellorServiceImpl implements CounsellorService {

	private CounsellorRepository counsellorRepo;

	private EnquiryRepository enquiryRepo;
	
	@Override
	public Counsellor findByEmail(String email) {
		
		return counsellorRepo.findByEmail(email);
	}

	@Override
	public boolean register(Counsellor counsellor) {
		Counsellor saveCounsellor = counsellorRepo.save(counsellor);

		if (null != saveCounsellor.getCounsellorId()) {
			return true;
		}
		return false;
	}

	@Override
	public Counsellor login(String email, String pwd) {
		Counsellor counsellor = counsellorRepo.findByEmailAndPassword(email, pwd);
		return counsellor;
	}

	@Override
	public DashboardResponse getDashboardInf(Long counsellorId) {
		DashboardResponse dashResponse = new DashboardResponse();
		List<Enquiry> enqList = enquiryRepo.getEnquriesByCounsellorId(counsellorId);
		int totalEnq = enqList.size();

		int entrolledEnqs = enqList.stream().filter(e -> e.getEnqStatus().equals("Entrolled"))
				.collect(Collectors.toList()).size();
		
		int lostEnqs = enqList.stream().filter(e -> e.getEnqStatus().equals("Lost"))
				.collect(Collectors.toList()).size();

		int openEnqs = enqList.stream().filter(e -> e.getEnqStatus().equals("Open")).collect(Collectors.toList())
				.size();
		
		dashResponse.setTotalEnqs(totalEnq);
		dashResponse.setEnrolledEnqs(entrolledEnqs);
		dashResponse.setOpenEnqs(openEnqs);
		dashResponse.setLostEnqs(lostEnqs);

		return dashResponse;
	}

	

}
