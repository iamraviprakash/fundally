package in.iiits.fundally.chiefadmin.entity;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="ProjectResourceDocument")
public class ProjectResourceDocument {

	public ProjectResourceDocument() {
		id = UUID.randomUUID().toString().replaceAll("-", "");
		java.util.Date today = new java.util.Date();
		java.sql.Timestamp timestamp = new java.sql.Timestamp(today.getTime());
		date = timestamp.toString();
	}
	
	@Id
	@Column(name="Id")
	private String id;
	
	@Column(name="Date")
	private String date;
	
	@Column(name="Description")
	private String description;
	
	@Column(name="Path")
	private String path;

	@ManyToOne
	@JoinColumn(name="ResourceId")
	private ProjectResource resource;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public ProjectResource getResource() {
		return resource;
	}

	public void setResource(ProjectResource resource) {
		this.resource = resource;
	}

	@Override
	public String toString() {
		return "ProjectResourceDocument [id=" + id + ", date=" + date + ", description=" + description + ", path="
				+ path + "]";
	}
}
