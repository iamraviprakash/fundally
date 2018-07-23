package in.iiits.fundally.faculty.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import in.iiits.fundally.faculty.classes.DisplayAccount;
import in.iiits.fundally.faculty.classes.DisplayInstituteRequestProfile;
import in.iiits.fundally.faculty.classes.DisplayProject;
import in.iiits.fundally.faculty.classes.DisplayProjectRequestProfile;
import in.iiits.fundally.faculty.classes.DisplayRequest;
import in.iiits.fundally.faculty.classes.DisplayTransaction;
import in.iiits.fundally.faculty.classes.NewProjectAccountForm;
import in.iiits.fundally.faculty.classes.NewProjectFormData;
import in.iiits.fundally.faculty.classes.NewResourceRequestFormData;
import in.iiits.fundally.faculty.dao.FacultyDAOs;
import in.iiits.fundally.faculty.entity.InstituteAccount;
import in.iiits.fundally.faculty.entity.InstituteRequest;
import in.iiits.fundally.faculty.entity.InstituteRequestStage;
import in.iiits.fundally.faculty.entity.InstituteRequestStageType;
import in.iiits.fundally.faculty.entity.InstituteResource;
import in.iiits.fundally.faculty.entity.InstituteTransaction;
import in.iiits.fundally.faculty.entity.Project;
import in.iiits.fundally.faculty.entity.ProjectAccount;
import in.iiits.fundally.faculty.entity.ProjectAccountType;
import in.iiits.fundally.faculty.entity.ProjectRequest;
import in.iiits.fundally.faculty.entity.ProjectRequestStage;
import in.iiits.fundally.faculty.entity.ProjectResource;
import in.iiits.fundally.faculty.entity.ProjectTransaction;
import in.iiits.fundally.faculty.entity.ResourceType;
import in.iiits.fundally.faculty.entity.Status;
import in.iiits.fundally.faculty.entity.TransactionType;
import in.iiits.fundally.faculty.entity.User;
import in.iiits.fundally.faculty.entity.UserSession;

@Service
public class FacultyServicesImpl implements FacultyServices {

	// need to inject DAOs
	@Autowired
	private FacultyDAOs facultyDAOs; 
	
	
	// Common Services
	
	@Override
	@Transactional
	public User getUser(String sessionId) {
		UserSession session = facultyDAOs.getSession(sessionId);
		return session.getUser();
	}
	
	@Override
	@Transactional
	public String checkSessionStatus(String sessionId, String userType) {
		
		UserSession userSession = facultyDAOs.getSession(sessionId);
		
		if(userSession != null){
			if(userSession.getStatus().equals(Status.ACTIVE) && userSession.getUser().getType().equals(userType)) 
				return "ACTIVE";
			else
				return "INACTIVE";
		}
		else
			return "INACTIVE";
	}
	
	@Override
	@Transactional
	public String getRequestStageId(String requestId) {
		// get Request
		InstituteRequest instituteRequest = facultyDAOs.getRequest(requestId);
		ProjectRequest projectRequest = facultyDAOs.getProjectRequest(requestId);
		
		if(instituteRequest != null)
			// return stage
			return instituteRequest.getStageType().getId();
		else if(projectRequest != null)
			return projectRequest.getStageType().getId();
		else
			return null;
	}
	
	
	// Institute Part
	
	@Override
	@Transactional
	public List<DisplayAccount> getInstituteAccountList(String userId) {
		
		List<InstituteAccount> instituteAccountList = facultyDAOs.getUser(userId).getInstituteAccountList();
		List<DisplayAccount> displayInstituteAccountList = new ArrayList<DisplayAccount>(); 
		
		for(InstituteAccount i: instituteAccountList) {
			DisplayAccount d = new DisplayAccount();
			d.setAccountNo(i.getAccountNo());
			d.setAccountType(i.getAccountType().getName());
			d.setBalance(i.getBalance());
			displayInstituteAccountList.add(d);
		}
		
		return displayInstituteAccountList;
	}
	
