package in.iiits.fundally.faculty.controllers;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import in.iiits.fundally.faculty.classes.DisplayAccount;
import in.iiits.fundally.faculty.classes.DisplayInstituteRequestProfile;
import in.iiits.fundally.faculty.classes.DisplayRequest;
import in.iiits.fundally.faculty.classes.DisplayTransaction;
import in.iiits.fundally.faculty.classes.NewResourceRequestFormData;
import in.iiits.fundally.faculty.entity.User;
import in.iiits.fundally.faculty.services.FacultyServices;

@Controller
@RequestMapping("/institute")
public class Institute {
	
	@Autowired
	private FacultyServices facultyServices;
	
	// done
	@GetMapping("/")
	public String showInstitutePage(@CookieValue(value="sessionId", defaultValue="NoSession") String sessionId, Model model) {
		
		// Dealing with cookie
		if(sessionId.equals("NoSession") || facultyServices.checkSessionStatus(sessionId, "faculty").equals("INACTIVE")) {
			//redirect to login page 
			return "redirect:/institute/redirectToLogin";
		}
		
		User user = facultyServices.getUser(sessionId);
		
		// get Institute Account List
		List<DisplayAccount> displayAccountList = facultyServices.getInstituteAccountList(user.getId());
		
		// bind the data to model
		model.addAttribute("displayAccountList",displayAccountList);
		model.addAttribute("Name", user.getFirstName()+" "+user.getLastName());
		model.addAttribute("Department", user.getType());
		
		return "instituteSection";
	}
	
