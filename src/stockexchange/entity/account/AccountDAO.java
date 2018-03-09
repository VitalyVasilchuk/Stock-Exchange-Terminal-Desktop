/* Интерфейc, определяющий функции хранища данных по Счетам */
package stockexchange.entity.account;

import java.util.List;
import stockexchange.entity.account.AccountList.Account;

public interface AccountDAO {

    // получить список всех счетов
    public List<Account> getList();

    // загрузить список всех счетов их хранилища
    public void getData();

}
