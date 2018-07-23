package in.iiits.fundally.procurement.dao;

import java.util.List;

import in.iiits.fundally.procurement.entity.InstituteQuotation;
import in.iiits.fundally.procurement.entity.InstituteRequest;
import in.iiits.fundally.procurement.entity.InstituteRequestStage;
import in.iiits.fundally.procurement.entity.InstituteRequestStageType;
import in.iiits.fundally.procurement.entity.InstituteResourceDocument;
import in.iiits.fundally.procurement.entity.ProjectQuotation;
import in.iiits.fundally.procurement.entity.ProjectRequest;
import in.iiits.fundally.procurement.entity.ProjectRequestStage;
import in.iiits.fundally.procurement.entity.ProjectResourceDocument;
import in.iiits.fundally.procurement.entity.UserSession;

public interface ProcurementDAOs {

	// common
	
	UserSession getSession(String sessionId);
	
	InstituteRequestStageType getStageType(String stageId);
	
	// institute

	List<InstituteRequest> getInstituteRequestListByStageType(InstituteRequestStageType stageType);

	InstituteRequest getInstituteRequest(String requestId);

	List<InstituteQuotation> getInstituteQuotationList(InstituteRequest request);

	List<InstituteRequestStage> getInstituteRequestStageList(InstituteRequest request);

	InstituteQuotation getInstituteQuotation(String documentId);

	InstituteResourceDocument getInstituteResourceDocument(String documentId);
	
	
	// project
	
	List<ProjectRequest> getProjectRequestListByStageType(InstituteRequestStageType stageType);

	ProjectRequest getProjectRequest(String requestId);
	
	List<ProjectQuotation> getProjectQuotationList(ProjectRequest request);

	List<ProjectRequestStage> getProjectRequestStageList(ProjectRequest request);

	ProjectQuotation getProjectQuotation(String documentId);

	ProjectResourceDocument getProjectResourceDocument(String documentId);

}
