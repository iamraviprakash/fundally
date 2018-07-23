package in.iiits.fundally.admin.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import in.iiits.fundally.admin.classes.DisplayAccount;
import in.iiits.fundally.admin.classes.DisplayInstituteRequestProfile;
import in.iiits.fundally.admin.classes.DisplayProject;
import in.iiits.fundally.admin.classes.DisplayProjectRequestProfile;
import in.iiits.fundally.admin.classes.DisplayRequest;
import in.iiits.fundally.admin.classes.DisplayTransaction;
import in.iiits.fundally.admin.classes.NewUserFormData;
import in.iiits.fundally.admin.dao.AdminDAOs;
import in.iiits.fundally.admin.entity.InstituteAccount;
import in.iiits.fundally.admin.entity.InstituteAccountType;
import in.iiits.fundally.admin.entity.InstituteRequest;
import in.iiits.fundally.admin.entity.InstituteRequestStage;
import in.iiits.fundally.admin.entity.InstituteRequestStageType;
import in.iiits.fundally.admin.entity.InstituteTransaction;
import in.iiits.fundally.admin.entity.Project;
import in.iiits.fundally.admin.entity.ProjectAccount;
import in.iiits.fundally.admin.entity.ProjectRequest;
import in.iiits.fundally.admin.entity.ProjectRequestStage;
import in.iiits.fundally.admin.entity.ProjectTransaction;
import in.iiits.fundally.admin.entity.Status;
import in.iiits.fundally.admin.entity.TransactionType;
import in.iiits.fundally.admin.entity.User;
import in.iiits.fundally.admin.entity.UserSession;

@Service
public class AdminServicesImpl implements AdminServices {

	
	// need to inject admin DAO
	@Autowired
	private AdminDAOs adminDAOs;
	
	
	// common
	
	@Transactional
	@Override
	public String checkSessionStatus(String sessionId, String userType) {
		UserSession userSession = adminDAOs.getSession(sessionId);
		if(userSession != null){
			if(userSession.getStatus().equals(Status.ACTIVE) && userSession.getUser().getType().equals(userType)) 
				return "ACTIVE";
			else
				return "INACTIVE";
		}
		else
			return "INACTIVE";
	}
	
	@Transactional
	@Override
	public String getRequestStageId(String requestId) {
		// get Request
		InstituteRequest instituteRequest = adminDAOs.getInstituteRequest(requestId);
		ProjectRequest projectRequest = adminDAOs.getProjectRequest(requestId);
		
		if(instituteRequest != null)
			// return stage
			return instituteRequest.getStageType().getId();
		else if(projectRequest != null)
			return projectRequest.getStageType().getId();
		else
			return null;
	}
	
	@Transactional
	@Override
	public List<DisplayRequest> getAllDisplayRequestList() {
		
		// for purchased resource
		InstituteRequestStageType stageType = adminDAOs.getStageType("NRQ");
		List<DisplayRequest> displayResourceRequestList = new ArrayList<DisplayRequest>();
		
		List<InstituteRequest> requestList = adminDAOs.getInstituteRequestListByStageType(stageType);
		
		DisplayRequest displayRequest = null;
		
		for(InstituteRequest r: requestList) {
			
			displayRequest = new DisplayRequest();
			
			displayRequest.setRequestSource("Institute");
			displayRequest.setAmount(r.getResource().getAmount());
			displayRequest.setRequesterEmailId(r.getAccount().getUser().getEmailId());
			displayRequest.setDeadline(r.getResource().getDeadlineDate());
			displayRequest.setRequestId(r.getId());
			displayRequest.setResourceId(r.getResource().getId());
			displayRequest.setResourceName(r.getResource().getName());
			displayRequest.setStatus(r.getStatus().name());
			displayRequest.setStage(r.getStageType().getName());
			displayRequest.setType(r.getResource().getType().name());
			
			displayResourceRequestList.add(displayRequest);
		}
		
		
		List<ProjectRequest> projectRequestList = adminDAOs.getProjectRequestListByStageType(stageType);
		
		for(ProjectRequest r: projectRequestList) {
			
			displayRequest = new DisplayRequest();
			
			displayRequest.setRequestSource("Project");
			displayRequest.setAmount(r.getResource().getAmount());
			displayRequest.setRequesterEmailId(r.getUser().getEmailId());
			displayRequest.setDeadline(r.getResource().getDeadlineDate());
			displayRequest.setRequestId(r.getId());
			displayRequest.setResourceId(r.getResource().getId());
			displayRequest.setResourceName(r.getResource().getName());
			displayRequest.setStatus(r.getStatus().name());
			displayRequest.setStage(r.getStageType().getName());
			displayRequest.setType(r.getResource().getType().name());
			
						
			displayResourceRequestList.add(displayRequest);
		}
		
		
		return displayResourceRequestList;
	}
	
