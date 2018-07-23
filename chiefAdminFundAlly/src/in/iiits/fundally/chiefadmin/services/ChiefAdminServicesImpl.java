package in.iiits.fundally.chiefadmin.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import in.iiits.fundally.chiefadmin.classes.DisplayAccount;
import in.iiits.fundally.chiefadmin.classes.DisplayInstituteRequestProfile;
import in.iiits.fundally.chiefadmin.classes.DisplayProject;
import in.iiits.fundally.chiefadmin.classes.DisplayProjectRequestProfile;
import in.iiits.fundally.chiefadmin.classes.DisplayRequest;
import in.iiits.fundally.chiefadmin.classes.DisplayTransaction;
import in.iiits.fundally.chiefadmin.dao.ChiefAdminDAOs;
import in.iiits.fundally.chiefadmin.entity.InstituteAccount;
import in.iiits.fundally.chiefadmin.entity.InstituteQuotation;
import in.iiits.fundally.chiefadmin.entity.InstituteRequest;
import in.iiits.fundally.chiefadmin.entity.InstituteRequestStage;
import in.iiits.fundally.chiefadmin.entity.InstituteRequestStageType;
import in.iiits.fundally.chiefadmin.entity.InstituteTransaction;
import in.iiits.fundally.chiefadmin.entity.Project;
import in.iiits.fundally.chiefadmin.entity.ProjectAccount;
import in.iiits.fundally.chiefadmin.entity.ProjectQuotation;
import in.iiits.fundally.chiefadmin.entity.ProjectRequest;
import in.iiits.fundally.chiefadmin.entity.ProjectRequestStage;
import in.iiits.fundally.chiefadmin.entity.ProjectTransaction;
import in.iiits.fundally.chiefadmin.entity.ResourceType;
import in.iiits.fundally.chiefadmin.entity.Status;
import in.iiits.fundally.chiefadmin.entity.TransactionType;
import in.iiits.fundally.chiefadmin.entity.User;
import in.iiits.fundally.chiefadmin.entity.UserSession;

@Service
public class ChiefAdminServicesImpl implements ChiefAdminServices {

	@Autowired
	private ChiefAdminDAOs chiefAdminDAOs;
	
	// common
	