	@Override
	@Transactional
	public String addInstituteRequest(NewResourceRequestFormData newResourceRequestFormData) {
		
		String message = null;
		
		// check for sufficient funds
		InstituteAccount account = facultyDAOs.getInstituteAccount(newResourceRequestFormData.getAccountNo());
		
		if(account != null && Double.parseDouble(account.getBalance())  > Double.parseDouble(newResourceRequestFormData.getAmount()) ) {
			
			InstituteResource resource = new InstituteResource();
			
			// set Resource
			resource.setName(newResourceRequestFormData.getName());
			resource.setDescription(newResourceRequestFormData.getDescription());
			if(newResourceRequestFormData.getType().equals("TANGIBLE"))
				resource.setType(ResourceType.TANGIBLE);
			else if(newResourceRequestFormData.getType().equals("INTANGIBLE"))
				resource.setType(ResourceType.INTANGIBLE);
			resource.setDeadlineDate(newResourceRequestFormData.getDeadline());;
			resource.setAmount(newResourceRequestFormData.getAmount());
			
			
			InstituteRequest request = new InstituteRequest();
			
			// set request
			request.setStatus(Status.PENDING);
			request.setDescription(newResourceRequestFormData.getReason());
			request.setResource(resource);
			
			// set stage 
			InstituteRequestStageType stageType = facultyDAOs.getStageType("NRQ");
			request.setStageType(stageType);
			
			// add request to account. As the Account is already in persistence stage.
			//	Any change made it will be automatically synchronized
			// So no need to do save() for request
			// Now after adding request also will be in persistence stage 
			account.add(request);
			
			/// set status
			InstituteRequestStage stage = new InstituteRequestStage();
			stage.setRequest(request);
			stage.setStageType(stageType);
			
			// add stage to Request. 
			// Since request in persistence stage 
			// so need to save stage just add stage to request 
			request.add(stage);
			
			message = "Request Added Successfully! <br>Request Id: " + request.getId() + " <br>Resource Id: " + resource.getId();
		}
		else {
			message = "Insuffient Funds";
		}
		
		return message;
	}
	
	@Override
	@Transactional
	public List<DisplayRequest> getRequestList(String accountNo) {
		
		List<InstituteRequest> requestList = facultyDAOs.getRequestList(accountNo);
		
		List<DisplayRequest> displayResourceRequestList = new ArrayList<DisplayRequest>();
		
		DisplayRequest displayRequest = null;
		
		for(InstituteRequest r: requestList) {
			
			displayRequest = new DisplayRequest();
			
			displayRequest.setAmount(r.getResource().getAmount());
			displayRequest.setRequesterEmailId(r.getAccount().getUser().getEmailId());
			displayRequest.setDeadline(r.getResource().getDeadlineDate());
			displayRequest.setRequestId(r.getId());
			displayRequest.setResourceId(r.getResource().getId());
			displayRequest.setResourceName(r.getResource().getName());
			displayRequest.setStage(r.getStageType().getName()); 
			displayRequest.setStatus(r.getStatus().name());
			displayRequest.setType(r.getResource().getType().name());
			
			displayResourceRequestList.add(displayRequest);
		}
		
		return displayResourceRequestList;
	}
	
	@Override
	@Transactional
	public DisplayInstituteRequestProfile getRequestProfile(String requestId) {
		
		DisplayInstituteRequestProfile profile = new DisplayInstituteRequestProfile();
		
		// get Request
		InstituteRequest request = facultyDAOs.getRequest(requestId);
		
		if(request != null) {
			// set Request Profile
			profile.setInstituteQuotationList(facultyDAOs.getQuotationList(request));
			profile.setInstituteRequest(request);
			profile.setInstituteRequestStageList(facultyDAOs.getRequestStageList(request));
			profile.setInstituteResource(request.getResource());
			profile.setInstituteResourceDocumentList(request.getResource().getResourceDocumentList());		
			profile.setInstituteTransaction(request.getTransaction());
		}
		
		return profile;
	}
	
