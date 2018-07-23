package in.iiits.fundally.chiefadmin.dao;

import java.util.List;

import in.iiits.fundally.chiefadmin.entity.InstituteAccount;
import in.iiits.fundally.chiefadmin.entity.InstituteQuotation;
import in.iiits.fundally.chiefadmin.entity.InstituteRequest;
import in.iiits.fundally.chiefadmin.entity.InstituteRequestStage;
import in.iiits.fundally.chiefadmin.entity.InstituteRequestStageType;
import in.iiits.fundally.chiefadmin.entity.Project;
import in.iiits.fundally.chiefadmin.entity.ProjectAccount;
import in.iiits.fundally.chiefadmin.entity.ProjectQuotation;
import in.iiits.fundally.chiefadmin.entity.ProjectRequest;
import in.iiits.fundally.chiefadmin.entity.ProjectRequestStage;
import in.iiits.fundally.chiefadmin.entity.User;
import in.iiits.fundally.chiefadmin.entity.UserSession;

public interface ChiefAdminDAOs {

	// common
	
	UserSession getSession(String sessionId);
	
	User getUser(String userId);
	
	List<User> getFacultyListByDepartment(String department);

	InstituteRequestStageType getStageType(String stageId);
	
	
	// institute
	
	List<InstituteRequest> getInstituteRequestListByStageType(InstituteRequestStageType stageType);

	InstituteRequest getInstituteRequest(String requestId);

	List<InstituteQuotation> getInstituteQuotationList(InstituteRequest request);

	List<InstituteRequestStage> getInstituteRequestStageList(InstituteRequest request);

	InstituteQuotation getInstituteQuotation(String quotationId);

	List<InstituteRequest> getInstituteRequestListByAccount(String accountNo);

	InstituteAccount getInstituteAccount(String accountNo);
	
	
	
	
	// project

	List<ProjectRequest> getProjectRequestListByStageType(InstituteRequestStageType stageType);
	
	ProjectRequest getProjectRequest(String requestId);

	List<ProjectQuotation> getProjectQuotationList(ProjectRequest request);

	List<ProjectRequestStage> getProjectRequestStageList(ProjectRequest request);

	ProjectQuotation getProjectQuotation(String quotationId);

	Project getProject(String projectId);

	ProjectAccount getProjectAccount(String accountNo);

	List<ProjectRequest> getProjectRequestListByAccount(String accountNo);

}
