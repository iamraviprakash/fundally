package in.iiits.fundally.admin.controllers;


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

import in.iiits.fundally.admin.classes.DisplayAccount;
import in.iiits.fundally.admin.classes.DisplayInstituteRequestProfile;
import in.iiits.fundally.admin.classes.DisplayProject;
import in.iiits.fundally.admin.classes.DisplayProjectRequestProfile;
import in.iiits.fundally.admin.classes.DisplayRequest;
import in.iiits.fundally.admin.classes.DisplayTransaction;
import in.iiits.fundally.admin.classes.NewUserFormData;
import in.iiits.fundally.admin.entity.User;
import in.iiits.fundally.admin.services.AdminServices;

@Controller
@RequestMapping("/faculty")
public class Faculty {

	// need to inject User service
	@Autowired
	private AdminServices adminServices;
	
	// common
	
	@GetMapping("/")
	public String showFacultySection(@CookieValue(value="sessionId", defaultValue="NoSession") String sessionId, Model model, @RequestParam(value="message", defaultValue="") String message) {
		
		// Dealing with cookie
		if(sessionId.equals("NoSession") || adminServices.checkSessionStatus(sessionId, "admin").equals("INACTIVE")) {
			//redirect to login page 
			return "redirect:/faculty/redirectToLogin";
		}

		User user = adminServices.getUser(sessionId);
		
		// Create a model attribute to bind the user form data
		NewUserFormData newUserFormData = new NewUserFormData();
		
		// List all cse faculties	
		List<User> cseList = adminServices.getFacultyListByDepartment("CSE");
		// List all cse faculties	
		List<User> eceList = adminServices.getFacultyListByDepartment("ECE");
	
		// Bind the list
		model.addAttribute("eceFacultyList", eceList);
		model.addAttribute("cseFacultyList", cseList);
		model.addAttribute("Name", user.getFirstName()+" "+user.getLastName());
		model.addAttribute("Department", user.getType());
		model.addAttribute("message", message);
		model.addAttribute("newUserFormData", newUserFormData);
		
		return "facultySection";
	}
	
	@PostMapping("/add")
	public String addFaculty(@CookieValue(value="sessionId", defaultValue="NoSession") String sessionId, Model model, @ModelAttribute NewUserFormData newUserFormData) {
		// Dealing with cookie
		if(sessionId.equals("NoSession") || adminServices.checkSessionStatus(sessionId, "admin").equals("INACTIVE")) {
			//redirect to login page 
			return "redirect:/faculty/redirectToLogin";
		}

		// Add user and get user id
		String message = adminServices.addUserAndAccount(newUserFormData);
		
		model.addAttribute("message", message);
		
		return "redirect:/faculty/";
	}

	@PostMapping("/refillAccount")
	public String refillAccount(@CookieValue(value="sessionId", defaultValue="NoSession") String sessionId, Model model, @RequestParam String accountNo, @RequestParam String amount) {
		// Dealing with cookie
		if(sessionId.equals("NoSession") || adminServices.checkSessionStatus(sessionId, "admin").equals("INACTIVE")) {
			//redirect to login page 
			return "redirect:/faculty/redirectToLogin";
		}
		
		String message = adminServices.refillAccount(accountNo, amount);
		
		model.addAttribute("message", message);
		
		return "redirect:/faculty/";
	}
	
	@GetMapping("/{userId}/view")
	public String showFacultyProfile(@CookieValue(value="sessionId", defaultValue="NoSession") String sessionId,  Model model, @PathVariable String userId) {
		// Dealing with cookie
		if(sessionId.equals("NoSession") || adminServices.checkSessionStatus(sessionId, "admin").equals("INACTIVE")) {
			//redirect to login page 
			return "redirect:/faculty/redirectToLogin";
		}
		
		// Institute account details
		// transaction list with account no
		// Requests List which includes details of resource also
		// get Institute Account List

		User user = adminServices.getUser(sessionId);
		User faculty = adminServices.getFaculty(userId);
		
		List<DisplayAccount> displayAccountList = adminServices.getInstituteDisplayAccountListByUser(userId);
		// Project List and link to project profile
		List<DisplayProject> displayProjectList = adminServices.getDisplayProjectListByUser(faculty.getId());
		
		// bind the data to model
		model.addAttribute("displayAccountList", displayAccountList);
		model.addAttribute("displayProjectList", displayProjectList);
		model.addAttribute("faculty", faculty);
		model.addAttribute("Name", user.getFirstName()+" "+user.getLastName());
		model.addAttribute("Department", user.getType());
		
		return "facultyProfile";
	}
	
	
	// institute
	
