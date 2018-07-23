package in.iiits.fundally.faculty.services;

import java.util.List;

import org.springframework.stereotype.Service;

import in.iiits.fundally.faculty.classes.DisplayAccount;
import in.iiits.fundally.faculty.classes.DisplayInstituteRequestProfile;
import in.iiits.fundally.faculty.classes.DisplayProject;
import in.iiits.fundally.faculty.classes.DisplayProjectRequestProfile;
import in.iiits.fundally.faculty.classes.DisplayRequest;
import in.iiits.fundally.faculty.classes.DisplayTransaction;
import in.iiits.fundally.faculty.classes.NewProjectAccountForm;
import in.iiits.fundally.faculty.classes.NewProjectFormData;
import in.iiits.fundally.faculty.classes.NewResourceRequestFormData;
import in.iiits.fundally.faculty.entity.ProjectAccountType;
import in.iiits.fundally.faculty.entity.User;

@Service
public interface FacultyServices {

	
	// Common services
	
	String checkSessionStatus(String sessionId, String userType);

	User getUser(String sessionId);

	
	
	// Institute Part
	
	List<DisplayAccount> getInstituteAccountList(String userId);
	
	DisplayInstituteRequestProfile getRequestProfile(String requestId);
	
	String addInstituteRequest(NewResourceRequestFormData newResourceRequestFormData);

	List<DisplayRequest> getRequestList(String accountNo);

	String createInstituteResourceIssueRequest(String requestId);

	List<DisplayTransaction> getInstituteTransactionList(String accountNo);

	String getRequestStageId(String requestId);

	
	// Project Part
	
	List<DisplayProject> getProjectList(String userId);

	String addProject(String userId, NewProjectFormData newProjectFormData);

	String addProjectAccountType(ProjectAccountType accountType);

	String refillAccount(String emailId, String accountNo, String amount);

	String addProjectRequest(String userId, NewResourceRequestFormData newResourceRequestFormData);

	String createProjectResourceIssueRequest(String userId, String requestId);

	List<DisplayRequest> getProjectRequestList(String accountNo);

	DisplayProjectRequestProfile getProjectRequestProfile(String requestId);

	List<DisplayTransaction> getProjectTransactionList(String accountNo);

	DisplayProject getDisplayProject(String projectId);

	List<DisplayAccount> getProjectAccountList(String projectId);

	String addProjectAccount(String projectId, NewProjectAccountForm newProjectAccountForm);

	List<ProjectAccountType> getProjectAccountTypeList();

	String addFacultyToProject(String projectId, String facultyEmailId);

	List<User> getProjectFacultyList(String projectId);

}