	// done
	@GetMapping("/{accountNo}/request/new")
	public String showRequestForm(@CookieValue(value="sessionId", defaultValue="NoSession") String sessionId, @PathVariable String accountNo, @RequestParam(value="message", defaultValue="") String message, Model model) {
		
		// Dealing with cookie
		if(sessionId.equals("NoSession") ||  facultyServices.checkSessionStatus(sessionId, "faculty").equals("INACTIVE")) {
			//redirect to login page 
			return "redirect:/institute/redirectToLogin";
		}
		
		User user = facultyServices.getUser(sessionId);
		
		String backUrl = "<a href=\"/institute/\">Back To Accounts</a>";
		
		// create request object
		NewResourceRequestFormData newResourceRequestFormData = new NewResourceRequestFormData(accountNo);
		
		model.addAttribute("newResourceRequestFormData",newResourceRequestFormData);
		model.addAttribute("Name", user.getFirstName()+" "+user.getLastName());
		model.addAttribute("Department", user.getType());
		model.addAttribute("message", message);
		model.addAttribute("backUrl", backUrl);
		model.addAttribute("activeInstitute", "id=\"active\"");
		
		return "requestForm";
	}
	
	
	@PostMapping("/{accountNo}/request/new/add")
	public String addRequest(@CookieValue(value="sessionId", defaultValue="NoSession") String sessionId, Model model, @ModelAttribute NewResourceRequestFormData newRequestFormData, @PathVariable String accountNo) {
		
		// Dealing with cookie
		if(sessionId.equals("NoSession") || facultyServices.checkSessionStatus(sessionId, "faculty").equals("INACTIVE")) {
			//redirect to login page 
			return "redirect:/institute/redirectToLogin";
		}
		
		String message = facultyServices.addInstituteRequest(newRequestFormData);
		
		model.addAttribute("message", message);
		
		return "redirect:/institute/"+accountNo+"/request/new";
	}
	
	
	@GetMapping("/{accountNo}/request/list")
	public String showRequestList(@CookieValue(value="sessionId", defaultValue="NoSession") String sessionId, @PathVariable String accountNo, Model model) {
		
		// Dealing with cookie
		if(sessionId.equals("NoSession") || facultyServices.checkSessionStatus(sessionId, "faculty").equals("INACTIVE")) {
			//redirect to login page 
			return "redirect:/institute/redirectToLogin";
		}
	
		User user = facultyServices.getUser(sessionId);
		
		String backUrl = "<a href=\"/institute/\">Back To Accounts</a>";
		
		// get the List of Request, New first.
		List<DisplayRequest> displayRequestList = facultyServices.getRequestList(accountNo);
		
		// bind the data to the model
		model.addAttribute("displayRequestList", displayRequestList);
		model.addAttribute("Name", user.getFirstName()+" "+user.getLastName());
		model.addAttribute("Department", user.getType());
		model.addAttribute("accountNo", accountNo);
		model.addAttribute("backUrl", backUrl);
		model.addAttribute("activeInstitute", "id=\"active\"");
		
		return "requestList";
	}
	
	
	
	
	@GetMapping("/{accountNo}/request/{requestId}/view")
	public String showRequestProfile(@CookieValue(value="sessionId", defaultValue="NoSession") String sessionId, @PathVariable String accountNo, @PathVariable String requestId, Model model, @RequestParam(defaultValue="") String message) {
		
		// Dealing with cookie
		if(sessionId.equals("NoSession") || facultyServices.checkSessionStatus(sessionId, "faculty").equals("INACTIVE")) {
			//redirect to login page 
			return "redirect:/institute/redirectToLogin";
		}	
		
		User user = facultyServices.getUser(sessionId);
		
		String issueUrl = null;
		
		String stageId = facultyServices.getRequestStageId(requestId);
		
		if(stageId != null && stageId.equals("RP")) {
			issueUrl = "<a href=\"issue\" style=\"display: inline-block; background: #536DFE; color: white; padding: 5px 15px 5px 15px; text-decoration: none;\">Submit Issue</a>";
		}
		else {
			issueUrl = "No option available!";
		}
		
		String backUrl = "<a href=\"/institute/" + accountNo + "/request/list\">Back To List</a>";
		
		DisplayInstituteRequestProfile displayInstituteRequestProfile = facultyServices.getRequestProfile(requestId);
		
		model.addAttribute("resource", displayInstituteRequestProfile.getInstituteResource());
		model.addAttribute("request", displayInstituteRequestProfile.getInstituteRequest());
		model.addAttribute("instituteTransaction", displayInstituteRequestProfile.getInstituteTransaction());
		model.addAttribute("requestStageList", displayInstituteRequestProfile.getInstituteRequestStageList());
		model.addAttribute("quotationList", displayInstituteRequestProfile.getInstituteQuotationList());
		model.addAttribute("resourceDocumentList", displayInstituteRequestProfile.getInstituteResourceDocumentList());
		
		model.addAttribute("issueUrl", issueUrl);
		model.addAttribute("backUrl", backUrl);
		model.addAttribute("Name", user.getFirstName()+" "+user.getLastName());
		model.addAttribute("Department", user.getType());
		model.addAttribute("message", message);
		model.addAttribute("sessionId", sessionId);
		model.addAttribute("source", "institute");
		model.addAttribute("activeInstitute", "id=\"active\"");
		
		return "requestProfile";
	}
	
	
	@GetMapping("/{accountNo}/request/{requestId}/issue")
	public String submitIssueRequest(@CookieValue(value="sessionId", defaultValue="NoSession") String sessionId, @PathVariable String accountNo,  @PathVariable String requestId, Model model) {

		// Dealing with cookie
		if(sessionId.equals("NoSession") || facultyServices.checkSessionStatus(sessionId, "faculty").equals("INACTIVE")) {
			//redirect to login page 
			return "redirect:/institute/redirectToLogin";
		}
		
		User user = facultyServices.getUser(sessionId);
		
		String message = facultyServices.createInstituteResourceIssueRequest(requestId);
		
		model.addAttribute("message", message);
		model.addAttribute("Name", user.getFirstName()+" "+user.getLastName());
		model.addAttribute("Department", user.getType());		
		
		return "redirect:/institute/"+accountNo+"/request/"+requestId+"/view";
	}
	
	
	@GetMapping("/{accountNo}/transaction/list")
	public String showTransactionList(@CookieValue(value="sessionId", defaultValue="NoSession") String sessionId, @PathVariable String accountNo, Model model) {
		
		// Dealing with cookie
		if(sessionId.equals("NoSession") || facultyServices.checkSessionStatus(sessionId, "faculty").equals("INACTIVE")) {
			//redirect to login page 
			return "redirect:/institute/redirectToLogin";
		}
	
		User user = facultyServices.getUser(sessionId);
		
		String backUrl = "<a href=\"/institute/\">Back To Accounts</a>";
		
		// get the List of Transactions New first 
		List<DisplayTransaction> displayTransactionList = facultyServices.getInstituteTransactionList(accountNo);
		
		// bind the data to the model
		model.addAttribute("displayTransactionList", displayTransactionList);
		model.addAttribute("accountNo", accountNo);
		model.addAttribute("backUrl", backUrl);
		model.addAttribute("Name", user.getFirstName()+" "+user.getLastName());
		model.addAttribute("Department", user.getType());
		model.addAttribute("activeInstitute", "id=\"active\"");
		
		return "transactionList";
	}

	
	@GetMapping("/redirectToLogin")
	public ModelAndView redirectToLoginPage(){
		
		String LoginPageUrl = "http://accounts.fundally.iiits.ac.in/auth/faculty";
		return new ModelAndView("redirect:" + LoginPageUrl);
	}
}
