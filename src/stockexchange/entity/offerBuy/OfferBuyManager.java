/* экземпляр данного класса позволят управлять списком данных из хранилища, полученного от фабрики */
package stockexchange.entity.offerBuy;

import java.util.List;
import stockexchange.SessionManager;

public class OfferBuyManager {
    private final OfferBuyDAO dao;

    public OfferBuyManager(String mode, SessionManager sessionManager) {
	this.dao = new OfferBuyFactory(mode, sessionManager).getDAO();
    }
    
    public List<OfferBuy.Offer> getList() {
	return dao.getList();
    }
    
    public void getData() {
	dao.getData();
    }
    
}
