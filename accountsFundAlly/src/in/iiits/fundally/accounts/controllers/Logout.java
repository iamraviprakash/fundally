package in.iiits.fundally.accounts.controllers;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import in.iiits.fundally.accounts.services.AccountsServices;

@Controller
@RequestMapping("/logout")
public class Logout {
	
	@Autowired
	private AccountsServices accountsServices;
	
	// function to logout using session Id
	
	@GetMapping("/{userType:admin|faculty|finance|chiefadmin|procurement}")
	public String destroySession(HttpServletResponse response, Model model, @RequestParam  String sessionId, @PathVariable String userType) {
		
		// call function to log out
		String message = accountsServices.destroyUserSession(sessionId);
		
		model.addAttribute("message", message);
		
		return "redirect:/auth/"+userType;
		
	}

}
