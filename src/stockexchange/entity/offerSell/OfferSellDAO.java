/* Интерфейc, определяющий функции хранища данных по Сделкам */
package stockexchange.entity.offerSell;

import java.util.List;
import stockexchange.entity.offerSell.OfferSell.Offer;

public interface OfferSellDAO {

    // получить список всех предложений
    public List<Offer> getList();

    public void getData();

}
