/*  класс определяющий с каким хранилищем работать */
package stockexchange.entity.deal;

import stockexchange.SessionManager;

public class DealFactory {

    private String mode;
    private SessionManager sessionManager;

    public DealFactory(String mode, SessionManager sessionManager) {
	this.mode = mode;
	this.sessionManager = sessionManager;
    }
    
     public DealDAO getDAO() {
	switch (mode) {
	    case "JSON":
		return new DealDAOJson();
	    case "POST":
		return new DealDAOPost(sessionManager);
	    default:
		return new DealDAOJson();
	}
    }

}
