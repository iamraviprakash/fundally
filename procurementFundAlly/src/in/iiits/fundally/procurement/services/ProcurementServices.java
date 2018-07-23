package in.iiits.fundally.procurement.services;

import java.util.List;

import in.iiits.fundally.procurement.classes.DisplayInstituteRequestProfile;
import in.iiits.fundally.procurement.classes.DisplayProjectRequestProfile;
import in.iiits.fundally.procurement.classes.DisplayRequest;
import in.iiits.fundally.procurement.entity.User;

public interface ProcurementServices {

	
	// common
	
	String checkSessionStatus(String sessionId, String string);

	List<DisplayRequest> getAllDisplayRequestList();
	
	User getUser(String sessionId);
	
	String approveRequest(String source, String requestId);

	String getRequestStageId(String requestId);
	
	String saveResourceDocument(String source, String requestId, String resourceDocumentPath);

	String saveQuotation(String source, String requestId, String quotationPath);

	String getResourceDocumentPath(String source, String documentId);

	String getQuotationPath(String source, String documentId);

	
	// institute
	
	DisplayInstituteRequestProfile getDisplayInstituteRequestProfile(String requestId);


	// project

	DisplayProjectRequestProfile getDisplayProjectRequestProfile(String requestId);

	
	// for static file
	String checkSessionStatus(String sessionId);

}
