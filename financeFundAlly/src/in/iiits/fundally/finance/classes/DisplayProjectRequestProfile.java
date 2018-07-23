package in.iiits.fundally.finance.classes;

import java.util.List;

import in.iiits.fundally.finance.entity.Project;
import in.iiits.fundally.finance.entity.ProjectAccount;
import in.iiits.fundally.finance.entity.ProjectQuotation;
import in.iiits.fundally.finance.entity.ProjectRequest;
import in.iiits.fundally.finance.entity.ProjectRequestStage;
import in.iiits.fundally.finance.entity.ProjectResource;
import in.iiits.fundally.finance.entity.ProjectResourceDocument;
import in.iiits.fundally.finance.entity.ProjectTransaction;
import in.iiits.fundally.finance.entity.User;

public class DisplayProjectRequestProfile {

	private Project project;
	
	private ProjectAccount projectAccount;
	
	private User user;
	
	private ProjectResource projectResource;
	
	private ProjectRequest projectRequest;
	
	private ProjectTransaction projectTransaction;
	
	private List<ProjectRequestStage> projectRequestStageList;
	
	private List<ProjectQuotation> projectQuotationList;
	
	private List<ProjectResourceDocument> projectResourceDocumentList;

	
	
	
	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

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

	public List<ProjectRequestStage> getProjectRequestStageList() {
		return projectRequestStageList;
	}



	public void setProjectRequestStageList(List<ProjectRequestStage> projectRequestStageList) {
		this.projectRequestStageList = projectRequestStageList;
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


	public ProjectAccount getProjectAccount() {
		return projectAccount;
	}


	public void setProjectAccount(ProjectAccount projectAccount) {
		this.projectAccount = projectAccount;
	}


	public User getUser() {
		return user;
	}


	public void setUser(User user) {
		this.user = user;
	}



	@Override
	public String toString() {
		return "DisplayProjectRequestProfile [project=" + project + ", projectAccount=" + projectAccount + ", user="
				+ user + ", projectResource=" + projectResource + ", projectRequest=" + projectRequest
				+ ", projectTransaction=" + projectTransaction + ", projectRequestStageList=" + projectRequestStageList
				+ ", projectQuotationList=" + projectQuotationList + ", projectResourceDocumentList="
				+ projectResourceDocumentList + "]";
	}

}