	@Transactional
	@Override
	public String approveRequest(String source, String requestId) {
		// get Request
		InstituteRequest instituteRequest = adminDAOs.getInstituteRequest(requestId);
		
		ProjectRequest projectRequest = adminDAOs.getProjectRequest(requestId);
		
		if(instituteRequest != null && source.equals("institute") && instituteRequest.getStageType().getId().equals("NRQ")) {
			// get Stage Type
			InstituteRequestStageType stageType = adminDAOs.getStageType("RV");
			
			// create Request Stage Instance
			InstituteRequestStage stage = new InstituteRequestStage();
			
			stage.setRequest(instituteRequest);
			stage.setStageType(stageType);
			
			// set and update request
			instituteRequest.setStageType(stageType);
			instituteRequest.add(stage);
			
			return "Request Approved!";
		}
		else if(projectRequest != null && source.equals("project") && projectRequest.getStageType().getId().equals("NRQ")) {
			
			// get Stage Type
			InstituteRequestStageType stageType = adminDAOs.getStageType("RV");
			
			// create Request Stage Instance
			ProjectRequestStage stage = new ProjectRequestStage();
			
			stage.setRequest(projectRequest);
			stage.setStageType(stageType);
			
			// set and update request
			projectRequest.setStageType(stageType);
			projectRequest.add(stage);
			
			return "Request Approved!";
		}
		else 
			return "Invalid Request!";
	}
	
	@Override
	@Transactional
	public List<User> getFacultyListByDepartment(String department) {
		
		return adminDAOs.getFacultyListByDepartment(department);
	}
	
	@Override
	@Transactional
	public User getUser(String sessionId) {
		UserSession session = adminDAOs.getSession(sessionId);
		return session.getUser();
	}
	
	@Override
	@Transactional
	public User getFaculty(String userId) {

		return adminDAOs.getUser(userId);
	}
	
	
	// Institute
	
	@Override
	@Transactional
	public String addUserAndAccount(NewUserFormData newUserFormData) {
		// create a new user
		User user = new User();
		
		user.setDepartment(newUserFormData.getDepartment());
		user.setEmailId(newUserFormData.getEmailId());
		user.setPassword(newUserFormData.getPassword());
		user.setFirstName(newUserFormData.getFirstName());
		user.setLastName(newUserFormData.getLastName());
		user.setType("faculty");
		
		// save the user
		adminDAOs.saveUser(user);
		
		
		// create account instance
		InstituteAccount instituteAccount = null; 
		InstituteAccountType instituteAccountType = null;
		InstituteTransaction instituteTransaction = null;
		
		// set different accounts
		
		instituteAccount = new InstituteAccount();
		instituteAccountType = adminDAOs.getInstituteAccountType("PRFA");
		instituteAccount.setAccountType(instituteAccountType);
		instituteAccount.setBalance(newUserFormData.getPRFABalance());
		user.add(instituteAccount);
		// transaction entry
		instituteTransaction = new InstituteTransaction();
		instituteTransaction.setClosingBalance(newUserFormData.getPRFABalance());
		instituteTransaction.setOpeningBalance("0");
		instituteTransaction.setType(TransactionType.CREDIT);
		instituteAccount.add(instituteTransaction);
	
						
		instituteAccount = new InstituteAccount();
		instituteAccountType = adminDAOs.getInstituteAccountType("PDFA");
		instituteAccount.setAccountType(instituteAccountType);
		instituteAccount.setBalance(newUserFormData.getPDFABalance());
		user.add(instituteAccount);
		// transaction entry
		instituteTransaction = new InstituteTransaction();
		instituteTransaction.setClosingBalance(newUserFormData.getPDFABalance());
		instituteTransaction.setOpeningBalance("0");
		instituteTransaction.setType(TransactionType.CREDIT);
		instituteAccount.add(instituteTransaction);
		
		
		instituteAccount = new InstituteAccount();
		instituteAccountType = adminDAOs.getInstituteAccountType("SGFA");
		instituteAccount.setAccountType(instituteAccountType);
		instituteAccount.setBalance(newUserFormData.getSGFABalance());
		user.add(instituteAccount);
		// transaction entry
		instituteTransaction = new InstituteTransaction();
		instituteTransaction.setClosingBalance(newUserFormData.getSGFABalance());
		instituteTransaction.setOpeningBalance("0");
		instituteTransaction.setType(TransactionType.CREDIT);
		instituteAccount.add(instituteTransaction);
		
		
		instituteAccount = new InstituteAccount();
		instituteAccountType = adminDAOs.getInstituteAccountType("FDFA");
		instituteAccount.setAccountType(instituteAccountType);
		instituteAccount.setBalance(newUserFormData.getFDFABalance());
		user.add(instituteAccount);
		// transaction entry
		instituteTransaction = new InstituteTransaction();
		instituteTransaction.setClosingBalance(newUserFormData.getFDFABalance());
		instituteTransaction.setOpeningBalance("0");
		instituteTransaction.setType(TransactionType.CREDIT);
		instituteAccount.add(instituteTransaction);
		
		
		return "User Added Successfully! <br>User Id: " + user.getId();
	}

