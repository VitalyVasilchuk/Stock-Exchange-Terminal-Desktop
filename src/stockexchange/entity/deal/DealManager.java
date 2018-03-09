/* экземпляр данного класса позволят управлять списком данных из хранилища, полученного от фабрики */
package stockexchange.entity.deal;

import java.util.List;
import stockexchange.SessionManager;

public class DealManager {
    private final DealDAO dao;

    public DealManager(String mode, SessionManager sessionManager) {
	this.dao = new DealFactory(mode, sessionManager).getDAO();
    }
    
    public List<Deal> getList() {
	return dao.getList();
    }
    
    public Deal getDeal(Long id) {
	return dao.get(id);
    }
    
    public Long insertDeal(Deal deal) {
	return dao.insert(deal);
    }
    
    public Long updateDeal(Deal deal){
	return dao.update(deal);
    }
    
    public void deleteDeal(Long id) {
	dao.delete(id);
    }
    
    public void getData(){
	dao.getData();
    }
    
}