	@Transactional
	@Override
	public String checkSessionStatus(String sessionId, String userType) {
		UserSession userSession = chiefAdminDAOs.getSession(sessionId);
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
	public List<DisplayRequest> getAllDisplayRequestList() {
		
		// institute
		
		// for purchased resource
		InstituteRequestStageType stageType = chiefAdminDAOs.getStageType("QP");
		List<InstituteRequest> instituteRequestList = chiefAdminDAOs.getInstituteRequestListByStageType(stageType);

		List<DisplayRequest> displayResourceRequestList = new ArrayList<DisplayRequest>();
		
		DisplayRequest displayRequest = null;
		
		for(InstituteRequest r: instituteRequestList) {
			
			displayRequest = new DisplayRequest();
			
			displayRequest.setRequestSource("Institute");
			displayRequest.setRequesterEmailId(r.getAccount().getUser().getEmailId());
			displayRequest.setStage(r.getStageType().getName());
			displayRequest.setAmount(r.getResource().getAmount());
			displayRequest.setDeadline(r.getResource().getDeadlineDate());
			displayRequest.setRequestId(r.getId());
			displayRequest.setResourceId(r.getResource().getId());
			displayRequest.setResourceName(r.getResource().getName());
			displayRequest.setStatus(r.getStatus().name());
			displayRequest.setType(r.getResource().getType().name());
			
			displayResourceRequestList.add(displayRequest);
		}
		
		//get request list if rv and intangible type
		stageType = chiefAdminDAOs.getStageType("RV");
		List<InstituteRequest> instituteRequestList2 = chiefAdminDAOs.getInstituteRequestListByStageType(stageType);
		
		for(InstituteRequest r: instituteRequestList2) {
			if(r.getResource().getType().equals(ResourceType.INTANGIBLE)) {
				
				displayRequest = new DisplayRequest();
				
				displayRequest.setRequestSource("Institute");
				displayRequest.setRequesterEmailId(r.getAccount().getUser().getEmailId());
				displayRequest.setStage(r.getStageType().getName());
				displayRequest.setAmount(r.getResource().getAmount());
				displayRequest.setDeadline(r.getResource().getDeadlineDate());
				displayRequest.setRequestId(r.getId());
				displayRequest.setResourceId(r.getResource().getId());
				displayRequest.setResourceName(r.getResource().getName());
				displayRequest.setStatus(r.getStatus().name());
				displayRequest.setType(r.getResource().getType().name());
				
				displayResourceRequestList.add(displayRequest);
			}
		}
		
		// project
		
		stageType = chiefAdminDAOs.getStageType("QP");
		List<ProjectRequest> projectRequestList = chiefAdminDAOs.getProjectRequestListByStageType(stageType);
		
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
		
		stageType = chiefAdminDAOs.getStageType("RV");
		List<ProjectRequest> projectRequestList2 = chiefAdminDAOs.getProjectRequestListByStageType(stageType);
		
		for(ProjectRequest r: projectRequestList2) {
			
			if(r.getResource().getType().equals(ResourceType.INTANGIBLE)) {
				
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
		}
		
		return displayResourceRequestList;
	}

	@Override
	@Transactional
	public User getFaculty(String userId) {

		return chiefAdminDAOs.getUser(userId);
	}
	
	@Transactional
	@Override
	public String getRequestStageId(String requestId) {
		
		// get Request
		InstituteRequest instituteRequest = chiefAdminDAOs.getInstituteRequest(requestId);
		ProjectRequest projectRequest = chiefAdminDAOs.getProjectRequest(requestId);
		
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
	public String approveRequest(String source, String requestId) {
		// get Request
		InstituteRequest instituteRequest = chiefAdminDAOs.getInstituteRequest(requestId);
		ProjectRequest projectRequest = chiefAdminDAOs.getProjectRequest(requestId);
		
		InstituteRequestStageType stageType = null;
		
		
		if(instituteRequest != null && source.equals("institute")) {
			
			// create Request Stage Instance
			InstituteRequestStage stage = new InstituteRequestStage();
			
			if(instituteRequest.getStageType().getId().equals("QP") && instituteRequest.getResource().getType().equals(ResourceType.TANGIBLE)) {
				
				// validate that request shall not be approved without approving quotation
				for(InstituteQuotation q: instituteRequest.getQuotationList()) {
					if(q.getStatus().equals(Status.PENDING))
						return "Approval Unsuccessful! Please approve quotation.";
				}
				
				stageType = chiefAdminDAOs.getStageType("QA");
				stage.setRequest(instituteRequest);
				stage.setStageType(stageType);
			}
			else if(instituteRequest.getStageType().getId().equals("RV") && instituteRequest.getResource().getType().equals(ResourceType.INTANGIBLE)){
				
				stageType = chiefAdminDAOs.getStageType("RA");
				stage.setRequest(instituteRequest);
				stage.setStageType(stageType);
			}
			
			// set and update request
			instituteRequest.setStageType(stageType);
			instituteRequest.add(stage);
			
			return "Request Approved!";
		}
		else if(projectRequest != null && source.equals("project")) {
			
			// create Request Stage Instance
			ProjectRequestStage stage = new ProjectRequestStage();
			
			if(projectRequest.getStageType().getId().equals("QP") && projectRequest.getResource().getType().equals(ResourceType.TANGIBLE)) {
				
				// validate that request shall not be approved without approving quotation
				for(ProjectQuotation q: projectRequest.getQuotationList()) {
					if(q.getStatus().equals(Status.PENDING))
						return "Approval Unsuccessful! Please approve quotation.";
				}
				
				stageType = chiefAdminDAOs.getStageType("QA");
				stage.setRequest(projectRequest);
				stage.setStageType(stageType);
			}
			else if(projectRequest.getStageType().getId().equals("RV") && projectRequest.getResource().getType().equals(ResourceType.INTANGIBLE)){
				
				stageType = chiefAdminDAOs.getStageType("RA");
				stage.setRequest(projectRequest);
				stage.setStageType(stageType);
			}
			
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
	public String approveQuotation(String source, String requestId, String quotationId) {
		// get request and validate that request is in QP stage
		// get quotation
		// validate
		// if all are pending then set approved one with matching quotationId
		// set rest of all as denied
		InstituteRequest instituteRequest = chiefAdminDAOs.getInstituteRequest(requestId);
		
		ProjectRequest projectRequest = chiefAdminDAOs.getProjectRequest(requestId);
		
		if(instituteRequest != null && source.equals("institute") && instituteRequest.getStageType().getId().equals("QP")) {
			
			List<InstituteQuotation> quotationList = chiefAdminDAOs.getInstituteQuotationList(instituteRequest);
			
			InstituteQuotation selectedQuotation = chiefAdminDAOs.getInstituteQuotation(quotationId);
		
			if(selectedQuotation != null && quotationList.contains(selectedQuotation)) {
				
				for(InstituteQuotation q: quotationList) {
				
					if(q.getStatus().equals(Status.PENDING)) {
						
						if(q.equals(selectedQuotation))
							q.setStatus(Status.APPROVED);
						else
							q.setStatus(Status.DENIED);
					}
					else
						return "Invalid Request!";
				}
				
				return "Quotation Approved Successfully!";
			}
			else
				return "Invalid Request!";
		}
		else if(projectRequest != null && source.equals("project") && projectRequest.getStageType().getId().equals("QP")) {
			
			List<ProjectQuotation> quotationList = chiefAdminDAOs.getProjectQuotationList(projectRequest);
			
			ProjectQuotation selectedQuotation = chiefAdminDAOs.getProjectQuotation(quotationId);
		
			if(selectedQuotation != null && quotationList.contains(selectedQuotation)) {
				
				for(ProjectQuotation q: quotationList) {
				
					if(q.getStatus().equals(Status.PENDING)) {
						
						if(q.equals(selectedQuotation))
							q.setStatus(Status.APPROVED);
						else
							q.setStatus(Status.DENIED);
					}
					else
						return "Invalid Request!";
				}
				
				return "Quotation Approved Successfully!";
			}
			else
				return "Invalid Request!";
			
		}
		else
			return "Invalid Request!";
	}
	
	@Override
	@Transactional
	public User getUser(String sessionId) {
		UserSession session = chiefAdminDAOs.getSession(sessionId);
		return session.getUser();
	}
	
	@Override
	@Transactional
	public List<User> getFacultyListByDepartment(String department) {
		
		return chiefAdminDAOs.getFacultyListByDepartment(department);
	}
	
	
	// institute
	
	@Transactional
	@Override
	public DisplayInstituteRequestProfile getDisplayInstituteRequestProfile(String requestId) {
		
		DisplayInstituteRequestProfile profile = new DisplayInstituteRequestProfile();
		
		// get Request
		InstituteRequest request = chiefAdminDAOs.getInstituteRequest(requestId);
		
		if(request != null) {
			// set Request Profile
			profile.setInstituteAccount(request.getAccount());
			profile.setUser(request.getAccount().getUser());
			profile.setInstituteQuotationList(chiefAdminDAOs.getInstituteQuotationList(request));
			profile.setInstituteRequest(request);
			profile.setInstituteRequestStageList(chiefAdminDAOs.getInstituteRequestStageList(request));
			profile.setInstituteResource(request.getResource());
			profile.setInstituteResourceDocumentList(request.getResource().getResourceDocumentList());		
			profile.setInstituteTransaction(request.getTransaction());
		}
		
		return profile;
	}
	
	@Override
	@Transactional
	public List<DisplayAccount> getInstituteDisplayAccountListByUser(String userId) {
		
		List<InstituteAccount> instituteAccountList = chiefAdminDAOs.getUser(userId).getInstituteAccountList();
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
	public List<DisplayRequest> getInstituteDisplayRequestListByAccount(String accountNo) {
		
		List<InstituteRequest> requestList = chiefAdminDAOs.getInstituteRequestListByAccount(accountNo);
		
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

		InstituteAccount account = chiefAdminDAOs.getInstituteAccount(accountNo);
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

	
	
	// project
	
	
	@Override
	@Transactional
	public List<DisplayProject> getDisplayProjectListByUser(String userId) {
		
		User user = chiefAdminDAOs.getUser(userId);
		
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

	@Transactional
	@Override
	public DisplayProjectRequestProfile getDisplayProjectRequestProfile(String requestId) {
		
		DisplayProjectRequestProfile profile = new DisplayProjectRequestProfile();
		
		// get Request
		ProjectRequest request = chiefAdminDAOs.getProjectRequest(requestId);
		
		if(request != null) {
			// set Request Profile
			profile.setProject(request.getAccount().getProject());
			profile.setProjectAccount(request.getAccount());
			profile.setUser(request.getUser());
			profile.setProjectQuotationList(chiefAdminDAOs.getProjectQuotationList(request));
			profile.setProjectRequest(request);
			profile.setProjectRequestStageList(chiefAdminDAOs.getProjectRequestStageList(request));
			profile.setProjectResource(request.getResource());
			profile.setProjectResourceDocumentList(request.getResource().getResourceDocumentList());		
			profile.setProjectTransaction(request.getTransaction());

			return profile;
		}
		else {
			return null;
		}
	}
	
	@Override
	@Transactional
	public DisplayProject getDisplayProject(String projectId) {

		Project project = chiefAdminDAOs.getProject(projectId);
		
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
		
		List<ProjectAccount> projectAccountList = chiefAdminDAOs.getProject(projectId).getProjectAccountList();
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
		
		Project project = chiefAdminDAOs.getProject(projectId);
		
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
	public List<DisplayRequest> getProjectDisplayRequestListByAccount(String accountNo) {
		
		List<ProjectRequest> requestList = chiefAdminDAOs.getProjectRequestListByAccount(accountNo);
		
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

		ProjectAccount account = chiefAdminDAOs.getProjectAccount(accountNo);
		
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

}
