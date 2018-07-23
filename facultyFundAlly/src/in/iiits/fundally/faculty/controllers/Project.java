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

import in.iiits.fundally.faculty.classes.DisplayAccount;
import in.iiits.fundally.faculty.classes.DisplayProject;
import in.iiits.fundally.faculty.classes.DisplayProjectRequestProfile;
import in.iiits.fundally.faculty.classes.DisplayRequest;
import in.iiits.fundally.faculty.classes.DisplayTransaction;
import in.iiits.fundally.faculty.classes.NewProjectAccountForm;
import in.iiits.fundally.faculty.classes.NewProjectFormData;
import in.iiits.fundally.faculty.classes.NewResourceRequestFormData;
import in.iiits.fundally.faculty.entity.ProjectAccountType;
import in.iiits.fundally.faculty.entity.User;
import in.iiits.fundally.faculty.services.FacultyServices;

@Controller
@RequestMapping("/project")
public class Project {

	@Autowired
	private FacultyServices facultyServices;
	
	@GetMapping("/")
	public String showMainPage(@CookieValue(value="sessionId", defaultValue="NoSession") String sessionId, @RequestParam(value="message", defaultValue="") String message, Model model) {
		
		// Dealing with cookie
		if(sessionId.equals("NoSession") || facultyServices.checkSessionStatus(sessionId, "faculty").equals("INACTIVE")) {
			//redirect to login page 
			return "redirect:/institute/redirectToLogin";
		}
		
		//future: get Project status like #of 
		
		User user = facultyServices.getUser(sessionId);
		
		NewProjectFormData newProjectFormData = new NewProjectFormData();
		ProjectAccountType projectAccountType = new ProjectAccountType();
		
		List<DisplayProject> displayProjectList = facultyServices.getProjectList(user.getId());
		
		model.addAttribute("displayProjectList", displayProjectList);
		model.addAttribute("name", user.getFirstName()+" "+user.getLastName());
		model.addAttribute("department", user.getType());
		model.addAttribute("message", message);
		model.addAttribute("newProjectFormData", newProjectFormData);
		model.addAttribute("projectAccountType", projectAccountType);
		
		return "projectSection";
	}
	
	@PostMapping("/add")
	public String addProject(@CookieValue(value="sessionId", defaultValue="NoSession") String sessionId, @ModelAttribute NewProjectFormData newProjectFormData, Model model){
		
		// Dealing with cookie
		if(sessionId.equals("NoSession") || facultyServices.checkSessionStatus(sessionId, "faculty").equals("INACTIVE")) {
			//redirect to login page 
			return "redirect:/institute/redirectToLogin";
		}
		
		User user = facultyServices.getUser(sessionId);
		
		String message = facultyServices.addProject(user.getId(), newProjectFormData);
		
		model.addAttribute("message", message);
		
		return "redirect:/project/";
	}
	
	// add money to account
	@PostMapping("/refillAccount")
	public String refillAccount(@CookieValue(value="sessionId", defaultValue="NoSession") String sessionId, @RequestParam String accountNo, @RequestParam String amount, Model model) {
		// Dealing with cookie
		if(sessionId.equals("NoSession") || facultyServices.checkSessionStatus(sessionId, "faculty").equals("INACTIVE")) {
			//redirect to login page 
			return "redirect:/institute/redirectToLogin";
		}
		
		User user = facultyServices.getUser(sessionId);
		
		String message = facultyServices.refillAccount(user.getEmailId(), accountNo, amount);
		
		model.addAttribute("message", message);
		
		return "redirect:/project/";
	}
	
	@PostMapping("/accountType/add")
	public String addAccountType(@CookieValue(value="sessionId", defaultValue="NoSession") String sessionId, @ModelAttribute ProjectAccountType accountType, Model model){
		
		// Dealing with cookie
		if(sessionId.equals("NoSession") || facultyServices.checkSessionStatus(sessionId, "faculty").equals("INACTIVE")) {
			//redirect to login page 
			return "redirect:/institute/redirectToLogin";
		}
	
		String message = facultyServices.addProjectAccountType(accountType);
		
		model.addAttribute("message", message);
		
		return "redirect:/project/";
	}
	
