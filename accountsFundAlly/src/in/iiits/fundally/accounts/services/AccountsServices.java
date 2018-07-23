package in.iiits.fundally.accounts.services;

import in.iiits.fundally.accounts.entity.User;

public interface AccountsServices {

	public User getUser(String requestedEmailId, String userType);
	
	public String createUserSession(String userId);

	public String destroyUserSession(String sessionId);

	public String checkSessionStatus(String sessionId, String userType);

	public String changePassword(String sessionId, String newPassword);
}
