package in.iiits.fundally.procurement.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="InstituteRequestStageType")
public class InstituteRequestStageType {

	@Id
	@Column(name="Id")
	private String id;
	
	@Column(name="Name")
	private String name;
	
	@Column(name="Description")
	private String description;

	// one to many bi direction from request to request stage
	@OneToMany(cascade=CascadeType.ALL, mappedBy="id.stageType")
	List<InstituteRequestStage> stageList;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<InstituteRequestStage> getStageList() {
		return stageList;
	}

	public void setStageList(List<InstituteRequestStage> stageList) {
		this.stageList = stageList;
	}

	@Override
	public String toString() {
		return "InstituteRequestStageType [id=" + id + ", name=" + name + ", description=" + description + "]";
	}
	
}