	@Transactional
	@Override
	public DisplayInstituteRequestProfile getDisplayInstituteRequestProfile(String requestId) {
		
		DisplayInstituteRequestProfile profile = new DisplayInstituteRequestProfile();
		
		// get Request
		InstituteRequest request = adminDAOs.getInstituteRequest(requestId);
		
		if(request != null) {
			// set Request Profile
			profile.setInstituteAccount(request.getAccount());
			profile.setUser(request.getAccount().getUser());
			profile.setInstituteQuotationList(adminDAOs.getInstituteQuotationList(request));
			profile.setInstituteRequest(request);
			profile.setInstituteRequestStageList(adminDAOs.getInstituteRequestStageList(request));
			profile.setInstituteResource(request.getResource());
			profile.setInstituteResourceDocumentList(request.getResource().getResourceDocumentList());		
			profile.setInstituteTransaction(request.getTransaction());
		}
		
		return profile;
	}
	
	@Override
	@Transactional
	public String refillAccount(String accountNo, String amount) {
		InstituteAccount instituteAccount = adminDAOs.getInstituteAccount(accountNo);
		
		if(instituteAccount != null) {
			Double currentBalance = Double.parseDouble(instituteAccount.getBalance());
			Double newBalance = currentBalance + Double.parseDouble(amount);
			
			InstituteTransaction instituteTransaction = new InstituteTransaction();
			instituteTransaction.setClosingBalance(newBalance.toString());
			instituteTransaction.setOpeningBalance(currentBalance.toString());
			instituteTransaction.setType(TransactionType.CREDIT);
			
			instituteAccount.setBalance(newBalance.toString());
			instituteAccount.add(instituteTransaction);
			
			return "Account Refilled Successfully!<br>Transaction Id: " + instituteTransaction.getId();
		}
		else
			return "Invalid Account No";
		
	}
	
	@Override
	@Transactional
	public List<DisplayAccount> getInstituteDisplayAccountListByUser(String userId) {
		
		List<InstituteAccount> instituteAccountList = adminDAOs.getUser(userId).getInstituteAccountList();
		List<DisplayAccount> instituteDisplayAccountList = new ArrayList<DisplayAccount>(); 
		
		for(InstituteAccount i: instituteAccountList) {
			DisplayAccount d = new DisplayAccount();
			d.setAccountNo(i.getAccountNo());
			d.setAccountType(i.getAccountType().getName());
			d.setBalance(i.getBalance());
			instituteDisplayAccountList.add(d);
		}
		
		return instituteDisplayAccountList;
	}
	
	@Override
	@Transactional
	public List<DisplayRequest> getInstituteDisplayRequestListByAccount(String accountNo) {
		
		List<InstituteRequest> requestList = adminDAOs.getInstituteRequestListByAccount(accountNo);
		
		List<DisplayRequest> displayRequestList = new ArrayList<DisplayRequest>();
		
		DisplayRequest displayRequest = null;
		
		for(InstituteRequest r: requestList) {
			
			displayRequest = new DisplayRequest();
			
			displayRequest.setRequestSource("Institute");
			displayRequest.setRequesterEmailId(r.getAccount().getUser().getEmailId());
			displayRequest.setAmount(r.getResource().getAmount());
			displayRequest.setDeadline(r.getResource().getDeadlineDate());
			displayRequest.setRequestId(r.getId());
			displayRequest.setResourceId(r.getResource().getId());
			displayRequest.setResourceName(r.getResource().getName());
			displayRequest.setStage(r.getStageType().getName()); 
			displayRequest.setStatus(r.getStatus().name());
			displayRequest.setType(r.getResource().getType().name());
			
			displayRequestList.add(displayRequest);
		}
		
		return displayRequestList;
	}
	
