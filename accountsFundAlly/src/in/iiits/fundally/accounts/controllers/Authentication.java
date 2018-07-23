package in.iiits.fundally.accounts.controllers;



import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import in.iiits.fundally.accounts.entity.User;
import in.iiits.fundally.accounts.services.AccountsServices;


@Controller
@RequestMapping("/auth")
public class Authentication {
	
	
	// Need to inject DAO into this controller
	@Autowired
	private AccountsServices accountsServices;
	
	@GetMapping(value = "/{userType:admin|faculty|finance|chiefadmin|procurement}")
	public String displayLoginPage(Model model, @PathVariable String userType, @RequestParam(defaultValue="") String message) {	
		
		model.addAttribute("userType", userType);
		model.addAttribute("message", message);
		model.addAttribute("userLabel", userType.toUpperCase());
		return "login";
	}

	@PostMapping(value = "/{userType:admin|faculty|finance|chiefadmin|procurement}/processForm")
	public String processFormLoginPage(HttpServletResponse response, Model model, @PathVariable String userType, @RequestParam String emailId, @RequestParam String password) {
		
		// get credentials
		//// get the users from Service
		User user = accountsServices.getUser(emailId, userType);
				
		// check credentials
		
		if(user != null ) {
			
			// check password
			if(password.equals(user.getPassword())){
				
				// create session 
				String sessionId = accountsServices.createUserSession(user.getId());
				
				// set cookie of value equal to sessionId
				//Cookie sessionIdCookie = new Cookie("sessionId", sessionId);
				//sessionIdCookie.setPath("/");
				
				//response.addCookie(sessionIdCookie);
				//redirect to index page
				model.addAttribute("token", sessionId);
				
				return "redirect:/auth/"+userType+"/redirectToHome";
			}
			else {
				
				// redirect to login page message invalid credentials
				model.addAttribute("message", "Authentication Failed");
				return "redirect:/auth/"+userType;
			}
			
		}
		else {
			// If wrong, redirect to login page
			model.addAttribute("message", "Authentication Failed");
			return "redirect:/auth/"+userType;
		} 
	}
	
	
	@GetMapping(value="/{userType:admin|faculty|finance|chiefadmin|procurement}/redirectToHome")
	public ModelAndView redirectToHome(Model model, @PathVariable String userType, @RequestParam String token) {
		
		String HomeUrl = null;
		
		if(userType.equals("admin")) {
			HomeUrl = "http://admin.fundally.iiits.ac.in";
		}
		else if(userType.equals("faculty")) {
			HomeUrl = "http://faculty.fundally.iiits.ac.in";
		}
		else if(userType.equals("finance")) {
			HomeUrl = "http://finance.fundally.iiits.ac.in";
		}
		else if(userType.equals("chiefadmin")) {
			HomeUrl = "http://chiefadmin.fundally.iiits.ac.in";
		}
		else if(userType.equals("procurement")) {
			HomeUrl = "http://procurement.fundally.iiits.ac.in";
		}
		
		model.addAttribute("token", token);
		return new ModelAndView("redirect:" + HomeUrl);
	}
	
}
