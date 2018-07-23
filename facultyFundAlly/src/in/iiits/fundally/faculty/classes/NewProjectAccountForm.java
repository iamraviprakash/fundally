package in.iiits.fundally.faculty.classes;

public class NewProjectAccountForm {

	private String accountTypeId;
	private String balance;
	
	public String getAccountTypeId() {
		return accountTypeId;
	}
	public void setAccountTypeId(String accountTypeId) {
		this.accountTypeId = accountTypeId;
	}
	public String getBalance() {
		return balance;
	}
	public void setBalance(String balance) {
		this.balance = balance;
	}
	
	@Override
	public String toString() {
		return "NewProjectAccountForm [accountTypeId=" + accountTypeId + ", balance=" + balance + "]";
	}
	
}
