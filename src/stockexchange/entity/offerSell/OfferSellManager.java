/* экземпляр данного класса позволят управлять списком данных из хранилища, полученного от фабрики */
package stockexchange.entity.offerSell;

import java.util.List;
import stockexchange.SessionManager;

public class OfferSellManager {

    private final OfferSellDAO dao;

    public OfferSellManager(String mode, SessionManager sessionManager) {
	this.dao = new OfferSellFactory(mode, sessionManager).getDAO();
    }

    public List<OfferSell.Offer> getList() {
	return dao.getList();
    }

    public void getData() {
	dao.getData();
    }

}