	@GetMapping("/{projectId}/view")
	public String showProjectProfile(@CookieValue(value="sessionId", defaultValue="NoSession") String sessionId,  @RequestParam(value="message", defaultValue="") String message, @PathVariable String projectId, Model model) {
		
		// Dealing with cookie
		if(sessionId.equals("NoSession") ||  facultyServices.checkSessionStatus(sessionId, "faculty").equals("INACTIVE")) {
			//redirect to login page 
			return "redirect:/institute/redirectToLogin";
		}
		
		User user = facultyServices.getUser(sessionId);

		String backUrl = "<a href=\"/project/\">Back To Projects</a>";
		
		//project details
		DisplayProject displayProject = facultyServices.getDisplayProject(projectId);
		
		//account list
		List<DisplayAccount> displayAccountList = facultyServices.getProjectAccountList(projectId);
		
		//faculty List
		List<User> facultyList = facultyServices.getProjectFacultyList(projectId);
		
		if(displayProject.getProjectIncharge().equals(user.getEmailId())){

			NewProjectAccountForm newProjectAccountForm = new NewProjectAccountForm();
			
			List<ProjectAccountType> projectAccountTypeList = facultyServices.getProjectAccountTypeList();

			model.addAttribute("newProjectAccountForm", newProjectAccountForm);
			model.addAttribute("projectAccountTypeList", projectAccountTypeList);
		}
		
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
	
	@PostMapping("/{projectId}/account/add")
	public String addAccount(@CookieValue(value="sessionId", defaultValue="NoSession") String sessionId, @ModelAttribute NewProjectAccountForm newProjectAccountForm, @PathVariable String projectId, Model model) {
		// Dealing with cookie
		if(sessionId.equals("NoSession") ||  facultyServices.checkSessionStatus(sessionId, "faculty").equals("INACTIVE")) {
			//redirect to login page 
			return "redirect:/institute/redirectToLogin";
		}
					
		String message = facultyServices.addProjectAccount(projectId, newProjectAccountForm);
		
		model.addAttribute("message", message);
				
		return "redirect:/project/" + projectId + "/view";
	}
	
	@PostMapping("/{projectId}/faculty/add")
	public String addFaculty(@CookieValue(value="sessionId", defaultValue="NoSession") String sessionId, @RequestParam String facultyEmailId, @PathVariable String projectId, Model model) {
		// Dealing with cookie
		if(sessionId.equals("NoSession") ||  facultyServices.checkSessionStatus(sessionId, "faculty").equals("INACTIVE")) {
			//redirect to login page 
			return "redirect:/institute/redirectToLogin";
		}
					
		String message = facultyServices.addFacultyToProject(projectId, facultyEmailId);
		
		model.addAttribute("message", message);
				
		return "redirect:/project/" + projectId + "/view";
	}
	
	@GetMapping("/{projectId}/account/{accountNo}/request/new")
	public String showRequestForm(@CookieValue(value="sessionId", defaultValue="NoSession") String sessionId, @PathVariable String accountNo, @PathVariable String projectId, @RequestParam(value="message", defaultValue="") String message, Model model) {
		// Dealing with cookie
		if(sessionId.equals("NoSession") ||  facultyServices.checkSessionStatus(sessionId, "faculty").equals("INACTIVE")) {
			//redirect to login page 
			return "redirect:/institute/redirectToLogin";
		}
		
		User user = facultyServices.getUser(sessionId);

		String backUrl = "<a href=\"/project/" + projectId + "/view\">Back To Accounts</a>";
		// create request object
		NewResourceRequestFormData newResourceRequestFormData = new NewResourceRequestFormData(accountNo);
		
		model.addAttribute("newResourceRequestFormData",newResourceRequestFormData);
		model.addAttribute("Name", user.getFirstName()+" "+user.getLastName());
		model.addAttribute("Department", user.getType());
		model.addAttribute("message", message);
		model.addAttribute("backUrl", backUrl);
		model.addAttribute("activeProject", "id=\"active\"");
		
		return "requestForm";
	}
	
	@PostMapping("/{projectId}/account/{accountNo}/request/new/add")
	public String addRequest(@CookieValue(value="sessionId", defaultValue="NoSession") String sessionId, Model model, @ModelAttribute NewResourceRequestFormData newResourceRequestFormData, @PathVariable String accountNo, @PathVariable String projectId) {
		
		// Dealing with cookie
		if(sessionId.equals("NoSession") || facultyServices.checkSessionStatus(sessionId, "faculty").equals("INACTIVE")) {
			//redirect to login page 
			return "redirect:/institute/redirectToLogin";
		}
		
		User user = facultyServices.getUser(sessionId);
		
		String message = facultyServices.addProjectRequest(user.getId(), newResourceRequestFormData);
		
		model.addAttribute("message", message);
		
		return "redirect:/project/"+projectId+"/account/"+accountNo+"/request/new";
	}
	
	@GetMapping("/{projectId}/account/{accountNo}/request/list")
	public String showRequestList(@CookieValue(value="sessionId", defaultValue="NoSession") String sessionId, @PathVariable String accountNo,  @PathVariable String projectId, Model model) {
		
		// Dealing with cookie
		if(sessionId.equals("NoSession") || facultyServices.checkSessionStatus(sessionId, "faculty").equals("INACTIVE")) {
			//redirect to login page 
			return "redirect:/institute/redirectToLogin";
		}
	
		User user = facultyServices.getUser(sessionId);
		
		model.addAttribute("Name", user.getFirstName()+" "+user.getLastName());
		model.addAttribute("Department", user.getType());
		model.addAttribute("accountNo", accountNo);
		
		String backUrl = "<a href=\"/project/"+projectId+"/view\">Back To Accounts</a>";
		
		model.addAttribute("backUrl", backUrl);
		
		// get the List of Request, New first.
		
		List<DisplayRequest> displayRequestList = facultyServices.getProjectRequestList(accountNo);
		
		// bind the data to the model
		model.addAttribute("displayRequestList", displayRequestList);
		model.addAttribute("activeProject", "id=\"active\"");
		
		return "requestList";
	}
	
	@GetMapping("/{projectId}/account/{accountNo}/request/{requestId}/view")
	public String showRequestProfile(@CookieValue(value="sessionId", defaultValue="NoSession") String sessionId, @PathVariable String accountNo, @PathVariable String requestId, @PathVariable String projectId, Model model, @RequestParam(defaultValue="") String message) {
		
		// Dealing with cookie
		if(sessionId.equals("NoSession") || facultyServices.checkSessionStatus(sessionId, "faculty").equals("INACTIVE")) {
			//redirect to login page 
			return "redirect:/institute/redirectToLogin";
		}	
		
		User user = facultyServices.getUser(sessionId);
		
		String issueUrl = null;

		DisplayProjectRequestProfile displayProjectRequestProfile = facultyServices.getProjectRequestProfile(requestId);
		
		String stageId = facultyServices.getRequestStageId(requestId);
		
		// Only requester can generate issue request
		if(stageId != null && stageId.equals("RP") && displayProjectRequestProfile.getProjectRequest().getUser().getEmailId().equals(user.getEmailId())) {
			issueUrl = "<a href=\"issue\" style=\"display: inline-block; background: #536DFE; color: white; padding: 5px 15px 5px 15px; text-decoration: none;\">Submit Issue</a>";
		}
		else {
			issueUrl = "No option available!";
		}
		
		String backUrl = "<a href=\"/project/" + projectId + "/account/" + accountNo +"/request/list\">Back To List</a>";
		
		
		model.addAttribute("Name", user.getFirstName()+" "+user.getLastName());
		model.addAttribute("Department", user.getType());
		model.addAttribute("message", message);
		model.addAttribute("accountNo", accountNo);
		model.addAttribute("resource", displayProjectRequestProfile.getProjectResource());
		model.addAttribute("request", displayProjectRequestProfile.getProjectRequest());
		model.addAttribute("transaction", displayProjectRequestProfile.getProjectTransaction());
		model.addAttribute("requestStageList", displayProjectRequestProfile.getProjectRequestStageList());
		model.addAttribute("quotationList", displayProjectRequestProfile.getProjectQuotationList());
		model.addAttribute("resourceDocumentList", displayProjectRequestProfile.getProjectResourceDocumentList());
		model.addAttribute("sessionId", sessionId);
		
		model.addAttribute("issueUrl", issueUrl);
		model.addAttribute("backUrl", backUrl);
		model.addAttribute("source", "project");
		model.addAttribute("activeProject", "id=\"active\"");
		
		return "requestProfile";
	}
	
	
	@GetMapping("/{projectId}/account/{accountNo}/request/{requestId}/issue")
	public String submitIssueRequest(@CookieValue(value="sessionId", defaultValue="NoSession") String sessionId, @PathVariable String accountNo, @PathVariable String projectId, @PathVariable String requestId, Model model) {

		// Dealing with cookie
		if(sessionId.equals("NoSession") || facultyServices.checkSessionStatus(sessionId, "faculty").equals("INACTIVE")) {
			//redirect to login page 
			return "redirect:/institute/redirectToLogin";
		}
		
		User user = facultyServices.getUser(sessionId);
		
		String message = facultyServices.createProjectResourceIssueRequest(user.getId(), requestId);
		
		model.addAttribute("message", message);
		model.addAttribute("Name", user.getFirstName()+" "+user.getLastName());
		model.addAttribute("Department", user.getType());	
		
		return "redirect:/project/"+projectId+"/account/"+accountNo+"/request/"+requestId+"/view";
	}
	
	
	@GetMapping("/{projectId}/account/{accountNo}/transaction/list")
	public String showTransactionList(@CookieValue(value="sessionId", defaultValue="NoSession") String sessionId, @PathVariable String accountNo, @PathVariable String projectId, Model model) {
		
		// Dealing with cookie
		if(sessionId.equals("NoSession") || facultyServices.checkSessionStatus(sessionId, "faculty").equals("INACTIVE")) {
			//redirect to login page 
			return "redirect:/institute/redirectToLogin";
		}
	
		User user = facultyServices.getUser(sessionId);
		
		String backUrl = "<a href=\"/project/"+projectId+"/view\">Back To Accounts</a>";
		
		// get the List of Transactions New first 
		List<DisplayTransaction> displayTransactionList = facultyServices.getProjectTransactionList(accountNo);
		
		// bind the data to the model
		model.addAttribute("displayTransactionList", displayTransactionList);
		model.addAttribute("accountNo", accountNo);
		model.addAttribute("backUrl", backUrl);
		model.addAttribute("Name", user.getFirstName()+" "+user.getLastName());
		model.addAttribute("Department", user.getType());
		model.addAttribute("activeProject", "id=\"active\"");
		
		return "transactionList";
	}
}
