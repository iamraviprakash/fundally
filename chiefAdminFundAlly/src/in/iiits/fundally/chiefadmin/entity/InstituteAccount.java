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
@Table(name="InstituteAccount")
public class InstituteAccount {

	public InstituteAccount() {
		accountNo = String.format("%040d", new BigInteger(UUID.randomUUID().toString().replaceAll("-", ""), 16));
		java.util.Date today = new java.util.Date();
		java.sql.Timestamp timestamp = new java.sql.Timestamp(today.getTime());
		openingDate = timestamp.toString();
	}
	
	@Id
	@Column(name="AccountNo")
	private String accountNo;
	
	@ManyToOne
	@JoinColumn(name="UserId")
	private User user;
	
	//Many to one uni direction from account to accountTypeId
	@ManyToOne
	@JoinColumn(name="AccountTypeId")
	private InstituteAccountType accountType;
	
	@Column(name="Balance")
	private String balance;

	@Column(name="OpeningDate")
	private String openingDate;
	
	// bi direction from account to request and from request to account
	@OneToMany(mappedBy="account", cascade=CascadeType.ALL)
	List<InstituteRequest> requestList;
	
	
	// bi direction to Transaction from account
	@OneToMany(mappedBy="account", cascade=CascadeType.ALL)
	List<InstituteTransaction> transactionList;
	
	
	// add request
	public void add(InstituteRequest request) {
		
		if(requestList == null) {
			requestList = new ArrayList<InstituteRequest>();
		}
		
		request.setAccount(this);
		
		requestList.add(request);
		
	}
	
	
	// add transaction
	public void add(InstituteTransaction transaction) {
		
		if(transactionList == null) {
			transactionList = new ArrayList<InstituteTransaction>();
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

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
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

	public List<InstituteRequest> getRequestList() {
		return requestList;
	}


	public void setRequestList(List<InstituteRequest> requestList) {
		this.requestList = requestList;
	}


	public List<InstituteTransaction> getTransactionList() {
		return transactionList;
	}


	public void setTransactionList(List<InstituteTransaction> transactionList) {
		this.transactionList = transactionList;
	}


	public InstituteAccountType getAccountType() {
		return accountType;
	}


	public void setAccountType(InstituteAccountType AccountType) {
		this.accountType = AccountType;
	}


	@Override
	public String toString() {
		return "InstituteAccount [accountNo=" + accountNo + ", user=" + user + ", accountType=" + accountType
				+ ", balance=" + balance + ", openingDate=" + openingDate + "]";
	}

}
