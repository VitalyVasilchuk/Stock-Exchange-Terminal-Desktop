/* экземпляр данного класса позволят управлять списком данных из хранилища, полученного от фабрики */
package stockexchange.entity.order;

import java.util.List;
import stockexchange.SessionManager;

public class OrderManager {
    private final OrderDAO dao;

    public OrderManager(String mode, SessionManager sessionManager) {
	this.dao = new OrderFactory(mode, sessionManager).getDAO();
    }
    
    public List<OrderList.Order> getList() {
	return dao.getList();
    }

    public void delete(Long id) {
	dao.delete(id);
    }

    public void add(String operation, String params) {
	dao.add(operation, params);
    }
    
    public void getData(){
	dao.getData();
    }
    
}