	@GetMapping("/{userId}/account/{accountNo}/request/list")
	public String showInstituteRequestList(@CookieValue(value="sessionId", defaultValue="NoSession") String sessionId, @PathVariable String accountNo, @PathVariable String userId, Model model) {
		
		// Dealing with cookie
		if(sessionId.equals("NoSession") || adminServices.checkSessionStatus(sessionId, "admin").equals("INACTIVE")) {
			//redirect to login page 
			return "redirect:/faculty/redirectToLogin";
		}
	
		User user = adminServices.getUser(sessionId);
		
		String backUrl = "<a href=\"/faculty/"+userId+"/view\">Back To Accounts</a>";
		
		// get the List of Request, New first.
		List<DisplayRequest> displayRequestList = adminServices.getInstituteDisplayRequestListByAccount(accountNo);
		
		// bind the data to the model
		model.addAttribute("displayRequestList", displayRequestList);
		model.addAttribute("Name", user.getFirstName()+" "+user.getLastName());
		model.addAttribute("Department", user.getType());
		model.addAttribute("accountNo", accountNo);
		model.addAttribute("backUrl", backUrl);
		model.addAttribute("activeFaculty", "id=\"active\"");		
		
		return "requestList";
	}
	
	@GetMapping("/{userId}/account/{accountNo}/transaction/list")
	public String showInstituteTransactionList(@CookieValue(value="sessionId", defaultValue="NoSession") String sessionId, @PathVariable String accountNo, @PathVariable String userId, Model model) {
		
		// Dealing with cookie
		if(sessionId.equals("NoSession") || adminServices.checkSessionStatus(sessionId, "admin").equals("INACTIVE")) {
			//redirect to login page 
			return "redirect:/faculty/redirectToLogin";
		}
	
		User user = adminServices.getUser(sessionId);
		
		String backUrl = "<a href=\"/faculty/"+userId+"/view\">Back To List</a>";
		
		// get the List of Transactions New first 
		List<DisplayTransaction> displayTransactionList = adminServices.getInstituteDisplayTransactionList(accountNo);
		
		// bind the data to the model
		model.addAttribute("displayTransactionList", displayTransactionList);
		model.addAttribute("accountNo", accountNo);
		model.addAttribute("Name", user.getFirstName()+" "+user.getLastName());
		model.addAttribute("Department", user.getType());
		model.addAttribute("backUrl", backUrl);
		model.addAttribute("activeFaculty", "id=\"active\"");		
		
		return "transactionList";
	}
	
