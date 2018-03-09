/* Интерфейc, определяющий функции хранища данных по Сделкам */
package stockexchange.entity.offerBuy;

import java.util.List;
import stockexchange.entity.offerBuy.OfferBuy.Offer;

public interface OfferBuyDAO {

    // получить список всех сделок
    public List<Offer> getList();

    public void getData();

}
