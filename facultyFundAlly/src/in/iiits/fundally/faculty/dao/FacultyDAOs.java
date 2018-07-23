package in.iiits.fundally.faculty.dao;

import java.util.List;

import in.iiits.fundally.faculty.entity.InstituteAccount;
import in.iiits.fundally.faculty.entity.InstituteQuotation;
import in.iiits.fundally.faculty.entity.InstituteRequest;
import in.iiits.fundally.faculty.entity.InstituteRequestStage;
import in.iiits.fundally.faculty.entity.InstituteRequestStageType;
import in.iiits.fundally.faculty.entity.InstituteResource;
import in.iiits.fundally.faculty.entity.Project;
import in.iiits.fundally.faculty.entity.ProjectAccount;
import in.iiits.fundally.faculty.entity.ProjectAccountType;
import in.iiits.fundally.faculty.entity.ProjectQuotation;
import in.iiits.fundally.faculty.entity.ProjectRequest;
import in.iiits.fundally.faculty.entity.ProjectRequestStage;
import in.iiits.fundally.faculty.entity.ProjectResource;
import in.iiits.fundally.faculty.entity.User;
import in.iiits.fundally.faculty.entity.UserSession;

public interface FacultyDAOs {

	public User getUser(String userId);
	
	public User getUserByEmailId(String facultyEmailId);

	
	// Institute
	
	public InstituteAccount getInstituteAccount(String accountNo);

	public InstituteRequestStageType getStageType(String stageId);

	public void saveStage(InstituteRequestStage stage);

	public String saveAccount(InstituteAccount theAccount);

	public UserSession getSession(String sessionId);

	public InstituteRequest getRequest(String requestId);

	public List<InstituteRequestStage> getRequestStageList(InstituteRequest request);

	public List<InstituteRequest> getRequestList(String accountNo);

	public List<InstituteQuotation> getQuotationList(InstituteRequest request);

	public List<InstituteRequest> getPendingInstituteIssueRequestList(InstituteResource resource, InstituteRequestStageType stageType);

	
	
	//Project
	
	
	public String saveProjectAccountType(ProjectAccountType accountType);

	public ProjectAccount getProjectAccount(String accountNo);

	public ProjectRequest getProjectRequest(String requestId);

	public List<ProjectRequest> getPendingProjectIssueRequestList(ProjectResource resource,
			InstituteRequestStageType stageType);

	public List<ProjectRequest> getProjectRequestList(String accountNo);

	public List<ProjectQuotation> getProjectQuotationList(ProjectRequest request);

	public List<ProjectRequestStage> getProjectRequestStageList(ProjectRequest request);

	public Project getProject(String projectId);

	public void saveProject(Project project);

	public List<ProjectAccountType> getProjectAccountTypeList();

	public ProjectAccountType getProjectAccountType(String accountTypeId);

}
