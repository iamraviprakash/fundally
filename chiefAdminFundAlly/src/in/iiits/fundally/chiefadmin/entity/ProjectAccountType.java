package in.iiits.fundally.chiefadmin.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;

@Entity
@Table(name="ProjectAccountType")
public class ProjectAccountType {
	
	@Id
	@Column(name="Id")
	private String id;
	
	@Column(name="Name")
	private String name;

	@Column(name="Description")
	private String description;
	
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

	@Override
	public String toString() {
		return "ProjectAccountType [id=" + id + ", name=" + name + ", description=" + description + "]";
	}

}
