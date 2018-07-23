package in.iiits.fundally.finance.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import in.iiits.fundally.finance.services.FinanceServices;

@Controller
@RequestMapping("/update")
public class Update {

	@Autowired
	private FinanceServices financeServices;
	
	@GetMapping("/password")
	public ModelAndView logoutUser(@CookieValue(value="sessionId", defaultValue="NoSession") String sessionId) {
		
		// Dealing with cookie
		if(sessionId.equals("NoSession") || financeServices.checkSessionStatus(sessionId, "finance").equals("INACTIVE")) {
			//redirect to login page 
			String LogoutPageUrl = "http://accounts.fundally.iiits.ac.in/auth/finance";
			return new ModelAndView("redirect:" + LogoutPageUrl);
			
		}	

		String LogoutPageUrl = "http://accounts.fundally.iiits.ac.in/update/password/finance?sessionId=" + sessionId;
		return new ModelAndView("redirect:" + LogoutPageUrl);
	}
}
