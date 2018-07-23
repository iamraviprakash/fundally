package in.iiits.fundally.faculty.classes;

public class NewResourceRequestFormData {

	public NewResourceRequestFormData(String accountNo) {
		
		this.accountNo = accountNo;
	}
	private String name;
	
	private String accountNo;
	
	private String description;
	
	private String type;
	
	private String amount;
	
	private String reason;
	
	private String deadline;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAccountNo() {
		return accountNo;
	}

	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getDeadline() {
		return deadline;
	}

	public void setDeadline(String deadline) {
		this.deadline = deadline;
	}

	@Override
	public String toString() {
		return "NewInstituteResourceRequestFormData [name=" + name + ", accountNo=" + accountNo + ", description="
				+ description + ", type=" + type + ", amount=" + amount + ", reason=" + reason + ", deadline="
				+ deadline + "]";
	}
}
