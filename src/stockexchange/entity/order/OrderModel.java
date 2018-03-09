package stockexchange.entity.order;

import java.util.List;
import javax.swing.table.AbstractTableModel;
import stockexchange.entity.order.OrderList.Order;

public class OrderModel extends AbstractTableModel {

    private final String[] HEADERS = {"type", "ID", "price", "volume", "amount"};
    private final List<Order> orders;

    public OrderModel(List<Order> orders) {
	this.orders = orders;
    }

    @Override
    public int getRowCount() {
	return orders.size();
    }

    @Override
    public int getColumnCount() {
	return HEADERS.length;
    }
    
    @Override
    public String getColumnName(int col) {
	return HEADERS[col];
    }
    
    @Override
    public Object getValueAt(int row, int col) {
	Order order = orders.get(row);
	switch (col) {
	    case 0:
		return order.getType();
	    case 1:
		return order.getId();
	    case 2:
		return order.getPrice();
	    case 3:
		return order.getAmnt_trade();
	   default:
		return order.getAmnt_base();
	}
    }

}