	@Override
	@Transactional
	public String createInstituteResourceIssueRequest(String requestId) {
		// Issue can be generated only when the stage is AP
		// Or if issue request is not already generated
		
		InstituteRequest request = facultyDAOs.getRequest(requestId);
		InstituteRequestStageType stageType = facultyDAOs.getStageType("IRQ");
		
		if(request != null  && request.getResource().getType().equals(ResourceType.TANGIBLE)) {
			// get request list of resources where stage type is IRQ 
			//if atleast one of them are pending then abort
			List<InstituteRequest> pendingIssueRequestList = facultyDAOs.getPendingInstituteIssueRequestList(request.getResource(), stageType);
			
			if(pendingIssueRequestList.size() == 0) {
				// get account
				InstituteAccount account = request.getAccount();
				
				// get resource
				InstituteRequest issueRequest = new InstituteRequest();
				
				// set request
				issueRequest.setStatus(Status.PENDING);
				issueRequest.setDescription("There is some problem with resource!");
				issueRequest.setResource(request.getResource());
				
				// set stage 
				issueRequest.setStageType(stageType);
				
				// add request to account. As the Account is already in persistence stage.
				//	Any change made it will be automatically synchronized
				// So no need to do save() for request
				// Now after adding request also will be in persistence stage 
				account.add(issueRequest);
				
				/// set status
				InstituteRequestStage stage = new InstituteRequestStage();
				stage.setRequest(issueRequest);
				stage.setStageType(stageType);
				
				// add stage to Request. 
				// Since request in persistence stage 
				// so need to save stage just add stage to request 
				issueRequest.add(stage);

				return "Issue Request Added Successfully! <br>Issue Id:" + request.getId();
			}
			else
				return "Cannot create new issue request. One already in pending!";
		}
		else
			return "Invalid Request!";
		
	}
	
	@Override
	@Transactional
	public List<DisplayTransaction> getInstituteTransactionList(String accountNo) {

		InstituteAccount account = facultyDAOs.getInstituteAccount(accountNo);
		
		if(account != null) {
			
			List<DisplayTransaction> displayTransactionList = new ArrayList<DisplayTransaction>();
			DisplayTransaction displayTransaction = null;
			
			for(InstituteTransaction t: account.getTransactionList()) {
				
				displayTransaction = new DisplayTransaction();
				
				if(t.getRequest() != null) {
					
					displayTransaction.setResourceName(t.getRequest().getResource().getName());
				}
				else if(t.getType().equals(TransactionType.CREDIT)){
					
					displayTransaction.setResourceName("By Admin");
				}
				
				displayTransaction.setClosingBalance(t.getClosingBalance());
				displayTransaction.setDate(t.getDate());
				displayTransaction.setOpeningBalance(t.getOpeningBalance());
				displayTransaction.setType(t.getType().name());
	
				displayTransaction.setTransactionId(t.getId());
				
				displayTransactionList.add(displayTransaction);
			}
			
			return displayTransactionList;
		}
		else
			return null;
	}


	
	
	// Project Part 
	
	@Override
	@Transactional
	public List<DisplayProject> getProjectList(String userId) {
		
		User user = facultyDAOs.getUser(userId);
		
		if(user != null) {
			
			List<DisplayProject> displayProjectList = new ArrayList<DisplayProject>();
			DisplayProject displayProject = null;
			
			for(Project p: user.getProjectList()) {
				
				displayProject = new DisplayProject();
				
				displayProject.setId(p.getId());
				displayProject.setName(p.getName());
				displayProject.setProjectIncharge(p.getProjectIncharge().getEmailId());
				displayProject.setSanctionedAmount(p.getSanctionedAmount());
				
				displayProjectList.add(displayProject);
			}
			
			return displayProjectList;
		}
		else
			return null;
	}

	@Override
	@Transactional
	public String addProject(String userId, NewProjectFormData newProjectFormData) {

		User user = facultyDAOs.getUser(userId);
		
		if(user != null) {
			// create project instance
			Project project = new Project();
			
			project.setClosingDate(newProjectFormData.getClosingDate());
			project.setDescription(newProjectFormData.getDescription());
			project.setName(newProjectFormData.getName());
			project.setProjectIncharge(user);
			project.setSanctionedAmount("0.00");
			project.setStatus(Status.ACTIVE);

			facultyDAOs.saveProject(project);
							
			//project.add(user);
			user.add(project);
			
			return "Project Added Successfully!<br>Project Id: " + project.getId();
		}
		else
			return "Invalid User";
	}

