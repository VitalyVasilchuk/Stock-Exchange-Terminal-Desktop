/*  класс определяющий с каким хранилищем работать */
package stockexchange.entity.order;

import stockexchange.SessionManager;

public class OrderFactory {

    private String mode;
    private SessionManager sessionManager;

    public OrderFactory(String mode, SessionManager sessionManager) {
	this.mode = mode;
	this.sessionManager = sessionManager;
    }
    
    public OrderDAO getDAO() {
	switch (mode) {
	    case "JSON":
		return new OrderDAOJson();
	    case "POST":
		return new OrderDAOPost(sessionManager);
	    default:
		return new OrderDAOJson();
	}
    }

}
