package in.iiits.fundally.admin.classes;

public class NewUserFormData {

	public NewUserFormData() {
		
	}
	
	private String firstName;
	
	private String lastName;
	
	private String emailId;
	
	private String password;
	
	private String department;
	
	private String PRFABalance;
	
	private String PDFABalance;
	
	private String SGFABalance;
	
	private String FDFABalance;

	
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

	public String getPRFABalance() {
		return PRFABalance;
	}

	public void setPRFABalance(String pRFABalance) {
		PRFABalance = pRFABalance;
	}

	public String getPDFABalance() {
		return PDFABalance;
	}

	public void setPDFABalance(String pDFABalance) {
		PDFABalance = pDFABalance;
	}

	public String getSGFABalance() {
		return SGFABalance;
	}

	public void setSGFABalance(String sGFABalance) {
		SGFABalance = sGFABalance;
	}

	public String getFDFABalance() {
		return FDFABalance;
	}

	public void setFDFABalance(String fDFABalance) {
		FDFABalance = fDFABalance;
	}

	@Override
	public String toString() {
		return "NewUserFormData [firstName=" + firstName + ", lastName=" + lastName + ", emailId=" + emailId
				+ ", password=" + password + ", department=" + department + ", PRFABalance=" + PRFABalance
				+ ", PDFABalance=" + PDFABalance + ", SGFABalance=" + SGFABalance + ", FDFABalance=" + FDFABalance
				+ "]";
	}
}
