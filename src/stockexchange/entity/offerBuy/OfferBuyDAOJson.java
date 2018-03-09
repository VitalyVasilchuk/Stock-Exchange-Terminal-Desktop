package stockexchange.entity.offerBuy;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class OfferBuyDAOJson implements OfferBuyDAO {

    List<OfferBuy.Offer> offers;

    public OfferBuyDAOJson() {
	offers = new ArrayList();
	getData();
    }

    // загружаем внешние данные в список предложений (List)
    @Override
    public void getData() {
	try {
	    InputStream jsonInput = getClass().getResourceAsStream("OfferBuy.json");
	    BufferedReader reader = new BufferedReader(new InputStreamReader(jsonInput));

	    Gson gson = new Gson();
	    OfferBuy ob = gson.fromJson(reader, OfferBuy.class);
	    
	    List offerList = ob.getList();
	
	    offers.clear();
	    offers.addAll(offerList);
	    
	    jsonInput.close();
	} catch (IOException | JsonSyntaxException | JsonIOException ex) {
	    ex.printStackTrace();
	}
    }

    @Override
    public List<OfferBuy.Offer> getList() {
	return offers;
    }

}
