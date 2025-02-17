package com.nath.counsellors_portal.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.nath.counsellors_portal.dto.DashboardResponse;
import com.nath.counsellors_portal.entity.Counsellor;
import com.nath.counsellors_portal.service.CounsellorService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;

@Controller
@AllArgsConstructor
public class CounsellorController {

	private CounsellorService counsellorService;

	@GetMapping("/")
	public String index(Model model) {
		Counsellor cobj = new Counsellor();
		model.addAttribute("counsellor", cobj);
		return "index";
	}

	@GetMapping("/register")
	public String register(Model model) {
		Counsellor cobj = new Counsellor();
		model.addAttribute("counsellor", cobj);
		return "register";
	}


	@PostMapping("/register")
	public String handleRegistration(Counsellor counsellor, Model model) {
		Counsellor byEmail = counsellorService.findByEmail(counsellor.getEmail());
		if (byEmail != null) {
			model.addAttribute("emsg", "Email Already registered");
			return "register";
		}

		boolean isRegistered = counsellorService.register(counsellor);

		if (isRegistered) {
			model.addAttribute("smsg", "Registration Sucessfull");
		} else {
			model.addAttribute("emsg", "Registration failed");
		}
		return "register";
	}

	@PostMapping("/login")
	public String login(Counsellor counsellor, HttpServletRequest request, Model model) {

		Counsellor c = counsellorService.login(counsellor.getEmail(), counsellor.getPassword());

		if (c == null) {
			model.addAttribute("emsg", "invalid credentials");
			return "index";
		} else {
			HttpSession sess = request.getSession(true);
			sess.setAttribute("counsellorId", c.getCounsellorId());

			DashboardResponse dobj = counsellorService.getDashboardInf(c.getCounsellorId());
			model.addAttribute("dashboardInf", dobj);
			return "redirect:/dashboard";
		}

	}

	@GetMapping("/dashboard")
	public String displayDashboard(HttpServletRequest req, Model model) {

		HttpSession sess = req.getSession(false);

		Long coId = (Long) sess.getAttribute("counsellorId");
		DashboardResponse dobj = counsellorService.getDashboardInf(coId);
		model.addAttribute("dashboardInf", dobj);

		return "dashboard";
	}

	@GetMapping("/logout")
	public String logout(HttpServletRequest req) {
		HttpSession sess = req.getSession(false);
		sess.invalidate();
		return "redirect:/";
	}

}
