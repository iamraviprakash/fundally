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

import in.iiits.fundally.chiefadmin.classes.DisplayAccount;
import in.iiits.fundally.chiefadmin.classes.DisplayInstituteRequestProfile;
import in.iiits.fundally.chiefadmin.classes.DisplayProject;
import in.iiits.fundally.chiefadmin.classes.DisplayProjectRequestProfile;
import in.iiits.fundally.chiefadmin.classes.DisplayRequest;
import in.iiits.fundally.chiefadmin.classes.DisplayTransaction;
import in.iiits.fundally.chiefadmin.entity.User;
import in.iiits.fundally.chiefadmin.services.ChiefAdminServices;

@Controller
@RequestMapping("/faculty")
public class Faculty {

	// need to inject User service
	@Autowired
	private ChiefAdminServices chiefAdminServices;
	
	// common
	
	@GetMapping("/")
	public String showFacultySection(@CookieValue(value="sessionId", defaultValue="NoSession") String sessionId, Model model, @RequestParam(value="message", defaultValue="") String message) {
		
		// Dealing with cookie
		if(sessionId.equals("NoSession") || chiefAdminServices.checkSessionStatus(sessionId, "chiefadmin").equals("INACTIVE")) {
			//redirect to login page 
			return "redirect:/faculty/redirectToLogin";
		}

		User user = chiefAdminServices.getUser(sessionId);
		
		// List all cse faculties	
		List<User> cseList = chiefAdminServices.getFacultyListByDepartment("CSE");
		// List all cse faculties	
		List<User> eceList = chiefAdminServices.getFacultyListByDepartment("ECE");
	
		model.addAttribute("cseFacultyList", cseList);
		model.addAttribute("eceFacultyList", eceList);
		model.addAttribute("Name", user.getFirstName()+" "+user.getLastName());
		model.addAttribute("Department", user.getType());
		model.addAttribute("message", message);
		
		return "facultySection";
	}
	
	@GetMapping("/{userId}/view")
	public String showFacultyProfile(@CookieValue(value="sessionId", defaultValue="NoSession") String sessionId, @PathVariable String userId, Model model) {
		// Dealing with cookie
		if(sessionId.equals("NoSession") || chiefAdminServices.checkSessionStatus(sessionId, "chiefadmin").equals("INACTIVE")) {
			//redirect to login page 
			return "redirect:/faculty/redirectToLogin";
		}
		
		// Institute account details
		// transaction list with account no
		// Requests List which includes details of resource also
		// get Institute Account List

		User faculty = chiefAdminServices.getFaculty(userId);
		User user = chiefAdminServices.getUser(sessionId);

		List<DisplayAccount> displayAccountList = chiefAdminServices.getInstituteDisplayAccountListByUser(userId);
		List<DisplayProject> displayProjectList = chiefAdminServices.getDisplayProjectListByUser(faculty.getId());
		
		// bind the data to model
		model.addAttribute("displayAccountList", displayAccountList);
		model.addAttribute("displayProjectList",displayProjectList);
		model.addAttribute("Name", user.getFirstName()+" "+user.getLastName());
		model.addAttribute("Department", user.getType());
		model.addAttribute("faculty", faculty);
		
		return "facultyProfile";
	}
	
	
	// institute
	
	
	@GetMapping("/{userId}/account/{accountNo}/request/list")
	public String showInstituteRequestList(@CookieValue(value="sessionId", defaultValue="NoSession") String sessionId, @PathVariable String accountNo, @PathVariable String userId, Model model) {
		
		// Dealing with cookie
		if(sessionId.equals("NoSession") || chiefAdminServices.checkSessionStatus(sessionId, "chiefadmin").equals("INACTIVE")) {
			//redirect to login page 
			return "redirect:/faculty/redirectToLogin";
		}
	
		User user = chiefAdminServices.getUser(sessionId);
		
		String backUrl = "<a href=\"/faculty/"+userId+"/view\">Back To Accounts</a>";
		
		// get the List of Request, New first.
		List<DisplayRequest> displayRequestList = chiefAdminServices.getInstituteDisplayRequestListByAccount(accountNo);
		
		// bind the data to the model
		model.addAttribute("displayRequestList", displayRequestList);
		model.addAttribute("Name", user.getFirstName()+" "+user.getLastName());
		model.addAttribute("Department", user.getType());
		model.addAttribute("accountNo", accountNo);
		model.addAttribute("backUrl", backUrl);
		model.addAttribute("activeFaculty", "id=\"active\"");
		
		return "requestList";
	}
	
