package in.iiits.fundally.procurement.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import in.iiits.fundally.procurement.services.ProcurementServices;

@Controller
@RequestMapping("/logout")
public class Logout {
	
	@Autowired
	private ProcurementServices procurementServices;
	
	@GetMapping("/")
	public ModelAndView logoutUser(@CookieValue(value="sessionId", defaultValue="NoSession") String sessionId) {
		
		// Dealing with cookie
		if(sessionId.equals("NoSession") || procurementServices.checkSessionStatus(sessionId, "procurement").equals("INACTIVE")) {
			//redirect to login page 
			String LogoutPageUrl = "http://accounts.fundally.iiits.ac.in/auth/procurement";
			return new ModelAndView("redirect:" + LogoutPageUrl);
		}	

		String LogoutPageUrl = "http://accounts.fundally.iiits.ac.in/logout/procurement?sessionId=" + sessionId;
		return new ModelAndView("redirect:" + LogoutPageUrl);
	}
	
}