	@Override
	@Transactional
	public List<DisplayTransaction> getInstituteDisplayTransactionList(String accountNo) {

		InstituteAccount account = adminDAOs.getInstituteAccount(accountNo);
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

	
	
	
	// Project
	
	
	@Transactional
	@Override
	public DisplayProjectRequestProfile getDisplayProjectRequestProfile(String requestId) {
		
		DisplayProjectRequestProfile profile = new DisplayProjectRequestProfile();
		
		// get Request
		ProjectRequest request = adminDAOs.getProjectRequest(requestId);
		
		if(request != null) {
			// set Request Profile
			profile.setProject(request.getAccount().getProject());
			profile.setProjectAccount(request.getAccount());
			profile.setUser(request.getUser());
			profile.setProjectQuotationList(adminDAOs.getProjectQuotationList(request));
			profile.setProjectRequest(request);
			profile.setProjectRequestStageList(adminDAOs.getProjectRequestStageList(request));
			profile.setProjectResource(request.getResource());
			profile.setProjectResourceDocumentList(request.getResource().getResourceDocumentList());		
			profile.setProjectTransaction(request.getTransaction());
		}
		
		return profile;
	}
	
	@Override
	@Transactional
	public DisplayProject getDisplayProject(String projectId) {

		Project project = adminDAOs.getProject(projectId);
		
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
	public List<DisplayAccount> getProjectDisplayAccountList(String projectId) {
		
		List<ProjectAccount> projectAccountList = adminDAOs.getProject(projectId).getProjectAccountList();
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
	public List<User> getProjectFacultyList(String projectId) {
		
		Project project = adminDAOs.getProject(projectId);
		
		if(project != null) {
			
			List<User> facultyList = new ArrayList<User>(); 
			
			for(User u:	project.getUserList()) 
				facultyList.add(u);
			
			return facultyList;
		}
		else
			return null;
	}
	
	@Override
	@Transactional
	public List<DisplayProject> getDisplayProjectListByUser(String userId) {
		
		User user = adminDAOs.getUser(userId);
		
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
	public List<DisplayRequest> getProjectDisplayRequestListByAccount(String accountNo) {
		
		List<ProjectRequest> requestList = adminDAOs.getProjectRequestListByAccount(accountNo);
		
		List<DisplayRequest> displayResourceRequestList = new ArrayList<DisplayRequest>();
		
		DisplayRequest displayRequest = null;
		
		for(ProjectRequest r: requestList) {
			
			displayRequest = new DisplayRequest();
			
			displayRequest.setRequestSource("Project");
			displayRequest.setRequesterEmailId(r.getUser().getEmailId());
			displayRequest.setAmount(r.getResource().getAmount());
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
	public List<DisplayTransaction> getProjectDisplayTransactionList(String accountNo) {

		ProjectAccount account = adminDAOs.getProjectAccount(accountNo);
		
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
	public String cancelRequest(String source, String requestId) {
		// get Request
		InstituteRequest instituteRequest = adminDAOs.getInstituteRequest(requestId);
		
		ProjectRequest projectRequest = adminDAOs.getProjectRequest(requestId);
		
		// current timestamp
		java.util.Date today = new java.util.Date();
		java.sql.Timestamp timestamp = new java.sql.Timestamp(today.getTime());
		
		if(instituteRequest != null && source.equals("institute") && instituteRequest.getStageType().getId().equals("NRQ")) {
			// get Stage Type
			InstituteRequestStageType stageType = adminDAOs.getStageType("RJT");
			
			// create Request Stage Instance
			InstituteRequestStage stage = new InstituteRequestStage();
			
			stage.setRequest(instituteRequest);
			stage.setStageType(stageType);
			
			// set and update request
			instituteRequest.setStageType(stageType);
			instituteRequest.setEndDate(timestamp.toString());
			instituteRequest.setStatus(Status.FINISHED);
			instituteRequest.add(stage);
			
			return "Request Cancelled!";
		}
		else if(projectRequest != null && source.equals("project") && projectRequest.getStageType().getId().equals("NRQ")) {
			// get Stage Type
			InstituteRequestStageType stageType = adminDAOs.getStageType("RJT");
			
			// create Request Stage Instance
			ProjectRequestStage stage = new ProjectRequestStage();
			
			stage.setRequest(projectRequest);
			stage.setStageType(stageType);
			
			// set and update request
			projectRequest.setStageType(stageType);
			projectRequest.setEndDate(timestamp.toString());
			projectRequest.setStatus(Status.FINISHED);
			projectRequest.add(stage);
			
			return "Request Cancelled!";
		}
		else 
			return "Invalid Request!";
	}

}
