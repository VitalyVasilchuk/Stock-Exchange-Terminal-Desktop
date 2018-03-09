/*  класс определяющий с каким хранилищем работать */
package stockexchange.entity.offerSell;

import stockexchange.SessionManager;

public class OfferSellFactory {

    private String mode;
    private SessionManager sessionManager;
    private String currencyPair;

    public OfferSellFactory(String mode, SessionManager sessionManager) {
	this.mode = mode;
	this.sessionManager = sessionManager;
	this.currencyPair = currencyPair;
    }

    public OfferSellDAO getDAO() {
	switch (mode) {
	    case "JSON":
		return new OfferSellDAOJson();
	    case "POST":
		return new OfferSellDAOPost(sessionManager);
	    default:
		return new OfferSellDAOJson();
	}
    }

}