	@GetMapping("/{userId}/account/{accountNo}/request/{requestId}/view")	// removed resouce id 
	public String showInstituteRequestProfile(@CookieValue(value="sessionId", defaultValue="NoSession") String sessionId, @PathVariable String accountNo,  @PathVariable String userId, @PathVariable String requestId, Model model, @RequestParam(defaultValue="") String message) {
		
		// Dealing with cookie
		if(sessionId.equals("NoSession") || chiefAdminServices.checkSessionStatus(sessionId, "chiefadmin").equals("INACTIVE")) {
			//redirect to login page 
			return "redirect:/faculty/redirectToLogin";
		}	
		
		User user = chiefAdminServices.getUser(sessionId);
		
		String backUrl = "<a href=\"/faculty/"+userId+"/account/"+accountNo+"/request/list\">Back To List</a>";
		
		String approveUrl = null;
		
		String stageId = chiefAdminServices.getRequestStageId(requestId);
		
		if(stageId != null && (stageId.equals("RV") || stageId.equals("QP"))) {
			approveUrl = "<a href=\"approve\" style=\"display: inline-block; margin:10px; background: #536DFE; color: white; padding: 5px 15px 5px 15px; text-decoration: none;\">Approve</a>";
		}
		else {
			approveUrl = "No option available!";
		}
		
		DisplayInstituteRequestProfile displayInstituteRequestProfile = chiefAdminServices.getDisplayInstituteRequestProfile(requestId);
		
		model.addAttribute("account", displayInstituteRequestProfile.getInstituteAccount());
		model.addAttribute("user", displayInstituteRequestProfile.getUser());
		model.addAttribute("resource", displayInstituteRequestProfile.getInstituteResource());
		model.addAttribute("request", displayInstituteRequestProfile.getInstituteRequest());
		model.addAttribute("transaction", displayInstituteRequestProfile.getInstituteTransaction());
		model.addAttribute("requestStageList", displayInstituteRequestProfile.getInstituteRequestStageList());
		model.addAttribute("quotationList", displayInstituteRequestProfile.getInstituteQuotationList());
		model.addAttribute("resourceDocumentList", displayInstituteRequestProfile.getInstituteResourceDocumentList());
		model.addAttribute("source", "institute");
		
		model.addAttribute("approveUrl", approveUrl);
		model.addAttribute("backUrl", backUrl);
		model.addAttribute("Name", user.getFirstName()+" "+user.getLastName());
		model.addAttribute("Department", user.getType());
		model.addAttribute("message", message);
		model.addAttribute("accountNo", accountNo);
		model.addAttribute("sessionId", sessionId);
		model.addAttribute("activeFaculty", "id=\"active\"");
		
		return "requestProfile";
	}
	
	@GetMapping("/{userId}/account/{accountNo}/transaction/list")
	public String showInstituteTransactionList(@CookieValue(value="sessionId", defaultValue="NoSession") String sessionId, @PathVariable String accountNo, @PathVariable String userId, Model model) {
		
		// Dealing with cookie
		if(sessionId.equals("NoSession") || chiefAdminServices.checkSessionStatus(sessionId, "chiefadmin").equals("INACTIVE")) {
			//redirect to login page 
			return "redirect:/faculty/redirectToLogin";
		}
	
		User user = chiefAdminServices.getUser(sessionId);
		
		String backUrl = "<a href=\"/faculty/"+userId+"/view\">Back To List</a>";
		
		// get the List of Transactions New first 
		List<DisplayTransaction> displayTransactionList = chiefAdminServices.getInstituteDisplayTransactionList(accountNo);
		
		// bind the data to the model
		model.addAttribute("displayTransactionList", displayTransactionList);
		model.addAttribute("accountNo", accountNo);
		model.addAttribute("Name", user.getFirstName()+" "+user.getLastName());
		model.addAttribute("Department", user.getType());
		model.addAttribute("backUrl", backUrl);
		model.addAttribute("activeFaculty", "id=\"active\"");
		
		return "transactionList";
	}
	
	@GetMapping("/{userId}/account/{accountNo}/request/{requestId}/approve")
	public String approveInstituteRequest(@CookieValue(value="sessionId", defaultValue="NoSession") String sessionId, Model model, @PathVariable String requestId, @PathVariable String userId, @PathVariable String accountNo) {
		
		// Dealing with cookie
		if(sessionId.equals("NoSession") || chiefAdminServices.checkSessionStatus(sessionId, "chiefadmin").equals("INACTIVE")) {
			//redirect to login page 
			return "redirect:/faculty/redirectToLogin";
		}	
		
		String message = chiefAdminServices.approveRequest("institute", requestId);
		
		model.addAttribute("message", message);
		
		return "redirect:/faculty/"+userId+"/account/"+accountNo+"/request/"+ requestId+"/view";
	}
	
