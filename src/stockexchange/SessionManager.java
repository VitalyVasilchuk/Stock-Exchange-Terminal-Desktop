package stockexchange;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import stockexchange.entity.account.AccountList;
import stockexchange.entity.account.AccountList.Account;
import stockexchange.entity.deal.Deal;
import stockexchange.entity.offerBuy.OfferBuy;
import stockexchange.entity.offerSell.OfferSell;
import stockexchange.entity.order.OrderList;
import stockexchange.entity.order.OrderList.Order;
import stockexchange.http.ConnectionHTTP;

public class SessionManager {
    // набор свойств, необходимых для формирования HTTP-запроса методом POST
    private String publicKey;
    private String privateKey;
    private Long outOrderId;
    private int nonce;
    
    private static final String USER_AGENT = "Mozilla/5.0";//"curl/7.57.0";

    //--------------------------------------------------------------------------
    // Public API
    //--------------------------------------------------------------------------
    // получение списка сделок по выбраной валютной паре
    private static final String URL_DEALS = "https://btc-trade.com.ua/api/deals/";
    // получение списка заявок на продажу валюты торга в выбраной валютной паре
    private static final String URL_TRADES_SELL = "https://btc-trade.com.ua/api/trades/sell/";
    // получение списка заявок на покупку валюты торга в выбраной валютной паре
    private static final String URL_TRADES_BUY = "https://btc-trade.com.ua/api/trades/buy/";

    //--------------------------------------------------------------------------
    // Private API
    //--------------------------------------------------------------------------
    // авторизация
    private static final String URL_AUTH = "https://btc-trade.com.ua/api/auth";
    // Получение баланса
    private static final String URL_BALANCE = "https://btc-trade.com.ua/api/balance";
    // Создание заявки на продажу
    private static final String URL_SELL = "https://btc-trade.com.ua/api/sell/";
    // Создание заявки на покупку
    private static final String URL_BUY = "https://btc-trade.com.ua/api/buy/";
    // Получение списка открытых ордеров
    private static final String URL_ORDERS = "https://btc-trade.com.ua/api/my_orders/";
    // Проверка статуса выполнения ордера
    private static final String URL_ORDER_STATUS = "https://btc-trade.com.ua/api/order/status/";
    // Удаление выставленного ордера
    private static final String URL_ORDER_REMOVE = "https://btc-trade.com.ua/api/remove/order/";
    // Получение стоимости покупки 1 коина
    private static final String URL_ASK = "https://btc-trade.com.ua/api/ask/";
    // Получение стоимости продажи 1 коина
    private static final String URL_BID = "https://btc-trade.com.ua/api/bid/";

    private ConnectionHTTP connectionHTTP;
    private String currencyPair;

    public SessionManager(String currencyPair) {
	this.currencyPair = currencyPair;
	this.outOrderId = Math.round(Math.random() * 1000 + System.currentTimeMillis());
	this.nonce = 1;
	
	// чтение файла с публичным и приватным ключами
	Properties props = new Properties();
	try {
	    props.load(new FileInputStream(new File("keys.ini")));
	} catch (IOException e) {
	    JOptionPane.showMessageDialog(null, "Wrong keys!\n" + e.toString(), "Wrong keys", JOptionPane.ERROR_MESSAGE);
	    return;
	}

	publicKey = props.getProperty("public_key");
	privateKey = props.getProperty("private_key");
	if (publicKey.length() < 64 || privateKey.length() < 64) {
	    JOptionPane.showMessageDialog(null, "Incorrect key length!", "Wrong keys", JOptionPane.ERROR_MESSAGE);
	    return;
	}
	
	String response;
	connectionHTTP = new ConnectionHTTP();
    }

    // Авторизация, данный запрос должен быть выполнен первым
    public String auth() {
	StringBuffer response = sendPOST(URL_AUTH, "");

	if (response.toString().length() > 0) {
	    Map<String, Object> retMap = new Gson().fromJson(response.toString(), new TypeToken<HashMap<String, Object>>() {
	    }.getType());
	    System.out.println("auth.status = " + retMap.get("status"));
	}

	return response.toString();
    }

    // запрос остатков по счетам
    public List getBalance() {
	List<Account> offers = new ArrayList();

	try {
	    StringBuffer response = sendPOST(URL_BALANCE, "");
	    Gson gson = new GsonBuilder().create();
	    AccountList accList = gson.fromJson(response.toString(), AccountList.class);

	    if (accList != null) {
		if (accList.getList() != null) {
		    accList.getList().forEach(x -> offers.add(x));
		}
	    }
	} catch (JsonSyntaxException e) {
	    System.err.println(getClass().getName() + ", getBalance()\n" + e.toString());
	}

	return offers;
    }

    // Возвращает список сделок по заданной валютной паре в формате вида "btc_uah"
    public List getDeals() {
	StringBuffer response = sendPOST(URL_DEALS + currencyPair, "");
	Gson gson = new GsonBuilder().create();

	List<Deal> list = gson.fromJson(response.toString(), new TypeToken<List<Deal>>() {
	}.getType());

	return list;
    }

