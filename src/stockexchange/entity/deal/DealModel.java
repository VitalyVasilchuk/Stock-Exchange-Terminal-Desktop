package stockexchange.entity.deal;

import java.util.List;
import javax.swing.table.AbstractTableModel;

public class DealModel extends AbstractTableModel{

    // список загловков для колонок в таблице
    private static final String[] HEADERS = {"type", "ID", "date",  "price", "volume", "amount", "member"};

    // список сделок, которые будем отображать в таблице
    private final List<Deal> deals;

    public DealModel(List<Deal> deals) {
	this.deals = deals;
    }

    @Override
    // получить количество строк в таблице
    public int getRowCount() {
	return deals.size();
    }

    @Override
    // получить количество столбцов
    public int getColumnCount() {
	return HEADERS.length;
    }

    @Override
    // вернуть загловок колонки - мы его берем из массива HEADERS
    public String getColumnName(int col) {
	return HEADERS[col];
    }

    @Override
    // получить объект для отображения в конкретной ячейке таблицы
    public Object getValueAt(int row, int col) {
	Deal deal = deals.get(row);
	// В зависимости от номера колонки возвращаем то или иное поле контакта
	switch (col) {
	    case 0:
		return deal.getType();
	    case 1:
		return deal.getId();
	    case 2:
		return deal.getPub_date();
	    case 3:
		return deal.getPrice();
	    case 4:
		return deal.getAmnt_trade();
	    case 5:
		return deal.getAmnt_base();
	    default:
		return deal.getUser();
	}
    }    
}

