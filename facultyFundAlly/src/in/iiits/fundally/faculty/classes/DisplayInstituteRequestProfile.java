package in.iiits.fundally.faculty.classes;

import java.util.List;

import in.iiits.fundally.faculty.entity.InstituteQuotation;
import in.iiits.fundally.faculty.entity.InstituteResource;
import in.iiits.fundally.faculty.entity.InstituteResourceDocument;
import in.iiits.fundally.faculty.entity.InstituteRequest;
import in.iiits.fundally.faculty.entity.InstituteRequestStage;
import in.iiits.fundally.faculty.entity.InstituteTransaction;

public class DisplayInstituteRequestProfile {

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

	public List<InstituteRequestStage> getInstituteRequestStageList() {
		return instituteRequestStageList;
	}


	public void setInstituteRequestStageList(List<InstituteRequestStage> instituteRequestStageList) {
		this.instituteRequestStageList = instituteRequestStageList;
	}


	@Override
	public String toString() {
		return "DisplayInstituteRequestProfile [instituteResource=" + instituteResource + ", instituteRequest="
				+ instituteRequest + ", instituteTransaction=" + instituteTransaction
				+ ", instituteRequestStageList=" + instituteRequestStageList
				+ ", instituteQuotationList=" + instituteQuotationList + ", instituteResourceDocumentList="
				+ instituteResourceDocumentList + "]";
	}

}