	@Override
	@Transactional
	public String addProjectAccountType(ProjectAccountType accountType) {
		
		return facultyDAOs.saveProjectAccountType(accountType);
	}

	@Override
	@Transactional
	public String refillAccount(String emailId, String accountNo, String amount) {
		ProjectAccount projectAccount = facultyDAOs.getProjectAccount(accountNo);
		
		// Only project in charge can refill the project account
		
		if(projectAccount != null && projectAccount.getProject().getProjectIncharge().getEmailId().equals(emailId)) {
			Double currentBalance = Double.parseDouble(projectAccount.getBalance());
			Double newBalance = currentBalance + Double.parseDouble(amount);
			
			ProjectTransaction projectTransaction = new ProjectTransaction();
			projectTransaction.setClosingBalance(newBalance.toString());
			projectTransaction.setOpeningBalance(currentBalance.toString());
			projectTransaction.setType(TransactionType.CREDIT);
			projectAccount.setBalance(newBalance.toString());
			projectAccount.add(projectTransaction);
			
			// update sanctioned amount
			Project project = projectAccount.getProject();
			
			Double currentSanctionedAmount = Double.parseDouble(project.getSanctionedAmount());
			Double newSanctionedAmount = currentSanctionedAmount + Double.parseDouble(amount);
			
			project.setSanctionedAmount(newSanctionedAmount.toString());
			
			return "Account Refilled Successfully!<br>Transaction Id: " + projectTransaction.getId();
		}
		else
			return "Invalid Request!";
	}

	@Override
	@Transactional
	public String addProjectRequest(String userId, NewResourceRequestFormData newResourceRequestFormData) {
		
		String message = null;
		
		// check for sufficient funds
		ProjectAccount account = facultyDAOs.getProjectAccount(newResourceRequestFormData.getAccountNo());
		
		// get User
		User user = facultyDAOs.getUser(userId);
		
		if(user != null && account != null && Double.parseDouble(account.getBalance())  > Double.parseDouble(newResourceRequestFormData.getAmount()) ) {
			
			ProjectResource resource = new ProjectResource();
			
			// set Resource
			resource.setName(newResourceRequestFormData.getName());
			resource.setDescription(newResourceRequestFormData.getDescription());
			if(newResourceRequestFormData.getType().equals("TANGIBLE"))
				resource.setType(ResourceType.TANGIBLE);
			else if(newResourceRequestFormData.getType().equals("INTANGIBLE"))
				resource.setType(ResourceType.INTANGIBLE);
			resource.setDeadlineDate(newResourceRequestFormData.getDeadline());;
			resource.setAmount(newResourceRequestFormData.getAmount());
			
			
			ProjectRequest request = new ProjectRequest();
			
			// set request
			request.setStatus(Status.PENDING);
			request.setDescription(newResourceRequestFormData.getReason());
			request.setResource(resource);
			request.setUser(user);
			
			// set stage 
			InstituteRequestStageType stageType = facultyDAOs.getStageType("NRQ");
			request.setStageType(stageType);
			
			// add request to account. As the Account is already in persistence stage.
			//	Any change made it will be automatically synchronized
			// So no need to do save() for request
			// Now after adding request also will be in persistence stage 
			account.add(request);
			
			/// set status
			ProjectRequestStage stage = new ProjectRequestStage();
			stage.setRequest(request);
			stage.setStageType(stageType);
			
			// add stage to Request. 
			// Since request in persistence stage 
			// so need to save stage just add stage to request 
			request.add(stage);
			
			message = "Request Added Successfully! <br>Request Id: " + request.getId() + " <br>Resource Id: " + resource.getId();
		}
		else {
			message = "Insuffient Funds";
		}
		
		return message;
	}
	
