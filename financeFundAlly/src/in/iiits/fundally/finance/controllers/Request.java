package in.iiits.fundally.finance.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import in.iiits.fundally.finance.classes.DisplayInstituteRequestProfile;
import in.iiits.fundally.finance.classes.DisplayProjectRequestProfile;
import in.iiits.fundally.finance.classes.DisplayRequest;
import in.iiits.fundally.finance.entity.User;
import in.iiits.fundally.finance.services.FinanceServices;

@Controller
@RequestMapping("/request")
public class Request {
	
	@Autowired
	private FinanceServices financeServices;
	
	@GetMapping("/")
	public String showRequestPage(@CookieValue(value="sessionId", defaultValue="NoSession") String sessionId, Model model) {
		
		// Dealing with cookie
		if(sessionId.equals("NoSession") || financeServices.checkSessionStatus(sessionId, "finance").equals("INACTIVE")) {
			//redirect to login page 
			return "redirect:/request/redirectToLogin";
		}
	
		User user = financeServices.getUser(sessionId);
		
		// get the List of Request, New first.
		List<DisplayRequest> displayRequestList = financeServices.getAllDisplayRequestList();
		
		// bind the data to the model
		model.addAttribute("displayRequestList", displayRequestList);
		model.addAttribute("Name", user.getFirstName()+" "+user.getLastName());
		model.addAttribute("Department", user.getType());
		
		return "requestSection";

	}
	
	@GetMapping("/{source:institute|project}/{requestId}/view")
	public String showRequestProfile(@CookieValue(value="sessionId", defaultValue="NoSession") String sessionId, Model model, @PathVariable String requestId, @PathVariable String source, @RequestParam(defaultValue="") String message) {
		
		// Dealing with cookie
		if(sessionId.equals("NoSession") || financeServices.checkSessionStatus(sessionId, "finance").equals("INACTIVE")) {
			//redirect to login page 
			return "redirect:/request/redirectToLogin";
		}	
		
		User user = financeServices.getUser(sessionId);
		
		String payUrl = null;
		
		String stageId = financeServices.getRequestStageId(requestId);
		
		String backUrl = "<a href=\"/request/\">Back To List</a>";
		
		if(stageId != null && (stageId.equals("RP") || stageId.equals("RA"))) {
			payUrl = "<a href=\"pay\" style=\"display: inline-block; margin:10px; background: #536DFE; color: white; padding: 5px 15px 5px 15px; text-decoration: none;\">Pay Amount</a>";
		}
		else {
			payUrl = "No option available!";
		}
		
		if(source.equals("institute")) {
			
			DisplayInstituteRequestProfile displayInstituteRequestProfile = financeServices.getDisplayInstituteRequestProfile(requestId);
			
			if(displayInstituteRequestProfile != null) {
				
				model.addAttribute("account", displayInstituteRequestProfile.getInstituteAccount());
				model.addAttribute("user", displayInstituteRequestProfile.getUser());
				model.addAttribute("resource", displayInstituteRequestProfile.getInstituteResource());
				model.addAttribute("request", displayInstituteRequestProfile.getInstituteRequest());
				model.addAttribute("transaction", displayInstituteRequestProfile.getInstituteTransaction());
				model.addAttribute("requestStageList", displayInstituteRequestProfile.getInstituteRequestStageList());
				model.addAttribute("quotationList", displayInstituteRequestProfile.getInstituteQuotationList());
				model.addAttribute("resourceDocumentList", displayInstituteRequestProfile.getInstituteResourceDocumentList());
				model.addAttribute("source", "institute");
			}
		}
		else if(source.equals("project")) {
			
			DisplayProjectRequestProfile displayProjectRequestProfile = financeServices.getDisplayProjectRequestProfile(requestId);
			
			if(displayProjectRequestProfile != null) {
				
				model.addAttribute("project", displayProjectRequestProfile.getProject());
				model.addAttribute("account", displayProjectRequestProfile.getProjectAccount());
				model.addAttribute("user", displayProjectRequestProfile.getUser());
				model.addAttribute("resource", displayProjectRequestProfile.getProjectResource());
				model.addAttribute("request", displayProjectRequestProfile.getProjectRequest());
				model.addAttribute("transaction", displayProjectRequestProfile.getProjectTransaction());
				model.addAttribute("requestStageList", displayProjectRequestProfile.getProjectRequestStageList());
				model.addAttribute("quotationList", displayProjectRequestProfile.getProjectQuotationList());
				model.addAttribute("resourceDocumentList", displayProjectRequestProfile.getProjectResourceDocumentList());
				model.addAttribute("source", "project");
			}
		}
		
		model.addAttribute("Name", user.getFirstName()+" "+user.getLastName());
		model.addAttribute("Department", user.getType());
		model.addAttribute("message", message);
		model.addAttribute("payUrl", payUrl);
		model.addAttribute("backUrl", backUrl);
		model.addAttribute("sessionId", sessionId);
		
		return "requestProfile";
	}
	
	@GetMapping("/{source:institute|project}/{requestId}/pay")
	public String payRequest(@CookieValue(value="sessionId", defaultValue="NoSession") String sessionId, Model model, @PathVariable String requestId, @PathVariable String source) {
		
		// Dealing with cookie
		if(sessionId.equals("NoSession") || financeServices.checkSessionStatus(sessionId, "finance").equals("INACTIVE")) {
			//redirect to login page 
			return "redirect:/request/redirectToLogin";
		}	
		
		String message = financeServices.payRequest(source, requestId);
		
		model.addAttribute("message", message);
		
		return "redirect:/request/" + source + "/" + requestId + "/view";
	}
	
	
	@GetMapping("/redirectToLogin")
	public ModelAndView redirectToLoginPage(){
		
		String LoginPageUrl = "http://accounts.fundally.iiits.ac.in/auth/finance";
		return new ModelAndView("redirect:" + LoginPageUrl);
	}
	
}
