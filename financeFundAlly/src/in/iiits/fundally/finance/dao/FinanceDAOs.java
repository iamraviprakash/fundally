package in.iiits.fundally.finance.dao;

import java.util.List;

import in.iiits.fundally.finance.entity.InstituteQuotation;
import in.iiits.fundally.finance.entity.InstituteRequest;
import in.iiits.fundally.finance.entity.InstituteRequestStage;
import in.iiits.fundally.finance.entity.InstituteRequestStageType;
import in.iiits.fundally.finance.entity.ProjectQuotation;
import in.iiits.fundally.finance.entity.ProjectRequest;
import in.iiits.fundally.finance.entity.ProjectRequestStage;
import in.iiits.fundally.finance.entity.UserSession;

public interface FinanceDAOs {
	
	// common
	
	UserSession getSession(String sessionId);
	
	InstituteRequestStageType getStageType(String stageId);
	
	
	// institute
	
	List<InstituteRequest> getInstituteRequestListByStageType(InstituteRequestStageType stageType);

	InstituteRequest getInstituteRequest(String requestId);

	List<InstituteQuotation> getInstituteQuotationList(InstituteRequest request);

	List<InstituteRequestStage> getInstituteRequestStageList(InstituteRequest request);
	
	
	// project
	
	List<ProjectRequest> getProjectRequestListByStageType(InstituteRequestStageType stageType);

	ProjectRequest getProjectRequest(String requestId);
	
	List<ProjectQuotation> getProjectQuotationList(ProjectRequest request);

	List<ProjectRequestStage> getProjectRequestStageList(ProjectRequest request);

}
