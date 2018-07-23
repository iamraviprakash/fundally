package in.iiits.fundally.finance.classes;

import java.util.List;

import in.iiits.fundally.finance.entity.InstituteAccount;
import in.iiits.fundally.finance.entity.InstituteQuotation;
import in.iiits.fundally.finance.entity.InstituteResource;
import in.iiits.fundally.finance.entity.InstituteResourceDocument;
import in.iiits.fundally.finance.entity.InstituteRequest;
import in.iiits.fundally.finance.entity.InstituteRequestStage;
import in.iiits.fundally.finance.entity.InstituteTransaction;
import in.iiits.fundally.finance.entity.User;

public class DisplayInstituteRequestProfile {

	private InstituteAccount instituteAccount;
	
	private User user;
	
	private InstituteResource instituteResource;
	
	private InstituteRequest instituteRequest;
	
	private InstituteTransaction instituteTransaction;
	
	private List<InstituteRequestStage> instituteRequestStageList;
	
	private List<InstituteQuotation> instituteQuotationList;
	
	private List<InstituteResourceDocument> instituteResourceDocumentList;

	
	public InstituteResource getInstituteResource() {
		return instituteResource;
	}


	public void setInstituteResource(InstituteResource instituteResource) {
		this.instituteResource = instituteResource;
	}


	public InstituteRequest getInstituteRequest() {
		return instituteRequest;
	}


	public void setInstituteRequest(InstituteRequest instituteRequest) {
		this.instituteRequest = instituteRequest;
	}


	public InstituteTransaction getInstituteTransaction() {
		return instituteTransaction;
	}


	public void setInstituteTransaction(InstituteTransaction instituteTransaction) {
		this.instituteTransaction = instituteTransaction;
	}

	public List<InstituteRequestStage> getInstituteRequestStageList() {
		return instituteRequestStageList;
	}


	public void setInstituteRequestStageList(List<InstituteRequestStage> instituteRequestStageList) {
		this.instituteRequestStageList = instituteRequestStageList;
	}


	public List<InstituteQuotation> getInstituteQuotationList() {
		return instituteQuotationList;
	}


	public void setInstituteQuotationList(List<InstituteQuotation> instituteQuotationList) {
		this.instituteQuotationList = instituteQuotationList;
	}


	public List<InstituteResourceDocument> getInstituteResourceDocumentList() {
		return instituteResourceDocumentList;
	}


	public void setInstituteResourceDocumentList(List<InstituteResourceDocument> instituteResourceDocumentList) {
		this.instituteResourceDocumentList = instituteResourceDocumentList;
	}


	public InstituteAccount getInstituteAccount() {
		return instituteAccount;
	}


	public void setInstituteAccount(InstituteAccount instituteAccount) {
		this.instituteAccount = instituteAccount;
	}


	public User getUser() {
		return user;
	}


	public void setUser(User user) {
		this.user = user;
	}


	@Override
	public String toString() {
		return "DisplayRequestProfile [instituteAccount=" + instituteAccount + ", user=" + user + ", instituteResource="
				+ instituteResource + ", instituteRequest=" + instituteRequest + ", instituteTransaction="
				+ instituteTransaction + ", instituteRequestStageList=" + instituteRequestStageList
				+ ", instituteQuotationList=" + instituteQuotationList + ", instituteResourceDocumentList="
				+ instituteResourceDocumentList + "]";
	}

}
