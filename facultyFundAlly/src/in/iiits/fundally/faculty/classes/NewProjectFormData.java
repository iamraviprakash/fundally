package in.iiits.fundally.faculty.classes;

public class NewProjectFormData {

	//name
	//description
	//closingDate
	private String name;
	
	private String description;
	
	private String closingDate;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getClosingDate() {
		return closingDate;
	}

	public void setClosingDate(String closingDate) {
		this.closingDate = closingDate;
	}

	@Override
	public String toString() {
		return "NewProjectForm [name=" + name + ", description=" + description + ", closingDate=" + closingDate + "]";
	}
}
