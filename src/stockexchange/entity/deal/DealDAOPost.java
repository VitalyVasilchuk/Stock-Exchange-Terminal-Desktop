package stockexchange.entity.deal;

import java.util.ArrayList;
import java.util.List;
import stockexchange.SessionManager;

public class DealDAOPost implements DealDAO {

    private final List<Deal> deals;
    private SessionManager sessionManager;

    public DealDAOPost(SessionManager sessionManager) {
	this.deals = new ArrayList();
	this.sessionManager = sessionManager;

	getData();
    }

    // загружает данные их хранилища в список
    @Override
    public final void getData() {
	List l = sessionManager.getDeals();
	deals.clear();
	deals.addAll(l);
    }

    // возвращает список данных, загруженных из хранилища
    @Override
    public List<Deal> getList() {
	return deals;
    }

    @Override
    public Deal get(Long id) {
	for (Deal deal : deals) {
	    if (deal.getId().equals(id)) {
		return deal;
	    }
	}
	return null;
    }

    @Override
    public Long insert(Deal deal) {
	return null;
    }

    @Override
    public Long update(Deal deal) {
	return null;
    }

    @Override
    public void delete(Long deal) {
    }

    public SessionManager getSessionManager() {
	return sessionManager;
    }

    public void setSessionManager(SessionManager sessionManager) {
	this.sessionManager = sessionManager;
    }
    
}
