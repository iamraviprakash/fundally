package in.iiits.fundally.chiefadmin.controllers;

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

import in.iiits.fundally.chiefadmin.classes.DisplayInstituteRequestProfile;
import in.iiits.fundally.chiefadmin.classes.DisplayProjectRequestProfile;
import in.iiits.fundally.chiefadmin.classes.DisplayRequest;
import in.iiits.fundally.chiefadmin.entity.User;
import in.iiits.fundally.chiefadmin.services.ChiefAdminServices;

@Controller
@RequestMapping("/request")
public class Request {
	
	@Autowired
	private ChiefAdminServices chiefAdminServices;
	
	@GetMapping("/")
	public String showRequestPage(@CookieValue(value="sessionId", defaultValue="NoSession") String sessionId, Model model) {
		
		// Dealing with cookie
		if(sessionId.equals("NoSession") || chiefAdminServices.checkSessionStatus(sessionId, "chiefadmin").equals("INACTIVE")) {
			//redirect to login page 
			return "redirect:/request/redirectToLogin";
		}
	
		User user = chiefAdminServices.getUser(sessionId);
		
		// get the List of Request, New first.
		List<DisplayRequest> displayRequestList = chiefAdminServices.getAllDisplayRequestList();
		
		// bind the data to the model
		model.addAttribute("displayRequestList", displayRequestList);
		model.addAttribute("Name", user.getFirstName()+" "+user.getLastName());
		model.addAttribute("Department", user.getType());
		
		return "requestSection";

	}
	
	@GetMapping("/{source:institute|project}/{requestId}/view")
	public String showRequestProfile(@CookieValue(value="sessionId", defaultValue="NoSession") String sessionId, Model model, @PathVariable String source, @PathVariable String requestId, @RequestParam(defaultValue="") String message) {
		
		// Dealing with cookie
		if(sessionId.equals("NoSession") || chiefAdminServices.checkSessionStatus(sessionId, "chiefadmin").equals("INACTIVE")) {
			//redirect to login page 
			return "redirect:/request/redirectToLogin";
		}	
		
		User user = chiefAdminServices.getUser(sessionId);
		
		String backUrl = "<a href=\"/request/\">Back To List</a>";
		
		String approveUrl = null;
		String cancelUrl = null;
		
		String stageId = chiefAdminServices.getRequestStageId(requestId);
		
		if(stageId != null && (stageId.equals("RV") || stageId.equals("QP"))) {
			approveUrl = "<a href=\"approve\" style=\"display: inline-block; margin:10px; background: #536DFE; color: white; padding: 5px 15px 5px 15px; text-decoration: none;\">Approve</a>";
			cancelUrl = "<a href=\"cancel\" style=\"display: inline-block; margin:10px; background: #536DFE; color: white; padding: 5px 15px 5px 15px; text-decoration: none;\">Cancel</a>";
		}
		else {
			approveUrl = "No option available!";
		}
		
		if(source.equals("institute")) {
			
			DisplayInstituteRequestProfile displayInstituteRequestProfile = chiefAdminServices.getDisplayInstituteRequestProfile(requestId);
			
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
			
			DisplayProjectRequestProfile displayProjectRequestProfile = chiefAdminServices.getDisplayProjectRequestProfile(requestId);
			
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
		model.addAttribute("backUrl", backUrl);
		model.addAttribute("message", message);
		model.addAttribute("approveUrl", approveUrl);
		model.addAttribute("cancelUrl", cancelUrl);
		model.addAttribute("sessionId", sessionId);
		model.addAttribute("activeRequest", "id=\"active\"");
		
		return "requestProfile";
	}
	
	@GetMapping("/{source:institute|project}/{requestId}/approve")
	public String approveRequest(@CookieValue(value="sessionId", defaultValue="NoSession") String sessionId, Model model, @PathVariable String requestId, @PathVariable String source) {
		
		// Dealing with cookie
		if(sessionId.equals("NoSession") || chiefAdminServices.checkSessionStatus(sessionId, "chiefadmin").equals("INACTIVE")) {
			//redirect to login page 
			return "redirect:/request/redirectToLogin";
		}	
		
		String message = chiefAdminServices.approveRequest(source, requestId);
		
		model.addAttribute("message", message);
		
		return "redirect:/request/" + source + "/" + requestId + "/view";
	}
	
	@GetMapping("/{source:institute|project}/{requestId}/quotation/{quotationId}/approve")
	public String approveQuotation(@CookieValue(value="sessionId", defaultValue="NoSession") String sessionId, Model model, @PathVariable String source, @PathVariable String quotationId, @PathVariable String requestId){
		
		// Dealing with cookie
		if(sessionId.equals("NoSession") || chiefAdminServices.checkSessionStatus(sessionId, "chiefadmin").equals("INACTIVE")) {
			//redirect to login page 
			return "redirect:/request/redirectToLogin";
		}	
		
		String message = chiefAdminServices.approveQuotation(source, requestId, quotationId);
		
		model.addAttribute("message", message);
		
		return "redirect:/request/" + source + "/" + requestId + "/view";
	}   
	
	@GetMapping("/redirectToLogin")
	public ModelAndView redirectToLoginPage(){
		
		// in urls use chiefadmin instead of chiefAdmin
		
		String LoginPageUrl = "http://accounts.fundally.iiits.ac.in/auth/chiefadmin";
		return new ModelAndView("redirect:" + LoginPageUrl);
	}
	
}
