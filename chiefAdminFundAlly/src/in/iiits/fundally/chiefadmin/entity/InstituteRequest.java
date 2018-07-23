package in.iiits.fundally.chiefadmin.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="InstituteRequest")
public class InstituteRequest {

	public InstituteRequest() {
		id = UUID.randomUUID().toString().replaceAll("-", "");
		java.util.Date today = new java.util.Date();
		java.sql.Timestamp timestamp = new java.sql.Timestamp(today.getTime());
		startDate = timestamp.toString();
	}
	
	@Id
	@Column(name="Id")
	private String id;
	
	// many to one uni directional from request to resource
	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="ResourceId")
	private InstituteResource resource;
	
	@Column(name="Description")
	private String description;
	
	@Column(name="StartDate")
	private String startDate;
	
	@Column(name="EndDate")
	private String endDate;
	
	//Many to one uni direction from account to RequestStageType
	@ManyToOne
	@JoinColumn(name="StageId")
	private InstituteRequestStageType stageType;

	@Column(name="Status")
	@Enumerated(EnumType.STRING)
	private Status status;

	@ManyToOne
	@JoinColumn(name="AccountNo")
	private InstituteAccount account;
	
	// one to many uni direction from request to request stage
	@OneToMany(mappedBy="id.request", cascade=CascadeType.ALL)
	private List<InstituteRequestStage> stageList;

	// one to many bi direction from request to quotation
	@OneToMany(mappedBy="request", cascade=CascadeType.ALL)
	private List<InstituteQuotation> quotationList;
	
	
	// one to one bi direction jointable from request to link to transaction
	// Reason for bi
	// 1. while creating new Request Transaction will not be present. So shift the fk to child table. (uni)
	// 2. But we want to access the transactions from request
	
	@OneToOne(mappedBy="request")
	private InstituteTransaction transaction;
	

	// add stage
	public void add(InstituteRequestStage stage) {
		
		if(stageList == null) {
			stageList = new ArrayList<InstituteRequestStage>();
		}
		
		stage.setRequest(this);
		
		stageList.add(stage);
	}
	
	
	// add quotation
	public void add(InstituteQuotation quotation) {
		
		if(quotationList == null) {
			quotationList = new ArrayList<InstituteQuotation>();
		}
		// set back this request to quotation
		quotation.setRequest(this);
		
		quotationList.add(quotation);
	}


	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public InstituteResource getResource() {
		return resource;
	}


	public void setResource(InstituteResource resource) {
		this.resource = resource;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	public String getStartDate() {
		return startDate;
	}


	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}


	public String getEndDate() {
		return endDate;
	}


	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}


	public InstituteRequestStageType getStageType() {
		return stageType;
	}


	public void setStageType(InstituteRequestStageType stageType) {
		this.stageType = stageType;
	}


	public Status getStatus() {
		return status;
	}


	public void setStatus(Status status) {
		this.status = status;
	}


	public InstituteAccount getAccount() {
		return account;
	}


	public void setAccount(InstituteAccount account) {
		this.account = account;
	}


	public List<InstituteRequestStage> getStageList() {
		return stageList;
	}


	public void setStageList(List<InstituteRequestStage> stageList) {
		this.stageList = stageList;
	}


	public List<InstituteQuotation> getQuotationList() {
		return quotationList;
	}


	public void setQuotationList(List<InstituteQuotation> quotationList) {
		this.quotationList = quotationList;
	}


	public InstituteTransaction getTransaction() {
		return transaction;
	}


	public void setTransaction(InstituteTransaction transaction) {
		this.transaction = transaction;
	}


	@Override
	public String toString() {
		return "InstituteRequest [id=" + id + ", resource=" + resource + ", description=" + description + ", startDate="
				+ startDate + ", endDate=" + endDate + ", stageType=" + stageType + ", status=" + status + ", account="
				+ account + ", transaction=" + transaction + "]";
	}

}
