package stockexchange.entity.order;

import java.util.ArrayList;
import java.util.List;
import stockexchange.entity.order.OrderList.Order;
import stockexchange.SessionManager;

public class OrderDAOPost implements OrderDAO {

    private final List<Order> orders;
    private SessionManager sessionManager;

    public OrderDAOPost(SessionManager sessionManager) {
	this.orders = new ArrayList();
	this.sessionManager = sessionManager;

	getData();
    }
    
    // загружает данные их хранилища в список
    @Override
    public final void getData() {
	List l = sessionManager.getOrder();
	orders.clear();
	orders.addAll(l);
    }

    @Override
    public void add(String operation, String params) {
	sessionManager.insertOrder(operation, params);
    }
    
// возвращает список данных, загруженных из хранилища
    @Override
    public List<Order> getList() {
	return orders;
    }

    @Override
    public Order get(Long id) {
	for (Order order : orders) {
	    if (order.getId().equals(id)) {
		return order;
	    }
	}
	return null;
    }

    @Override
    public Long insert(Order order) {
	throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void delete(Long id) {
	sessionManager.deleteOrder(id);
    }

    public SessionManager getSessionManager() {
	return sessionManager;
    }

    public void setSessionManager(SessionManager sessionManager) {
	this.sessionManager = sessionManager;
    }

}
