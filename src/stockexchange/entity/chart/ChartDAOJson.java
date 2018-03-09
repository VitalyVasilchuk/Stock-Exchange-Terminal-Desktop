package stockexchange.entity.chart;

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

public class ChartDAOJson {

    List dataList;

    public ChartDAOJson() {
	dataList = new ArrayList();
	getData();
    }

    // загружаем внешние данные в список сделок (List)
    public void getData() {
	Gson gson = new Gson();
	try {
	    InputStream jsonInput = getClass().getResourceAsStream("chart.json");
	    BufferedReader reader = new BufferedReader(new InputStreamReader(jsonInput));

	    Object[] d = gson.fromJson(reader, Object[].class);

	    dataList.clear();
	    dataList.addAll(Arrays.asList(d));
	    jsonInput.close();
	} catch (IOException | JsonSyntaxException | JsonIOException ex) {
	    ex.printStackTrace();
	}
    }

}
