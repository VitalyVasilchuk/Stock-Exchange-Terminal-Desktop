package stockexchange.entity.account;

import java.util.ArrayList;
import java.util.List;
import stockexchange.entity.account.AccountList.Account;
import stockexchange.SessionManager;

public class AccountDAOPost implements AccountDAO {

    private final List<Account> accounts;
    private String currencyPair;
    private final SessionManager sessionManager;

    public AccountDAOPost(SessionManager sessionManager) {
	this.accounts = new ArrayList();
	this.sessionManager = sessionManager;

	getData();
    }

    // загружает данные их хранилища в список
    @Override
    public final void getData() {
	List l = sessionManager.getBalance();
	accounts.clear();
	accounts.addAll(l);
    }

    // возвращает список данных, загруженных из хранилища
    @Override
    public List<Account> getList() {
	return accounts;
    }

}
