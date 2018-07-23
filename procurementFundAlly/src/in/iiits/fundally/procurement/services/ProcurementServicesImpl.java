package in.iiits.fundally.procurement.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import in.iiits.fundally.procurement.classes.DisplayInstituteRequestProfile;
import in.iiits.fundally.procurement.classes.DisplayProjectRequestProfile;
import in.iiits.fundally.procurement.classes.DisplayRequest;
import in.iiits.fundally.procurement.dao.ProcurementDAOs;
import in.iiits.fundally.procurement.entity.InstituteQuotation;
import in.iiits.fundally.procurement.entity.InstituteRequest;
import in.iiits.fundally.procurement.entity.InstituteRequestStage;
import in.iiits.fundally.procurement.entity.InstituteRequestStageType;
import in.iiits.fundally.procurement.entity.InstituteResource;
import in.iiits.fundally.procurement.entity.InstituteResourceDocument;
import in.iiits.fundally.procurement.entity.ProjectQuotation;
import in.iiits.fundally.procurement.entity.ProjectRequest;
import in.iiits.fundally.procurement.entity.ProjectRequestStage;
import in.iiits.fundally.procurement.entity.ProjectResource;
import in.iiits.fundally.procurement.entity.ProjectResourceDocument;
import in.iiits.fundally.procurement.entity.ResourceType;
import in.iiits.fundally.procurement.entity.Status;
import in.iiits.fundally.procurement.entity.User;
import in.iiits.fundally.procurement.entity.UserSession;

@Service
public class ProcurementServicesImpl implements ProcurementServices {

	@Autowired
	private ProcurementDAOs procurementDAOs;

	// common
	
	@Transactional
	@Override
	public String checkSessionStatus(String sessionId, String userType) {
		
		UserSession userSession = procurementDAOs.getSession(sessionId);
		
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
		InstituteRequest instituteRequest = procurementDAOs.getInstituteRequest(requestId);
		ProjectRequest projectRequest = procurementDAOs.getProjectRequest(requestId);
		
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
		
		// for validated request
		InstituteRequestStageType stageType = procurementDAOs.getStageType("RV");
		List<InstituteRequest> requestList = procurementDAOs.getInstituteRequestListByStageType(stageType);
		
		// for quotation approved request
		stageType = procurementDAOs.getStageType("QA");
		// appending two types of request in one
		requestList.addAll(procurementDAOs.getInstituteRequestListByStageType(stageType));
		
		// also add those requests which have issues 
		stageType = procurementDAOs.getStageType("IRQ");
		// appending two types of request in one
		requestList.addAll(procurementDAOs.getInstituteRequestListByStageType(stageType));
		
		List<DisplayRequest> displayRequestList = new ArrayList<DisplayRequest>();
		
		DisplayRequest displayRequest = null;
		
		for(InstituteRequest r: requestList) {
			if(r.getResource().getType().equals(ResourceType.TANGIBLE)) {
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
				
				displayRequestList.add(displayRequest);
			}
		}
		
		stageType = procurementDAOs.getStageType("RV");
		List<ProjectRequest> projectRequestList = procurementDAOs.getProjectRequestListByStageType(stageType);
		
		stageType = procurementDAOs.getStageType("QA");
		projectRequestList.addAll(procurementDAOs.getProjectRequestListByStageType(stageType));
		
		stageType = procurementDAOs.getStageType("IRQ");
		projectRequestList.addAll(procurementDAOs.getProjectRequestListByStageType(stageType));
		
		for(ProjectRequest r: projectRequestList) {
			if(r.getResource().getType().equals(ResourceType.TANGIBLE)) {
				
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
							
				displayRequestList.add(displayRequest);
			}
		}
		
		return displayRequestList;
	}

