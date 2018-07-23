package in.iiits.fundally.accounts.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;


@Entity
@Table(name="User")
public class User {

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
	
	@OneToMany(mappedBy="user", cascade=CascadeType.ALL)
	List<UserSession> userSessionList;
	
	// add user session
	public void add(UserSession userSession) {
		
		if(userSessionList == null)
			userSessionList = new ArrayList<UserSession>();
		
		userSession.setUser(this);
		
		userSessionList.add(userSession);
			
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

	public List<UserSession> getUserSessionList() {
		return userSessionList;
	}

	public void setUserSessionList(List<UserSession> userSessionList) {
		this.userSessionList = userSessionList;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", emailId=" + emailId
				+ ", password=" + password + ", department=" + department + ", type=" + type + "]";
	}
}