	@Override
	@Transactional
	public String createProjectResourceIssueRequest(String userId, String requestId) {
		// Issue can be generated only when the stage is AP
		// Or if issue request is not already generated
		User user = facultyDAOs.getUser(userId);
		ProjectRequest request = facultyDAOs.getProjectRequest(requestId);
		InstituteRequestStageType stageType = facultyDAOs.getStageType("IRQ");
		
		if(request != null && request.getUser().getEmailId().equals(user.getEmailId()) && request.getResource().getType().equals(ResourceType.TANGIBLE)) {
			// get request list of resources where stage type is IRQ 
			//if atleast one of them are pending then abort
			List<ProjectRequest> pendingIssueRequestList = facultyDAOs.getPendingProjectIssueRequestList(request.getResource(), stageType);
			
			if(pendingIssueRequestList.size() == 0) {
				// get account
				ProjectAccount account = request.getAccount();
				
				// get resource
				ProjectRequest issueRequest = new ProjectRequest();
				
				// set request
				issueRequest.setUser(request.getUser()); // extra with project
				issueRequest.setStatus(Status.PENDING);
				issueRequest.setDescription("There is some problem with resource!");
				issueRequest.setResource(request.getResource());
				
				// set stage 
				issueRequest.setStageType(stageType);
				
				// add request to account. As the Account is already in persistence stage.
				//	Any change made it will be automatically synchronized
				// So no need to do save() for request
				// Now after adding request also will be in persistence stage 
				account.add(issueRequest);
				
				/// set status
				ProjectRequestStage stage = new ProjectRequestStage();
				stage.setRequest(issueRequest);
				stage.setStageType(stageType);
				
				// add stage to Request. 
				// Since request in persistence stage 
				// so need to save stage just add stage to request 
				issueRequest.add(stage);

				return "Issue Request Added Successfully! <br>Issue Id:" + request.getId();
			}
			else
				return "Cannot create new issue request. One already in pending!";
		}
		else
			return "Invalid Request!";
		
	}

	@Override
	@Transactional
	public List<DisplayRequest> getProjectRequestList(String accountNo) {
		
		List<ProjectRequest> requestList = facultyDAOs.getProjectRequestList(accountNo);
		
		List<DisplayRequest> displayResourceRequestList = new ArrayList<DisplayRequest>();
		
		DisplayRequest displayRequest = null;
		
		for(ProjectRequest r: requestList) {
			
			displayRequest = new DisplayRequest();
			
			displayRequest.setAmount(r.getResource().getAmount());
			displayRequest.setRequesterEmailId(r.getUser().getEmailId());
			displayRequest.setDeadline(r.getResource().getDeadlineDate());
			displayRequest.setRequestId(r.getId());
			displayRequest.setResourceId(r.getResource().getId());
			displayRequest.setResourceName(r.getResource().getName());
			displayRequest.setStage(r.getStageType().getName()); 
			displayRequest.setStatus(r.getStatus().name());
			displayRequest.setType(r.getResource().getType().name());
			
			displayResourceRequestList.add(displayRequest);
		}
		
		return displayResourceRequestList;
	}
	
	@Override
	@Transactional
	public DisplayProjectRequestProfile getProjectRequestProfile(String requestId) {
		
		DisplayProjectRequestProfile profile = new DisplayProjectRequestProfile();
		
		// get Request
		ProjectRequest request = facultyDAOs.getProjectRequest(requestId);
		
		if(request != null) {
			// set Request Profile
			profile.setProjectQuotationList(facultyDAOs.getProjectQuotationList(request));
			profile.setProjectRequest(request);
			profile.setProjectRequestStageList(facultyDAOs.getProjectRequestStageList(request));
			profile.setProjectResource(request.getResource());
			profile.setProjectResourceDocumentList(request.getResource().getResourceDocumentList());		
			profile.setProjectTransaction(request.getTransaction());
		}
		
		return profile;
	}
	
	@Override
	@Transactional
	public List<DisplayTransaction> getProjectTransactionList(String accountNo) {

		ProjectAccount account = facultyDAOs.getProjectAccount(accountNo);
		
		if(account != null) {
			
			List<DisplayTransaction> displayTransactionList = new ArrayList<DisplayTransaction>();
			DisplayTransaction displayTransaction = null;
			
			for(ProjectTransaction t: account.getTransactionList()) {
				
				displayTransaction = new DisplayTransaction();
				
				if(t.getRequest() != null) {
					
					displayTransaction.setResourceName(t.getRequest().getResource().getName());
				}
				else if(t.getType().equals(TransactionType.CREDIT)){
					
					displayTransaction.setResourceName("By Admin");
				}
				
				displayTransaction.setClosingBalance(t.getClosingBalance());
				displayTransaction.setDate(t.getDate());
				displayTransaction.setOpeningBalance(t.getOpeningBalance());
				displayTransaction.setType(t.getType().name());
	
				displayTransaction.setTransactionId(t.getId());
				
				displayTransactionList.add(displayTransaction);
			}
			
			return displayTransactionList;
		}
		else
			return null;
	}
	