	@GetMapping("/{userId}/account/{accountNo}/request/{requestId}/view")	
	public String showInstituteRequestProfile(@CookieValue(value="sessionId", defaultValue="NoSession") String sessionId, @PathVariable String accountNo,  @PathVariable String userId, @PathVariable String requestId, Model model, @RequestParam(defaultValue="") String message) {
		
		// Dealing with cookie
		if(sessionId.equals("NoSession") || adminServices.checkSessionStatus(sessionId, "admin").equals("INACTIVE")) {
			//redirect to login page 
			return "redirect:/faculty/redirectToLogin";
		}	
		
		User user = adminServices.getUser(sessionId);
		
		String approveUrl = null;
		String cancelUrl = null;
		
		String backUrl = "<a href=\"/faculty/"+userId+"/account/"+accountNo+"/request/list\">Back To List</a>";
		
		String stageId = adminServices.getRequestStageId(requestId);
		
		if(stageId != null && stageId.equals("NRQ")) {
			approveUrl = "<a href=\"approve\" style=\"display: inline-block; margin:10px; background: #536DFE; color: white; padding: 5px 15px 5px 15px; text-decoration: none;\">Approve</a>";
			cancelUrl = "<a href=\"cancel\" style=\"display: inline-block; margin:10px; background: #536DFE; color: white; padding: 5px 15px 5px 15px; text-decoration: none;\">Cancel</a>";
		}
		else {
			approveUrl = "No option available!";
		}
		
		DisplayInstituteRequestProfile displayInstituteRequestProfile = adminServices.getDisplayInstituteRequestProfile(requestId);
		
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
	
	@GetMapping("/{userId}/account/{accountNo}/request/{requestId}/approve")
	public String approveInstituteRequest(@CookieValue(value="sessionId", defaultValue="NoSession") String sessionId, Model model, @PathVariable String requestId, @PathVariable String userId, @PathVariable String accountNo) {
		
		// Dealing with cookie
		if(sessionId.equals("NoSession") || adminServices.checkSessionStatus(sessionId, "admin").equals("INACTIVE")) {
			//redirect to login page 
			return "redirect:/faculty/redirectToLogin";
		}	
		
		String message = adminServices.approveRequest("institute", requestId);
		
		model.addAttribute("message", message);
		
		return "redirect:/faculty/"+userId+"/account/"+accountNo+"/request/"+ requestId+"/view";
	}
	
	@GetMapping("/{userId}/account/{accountNo}/request/{requestId}/cancel")
	public String cancelInstituteRequest(@CookieValue(value="sessionId", defaultValue="NoSession") String sessionId, Model model, @PathVariable String requestId, @PathVariable String userId, @PathVariable String accountNo) {
		
		// Dealing with cookie
		if(sessionId.equals("NoSession") || adminServices.checkSessionStatus(sessionId, "admin").equals("INACTIVE")) {
			//redirect to login page 
			return "redirect:/faculty/redirectToLogin";
		}	
		
		String message = adminServices.cancelRequest("institute", requestId);
		
		model.addAttribute("message", message);
		
		return "redirect:/faculty/"+userId+"/account/"+accountNo+"/request/"+ requestId+"/view";
	}
	
	// project
	
	@GetMapping("/{userId}/project/{projectId}/view")
	public String showProjectProfile(@CookieValue(value="sessionId", defaultValue="NoSession") String sessionId, Model model, @PathVariable String userId, @RequestParam(value="message", defaultValue="") String message, @PathVariable String projectId) {
		
		// Dealing with cookie
		if(sessionId.equals("NoSession") ||  adminServices.checkSessionStatus(sessionId, "admin").equals("INACTIVE")) {
			//redirect to login page 
			return "redirect:/faculty/redirectToLogin";
		}
		
		User user = adminServices.getUser(sessionId);

		String backUrl = "<a href=\"/faculty/"+ userId + "/view\">Back To Projects</a>";
		
		//project details
		DisplayProject displayProject = adminServices.getDisplayProject(projectId);
		
		//account list
		List<DisplayAccount> displayAccountList = adminServices.getProjectDisplayAccountList(projectId);
		
		//faculty List
		List<User> facultyList = adminServices.getProjectFacultyList(projectId);
			
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
		if(sessionId.equals("NoSession") || adminServices.checkSessionStatus(sessionId, "admin").equals("INACTIVE")) {
			//redirect to login page 
			return "redirect:/faculty/redirectToLogin";
		}
	
		User user = adminServices.getUser(sessionId);
		
		String backUrl = "<a href=\"/faculty/"+userId+"/project/"+projectId+"/view\">Back To Accounts</a>";
		
		// get the List of Transactions New first 
		List<DisplayTransaction> displayTransactionList = adminServices.getProjectDisplayTransactionList(accountNo);
		
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
		if(sessionId.equals("NoSession") || adminServices.checkSessionStatus(sessionId, "admin").equals("INACTIVE")) {
			//redirect to login page 
			return "redirect:/faculty/redirectToLogin";
		}
	
		User user = adminServices.getUser(sessionId);
		
		String backUrl = "<a href=\"/faculty/"+ userId +"/project/"+projectId+"/view\">Back To Accounts</a>";
		
		// get the List of Request, New first.
		List<DisplayRequest> displayRequestList = adminServices.getProjectDisplayRequestListByAccount(accountNo);
		
		// bind the data to the model
		model.addAttribute("displayRequestList", displayRequestList);
		model.addAttribute("backUrl", backUrl);
		model.addAttribute("Name", user.getFirstName()+" "+user.getLastName());
		model.addAttribute("Department", user.getType());
		model.addAttribute("accountNo", accountNo);
		model.addAttribute("activeFaculty", "id=\"active\"");		
		
		return "requestList";
	}
	
	@GetMapping("/{userId}/project/{projectId}/account/{accountNo}/request/{requestId}/view")
	public String showProjectRequestProfile(@CookieValue(value="sessionId", defaultValue="NoSession") String sessionId, Model model, @PathVariable String accountNo, @PathVariable String userId, @PathVariable String requestId, @PathVariable String projectId, @RequestParam(defaultValue="") String message) {
		
		// Dealing with cookie
		if(sessionId.equals("NoSession") || adminServices.checkSessionStatus(sessionId, "admin").equals("INACTIVE")) {
			//redirect to login page 
			return "redirect:/faculty/redirectToLogin";
		}	
		
		User user = adminServices.getUser(sessionId);
		
		String approveUrl = null;
		String cancelUrl = null;
		
		String stageId = adminServices.getRequestStageId(requestId);
		
		String backUrl = "<a href=\"/faculty/"+userId+"/project/" + projectId + "/account/" + accountNo +"/request/list\">Back To List</a>";
		
		if(stageId != null && stageId.equals("NRQ")) {
			approveUrl = "<a href=\"approve\" style=\"display: inline-block; margin:10px; background: #536DFE; color: white; padding: 5px 15px 5px 15px; text-decoration: none;\">Approve</a>";
			cancelUrl = "<a href=\"cancel\" style=\"display: inline-block; margin:10px; background: #536DFE; color: white; padding: 5px 15px 5px 15px; text-decoration: none;\">Cancel</a>";
		}
		else {
			approveUrl = "No option available!";
		}

		DisplayProjectRequestProfile displayProjectRequestProfile = adminServices.getDisplayProjectRequestProfile(requestId);
		
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
		if(sessionId.equals("NoSession") || adminServices.checkSessionStatus(sessionId, "admin").equals("INACTIVE")) {
			//redirect to login page 
			return "redirect:/faculty/redirectToLogin";
		}	
		
		String message = adminServices.approveRequest("project", requestId);
		
		model.addAttribute("message", message);
		
		return "redirect:/faculty/"+userId+"/project/"+projectId+"/account/"+accountNo+"/request/"+requestId+"/view";
	}
	
	@GetMapping("/{userId}/project/{projectId}/account/{accountNo}/request/{requestId}/cancel")
	public String cancelProjectRequest(@CookieValue(value="sessionId", defaultValue="NoSession") String sessionId, Model model, @PathVariable String projectId, @PathVariable String userId, @PathVariable String accountNo, @PathVariable String requestId) {
		
		// Dealing with cookie
		if(sessionId.equals("NoSession") || adminServices.checkSessionStatus(sessionId, "admin").equals("INACTIVE")) {
			//redirect to login page 
			return "redirect:/faculty/redirectToLogin";
		}	
		
		String message = adminServices.cancelRequest("project", requestId);
		
		model.addAttribute("message", message);
		
		return "redirect:/faculty/"+userId+"/project/"+projectId+"/account/"+accountNo+"/request/"+requestId+"/view";
	}
	
	
	// common
	
	@GetMapping("/redirectToLogin")
	public ModelAndView redirectToLoginPage(){
		
		String LoginPageUrl = "http://accounts.fundally.iiits.ac.in/auth/admin";
		return new ModelAndView("redirect:" + LoginPageUrl);
	}

}

