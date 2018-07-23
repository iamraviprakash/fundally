package in.iiits.fundally.admin.entity;

import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;


@Entity
@Table(name="ProjectTransaction")
public class ProjectTransaction {

	public ProjectTransaction() {
		id = UUID.randomUUID().toString().replaceAll("-", "");
		java.util.Date today = new java.util.Date();
		java.sql.Timestamp timestamp = new java.sql.Timestamp(today.getTime());
		date = timestamp.toString();
	}
	
	@Id
	@Column(name="Id")
	private String id;
	
	@ManyToOne
	@JoinColumn(name="AccountNo")
	private ProjectAccount account;
	
	@Column(name="OpeningBalance")
	private String openingBalance;
	
	@Column(name="ClosingBalance")
	private String closingBalance;
	
	@Column(name="Date")
	private String date;
	
	@Column(name="Type")
	@Enumerated(EnumType.STRING)
	private TransactionType type;
	
	// one to one uni direction jointable from request to link to transaction
	@OneToOne(cascade=CascadeType.ALL)
	@JoinTable(name="ProjectRequestTransactionLink",
			joinColumns=@JoinColumn(name="TransactionId"),
			inverseJoinColumns=@JoinColumn(name="RequestId")
			)
	private ProjectRequest request;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public ProjectAccount getAccount() {
		return account;
	}

	public void setAccount(ProjectAccount account) {
		this.account = account;
	}

	public String getOpeningBalance() {
		return openingBalance;
	}

	public void setOpeningBalance(String openingBalance) {
		this.openingBalance = openingBalance;
	}

	public String getClosingBalance() {
		return closingBalance;
	}

	public void setClosingBalance(String closingBalance) {
		this.closingBalance = closingBalance;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public TransactionType getType() {
		return type;
	}

	public void setType(TransactionType type) {
		this.type = type;
	}

	public ProjectRequest getRequest() {
		return request;
	}

	public void setRequest(ProjectRequest request) {
		this.request = request;
	}

	@Override
	public String toString() {
		return "ProjectTransaction [id=" + id + ", openingBalance=" + openingBalance + ", closingBalance="
				+ closingBalance + ", date=" + date + ", type=" + type + "]";
	}
}
