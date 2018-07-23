package in.iiits.fundally.chiefadmin.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;


@Entity
@Table(name="User")
public class User {

	public User() {
		id = UUID.randomUUID().toString().replaceAll("-", "");
	}
	
	@Id
	@Column(name="Id")
	private String id;
	
	@Column(name="FirstName")
	private String firstName;
	
	@Column(name="LastName")
	private String lastName;
	
	@Column(name="EmailId")
	private String emailId;
	
	@Column(name="Password")
	private String password;
	
	@Column(name="Department")
	private String department;
	
	@Column(name="Type")
	private String type;
	
	// one to many bidirectional
	@OneToMany(cascade=CascadeType.ALL , mappedBy="user")
	private List<InstituteAccount> instituteAccountList;

	@ManyToMany(cascade=CascadeType.ALL)
	@JoinTable(name="ProjectFacultyLink",
			joinColumns=@JoinColumn(name="UserId"),
			inverseJoinColumns=@JoinColumn(name="ProjectId")
			)
	private List<Project> projectList;
		
		
	// add account
	public void add(InstituteAccount account) {
		
		if(instituteAccountList == null) {
			instituteAccountList = new ArrayList<InstituteAccount>();
		}
		
		account.setUser(this);
		
		instituteAccountList.add(account);
	}
	
	// add project to user
	public void add(Project project) {
		
		if(projectList == null) {
			projectList = new ArrayList<Project>();
		}
		
		projectList.add(project);
	}


	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public String getFirstName() {
		return firstName;
	}


	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}


	public String getLastName() {
		return lastName;
	}


	public void setLastName(String lastName) {
		this.lastName = lastName;
	}


	public String getEmailId() {
		return emailId;
	}


	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}


	public String getDepartment() {
		return department;
	}


	public void setDepartment(String department) {
		this.department = department;
	}


	public String getType() {
		return type;
	}


	public void setType(String type) {
		this.type = type;
	}


	public List<InstituteAccount> getInstituteAccountList() {
		return instituteAccountList;
	}


	public void setInstituteAccountList(List<InstituteAccount> instituteAccountList) {
		this.instituteAccountList = instituteAccountList;
	}

	public List<Project> getProjectList() {
		return projectList;
	}


	public void setProjectList(List<Project> projectList) {
		this.projectList = projectList;
	}


	@Override
	public String toString() {
		return "User [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", emailId=" + emailId
				+ ", password=" + password + ", department=" + department + ", type=" + type + "]";
	}

}
