/*  класс определяющий с каким хранилищем работать */
package stockexchange.entity.account;

import stockexchange.SessionManager;

public class AccountFactory {

    private final String mode;
    private SessionManager sessionManager;

    public AccountFactory(String mode, SessionManager sessionManager) {
	this.mode = mode;
	this.sessionManager = sessionManager;
    }

    public AccountDAO getDAO() {
	switch (mode) {
	    case "JSON":
		return new AccountDAOJson();
	    case "POST":
		return new AccountDAOPost(sessionManager);
	    default:
		return new AccountDAOJson();
	}
    }

}
