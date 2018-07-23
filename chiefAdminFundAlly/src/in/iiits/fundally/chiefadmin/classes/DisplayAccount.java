package in.iiits.fundally.chiefadmin.classes;

public class DisplayAccount {

	// account no
	private String accountNo;
	
	// Balance
	private String balance;
	
	// Account type
	private String accountType;

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

	public String getAccountType() {
		return accountType;
	}

	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}

	@Override
	public String toString() {
		return "DisplayInstituteAccount [accountNo=" + accountNo + ", balance=" + balance + ", accountType="
				+ accountType + "]";
	}
}
