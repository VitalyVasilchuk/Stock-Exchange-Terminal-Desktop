package stockexchange.entity.order;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import stockexchange.entity.order.OrderList.Order;

public class OrderDAOJson implements OrderDAO {

    List<Order> orders;

    public OrderDAOJson() {
	orders = new ArrayList();
	getData();
    }

    // загружаем внешние данные в список предложений (List)
    @Override
    public void getData() {
	try {
	    InputStream jsonInput = getClass().getResourceAsStream("Order.json");
	    BufferedReader reader = new BufferedReader(new InputStreamReader(jsonInput));

	    Gson gson = new Gson();
	    OrderList acc = gson.fromJson(reader, OrderList.class);
	    
	    List offerList = acc.getList();
	
	    orders.clear();
	    orders.addAll(offerList);
	    
	    jsonInput.close();
	} catch (IOException | JsonSyntaxException | JsonIOException ex) {
	    ex.printStackTrace();
	}
    }

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
	System.out.println(id+"; "+orders.size());
	for (Iterator<Order> it = orders.iterator(); it.hasNext();) {
	    Order o = it.next();
	    if (o.getId().equals(""+id)) {
		it.remove();
		System.out.println(id+"; "+orders.size());
	    }
	}
    }

    @Override
    public void add(String operation, String params) {
	throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
