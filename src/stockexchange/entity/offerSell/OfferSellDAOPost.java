package stockexchange.entity.offerSell;

import java.util.ArrayList;
import java.util.List;
import stockexchange.SessionManager;

public class OfferSellDAOPost implements OfferSellDAO {

    private final List<OfferSell.Offer> offers;
    private SessionManager sessionManager;

    public OfferSellDAOPost(SessionManager sessionManager) {
	this.offers = new ArrayList();
	this.sessionManager = sessionManager;

	getData();
    }

    // загружает данные их хранилища в список
    @Override
    public final void getData() {
	List l = sessionManager.getOfferSell();
	offers.clear();
	offers.addAll(l);
    }

    // возвращает список данных, загруженных из хранилища
    @Override
    public List<OfferSell.Offer> getList() {
	return offers;
    }

    public SessionManager getSessionManager() {
	return sessionManager;
    }

    public void setSessionManager(SessionManager sessionManager) {
	this.sessionManager = sessionManager;
    }

}
