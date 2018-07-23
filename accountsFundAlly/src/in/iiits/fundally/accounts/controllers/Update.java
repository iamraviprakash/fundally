package in.iiits.fundally.accounts.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import in.iiits.fundally.accounts.services.AccountsServices;

@Controller
@RequestMapping("/update")
public class Update {

	@Autowired
	private AccountsServices accountsServices;
	
	@GetMapping("/password/{userType:admin|faculty|finance|chiefadmin|procurement}")
	public String showChangePasswordForm(@RequestParam(defaultValue="") String message, @RequestParam String sessionId, Model model, @PathVariable String userType) {
		// Dealing with cookie	
		// check session If active redirect it to home page
		if(!sessionId.equals("NoSession") && accountsServices.checkSessionStatus(sessionId, userType).equals("INACTIVE"))
		{
			model.addAttribute("message", "Invalid Session!");
			return "redirect:/auth/"+userType;
		}

		model.addAttribute("message", message);
		model.addAttribute("sessionId", sessionId);
		
		return "changePasswordForm";
	}
	
	@PostMapping("/password/{userType:admin|faculty|finance|chiefadmin|procurement}/submit")
	public String changePassword(@RequestParam String newPassword, @RequestParam String confirmPassword, @PathVariable String userType, @RequestParam String sessionId, Model model){
		// Dealing with cookie	
		// check session If active redirect it to home page
		if(!sessionId.equals("NoSession") && accountsServices.checkSessionStatus(sessionId, userType).equals("INACTIVE"))
		{
			model.addAttribute("message", "Invalid Session!");
			return "redirect:/auth/"+userType;
		}
		else if(!newPassword.equals(confirmPassword)) {

			model.addAttribute("message", "Password is not matching!");
			return "redirect:/update/password/"+userType;
		}
		// update password
		String message = accountsServices.changePassword(sessionId, newPassword);
				
		model.addAttribute("message", message);
		return "redirect:/auth/"+userType;
	}
}
