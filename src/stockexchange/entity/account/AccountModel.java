package stockexchange.entity.account;

import java.util.List;
import javax.swing.table.AbstractTableModel;
import stockexchange.entity.account.AccountList.Account;

// счета по валютам
public class AccountModel extends AbstractTableModel {

    // Список загловков для колонок в таблице
    private static final String[] HEADERS = {"валюта", "баланс"};

    // Здесь мы храним список контактов, которые будем отображать в таблице
    private final List<Account> accounts;

    public AccountModel(List<Account> accounts) {
	this.accounts = accounts;
    }

    @Override
    // Получить количество строк в таблице - у нас это размер коллекции
    public int getRowCount() {
	return accounts.size();
    }

    @Override
    // Получить количество столбцов - их у нас стольк же, сколько полей
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
	Account account = accounts.get(row);
	// В зависимости от номера колонки возвращаем то или иное поле контакта
	switch (col) {
	    case 0:
		return account.getCurrency();
	    case 1:
		return account.getBalance();
	    default:
		return account.getBalance();
	}
    }
}
