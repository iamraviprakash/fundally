package in.iiits.fundally.admin.services;

import java.util.List;

import in.iiits.fundally.admin.classes.DisplayAccount;
import in.iiits.fundally.admin.classes.DisplayTransaction;
import in.iiits.fundally.admin.classes.DisplayProject;
import in.iiits.fundally.admin.classes.DisplayProjectRequestProfile;
import in.iiits.fundally.admin.classes.DisplayRequest;
import in.iiits.fundally.admin.classes.DisplayInstituteRequestProfile;
import in.iiits.fundally.admin.classes.NewUserFormData;
import in.iiits.fundally.admin.entity.User;

public interface AdminServices {

	// common
	String addUserAndAccount(NewUserFormData newUserFormData);
	
	List<User> getFacultyListByDepartment(String department);
	
	String checkSessionStatus(String sessionId, String string);
	
	List<DisplayRequest> getAllDisplayRequestList();

	String getRequestStageId(String requestId);
	
	String approveRequest(String source, String requestId);
	
	String cancelRequest(String source, String requestId);

	String refillAccount(String accountNo, String amount);

	User getUser(String sessionId);
	
	User getFaculty(String userId);
	
	
	// institute
	
	DisplayInstituteRequestProfile getDisplayInstituteRequestProfile(String requestId);

	List<DisplayAccount> getInstituteDisplayAccountListByUser(String userId);

	List<DisplayRequest> getInstituteDisplayRequestListByAccount(String accountNo);

	List<DisplayTransaction> getInstituteDisplayTransactionList(String accountNo);


	
	// project
	
	DisplayProjectRequestProfile getDisplayProjectRequestProfile(String requestId);

	DisplayProject getDisplayProject(String projectId);

	List<DisplayAccount> getProjectDisplayAccountList(String projectId);

	List<User> getProjectFacultyList(String projectId);

	List<DisplayProject> getDisplayProjectListByUser(String userId);

	List<DisplayRequest> getProjectDisplayRequestListByAccount(String accountNo);

	List<DisplayTransaction> getProjectDisplayTransactionList(String accountNo);

	


}