	@Override
	@Transactional
	public List<DisplayAccount> getProjectAccountList(String projectId) {
		
		List<ProjectAccount> projectAccountList = facultyDAOs.getProject(projectId).getProjectAccountList();
		List<DisplayAccount> displayProjectAccountList = new ArrayList<DisplayAccount>(); 
		
		if(projectAccountList != null) {
			
			for(ProjectAccount p: projectAccountList) {
				
				DisplayAccount d = new DisplayAccount();
				d.setAccountNo(p.getAccountNo());
				d.setAccountType(p.getAccountType().getName());
				d.setBalance(p.getBalance());
				
				displayProjectAccountList.add(d);
			}
			
			return displayProjectAccountList;
		}
		else
			return null;
	}

	@Override
	@Transactional
	public DisplayProject getDisplayProject(String projectId) {

		Project project = facultyDAOs.getProject(projectId);
		
		if(project != null) {
			
			DisplayProject displayProject = new DisplayProject();
			
			displayProject.setDescription(project.getDescription());
			displayProject.setId(project.getId());
			displayProject.setName(project.getName());
			displayProject.setProjectIncharge(project.getProjectIncharge().getEmailId());
			displayProject.setSanctionedAmount(project.getSanctionedAmount());
			displayProject.setClosingDate(project.getClosingDate());
			displayProject.setOpeningDate(project.getOpeningDate());
			displayProject.setStatus(project.getStatus().name());
			
			return displayProject;
		}
		else
			return null;
	}

	@Override
	@Transactional
	public String addProjectAccount(String projectId, NewProjectAccountForm newProjectAccountForm) {
		
		Project project = facultyDAOs.getProject(projectId);
		ProjectAccountType projectAccountType = facultyDAOs.getProjectAccountType(newProjectAccountForm.getAccountTypeId()); 
		
		if(project != null && projectAccountType != null) {
			
			ProjectAccount projectAccount = new ProjectAccount();
			
			Double inputAmount = Double.parseDouble(newProjectAccountForm.getBalance());
			Double currentBalance = Double.parseDouble(project.getSanctionedAmount()) + inputAmount;

			projectAccount.setAccountType(projectAccountType);
			projectAccount.setBalance(newProjectAccountForm.getBalance());
			
			project.setSanctionedAmount(currentBalance.toString());			
			project.add(projectAccount);
			
			// transaction entry
			ProjectTransaction projectTransaction = new ProjectTransaction();
			projectTransaction.setClosingBalance(newProjectAccountForm.getBalance());
			projectTransaction.setOpeningBalance("0");
			projectTransaction.setType(TransactionType.CREDIT);
			projectAccount.add(projectTransaction);
			
			return "Account Added Successfully!<br>Account No: " + projectAccount.getAccountNo(); 
		}
		else
			return "Invalid Request!";
	}

	@Override
	@Transactional
	public List<ProjectAccountType> getProjectAccountTypeList() {
		
		return facultyDAOs.getProjectAccountTypeList();
	}

	@Override
	@Transactional
	public String addFacultyToProject(String projectId, String facultyEmailId) {
		// get Project
		Project project = facultyDAOs.getProject(projectId);
		// get User
		User user = facultyDAOs.getUserByEmailId(facultyEmailId);
		
		if(user != null && project != null) {
			// add user to Project
			user.add(project);
			
			// add project to user
			//project.add(user);
			
			return facultyEmailId + " Added Successfully!";
		}
		else
			return "Invalid Request!";
	}

	@Override
	@Transactional
	public List<User> getProjectFacultyList(String projectId) {
		
		Project project = facultyDAOs.getProject(projectId);
		
		if(project != null) {
			
			List<User> facultyList = new ArrayList<User>(); 
			
			for(User u:	project.getUserList()) 
				facultyList.add(u);
			
			return facultyList;
		}
		else
			return null;
	}
	
}