    public List<OfferBuy.Offer> getOfferBuy() {
	List<OfferBuy.Offer> offers = new ArrayList();

	try {
	    StringBuffer response = sendPOST(URL_TRADES_BUY + currencyPair, "");
	    Gson gson = new GsonBuilder().create();
	    OfferBuy ob = gson.fromJson(response.toString(), OfferBuy.class);

	    if (ob != null) {
		ob.getList().forEach(x -> offers.add(x));
	    }
	} catch (JsonSyntaxException e) {
	    System.err.println(getClass().getName() + ", getOfferBuy()\n" + e.toString());
	}

	return offers;
    }

    public List getOfferSell() {
	List<OfferSell.Offer> offers = new ArrayList();

	try {

	    StringBuffer response = sendPOST(URL_TRADES_SELL + currencyPair, "");
	    Gson gson = new GsonBuilder().create();
	    OfferSell os = gson.fromJson(response.toString(), OfferSell.class);

	    if (os != null) {
		os.getList().forEach(x -> offers.add(x));
	    }
	} catch (JsonSyntaxException e) {
	    System.err.println(getClass().getName() + ", getOfferSell()\n" + e.toString());
	}

	return offers;
    }

    public List getOrder() {
	List<Order> orders = new ArrayList();

	try {
	    StringBuffer response = sendPOST(URL_ORDERS + currencyPair, "");
	    Gson gson = new GsonBuilder().create();
	    OrderList orderList = gson.fromJson(response.toString(), OrderList.class);

	    if (orderList != null) {
		if (orderList.getList() != null) {
		    orderList.getList().forEach(x -> orders.add(x));
		}
	    }
	} catch (JsonSyntaxException e) {
	    System.err.println(getClass().getName() + ", getOrder()\n" + e.toString());
	}

	return orders;
    }

    public void deleteOrder(long id) {
	StringBuffer response = sendPOST(URL_ORDER_REMOVE + id, "");
    }

    // создание ордера по операции
    public void insertOrder(String operation, String params) {
	String addressPOST = "";
	switch (operation) {
	    case "BUY":
		addressPOST = URL_BUY + currencyPair;
		break;
	    case "SELL":
		addressPOST = URL_SELL + currencyPair;
		break;
	}

	if (addressPOST.length() > 0) {
	    StringBuffer response = sendPOST(addressPOST, params);

	    Map<String, Object> retMap = new Gson().fromJson(response.toString(), new TypeToken<HashMap<String, Object>>() {
	    }.getType());
	    System.out.println(operation + ": status = " + retMap.get("status") + ", description = " + retMap.get("description"));
	}
    }

    public String getCurrencyPair() {
	return currencyPair;
    }

    public void setCurrencyPair(String currencyPair) {
	this.currencyPair = currencyPair;
    }
    
    //  возвращает хеш сумму переданной строки с использованием алгоритма хеширования "SHA-256"
    public String getHashSHA256(String text) {
	try {
	    MessageDigest md = MessageDigest.getInstance("SHA-256");
	    md.update(text.getBytes(StandardCharsets.UTF_8));
	    byte[] digest = md.digest();
	    String hex = String.format("%064x", new BigInteger(1, digest));

	    return hex;
	} catch (NoSuchAlgorithmException ex) {
	    Logger.getLogger(ConnectionHTTP.class.getName()).log(Level.SEVERE, null, ex);
	}
	return "";
    }
    
    public String getAPISign(String params) {
	return getHashSHA256(params + getPrivateKey());
    }
    
    // возвращает сформированую строку параметров для POST-запроса
    public String createRequestParams(String params) {
	String requestParams = "out_order_id=" + getOutOrderId() + "&nonce=" + getNonce();
	if (params.length() > 0) {
	    requestParams += "&" + params;
	}	
	nonce++;
	outOrderId++;
	
	return requestParams;
    }
    
    // возвращает сформированую коллекцию параметров для HTTP-заголовка
    public Map createParamHTTP(String requestParams) {
	Map<String, String> mapParams = new HashMap<>();
	mapParams.put("User-Agent", USER_AGENT);
	mapParams.put("public-key", getPublicKey());
	mapParams.put("api-sign", getAPISign(requestParams));
	return mapParams;
    }
    
    // отправляет POST-запрос с сформированными HTTP-заголовком и POST-параметрами
    public StringBuffer sendPOST (String url, String params) {
	String requestParams = createRequestParams(params);
	Map<String, String> headerHTTP = createParamHTTP(requestParams);
	StringBuffer response = connectionHTTP.sendPOST(url, headerHTTP, requestParams);
	return response;
    }
    
    
    //--------------------------------------------------------------------------
    // GET и SET методы
    //--------------------------------------------------------------------------
    public String getPublicKey() {
	return publicKey;
    }

    public void setPublicKey(String publicKey) {
	this.publicKey = publicKey;
    }

    public String getPrivateKey() {
	return privateKey;
    }

    public void setPrivateKey(String privateKey) {
	this.privateKey = privateKey;
    }

    public Long getOutOrderId() {
	return outOrderId;
    }

    public void setOutOrderId(Long outOrderId) {
	this.outOrderId = outOrderId;
    }

    public int getNonce() {
	return nonce;
    }

    public void setNonce(int nonce) {
	this.nonce = nonce;
    }
}
