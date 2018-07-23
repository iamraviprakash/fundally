package in.iiits.fundally.chiefadmin.services;

import java.util.List;

import in.iiits.fundally.chiefadmin.classes.DisplayAccount;
import in.iiits.fundally.chiefadmin.classes.DisplayInstituteRequestProfile;
import in.iiits.fundally.chiefadmin.classes.DisplayProject;
import in.iiits.fundally.chiefadmin.classes.DisplayProjectRequestProfile;
import in.iiits.fundally.chiefadmin.classes.DisplayRequest;
import in.iiits.fundally.chiefadmin.classes.DisplayTransaction;
import in.iiits.fundally.chiefadmin.entity.User;

public interface ChiefAdminServices {

	// Common
	
	String checkSessionStatus(String sessionId, String string);

	List<DisplayRequest> getAllDisplayRequestList();
	
	String getRequestStageId(String requestId);
	
	String approveRequest(String source, String requestId);
	
	User getUser(String sessionId);

	List<User> getFacultyListByDepartment(String department);
	
	String approveQuotation(String source, String requestId, String quotationId);
	
	
	// Institute
	
	DisplayInstituteRequestProfile getDisplayInstituteRequestProfile(String requestId);
	
	User getFaculty(String userId);

	List<DisplayAccount> getInstituteDisplayAccountListByUser(String userId);

	List<DisplayRequest> getInstituteDisplayRequestListByAccount(String accountNo);

	List<DisplayTransaction> getInstituteDisplayTransactionList(String accountNo);
	
	
	// Project

	DisplayProjectRequestProfile getDisplayProjectRequestProfile(String requestId);

	List<DisplayProject> getDisplayProjectListByUser(String id);

	DisplayProject getDisplayProject(String projectId);

	List<DisplayAccount> getProjectDisplayAccountList(String projectId);

	List<User> getProjectFacultyList(String projectId);

	List<DisplayTransaction> getProjectDisplayTransactionList(String accountNo);

	List<DisplayRequest> getProjectDisplayRequestListByAccount(String accountNo);

	

}
