/* экземпляр данного класса позволят управлять списком данных из хранилища, полученного от фабрики */
package stockexchange.entity.account;

import java.util.List;
import stockexchange.SessionManager;

public class AccountManager {
    private final AccountDAO dao;

    public AccountManager(String mode, SessionManager sessionManager) {
	this.dao = new AccountFactory(mode, sessionManager).getDAO();
    }
    
    public List<AccountList.Account> getList() {
	return dao.getList();
    }
    
    public void getData() {
	dao.getData();
    }
    
}
