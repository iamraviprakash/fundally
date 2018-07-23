package in.iiits.fundally.admin.entity;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;

@Embeddable
public class InstituteRequestStageId implements Serializable {

	@ManyToOne
	private InstituteRequestStageType stageType;
	
	@ManyToOne(cascade=CascadeType.ALL)
	private InstituteRequest request;

	public InstituteRequestStageType getStageType() {
		return stageType;
	}

	public void setStageType(InstituteRequestStageType stageType) {
		this.stageType = stageType;
	}

	public InstituteRequest getRequest() {
		return request;
	}

	public void setRequest(InstituteRequest request) {
		this.request = request;
	}

	@Override
	public String toString() {
		return "InstituteRequestStageId [stageType=" + stageType + ", request=" + request + "]";
	}

}
