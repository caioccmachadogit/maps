package base.imonitore.com.br.mapsapplication.servicesmanager;


import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class GsonDateAdapter implements JsonSerializer<Date>,
		JsonDeserializer<Date> {
	
	private final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
	private final SimpleDateFormat dateFormatSaida = new SimpleDateFormat("yyyyMMddHHmmss");

	@Override
	public JsonElement serialize(Date src, Type typeOfSrc, JsonSerializationContext context) {
		//return new JsonPrimitive(src.getTime());
		String dateFormatted = dateFormatSaida.format(src);
		//BatLog.i("GsonDateAdapter", "Data formatada: "+dateFormatted);
		return new JsonPrimitive(dateFormatted);
	}

	public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
			throws JsonParseException {
		
		//return new Date(json.getAsJsonPrimitive().getAsLong());
		
		Date date = null;
		
		try {
			date = dateFormat.parse(json.getAsString());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		return date;
		
	}
}
