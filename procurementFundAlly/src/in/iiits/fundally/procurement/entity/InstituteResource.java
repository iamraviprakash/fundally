package in.iiits.fundally.procurement.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="InstituteResource")
public class InstituteResource {

	public InstituteResource() {
		id = UUID.randomUUID().toString().replaceAll("-", "");
	}
	
	@Id
	@Column(name="Id")
	private String id;
	
	@Column(name="Name")
	private String name;
	
	@Column(name="Description")
	private String description;
	
	@Column(name="Type")
	@Enumerated(EnumType.STRING)
	private ResourceType type;
	
	@Column(name="Amount")
	private String amount;
	
	@Column(name="DeadlineDate")
	private String deadlineDate;

	// one to many bidirectional From resource to document from document to Resource
	@OneToMany(fetch=FetchType.EAGER, mappedBy="resource", cascade=CascadeType.ALL)
	List<InstituteResourceDocument> resourceDocumentList;
	
	// add document 
	public void add(InstituteResourceDocument resourceDocument) {
		
		if(resourceDocumentList == null) {
			resourceDocumentList = new ArrayList<InstituteResourceDocument>();
		}
		
		resourceDocument.setResource(this);
		
		resourceDocumentList.add(resourceDocument);
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public ResourceType getType() {
		return type;
	}

	public void setType(ResourceType type) {
		this.type = type;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getDeadlineDate() {
		return deadlineDate;
	}

	public void setDeadlineDate(String deadlineDate) {
		this.deadlineDate = deadlineDate;
	}

	public List<InstituteResourceDocument> getResourceDocumentList() {
		return resourceDocumentList;
	}

	public void setResourceDocumentList(List<InstituteResourceDocument> resourceDocumentList) {
		this.resourceDocumentList = resourceDocumentList;
	}

	@Override
	public String toString() {
		return "InstituteResource [id=" + id + ", name=" + name + ", description=" + description + ", type=" + type
				+ ", amount=" + amount + ", deadlineDate=" + deadlineDate + ", resourceDocumentList="
				+ resourceDocumentList + "]";
	}

}
