package stockexchange.entity.offerSell;

import java.util.List;
import javax.swing.table.AbstractTableModel;
import stockexchange.entity.offerSell.OfferSell.Offer;

// предложения на покупку
public class OfferSellModel extends AbstractTableModel {

    private static final String[] HEADERS = {"price", "volume", "amount"};
    private final List<Offer> offers;

    public OfferSellModel(List<Offer> offers) {
	this.offers = offers;
    }

    @Override
    // Получить количество строк в таблице: размер коллекции
    public int getRowCount() {
	return offers.size();
    }

    @Override
    // Получить количество столбцов - их у нас стольк же, сколько заголовков
    public int getColumnCount() {
	return HEADERS.length;
    }

    @Override
    // Вернуть загловок колонки - мы его берем из массива HEADERS
    public String getColumnName(int col) {
	return HEADERS[col];
    }

    @Override
    // Получить объект для тображения в кокнретной ячейке таблицы
    // В данном случае мы отдаем строковое представление поля
    public Object getValueAt(int row, int col) {
	Offer trade = offers.get(row);
	// В зависимости от номера колонки возвращаем то или иное поле контакта
	switch (col) {
	    case 0:
		return "<html><b>" + trade.getPrice();
	    case 1:
		return trade.getCurrency_trade();
	    case 2:
		return trade.getCurrency_base();
	    default:
		return "";
	}
    }
}