	@GetMapping("/{userId}/account/{accountNo}/request/{requestId}/quotation/{quotationId}/approve")
	public String approveInstituteQuotation(@CookieValue(value="sessionId", defaultValue="NoSession") String sessionId, Model model, @PathVariable String accountNo, @PathVariable String quotationId, @PathVariable String requestId, @PathVariable String userId){
		
		// Dealing with cookie
		if(sessionId.equals("NoSession") || chiefAdminServices.checkSessionStatus(sessionId, "chiefadmin").equals("INACTIVE")) {
			//redirect to login page 
			return "redirect:/request/redirectToLogin";
		}	
		
		String message = chiefAdminServices.approveQuotation("institute", requestId, quotationId);
		
		model.addAttribute("message", message);
		
		return "redirect:/faculty/"+userId+"/account/"+accountNo+"/request/"+ requestId+"/view";
	}   
	
	
	// project
	
	
	@GetMapping("/{userId}/project/{projectId}/view")
	public String showProjectProfile(@CookieValue(value="sessionId", defaultValue="NoSession") String sessionId, Model model, @PathVariable String userId, @RequestParam(value="message", defaultValue="") String message, @PathVariable String projectId) {
		
		// Dealing with cookie
		if(sessionId.equals("NoSession") ||  chiefAdminServices.checkSessionStatus(sessionId, "chiefadmin").equals("INACTIVE")) {
			//redirect to login page 
			return "redirect:/faculty/redirectToLogin";
		}
		
		User user = chiefAdminServices.getUser(sessionId);

		String backUrl = "<a href=\"/faculty/"+ userId + "/view\">Back To Projects</a>";
		
		//project details
		DisplayProject displayProject = chiefAdminServices.getDisplayProject(projectId);
		
		//account list
		List<DisplayAccount> displayAccountList = chiefAdminServices.getProjectDisplayAccountList(projectId);
		
		//faculty List
		List<User> facultyList = chiefAdminServices.getProjectFacultyList(projectId);
			
		model.addAttribute("Name", user.getFirstName()+" "+user.getLastName());
		model.addAttribute("Department", user.getType());
		model.addAttribute("backUrl", backUrl);
		model.addAttribute("displayProject", displayProject);
		model.addAttribute("displayAccountList",displayAccountList);
		model.addAttribute("message", message);
		model.addAttribute("facultyList", facultyList);
		model.addAttribute("userEmailId", user.getEmailId());
		
		return "projectProfile";
	}
	
	@GetMapping("/{userId}/project/{projectId}/account/{accountNo}/transaction/list")
	public String showProjectTransactionList(@CookieValue(value="sessionId", defaultValue="NoSession") String sessionId, Model model, @PathVariable String accountNo, @PathVariable String userId, @PathVariable String projectId) {
		
		// Dealing with cookie
		if(sessionId.equals("NoSession") || chiefAdminServices.checkSessionStatus(sessionId, "chiefadmin").equals("INACTIVE")) {
			//redirect to login page 
			return "redirect:/faculty/redirectToLogin";
		}
	
		User user = chiefAdminServices.getUser(sessionId);
		
		String backUrl = "<a href=\"/faculty/"+userId+"/project/"+projectId+"/view\">Back To Accounts</a>";
		
		// get the List of Transactions New first 
		List<DisplayTransaction> displayTransactionList = chiefAdminServices.getProjectDisplayTransactionList(accountNo);
		
		// bind the data to the model
		model.addAttribute("displayTransactionList", displayTransactionList);
		model.addAttribute("accountNo", accountNo);
		model.addAttribute("backUrl", backUrl);
		model.addAttribute("Name", user.getFirstName()+" "+user.getLastName());
		model.addAttribute("Department", user.getType());
		model.addAttribute("activeFaculty", "id=\"active\"");
		
		return "transactionList";
	}
	
	@GetMapping("/{userId}/project/{projectId}/account/{accountNo}/request/list")
	public String showProjectRequestList(@CookieValue(value="sessionId", defaultValue="NoSession") String sessionId, Model model, @PathVariable String userId, @PathVariable String accountNo, @PathVariable String projectId) {
		
		// Dealing with cookie
		if(sessionId.equals("NoSession") || chiefAdminServices.checkSessionStatus(sessionId, "chiefadmin").equals("INACTIVE")) {
			//redirect to login page 
			return "redirect:/faculty/redirectToLogin";
		}
	
		User user = chiefAdminServices.getUser(sessionId);
		
		String backUrl = "<a href=\"/faculty/"+ userId +"/project/"+projectId+"/view\">Back To Accounts</a>";
		
		// get the List of Request, New first.
		List<DisplayRequest> displayRequestList = chiefAdminServices.getProjectDisplayRequestListByAccount(accountNo);
		
		// bind the data to the model
		model.addAttribute("displayRequestList", displayRequestList);
		model.addAttribute("Name", user.getFirstName()+" "+user.getLastName());
		model.addAttribute("Department", user.getType());
		model.addAttribute("accountNo", accountNo);
		model.addAttribute("backUrl", backUrl);
		model.addAttribute("activeFaculty", "id=\"active\"");
		
		return "requestList";
	}
	
