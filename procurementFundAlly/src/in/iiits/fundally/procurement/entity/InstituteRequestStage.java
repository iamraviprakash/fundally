package in.iiits.fundally.procurement.entity;

import javax.persistence.AssociationOverride;
import javax.persistence.AssociationOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.Table;

@Entity
@Table(name="InstituteRequestStage")
@AssociationOverrides({
	@AssociationOverride(name="id.stageType", joinColumns=@JoinColumn(name="StageId")),
	@AssociationOverride(name="id.request", joinColumns=@JoinColumn(name="RequestId"))
})
public class InstituteRequestStage {

	public InstituteRequestStage() {
		java.util.Date today = new java.util.Date();
		java.sql.Timestamp timestamp = new java.sql.Timestamp(today.getTime());
		date = timestamp.toString();
		id = new InstituteRequestStageId();
	}
	
	// composite key
	@EmbeddedId
	private InstituteRequestStageId id;

	
	// extra columns
	@Column(name="Date")
	private String date;
	
	public InstituteRequestStageType getStageType() {
		return getId().getStageType();
	}
	
	public void setStageType(InstituteRequestStageType stageType) {
		getId().setStageType(stageType);
	}
	
	public InstituteRequest getRequest() {
		return getId().getRequest();
	}
	
	public void setRequest(InstituteRequest request) {
		getId().setRequest(request);
	}

	public InstituteRequestStageId getId() {
		return id;
	}

	public void setId(InstituteRequestStageId id) {
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
		return "InstituteResourceRequestStage [id=" + id + ", date=" + date + "]";
	}

}
