package in.iiits.fundally.chiefadmin.entity;

import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="InstituteQuotation")
public class InstituteQuotation {
	
	public InstituteQuotation() {
		id = UUID.randomUUID().toString().replaceAll("-", "");
		java.util.Date today = new java.util.Date();
		java.sql.Timestamp timestamp = new java.sql.Timestamp(today.getTime());
		date = timestamp.toString();
	}
	
	@Id
	@Column(name="Id")
	private String id;
	
	@Column(name="Path")
	private String path;
	
	@Column(name="Status")
	@Enumerated(EnumType.STRING)
	private Status status;
	
	@Column(name="Date")
	private String date;

	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="RequestId")
	InstituteRequest request;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public InstituteRequest getRequest() {
		return request;
	}

	public void setRequest(InstituteRequest request) {
		this.request = request;
	}

	@Override
	public String toString() {
		return "InstituteQuotation [id=" + id + ", path=" + path + ", status=" + status + ", date=" + date + "]";
	}
}
