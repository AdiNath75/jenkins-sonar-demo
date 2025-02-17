package com.nath.counsellors_portal.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.nath.counsellors_portal.dto.ViewEnqFilterRequest;
import com.nath.counsellors_portal.entity.Enquiry;
import com.nath.counsellors_portal.service.EnquiryService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;

@Controller
@AllArgsConstructor
public class EnquiryController {

	private EnquiryService enquiryService;

	@GetMapping("/enquiry")
	public String addEnquiryPage(Model model) {

		Enquiry enquiry  = new Enquiry();
		model.addAttribute("enquiry", enquiry);

		return "enquiryForm";
	}
	
	@GetMapping("/editEnq")
	public String editEnquiry(@RequestParam("enqId") Long enqId, Model model) {
		Enquiry enquiry = enquiryService.getEnquiryById(enqId);
		
		model.addAttribute("enquiry", enquiry);
		
		return "enquiryForm";
	}
	

	@GetMapping("/view-enquiries")
	public String getEnquries(HttpServletRequest request, Model model) {
		// get existing session obj
		HttpSession session = request.getSession(false);
		Long coId = (Long) session.getAttribute("counsellorId");

		List<Enquiry> enqList = enquiryService.getAllEnquiries(coId);
		model.addAttribute("enquiries", enqList);
		
		ViewEnqFilterRequest filterReq = new ViewEnqFilterRequest();
		model.addAttribute("viewEnqsFilterRequest", filterReq);

		return "viewEnqsPage";
	}
	
	@PostMapping("/filter-enqs")
	public String filterEnquiries(ViewEnqFilterRequest viewEnqsFilterRequest, HttpServletRequest request, Model model) {
		HttpSession session = request.getSession(false);
		Long coId = (Long) session.getAttribute("counsellorId");
		
		List<Enquiry> enqList = enquiryService.getEnquiresWithFilter(viewEnqsFilterRequest, coId);
		model.addAttribute("enquiries", enqList);
		
		ViewEnqFilterRequest filterReq = new ViewEnqFilterRequest();
		model.addAttribute("viewEnqsFilterRequest", filterReq);
		return "viewEnqsPage";
	}

	@PostMapping("/addEnq") 
	public String handleAddEnquiry(@ModelAttribute("enquiry") Enquiry enquiry, HttpServletRequest req, Model model)
			throws Exception {

		HttpSession sess = req.getSession(false);

		Long coId = (Long) sess.getAttribute("counsellorId");

		boolean isSaved = enquiryService.addEnquiry(enquiry, coId);

		if (isSaved) {
			model.addAttribute("smsg", "Enquiry Added");
		} else {
			model.addAttribute("emsg", "Fail to Add Enquiry");
		}
		return "enquiryForm";
	}
}
