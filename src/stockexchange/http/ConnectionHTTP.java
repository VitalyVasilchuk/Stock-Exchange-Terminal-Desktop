package stockexchange.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

public class ConnectionHTTP {

    private int timeoutConnect;
    private int timeoutRead;
    private boolean debug;

    public ConnectionHTTP() {
	this.timeoutConnect = 250;
	this.timeoutRead = 3000;
	this.debug = true;
    }

    // подготовка и отправка POST-запроса, с передачей параметров в HTTP-заголовке
    public StringBuffer sendPOST(String sURL, Map<String, String> headerHTTP, String requestParams) {
	StringBuffer response = new StringBuffer();
	HttpURLConnection connection = null;

	if (debug) {
	    System.out.println("{================================================");
	    System.out.println("Request: {" + sURL + "}");
	    System.out.println("requestParams {" + requestParams + "}");
	    System.out.println("-------------------------------------------------");
	}

	try {
	    // создание подключения по заданному адресу
	    connection = (HttpURLConnection) new URL(sURL).openConnection();

	    // установка параметров HTTP-заголовка
	    connection.setRequestMethod("POST");
	    
	    // формирование параметров HTTP-заголовка
	    for (Map.Entry<String, String> param : headerHTTP.entrySet()) {
		String name = param.getKey();
		String value = param.getValue();
		connection.setRequestProperty(name, value);
	    }
	    
	    connection.setUseCaches(false);
	    connection.setConnectTimeout(timeoutConnect);
	    connection.setReadTimeout(timeoutRead);

	    // формирование параметров POST-запроса
	    connection.setDoOutput(true);
	    OutputStream os = connection.getOutputStream();
	    os.write(requestParams.getBytes());
	    os.flush();
	    os.close();

	    // выполняем подключение
	    connection.connect();
	    
	    int responseCode = connection.getResponseCode();

	    // в случае успешного подключения формируем ответ
	    if (responseCode == HttpURLConnection.HTTP_OK) {
		String inputLine;
		// получаем входящий поток, читаем его и закрываем
		BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
		while ((inputLine = in.readLine()) != null) {
		    response.append(inputLine);
		}
		in.close();
	    }
//	    else if (responseCode == 502 || responseCode == 504) {
//		sendPOST(sURL, headerHTTP, requestParams);
//	    }
		
	    if (debug) {
		System.out.println("Response { code: " + responseCode + ", msg: " + connection.getResponseMessage() + "}");
		System.out.println("================================================}");
	    }
	} catch (IOException e) {
	    System.err.println(getClass().getName() + ", sendPOST()\n" + e.toString());
	} finally {
	    if (connection != null) {
		connection.disconnect();
	    }
	}

	return response;
    }

    //--------------------------------------------------------------------------
    // GET и SET методы
    //--------------------------------------------------------------------------
    public int getTimeoutConnect() {
	return timeoutConnect;
    }

    public void setTimeoutConnect(int timeoutConnect) {
	this.timeoutConnect = timeoutConnect;
    }

    public int getTimeoutRead() {
	return timeoutRead;
    }

    public void setTimeoutRead(int timeoutRead) {
	this.timeoutRead = timeoutRead;
    }

    public boolean isDebug() {
	return debug;
    }

    public void setDebug(boolean debug) {
	this.debug = debug;
    }

}
