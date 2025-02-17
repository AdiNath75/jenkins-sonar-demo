package com.nath.counsellors_portal.dto;

import lombok.Data;

@Data
public class DashboardResponse {
	
	private Integer totalEnqs;
	private Integer openEnqs;
	private Integer enrolledEnqs;
	private Integer lostEnqs;

}
