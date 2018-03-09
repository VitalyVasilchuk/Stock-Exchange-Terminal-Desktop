package stockexchange.entity.account;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import stockexchange.entity.account.AccountList.Account;

public class AccountDAOJson implements AccountDAO {

    List<Account> offers;

    public AccountDAOJson() {
	offers = new ArrayList();
	getData();
    }

    // загружаем внешние данные в список предложений (List)
    @Override
    public void getData() {
	try {
	    InputStream jsonInput = getClass().getResourceAsStream("Account.json");
	    BufferedReader reader = new BufferedReader(new InputStreamReader(jsonInput));

	    Gson gson = new Gson();
	    AccountList acc = gson.fromJson(reader, AccountList.class);
	    
	    List offerList = acc.getList();
	
	    offers.clear();
	    offers.addAll(offerList);
	    
	    jsonInput.close();
	} catch (IOException | JsonSyntaxException | JsonIOException ex) {
	    ex.printStackTrace();
	}
    }

    @Override
    public List<Account> getList() {
	return offers;
    }

}
