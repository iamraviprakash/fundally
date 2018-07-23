package in.iiits.fundally.finance.entity;

import javax.persistence.AssociationOverride;
import javax.persistence.AssociationOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.Table;

@Entity
@Table(name="ProjectRequestStage")
@AssociationOverrides({
	@AssociationOverride(name="id.stageType", joinColumns=@JoinColumn(name="StageId")),
	@AssociationOverride(name="id.request", joinColumns=@JoinColumn(name="RequestId"))
})
public class ProjectRequestStage {

	public ProjectRequestStage() {
		java.util.Date today = new java.util.Date();
		java.sql.Timestamp timestamp = new java.sql.Timestamp(today.getTime());
		date = timestamp.toString();
		id = new ProjectRequestStageId();
	}
	
	// composite key
	@EmbeddedId
	private ProjectRequestStageId id;

	
	// extra columns
	@Column(name="Date")
	private String date;
	
	public InstituteRequestStageType getStageType() {
		return getId().getStageType();
	}
	
	public void setStageType(InstituteRequestStageType stageType) {
		getId().setStageType(stageType);
	}
	
	public ProjectRequest getRequest() {
		return getId().getRequest();
	}
	
	public void setRequest(ProjectRequest request) {
		getId().setRequest(request);
	}

	public ProjectRequestStageId getId() {
		return id;
	}

	public void setId(ProjectRequestStageId id) {
		this.id = id;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	@Override
	public String toString() {
		return "ProjectResourceRequestStage [id=" + id + ", date=" + date + "]";
	}

}
