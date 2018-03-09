package stockexchange.entity.offerSell;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import stockexchange.entity.offerSell.OfferSell.Offer;

public class OfferSellDAOJson implements OfferSellDAO {

    List<OfferSell.Offer> offers;

    public OfferSellDAOJson() {
	offers = new ArrayList();
	getData();
    }

    // загружаем внешние данные в список предложений (List)
    @Override
    public void getData() {
	try {
	    InputStream jsonInput = getClass().getResourceAsStream("OfferSell.json");
	    BufferedReader reader = new BufferedReader(new InputStreamReader(jsonInput));

	    Gson gson = new Gson();
	    OfferSell ob = gson.fromJson(reader, OfferSell.class);
	    
	    List offerList = ob.getList();
	
	    offers.clear();
	    offers.addAll(offerList);
	    
	    jsonInput.close();
	} catch (IOException | JsonSyntaxException | JsonIOException ex) {
	    ex.printStackTrace();
	}
    }

    @Override
    public List<Offer> getList() {
	return offers;
    }

}
