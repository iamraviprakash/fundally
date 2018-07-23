package in.iiits.fundally.accounts.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import in.iiits.fundally.accounts.entity.User;
import in.iiits.fundally.accounts.entity.UserSession;

@Repository
public class AccountsDAOsImpl implements AccountsDAOs {

	@Autowired
	private SessionFactory accountsSessionFactory;

	@Override
	public UserSession getSession(String sessionId) {
		// get the current hibernate session
		Session currentSession = accountsSessionFactory.getCurrentSession();
		//get the session of specified sessionId
		return currentSession.get(UserSession.class, sessionId);
	}
	
	@Override
	public User getUser(String requestedEmailId, String userType) {
		// get the current hibernate session
		Session currentSession = accountsSessionFactory.getCurrentSession();
		// create a query
		Query<User> query = currentSession.createQuery("from User where emailId ='"+requestedEmailId+"' and type ='"+userType+"'", User.class);
		//execute the query and get the result
		List<User> userList = query.getResultList();
		
		System.out.println(userList);
		
		if(!userList.isEmpty())
			return userList.get(0);
		else
			return null;
	}

	@Override
	public User getUser(String userId) {
		
		Session currentSession = accountsSessionFactory.getCurrentSession();
		return currentSession.get(User.class, userId);
	}
	
}
