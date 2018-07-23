package in.iiits.fundally.admin.dao;

import java.util.List;

import in.iiits.fundally.admin.entity.InstituteAccount;
import in.iiits.fundally.admin.entity.InstituteAccountType;
import in.iiits.fundally.admin.entity.InstituteQuotation;
import in.iiits.fundally.admin.entity.InstituteRequest;
import in.iiits.fundally.admin.entity.InstituteRequestStage;
import in.iiits.fundally.admin.entity.InstituteRequestStageType;
import in.iiits.fundally.admin.entity.Project;
import in.iiits.fundally.admin.entity.ProjectAccount;
import in.iiits.fundally.admin.entity.ProjectQuotation;
import in.iiits.fundally.admin.entity.ProjectRequest;
import in.iiits.fundally.admin.entity.ProjectRequestStage;
import in.iiits.fundally.admin.entity.User;
import in.iiits.fundally.admin.entity.UserSession;

public interface AdminDAOs {

	// common
	
	UserSession getSession(String sessionId);
	
	List<User> getFacultyListByDepartment(String theDepartment);
	
	InstituteRequestStageType getStageType(String stageId);
	
	User getUser(String userId);

	void saveUser(User user);
	
	
	
	// institute

	InstituteAccountType getInstituteAccountType(String accountTypeId);
	
	List<InstituteRequest> getInstituteRequestListByStageType(InstituteRequestStageType stageType);

	InstituteRequest getInstituteRequest(String requestId);

	List<InstituteQuotation> getInstituteQuotationList(InstituteRequest request);

	List<InstituteRequestStage> getInstituteRequestStageList(InstituteRequest request);

	InstituteAccount getInstituteAccount(String accountNo);

	List<InstituteRequest> getInstituteRequestListByAccount(String accountNo);
	
	
	// Project 

	List<ProjectRequest> getProjectRequestListByStageType(InstituteRequestStageType stageType);

	ProjectRequest getProjectRequest(String requestId);

	List<ProjectQuotation> getProjectQuotationList(ProjectRequest request);

	List<ProjectRequestStage> getProjectRequestStageList(ProjectRequest request);

	Project getProject(String projectId);

	List<ProjectRequest> getProjectRequestListByAccount(String accountNo);

	ProjectAccount getProjectAccount(String accountNo);
}
