package in.iiits.fundally.chiefadmin.classes;

public class DisplayTransaction {


	private String transactionId;
	private String resourceName;
	private String openingBalance;
	private String closingBalance;
	private String type;
	private String date;
	
	public String getTransactionId() {
		return transactionId;
	}
	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}
	public String getResourceName() {
		return resourceName;
	}
	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
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
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	
	@Override
	public String toString() {
		return "DisplayInstituteTransaction [transactionId=" + transactionId + ", resourceName=" + resourceName
				+ ", openingBalance=" + openingBalance + ", closingBalance=" + closingBalance + ", type=" + type
				+ ", date=" + date + "]";
	}
}
