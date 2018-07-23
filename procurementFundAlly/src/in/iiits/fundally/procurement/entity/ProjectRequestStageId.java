package in.iiits.fundally.procurement.entity;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;

@Embeddable
public class ProjectRequestStageId implements Serializable {

	
	@ManyToOne
	private InstituteRequestStageType stageType;
	
	@ManyToOne(cascade=CascadeType.ALL)
	private ProjectRequest request;

	public InstituteRequestStageType getStageType() {
		return stageType;
	}

	public void setStageType(InstituteRequestStageType stageType) {
		this.stageType = stageType;
	}

	public ProjectRequest getRequest() {
		return request;
	}

	public void setRequest(ProjectRequest request) {
		this.request = request;
	}

	@Override
	public String toString() {
		return "ProjectRequestStageId [stageType=" + stageType + ", request=" + request + "]";
	}

}
