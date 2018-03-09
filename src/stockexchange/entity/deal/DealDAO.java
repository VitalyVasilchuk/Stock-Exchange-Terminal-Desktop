/* Интерфейc, определяющий функции хранища данных по Сделкам */
package stockexchange.entity.deal;

import java.util.List;

public interface DealDAO {

    // получить список всех сделок
    public List<Deal> getList();
    
    // найти сделку в списке по заданному ID
    public Deal get(Long id);

    // добавить сделку
    public Long insert(Deal deal);

    // изменить сделку
    public Long update(Deal deal);

    // удалить сделку
    public void delete(Long deal);

    public void getData();

}
