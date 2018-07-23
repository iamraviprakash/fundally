package in.iiits.fundally.finance.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import in.iiits.fundally.finance.entity.InstituteQuotation;
import in.iiits.fundally.finance.entity.InstituteRequestStageType;
import in.iiits.fundally.finance.entity.ProjectQuotation;
import in.iiits.fundally.finance.entity.ProjectRequest;
import in.iiits.fundally.finance.entity.ProjectRequestStage;
import in.iiits.fundally.finance.entity.InstituteRequest;
import in.iiits.fundally.finance.entity.InstituteRequestStage;
import in.iiits.fundally.finance.entity.UserSession;

@Repository
public class FinanceDAOsImpl implements FinanceDAOs {

	@Autowired
	private SessionFactory financeSessionFactory;
	
	@Override
	public UserSession getSession(String sessionId) {
		Session currentSession = financeSessionFactory.getCurrentSession();
		return currentSession.get(UserSession.class, sessionId);
	}

	@Override
	public List<InstituteRequest> getInstituteRequestListByStageType(InstituteRequestStageType stageType) {
		Session currentSession = financeSessionFactory.getCurrentSession();
		Query<InstituteRequest> query = currentSession.createQuery("from InstituteRequest where stageType = :stageType", InstituteRequest.class);
		query.setParameter("stageType", stageType);
		return query.getResultList();
	}

	@Override
	public InstituteRequestStageType getStageType(String stageId) {
		Session currentSession = financeSessionFactory.getCurrentSession();
		return currentSession.get(InstituteRequestStageType.class, stageId);
	}

	@Override
	public InstituteRequest getInstituteRequest(String requestId) {
		Session currentSession = financeSessionFactory.getCurrentSession();
		return currentSession.get(InstituteRequest.class, requestId);
	}

	@Override
	public List<InstituteQuotation> getInstituteQuotationList(InstituteRequest request) {
		Session currentSession = financeSessionFactory.getCurrentSession();
		Query<InstituteQuotation> query = currentSession.createQuery("from InstituteQuotation where request = :request", InstituteQuotation.class);    
		query.setParameter("request", request);
		return query.getResultList();
	}

	@Override
	public List<InstituteRequestStage> getInstituteRequestStageList(InstituteRequest request) {
		Session currentSession = financeSessionFactory.getCurrentSession();
		Query<InstituteRequestStage> query = currentSession.createQuery("from InstituteRequestStage where id.request = :request", InstituteRequestStage.class);    
		query.setParameter("request", request);
		return query.getResultList();
	}

	@Override
	public List<ProjectRequest> getProjectRequestListByStageType(InstituteRequestStageType stageType) {
		Session currentSession = financeSessionFactory.getCurrentSession();
		Query<ProjectRequest> query = currentSession.createQuery("from ProjectRequest where stageType = :stageType", ProjectRequest.class);
		query.setParameter("stageType", stageType);
		return query.getResultList();
	}

	@Override
	public ProjectRequest getProjectRequest(String requestId) {
		Session currentSession = financeSessionFactory.getCurrentSession();
		return currentSession.get(ProjectRequest.class, requestId);
	}

	@Override
	public List<ProjectQuotation> getProjectQuotationList(ProjectRequest request) {
		Session currentSession = financeSessionFactory.getCurrentSession();
		Query<ProjectQuotation> query = currentSession.createQuery("from ProjectQuotation where request = :request", ProjectQuotation.class);    
		query.setParameter("request", request);
		return query.getResultList();
	}

	@Override
	public List<ProjectRequestStage> getProjectRequestStageList(ProjectRequest request) {
		Session currentSession = financeSessionFactory.getCurrentSession();
		Query<ProjectRequestStage> query = currentSession.createQuery("from ProjectRequestStage where id.request = :request", ProjectRequestStage.class);    
		query.setParameter("request", request);
		return query.getResultList();
	}

}
