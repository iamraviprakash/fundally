package in.iiits.fundally.accounts.dao;

import in.iiits.fundally.accounts.entity.User;
import in.iiits.fundally.accounts.entity.UserSession;

public interface AccountsDAOs {

	// for authentication
	public User getUser(String requestedEmailId, String userType);

	// to create session
	public User getUser(String userId);
	
	public UserSession getSession(String sessionId);
}
