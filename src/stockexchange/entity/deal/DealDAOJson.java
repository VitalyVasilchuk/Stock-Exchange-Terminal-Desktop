package stockexchange.entity.deal;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DealDAOJson implements DealDAO {

    List<Deal> deals;

    public DealDAOJson() {
	deals = new ArrayList();
	getData();
    }

    // загружаем внешние данные в список сделок (List)
    @Override
    public void getData() {
	Gson gson = new Gson();
	try {
	    InputStream jsonInput = getClass().getResourceAsStream("Deal.json");
	    BufferedReader reader = new BufferedReader(new InputStreamReader(jsonInput));

	    Deal[] d = gson.fromJson(reader, Deal[].class);

	    deals.clear();
	    deals.addAll(Arrays.asList(d));
	    jsonInput.close();
	} catch (IOException | JsonSyntaxException | JsonIOException ex) {
	    ex.printStackTrace();
	}
    }

    @Override
    public List<Deal> getList() {
	return deals;
    }

    @Override
    public Deal get(Long id) {
	for (Deal deal : deals) {
	    if (deal.getId().equals(id)) {
		return deal;
	    }
	}
	return null;
    }

    @Override
    public Long insert(Deal deal) {
	return null;
    }

    @Override
    public Long update(Deal deal) {
	return null;
    }

    @Override
    public void delete(Long deal) {
    }

}
