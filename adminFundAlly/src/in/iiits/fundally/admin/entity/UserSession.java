package in.iiits.fundally.admin.entity;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="Session")
public class UserSession {
	
	@Id
	@Column(name="Id")
	private String id;
	
	// ManyToOne unidirecional
	@ManyToOne
	@JoinColumn(name="UserId")
	private User user;

	@Column(name="Status")
	@Enumerated(EnumType.STRING)
	private Status status;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "UserSession [id=" + id + ", user=" + user + ", status=" + status + "]";
	}
}
