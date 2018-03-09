/*  класс определяющий с каким хранилищем работать */
package stockexchange.entity.offerBuy;

import stockexchange.SessionManager;

public class OfferBuyFactory {

    private String mode;
    private SessionManager sessionManager;

    public OfferBuyFactory(String mode, SessionManager sessionManager) {
	this.mode = mode;
	this.sessionManager = sessionManager;
    }

    public OfferBuyDAO getDAO() {
	switch (mode) {
	    case "JSON":
		return new OfferBuyDAOJson();
	    case "POST":
		return new OfferBuyDAOPost(sessionManager);
	    default:
		return new OfferBuyDAOJson();
	}
    }

}
