package in.iiits.fundally.faculty.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import in.iiits.fundally.faculty.entity.InstituteAccount;
import in.iiits.fundally.faculty.entity.InstituteQuotation;
import in.iiits.fundally.faculty.entity.InstituteRequest;
import in.iiits.fundally.faculty.entity.InstituteRequestStage;
import in.iiits.fundally.faculty.entity.InstituteRequestStageType;
import in.iiits.fundally.faculty.entity.InstituteResource;
import in.iiits.fundally.faculty.entity.Project;
import in.iiits.fundally.faculty.entity.ProjectAccount;
import in.iiits.fundally.faculty.entity.ProjectAccountType;
import in.iiits.fundally.faculty.entity.ProjectQuotation;
import in.iiits.fundally.faculty.entity.ProjectRequest;
import in.iiits.fundally.faculty.entity.ProjectRequestStage;
import in.iiits.fundally.faculty.entity.ProjectResource;
import in.iiits.fundally.faculty.entity.Status;
import in.iiits.fundally.faculty.entity.User;
import in.iiits.fundally.faculty.entity.UserSession;

@Repository
public class FacultyDAOsImpl implements FacultyDAOs {

	// need to inject sessionFactory
	@Autowired
	private SessionFactory facultySessionFactory;

	@Override
	public User getUser(String userId) {
		Session currentSession = facultySessionFactory.getCurrentSession();
		return currentSession.get(User.class, userId);
	}

	@Override
	public InstituteAccount getInstituteAccount(String accountNo) {
		Session currentSession = facultySessionFactory.getCurrentSession();
		return currentSession.get(InstituteAccount.class, accountNo);
	}

	@Override
	public InstituteRequestStageType getStageType(String stageId) {
		Session currentSession = facultySessionFactory.getCurrentSession();
		return currentSession.get(InstituteRequestStageType.class, stageId);
	}

	@Override
	public void saveStage(InstituteRequestStage stage) {
		Session currentSession = facultySessionFactory.getCurrentSession();
		currentSession.save(stage);
		return ;
	}

	@Override
	public String saveAccount(InstituteAccount account) {
		Session currentSession = facultySessionFactory.getCurrentSession();
		return currentSession.save(account).toString();
	}

	@Override
	public UserSession getSession(String sessionId) {
		Session currentSession = facultySessionFactory.getCurrentSession();
		return currentSession.get(UserSession.class, sessionId);
	}

	
	@Override
	public InstituteRequest getRequest(String requestId) {
		Session currentSession = facultySessionFactory.getCurrentSession();
		return currentSession.get(InstituteRequest.class, requestId);
	}

	@Override
	public List<InstituteRequestStage> getRequestStageList(InstituteRequest request) {
		Session currentSession = facultySessionFactory.getCurrentSession();
		Query<InstituteRequestStage> query = currentSession.createQuery("from InstituteRequestStage where id.request = :request", InstituteRequestStage.class);    
		query.setParameter("request", request);
		return query.getResultList();
	}

	@Override
	public List<InstituteRequest> getRequestList(String accountNo) {
		Session currentSession = facultySessionFactory.getCurrentSession();
		Query<InstituteRequest> query = currentSession.createQuery("from InstituteRequest where account.accountNo = :accountNo order by startDate desc", InstituteRequest.class);    
		query.setParameter("accountNo", accountNo);
		return query.getResultList();
	}


	@Override
	public List<InstituteQuotation> getQuotationList(InstituteRequest request) {
		Session currentSession = facultySessionFactory.getCurrentSession();
		Query<InstituteQuotation> query = currentSession.createQuery("from InstituteQuotation where request = :request", InstituteQuotation.class);    
		query.setParameter("request", request);
		return query.getResultList();
	}

