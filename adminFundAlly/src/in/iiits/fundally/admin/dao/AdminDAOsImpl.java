package in.iiits.fundally.admin.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import in.iiits.fundally.admin.entity.InstituteAccount;
import in.iiits.fundally.admin.entity.InstituteAccountType;
import in.iiits.fundally.admin.entity.InstituteQuotation;
import in.iiits.fundally.admin.entity.InstituteRequest;
import in.iiits.fundally.admin.entity.InstituteRequestStage;
import in.iiits.fundally.admin.entity.InstituteRequestStageType;
import in.iiits.fundally.admin.entity.Project;
import in.iiits.fundally.admin.entity.ProjectAccount;
import in.iiits.fundally.admin.entity.ProjectQuotation;
import in.iiits.fundally.admin.entity.ProjectRequest;
import in.iiits.fundally.admin.entity.ProjectRequestStage;
import in.iiits.fundally.admin.entity.User;
import in.iiits.fundally.admin.entity.UserSession;

@Repository
public class AdminDAOsImpl implements AdminDAOs {

	// need the inject session factory
	@Autowired
	private SessionFactory adminSessionFactory;

	@Override
	public UserSession getSession(String sessionId) {
		Session currentSession = adminSessionFactory.getCurrentSession();
		return currentSession.get(UserSession.class, sessionId);
	}

	@Override
	public User getUser(String userId) {
		Session currentSession = adminSessionFactory.getCurrentSession();
		return currentSession.get(User.class, userId);
	}
	
	@Override
	public List<User> getFacultyListByDepartment(String department) {
		// get current session
		Session currentSession = adminSessionFactory.getCurrentSession();
		
		// Now get the user of the newly added user
		Query<User> query = currentSession.createQuery("from User where type='Faculty' and department='"+department+"' order by firstName", User.class);
		
		return query.getResultList();
	}

	@Override
	public void saveUser(User user) {
		// get current session
		Session currentSession = adminSessionFactory.getCurrentSession();
		// save the user
		currentSession.save(user);
		return;
	}

	@Override
	public InstituteAccountType getInstituteAccountType(String accountTypeId) {
		Session currentSession = adminSessionFactory.getCurrentSession();
		return currentSession.get(InstituteAccountType.class, accountTypeId);
	}
	
	@Override
	public List<InstituteRequest> getInstituteRequestListByStageType(InstituteRequestStageType stageType) {
		Session currentSession = adminSessionFactory.getCurrentSession();
		Query<InstituteRequest> query = currentSession.createQuery("from InstituteRequest where stageType = :stageType", InstituteRequest.class);
		query.setParameter("stageType", stageType);
		return query.getResultList();
	}

	@Override
	public InstituteRequestStageType getStageType(String stageId) {
		Session currentSession = adminSessionFactory.getCurrentSession();
		return currentSession.get(InstituteRequestStageType.class, stageId);
	}

	@Override
	public InstituteRequest getInstituteRequest(String requestId) {
		Session currentSession = adminSessionFactory.getCurrentSession();
		return currentSession.get(InstituteRequest.class, requestId);
	}

	@Override
	public List<InstituteQuotation> getInstituteQuotationList(InstituteRequest request) {
		Session currentSession = adminSessionFactory.getCurrentSession();
		Query<InstituteQuotation> query = currentSession.createQuery("from InstituteQuotation where request = :request", InstituteQuotation.class);    
		query.setParameter("request", request);
		return query.getResultList();
	}

	@Override
	public List<InstituteRequestStage> getInstituteRequestStageList(InstituteRequest request) {
		Session currentSession = adminSessionFactory.getCurrentSession();
		Query<InstituteRequestStage> query = currentSession.createQuery("from InstituteRequestStage where id.request = :request", InstituteRequestStage.class);    
		query.setParameter("request", request);
		return query.getResultList();
	}

	@Override
	public InstituteAccount getInstituteAccount(String accountNo) {
		Session currentSession = adminSessionFactory.getCurrentSession();
		return currentSession.get(InstituteAccount.class, accountNo);
	}
	
	@Override
	public List<InstituteRequest> getInstituteRequestListByAccount(String accountNo) {
		Session currentSession = adminSessionFactory.getCurrentSession();
		Query<InstituteRequest> query = currentSession.createQuery("from InstituteRequest where account.accountNo = :accountNo order by startDate desc", InstituteRequest.class);    
		query.setParameter("accountNo", accountNo);
		return query.getResultList();
	}
	
	// project
	
	@Override
	public List<ProjectRequest> getProjectRequestListByStageType(InstituteRequestStageType stageType) {
		Session currentSession = adminSessionFactory.getCurrentSession();
		Query<ProjectRequest> query = currentSession.createQuery("from ProjectRequest where stageType = :stageType", ProjectRequest.class);
		query.setParameter("stageType", stageType);
		return query.getResultList();
	}
	
	@Override
	public ProjectRequest getProjectRequest(String requestId) {
		Session currentSession = adminSessionFactory.getCurrentSession();
		return currentSession.get(ProjectRequest.class, requestId);
	}
	
	@Override
	public List<ProjectQuotation> getProjectQuotationList(ProjectRequest request) {
		Session currentSession = adminSessionFactory.getCurrentSession();
		Query<ProjectQuotation> query = currentSession.createQuery("from ProjectQuotation where request = :request", ProjectQuotation.class);    
		query.setParameter("request", request);
		return query.getResultList();
	}

	@Override
	public List<ProjectRequestStage> getProjectRequestStageList(ProjectRequest request) {
		Session currentSession = adminSessionFactory.getCurrentSession();
		Query<ProjectRequestStage> query = currentSession.createQuery("from ProjectRequestStage where id.request = :request", ProjectRequestStage.class);    
		query.setParameter("request", request);
		return query.getResultList();
	}

	@Override
	public Project getProject(String projectId) {
		Session currentSession = adminSessionFactory.getCurrentSession();
		return currentSession.get(Project.class, projectId);
	}
	
	@Override
	public List<ProjectRequest> getProjectRequestListByAccount(String accountNo) {
		Session currentSession = adminSessionFactory.getCurrentSession();
		Query<ProjectRequest> query = currentSession.createQuery("from ProjectRequest where account.accountNo = :accountNo order by startDate desc", ProjectRequest.class);    
		query.setParameter("accountNo", accountNo);
		return query.getResultList();
	}
	
	@Override
	public ProjectAccount getProjectAccount(String accountNo) {
		Session currentSession = adminSessionFactory.getCurrentSession();
		return currentSession.get(ProjectAccount.class, accountNo);
	}
}
