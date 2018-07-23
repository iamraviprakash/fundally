package in.iiits.fundally.admin.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="Project")
public class Project {

	public Project() {
		id = UUID.randomUUID().toString().replaceAll("-", "");
		java.util.Date today = new java.util.Date();
		java.sql.Timestamp timestamp = new java.sql.Timestamp(today.getTime());
		openingDate = timestamp.toString();
	}
	
	@Id
	@Column(name="Id")
	private String id;
	
	@Column(name="Name")
	private String name;
	
	@Column(name="SanctionedAmount")
	private String sanctionedAmount;
	
	// Many to one uni-directional
	@ManyToOne
	@JoinColumn(name="UserId")
	private User projectIncharge;
	
	@Column(name="OpeningDate")
	private String openingDate;
	
	@Column(name="ClosingDate")
	private String closingDate;
	
	@Column(name="Description")
	private String description;
	
	@Column(name="Status")
	@Enumerated(EnumType.STRING)
	private Status status;

	// one to many bidirectional
	@OneToMany(cascade=CascadeType.ALL, mappedBy="project")
	private List<ProjectAccount> projectAccountList;

	// many to many direction jointable from project to link to user
	@ManyToMany(cascade=CascadeType.ALL, mappedBy="projectList")
//	@JoinTable(name="ProjectFacultyLink",
//			joinColumns=@JoinColumn(name="ProjectId"),
//			inverseJoinColumns=@JoinColumn(name="UserId")
//			)
	private List<User> userList;
	
	
	// add account
	public void add(ProjectAccount account) {
		
		if(projectAccountList == null) {
			projectAccountList = new ArrayList<ProjectAccount>();
		}
		
		account.setProject(this);
		
		projectAccountList.add(account);
	}
	
	// add user to project
//	public void add(User user) {
//		
//		if(userList == null) {
//			userList = new ArrayList<User>();
//		}
//		
//		userList.add(user);
//	}

	public User getProjectIncharge() {
		return projectIncharge;
	}

	public void setProjectIncharge(User projectIncharge) {
		this.projectIncharge = projectIncharge;
	}

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

	public List<User> getUserList() {
		return userList;
	}

	public void setUserList(List<User> userList) {
		this.userList = userList;
	}

	public String getOpeningDate() {
		return openingDate;
	}

	public void setOpeningDate(String openingDate) {
		this.openingDate = openingDate;
	}

	public String getClosingDate() {
		return closingDate;
	}

	public void setClosingDate(String closingDate) {
		this.closingDate = closingDate;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public String getSanctionedAmount() {
		return sanctionedAmount;
	}

	public void setSanctionedAmount(String sanctionedAmount) {
		this.sanctionedAmount = sanctionedAmount;
	}

	public List<ProjectAccount> getProjectAccountList() {
		return projectAccountList;
	}

	public void setProjectAccountList(List<ProjectAccount> projectAccountList) {
		this.projectAccountList = projectAccountList;
	}

	@Override
	public String toString() {
		return "Project [id=" + id + ", name=" + name + ", sanctionedAmount=" + sanctionedAmount + ", openingDate="
				+ openingDate + ", closingDate=" + closingDate + ", description=" + description + ", status=" + status
				+ ", projectAccountList=" + projectAccountList + "]";
	}
}