	@Transactional
	@Override
	public String approveRequest(String source, String requestId) {
		
		// get Request
		InstituteRequest instituteRequest = procurementDAOs.getInstituteRequest(requestId);
		ProjectRequest projectRequest = procurementDAOs.getProjectRequest(requestId);
		
		// get Stage Type
		InstituteRequestStageType stageType = null;
		
		if(instituteRequest != null && source.equals("institute")) {
			
			if(instituteRequest.getStageType().getId().equals("RV")) {
				// validate that request shall not be approved without uploading quotation
				if(instituteRequest.getQuotationList().size() == 0)
					return "Approval Unsuccessful! Please upload quotation.";
				
				stageType = procurementDAOs.getStageType("QP");
			}
			else if(instituteRequest.getStageType().getId().equals("QA")) {
				// validate that request shall not be approved without uploading quotation
				if(instituteRequest.getResource().getResourceDocumentList().size() == 0)
					return "Approval Unsuccessful! Please Resource Document.";
				
				stageType = procurementDAOs.getStageType("RP");
			}
			else if(instituteRequest.getStageType().getId().equals("IRQ")) {
				
				stageType = procurementDAOs.getStageType("RR");
				
				// create Request Stage Instance
				InstituteRequestStage stage = new InstituteRequestStage();
				stage.setRequest(instituteRequest);
				stage.setStageType(stageType);
				
				// set and update request
				instituteRequest.setStageType(stageType);
				instituteRequest.add(stage);
				
				// set status complete
				instituteRequest.setStatus(Status.FINISHED);

				return "Request Closed!";
			}
			
			// create Request Stage Instance
			InstituteRequestStage stage = new InstituteRequestStage();
			stage.setRequest(instituteRequest);
			stage.setStageType(stageType);
			
			// set and update request
			instituteRequest.setStageType(stageType);
			instituteRequest.add(stage);
			
			return "Request Approved!";
		}
		else if(projectRequest != null && source.equals("project")) {
			
			if(projectRequest.getStageType().getId().equals("RV")) {
				// validate that request shall not be approved without uploading quotation
				if(projectRequest.getQuotationList().size() == 0)
					return "Approval Unsuccessful! Please upload quotation.";
				
				stageType = procurementDAOs.getStageType("QP");
			}
			else if(projectRequest.getStageType().getId().equals("QA")) {
				// validate that request shall not be approved without uploading quotation
				if(projectRequest.getResource().getResourceDocumentList().size() == 0)
					return "Approval Unsuccessful! Please Resource Document.";
				
				stageType = procurementDAOs.getStageType("RP");
			}
			else if(projectRequest.getStageType().getId().equals("IRQ")) {
				
				stageType = procurementDAOs.getStageType("RR");
				
				// create Request Stage Instance
				ProjectRequestStage stage = new ProjectRequestStage();
				stage.setRequest(projectRequest);
				stage.setStageType(stageType);
				
				// set and update request
				projectRequest.setStageType(stageType);
				projectRequest.add(stage);
				
				// set status complete
				projectRequest.setStatus(Status.FINISHED);

				return "Request Closed!";
			}
			
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
	public User getUser(String sessionId) {
		UserSession session = procurementDAOs.getSession(sessionId);
		return session.getUser();
	}
	
	// for pdf
	@Override
	@Transactional
	public String checkSessionStatus(String sessionId) {
		
		UserSession userSession = procurementDAOs.getSession(sessionId);
		
		if(userSession != null && userSession.getStatus().equals(Status.ACTIVE))
			return "ACTIVE";
		else
			return "INACTIVE";
	}
	
	
	@Override
	@Transactional
	public String saveResourceDocument(String source, String requestId, String resourceDocumentPath) {
		
		InstituteRequest instituteRequest = procurementDAOs.getInstituteRequest(requestId);
		ProjectRequest projectRequest = procurementDAOs.getProjectRequest(requestId);
		
		if(instituteRequest != null && source.equals("institute")) {
			
			InstituteResource resource = instituteRequest.getResource();
			
			InstituteResourceDocument resourceDocument = new InstituteResourceDocument();
			resourceDocumentPath = resourceDocumentPath + "/" + resourceDocument.getId();
			resourceDocument.setPath(resourceDocumentPath);
			resourceDocument.setDescription("");
			
			resource.add(resourceDocument);
			
			return resourceDocument.getId();
		}
		else if(projectRequest != null && source.equals("project")) {
			
			ProjectResource resource = projectRequest.getResource();
			
			ProjectResourceDocument resourceDocument = new ProjectResourceDocument();
			resourceDocumentPath = resourceDocumentPath + "/" + resourceDocument.getId();
			resourceDocument.setPath(resourceDocumentPath);
			resourceDocument.setDescription("");
			
			resource.add(resourceDocument);
			
			return resourceDocument.getId();
		}
		else
			return "Invalid Request!";
	}

	@Override
	@Transactional
	public String saveQuotation(String source, String requestId, String quotationPath) {
		
		InstituteRequest instituteRequest = procurementDAOs.getInstituteRequest(requestId);
		ProjectRequest projectRequest = procurementDAOs.getProjectRequest(requestId);
		
		if(instituteRequest != null && source.equals("institute")) {
			
			InstituteQuotation quotation = new InstituteQuotation();
			quotationPath = quotationPath + "/" + quotation.getId();
			quotation.setPath(quotationPath);
			quotation.setStatus(Status.PENDING);
			
			instituteRequest.add(quotation);
			
			return quotation.getId();
		}
		else if(projectRequest != null && source.equals("project")) {
			
			ProjectQuotation quotation = new ProjectQuotation();
			quotationPath = quotationPath + "/" + quotation.getId();
			quotation.setPath(quotationPath);
			quotation.setStatus(Status.PENDING);
			
			projectRequest.add(quotation);
			
			return quotation.getId();
		}
		else
			return "Invalid Request!";
		
	}

	@Override
	@Transactional
	public String getResourceDocumentPath(String source, String documentId) {
		// get resource document
		InstituteResourceDocument instituteResourceDocument = procurementDAOs.getInstituteResourceDocument(documentId);
		ProjectResourceDocument projectResourceDocument = procurementDAOs.getProjectResourceDocument(documentId);
		
		if(instituteResourceDocument != null && source.equals("institute"))
			return instituteResourceDocument.getPath();
		else if(projectResourceDocument != null && source.equals("project"))
			return projectResourceDocument.getPath();
		else
			return "";
	}

	@Override
	@Transactional
	public String getQuotationPath(String source, String documentId) {
		// get quotation
		InstituteQuotation instituteQuotation = procurementDAOs.getInstituteQuotation(documentId);
		ProjectQuotation projectQuotation = procurementDAOs.getProjectQuotation(documentId);
		
		if(instituteQuotation != null && source.equals("institute"))
			return instituteQuotation.getPath();
		else if(projectQuotation != null && source.equals("project"))
			return projectQuotation.getPath();
		else
			return "";
	}

	
	// institute
	
	@Transactional
	@Override
	public DisplayInstituteRequestProfile getDisplayInstituteRequestProfile(String requestId) {
		DisplayInstituteRequestProfile profile = new DisplayInstituteRequestProfile();
		// get Request
		InstituteRequest request = procurementDAOs.getInstituteRequest(requestId);
		
		if(request != null) {
			// set Request Profile
			profile.setInstituteAccount(request.getAccount());
			profile.setUser(request.getAccount().getUser());
			profile.setInstituteQuotationList(procurementDAOs.getInstituteQuotationList(request));
			profile.setInstituteRequest(request);
			profile.setInstituteRequestStageList(procurementDAOs.getInstituteRequestStageList(request));
			profile.setInstituteResource(request.getResource());
			profile.setInstituteResourceDocumentList(request.getResource().getResourceDocumentList());		
			profile.setInstituteTransaction(request.getTransaction());
		}
		
		return profile;
	}
	
	
	// project
	
	@Transactional
	@Override
	public DisplayProjectRequestProfile getDisplayProjectRequestProfile(String requestId) {
		
		DisplayProjectRequestProfile profile = new DisplayProjectRequestProfile();
		
		// get Request
		ProjectRequest request = procurementDAOs.getProjectRequest(requestId);
		
		if(request != null) {
			// set Request Profile
			profile.setProject(request.getAccount().getProject());
			profile.setProjectAccount(request.getAccount());
			profile.setUser(request.getUser());
			profile.setProjectQuotationList(procurementDAOs.getProjectQuotationList(request));
			profile.setProjectRequest(request);
			profile.setProjectRequestStageList(procurementDAOs.getProjectRequestStageList(request));
			profile.setProjectResource(request.getResource());
			profile.setProjectResourceDocumentList(request.getResource().getResourceDocumentList());		
			profile.setProjectTransaction(request.getTransaction());
		}
		
		return profile;
	}
	
	


}