	@GetMapping("/{userId}/project/{projectId}/account/{accountNo}/request/{requestId}/view")
	public String showProjectRequestProfile(@CookieValue(value="sessionId", defaultValue="NoSession") String sessionId, Model model, @PathVariable String accountNo, @PathVariable String userId, @PathVariable String requestId, @PathVariable String projectId, @RequestParam(defaultValue="") String message) {
		
		// Dealing with cookie
		if(sessionId.equals("NoSession") || chiefAdminServices.checkSessionStatus(sessionId, "chiefadmin").equals("INACTIVE")) {
			//redirect to login page 
			return "redirect:/faculty/redirectToLogin";
		}	
		
		User user = chiefAdminServices.getUser(sessionId);
		
		String approveUrl = null;
		String cancelUrl = null;
		
		String stageId = chiefAdminServices.getRequestStageId(requestId);
		
		String backUrl = "<a href=\"/faculty/"+userId+"/project/" + projectId + "/account/" + accountNo +"/request/list\">Back To List</a>";
		
		if(stageId != null && (stageId.equals("RV") || stageId.equals("QP"))) {
			approveUrl = "<a href=\"approve\" style=\"display: inline-block; margin:10px; background: #536DFE; color: white; padding: 5px 15px 5px 15px; text-decoration: none;\">Approve</a>";
			cancelUrl = "<a href=\"cancel\" style=\"display: inline-block; margin:10px; background: #536DFE; color: white; padding: 5px 15px 5px 15px; text-decoration: none;\">Cancel</a>";
		}
		else {
			approveUrl = "No option available!";
		}

		DisplayProjectRequestProfile displayProjectRequestProfile = chiefAdminServices.getDisplayProjectRequestProfile(requestId);
		
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
		
		model.addAttribute("approveUrl", approveUrl);
		model.addAttribute("cancelUrl", cancelUrl);
		model.addAttribute("backUrl", backUrl);
		model.addAttribute("Name", user.getFirstName()+" "+user.getLastName());
		model.addAttribute("Department", user.getType());
		model.addAttribute("message", message);
		model.addAttribute("accountNo", accountNo);
		model.addAttribute("sessionId", sessionId);
		model.addAttribute("activeFaculty", "id=\"active\"");
		
		return "requestProfile";
	}
	
	@GetMapping("/{userId}/project/{projectId}/account/{accountNo}/request/{requestId}/approve")
	public String approveProjectRequest(@CookieValue(value="sessionId", defaultValue="NoSession") String sessionId, Model model, @PathVariable String projectId, @PathVariable String userId, @PathVariable String accountNo, @PathVariable String requestId) {
		
		// Dealing with cookie
		if(sessionId.equals("NoSession") || chiefAdminServices.checkSessionStatus(sessionId, "chiefadmin").equals("INACTIVE")) {
			//redirect to login page 
			return "redirect:/faculty/redirectToLogin";
		}	
		
		String message = chiefAdminServices.approveRequest("project", requestId);
		
		model.addAttribute("message", message);
		
		return "redirect:/faculty/"+userId+"/project/"+projectId+"/account/"+accountNo+"/request/"+requestId+"/view";
	}

	@GetMapping("/{userId}/project/{projectId}/account/{accountNo}/request/{requestId}/quotation/{quotationId}/approve")
	public String approveProjectQuotation(@CookieValue(value="sessionId", defaultValue="NoSession") String sessionId, Model model, @PathVariable String accountNo, @PathVariable String projectId, @PathVariable String quotationId, @PathVariable String requestId, @PathVariable String userId){
		
		// Dealing with cookie
		if(sessionId.equals("NoSession") || chiefAdminServices.checkSessionStatus(sessionId, "chiefadmin").equals("INACTIVE")) {
			//redirect to login page 
			return "redirect:/request/redirectToLogin";
		}	
		
		String message = chiefAdminServices.approveQuotation("project", requestId, quotationId);
		
		model.addAttribute("message", message);
		
		return "redirect:/faculty/"+userId+"/project/"+projectId+"/account/"+accountNo+"/request/"+requestId+"/view";
	} 
	
	// common
	
	@GetMapping("/redirectToLogin")
	public ModelAndView redirectToLoginPage(){
		
		String LoginPageUrl = "http://accounts.fundally.iiits.ac.in/auth/chiefadmin";
		return new ModelAndView("redirect:" + LoginPageUrl);
	}
	
}