	@Override
	public List<InstituteRequest> getPendingInstituteIssueRequestList(InstituteResource resource, InstituteRequestStageType stageType) {
		Session currentSession = facultySessionFactory.getCurrentSession();
		Query<InstituteRequest> query = currentSession.createQuery("from InstituteRequest where resource = :resource and stageType = :stageType and status = :status", InstituteRequest.class);    
		query.setParameter("resource", resource);
		query.setParameter("stageType", stageType);
		query.setParameter("status", Status.PENDING);
		return query.getResultList();
	}

	
	// Project
	
	@Override
	public String saveProjectAccountType(ProjectAccountType accountType) {
		Session currentSession = facultySessionFactory.getCurrentSession();
		currentSession.save(accountType);
		return "Account Type Added Successfully!";
	}

	@Override
	public ProjectAccount getProjectAccount(String accountNo) {
		Session currentSession = facultySessionFactory.getCurrentSession();
		return currentSession.get(ProjectAccount.class, accountNo);
	}

	@Override
	public ProjectRequest getProjectRequest(String requestId) {
		Session currentSession = facultySessionFactory.getCurrentSession();
		return currentSession.get(ProjectRequest.class, requestId);
	}

	@Override
	public List<ProjectRequest> getPendingProjectIssueRequestList(ProjectResource resource, InstituteRequestStageType stageType) {
		Session currentSession = facultySessionFactory.getCurrentSession();
		Query<ProjectRequest> query = currentSession.createQuery("from ProjectRequest where resource = :resource and stageType = :stageType and status = :status", ProjectRequest.class);    
		query.setParameter("resource", resource);
		query.setParameter("stageType", stageType);
		query.setParameter("status", Status.PENDING);
		return query.getResultList();
	}

	@Override
	public List<ProjectRequest> getProjectRequestList(String accountNo) {
		Session currentSession = facultySessionFactory.getCurrentSession();
		Query<ProjectRequest> query = currentSession.createQuery("from ProjectRequest where account.accountNo = :accountNo order by startDate desc", ProjectRequest.class);    
		query.setParameter("accountNo", accountNo);
		return query.getResultList();
	}
	
	@Override
	public List<ProjectQuotation> getProjectQuotationList(ProjectRequest request) {
		Session currentSession = facultySessionFactory.getCurrentSession();
		Query<ProjectQuotation> query = currentSession.createQuery("from ProjectQuotation where request = :request", ProjectQuotation.class);    
		query.setParameter("request", request);
		return query.getResultList();
	}
	
	@Override
	public List<ProjectRequestStage> getProjectRequestStageList(ProjectRequest request) {
		Session currentSession = facultySessionFactory.getCurrentSession();
		Query<ProjectRequestStage> query = currentSession.createQuery("from ProjectRequestStage where id.request = :request", ProjectRequestStage.class);    
		query.setParameter("request", request);
		return query.getResultList();
	}

	@Override
	public Project getProject(String projectId) {
		Session currentSession = facultySessionFactory.getCurrentSession();
		return currentSession.get(Project.class, projectId);
	}

	@Override
	public void saveProject(Project project) {
		Session currentSession = facultySessionFactory.getCurrentSession();
		currentSession.save(project);
	}

	@Override
	public List<ProjectAccountType> getProjectAccountTypeList() {
		Session currentSession = facultySessionFactory.getCurrentSession();
		Query<ProjectAccountType> query = currentSession.createQuery("from ProjectAccountType", ProjectAccountType.class);    
		return query.getResultList();
	}

	@Override
	public User getUserByEmailId(String facultyEmailId) {
		Session currentSession = facultySessionFactory.getCurrentSession();
		Query<User> query = currentSession.createQuery("from User where emailId = :facultyEmailId", User.class);    
		query.setParameter("facultyEmailId", facultyEmailId);
		
		if(!query.getResultList().isEmpty())
			return query.getResultList().get(0);
		else
			return null;
	}

	@Override
	public ProjectAccountType getProjectAccountType(String accountTypeId) {
		Session currentSession = facultySessionFactory.getCurrentSession();
		return currentSession.get(ProjectAccountType.class, accountTypeId);
	}
}
