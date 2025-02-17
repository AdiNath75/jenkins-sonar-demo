package com.nath.counsellors_portal.service;

import com.nath.counsellors_portal.dto.DashboardResponse;
import com.nath.counsellors_portal.entity.Counsellor;

public interface CounsellorService {
	
	public Counsellor findByEmail(String email);
	
	public boolean  register(Counsellor counsellor);
	
	public Counsellor login(String email, String pwd);
	
	public DashboardResponse getDashboardInf(Long counsellorId);

}
