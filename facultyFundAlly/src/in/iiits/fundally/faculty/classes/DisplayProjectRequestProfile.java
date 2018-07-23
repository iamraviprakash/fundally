package in.iiits.fundally.faculty.classes;

import java.util.List;

import in.iiits.fundally.faculty.entity.ProjectQuotation;
import in.iiits.fundally.faculty.entity.ProjectResource;
import in.iiits.fundally.faculty.entity.ProjectResourceDocument;
import in.iiits.fundally.faculty.entity.ProjectRequest;
import in.iiits.fundally.faculty.entity.ProjectRequestStage;
import in.iiits.fundally.faculty.entity.ProjectTransaction;

public class DisplayProjectRequestProfile {

	private ProjectResource projectResource;
	
	private ProjectRequest projectRequest;
	
	private ProjectTransaction projectTransaction;
	
	private List<ProjectRequestStage> projectRequestStageList;
	
	private List<ProjectQuotation> projectQuotationList;
	
	private List<ProjectResourceDocument> projectResourceDocumentList;


	public ProjectResource getProjectResource() {
		return projectResource;
	}


	public void setProjectResource(ProjectResource projectResource) {
		this.projectResource = projectResource;
	}


	public ProjectRequest getProjectRequest() {
		return projectRequest;
	}


	public void setProjectRequest(ProjectRequest projectRequest) {
		this.projectRequest = projectRequest;
	}


	public ProjectTransaction getProjectTransaction() {
		return projectTransaction;
	}


	public void setProjectTransaction(ProjectTransaction projectTransaction) {
		this.projectTransaction = projectTransaction;
	}


	public List<ProjectQuotation> getProjectQuotationList() {
		return projectQuotationList;
	}


	public void setProjectQuotationList(List<ProjectQuotation> projectQuotationList) {
		this.projectQuotationList = projectQuotationList;
	}


	public List<ProjectResourceDocument> getProjectResourceDocumentList() {
		return projectResourceDocumentList;
	}


	public void setProjectResourceDocumentList(List<ProjectResourceDocument> projectResourceDocumentList) {
		this.projectResourceDocumentList = projectResourceDocumentList;
	}

	public List<ProjectRequestStage> getProjectRequestStageList() {
		return projectRequestStageList;
	}


	public void setProjectRequestStageList(List<ProjectRequestStage> projectRequestStageList) {
		this.projectRequestStageList = projectRequestStageList;
	}


	@Override
	public String toString() {
		return "DisplayProjectRequestProfile [projectResource=" + projectResource + ", projectRequest="
				+ projectRequest + ", projectTransaction=" + projectTransaction
				+ ", projectRequestStageList=" + projectRequestStageList
				+ ", projectQuotationList=" + projectQuotationList + ", projectResourceDocumentList="
				+ projectResourceDocumentList + "]";
	}

}
