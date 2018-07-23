package in.iiits.fundally.chiefadmin.entity;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="ProjectAccount")
public class ProjectAccount {

	public ProjectAccount() {
		accountNo = String.format("%040d", new BigInteger(UUID.randomUUID().toString().replaceAll("-", ""), 16));
		java.util.Date today = new java.util.Date();
		java.sql.Timestamp timestamp = new java.sql.Timestamp(today.getTime());
		openingDate = timestamp.toString();
	}
	
	@Id
	@Column(name="AccountNo")
	private String accountNo;
	
	@ManyToOne
	@JoinColumn(name="ProjectId")
	private Project project;
	
	//Many to one uni direction from account to accountTypeId
	@ManyToOne
	@JoinColumn(name="AccountTypeId")
	private ProjectAccountType accountType;
	
	@Column(name="Balance")
	private String balance;

	@Column(name="OpeningDate")
	private String openingDate;
	
	// bi direction from account to request and from request to account
	@OneToMany(mappedBy="account", cascade=CascadeType.ALL)
	List<ProjectRequest> requestList;
	
	
	// bi direction to Transaction from account
	@OneToMany(mappedBy="account", cascade=CascadeType.ALL)
	List<ProjectTransaction> transactionList;
	
	
	// add request
	public void add(ProjectRequest request) {
		
		if(requestList == null) {
			requestList = new ArrayList<ProjectRequest>();
		}
		
		request.setAccount(this);
		
		requestList.add(request);
		
	}
	
	// add transaction
	public void add(ProjectTransaction transaction) {
		
		if(transactionList == null) {
			transactionList = new ArrayList<ProjectTransaction>();
		}
		
		transaction.setAccount(this);
		
		transactionList.add(transaction);
	}
	
	
	public String getAccountNo() {
		return accountNo;
	}

	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}

	public String getBalance() {
		return balance;
	}

	public void setBalance(String balance) {
		this.balance = balance;
	}

	public String getOpeningDate() {
		return openingDate;
	}

	public void setOpeningDate(String openingDate) {
		this.openingDate = openingDate;
	}

	public List<ProjectRequest> getRequestList() {
		return requestList;
	}


	public void setRequestList(List<ProjectRequest> requestList) {
		this.requestList = requestList;
	}


	public List<ProjectTransaction> getTransactionList() {
		return transactionList;
	}


	public void setTransactionList(List<ProjectTransaction> transactionList) {
		this.transactionList = transactionList;
	}


	public ProjectAccountType getAccountType() {
		return accountType;
	}


	public void setAccountType(ProjectAccountType accountType) {
		this.accountType = accountType;
	}

	public Project getProject() {
		return project;
	}


	public void setProject(Project project) {
		this.project = project;
	}


	@Override
	public String toString() {
		return "ProjectAccount [accountNo=" + accountNo + ", accountType=" + accountType + ", balance=" + balance
				+ ", openingDate=" + openingDate + "]";
	}

}
