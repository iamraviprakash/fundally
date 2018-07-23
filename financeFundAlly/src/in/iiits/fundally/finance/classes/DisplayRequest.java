package in.iiits.fundally.finance.classes;

public class DisplayRequest {

	private String resourceName;
	private String requesterEmailId;
	private String amount;
	private String deadline;
	private String type;
	private String stage;
	private String status;
	private String requestId;
	private String resourceId;
	private String requestSource;
	
	
	public String getRequestSource() {
		return requestSource;
	}
	public void setRequestSource(String requestSource) {
		this.requestSource = requestSource;
	}
	public String getRequesterEmailId() {
		return requesterEmailId;
	}
	public void setRequesterEmailId(String requesterEmailId) {
		this.requesterEmailId = requesterEmailId;
	}
	public String getResourceName() {
		return resourceName;
	}
	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getDeadline() {
		return deadline;
	}
	public void setDeadline(String deadline) {
		this.deadline = deadline;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getStage() {
		return stage;
	}
	public void setStage(String stage) {
		this.stage = stage;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getRequestId() {
		return requestId;
	}
	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}
	public String getResourceId() {
		return resourceId;
	}
	public void setResourceId(String resourceId) {
		this.resourceId = resourceId;
	}
	
	@Override
	public String toString() {
		return "DisplayRequest [resourceName=" + resourceName + ", requesterEmailId=" + requesterEmailId + ", amount="
				+ amount + ", deadline=" + deadline + ", type=" + type + ", stage=" + stage + ", status=" + status
				+ ", requestId=" + requestId + ", resourceId=" + resourceId + ", requestSource=" + requestSource + "]";
	}
}
