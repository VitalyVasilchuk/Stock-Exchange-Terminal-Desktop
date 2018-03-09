package stockexchange.entity.offerBuy;

import java.util.ArrayList;
import java.util.List;
import stockexchange.SessionManager;

public class OfferBuyDAOPost implements OfferBuyDAO {

    private final List<OfferBuy.Offer> offers;
    private SessionManager sessionManager;

    public OfferBuyDAOPost(SessionManager sessionManager) {
	this.offers = new ArrayList();
	this.sessionManager = sessionManager;
	
	getData();
    }
    
    // загружает данные их хранилища в список
    @Override
    public final void getData() {
	List l = sessionManager.getOfferBuy();
	offers.clear();
	offers.addAll(l);
    }

    // возвращает список данных, загруженных из хранилища
    @Override
    public List<OfferBuy.Offer> getList() {
	return offers;
    }

    public SessionManager getSessionManager() {
	return sessionManager;
    }

    public void setSessionManager(SessionManager sessionManager) {
	this.sessionManager = sessionManager;
    }

}
