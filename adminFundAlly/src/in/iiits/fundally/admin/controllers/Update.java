package in.iiits.fundally.admin.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import in.iiits.fundally.admin.services.AdminServices;

@Controller
@RequestMapping("/update")
public class Update {

	@Autowired
	private AdminServices userService;
	
	@GetMapping("/password")
	public ModelAndView logoutUser(@CookieValue(value="sessionId", defaultValue="NoSession") String sessionId) {
		
		// Dealing with cookie
		if(sessionId.equals("NoSession") || userService.checkSessionStatus(sessionId, "admin").equals("INACTIVE")) {
			//redirect to login page 
			String LogoutPageUrl = "http://accounts.fundally.iiits.ac.in/auth/admin";
			return new ModelAndView("redirect:" + LogoutPageUrl);
			
		}	

		String LogoutPageUrl = "http://accounts.fundally.iiits.ac.in/update/password/admin?sessionId=" + sessionId;
		return new ModelAndView("redirect:" + LogoutPageUrl);
	}
}
