/* Интерфейc, определяющий функции хранища данных по Счетам */
package stockexchange.entity.order;

import java.util.List;
import stockexchange.entity.order.OrderList.Order;

public interface OrderDAO {

    // получить список всех ордеров
    public List<Order> getList();

    // найти ордер в списке по заданному ID
    public Order get(Long id);

    // добавить Order
    public Long insert(Order order);

    // изменить Order
    //public Long update(Order order);
    
// отменить Order
    public void delete(Long id);

    public void add(String operation, String params);

    public void getData();

}
