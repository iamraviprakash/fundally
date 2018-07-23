package in.iiits.fundally.faculty.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import in.iiits.fundally.faculty.services.FacultyServices;

@Controller
@RequestMapping("/update")
public class Update {

	@Autowired
	private FacultyServices facultyServices;
	
	@GetMapping("/password")
	public ModelAndView logoutUser(@CookieValue(value="sessionId", defaultValue="NoSession") String sessionId) {
		
		// Dealing with cookie
		if(sessionId.equals("NoSession") || facultyServices.checkSessionStatus(sessionId, "faculty").equals("INACTIVE")) {
			//redirect to login page 
			String LogoutPageUrl = "http://accounts.fundally.iiits.ac.in/auth/faculty";
			return new ModelAndView("redirect:" + LogoutPageUrl);
			
		}	

		String LogoutPageUrl = "http://accounts.fundally.iiits.ac.in/update/password/faculty?sessionId=" + sessionId;
		return new ModelAndView("redirect:" + LogoutPageUrl);
	}
}
