package in.iiits.fundally.admin.classes;

public class DisplayProject {

	private String id;
	private String name;
	private String projectIncharge;
	private String sanctionedAmount;
	private String description;
	private String openingDate;
	private String closingDate;
	private String status;
	
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getOpeningDate() {
		return openingDate;
	}
	public void setOpeningDate(String openingDate) {
		this.openingDate = openingDate;
	}
	public String getClosingDate() {
		return closingDate;
	}
	public void setClosingDate(String closingDate) {
		this.closingDate = closingDate;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getProjectIncharge() {
		return projectIncharge;
	}
	public void setProjectIncharge(String projectIncharge) {
		this.projectIncharge = projectIncharge;
	}
	public String getSanctionedAmount() {
		return sanctionedAmount;
	}
	public void setSanctionedAmount(String sanctionedAmount) {
		this.sanctionedAmount = sanctionedAmount;
	}
	@Override
	public String toString() {
		return "DisplayProject [id=" + id + ", name=" + name + ", projectIncharge=" + projectIncharge
				+ ", sanctionedAmount=" + sanctionedAmount + ", description=" + description + ", openingDate="
				+ openingDate + ", closingDate=" + closingDate + ", status=" + status + "]";
	}
}
