package in.iiits.fundally.accounts.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import in.iiits.fundally.accounts.dao.AccountsDAOs;
import in.iiits.fundally.accounts.entity.Status;
import in.iiits.fundally.accounts.entity.User;
import in.iiits.fundally.accounts.entity.UserSession;


@Service
public class AccountsServicesImpl implements AccountsServices {

	@Autowired
	private AccountsDAOs accountsDAOs;
	
	@Override
	@Transactional
	public User getUser(String requestedEmailId, String userType) {
		
		return accountsDAOs.getUser(requestedEmailId, userType);
	}

	@Override
	@Transactional
	public String createUserSession(String userId) {
				
		User user = accountsDAOs.getUser(userId);
		
		// create an user session object
		UserSession userSession = new UserSession();
		
		// Initialize the object
		userSession.setStatus(Status.ACTIVE);
				
		// add session to user
		user.add(userSession);

		// return session ID
		return userSession.getId();
	}

	@Override
	@Transactional
	public String destroyUserSession(String sessionId) {
		
		UserSession userSession = accountsDAOs.getSession(sessionId);
		
		if(userSession != null){
			// Set the status to inactive mode and date to closing date
			userSession.setStatus(Status.INACTIVE);
			return "Logged Out Successfully";
		}
		else
			return null;
	}

	@Override
	@Transactional
	public String checkSessionStatus(String sessionId, String userType) {
		
		UserSession userSession = accountsDAOs.getSession(sessionId);
		
		if(userSession != null){
			
			if(userSession.getStatus().equals(Status.ACTIVE) && userSession.getUser().getType().equals(userType)) 
				return "ACTIVE";
			else
				return "INACTIVE";
		}
		else
			return "INACTIVE";
	}

	@Override
	@Transactional
	public String changePassword(String sessionId, String newPassword) {
		// get user form session
		UserSession userSession = accountsDAOs.getSession(sessionId);
		User user = userSession.getUser();
		
		// update user password
		user.setPassword(newPassword);
		
		// destroy session
		this.destroyUserSession(sessionId);
		
		return "Password changed successfully!";
	}
}
