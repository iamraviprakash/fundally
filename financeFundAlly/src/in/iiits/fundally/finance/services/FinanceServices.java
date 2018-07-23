package in.iiits.fundally.finance.services;

import java.util.List;

import in.iiits.fundally.finance.classes.DisplayInstituteRequestProfile;
import in.iiits.fundally.finance.classes.DisplayProjectRequestProfile;
import in.iiits.fundally.finance.classes.DisplayRequest;
import in.iiits.fundally.finance.entity.User;

public interface FinanceServices {

	// common
	
	String checkSessionStatus(String sessionId, String string);

	List<DisplayRequest> getAllDisplayRequestList();

	String getRequestStageId(String requestId);

	String payRequest(String source, String requestId);

	User getUser(String sessionId);
	
	
	// institute
	
	DisplayInstituteRequestProfile getDisplayInstituteRequestProfile(String requestId);
	
	
	// project
	
	DisplayProjectRequestProfile getDisplayProjectRequestProfile(String requestId);
	
}
